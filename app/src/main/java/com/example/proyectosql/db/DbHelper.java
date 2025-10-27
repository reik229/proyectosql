package com.example.proyectosql.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * Clase {@code DbHelper} encargada de la creación, actualización y mantenimiento
 * de la base de datos SQLite utilizada por la aplicación.
 * <p>
 * Extiende de {@link SQLiteOpenHelper} y define la estructura de la tabla principal
 * {@code contactos}, utilizada para almacenar la información de los contactos
 * registrados en la agenda.
 */
public class DbHelper extends SQLiteOpenHelper {

    /** Versión de la base de datos, utilizada para controlar actualizaciones de esquema. */
    private static final int DATABASE_VERSION = 1;

    /** Nombre físico del archivo de base de datos. */
    private static final String DATABASE_NOMBRE = "agenda.db";

    /** Nombre de la tabla principal que contiene los registros de contactos. */
    public static final String TABLA_CONTATOS = "contactos";

    /**
     * Constructor que inicializa el ayudante de base de datos con los parámetros definidos.
     *
     * @param context Contexto de la aplicación o actividad que accede a la base de datos.
     */
    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    /**
     * Método invocado automáticamente al crear la base de datos por primera vez.
     * Define la estructura de la tabla {@code contactos} con sus columnas:
     * <ul>
     *   <li><b>id</b> – Clave primaria autoincremental</li>
     *   <li><b>nombre</b> – Nombre del contacto (campo obligatorio)</li>
     *   <li><b>telefono</b> – Número telefónico (campo obligatorio)</li>
     *   <li><b>correo_electronico</b> – Correo electrónico del contacto</li>
     * </ul>
     *
     * @param sqLiteDatabase Instancia de la base de datos sobre la que se ejecutan las operaciones SQL.
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLA_CONTATOS + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "telefono TEXT NOT NULL," +
                "correo_electronico TEXT)");
    }

    /**
     * Método invocado automáticamente cuando la versión de la base de datos cambia.
     * En este caso, elimina la tabla existente y la vuelve a crear.
     *
     * @param sqLiteDatabase Instancia de la base de datos.
     * @param i              Versión anterior de la base de datos.
     * @param i1             Nueva versión de la base de datos.
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLA_CONTATOS);
        onCreate(sqLiteDatabase);
    }
}
