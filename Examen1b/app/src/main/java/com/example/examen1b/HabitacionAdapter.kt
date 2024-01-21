package com.example.examen1b

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class HabitacionAdapter(
    private val habitaciones: List<Habitacion>,
    private val onItemClicked: (Int) -> Unit,
    private val onItemEditClicked: (Int) -> Unit,
    private val onItemDeleteClicked: (Int) -> Unit
) : RecyclerView.Adapter<HabitacionAdapter.HabitacionViewHolder>() {

    class HabitacionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nombreTextView: TextView = view.findViewById(R.id.nombreTextView)
        private var currentHabitacion: Habitacion? = null

        fun bind(habitacion: Habitacion, onItemClicked: (Int) -> Unit, onItemEditClicked: (Int) -> Unit, onItemDeleteClicked: (Int) -> Unit) {
            currentHabitacion = habitacion
            nombreTextView.text = habitacion.nombre

            itemView.setOnClickListener {
                currentHabitacion?.id?.let(onItemClicked)
            }

            // Botón o vista para editar
            itemView.findViewById<Button>(R.id.btnEditarHabitacion).setOnClickListener {
                currentHabitacion?.id?.let(onItemEditClicked)
            }

            // Botón o vista para eliminar
            itemView.findViewById<Button>(R.id.btnEliminarHabitacion).setOnClickListener {
                currentHabitacion?.id?.let(onItemDeleteClicked)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitacionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.habitacion_list_item, parent, false)
        return HabitacionViewHolder(view)
    }

    override fun onBindViewHolder(holder: HabitacionViewHolder, position: Int) {
        val habitacion = habitaciones[position]
        holder.bind(habitacion, onItemClicked, onItemEditClicked, onItemDeleteClicked)
    }

    override fun getItemCount() = habitaciones.size
}
