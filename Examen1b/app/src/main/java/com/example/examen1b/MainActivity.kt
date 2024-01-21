package com.example.examen1b

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerViewHabitaciones: RecyclerView
    private lateinit var btnAgregarHabitacion: Button
    private lateinit var btnAgregarCliente: Button // Añadir botón para agregar clientes
    private lateinit var habitacionDao: HabitacionDao
    private var habitacionSeleccionadaId: Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerViewHabitaciones = findViewById(R.id.recyclerViewHabitaciones)
        btnAgregarHabitacion = findViewById(R.id.btnAgregarHabitacion)
        btnAgregarCliente = findViewById(R.id.btnAgregarCliente) // Inicializar botón de agregar cliente
        habitacionDao = HabitacionDao(DatabaseHelper(this))

        btnAgregarHabitacion.setOnClickListener {
            val intent = Intent(this, HabitacionFormActivity::class.java)
            startActivity(intent)
        }

        btnAgregarCliente.setOnClickListener {
            if (habitacionSeleccionadaId != -1) {
                val intent = Intent(this, ClientesActivity::class.java)
                intent.putExtra("HABITACION_ID", habitacionSeleccionadaId)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Por favor, selecciona una habitación primero", Toast.LENGTH_SHORT).show()
            }
        }

        cargarHabitaciones()
    }

    private fun cargarHabitaciones() {
        val habitaciones = habitacionDao.obtenerHabitaciones()
        recyclerViewHabitaciones.layoutManager = LinearLayoutManager(this)
        recyclerViewHabitaciones.adapter = HabitacionAdapter(
            habitaciones,
            onItemClicked = { idHabitacion ->
                // Iniciar ClientesActivity con el ID de la habitación
                val intent = Intent(this, ClientesActivity::class.java)
                intent.putExtra("HABITACION_ID", idHabitacion)
                startActivity(intent)
            },
            onItemEditClicked = { idHabitacion ->
                habitacionSeleccionadaId = idHabitacion
                // Iniciar HabitacionFormActivity para editar la habitación
                val intent = Intent(this, HabitacionFormActivity::class.java)
                intent.putExtra("HABITACION_ID", idHabitacion)
                startActivity(intent)
            },
            onItemDeleteClicked = { idHabitacion ->
                eliminarHabitacion(idHabitacion)
            }
        )
    }


    private fun eliminarHabitacion(idHabitacion: Int) {
        // Código para eliminar la habitación
        val resultado = habitacionDao.eliminarHabitacion(idHabitacion)
        if (resultado > 0) {
            Toast.makeText(this, "Habitación eliminada", Toast.LENGTH_SHORT).show()
            cargarHabitaciones() // Recargar la lista
        } else {
            Toast.makeText(this, "Error al eliminar la habitación", Toast.LENGTH_SHORT).show()
        }
    }



    override fun onResume() {
        super.onResume()
        cargarHabitaciones() // Recargar la lista cuando regresas a esta actividad
    }

}
