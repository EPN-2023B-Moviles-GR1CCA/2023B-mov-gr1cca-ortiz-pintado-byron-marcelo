package com.example.examen1b

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.SwitchCompat
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class HabitacionFormActivity : AppCompatActivity() {

    private lateinit var etNombreHabitacion: EditText
    private lateinit var etCapacidadHabitacion: EditText
    private lateinit var etPrecioHabitacion: EditText
    private lateinit var switchDisponible: SwitchCompat
    private lateinit var btnGuardarHabitacion: Button
    private var habitacionId: String? = null
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habitacion_form)

        // Inicializar vistas
        etNombreHabitacion = findViewById(R.id.etNombreHabitacion)
        etCapacidadHabitacion = findViewById(R.id.etCapacidadHabitacion)
        etPrecioHabitacion = findViewById(R.id.etPrecioHabitacion)
        switchDisponible = findViewById(R.id.switchDisponible)
        btnGuardarHabitacion = findViewById(R.id.btnGuardarHabitacion)

        // Obtener el ID de habitación si se está editando una habitación existente
        habitacionId = intent.getStringExtra("HABITACION_ID")

        // Configurar el botón de guardado o actualización
        btnGuardarHabitacion.setOnClickListener {
            guardarOActualizarHabitacion()
        }

        // Cargar los datos de la habitación si es necesario
        habitacionId?.let {
            cargarDatosHabitacion(it)
        } ?: run {
            btnGuardarHabitacion.text = getString(R.string.guardar)
        }
    }

    private fun guardarOActualizarHabitacion() {
        val nombre = etNombreHabitacion.text.toString()
        val capacidad = etCapacidadHabitacion.text.toString().toIntOrNull()
        val precio = etPrecioHabitacion.text.toString().toDoubleOrNull()
        val disponible = switchDisponible.isChecked

        val habitacion = Habitacion(
            nombre = nombre,
            capacidad = capacidad,
            precio = precio,
            disponible = disponible
        )

        if (habitacionId == null) {
            db.collection("habitaciones").add(habitacion)
                .addOnSuccessListener {
                    Toast.makeText(this, "Habitación guardada con éxito", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al guardar la habitación", Toast.LENGTH_SHORT).show()
                }
        } else {
            db.collection("habitaciones").document(habitacionId!!)
                .set(habitacion)
                .addOnSuccessListener {
                    Toast.makeText(this, "Habitación actualizada con éxito", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al actualizar la habitación", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun cargarDatosHabitacion(id: String) {
        db.collection("habitaciones").document(id).get()
            .addOnSuccessListener { documentSnapshot ->
                val habitacion = documentSnapshot.toObject(Habitacion::class.java)
                habitacion?.let {
                    etNombreHabitacion.setText(it.nombre)
                    etCapacidadHabitacion.setText(it.capacidad?.toString())
                    etPrecioHabitacion.setText(it.precio?.toString())
                    switchDisponible.isChecked = it.disponible ?: false

                    btnGuardarHabitacion.text = getString(R.string.actualizar_habitacion)
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al cargar la habitación", Toast.LENGTH_SHORT).show()
            }
    }
}
