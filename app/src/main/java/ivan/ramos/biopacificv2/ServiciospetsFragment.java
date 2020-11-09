package ivan.ramos.biopacificv2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ivan.ramos.biopacificv2.adapters.AdapterServiciosXmascota;
import ivan.ramos.biopacificv2.adapters.Adapterlistmascotasxcliente;
import ivan.ramos.biopacificv2.models.ServiciosXmascotas;
import ivan.ramos.biopacificv2.service.Services;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServiciospetsFragment extends Fragment {

    private RecyclerView Recycler;
    private AdapterServiciosXmascota adapter;
    ArrayList<ServiciosXmascotas> listServiciosXmasc;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_serviciospets,container,false);
        Recycler = view.findViewById(R.id.recyMascotas);
        Recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        ListServiciosMascotas(Adapterlistmascotasxcliente.idmascota);

        return view;

    }

    private void ListServiciosMascotas(String idmascota) {
        //Toast.makeText(this, "MENSAGGE"+idmascota, Toast.LENGTH_SHORT).show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.14:8090/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Services services =  retrofit.create(Services.class);
        Call<List<ServiciosXmascotas>> call = services.getlistadoserviciosmasc(idmascota);
        call.enqueue(new Callback<List<ServiciosXmascotas>>() {
            @Override
            public void onResponse(Call<List<ServiciosXmascotas>> call, Response<List<ServiciosXmascotas>> response) {
                listServiciosXmasc = new ArrayList<>(response.body());
                Toast.makeText(getActivity(), "Revise los estados de sus servicios", Toast.LENGTH_SHORT).show();
                adapter = new AdapterServiciosXmascota(getActivity(),listServiciosXmasc);
                adapter.notifyDataSetChanged();
                Recycler.setAdapter(adapter);

            }
            @Override
            public void onFailure(Call<List<ServiciosXmascotas>> call, Throwable t) {
                Toast.makeText(getActivity(), "Falied: "+t, Toast.LENGTH_LONG).show();
            }
        });
    }
}