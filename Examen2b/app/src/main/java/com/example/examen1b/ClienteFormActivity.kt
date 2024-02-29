package com.example.examen1b

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class ClienteFormActivity : AppCompatActivity() {

    private var clienteId: String? = null
    private lateinit var etNombreCliente: EditText
    private lateinit var etDocumentoCliente: EditText
    private lateinit var etFechaCheckIn: EditText
    private lateinit var etFechaCheckOut: EditText
    private lateinit var etNumeroTelefono: EditText
    private var habitacionId: String? = null
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cliente_form)

        // Se mueve la extracción del ID de habitación aquí para evitar llamadas prematuras antes de setContentView
        habitacionId = intent.getStringExtra("HABITACION_ID")
        if (habitacionId == null) {
            Toast.makeText(this, "Error: No se especificó la habitación", Toast.LENGTH_LONG).show()
            finish() // Termina la actividad si no hay ID de habitación
            return
        }

        clienteId = intent.getStringExtra("CLIENTE_ID")

        initViews()
        setupListeners()
    }

    private fun initViews() {
        etNombreCliente = findViewById(R.id.etNombreCliente)
        etDocumentoCliente = findViewById(R.id.etDocumentoCliente)
        etFechaCheckIn = findViewById(R.id.etFechaCheckIn)
        etFechaCheckOut = findViewById(R.id.etFechaCheckOut)
        etNumeroTelefono = findViewById(R.id.etNumeroTelefono)

        clienteId?.let { cargarDatosCliente(it) }
    }

    private fun setupListeners() {
        val btnGuardarCliente = findViewById<Button>(R.id.btnGuardarCliente)
        btnGuardarCliente.setOnClickListener { guardarCliente() }
    }

    private fun cargarDatosCliente(id: String) {
        db.collection("clientes").document(id).get()
            .addOnSuccessListener { documentSnapshot ->
                val cliente = documentSnapshot.toObject(ClienteHotel::class.java)
                cliente?.apply {
                    etNombreCliente.setText(nombre)
                    etDocumentoCliente.setText(documento)
                    etFechaCheckIn.setText(fechaCheckIn)
                    etFechaCheckOut.setText(fechaCheckOut)
                    etNumeroTelefono.setText(numeroTelefono)
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al cargar los datos del cliente: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun guardarCliente() {
        val cliente = ClienteHotel(
            nombre = etNombreCliente.text.toString(),
            documento = etDocumentoCliente.text.toString(),
            fechaCheckIn = etFechaCheckIn.text.toString(),
            fechaCheckOut = etFechaCheckOut.text.toString(),
            numeroTelefono = etNumeroTelefono.text.toString(),
            habitacionId = this.habitacionId // Usa this.habitacionId para referenciar el campo de la clase
        )

        val collectionRef = db.collection("clientes")
        if (clienteId == null) {
            collectionRef.add(cliente)
                .addOnSuccessListener {
                    Toast.makeText(this, "Cliente guardado con éxito", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error al guardar el cliente: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            clienteId?.let {
                collectionRef.document(it).set(cliente)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Cliente actualizado con éxito", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Error al actualizar el cliente: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}
