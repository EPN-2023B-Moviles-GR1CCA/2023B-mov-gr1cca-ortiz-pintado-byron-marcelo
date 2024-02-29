package com.example.examen1b

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class ClientesActivity : AppCompatActivity() {

    private lateinit var recyclerViewClientes: RecyclerView
    private lateinit var btnAgregarCliente: Button
    private var habitacionId: String? = null // Cambiado a String para coincidir con los ID de Firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cliente_list)

        recyclerViewClientes = findViewById(R.id.recyclerViewClientes)
        btnAgregarCliente = findViewById(R.id.btnAgregarCliente)

        habitacionId = intent.getStringExtra("HABITACION_ID")
        if (habitacionId == null) {
            Toast.makeText(this, "Error: No se especificó la habitación", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        btnAgregarCliente.setOnClickListener {
            val intent = Intent(this, ClienteFormActivity::class.java)
            intent.putExtra("HABITACION_ID", habitacionId) // Asegúrate de que habitacionId contiene el ID correcto
            startActivity(intent)
        }


        cargarClientes()
    }

    private fun cargarClientes() {
        FirebaseFirestore.getInstance().collection("clientes")
            .whereEqualTo("habitacionId", habitacionId)
            .get()
            .addOnSuccessListener { result ->
                val clientes = result.toObjects(ClienteHotel::class.java)
                Log.d("ClientesActivity", "Clientes cargados: $clientes")
                recyclerViewClientes.layoutManager = LinearLayoutManager(this)
                recyclerViewClientes.adapter = ClienteAdapter(clientes,
                    onItemClicked = { _ ->
                        // Si no necesitas manejar el clic aquí, simplemente puedes dejarlo vacío
                    },
                    onItemEditClicked = { cliente ->
                        val intent = Intent(this, ClienteFormActivity::class.java)
                        intent.putExtra("CLIENTE_ID", cliente.id) // Asumiendo que tienes un id en ClienteHotel
                        intent.putExtra("HABITACION_ID", habitacionId)
                        startActivity(intent)
                    },
                    onEliminarClicked = { cliente ->
                        FirebaseFirestore.getInstance().collection("clientes").document(cliente.id!!)
                            .delete()
                            .addOnSuccessListener {
                                Toast.makeText(this, "Cliente eliminado con éxito", Toast.LENGTH_SHORT).show()
                                cargarClientes() // Recargar lista de clientes
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, "Error al eliminar cliente", Toast.LENGTH_SHORT).show()
                            }
                    }
                )

            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error al cargar clientes: ${exception.message}", Toast.LENGTH_SHORT).show()
                Log.e("ClientesActivity", "Error al cargar clientes", exception)
            }
    }
}
