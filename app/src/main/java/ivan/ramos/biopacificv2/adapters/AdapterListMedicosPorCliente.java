package ivan.ramos.biopacificv2.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ivan.ramos.biopacificv2.MainActivity;
import ivan.ramos.biopacificv2.R;
import ivan.ramos.biopacificv2.activitys.ServiciosPorMasc;
import ivan.ramos.biopacificv2.activitys.ServiciosPorMedic;
import ivan.ramos.biopacificv2.fragments.MascotasFragment;
import ivan.ramos.biopacificv2.fragments.MedicosFragment;
import ivan.ramos.biopacificv2.models.ListadoMascotasPorVet;
import ivan.ramos.biopacificv2.models.Medicos;
import ivan.ramos.biopacificv2.models.ServiciosXmascotas;
import ivan.ramos.biopacificv2.service.ApiRetrofit;
import ivan.ramos.biopacificv2.service.Services;
import ivan.ramos.biopacificv2.service.comunicFragments;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdapterListMedicosPorCliente extends RecyclerView.Adapter<AdapterListMedicosPorCliente.ViewHolder> implements Filterable {

    private Context context;
    private ArrayList<Medicos> listMedicosPorVet;
    private ArrayList<Medicos> listaTodo;
    ArrayList<ServiciosXmascotas> listServiciosXmasc;
    public static String id, nom;
    private comunicFragments callback;
    private Services services = ApiRetrofit.getRetrofit();
    public AdapterListMedicosPorCliente(Context context, ArrayList<Medicos> listMedicosPorVet) {
        this.context = context;
        //listMedicosPorVet = new ArrayList<>();
        this.listMedicosPorVet = listMedicosPorVet;
        this.listaTodo = new ArrayList<>(listMedicosPorVet);
    }
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        try {
            callback = (comunicFragments)context;
        }catch(Exception e){
            throw new ClassCastException(context.toString());
        }
    }

    @NonNull
    @Override
    public AdapterListMedicosPorCliente.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_medicos,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListMedicosPorCliente.ViewHolder holder, int position) {
        Medicos itemMedicos = listMedicosPorVet.get(position);
        holder.txtDni.setText(itemMedicos.getDni());
        holder.txtMameMedic.setText(itemMedicos.getNombre());
        holder.txtApeMedic.setText(itemMedicos.getApellido());
        holder.txtSexoMedic.setText(itemMedicos.getSexo());
        holder.txtEdadMedic.setText(itemMedicos.getEdad());
        if(itemMedicos.getSexo().equals("Masculino")){
            holder.imgMedic.setImageResource(R.drawable.medico);
        }else {
            holder.imgMedic.setImageResource(R.drawable.medica);
        }

        holder.editMedico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = itemMedicos.getIdMedico();
                callback.editMedicos();
            }
        });
        holder.deleteMedico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = itemMedicos.getIdMedico();
                /*Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.0.14:8091/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Services services =  retrofit.create(Services.class);*/
                Call<Medicos> call = services.deleteMedico(id);
                call.enqueue(new Callback<Medicos>() {
                    @Override
                    public void onResponse(Call<Medicos> call, Response<Medicos> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(context, "Eliminado", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Medicos> call, Throwable t) {
                        Toast.makeText(context, "Falied: "+t, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        holder.cardMedicos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nom = itemMedicos.getNombre()+" "+itemMedicos.getApellido();
                id = itemMedicos.getIdMedico();

                    /*Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://192.168.0.14:8091/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    Services services =  retrofit.create(Services.class);*/
                    Call<List<ServiciosXmascotas>> call = services.getListadoServiPorMedic(id);
                    call.enqueue(new Callback<List<ServiciosXmascotas>>() {
                        @Override
                        public void onResponse(Call<List<ServiciosXmascotas>> call, Response<List<ServiciosXmascotas>> response) {
                            listServiciosXmasc = new ArrayList<>(response.body());
                            if(listServiciosXmasc.size()>0){
                            /*Intent intent = new Intent(context, ServiciosPorMedic.class);
                            context.startActivity(intent);*/
                                Navigation.findNavController(v).navigate(R.id.action_medicosFragment_to_serviciosPorMedic);
                            }else{
                                Toast.makeText(v.getContext(), "No hay servicios", Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<List<ServiciosXmascotas>> call, Throwable t) {
                            Toast.makeText(v.getContext(), "Falied: "+t, Toast.LENGTH_LONG).show();
                        }
                    });
                }

        });
    holder.cardMedicos.setOnLongClickListener(new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            id =itemMedicos.getIdMedico();
            nom =itemMedicos.getApellido();
            return false;
        }
    });
        /*holder.cardMedicos.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (actionMode != null) {
                    return false;
                }
                //MedicosFragment.toolbar.setVisibility(View.INVISIBLE);
                // Start the CAB using the ActionMode.Callback defined above
                actionMode = v.startActionMode(actionModeCallback);
                v.setSelected(true);
                return true;
            }
        });*/

    }
   /* public void addItems(List<Medicos> medicosList){
        listMedicosPorVet.clear();
        listMedicosPorVet.addAll(medicosList);
        notifyDataSetChanged();
    }*/

    @Override
    public int getItemCount() {
        return listMedicosPorVet.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Medicos> filteredList = new ArrayList<>();
            if(constraint.toString().isEmpty()){
                filteredList.addAll(listaTodo);
            }else
            {
                for(Medicos movie :  listaTodo){
                    String patt = constraint.toString().toLowerCase();
                    if(movie.getDni().toLowerCase().contains(patt)||movie.getNombre().toLowerCase().contains(patt)||movie.getApellido().toLowerCase().contains(patt)){
                        filteredList.add(movie);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listMedicosPorVet.clear();
            listMedicosPorVet.addAll((Collection<? extends Medicos>)results.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView txtDni, txtMameMedic,txtApeMedic,txtSexoMedic,txtEdadMedic;
        MaterialButton editMedico,deleteMedico;
        ImageView imgMedic;
        CardView cardMedicos;
        ConstraintLayout cardView_medico;
        MaterialCheckBox checkAction;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMedic = itemView.findViewById(R.id.tvimagen);
            txtDni = itemView.findViewById(R.id.txtDni);
            txtMameMedic = itemView.findViewById(R.id.txtMameMedic);
            txtApeMedic = itemView.findViewById(R.id.txtApeMedic);
            txtSexoMedic = itemView.findViewById(R.id.txtSexoMedic);
            txtEdadMedic = itemView.findViewById(R.id.txtEdadMedic);
            editMedico = itemView.findViewById(R.id.editMedico);
            deleteMedico = itemView.findViewById(R.id.deleteMedico);
            editMedico.setVisibility(View.GONE);
            deleteMedico.setVisibility(View.GONE);
            cardMedicos = itemView.findViewById(R.id.cardMedicos);
            cardView_medico = itemView.findViewById(R.id.cardView_medico);
            checkAction = itemView.findViewById(R.id.checkAction);
            checkAction.setVisibility(View.INVISIBLE);
            cardMedicos.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(this.getAdapterPosition(),R.id.action_edit,0,"Editar");
            menu.add(this.getAdapterPosition(),R.id.action_delete,1,"Eliminar");
        }
    }

   /* private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.actions_bars, menu);
            mode.setTitle("Seleccione un item");
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_delete:
                    callback.editVeterinaria();
                    mode.finish(); // Action picked, so close the CAB
                    return true;
                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
        }
    };*/
}
