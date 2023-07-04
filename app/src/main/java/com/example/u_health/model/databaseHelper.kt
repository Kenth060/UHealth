package com.example.u_health.model

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf

class databaseHelper(context: Context) : SQLiteOpenHelper( context,
    constants.DATABASE_NAME,
    null,
    constants.DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableMeds = "CREATE TABLE ${constants.ENTITY_MEDICAMENTOS} ( " +
                "${constants.PROPERTY_ID}   INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${constants.PROPERTY_PASTILLA} VARCHAR(120)," +
                "${constants.PROPERTY_DOSIS} VARCHAR(120)," +
                "${constants.PROPERTY_HORA} VARCHAR(120)," +
                "${constants.PROPERTY_CANTIDAD} INT)"

        val createTableCitas = "CREATE TABLE ${constants.ENTITY_CITAS} ( " +
                "${constants.PROPERTY_ID}   INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${constants.PROPERTY_TITULO} VARCHAR(120)," +
                "${constants.PROPERTY_MEDICO} VARCHAR(120)," +
                "${constants.PROPERTY_FECHA} VARCHAR(120)," +
                "${constants.PROPERTY_HORA} VARCHAR(120)," +
                "${constants.PROPERTY_DETALLES} VARCHAR(300))"

        db?.execSQL(createTableMeds)
        db?.execSQL(createTableCitas)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    @SuppressLint("Range")
    fun getRecordatorios_Medicamentos(): MutableList<Medicamentos>
    {
        val meds: MutableList<Medicamentos> = mutableListOf()
        val database = this.readableDatabase
        val query = "SELECT * FROM ${constants.ENTITY_MEDICAMENTOS}"
        val result = database.rawQuery(query, null)

        if (result.moveToFirst()) {
            do {
                val med = Medicamentos()
                med.Id = result.getLong(result.getColumnIndex(constants.PROPERTY_ID))
                med.Pastilla = result.getString(result.getColumnIndex(constants.PROPERTY_PASTILLA))
                med.Dosis = result.getString(result.getColumnIndex(constants.PROPERTY_DOSIS))
                med.Hora = result.getString(result.getColumnIndex(constants.PROPERTY_HORA))
                med.Cantidad = result.getInt(result.getColumnIndex(constants.PROPERTY_CANTIDAD))
                meds.add(med)
            } while (result.moveToNext())
        }
        return meds
    }

    @SuppressLint("Range")
    fun getRecordatorios_Citas(): MutableList<Citas>
    {
        val citas: MutableList<Citas> = mutableListOf()
        val database = this.readableDatabase
        val query = "SELECT * FROM ${constants.ENTITY_CITAS}"
        val result = database.rawQuery(query, null)

        if (result.moveToFirst())
        {
            do {
                val cita = Citas()
                cita.Id = result.getLong(result.getColumnIndex(constants.PROPERTY_ID))
                cita.Titulo = result.getString(result.getColumnIndex(constants.PROPERTY_TITULO))
                cita.Medico = result.getString(result.getColumnIndex(constants.PROPERTY_MEDICO))
                cita.Fecha = result.getString(result.getColumnIndex(constants.PROPERTY_FECHA))
                cita.Hora = result.getString(result.getColumnIndex(constants.PROPERTY_HORA))
                cita.Detalles = result.getString(result.getColumnIndex(constants.PROPERTY_DETALLES))
                citas.add(cita)
            } while (result.moveToNext())
        }
        return citas
    }

    fun insertarRecordatorios_Medicamentos(meds: Medicamentos): Long
    {
        val database = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(constants.PROPERTY_PASTILLA, meds.Pastilla)
            put(constants.PROPERTY_DOSIS, meds.Dosis)
            put(constants.PROPERTY_HORA, meds.Hora)
            put(constants.PROPERTY_CANTIDAD, meds.Cantidad)
        }

        val resultId = database.insert(constants.ENTITY_MEDICAMENTOS, null, contentValues)

        return resultId
    }

    fun insertarRecordatorios_Citas(citas: Citas): Long
    {
        val database = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(constants.PROPERTY_ID, citas.Id)
            put(constants.PROPERTY_TITULO, citas.Titulo)
            put(constants.PROPERTY_MEDICO, citas.Medico)
            put(constants.PROPERTY_FECHA, citas.Fecha)
            put(constants.PROPERTY_HORA, citas.Hora)
            put(constants.PROPERTY_DETALLES, citas.Detalles)
        }

        val resultId = database.insert(constants.ENTITY_CITAS, null, contentValues)

        return resultId
    }

    fun actualizarRecordatorios_Medicamentos(meds: Medicamentos): Boolean
    {
        val database = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(constants.PROPERTY_PASTILLA, meds.Pastilla)
            put(constants.PROPERTY_DOSIS, meds.Dosis)
            put(constants.PROPERTY_HORA, meds.Hora)
            put(constants.PROPERTY_CANTIDAD, (meds.Cantidad-1))
        }

        val result = database.update(
            constants.ENTITY_MEDICAMENTOS,
            contentValues,
            "${constants.PROPERTY_ID}= ${meds.Id}",
            null)

        return result == constants.TRUE
    }


    fun deleteRecordatorios_Medicamentos(meds: Medicamentos):Boolean
    {
        val database = this.writableDatabase
        val result = database.delete(
            constants.ENTITY_MEDICAMENTOS,
            "${constants.PROPERTY_ID} = ${meds.Id}",
            null)

        return result == constants.TRUE
    }

    fun deleteRecordatorios_Citas(citas: Citas):Boolean
    {
        val database = this.writableDatabase
        val result = database.delete(
            constants.ENTITY_CITAS,
            "${constants.PROPERTY_ID} = ${citas.Id}",
            null)

        return result == constants.TRUE
    }

}