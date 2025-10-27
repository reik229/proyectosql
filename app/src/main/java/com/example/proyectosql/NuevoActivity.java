package com.example.proyectosql;

// Importaciones necesarias para UI, intents y base de datos
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge; // Librería para habilitar Edge-to-Edge en la UI
import androidx.appcompat.app.AppCompatActivity; // Clase base para actividades con compatibilidad ActionBar

import com.example.proyectosql.db.DbContactos; // Clase de acceso a la base de datos de contactos

public class NuevoActivity extends AppCompatActivity {

    // Declaración de variables de UI
    EditText txtNombre, txtTelefono, txtCorreoElectronico; // Campos de texto para datos del contacto
    Button btnGuarda; // Botón para guardar nuevo contacto

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Habilita diseño Edge-to-Edge
        setContentView(R.layout.activity_nuevo); // Inflar layout de la actividad

        // Mostrar la flecha "atrás" en la barra de acción
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Inicialización de componentes de la interfaz
        txtNombre = findViewById(R.id.txtNombre);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtCorreoElectronico = findViewById(R.id.txtCorreoElectronico);
        btnGuarda = findViewById(R.id.btnGuarda);

        // Configuración del listener para guardar un nuevo contacto
        btnGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear instancia de la base de datos
                DbContactos dbContactos = new DbContactos(NuevoActivity.this);

                // Insertar un nuevo contacto en la base de datos
                long id = dbContactos.insertarContacto(
                        txtNombre.getText().toString(),
                        txtTelefono.getText().toString(),
                        txtCorreoElectronico.getText().toString()
                );

                // Retroalimentación al usuario según el resultado de la inserción
                if (id > 0) {
                    Toast.makeText(NuevoActivity.this, "Registro guardado", Toast.LENGTH_LONG).show();
                    limpiar(); // Limpiar los campos tras insertar correctamente
                } else {
                    Toast.makeText(NuevoActivity.this, "Error al guardar el registro", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // Método privado para limpiar los campos de texto
    private void limpiar() {
        txtNombre.setText("");
        txtTelefono.setText("");
        txtCorreoElectronico.setText("");
    }

    // Manejo de la flecha "atrás" en la barra de acción
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // Vuelve a la actividad anterior (MainActivity)
        return true;
    }
}
