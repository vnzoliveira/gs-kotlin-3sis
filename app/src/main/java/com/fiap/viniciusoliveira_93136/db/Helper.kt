package com.fiap.viniciusoliveira_93136.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.fiap.viniciusoliveira_93136.model.Dica

class Helper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "ecodicas.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_DICAS = "dicas"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITULO = "titulo"
        private const val COLUMN_DESCRICAO = "descricao"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE = ("CREATE TABLE $TABLE_DICAS("
                + "$COLUMN_ID INTEGER PRIMARY KEY,"
                + "$COLUMN_TITULO TEXT,"
                + "$COLUMN_DESCRICAO TEXT)")
        db?.execSQL(CREATE_TABLE)
        inserirDicasIniciais(db)
    }

    private fun inserirDicasIniciais(db: SQLiteDatabase?) {
        val dicasIniciais = listOf(
            Dica("Use lâmpadas LED", "Economize energia usando lâmpadas LED."),
            Dica("Desligue aparelhos que não estão em uso", "Aparelhos em standby consomem energia. Desconecte-os."),
        )
        for (dica in dicasIniciais) {
            val values = ContentValues().apply {
                put(COLUMN_TITULO, dica.titulo)
                put(COLUMN_DESCRICAO, dica.descricao)
            }
            db?.insert(TABLE_DICAS, null, values)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_DICAS")
        onCreate(db)
    }

    fun getAllDicas(): MutableList<Dica> {
        val listaDicas = mutableListOf<Dica>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_DICAS", null)
        if (cursor.moveToFirst()) {
            do {
                val titulo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITULO))
                val descricao = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRICAO))
                listaDicas.add(Dica(titulo, descricao))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return listaDicas
    }

    fun inserirDica(dica: Dica) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITULO, dica.titulo)
            put(COLUMN_DESCRICAO, dica.descricao)
        }
        db.insert(TABLE_DICAS, null, values)
        db.close()
    }
}