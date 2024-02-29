package com.example.examen1b

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class FirestoreService {
    private val db = FirebaseFirestore.getInstance()

    // Agregar habitación
    fun agregarHabitacion(habitacion: Habitacion, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("habitaciones")
            .add(habitacion)
            .addOnSuccessListener {
                Log.d("FirestoreService", "Habitación agregada con éxito")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreService", "Error al agregar habitación", e)
                onFailure(e)
            }
    }

    // Obtener todas las habitaciones
    fun obtenerHabitaciones(onResult: (List<Habitacion>) -> Unit) {
        db.collection("habitaciones")
            .get()
            .addOnSuccessListener { result ->
                val habitaciones = result.documents.mapNotNull { it.toObject(Habitacion::class.java) }
                onResult(habitaciones)
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreService", "Error al obtener habitaciones", e)
            }
    }

    // Actualizar una habitación específica
    fun actualizarHabitacion(habitacionId: String, habitacion: Habitacion, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("habitaciones").document(habitacionId)
            .set(habitacion)
            .addOnSuccessListener {
                Log.d("FirestoreService", "Habitación actualizada con éxito")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreService", "Error al actualizar habitación", e)
                onFailure(e)
            }
    }

    // Eliminar una habitación específica
    fun eliminarHabitacion(habitacionId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("habitaciones").document(habitacionId)
            .delete()
            .addOnSuccessListener {
                Log.d("FirestoreService", "Habitación eliminada con éxito")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreService", "Error al eliminar habitación", e)
                onFailure(e)
            }
    }

    // Agregar un cliente
    fun agregarCliente(cliente: ClienteHotel, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("clientes")
            .add(cliente)
            .addOnSuccessListener {
                Log.d("FirestoreService", "Cliente agregado con éxito")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreService", "Error al agregar cliente", e)
                onFailure(e)
            }
    }

    // Obtener todos los clientes
    fun obtenerClientes(onResult: (List<ClienteHotel>) -> Unit) {
        db.collection("clientes")
            .get()
            .addOnSuccessListener { result ->
                val clientes = result.documents.mapNotNull { it.toObject(ClienteHotel::class.java) }
                onResult(clientes)
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreService", "Error al obtener clientes", e)
            }
    }

    // Actualizar un cliente específico
    fun actualizarCliente(clienteId: String, cliente: ClienteHotel, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("clientes").document(clienteId)
            .set(cliente)
            .addOnSuccessListener {
                Log.d("FirestoreService", "Cliente actualizado con éxito")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreService", "Error al actualizar cliente", e)
                onFailure(e)
            }
    }

    // Eliminar un cliente específico
    fun eliminarCliente(clienteId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("clientes").document(clienteId)
            .delete()
            .addOnSuccessListener {
                Log.d("FirestoreService", "Cliente eliminado con éxito")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreService", "Error al eliminar cliente", e)
                onFailure(e)
            }
    }
}
