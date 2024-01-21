package com.example.examen1b

import android.content.ContentValues

class HabitacionDao(private val dbHelper: DatabaseHelper) {

    fun insertarHabitacion(habitacion: Habitacion): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(HabitacionContract.HabitacionEntry.COLUMN_NAME_NOMBRE, habitacion.nombre)
            put(HabitacionContract.HabitacionEntry.COLUMN_NAME_CAPACIDAD, habitacion.capacidad)
            put(HabitacionContract.HabitacionEntry.COLUMN_NAME_PRECIO, habitacion.precio)
            put(HabitacionContract.HabitacionEntry.COLUMN_NAME_DISPONIBLE, if (habitacion.disponible) 1 else 0)
        }
        return db.insert(HabitacionContract.HabitacionEntry.TABLE_NAME, null, values)
    }

    fun obtenerHabitaciones(): List<Habitacion> {
        val db = dbHelper.readableDatabase
        val projection = arrayOf(
            HabitacionContract.HabitacionEntry.COLUMN_NAME_ID,
            HabitacionContract.HabitacionEntry.COLUMN_NAME_NOMBRE,
            HabitacionContract.HabitacionEntry.COLUMN_NAME_CAPACIDAD,
            HabitacionContract.HabitacionEntry.COLUMN_NAME_PRECIO,
            HabitacionContract.HabitacionEntry.COLUMN_NAME_DISPONIBLE
        )

        val cursor = db.query(
            HabitacionContract.HabitacionEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )

        val habitaciones = mutableListOf<Habitacion>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(HabitacionContract.HabitacionEntry.COLUMN_NAME_ID))
                val nombre = getString(getColumnIndexOrThrow(HabitacionContract.HabitacionEntry.COLUMN_NAME_NOMBRE))
                val capacidad = getInt(getColumnIndexOrThrow(HabitacionContract.HabitacionEntry.COLUMN_NAME_CAPACIDAD))
                val precio = getDouble(getColumnIndexOrThrow(HabitacionContract.HabitacionEntry.COLUMN_NAME_PRECIO))
                val disponible = getInt(getColumnIndexOrThrow(HabitacionContract.HabitacionEntry.COLUMN_NAME_DISPONIBLE)) != 0

                habitaciones.add(Habitacion(id, nombre, capacidad, precio, disponible))
            }
        }
        cursor.close()
        return habitaciones
    }

    fun actualizarHabitacion(habitacion: Habitacion): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(HabitacionContract.HabitacionEntry.COLUMN_NAME_NOMBRE, habitacion.nombre)
            put(HabitacionContract.HabitacionEntry.COLUMN_NAME_CAPACIDAD, habitacion.capacidad)
            put(HabitacionContract.HabitacionEntry.COLUMN_NAME_PRECIO, habitacion.precio)
            put(HabitacionContract.HabitacionEntry.COLUMN_NAME_DISPONIBLE, if (habitacion.disponible) 1 else 0)
        }

        val selection = "${HabitacionContract.HabitacionEntry.COLUMN_NAME_ID} LIKE ?"
        val selectionArgs = arrayOf(habitacion.id.toString())
        return db.update(HabitacionContract.HabitacionEntry.TABLE_NAME, values, selection, selectionArgs)
    }

    fun eliminarHabitacion(habitacionId: Int): Int {
        val db = dbHelper.writableDatabase
        val selection = "${HabitacionContract.HabitacionEntry.COLUMN_NAME_ID} LIKE ?"
        val selectionArgs = arrayOf(habitacionId.toString())
        return db.delete(HabitacionContract.HabitacionEntry.TABLE_NAME, selection, selectionArgs)
    }

    fun obtenerHabitacionPorId(id: Int): Habitacion? {
        val db = dbHelper.readableDatabase
        val projection = arrayOf(
            HabitacionContract.HabitacionEntry.COLUMN_NAME_ID,
            HabitacionContract.HabitacionEntry.COLUMN_NAME_NOMBRE,
            HabitacionContract.HabitacionEntry.COLUMN_NAME_CAPACIDAD,
            HabitacionContract.HabitacionEntry.COLUMN_NAME_PRECIO,
            HabitacionContract.HabitacionEntry.COLUMN_NAME_DISPONIBLE
        )
        val selection = "${HabitacionContract.HabitacionEntry.COLUMN_NAME_ID} = ?"
        val selectionArgs = arrayOf(id.toString())

        val cursor = db.query(
            HabitacionContract.HabitacionEntry.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        with(cursor) {
            if (moveToFirst()) {
                val nombre = getString(getColumnIndexOrThrow(HabitacionContract.HabitacionEntry.COLUMN_NAME_NOMBRE))
                val capacidad = getInt(getColumnIndexOrThrow(HabitacionContract.HabitacionEntry.COLUMN_NAME_CAPACIDAD))
                val precio = getDouble(getColumnIndexOrThrow(HabitacionContract.HabitacionEntry.COLUMN_NAME_PRECIO))
                val disponible = getInt(getColumnIndexOrThrow(HabitacionContract.HabitacionEntry.COLUMN_NAME_DISPONIBLE)) != 0
                cursor.close()
                return Habitacion(id, nombre, capacidad, precio, disponible)
            }
        }
        cursor.close()
        return null
    }
}
