package com.example.examen1b

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.SwitchCompat
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class HabitacionFormActivity : AppCompatActivity() {

    // Al comienzo de la clase
    private var habitacionId: Int = -1
    private lateinit var habitacionDao: HabitacionDao
    private lateinit var etNombreHabitacion: EditText
    private lateinit var etCapacidadHabitacion: EditText
    private lateinit var etPrecioHabitacion: EditText
    private lateinit var switchDisponible: SwitchCompat
    private lateinit var btnGuardarHabitacion: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habitacion_form)

        etNombreHabitacion = findViewById(R.id.etNombreHabitacion)
        etCapacidadHabitacion = findViewById(R.id.etCapacidadHabitacion)
        etPrecioHabitacion = findViewById(R.id.etPrecioHabitacion)
        switchDisponible = findViewById(R.id.switchDisponible)
        btnGuardarHabitacion = findViewById(R.id.btnGuardarHabitacion)
        habitacionDao = HabitacionDao(DatabaseHelper(this)) // Inicializa aquí

        btnGuardarHabitacion = findViewById(R.id.btnGuardarHabitacion)

        btnGuardarHabitacion.text = getString(R.string.guardar)

        habitacionId = intent.getIntExtra("HABITACION_ID", -1)

        btnGuardarHabitacion.setOnClickListener {
            guardarHabitacion()
        }

        if (habitacionId != -1) {
            cargarDatosHabitacion(habitacionId)
        }


    }


    private fun guardarHabitacion() {
        val nombre = etNombreHabitacion.text.toString()
        val capacidad = etCapacidadHabitacion.text.toString().toIntOrNull() ?: 0
        val precio = etPrecioHabitacion.text.toString().toDoubleOrNull() ?: 0.0
        val disponible = switchDisponible.isChecked

        val habitacion = Habitacion(
            id = habitacionId, // Usar habitacionId. Si es -1, SQLite generará un nuevo ID
            nombre = nombre,
            capacidad = capacidad,
            precio = precio,
            disponible = disponible
        )

        val resultado = if (habitacionId == -1) {
            habitacionDao.insertarHabitacion(habitacion)
        } else {
            habitacionDao.actualizarHabitacion(habitacion).toLong()
        }

        if (resultado != -1L) {
            Toast.makeText(this, "Habitación guardada con éxito", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Error al guardar la habitación", Toast.LENGTH_SHORT).show()
        }
    }


    private fun cargarDatosHabitacion(id: Int) {
        val habitacion = habitacionDao.obtenerHabitacionPorId(id)
        habitacion?.let {
            etNombreHabitacion.setText(it.nombre)
            etCapacidadHabitacion.setText(it.capacidad.toString())
            etPrecioHabitacion.setText(it.precio.toString())
            switchDisponible.isChecked = it.disponible

            // Cambiar el texto del botón
            btnGuardarHabitacion.text = getString(R.string.actualizar_habitacion)
        }
    }

}
