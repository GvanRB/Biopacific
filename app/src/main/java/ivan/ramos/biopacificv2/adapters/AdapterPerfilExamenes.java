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
import ivan.ramos.biopacificv2.models.PerfilExamenes;

public class AdapterPerfilExamenes extends RecyclerView.Adapter<AdapterPerfilExamenes.ViewHolder> {
    private Context context;
    private ArrayList<PerfilExamenes> listPerfilExamenes;

    public AdapterPerfilExamenes(Context context, ArrayList<PerfilExamenes> listPerfilExamenes) {
        this.context = context;
        this.listPerfilExamenes = listPerfilExamenes;
    }

    @NonNull
    @Override
    public AdapterPerfilExamenes.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_perfil_examenes,parent,false);
        return new AdapterPerfilExamenes.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPerfilExamenes.ViewHolder holder, int position) {
            final PerfilExamenes itemperfilexamenes = listPerfilExamenes.get(position);
            holder.txtPerfilExamenes.setText(itemperfilexamenes.getNombreExamen()+" - "+itemperfilexamenes.getTipoExamen());
    }

    @Override
    public int getItemCount() {
        return listPerfilExamenes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtPerfilExamenes;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPerfilExamenes = itemView.findViewById(R.id.txtPerfilExamenes);
        }
    }
}
