package com.example.proyectosql.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.HashSet;
import java.util.Set;

/**
 * Clase {@code DbNotas} que gestiona la persistencia de notas asociadas a fechas
 * en una base de datos SQLite.
 * <p>
 * Permite realizar operaciones CRUD básicas:
 * <ul>
 *     <li>Crear o actualizar notas por fecha</li>
 *     <li>Obtener notas existentes</li>
 *     <li>Eliminar notas</li>
 *     <li>Recuperar todas las fechas con notas registradas</li>
 * </ul>
 */
public class DbNotas extends SQLiteOpenHelper {

    /** Nombre de la base de datos. */
    private static final String DATABASE_NAME = "notas.db";

    /** Versión de la base de datos para control de actualizaciones. */
    private static final int DATABASE_VERSION = 1;

    /** Nombre de la tabla que almacena las notas. */
    public static final String TABLE_NAME = "notas";

    /**
     * Constructor que inicializa el ayudante de base de datos.
     *
     * @param context Contexto de la aplicación o actividad que accede a la base de datos.
     */
    public DbNotas(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Método invocado automáticamente al crear la base de datos.
     * Define la estructura de la tabla {@code notas}, donde cada fecha es única.
     *
     * @param db Instancia de la base de datos SQLite.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "fecha TEXT UNIQUE, " +   // clave única por fecha
                "nota TEXT)";
        db.execSQL(query);
    }

    /**
     * Método invocado al actualizar la versión de la base de datos.
     * Elimina la tabla existente y la recrea.
     *
     * @param db         Instancia de la base de datos.
     * @param oldVersion Versión anterior.
     * @param newVersion Nueva versión.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * Inserta una nueva nota o actualiza la existente si la fecha ya está registrada.
     *
     * @param fecha Fecha asociada a la nota en formato dd/MM/yyyy.
     * @param nota  Contenido de la nota.
     */
    public void guardarNota(String fecha, String nota) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("fecha", fecha);
            values.put("nota", nota);
            db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene la nota registrada para una fecha específica.
     *
     * @param fecha Fecha de la nota a recuperar.
     * @return Texto de la nota o {@code null} si no existe.
     */
    public String obtenerNota(String fecha) {
        String nota = null;
        try (SQLiteDatabase db = this.getReadableDatabase();
             Cursor cursor = db.query(TABLE_NAME,
                     new String[]{"nota"},
                     "fecha=?",
                     new String[]{fecha},
                     null, null, null)) {

            if (cursor != null && cursor.moveToFirst()) {
                nota = cursor.getString(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nota;
    }

    /**
     * Elimina la nota asociada a una fecha específica.
     *
     * @param fecha Fecha de la nota a eliminar.
     */
    public void borrarNota(String fecha) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.delete(TABLE_NAME, "fecha=?", new String[]{fecha});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Devuelve todas las fechas que tienen notas registradas.
     *
     * @return Conjunto de fechas con notas.
     */
    public Set<String> obtenerTodasFechas() {
        Set<String> fechas = new HashSet<>();
        try (SQLiteDatabase db = this.getReadableDatabase();
             Cursor cursor = db.query(TABLE_NAME,
                     new String[]{"fecha"},
                     null, null, null, null, null)) {

            while (cursor != null && cursor.moveToNext()) {
                fechas.add(cursor.getString(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fechas;
    }
}
