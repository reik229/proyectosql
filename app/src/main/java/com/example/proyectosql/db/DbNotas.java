package com.example.proyectosql.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.HashSet;
import java.util.Set;

public class DbNotas extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "notas.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "notas";

    public DbNotas(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "fecha TEXT UNIQUE," +  // clave única por fecha
                "nota TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Guardar o actualizar nota
    public void guardarNota(String fecha, String nota) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("fecha", fecha);
        values.put("nota", nota);
        db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    // Obtener nota por fecha
    public String obtenerNota(String fecha) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{"nota"}, "fecha=?",
                new String[]{fecha}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            String nota = cursor.getString(0);
            cursor.close();
            db.close();
            return nota;
        }
        if (cursor != null) cursor.close();
        db.close();
        return null;
    }

    // Borrar nota por fecha
    public void borrarNota(String fecha) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "fecha=?", new String[]{fecha});
        db.close();
    }

    // Devuelve todas las fechas que tienen nota
    public Set<String> obtenerTodasFechas() {
        Set<String> fechas = new HashSet<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{"fecha"}, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                fechas.add(cursor.getString(0));
            }
            cursor.close();
        }
        db.close();
        return fechas;
    }

}
