package com.example.examen1b

import android.content.ContentValues

class ClienteHotelDao(private val dbHelper: DatabaseHelper) {

    fun insertarCliente(cliente: ClienteHotel): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(ClienteHotelContract.ClienteEntry.COLUMN_NAME_NOMBRE, cliente.nombre)
            put(ClienteHotelContract.ClienteEntry.COLUMN_NAME_DOCUMENTO, cliente.documento)
            put(ClienteHotelContract.ClienteEntry.COLUMN_NAME_FECHA_CHECK_IN, cliente.fechaCheckIn)
            put(ClienteHotelContract.ClienteEntry.COLUMN_NAME_FECHA_CHECK_OUT, cliente.fechaCheckOut)
            put(ClienteHotelContract.ClienteEntry.COLUMN_NAME_NUMERO_TELEFONO, cliente.numeroTelefono)
            put(ClienteHotelContract.ClienteEntry.COLUMN_NAME_HABITACION_ID, cliente.habitacionId)
        }
        return db.insert(ClienteHotelContract.ClienteEntry.TABLE_NAME, null, values)
    }

    fun obtenerClientes(): List<ClienteHotel> {
        val db = dbHelper.readableDatabase
        val projection = arrayOf(
            ClienteHotelContract.ClienteEntry.COLUMN_NAME_ID,
            ClienteHotelContract.ClienteEntry.COLUMN_NAME_NOMBRE,
            ClienteHotelContract.ClienteEntry.COLUMN_NAME_DOCUMENTO,
            ClienteHotelContract.ClienteEntry.COLUMN_NAME_FECHA_CHECK_IN,
            ClienteHotelContract.ClienteEntry.COLUMN_NAME_FECHA_CHECK_OUT,
            ClienteHotelContract.ClienteEntry.COLUMN_NAME_NUMERO_TELEFONO,
            ClienteHotelContract.ClienteEntry.COLUMN_NAME_HABITACION_ID
        )

        val cursor = db.query(
            ClienteHotelContract.ClienteEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )

        val clientes = mutableListOf<ClienteHotel>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(ClienteHotelContract.ClienteEntry.COLUMN_NAME_ID))
                val nombre = getString(getColumnIndexOrThrow(ClienteHotelContract.ClienteEntry.COLUMN_NAME_NOMBRE))
                val documento = getString(getColumnIndexOrThrow(ClienteHotelContract.ClienteEntry.COLUMN_NAME_DOCUMENTO))
                val fechaCheckIn = getString(getColumnIndexOrThrow(ClienteHotelContract.ClienteEntry.COLUMN_NAME_FECHA_CHECK_IN))
                val fechaCheckOut = getString(getColumnIndexOrThrow(ClienteHotelContract.ClienteEntry.COLUMN_NAME_FECHA_CHECK_OUT))
                val numeroTelefono = getString(getColumnIndexOrThrow(ClienteHotelContract.ClienteEntry.COLUMN_NAME_NUMERO_TELEFONO))
                val habitacionId = getInt(getColumnIndexOrThrow(ClienteHotelContract.ClienteEntry.COLUMN_NAME_HABITACION_ID))

                clientes.add(ClienteHotel(id, nombre, documento, fechaCheckIn, fechaCheckOut, numeroTelefono, habitacionId))
            }
        }
        cursor.close()
        return clientes
    }

    fun actualizarCliente(cliente: ClienteHotel): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(ClienteHotelContract.ClienteEntry.COLUMN_NAME_NOMBRE, cliente.nombre)
            put(ClienteHotelContract.ClienteEntry.COLUMN_NAME_DOCUMENTO, cliente.documento)
            put(ClienteHotelContract.ClienteEntry.COLUMN_NAME_FECHA_CHECK_IN, cliente.fechaCheckIn)
            put(ClienteHotelContract.ClienteEntry.COLUMN_NAME_FECHA_CHECK_OUT, cliente.fechaCheckOut)
            put(ClienteHotelContract.ClienteEntry.COLUMN_NAME_NUMERO_TELEFONO, cliente.numeroTelefono)
            put(ClienteHotelContract.ClienteEntry.COLUMN_NAME_HABITACION_ID, cliente.habitacionId)
        }

        val selection = "${ClienteHotelContract.ClienteEntry.COLUMN_NAME_ID} LIKE ?"
        val selectionArgs = arrayOf(cliente.id.toString())
        return db.update(ClienteHotelContract.ClienteEntry.TABLE_NAME, values, selection, selectionArgs)
    }

    fun eliminarCliente(clienteId: Int): Int {
        val db = dbHelper.writableDatabase
        val selection = "${ClienteHotelContract.ClienteEntry.COLUMN_NAME_ID} LIKE ?"
        val selectionArgs = arrayOf(clienteId.toString())
        return db.delete(ClienteHotelContract.ClienteEntry.TABLE_NAME, selection, selectionArgs)
    }

    fun obtenerClientePorId(clienteId: Int): ClienteHotel? {
        val db = dbHelper.readableDatabase
        val projection = arrayOf(
            ClienteHotelContract.ClienteEntry.COLUMN_NAME_ID,
            ClienteHotelContract.ClienteEntry.COLUMN_NAME_NOMBRE,
            ClienteHotelContract.ClienteEntry.COLUMN_NAME_DOCUMENTO,
            ClienteHotelContract.ClienteEntry.COLUMN_NAME_FECHA_CHECK_IN,
            ClienteHotelContract.ClienteEntry.COLUMN_NAME_FECHA_CHECK_OUT,
            ClienteHotelContract.ClienteEntry.COLUMN_NAME_NUMERO_TELEFONO,
            ClienteHotelContract.ClienteEntry.COLUMN_NAME_HABITACION_ID
        )
        val selection = "${ClienteHotelContract.ClienteEntry.COLUMN_NAME_ID} = ?"
        val selectionArgs = arrayOf(clienteId.toString())
        val cursor = db.query(
            ClienteHotelContract.ClienteEntry.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        var cliente: ClienteHotel? = null
        with(cursor) {
            if (moveToFirst()) {
                val id = getInt(getColumnIndexOrThrow(ClienteHotelContract.ClienteEntry.COLUMN_NAME_ID))
                val nombre = getString(getColumnIndexOrThrow(ClienteHotelContract.ClienteEntry.COLUMN_NAME_NOMBRE))
                val documento = getString(getColumnIndexOrThrow(ClienteHotelContract.ClienteEntry.COLUMN_NAME_DOCUMENTO))
                val fechaCheckIn = getString(getColumnIndexOrThrow(ClienteHotelContract.ClienteEntry.COLUMN_NAME_FECHA_CHECK_IN))
                val fechaCheckOut = getString(getColumnIndexOrThrow(ClienteHotelContract.ClienteEntry.COLUMN_NAME_FECHA_CHECK_OUT))
                val numeroTelefono = getString(getColumnIndexOrThrow(ClienteHotelContract.ClienteEntry.COLUMN_NAME_NUMERO_TELEFONO))
                val habitacionId = getInt(getColumnIndexOrThrow(ClienteHotelContract.ClienteEntry.COLUMN_NAME_HABITACION_ID))

                cliente = ClienteHotel(
                    id = id,
                    nombre = nombre,
                    documento = documento,
                    fechaCheckIn = fechaCheckIn,
                    fechaCheckOut = fechaCheckOut,
                    numeroTelefono = numeroTelefono,
                    habitacionId = habitacionId
                )
            }
        }
        cursor.close()
        return cliente
    }

    fun obtenerClientesPorHabitacion(habitacionId: Int): List<ClienteHotel> {
        val db = dbHelper.readableDatabase
        val projection = arrayOf(
            ClienteHotelContract.ClienteEntry.COLUMN_NAME_ID,
            ClienteHotelContract.ClienteEntry.COLUMN_NAME_NOMBRE,
            ClienteHotelContract.ClienteEntry.COLUMN_NAME_DOCUMENTO,
            ClienteHotelContract.ClienteEntry.COLUMN_NAME_FECHA_CHECK_IN,
            ClienteHotelContract.ClienteEntry.COLUMN_NAME_FECHA_CHECK_OUT,
            ClienteHotelContract.ClienteEntry.COLUMN_NAME_NUMERO_TELEFONO,
            ClienteHotelContract.ClienteEntry.COLUMN_NAME_HABITACION_ID
        )
        val selection = "${ClienteHotelContract.ClienteEntry.COLUMN_NAME_HABITACION_ID} = ?"
        val selectionArgs = arrayOf(habitacionId.toString())

        val cursor = db.query(
            ClienteHotelContract.ClienteEntry.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        val clientes = mutableListOf<ClienteHotel>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(ClienteHotelContract.ClienteEntry.COLUMN_NAME_ID))
                val nombre = getString(getColumnIndexOrThrow(ClienteHotelContract.ClienteEntry.COLUMN_NAME_NOMBRE))
                val documento = getString(getColumnIndexOrThrow(ClienteHotelContract.ClienteEntry.COLUMN_NAME_DOCUMENTO))
                val fechaCheckIn = getString(getColumnIndexOrThrow(ClienteHotelContract.ClienteEntry.COLUMN_NAME_FECHA_CHECK_IN))
                val fechaCheckOut = getString(getColumnIndexOrThrow(ClienteHotelContract.ClienteEntry.COLUMN_NAME_FECHA_CHECK_OUT))
                val numeroTelefono = getString(getColumnIndexOrThrow(ClienteHotelContract.ClienteEntry.COLUMN_NAME_NUMERO_TELEFONO))
                val habitacionIdObtenida = getInt(getColumnIndexOrThrow(ClienteHotelContract.ClienteEntry.COLUMN_NAME_HABITACION_ID))

                clientes.add(ClienteHotel(id, nombre, documento, fechaCheckIn, fechaCheckOut, numeroTelefono, habitacionIdObtenida))
            }
        }
        cursor.close()
        return clientes
    }

}

