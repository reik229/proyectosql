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

/**
 * {@code ListaContactosAdapter} gestiona la representación visual de una lista
 * de objetos {@link Contactos} dentro de un {@link RecyclerView}.
 * <p>
 * Su propósito es vincular los datos de cada contacto con el diseño
 * de la vista individual y permitir la navegación hacia {@link VerActivity}
 * para visualizar los detalles completos del contacto seleccionado.
 */
public class ListaContactosAdapter extends RecyclerView.Adapter<ListaContactosAdapter.ContactoViewHolder> {

    private final ArrayList<Contactos> listaContactos;

    /**
     * Constructor del adaptador.
     *
     * @param listaContactos Lista de contactos a mostrar en el RecyclerView.
     */
    public ListaContactosAdapter(ArrayList<Contactos> listaContactos) {
        this.listaContactos = listaContactos;
    }

    /**
     * Infla el layout de cada elemento de la lista y crea una instancia
     * del {@link ContactoViewHolder}.
     *
     * @param parent   Vista padre que contendrá el layout inflado.
     * @param viewType Tipo de vista (usualmente único en este caso).
     * @return Instancia de {@link ContactoViewHolder}.
     */
    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_item_contacto, parent, false);
        return new ContactoViewHolder(view);
    }

    /**
     * Asocia los datos del objeto {@link Contactos} con las vistas del ViewHolder.
     * Además, configura el evento {@code onClick} para abrir la actividad
     * {@link VerActivity} con los detalles del contacto seleccionado.
     *
     * @param holder   Instancia del {@link ContactoViewHolder}.
     * @param position Posición del elemento dentro de la lista.
     */
    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder holder, int position) {
        Contactos contacto = listaContactos.get(position);

        holder.viewNombre.setText(contacto.getNombre());
        holder.viewTelefono.setText(contacto.getTelefono());
        holder.viewCorreo.setText(contacto.getCorreoElectronico());

        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, VerActivity.class);
            intent.putExtra("ID", contacto.getId());
            context.startActivity(intent);
        });
    }

    /**
     * Devuelve la cantidad total de elementos en la lista.
     *
     * @return Número de contactos registrados.
     */
    @Override
    public int getItemCount() {
        return listaContactos.size();
    }

    /**
     * {@code ContactoViewHolder} contiene las referencias a los elementos
     * visuales que representan los atributos de un contacto.
     * Se utiliza para optimizar el rendimiento de la lista.
     */
    public static class ContactoViewHolder extends RecyclerView.ViewHolder {

        TextView viewNombre, viewTelefono, viewCorreo;

        /**
         * Constructor que inicializa las referencias a las vistas del layout.
         *
         * @param itemView Vista correspondiente a un elemento del RecyclerView.
         */
        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);
            viewNombre = itemView.findViewById(R.id.viewNombre);
            viewTelefono = itemView.findViewById(R.id.viewTelefon); // Verificar ID correcto en XML
            viewCorreo = itemView.findViewById(R.id.viewCorreo);
        }
    }
}
