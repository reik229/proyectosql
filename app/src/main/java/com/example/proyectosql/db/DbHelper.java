package com.example.proyectosql.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private  static final int DATABASE_VERSION = 1;
    private  static final String DATABASE_NOMBRE = "agenda.db";
    public  static final String TABLA_CONTATOS = "contactos";
    public DbHelper(@Nullable Context context ) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLA_CONTATOS + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                 "telefono TEXT NOT NULL," +
                  "correo_electronico TEXT)");



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE " + TABLA_CONTATOS);
        onCreate(sqLiteDatabase);


    }
}
