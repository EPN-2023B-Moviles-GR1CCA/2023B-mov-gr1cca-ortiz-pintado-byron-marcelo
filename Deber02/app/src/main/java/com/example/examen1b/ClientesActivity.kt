package com.example.examen1b

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ClientesActivity : AppCompatActivity() {

    private lateinit var recyclerViewClientes: RecyclerView
    private lateinit var btnAgregarCliente: Button
    private lateinit var clienteHotelDao: ClienteHotelDao
    private var habitacionId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cliente_list)

        recyclerViewClientes = findViewById(R.id.recyclerViewClientes)
        btnAgregarCliente = findViewById(R.id.btnAgregarCliente)
        clienteHotelDao = ClienteHotelDao(DatabaseHelper(this))

        habitacionId = intent.getIntExtra("HABITACION_ID", -1)
        if (habitacionId == -1) {
            Toast.makeText(this, "Error: No se especificó la habitación", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        btnAgregarCliente.setOnClickListener {
            val intent = Intent(this, ClienteFormActivity::class.java)
            intent.putExtra("HABITACION_ID", habitacionId)
            startActivity(intent)
        }

        cargarClientes()
    }

    private fun cargarClientes() {
        val clientes = clienteHotelDao.obtenerClientesPorHabitacion(habitacionId)
        recyclerViewClientes.layoutManager = LinearLayoutManager(this)
        recyclerViewClientes.adapter = ClienteAdapter(clientes,
            onItemClicked = { cliente ->
                // Manejar clic en el cliente, por ejemplo, mostrar detalles
            },
            onItemEditClicked = { cliente ->
                // Iniciar ClienteFormActivity para editar el cliente
                val intent = Intent(this, ClienteFormActivity::class.java)
                intent.putExtra("CLIENTE_ID", cliente.id)
                intent.putExtra("HABITACION_ID", habitacionId)
                startActivity(intent)
            },
            onEliminarClicked = { cliente ->
                clienteHotelDao.eliminarCliente(cliente.id)
                cargarClientes()
            }
        )
    }

    override fun onResume() {
        super.onResume()
        cargarClientes()
    }
}
