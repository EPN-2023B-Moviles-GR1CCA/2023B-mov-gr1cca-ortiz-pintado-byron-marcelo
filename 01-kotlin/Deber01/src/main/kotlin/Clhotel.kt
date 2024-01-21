import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class ClienteHotel(
    var nombre: String,
    val documentoIdentidad: String, // Este se mantiene inmutable porque es un identificador único.
    var fechaCheckIn: LocalDate,
    var fechaCheckOut: LocalDate,
    var numeroTelefono: String
) {
    fun mostrarInfo() {
        println("Cliente: $nombre - Documento: $documentoIdentidad, Check-in: $fechaCheckIn, Check-out: $fechaCheckOut, Teléfono: $numeroTelefono")
    }
}
