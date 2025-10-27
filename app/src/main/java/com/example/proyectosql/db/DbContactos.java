package com.example.proyectosql.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.proyectosql.entidades.Contactos;

import java.util.ArrayList;

/**
 * Clase {@code DbContactos} que implementa las operaciones CRUD (crear, leer, actualizar, eliminar)
 * sobre la tabla de contactos en la base de datos SQLite local.
 * <p>
 * Extiende de {@link DbHelper}, utilizando sus métodos de acceso para la creación y conexión
 * a la base de datos. Cada método interactúa directamente con la tabla {@code TABLA_CONTATOS}.
 */
public class DbContactos extends DbHelper {

    private final Context context;

    /**
     * Constructor que inicializa el contexto de la base de datos.
     *
     * @param context Contexto de la aplicación o actividad que accede a la base de datos.
     */
    public DbContactos(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    /**
     * Inserta un nuevo registro de contacto en la base de datos.
     *
     * @param nombre             Nombre del contacto.
     * @param telefono           Número telefónico del contacto.
     * @param correo_electronico Dirección de correo electrónico del contacto.
     * @return ID autogenerado del nuevo contacto insertado, o {@code 0} si ocurrió un error.
     */
    public long insertarContacto(String nombre, String telefono, String correo_electronico) {
        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("telefono", telefono);
            values.put("correo_electronico", correo_electronico);

            id = db.insert(TABLA_CONTATOS, null, values);
            db.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return id;
    }

    /**
     * Obtiene todos los contactos almacenados en la base de datos.
     *
     * @return Lista de objetos {@link Contactos} que representan los registros existentes.
     */
    public ArrayList<Contactos> mostrarContactos() {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ArrayList<Contactos> listaContactos = new ArrayList<>();
        Cursor cursorContactos = db.rawQuery("SELECT * FROM " + TABLA_CONTATOS, null);

        if (cursorContactos.moveToFirst()) {
            do {
                Contactos contacto = new Contactos();
                contacto.setId(cursorContactos.getInt(0));
                contacto.setNombre(cursorContactos.getString(1));
                contacto.setTelefono(cursorContactos.getString(2));
                contacto.setCorreoElectronico(cursorContactos.getString(3));
                listaContactos.add(contacto);
            } while (cursorContactos.moveToNext());
        }

        cursorContactos.close();
        db.close();
        return listaContactos;
    }

    /**
     * Recupera un contacto específico por su ID.
     *
     * @param id Identificador único del contacto.
     * @return Objeto {@link Contactos} con la información del registro, o {@code null} si no existe.
     */
    public Contactos verContactos(int id) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Contactos contacto = null;
        Cursor cursorContactos = db.rawQuery(
                "SELECT * FROM " + TABLA_CONTATOS + " WHERE id = " + id + " LIMIT 1", null
        );

        if (cursorContactos.moveToFirst()) {
            contacto = new Contactos();
            contacto.setId(cursorContactos.getInt(0));
            contacto.setNombre(cursorContactos.getString(1));
            contacto.setTelefono(cursorContactos.getString(2));
            contacto.setCorreoElectronico(cursorContactos.getString(3));
        }

        cursorContactos.close();
        db.close();
        return contacto;
    }

    /**
     * Elimina un contacto de la base de datos según su ID.
     *
     * @param id Identificador del contacto a eliminar.
     * @return {@code true} si la operación fue exitosa, {@code false} en caso contrario.
     */
    public boolean eliminarContacto(int id) {
        boolean correcto;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLA_CONTATOS + " WHERE id = '" + id + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            correcto = false;
        } finally {
            db.close();
        }
        return correcto;
    }

    /**
     * Actualiza los datos de un contacto existente.
     *
     * @param id                 ID del contacto a modificar.
     * @param nombre             Nuevo nombre.
     * @param telefono           Nuevo número telefónico.
     * @param correo_electronico Nuevo correo electrónico.
     * @return {@code true} si la actualización fue exitosa, {@code false} si ocurrió un error.
     */
    public boolean editarContacto(int id, String nombre, String telefono, String correo_electronico) {
        boolean correcto;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLA_CONTATOS +
                    " SET nombre = '" + nombre + "', telefono = '" + telefono +
                    "', correo_electronico = '" + correo_electronico + "' WHERE id = " + id);
            correcto = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }
}
