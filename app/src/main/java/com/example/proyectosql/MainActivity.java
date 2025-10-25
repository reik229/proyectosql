package com.example.proyectosql;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectosql.adaptadores.ListaContactosAdapter;
import com.example.proyectosql.db.DbContactos;
import com.example.proyectosql.entidades.Contactos;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView listaContactos;
    ArrayList<Contactos> listaArrayContactos;
    ListaContactosAdapter adapter;
    DbContactos dbContactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaContactos = findViewById(R.id.listaContactos);
        listaContactos.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar base de datos
        dbContactos = new DbContactos(this);

        // Obtener lista de contactos
        listaArrayContactos = new ArrayList<>();
        listaArrayContactos.addAll(dbContactos.mostrarContactos());

        // Configurar adaptador
        adapter = new ListaContactosAdapter(listaArrayContactos);
        listaContactos.setAdapter(adapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();

        // Actualizar la lista de contactos al volver de NuevoActivity
        listaArrayContactos.clear();
        listaArrayContactos.addAll(dbContactos.mostrarContactos());
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuNuevo) {
            nuevoRegistro();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void nuevoRegistro() {
        Intent intent = new Intent(this, NuevoActivity.class);
        startActivity(intent);
    }
}
