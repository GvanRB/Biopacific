package ivan.ramos.biopacificv2.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ivan.ramos.biopacificv2.R;
import ivan.ramos.biopacificv2.models.ServiciosXmascotas;

public class AdapterServiciosXmedico extends RecyclerView.Adapter<AdapterServiciosXmedico.ViewHolder> implements Filterable {
    private Context context;
    ArrayList<ServiciosXmascotas> listServiciosXMasc;
    ArrayList<ServiciosXmascotas> listaTodo;
    public static  String ordenid, result;
    private static String BASE_URL = "http://10.0.2.2:5000/";

    public AdapterServiciosXmedico(Context context, ArrayList<ServiciosXmascotas> listServiciosXMasc) {
        this.context = context;
        this.listServiciosXMasc = listServiciosXMasc;
        this.listaTodo = new ArrayList<>(listServiciosXMasc);
    }

    @NonNull
    @Override
    public AdapterServiciosXmedico.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_serviciosxmedic,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterServiciosXmedico.ViewHolder holder, int position) {
        final ServiciosXmascotas itemServiciosxMasc=listServiciosXMasc.get(position);
        holder.txtPerfil.setText(itemServiciosxMasc.getNombrePerfil());
        holder.txtMascota.setText(itemServiciosxMasc.getNombreMascota());

        if(itemServiciosxMasc.getEstadoOrden()==1){
            holder.Estadotxt.setText("Solicitado");
            holder.Estadotxt.setTextColor(Color.parseColor("#0077D6"));
        }else if(itemServiciosxMasc.getEstadoOrden()==3){
            holder.Estadotxt.setText("Finalizado");
            holder.Estadotxt.setTextColor(Color.parseColor("#4CAF50"));
        }else {
            holder.Estadotxt.setText("Cancelado");
            holder.Estadotxt.setTextColor(Color.parseColor("#F44336"));
        }
        holder.costotxt.setText(itemServiciosxMasc.getCostoPerfil().toString());
        holder.Fechatxt.setText(itemServiciosxMasc.getFechaRegistroOrden());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AdapterServiciosXmascota.ordenid = BASE_URL+"api/Orden/OrdenPdf/"+itemServiciosxMasc.getIdOrden();
                AdapterServiciosXmascota.result =BASE_URL+"api/Resultado/ResultadoPdf/"+itemServiciosxMasc.getIdOrden();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return listServiciosXMasc.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ServiciosXmascotas> filteredList = new ArrayList<>();
            if(constraint.toString().isEmpty()){
                filteredList.clear();
                filteredList.addAll(listaTodo);

            }else{
                String patt = constraint.toString().toLowerCase().trim();
                for(ServiciosXmascotas item :  listaTodo){
                    if(item.getFechaRegistroOrden().toLowerCase().contains(patt)||item.getNombreMascota().toLowerCase().contains(patt)
                            ||item.getIdOrden().toLowerCase().contains(patt)
                            ||item.getNombrePerfil().toLowerCase().contains(patt)){
                        filteredList.add(item);
                    }/*else {
                        Toast.makeText(context, "No se encuentran resultados", Toast.LENGTH_SHORT).show();
                    }*/
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listServiciosXMasc.clear();
            listServiciosXMasc.addAll((Collection<? extends ServiciosXmascotas>)results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        TextView txtPerfil,Estadotxt,txtMascota,costotxt,Fechatxt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPerfil = itemView.findViewById(R.id.txtPerfil);
            Estadotxt = itemView.findViewById(R.id.Estadotxt);
            txtMascota = itemView.findViewById(R.id.txtMascota);
            costotxt = itemView.findViewById(R.id.costotxt);
            Fechatxt = itemView.findViewById(R.id.Fechatxt);
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            if(Estadotxt.getText().equals("Solicitado")){
                menu.add(getAdapterPosition(),101,0,"Ver comprobante");
            }else if(Estadotxt.getText().equals("Finalizado")){
                menu.add(getAdapterPosition(),101,0,"Ver comprobante");
                menu.add(getAdapterPosition(),102,1,"Ver Resultados");
            }else if (Estadotxt.getText().equals("Cancelado")){
                Toast.makeText(context, "Servicio Cancelado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
