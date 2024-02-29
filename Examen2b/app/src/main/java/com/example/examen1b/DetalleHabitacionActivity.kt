package com.example.examen1b

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class DetalleHabitacionActivity : AppCompatActivity() {

    private lateinit var tvNombreHabitacion: TextView
    private lateinit var tvCapacidadHabitacion: TextView
    private lateinit var tvPrecioHabitacion: TextView
    private lateinit var tvDisponibilidadHabitacion: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_habitacion)

        tvNombreHabitacion = findViewById(R.id.tvNombreHabitacion)
        tvCapacidadHabitacion = findViewById(R.id.tvCapacidadHabitacion)
        tvPrecioHabitacion = findViewById(R.id.tvPrecioHabitacion)
        tvDisponibilidadHabitacion = findViewById(R.id.tvDisponibilidadHabitacion)

        // Cambiado a String para coincidir con los ID de Firestore
        val habitacionId = intent.getStringExtra("HABITACION_ID")
        if (habitacionId != null) {
            cargarDatosHabitacion(habitacionId)
        } else {
            // Manejar el caso en que no se pase un ID de habitación
            finish() // O mostrar un mensaje de error
        }
    }

    private fun cargarDatosHabitacion(id: String) {
        FirebaseFirestore.getInstance().collection("habitaciones").document(id).get()
            .addOnSuccessListener { documentSnapshot ->
                val habitacion = documentSnapshot.toObject(Habitacion::class.java)
                habitacion?.let {
                    tvNombreHabitacion.text = it.nombre
                    tvCapacidadHabitacion.text = getString(R.string.capacidad_format, it.capacidad ?: 0)
                    tvPrecioHabitacion.text = getString(R.string.precio_noche_format, it.precio ?: 0.0)
                    tvDisponibilidadHabitacion.text = if (it.disponible == true) {
                        getString(R.string.disponible)
                    } else {
                        getString(R.string.no_disponible)
                    }
                }
            }
            .addOnFailureListener { e ->
                // Manejar el error
                e.printStackTrace()
            }
    }
}
