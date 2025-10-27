package com.example.proyectosql;

// Importación de librerías necesarias para la actividad, manejo de vistas, intents y componentes UI
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge; // Librería para habilitar el modo Edge-to-Edge en la UI
import androidx.appcompat.app.AppCompatActivity; // Clase base para actividades con compatibilidad con ActionBar

import com.example.proyectosql.db.DbContactos; // Clase de acceso a la base de datos para contactos
import com.example.proyectosql.entidades.Contactos; // Modelo de datos Contactos
import com.google.android.material.floatingactionbutton.FloatingActionButton; // Componente FloatingActionButton de Material Design

public class EditarActivity extends AppCompatActivity {

    // Declaración de variables de UI y estado
    EditText txtNombre, txtTelefono, txtCorreo; // Campos de texto para nombre, teléfono y correo
    Button btnGuarda; // Botón para guardar cambios
    FloatingActionButton fabEditar, fabEliminar; // Botones flotantes para editar y eliminar
    boolean correcto = false; // Indicador del resultado de la operación de edición

    Contactos contacto; // Objeto contacto para manipulación de datos
    int id = 0; // ID del contacto a editar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this); // Habilita diseño Edge-to-Edge para la actividad
        setContentView(R.layout.activity_ver); // Inflar layout asociado a la actividad

        // Inicialización de los componentes de la interfaz mediante findViewById
        txtNombre = findViewById(R.id.txtNombre);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtCorreo = findViewById(R.id.txtCorreoElectronico);
        btnGuarda = findViewById(R.id.btnGuarda);
        fabEditar = findViewById(R.id.fabEditar);
        fabEditar.setVisibility(View.INVISIBLE); // Oculta el FAB de editar
        fabEliminar = findViewById(R.id.fabEliminar);
        fabEditar.setVisibility(View.INVISIBLE); // Posible error: se oculta fabEditar nuevamente en lugar de fabEliminar

        // Recuperación del ID del contacto desde extras o savedInstanceState
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                id = 0; // Valor por defecto si no hay extras
            } else {
                id = extras.getInt("ID"); // Recupera ID enviado mediante intent
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID"); // Recupera ID desde estado guardado
        }

        DbContactos dbContactos = new DbContactos(EditarActivity.this); // Inicialización de la clase de base de datos
        contacto = dbContactos.verContactos(id); // Obtención del contacto según ID

        // Si el contacto existe, se cargan los datos en los campos de texto
        if (contacto != null) {
            txtNombre.setText(contacto.getNombre());
            txtTelefono.setText(contacto.getTelefono());
            txtCorreo.setText(contacto.getCorreoElectronico());
        }

        // Configuración del listener para el botón guardar
        btnGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Validación de campos obligatorios
                if (!txtNombre.getText().toString().equals("") &&
                        !txtTelefono.getText().toString().equals("")) {

                    // Llamada a la función de edición en la base de datos y captura del resultado
                    correcto = dbContactos.editarContacto(
                            id,
                            txtNombre.getText().toString(),
                            txtTelefono.getText().toString(),
                            txtCorreo.getText().toString()
                    );

                    verRegistro(); // Redirección a la actividad de visualización del contacto

                    // Retroalimentación al usuario según el resultado de la operación
                    if (correcto) {
                        Toast.makeText(EditarActivity.this, "REGISTRO MODIFICADO", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditarActivity.this, "ERROR AL MODIFICAR EL REGISTRO", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Mensaje de validación cuando faltan campos obligatorios
                    Toast.makeText(EditarActivity.this, "DEBE LLENAR TODOS LOS CAMPOS", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Método privado para abrir la actividad de visualización de un contacto específico
    private void verRegistro() {
        Intent intent = new Intent(this, VerActivity.class); // Creación del intent hacia VerActivity
        intent.putExtra("ID", id); // Se envía el ID del contacto
        startActivity(intent); // Se inicia la actividad
    }
}
