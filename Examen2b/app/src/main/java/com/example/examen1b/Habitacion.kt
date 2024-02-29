package com.example.examen1b

import com.google.firebase.firestore.DocumentId

data class Habitacion(
    @DocumentId
    val id: String? = null, // Firestore genera este ID automáticamente
    val nombre: String? = null,
    val capacidad: Int? = null,
    val precio: Double? = null,
    val disponible: Boolean? = null
)
