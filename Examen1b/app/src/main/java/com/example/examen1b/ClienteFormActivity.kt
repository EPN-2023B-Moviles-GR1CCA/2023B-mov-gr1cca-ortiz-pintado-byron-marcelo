package com.example.examen1b

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ClienteFormActivity : AppCompatActivity() {

    private var clienteId: Int = -1
    private lateinit var clienteHotelDao: ClienteHotelDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cliente_form)

        clienteHotelDao = ClienteHotelDao(DatabaseHelper(this)) // Inicializar DAO

        val habitacionId = intent.getIntExtra("HABITACION_ID", -1)
        if (habitacionId == -1) {
            Toast.makeText(this, "Error: No se especificó la habitación", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        clienteId = intent.getIntExtra("CLIENTE_ID", -1)
        if (clienteId != -1) {
            cargarDatosCliente(clienteId)
        }

        val btnGuardarCliente = findViewById<Button>(R.id.btnGuardarCliente)
        btnGuardarCliente.setOnClickListener {
            guardarCliente(habitacionId)
        }
    }

    private fun cargarDatosCliente(id: Int) {
        val cliente = clienteHotelDao.obtenerClientePorId(id)
        cliente?.let {
            findViewById<EditText>(R.id.etNombreCliente).setText(it.nombre)
            findViewById<EditText>(R.id.etDocumentoCliente).setText(it.documento)
            findViewById<EditText>(R.id.etFechaCheckIn).setText(it.fechaCheckIn)
            findViewById<EditText>(R.id.etFechaCheckOut).setText(it.fechaCheckOut)
            findViewById<EditText>(R.id.etNumeroTelefono).setText(it.numeroTelefono)
        }
    }

    private fun guardarCliente(habitacionId: Int) {
        val nombre = findViewById<EditText>(R.id.etNombreCliente).text.toString()
        val documento = findViewById<EditText>(R.id.etDocumentoCliente).text.toString()
        val fechaCheckIn = findViewById<EditText>(R.id.etFechaCheckIn).text.toString()
        val fechaCheckOut = findViewById<EditText>(R.id.etFechaCheckOut).text.toString()
        val numeroTelefono = findViewById<EditText>(R.id.etNumeroTelefono).text.toString()

        val cliente = ClienteHotel(
            id = if (clienteId == -1) 0 else clienteId,
            nombre = nombre,
            documento = documento,
            fechaCheckIn = fechaCheckIn,
            fechaCheckOut = fechaCheckOut,
            numeroTelefono = numeroTelefono,
            habitacionId = habitacionId
        )

        val resultado = if (clienteId == -1) {
            clienteHotelDao.insertarCliente(cliente)
        } else {
            clienteHotelDao.actualizarCliente(cliente).toLong()
        }

        if (resultado != -1L) {
            Toast.makeText(this, "Cliente guardado con éxito", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Error al guardar el cliente", Toast.LENGTH_SHORT).show()
        }
    }
}
