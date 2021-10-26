package ivan.ramos.biopacificv2.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import ivan.ramos.biopacificv2.R;
import ivan.ramos.biopacificv2.activitys.Login;
import ivan.ramos.biopacificv2.activitys.PdfTarifario;
import ivan.ramos.biopacificv2.activitys.SimpleBlueDivider;
import ivan.ramos.biopacificv2.adapters.AdapterListMedicosPorCliente;
import ivan.ramos.biopacificv2.models.ListadoMascotasPorVet;
import ivan.ramos.biopacificv2.models.Medicos;
import ivan.ramos.biopacificv2.service.ApiRetrofit;
import ivan.ramos.biopacificv2.service.Services;
import ivan.ramos.biopacificv2.service.comunicFragments;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;


public class MedicosFragment extends Fragment {
     private RecyclerView recyclerMedicos;
     private AdapterListMedicosPorCliente adapter;
     public static ArrayList<Medicos> lstMedicos;
     //private Services services;
     //public static List<Medicos> seleccionados = new ArrayList<>();
     private Retrofit retrofit;
     private FloatingActionButton addMedicos;
     private comunicFragments callback;
     public static Toolbar toolbar;
     public static MaterialButton refrescar;
     private Services services = ApiRetrofit.getRetrofit();
    public MedicosFragment() {
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            callback = (comunicFragments)context;

        }catch (Exception e){
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
        View view = inflater.inflate(R.layout.fragment_medicos, container, false);
         toolbar = view.findViewById(R.id.toolbarMedicos);
        AppCompatActivity activity = (AppCompatActivity)getActivity();
        activity.setSupportActionBar(toolbar);
        refrescar = view.findViewById(R.id.refrescar);
        recyclerMedicos = view.findViewById(R.id.recyclerMedicos);
        recyclerMedicos.addItemDecoration(new SimpleBlueDivider(getContext()));
        //adapter = new AdapterListMedicosPorCliente(getActivity(),lstMedicos);
        //retrofit = new Retrofit.Builder().baseUrl("http://192.168.0.14:8091/").addConverterFactory(GsonConverterFactory.create()).build();
        recyclerMedicos.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerMedicos.setAdapter(adapter);
        //services = retrofit.create(Services.class);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        MedicosPorCliente(sharedPreferences.getString("idVeterinaria",""));
        refrescar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getActivity().getSharedPreferences("Login", MODE_PRIVATE);
                MedicosPorCliente(preferences.getString("idVeterinaria",""));
            }
        });
        addMedicos=view.findViewById(R.id.addMedicos);
        addMedicos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.addMedicos();
            }
        });
        return view;
    }

    private void MedicosPorCliente(String idVeterinaria) {
        Call<List<Medicos>> itemsMascotas = services.postListMediXVet(idVeterinaria);
        itemsMascotas.enqueue(new Callback<List<Medicos>>() {
            @Override
            public void onResponse(Call<List<Medicos>> call, Response<List<Medicos>> response) {
                if(response.isSuccessful()){
                   /* lstMedicos = response.body();
                    adapter.addItems(lstMedicos);*/
                    lstMedicos = new ArrayList<>(response.body());
                    adapter = new AdapterListMedicosPorCliente(getActivity(),lstMedicos);
                    recyclerMedicos.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }else {
                    Log.e("TagError", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<List<Medicos>> call, Throwable t) {

            }
        });
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.filter_recycler,menu);
        MenuItem item = menu.findItem(R.id.filterRecycler);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Buscar medico...");
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
        }
        return true;
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                callback.editMedicos();
                break;
            case R.id.action_delete:
                query();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void query() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Se eliminará todos los servicios solicitados de este Dr.")
                .setTitle("¿Eliminar al Dr. "+AdapterListMedicosPorCliente.nom+"?");
        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                /*Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.0.14:8091/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Services services =  retrofit.create(Services.class);*/
                Call<Medicos> call = services.deleteMedico(AdapterListMedicosPorCliente.id);
                call.enqueue(new Callback<Medicos>() {
                    @Override
                    public void onResponse(Call<Medicos> call, Response<Medicos> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(getActivity(), "Eliminado", Toast.LENGTH_SHORT).show();
                            refrescarView();
                        }
                    }
                    @Override
                    public void onFailure(Call<Medicos> call, Throwable t) {
                        Toast.makeText(getActivity(), "Falied: "+t, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void refrescarView() {
        SharedPreferences preferences = getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        MedicosPorCliente(preferences.getString("idVeterinaria",""));
    }
}