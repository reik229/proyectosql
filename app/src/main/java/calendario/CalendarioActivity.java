package com.example.proyectosql;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectosql.db.DbNotas;

import java.util.HashSet;
import java.util.Set;

/**
 * Clase {@code CalendarioActivity} que gestiona un calendario interactivo
 * con funcionalidad de registro, visualización y edición de notas
 * asociadas a fechas específicas.
 *
 * Esta actividad permite:
 * <ul>
 *   <li>Seleccionar una fecha en el calendario</li>
 *   <li>Mostrar una nota existente o crear una nueva</li>
 *   <li>Guardar o eliminar notas en la base de datos</li>
 * </ul>
 *
 * La persistencia de datos se realiza mediante la clase {@link DbNotas}.
 */
public class CalendarioActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private TextView txtNota;
    private DbNotas dbNotas;
    private Set<String> fechasConNotas;
    private String fechaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        dbNotas = new DbNotas(this);
        calendarView = findViewById(R.id.calendarView);
        txtNota = findViewById(R.id.txtNota);
        fechasConNotas = new HashSet<>();

        cargarFechasConNotas();

        /**
         * Listener que detecta la selección de una fecha en el calendario.
         * Al seleccionar una fecha, se consulta si existe una nota asociada
         * y se muestra un diálogo para visualizar o editar su contenido.
         */
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            fechaSeleccionada = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year);
            String notaExistente = dbNotas.obtenerNota(fechaSeleccionada);

            if (notaExistente != null && !notaExistente.isEmpty()) {
                txtNota.setText("Nota para " + fechaSeleccionada + ":\n" + notaExistente);
            } else {
                txtNota.setText("No hay nota para " + fechaSeleccionada + ".");
            }

            mostrarDialogoNota(fechaSeleccionada);
        });
    }

    /**
     * Carga en memoria las fechas que tienen notas registradas en la base de datos.
     */
    private void cargarFechasConNotas() {
        fechasConNotas.clear();
        fechasConNotas.addAll(dbNotas.obtenerTodasFechas());
    }

    /**
     * Muestra un cuadro de diálogo para crear, editar o eliminar la nota asociada
     * a una fecha específica.
     *
     * @param fecha Fecha seleccionada en formato dd/MM/yyyy.
     */
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
                txtNota.setText("📝 Nota para " + fecha + ":\n" + nota);
                Toast.makeText(this, "Nota guardada", Toast.LENGTH_SHORT).show();
            } else {
                dbNotas.borrarNota(fecha);
                fechasConNotas.remove(fecha);
                txtNota.setText("📅 No hay nota para " + fecha + ".");
                Toast.makeText(this, "Nota eliminada", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());
        builder.show();
    }
}
