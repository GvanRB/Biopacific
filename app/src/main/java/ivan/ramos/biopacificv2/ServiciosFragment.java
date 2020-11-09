package ivan.ramos.biopacificv2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ivan.ramos.biopacificv2.adapters.AdapterServiciosXmascota;
import ivan.ramos.biopacificv2.adapters.Adapterlistmascotasxcliente;
import ivan.ramos.biopacificv2.models.ListadoMascotasPorCliente;
import ivan.ramos.biopacificv2.models.Perfiles;
import ivan.ramos.biopacificv2.models.ServiciosXmascotas;
import ivan.ramos.biopacificv2.models.Solicitarservicio;
import ivan.ramos.biopacificv2.models.Usuarios;
import ivan.ramos.biopacificv2.service.Services;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServiciosFragment extends Fragment {

    private Button btnSolicitar;
    private TextView txtCodPerfil, txtCodMasc,txtCostoPerfil;
    Spinner comboPerfiles, comboMascotas;
    public static String formattedDate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_servicios,container,false);
        txtCodPerfil=view.findViewById(R.id.txtCodPerfil);
        txtCodMasc=view.findViewById(R.id.txtCodMasc);
        txtCostoPerfil=view.findViewById(R.id.txtCostoPerfil);
        btnSolicitar = view.findViewById(R.id.btnSolicitar);
        comboPerfiles = view.findViewById(R.id.ComboPerfiles);
        comboMascotas = view.findViewById(R.id.comboMascotas);
        Usuarios usuario = new Usuarios();
        usuario.setNombreUsuario(Login.email);
        usuario.setContraseña(Login.pass);
        spinnerMascotas(usuario);
        spinnerPerfiles();
        btnSolicitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrarServicio(saveservicio());
            }
        });
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedDate = df.format(c.getTime());

        return view;


    }

    private void RegistrarServicio(Solicitarservicio solicitarservicio) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.14:8090/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Services services =  retrofit.create(Services.class);
        Call<Solicitarservicio> call = services.postRegistrarServicio(solicitarservicio);
        call.enqueue(new Callback<Solicitarservicio>() {
            @Override
            public void onResponse(Call<Solicitarservicio> call, Response<Solicitarservicio> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getActivity(),"¡Solicitado!", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(),"Siga solicitando servicios", Toast.LENGTH_SHORT).show();
                     /*Intent intent = new Intent(Servicio.this, MenuPrincipal.class);
                     startActivity(intent);*/
                }
            }
            @Override
            public void onFailure(Call<Solicitarservicio> call, Throwable t) {
                Toast.makeText(getActivity(), "Falied: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void spinnerPerfiles() {
        final List<Perfiles> perfilesList = new ArrayList<>();
        final ArrayAdapter<Perfiles> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, perfilesList);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.14:8090/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Services services =  retrofit.create(Services.class);
        Call<List<Perfiles>> call = services.getListPerfiles();
        call.enqueue(new Callback<List<Perfiles>>() {
            @Override
            public void onResponse(Call<List<Perfiles>> call, Response<List<Perfiles>> response) {
                if(response.isSuccessful()){
                    for(final Perfiles listPer : response.body()){
                        final String idPerfil = listPer.getIdPerfil();
                        final String nombre = listPer.getNombre();
                        final Integer costo = listPer.getCosto();
                        final Perfiles perfiles = new Perfiles(idPerfil,nombre,costo);
                        perfilesList.add(perfiles);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                        comboPerfiles.setAdapter(adapter);

                        comboPerfiles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                txtCostoPerfil.setText(perfilesList.get(position).getCosto().toString());
                                txtCodPerfil.setText(perfilesList.get(position).getIdPerfil());
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                    }
                }

            }
            @Override
            public void onFailure(Call<List<Perfiles>> call, Throwable t) {
                Toast.makeText(getActivity(), "Falied: "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void spinnerMascotas(Usuarios usuarios) {
        final List<ListadoMascotasPorCliente> mascotasList = new ArrayList<>();
        final ArrayAdapter<ListadoMascotasPorCliente> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, mascotasList);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.14:8090/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Services services =  retrofit.create(Services.class);
        Call<List<ListadoMascotasPorCliente>> call = services.postListMascXCli(usuarios);
        call.enqueue(new Callback<List<ListadoMascotasPorCliente>>() {
            @Override
            public void onResponse(Call<List<ListadoMascotasPorCliente>> call, Response<List<ListadoMascotasPorCliente>> response) {
                if(response.isSuccessful()){
                    for(ListadoMascotasPorCliente listPer : response.body()){
                        String idmascota = listPer.getIdMascota();
                        String nombre = listPer.getNombre();
                        String genero = listPer.getGenero();
                        String raza = listPer.getRaza();
                        String especie = listPer.getEspecie();
                        ListadoMascotasPorCliente mascotas = new ListadoMascotasPorCliente(idmascota,nombre,genero,raza,especie);
                        mascotasList.add(mascotas);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                        comboMascotas.setAdapter(adapter);

                        comboMascotas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                txtCodMasc.setText(mascotasList.get(position).getIdMascota());
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<List<ListadoMascotasPorCliente>> call, Throwable t) {
                Toast.makeText(getActivity(), "Falied: "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private Solicitarservicio saveservicio() {
        Solicitarservicio addServicio = new Solicitarservicio();
        addServicio.setIdPerfil(txtCodPerfil.getText().toString());
        addServicio.setIdCliente(Login.idCliente);
        addServicio.setIdMascota(txtCodMasc.getText().toString());
        //Integer costo = Integer.parseInt(txtCostoPerfil.getText().toString());
        addServicio.setCostoServicio(Integer.parseInt(txtCostoPerfil.getText().toString()));
        addServicio.setFechaRegistro(formattedDate);
        //Toast.makeText(this, txtCostoPerfil.getText().toString(), Toast.LENGTH_SHORT).show();
        return addServicio;
    }


}