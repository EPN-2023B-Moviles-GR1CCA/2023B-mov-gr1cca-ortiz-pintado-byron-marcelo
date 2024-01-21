import java.io.File
import java.io.IOException

data class Habitacion(
    val nombre: String,
    var capacidad: Int,
    var precioPorNoche: Double,
    var disponible: Boolean,
    val numeroHabitacion: Int
) {
    val clientes = mutableListOf<ClienteHotel>()

    fun agregarCliente(cliente: ClienteHotel) {
        if (disponible && clientes.size < capacidad) {
            clientes.add(cliente)
            println("Cliente agregado correctamente a la habitación $nombre.")
        } else {
            println("No se puede agregar el cliente. La habitación $nombre no está disponible o ya alcanzó su capacidad máxima.")
        }
    }

    fun eliminarCliente(documentoIdentidad: String): Boolean {
        val clienteAEliminar = clientes.find { it.documentoIdentidad == documentoIdentidad }
        return if (clienteAEliminar != null) {
            clientes.remove(clienteAEliminar)
            println("Cliente eliminado correctamente de la habitación $nombre.")
            true
        } else {
            false
        }
    }

    fun mostrarInfo() {
        println("Habitación $nombre - Capacidad: $capacidad, Precio por Noche: $precioPorNoche, Disponible: $disponible, Número de Habitación: $numeroHabitacion")
        clientes.forEach { it.mostrarInfo() }
    }

    fun guardarClientesEnArchivo() {
        val archivo = File("clientes_${nombre}.txt")
        archivo.writeText("") // Limpiar el archivo
        clientes.forEach { cliente ->
            archivo.appendText("${cliente.documentoIdentidad},${cliente.nombre},${cliente.fechaCheckIn},${cliente.fechaCheckOut},${cliente.numeroTelefono}\n")
        }
    }
}