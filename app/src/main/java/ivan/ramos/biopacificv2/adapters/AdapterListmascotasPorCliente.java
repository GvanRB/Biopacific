package ivan.ramos.biopacificv2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import ivan.ramos.biopacificv2.R;
import ivan.ramos.biopacificv2.fragments.MascotasFragment;
import ivan.ramos.biopacificv2.models.ListadoMascotasPorVet;
import ivan.ramos.biopacificv2.models.Mascotas;
import ivan.ramos.biopacificv2.models.ServiciosXmascotas;
import ivan.ramos.biopacificv2.service.ApiRetrofit;
import ivan.ramos.biopacificv2.service.Services;
import ivan.ramos.biopacificv2.service.comunicFragments;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AdapterListmascotasPorCliente extends RecyclerView.Adapter<AdapterListmascotasPorCliente.ViewHolder> implements Filterable  {
    public static String idmascota, nomMas, nomPro;
    private Context context;
    private ArrayList<ListadoMascotasPorVet> listadoMascotasPorVets;
    private ArrayList<ListadoMascotasPorVet> listaTodo;
    ArrayList<ServiciosXmascotas> listServiciosXmasc;
    private comunicFragments callback;
    private Services services = ApiRetrofit.getRetrofit();

    public AdapterListmascotasPorCliente(Context context, ArrayList<ListadoMascotasPorVet> listadoMascotasPorVets) {
        this.context = context;
        //listadoMascotasPorVets = new ArrayList<>();
        Collections.reverse(listadoMascotasPorVets);
        this.listadoMascotasPorVets = listadoMascotasPorVets;
        this.listaTodo = new ArrayList<>(listadoMascotasPorVets);

        // this.listadoMascotasPorVets = listadoMascotasPorVets;
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
    public AdapterListmascotasPorCliente.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mascotas,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterListmascotasPorCliente.ViewHolder holder, int position) {
        final ListadoMascotasPorVet itemsmascotas= listadoMascotasPorVets.get(position);
        holder.idMascota.setText(itemsmascotas.getIdMascota()+"");
        holder.nombre.setText(itemsmascotas.getNombre()+"");
        holder.genero.setText(itemsmascotas.getGenero()+"");
        holder.raza.setText(itemsmascotas.getRaza()+"");
        holder.especie.setText(itemsmascotas.getEspecie()+"");

        if(itemsmascotas.getEspecie().equals("Canino")){
            holder.imgEspecie.setImageResource(R.drawable.caninocircle);
            //holder.cardView_mascota.setBackgroundResource(R.drawable.cardview_background_canino);
        }else {
            holder.imgEspecie.setImageResource(R.drawable.felinocircle);
            //holder.cardView_mascota.setBackgroundResource(R.drawable.cardview_background_gato);
        }
        holder.cardMascotas.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                idmascota = itemsmascotas.getIdMascota();
                MascotasFragment.toolbar.getMenu().clear();
                MascotasFragment.toolbar.inflateMenu(R.menu.actions_bars);
                MascotasFragment.toolbar.setVisibility(View.VISIBLE);
                MascotasFragment.txtAction.setVisibility(View.VISIBLE);
                MascotasFragment.btnBack.setVisibility(View.VISIBLE);
                MascotasFragment.toolbar.setTitle("");
                if(MascotasFragment.seleccionados.size()==0){
                    MascotasFragment.seleccionados.add(itemsmascotas);
                    holder.checkAction.setVisibility(View.VISIBLE);
                    holder.checkAction.setChecked(true);
                    holder.cardView_mascota.setBackgroundResource(R.drawable.back_card);
                    MascotasFragment.txtAction.setText(MascotasFragment.seleccionados.size()+" Mascota selccionada");
                }

                //idmascota = MascotasFragment.seleccionados.get(0).getIdMascota();
                return true;
            }

        });
        holder.propietario.setText(itemsmascotas.getNombrePropietario()+"");
        holder.cardMascotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nomMas = itemsmascotas.getNombre();
                nomPro = itemsmascotas.getNombrePropietario();
                idmascota = itemsmascotas.getIdMascota();
                if(MascotasFragment.seleccionados.size()>0){
                    holder.checkAction.setVisibility(View.VISIBLE);
                    if(!holder.checkAction.isChecked()){
                        holder.checkAction.setChecked(true);
                        holder.cardView_mascota.setBackgroundResource(R.drawable.back_card);
                        MascotasFragment.seleccionados.add(itemsmascotas);
                        if (MascotasFragment.seleccionados.size()==1){
                            MascotasFragment.txtAction.setText(MascotasFragment.seleccionados.size()+" Mascota seleccionada");
                        }else{
                            MascotasFragment.txtAction.setText(MascotasFragment.seleccionados.size()+" Mascotas selccionadas");
                        }
                    }else{
                        holder.checkAction.setChecked(false);
                        holder.cardView_mascota.setBackgroundResource(R.drawable.cardview_background_canino);
                      /* if(itemsmascotas.getEspecie().equals("Canino")){
                           holder.cardView_mascota.setBackgroundResource(R.drawable.cardview_background_canino);
                       }else{
                           holder.cardView_mascota.setBackgroundResource(R.drawable.cardview_background_gato);
                       }*/
                        holder.checkAction.setVisibility(View.INVISIBLE);
                        MascotasFragment.seleccionados.remove(itemsmascotas);
                        if(MascotasFragment.seleccionados.size()==0){
                            MascotasFragment.btnBack.performClick();
                        }else if (MascotasFragment.seleccionados.size()==1){
                            MascotasFragment.txtAction.setText(MascotasFragment.seleccionados.size()+" Mascota seleccionada");
                        }else {
                            MascotasFragment.txtAction.setText(MascotasFragment.seleccionados.size()+" Mascotas selccionadas");
                        }
                    }

                }else{
                    /*Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://192.168.0.14:8091/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    Services services =  retrofit.create(Services.class);*/
                    Call<List<ServiciosXmascotas>> call = services.getListadoServiPorMasc(idmascota);
                    call.enqueue(new Callback<List<ServiciosXmascotas>>() {
                        @Override
                        public void onResponse(Call<List<ServiciosXmascotas>> call, Response<List<ServiciosXmascotas>> response) {
                            listServiciosXmasc = new ArrayList<>(response.body());
                            if(listServiciosXmasc.size()>0){
                                Navigation.findNavController(v).navigate(R.id.action_mascotasFragment_to_serviciosPorMasc);
                            }else{
                                Toast.makeText(v.getContext(), "No tiene servicios", Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<List<ServiciosXmascotas>> call, Throwable t) {
                            Toast.makeText(v.getContext(), "Falied: "+t, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
        holder.editMasc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MascotasFragment.seleccionados.clear();
                idmascota = itemsmascotas.getIdMascota();
                callback.editMascota();

            }
        });
        holder.deleteMasc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idmascota=itemsmascotas.getIdMascota();
                /*Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.0.14:8091/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Services services =  retrofit.create(Services.class);*/
                Call<Mascotas> call = services.deleteMascota(idmascota);
                call.enqueue(new Callback<Mascotas>() {
                    @Override
                    public void onResponse(Call<Mascotas> call, Response<Mascotas> response) {
                        if(response.isSuccessful()){
                            MascotasFragment.seleccionados.clear();
                            Toast.makeText(context, "Eliminado correctamente", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Mascotas> call, Throwable t) {
                        Toast.makeText(context, "Falied: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    /*public void addItems(List<ListadoMascotasPorVet> listMascPorVet){
        listadoMascotasPorVets.clear();
        listadoMascotasPorVets.addAll(listMascPorVet);
        notifyDataSetChanged();
    }*/


    @Override
    public int getItemCount() {
        return listadoMascotasPorVets.size();
    }


    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ListadoMascotasPorVet> filteredList = new ArrayList<>();
            if(constraint.toString().isEmpty()){
                filteredList.clear();
                filteredList.addAll(listadoMascotasPorVets);

            }else{
                String patt = constraint.toString().toLowerCase().trim();
                for(ListadoMascotasPorVet item :  listaTodo){
                    if(item.getNombre().toLowerCase().contains(patt)||item.getNombrePropietario().toLowerCase().contains(patt)||item.getIdMascota().toLowerCase().contains(patt)){
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
            listadoMascotasPorVets.clear();
            listadoMascotasPorVets.addAll((Collection<? extends ListadoMascotasPorVet>)results.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre,idMascota,genero,raza,especie,propietario;
        CardView cardMascotas;
        ConstraintLayout cardView_mascota;
        de.hdodenhof.circleimageview.CircleImageView imgEspecie;
        MaterialButton editMasc,deleteMasc;
        MaterialCheckBox checkAction;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgEspecie = itemView.findViewById(R.id.tvimagen);
            idMascota=itemView.findViewById(R.id.idMascota);
            nombre=itemView.findViewById(R.id.txtMameMedic);
            genero=itemView.findViewById(R.id.genero);
            raza=itemView.findViewById(R.id.raza);
            especie=itemView.findViewById(R.id.especie);
            propietario=itemView.findViewById(R.id.propietario);
            cardMascotas=itemView.findViewById(R.id.cardMascotas);
            editMasc=itemView.findViewById(R.id.editMasc);
            deleteMasc = itemView.findViewById(R.id.deleteMasc);
            editMasc.setVisibility(View.GONE);
            deleteMasc.setVisibility(View.GONE);
            cardView_mascota = itemView.findViewById(R.id.cardView_mascota);
            checkAction = itemView.findViewById(R.id.checkAction);
            checkAction.setVisibility(View.INVISIBLE);

        }
    }

}
