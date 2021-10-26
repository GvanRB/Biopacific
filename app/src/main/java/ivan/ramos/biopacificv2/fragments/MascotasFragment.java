package ivan.ramos.biopacificv2.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import ivan.ramos.biopacificv2.activitys.Login;
import ivan.ramos.biopacificv2.R;
import ivan.ramos.biopacificv2.activitys.PdfTarifario;
import ivan.ramos.biopacificv2.activitys.SimpleBlueDivider;
import ivan.ramos.biopacificv2.adapters.AdapterListmascotasPorCliente;
import ivan.ramos.biopacificv2.models.ListadoMascotasPorVet;
import ivan.ramos.biopacificv2.models.Mascotas;
import ivan.ramos.biopacificv2.service.ApiRetrofit;
import ivan.ramos.biopacificv2.service.Services;
import ivan.ramos.biopacificv2.service.comunicFragments;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;


public class MascotasFragment extends Fragment {
    public static String idmascota;
    public static FloatingActionButton addpets;
    private RecyclerView Recycler;
    private AdapterListmascotasPorCliente adapter;
    ArrayList<ListadoMascotasPorVet> listmascota;
    private comunicFragments callback;
    public static TextView txtAction;
    public static Toolbar toolbar;
    public static MaterialButton btnBack;
    public static List<ListadoMascotasPorVet> seleccionados = new ArrayList<>();
    public Menu menuGuardar;
    public MenuInflater menuInflaterGuardar;
    MaterialButton btm;
    private Services services = ApiRetrofit.getRetrofit();

    public MascotasFragment() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            callback = (comunicFragments)context;

        }catch(Exception e){
            throw new ClassCastException(context.toString());
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mascotas, container, false);
        toolbar = view.findViewById(R.id.toolbarMasc);
        txtAction = view.findViewById(R.id.txtAction);
        btnBack = view.findViewById(R.id.btnBack);
        txtAction.setVisibility(View.GONE);
        btnBack.setVisibility(View.GONE);
        AppCompatActivity activity =(AppCompatActivity) getActivity();
        //toolbar.setBackgroundResource(R.drawable.toolbar);
        activity.setSupportActionBar(toolbar);
        seleccionados.clear();
        //activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Recycler = view.findViewById(R.id.Recycler);
        //adapter = new AdapterListmascotasPorCliente(getActivity(),listmascota);
        //DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        //dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recycler_divider));
        Recycler.addItemDecoration(new SimpleBlueDivider(getContext()));
        Recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        //Recycler.setAdapter(adapter);
        addpets = view.findViewById(R.id.addpets);
        addpets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.addMascota();
            }
        });
        SharedPreferences preferences = getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        Mascotasxcliente(preferences.getString("idVeterinaria",""));
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar.getMenu().clear();
                txtAction.setVisibility(View.GONE);
                btnBack.setVisibility(View.GONE);
                seleccionados.clear();
                toolbar.setTitle("BioPacific");
                SharedPreferences preferences = getActivity().getSharedPreferences("Login", MODE_PRIVATE);
                Mascotasxcliente(preferences.getString("idVeterinaria",""));
                onCreateOptionsMenu(menuGuardar,menuInflaterGuardar);
            }
        });

        return view;
    }


    private void Mascotasxcliente(final String idVeterinaria) {
        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Services services =  retrofit.create(Services.class);*/
        Call<List<ListadoMascotasPorVet>> call = services.postListMascXCli(idVeterinaria);
        call.enqueue(new Callback<List<ListadoMascotasPorVet>>() {
            @Override
            public void onResponse(Call<List<ListadoMascotasPorVet>> call, Response<List<ListadoMascotasPorVet>> response) {
                if(response.isSuccessful()){
                    listmascota = new ArrayList<>(response.body());
                    adapter = new AdapterListmascotasPorCliente(getContext(),listmascota);
                    adapter.notifyDataSetChanged();
                    Recycler.setAdapter(adapter);
                }else {
                    Log.e("TagError", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<List<ListadoMascotasPorVet>> call, Throwable t) {
                Toast.makeText(getContext(), "Falied: "+t, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.filter_recycler,menu);
        menuGuardar = menu;
        menuInflaterGuardar = inflater;
        MenuItem item = menu.findItem(R.id.filterRecycler);
        SearchView searchView = (SearchView) item.getActionView();
        /*ImageView btm = searchView.findViewById(R.id.search_close_btn);
        btm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBack.performClick();
            }
        });*/
        searchView.setQueryHint("Buscar mascota...");
        SearchView.SearchAutoComplete theTextArea = searchView.findViewById(R.id.search_src_text);
        theTextArea.setTextColor(getResources().getColor(R.color.white));
        theTextArea.setHintTextColor(getResources().getColor(R.color.hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.verPerfil:
                callback.editVeterinaria();
                //Toast.makeText(this, ""+idveteri, Toast.LENGTH_SHORT).show();
                break;
            case R.id.CerrarSession:
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("Login", MODE_PRIVATE).edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(getActivity(), Login.class));
                getActivity().onBackPressed();
                break;
            case R.id.verServicios:
                startActivity(new Intent(getActivity(), PdfTarifario.class));
                break;
            case R.id.action_delete:
                query();
                break;
            case R.id.action_edit:
                if(MascotasFragment.seleccionados.size()>1){
                    Toast.makeText(getActivity(), "Selecciona 1", Toast.LENGTH_SHORT).show();

                }else{
                    AdapterListmascotasPorCliente.idmascota = seleccionados.get(0).getIdMascota();
                    callback.editMascota();
                }
        }
        return true;
    }

    private void query() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_query, null);
        builder.setView(view);

        if(seleccionados.size()==1){
            ((TextView) view.findViewById(R.id.textTitle)).setText("¿Eliminar a la mascota "+seleccionados.get(0).getNombre()+"?");
            ((TextView) view.findViewById(R.id.textMessage)).setText("Se eliminará los servicios solicitados de esta mascota");
        }else{
            ((TextView) view.findViewById(R.id.textTitle)).setText("¿Eliminar "+seleccionados.size()+" mascotas seleccionadas?");
            ((TextView) view.findViewById(R.id.textMessage)).setText("Se eliminará los servicios solicitados de estas mascotas");
        }

        ((MaterialButton) view.findViewById(R.id.btnNO)).setText("Cancelar");
        ((MaterialButton) view.findViewById(R.id.btnSi)).setText("Eliminar");
        ((ImageView) view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.ic_warning);
        final AlertDialog alertDialog = builder.show();
        //alertDialog.setCancelable(false);
        view.findViewById(R.id.btnSi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(ListadoMascotasPorVet itemMascota : seleccionados){
                   /* Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://192.168.0.14:8091/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    Services services =  retrofit.create(Services.class);*/
                    if(seleccionados.size()==1){
                        Toast.makeText(getActivity(), "Mascota eliminado", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(), +seleccionados.size()+" Mascotas eliminados", Toast.LENGTH_SHORT).show();
                    }
                    Call<Mascotas> call = services.deleteMascota(itemMascota.getIdMascota());
                    call.enqueue(new Callback<Mascotas>() {
                        @Override
                        public void onResponse(Call<Mascotas> call, Response<Mascotas> response) {
                            if(response.isSuccessful()){
                            }
                        }
                        @Override
                        public void onFailure(Call<Mascotas> call, Throwable t) {
                            Toast.makeText(getActivity(), "Falied: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                alertDialog.dismiss();
                btnBack.performClick();
            }
        });view.findViewById(R.id.btnNO).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        if(alertDialog.getWindow()!=null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();
    }


}