import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.io.IOException

fun main() {
    val habitaciones = mutableListOf<Habitacion>()
    var continuar = true

    while (continuar) {
        println("\n--- Menú Principal ---")
        println("1. Agregar habitación")
        println("2. Agregar cliente a habitación")
        println("3. Mostrar todas las habitaciones")
        println("4. Guardar información de clientes en archivo")
        println("5. Mostrar todos los clientes")
        println("6. Actualizar cliente")
        println("7. Eliminar cliente")
        println("8. Salir")

        print("Seleccione una opción: ")
        when (readLine()?.toIntOrNull()) {
            1 -> agregarHabitacion(habitaciones)
            2 -> agregarClienteAHabitacion(habitaciones)
            3 -> mostrarHabitaciones(habitaciones)
            4 -> guardarInformacionEnArchivo(habitaciones)
            5 -> mostrarTodosLosClientes(habitaciones)
            6 -> actualizarCliente(habitaciones)
            7 -> eliminarCliente(habitaciones)
            8 -> {
                println("Saliendo del programa...")
                continuar = false
            }
            else -> println("Opción no válida.")
        }
    }
}

fun agregarHabitacion(habitaciones: MutableList<Habitacion>) {
    println("Agregar nueva habitación:")
    print("Nombre de la habitación: ")
    val nombre = readLine()!!
    print("Capacidad de la habitación: ")
    val capacidad = readLine()!!.toInt()
    print("Precio por noche: ")
    val precioPorNoche = readLine()!!.toDouble()
    print("Número de habitación: ")
    val numeroHabitacion = readLine()!!.toInt()
    val habitacion = Habitacion(nombre, capacidad, precioPorNoche, true, numeroHabitacion)
    habitaciones.add(habitacion)
    println("Habitación agregada con éxito.")
}

fun agregarClienteAHabitacion(habitaciones: MutableList<Habitacion>) {
    println("Agregar cliente a habitación:")
    print("Número de habitación: ")
    val numeroHabitacion = readLine()!!.toInt()
    val habitacion = habitaciones.find { it.numeroHabitacion == numeroHabitacion }
    if (habitacion != null && habitacion.disponible) {
        print("Nombre del cliente: ")
        val nombreCliente = readLine()!!
        print("Documento de identidad: ")
        val documentoIdentidad = readLine()!!
        print("Fecha de check-in (yyyy-MM-dd): ")
        val fechaCheckIn = LocalDate.parse(readLine(), DateTimeFormatter.ISO_LOCAL_DATE)
        print("Fecha de check-out (yyyy-MM-dd): ")
        val fechaCheckOut = LocalDate.parse(readLine(), DateTimeFormatter.ISO_LOCAL_DATE)
        print("Número de teléfono: ")
        val numeroTelefono = readLine()!!
        val cliente = ClienteHotel(nombreCliente, documentoIdentidad, fechaCheckIn, fechaCheckOut, numeroTelefono)
        habitacion.agregarCliente(cliente)
    } else {
        println("Habitación no encontrada o no disponible.")
    }
}

fun mostrarHabitaciones(habitaciones: MutableList<Habitacion>) {
    habitaciones.forEach { it.mostrarInfo() }
}

fun mostrarTodosLosClientes(habitaciones: MutableList<Habitacion>) {
    habitaciones.flatMap { it.clientes }.forEach { it.mostrarInfo() }
}

fun actualizarCliente(habitaciones: MutableList<Habitacion>) {
    println("Ingrese el número de identificación del cliente que desea actualizar:")
    val documento = readLine()!!
    val cliente = habitaciones.flatMap { it.clientes }.find { it.documentoIdentidad == documento }
    if (cliente != null) {
        println("Ingrese los nuevos datos del cliente.")
        // Aquí se podrían solicitar y actualizar más datos si fuera necesario.
        println("Nuevo nombre del cliente:")
        cliente.nombre = readLine()!!
        println("Nueva fecha de check-in (yyyy-MM-dd):")
        cliente.fechaCheckIn = LocalDate.parse(readLine())
        println("Nueva fecha de check-out (yyyy-MM-dd):")
        cliente.fechaCheckOut = LocalDate.parse(readLine())
        println("Cliente actualizado correctamente.")
    } else {
        println("Cliente no encontrado.")
    }
}

fun eliminarCliente(habitaciones: MutableList<Habitacion>) {
    println("Ingrese el número de identificación del cliente que desea eliminar:")
    val documento = readLine()!!
    habitaciones.forEach { habitacion ->
        habitacion.eliminarCliente(documento)
    }
    println("Si el cliente existía, ha sido eliminado.")
}

fun guardarInformacionEnArchivo(habitaciones: MutableList<Habitacion>) {
    habitaciones.forEach {
        try {
            it.guardarClientesEnArchivo()
            println("Información de clientes guardada para la habitación ${it.nombre}.")
        } catch (e: IOException) {
            println("Error al guardar la información de clientes para la habitación ${it.nombre}: ${e.message}")
        }
    }
}
