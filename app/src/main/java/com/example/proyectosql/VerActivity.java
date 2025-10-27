package com.example.proyectosql;

// Importaciones necesarias para UI, intents, alert dialog y base de datos
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge; // Librería para habilitar Edge-to-Edge en la UI
import androidx.appcompat.app.AlertDialog; // Para mostrar diálogos de confirmación
import androidx.appcompat.app.AppCompatActivity; // Clase base para actividades con ActionBar

import com.example.proyectosql.db.DbContactos; // Clase de acceso a la base de datos de contactos
import com.example.proyectosql.entidades.Contactos; // Modelo de datos Contactos
import com.google.android.material.floatingactionbutton.FloatingActionButton; // FAB de Material Design

public class VerActivity extends AppCompatActivity {

    // Declaración de variables de UI
    EditText txtNombre, txtTelefono, txtCorreo; // Campos de texto para mostrar contacto
    Button btnGuarda; // Botón para guardar (invisible en esta actividad)
    FloatingActionButton fabEditar, fabEliminar; // FABs para editar y eliminar

    Contactos contacto; // Objeto Contacto a visualizar
    int id = 0; // ID del contacto

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Habilita Edge-to-Edge
        setContentView(R.layout.activity_ver); // Inflar layout de ver contacto

        // Inicialización de componentes de UI
        txtNombre = findViewById(R.id.txtNombre);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtCorreo = findViewById(R.id.txtCorreoElectronico);
        btnGuarda = findViewById(R.id.btnGuarda);
        fabEditar = findViewById(R.id.fabEditar);
        fabEliminar = findViewById(R.id.fabEliminar);

        // Recuperación del ID desde extras o savedInstanceState
        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                id = Integer.parseInt(null); // Posible error si extras es null
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }

        DbContactos dbContactos = new DbContactos(VerActivity.this); // Inicialización DB
        contacto = dbContactos.verContactos(id); // Obtener contacto por ID

        // Si existe el contacto, cargar datos en campos de texto
        if(contacto != null){
            txtNombre.setText(contacto.getNombre());
            txtTelefono.setText(contacto.getTelefono());
            txtCorreo.setText(contacto.getCorreoElectronico());

            btnGuarda.setVisibility(View.INVISIBLE); // Ocultar botón guardar
            // Bloquear edición de campos
            txtNombre.setInputType(InputType.TYPE_NULL);
            txtTelefono.setInputType(InputType.TYPE_NULL);
            txtCorreo.setInputType(InputType.TYPE_NULL);
        }

        // Configuración del FAB de edición
        fabEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VerActivity.this, EditarActivity.class);
                intent.putExtra("ID", id); // Enviar ID al EditarActivity
                startActivity(intent);
            }
        });

        // Configuración del FAB de eliminación con AlertDialog de confirmación
        fabEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VerActivity.this);
                builder.setMessage("¿Desea eliminar el contacto?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(dbContactos.eliminarContacto(id)){
                                    lista(); // Volver a la lista de contactos
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // No hacer nada si el usuario cancela
                            }
                        }).show();
            }
        });
    }

    // Método privado para volver a MainActivity (lista de contactos)
    private void lista (){
        Intent intent = new Intent(VerActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
