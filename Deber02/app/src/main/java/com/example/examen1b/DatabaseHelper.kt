package com.example.examen1b

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "HotelDatabase.db"

        private const val SQL_CREATE_HABITACIONES =
            "CREATE TABLE ${HabitacionContract.HabitacionEntry.TABLE_NAME} (" +
                    "${HabitacionContract.HabitacionEntry.COLUMN_NAME_ID} INTEGER PRIMARY KEY," +
                    "${HabitacionContract.HabitacionEntry.COLUMN_NAME_NOMBRE} TEXT," +
                    "${HabitacionContract.HabitacionEntry.COLUMN_NAME_CAPACIDAD} INTEGER," +
                    "${HabitacionContract.HabitacionEntry.COLUMN_NAME_PRECIO} REAL," +
                    "${HabitacionContract.HabitacionEntry.COLUMN_NAME_DISPONIBLE} INTEGER)"

        private const val SQL_CREATE_CLIENTES =
            "CREATE TABLE ${ClienteHotelContract.ClienteEntry.TABLE_NAME} (" +
                    "${ClienteHotelContract.ClienteEntry.COLUMN_NAME_ID} INTEGER PRIMARY KEY," +
                    "${ClienteHotelContract.ClienteEntry.COLUMN_NAME_NOMBRE} TEXT," +
                    "${ClienteHotelContract.ClienteEntry.COLUMN_NAME_DOCUMENTO} TEXT," +
                    "${ClienteHotelContract.ClienteEntry.COLUMN_NAME_FECHA_CHECK_IN} TEXT," +
                    "${ClienteHotelContract.ClienteEntry.COLUMN_NAME_FECHA_CHECK_OUT} TEXT," +
                    "${ClienteHotelContract.ClienteEntry.COLUMN_NAME_NUMERO_TELEFONO} TEXT," +
                    "${ClienteHotelContract.ClienteEntry.COLUMN_NAME_HABITACION_ID} INTEGER," +
                    "FOREIGN KEY(${ClienteHotelContract.ClienteEntry.COLUMN_NAME_HABITACION_ID}) REFERENCES " +
                    "${HabitacionContract.HabitacionEntry.TABLE_NAME}(${HabitacionContract.HabitacionEntry.COLUMN_NAME_ID}))"

        private const val SQL_DELETE_HABITACIONES =
            "DROP TABLE IF EXISTS ${HabitacionContract.HabitacionEntry.TABLE_NAME}"

        private const val SQL_DELETE_CLIENTES =
            "DROP TABLE IF EXISTS ${ClienteHotelContract.ClienteEntry.TABLE_NAME}"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_HABITACIONES)
        db.execSQL(SQL_CREATE_CLIENTES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_HABITACIONES)
        db.execSQL(SQL_DELETE_CLIENTES)
        onCreate(db)
    }
    override fun onConfigure(db: SQLiteDatabase?) {
        super.onConfigure(db)
        db?.setForeignKeyConstraintsEnabled(true)
    }

    // Aquí puedes agregar métodos adicionales para insertar, actualizar, eliminar y seleccionar registros de la base de datos.
}

