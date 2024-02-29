package com.example.examen1b

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class HabitacionAdapter(
    private val habitaciones: List<Habitacion>,
    private val onItemClicked: (String) -> Unit,
    private val onItemEditClicked: (String) -> Unit,
    private val onItemDeleteClicked: (String) -> Unit
) : RecyclerView.Adapter<HabitacionAdapter.HabitacionViewHolder>() {

    class HabitacionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nombreTextView: TextView = view.findViewById(R.id.nombreTextView)

        fun bind(habitacion: Habitacion, onItemClicked: (String) -> Unit, onItemEditClicked: (String) -> Unit, onItemDeleteClicked: (String) -> Unit) {
            nombreTextView.text = habitacion.nombre
            habitacion.id?.let { id ->
                itemView.setOnClickListener { onItemClicked(id) }
                itemView.findViewById<Button>(R.id.btnEditarHabitacion).setOnClickListener { onItemEditClicked(id) }
                itemView.findViewById<Button>(R.id.btnEliminarHabitacion).setOnClickListener { onItemDeleteClicked(id) }
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

