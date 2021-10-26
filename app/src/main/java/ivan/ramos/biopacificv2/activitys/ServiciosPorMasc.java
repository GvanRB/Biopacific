package ivan.ramos.biopacificv2.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ivan.ramos.biopacificv2.R;
import ivan.ramos.biopacificv2.adapters.AdapterListmascotasPorCliente;
import ivan.ramos.biopacificv2.adapters.AdapterServiciosXmascota;
import ivan.ramos.biopacificv2.models.ServiciosXmascotas;
import ivan.ramos.biopacificv2.service.ApiRetrofit;
import ivan.ramos.biopacificv2.service.Services;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiciosPorMasc extends AppCompatActivity {
    private RecyclerView Recycler;
    private AdapterServiciosXmascota adapter;
    private TextView Mascotatxt,txtPropi;
    private ImageView btnBack;
    private Services services = ApiRetrofit.getRetrofit();
    ArrayList<ServiciosXmascotas> listServiciosXmasc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviciopormasc);
        Toolbar toolbar = findViewById(R.id.toolbarPetServ);
        setSupportActionBar(toolbar);
        Mascotatxt = findViewById(R.id.Mascotatxt);
        txtPropi = findViewById(R.id.txtPropi);
        btnBack = findViewById(R.id.btnBack);
       /* btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                //startActivity(new Intent(ServiciosPorMasc.this, MainActivity.class));
            }
        });*/
        Mascotatxt.setText(AdapterListmascotasPorCliente.nomMas);
        txtPropi.setText(AdapterListmascotasPorCliente.nomPro);
        Recycler = findViewById(R.id.recyMascotas);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recycler_divider));
        Recycler.addItemDecoration(dividerItemDecoration);
        Recycler.setLayoutManager(new LinearLayoutManager(this));
        ListServiciosMascotas(AdapterListmascotasPorCliente.idmascota);
    }



    private void ListServiciosMascotas(String idmascota) {
        //Toast.makeText(this, "MENSAGGE"+idmascota, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(ServiciosPorMasc.this, "Revise los estados de sus servicios", Toast.LENGTH_SHORT).show();
                adapter = new AdapterServiciosXmascota(ServiciosPorMasc.this,listServiciosXmasc);
                adapter.notifyDataSetChanged();
                Recycler.setAdapter(adapter);

            }
            @Override
            public void onFailure(Call<List<ServiciosXmascotas>> call, Throwable t) {
                Toast.makeText(ServiciosPorMasc.this, "Falied: "+t, Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter_recycler, menu);
        MenuItem menuItem1 = menu.findItem(R.id.verPerfil);
        menuItem1.setVisible(false);
        MenuItem menuItem2 = menu.findItem(R.id.CerrarSession);
        menuItem2.setVisible(false);
        MenuItem menuItem3 = menu.findItem(R.id.verServicios);
        menuItem3.setVisible(false);
        MenuItem item = menu.findItem(R.id.filterRecycler);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Buscar servicios...");
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

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 101:
                startActivity(new Intent(this, PdfViewFact.class));
                break;
            case 102:
                startActivity(new Intent(this, PdfViewResult.class));
                //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(AdapterServiciosXmascota.ordenid)));
                break;
        }
        return super.onContextItemSelected(item);
    }
}