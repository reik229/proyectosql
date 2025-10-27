package com.example.proyectosql;

// Importaciones necesarias para UI, intents y RecyclerView
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity; // Clase base para actividades con ActionBar
import androidx.recyclerview.widget.LinearLayoutManager; // LayoutManager lineal para RecyclerView
import androidx.recyclerview.widget.RecyclerView; // Componente para listas dinámicas

import com.example.proyectosql.adaptadores.ListaContactosAdapter; // Adaptador personalizado para mostrar contactos
import com.example.proyectosql.db.DbContactos; // Clase de acceso a la base de datos de contactos
import com.example.proyectosql.entidades.Contactos; // Modelo de datos Contactos

import java.util.ArrayList; // Estructura de datos dinámica para almacenar contactos

public class MainActivity extends AppCompatActivity {

    // Declaración de variables de UI y datos
    RecyclerView listaContactos; // RecyclerView para mostrar la lista de contactos
    ArrayList<Contactos> listaArrayContactos; // ArrayList para almacenar objetos Contactos
    ListaContactosAdapter adapter; // Adaptador para conectar datos con RecyclerView
    DbContactos dbContactos; // Instancia de la clase de base de datos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Inflar el layout principal

        // Inicialización del RecyclerView y asignación de LayoutManager lineal
        listaContactos = findViewById(R.id.listaContactos);
        listaContactos.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar la base de datos de contactos
        dbContactos = new DbContactos(this);

        // Obtener la lista de contactos desde la base de datos
        listaArrayContactos = new ArrayList<>();
        listaArrayContactos.addAll(dbContactos.mostrarContactos());

        // Configurar el adaptador con la lista de contactos y asignarlo al RecyclerView
        adapter = new ListaContactosAdapter(listaArrayContactos);
        listaContactos.setAdapter(adapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();

        // Actualizar la lista de contactos cada vez que se retorne a MainActivity
        // desde otra actividad (ej. NuevoActivity)
        listaArrayContactos.clear(); // Limpiar lista actual
        listaArrayContactos.addAll(dbContactos.mostrarContactos()); // Re-cargar datos desde la base
        adapter.notifyDataSetChanged(); // Notificar al adaptador que los datos cambiaron
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater(); // Inflar menú desde XML
        inflater.inflate(R.menu.menu_principal, menu); // Cargar menú principal
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Manejo de selección de ítems del menú
        if (item.getItemId() == R.id.menuNuevo) { // Si se selecciona "Nuevo"
            nuevoRegistro(); // Llamar método para abrir actividad de nuevo registro
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Método privado para abrir la actividad NuevoActivity mediante intent
    private void nuevoRegistro() {
        Intent intent = new Intent(this, NuevoActivity.class);
        startActivity(intent);
    }
}
