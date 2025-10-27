package com.example.proyectosql;

// Importaciones necesarias para UI e intents
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity; // Clase base para actividades con compatibilidad ActionBar

public class PrincipalActivity extends AppCompatActivity {

    // Declaración de botones para navegación
    Button btnCalendario, btnContactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal); // Inflar layout de la actividad principal

        // Inicialización de botones mediante findViewById
        btnCalendario = findViewById(R.id.btnCalendario);
        btnContactos = findViewById(R.id.btnContactos);

        // Configuración del listener para ir a CalendarioActivity
        btnCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear intent explícito para navegar a CalendarioActivity
                Intent intent = new Intent(PrincipalActivity.this, com.example.proyectosql.CalendarioActivity.class);
                startActivity(intent); // Iniciar actividad de calendario
            }
        });

        // Configuración del listener para ir a MainActivity (lista de contactos)
        btnContactos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear intent explícito para navegar a MainActivity
                Intent intent = new Intent(PrincipalActivity.this, MainActivity.class);
                startActivity(intent); // Iniciar actividad de contactos
            }
        });
    }
}
