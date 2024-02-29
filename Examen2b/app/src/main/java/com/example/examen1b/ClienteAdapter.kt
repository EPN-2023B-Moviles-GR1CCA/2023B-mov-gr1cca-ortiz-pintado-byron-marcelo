package com.example.examen1b

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ClienteAdapter(
    private val clientes: List<ClienteHotel>,
    private val onItemClicked: (ClienteHotel) -> Unit, // Parámetro lambda para manejar clics en el cliente
    private val onItemEditClicked: (ClienteHotel) -> Unit, // Para manejar clics en editar
    private val onEliminarClicked: (ClienteHotel) -> Unit // Parámetro lambda para manejar clics en eliminar
) : RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder>() {

    class ClienteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nombreTextView: TextView = view.findViewById(R.id.tvNombreCliente)
        private val editarButton: Button = view.findViewById(R.id.btnEditarCliente)
        private val eliminarButton: Button = view.findViewById(R.id.btnEliminarCliente)

        fun bind(cliente: ClienteHotel, onItemClicked: (ClienteHotel) -> Unit, onItemEditClicked: (ClienteHotel) -> Unit, onEliminarClicked: (ClienteHotel) -> Unit) {
            nombreTextView.text = cliente.nombre
            itemView.setOnClickListener { onItemClicked(cliente) }
            editarButton.setOnClickListener { onItemEditClicked(cliente) }
            eliminarButton.setOnClickListener { onEliminarClicked(cliente) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClienteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cliente_list_item, parent, false)
        return ClienteViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClienteViewHolder, position: Int) {
        val cliente = clientes[position]
        holder.bind(cliente, onItemClicked, onItemEditClicked, onEliminarClicked)
    }

    override fun getItemCount() = clientes.size
}
