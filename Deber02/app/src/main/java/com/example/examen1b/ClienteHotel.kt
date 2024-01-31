package com.example.examen1b



data class ClienteHotel(
    val id: Int,
    val nombre: String,
    val documento: String,
    val fechaCheckIn: String,
    val fechaCheckOut: String,
    val numeroTelefono: String,
    val habitacionId: Int
)
