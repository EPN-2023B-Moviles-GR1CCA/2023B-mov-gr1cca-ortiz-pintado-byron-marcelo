package com.example.examen1b

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetalleHabitacionActivity : AppCompatActivity() {

    private lateinit var tvNombreHabitacion: TextView
    private lateinit var tvCapacidadHabitacion: TextView
    private lateinit var tvPrecioHabitacion: TextView
    private lateinit var tvDisponibilidadHabitacion: TextView
    private lateinit var habitacionDao: HabitacionDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_habitacion)

        tvNombreHabitacion = findViewById(R.id.tvNombreHabitacion)
        tvCapacidadHabitacion = findViewById(R.id.tvCapacidadHabitacion)
        tvPrecioHabitacion = findViewById(R.id.tvPrecioHabitacion)
        tvDisponibilidadHabitacion = findViewById(R.id.tvDisponibilidadHabitacion)
        habitacionDao = HabitacionDao(DatabaseHelper(this))

        val habitacionId = intent.getIntExtra("HABITACION_ID", -1)
        if (habitacionId != -1) {
            cargarDatosHabitacion(habitacionId)
        }
    }

    private fun cargarDatosHabitacion(id: Int) {
        val habitacion = habitacionDao.obtenerHabitacionPorId(id)
        habitacion?.let {
            tvNombreHabitacion.text = it.nombre
            tvCapacidadHabitacion.text = getString(R.string.capacidad_format, it.capacidad)
            tvPrecioHabitacion.text = getString(R.string.precio_noche_format, it.precio)
            tvDisponibilidadHabitacion.text = if (it.disponible) {
                getString(R.string.disponible)
            } else {
                getString(R.string.no_disponible)
            }
        }
    }
}
