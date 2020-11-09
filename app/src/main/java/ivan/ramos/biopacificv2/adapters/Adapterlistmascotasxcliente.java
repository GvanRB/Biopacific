package ivan.ramos.biopacificv2.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ivan.ramos.biopacificv2.R;
//import ivan.ramos.biopacificv2.ServiciosMascota;
import ivan.ramos.biopacificv2.ServiciosFragment;
import ivan.ramos.biopacificv2.ServiciospetsFragment;
import ivan.ramos.biopacificv2.models.ListadoMascotasPorCliente;
import ivan.ramos.biopacificv2.prueba;

public class Adapterlistmascotasxcliente extends RecyclerView.Adapter<ivan.ramos.biopacificv2.adapters.Adapterlistmascotasxcliente.ViewHolder> {
    public static String idmascota;
    private Context context;
    ArrayList<ListadoMascotasPorCliente> listadoMascotasPorClientes;


    public Adapterlistmascotasxcliente(Context context, ArrayList<ListadoMascotasPorCliente> listadoMascotasPorClientes) {
        this.context = context;
        this.listadoMascotasPorClientes = listadoMascotasPorClientes;
    }

    @NonNull
    @Override
    public ivan.ramos.biopacificv2.adapters.Adapterlistmascotasxcliente.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mascotas,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ivan.ramos.biopacificv2.adapters.Adapterlistmascotasxcliente.ViewHolder holder, int position) {
        final ListadoMascotasPorCliente itemsmascotas=listadoMascotasPorClientes.get(position);
        holder.idMascota.setText(itemsmascotas.getIdMascota()+"");
        holder.nombre.setText(itemsmascotas.getNombre()+"");
        holder.genero.setText(itemsmascotas.getGenero()+"");
        holder.raza.setText(itemsmascotas.getRaza()+"");
        holder.especie.setText(itemsmascotas.getEspecie()+"");
        holder.cardMascotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idmascota=itemsmascotas.getIdMascota();

               /* ServiciospetsFragment pets = new ServiciospetsFragment();

                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                ServiciospetsFragment serpets= new ServiciospetsFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.prueba,serpets).addToBackStack(null).commit();
                */
                Intent intent = new Intent(context, prueba.class);
                idmascota = itemsmascotas.getIdMascota();
                context.startActivity(intent);
                //Toast.makeText(context, itemsmascotas.getIdMascota(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listadoMascotasPorClientes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre,idMascota,genero,raza,especie;
        CardView cardMascotas;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idMascota=itemView.findViewById(R.id.idMascota);
            nombre=itemView.findViewById(R.id.nombre);
            genero=itemView.findViewById(R.id.genero);
            raza=itemView.findViewById(R.id.raza);
            especie=itemView.findViewById(R.id.especie);
            cardMascotas=itemView.findViewById(R.id.cardMascotas);
        }
    }
}
