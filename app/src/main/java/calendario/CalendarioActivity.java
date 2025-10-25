package com.example.proyectosql;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectosql.db.DbNotas;

import java.util.HashSet;
import java.util.Set;

public class CalendarioActivity extends AppCompatActivity {

    CalendarView calendarView;
    DbNotas dbNotas;
    Set<String> fechasConNotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        dbNotas = new DbNotas(this);
        calendarView = findViewById(R.id.calendarView);
        fechasConNotas = new HashSet<>();

        // Cargar fechas con notas existentes
        cargarFechasConNotas();

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String fecha = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year);
            mostrarDialogoNota(fecha);
        });
    }

    private void cargarFechasConNotas() {
        // Para simplificar, podemos consultar todas las notas y guardar solo las fechas
        // Esto requiere modificar DbNotas para devolver todas las fechas
        fechasConNotas.clear();
        for (String fecha : dbNotas.obtenerTodasFechas()) {
            fechasConNotas.add(fecha);
        }
    }

    private void mostrarDialogoNota(String fecha) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nota para " + fecha);

        final EditText input = new EditText(this);
        input.setHint("Escribe tu nota aquí...");

        String notaExistente = dbNotas.obtenerNota(fecha);
        if (notaExistente != null) {
            input.setText(notaExistente);
        }

        builder.setView(input);

        builder.setPositiveButton("Guardar", (dialog, which) -> {
            String nota = input.getText().toString().trim();
            if (!nota.isEmpty()) {
                dbNotas.guardarNota(fecha, nota);
                fechasConNotas.add(fecha);
                Toast.makeText(this, "Nota guardada", Toast.LENGTH_SHORT).show();
            } else {
                dbNotas.borrarNota(fecha);
                fechasConNotas.remove(fecha);
                Toast.makeText(this, "Nota eliminada", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
