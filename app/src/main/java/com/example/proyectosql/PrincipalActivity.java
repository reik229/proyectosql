package com.example.proyectosql;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PrincipalActivity extends AppCompatActivity {

    Button btnCalendario, btnContactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        btnCalendario = findViewById(R.id.btnCalendario);
        btnContactos = findViewById(R.id.btnContactos);

        btnCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ir al calendario
                Intent intent = new Intent(PrincipalActivity.this, com.example.proyectosql.CalendarioActivity.class);
                startActivity(intent);
            }
        });

        btnContactos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ir a la lista de contactos
                Intent intent = new Intent(PrincipalActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
