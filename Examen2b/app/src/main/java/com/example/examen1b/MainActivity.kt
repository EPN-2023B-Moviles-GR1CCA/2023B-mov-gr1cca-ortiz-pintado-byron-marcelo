package com.example.examen1b

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerViewHabitaciones: RecyclerView
    private lateinit var btnAgregarHabitacion: Button
    private var habitacionSeleccionadaId: String? = null
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerViewHabitaciones = findViewById(R.id.recyclerViewHabitaciones)
        btnAgregarHabitacion = findViewById(R.id.btnAgregarHabitacion)

        btnAgregarHabitacion.setOnClickListener {
            val intent = Intent(this, HabitacionFormActivity::class.java)
            startActivity(intent)
        }

        cargarHabitaciones()
    }

    private fun cargarHabitaciones() {
        db.collection("habitaciones").get()
            .addOnSuccessListener { documents ->
                val habitaciones = documents.toObjects(Habitacion::class.java)
                recyclerViewHabitaciones.layoutManager = LinearLayoutManager(this)
                recyclerViewHabitaciones.adapter = HabitacionAdapter(habitaciones, onItemClicked = { idHabitacion ->
                    // Cuando se selecciona una habitación, navega a la actividad de clientes con el ID de habitación
                    habitacionSeleccionadaId = idHabitacion
                    val intent = Intent(this, ClientesActivity::class.java).apply {
                        putExtra("HABITACION_ID", idHabitacion)
                    }
                    startActivity(intent)
                }, onItemEditClicked = { idHabitacion ->
                    // Aquí puedes implementar la lógica para editar una habitación si es necesario
                }, onItemDeleteClicked = { idHabitacion ->
                    eliminarHabitacion(idHabitacion)
                })
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al cargar las habitaciones", Toast.LENGTH_SHORT).show()
            }
    }

    private fun eliminarHabitacion(idHabitacion: String) {
        db.collection("habitaciones").document(idHabitacion).delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Habitación eliminada", Toast.LENGTH_SHORT).show()
                cargarHabitaciones() // Recarga la lista después de eliminar
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al eliminar la habitación", Toast.LENGTH_SHORT).show()
            }
    }
}
