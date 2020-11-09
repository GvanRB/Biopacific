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

import ivan.ramos.biopacificv2.adapters.Adapterlistmascotasxcliente;
import ivan.ramos.biopacificv2.models.ListadoMascotasPorCliente;
import ivan.ramos.biopacificv2.models.Usuarios;
import ivan.ramos.biopacificv2.service.Services;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MascotasFragment extends Fragment {

    private RecyclerView Recycler;
    private Adapterlistmascotasxcliente adapter;
    ArrayList<ListadoMascotasPorCliente> listmascota;
    public MascotasFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view = inflater.inflate(R.layout.fragment_mascotas, container, false);

        Recycler = (RecyclerView) view.findViewById(R.id.Recycler);
        Recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        Usuarios usuario = new Usuarios();
        usuario.setNombreUsuario(Login.email);
        usuario.setContraseña(Login.pass);
        Mascotasxcliente(usuario);
        return view;
    }

    private void Mascotasxcliente(Usuarios usuarios) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.14:8090/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Services services =  retrofit.create(Services.class);
        Call<List<ListadoMascotasPorCliente>> call = services.postListMascXCli(usuarios);
        call.enqueue(new Callback<List<ListadoMascotasPorCliente>>() {
            @Override
            public void onResponse(Call<List<ListadoMascotasPorCliente>> call, Response<List<ListadoMascotasPorCliente>> response) {
                listmascota = new ArrayList<>(response.body());
                adapter = new Adapterlistmascotasxcliente(getContext(),listmascota);
                adapter.notifyDataSetChanged();
                Recycler.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<ListadoMascotasPorCliente>> call, Throwable t) {
                Toast.makeText(getContext(), "Falied: "+t, Toast.LENGTH_LONG).show();
            }
        });

    }
}