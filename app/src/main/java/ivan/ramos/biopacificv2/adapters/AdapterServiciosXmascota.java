package ivan.ramos.biopacificv2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ivan.ramos.biopacificv2.R;
import ivan.ramos.biopacificv2.models.ServiciosXmascotas;

public class AdapterServiciosXmascota extends RecyclerView.Adapter<ivan.ramos.biopacificv2.adapters.AdapterServiciosXmascota.ViewHolder> {

    private Context context;
    ArrayList<ServiciosXmascotas> listServiciosXMasc;

    public AdapterServiciosXmascota(Context context, ArrayList<ServiciosXmascotas> listServiciosXMasc) {
        this.context = context;
        this.listServiciosXMasc = listServiciosXMasc;
    }

    @NonNull
    @Override
    public ivan.ramos.biopacificv2.adapters.AdapterServiciosXmascota.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_serviciosxmasc,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ivan.ramos.biopacificv2.adapters.AdapterServiciosXmascota.ViewHolder holder, int position) {
        final ServiciosXmascotas itemServiciosxMasc=listServiciosXMasc.get(position);
        holder.Mascotatxt.setText(itemServiciosxMasc.getNombreMascota()+"");
        holder.Perfiltxt.setText(itemServiciosxMasc.getNombre()+"");
        holder.Estadotxt.setText(itemServiciosxMasc.getEstado()+"");
        holder.costotxt.setText(itemServiciosxMasc.getCosto()+"");
        holder.Fechatxt.setText(itemServiciosxMasc.getFechaRegistro()+"");
    }

    @Override
    public int getItemCount() {
        return listServiciosXMasc.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView Mascotatxt,Perfiltxt,Estadotxt,costotxt,Fechatxt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Mascotatxt = itemView.findViewById(R.id.Mascotatxt);
            Perfiltxt = itemView.findViewById(R.id.Perfiltxt);
            Estadotxt = itemView.findViewById(R.id.Estadotxt);
            costotxt = itemView.findViewById(R.id.costotxt);
            Fechatxt = itemView.findViewById(R.id.Fechatxt);

        }
    }
}
