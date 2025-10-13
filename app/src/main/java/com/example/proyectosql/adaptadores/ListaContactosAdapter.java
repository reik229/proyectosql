package com.example.proyectosql.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectosql.R;
import com.example.proyectosql.VerActivity;
import com.example.proyectosql.entidades.Contactos;

import java.util.ArrayList;

public class ListaContactosAdapter extends RecyclerView.Adapter<ListaContactosAdapter.ContactoViewHolder> {

    private ArrayList<Contactos> listaContactos;

    public ListaContactosAdapter(ArrayList<Contactos> listaContactos) {
        this.listaContactos = listaContactos;
    }

    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar correctamente el layout sin perder las propiedades de tamaño
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_item_contacto, parent, false);
        return new ContactoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder holder, int position) {
        Contactos contacto = listaContactos.get(position);

        holder.viewNombre.setText(contacto.getNombre());
        holder.viewTelefono.setText(contacto.getTelefono());
        holder.viewCorreo.setText(contacto.getCorreoElectronico());

        // Efecto visual y acción al hacer clic
        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, VerActivity.class);
            intent.putExtra("ID", contacto.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listaContactos.size();
    }

    public static class ContactoViewHolder extends RecyclerView.ViewHolder {

        TextView viewNombre, viewTelefono, viewCorreo;

        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);
            viewNombre = itemView.findViewById(R.id.viewNombre);
            viewTelefono = itemView.findViewById(R.id.viewTelefon);
            viewCorreo = itemView.findViewById(R.id.viewCorreo);
        }
    }
}
