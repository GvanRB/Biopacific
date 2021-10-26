package ivan.ramos.biopacificv2.dialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

import ivan.ramos.biopacificv2.MainActivity;
import ivan.ramos.biopacificv2.R;
import ivan.ramos.biopacificv2.adapters.AdapterListmascotasPorCliente;
import ivan.ramos.biopacificv2.fragments.MascotasFragment;
import ivan.ramos.biopacificv2.models.Especie;
import ivan.ramos.biopacificv2.models.Mascotas;
import ivan.ramos.biopacificv2.models.Raza;
import ivan.ramos.biopacificv2.service.ApiRetrofit;
import ivan.ramos.biopacificv2.service.Services;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditMascotaDialog extends DialogFragment {
    private TextView txtRaza;
    private TextInputEditText txtnameMas, txtGenero,txtEdad,txtPro;
    private MaterialButton cancelar, btnEditar;
    SearchableSpinner spinnerEspecie, spinnerRaza;
    List<Raza> razaList = new ArrayList<>();
    List<Raza> filtroraza = new ArrayList<>();
    List<Especie> especieList = new ArrayList<>();
    String codRaza,codEspecie;
    private Services services = ApiRetrofit.getRetrofit();
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.addmascota_dialog, null);
        builder.setView(view);
        ListarIdMascotaPorVet(AdapterListmascotasPorCliente.idmascota);
        spinnerEspecie =view.findViewById(R.id.spinnerEspecie);
        spinnerRaza =view.findViewById(R.id.spinnerRaza);
        txtnameMas = view.findViewById(R.id.txtnameMas);
        txtGenero = view.findViewById(R.id.txtGenero);
        txtRaza = view.findViewById(R.id.txtRaza);
        txtEdad = view.findViewById(R.id.txtEdad);
        txtPro = view.findViewById(R.id.txtPro);
        cancelar =view.findViewById(R.id.cancelar);
        btnEditar =view.findViewById(R.id.registrarpets);
        //registrar.setEnabled(false);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditMascotaPorVet(AdapterListmascotasPorCliente.idmascota);
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });view.findViewById(R.id.imageIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        SpinnerEspecies();
        spinnerEspecie.setTitle("Seleccione una Especie");
        spinnerEspecie.setPositiveButton("Cancelar");
        SpinnerRazas();
        spinnerRaza.setTitle("Seleccione una Raza");
        spinnerRaza.setPositiveButton("Cancelar");


        /*for(int i=0;i<especieList.size();i++) {
                        if (especieList.get(i).getIdEspecie().equalsIgnoreCase(codEspecie)) {
                            spinnerEspecie.setSelection(i);
                        }
                    }*/

        ((TextView)view.findViewById(R.id.textTitle)).setText("Editar Mascota");
        ((MaterialButton)view.findViewById(R.id.registrarpets)).setText("Editar");
        ((TextView)view.findViewById(R.id.textView15)).setText("Modifique los datos de la mascota");
        final AlertDialog alertDialog = builder.show();


        if(alertDialog.getWindow()!=null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        return alertDialog;
    }

    private void EditMascotaPorVet(String idmascota) {
       /* Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.14:8091/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Services services =  retrofit.create(Services.class);*/
        Call<Mascotas> call = services.editarMedicoPorVet(editMascota(),idmascota);
        call.enqueue(new Callback<Mascotas>() {
            @Override
            public void onResponse(Call<Mascotas> call, Response<Mascotas> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getActivity(), "Actualizado", Toast.LENGTH_SHORT).show();
                    dismiss();
                    MascotasFragment.btnBack.performClick();
                }
            }
            @Override
            public void onFailure(Call<Mascotas> call, Throwable t) {
                Toast.makeText(getContext(), "Falied: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ListarIdMascotaPorVet(String idmascota) {
       /* Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.14:8091/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Services services =  retrofit.create(Services.class);*/
        Call<Mascotas> call = services.getIdMascotaPorVet(idmascota);
        call.enqueue(new Callback<Mascotas>() {
            @Override
            public void onResponse(Call<Mascotas> call, Response<Mascotas> response) {
                Mascotas mas = response.body();
                txtnameMas.setText(mas.getNombre());
                txtGenero.setText(mas.getGenero());
                txtEdad.setText(mas.getEdad().toString());
                txtPro.setText(mas.getNombrePropietario());
                codRaza = mas.getIdRaza();
                codEspecie = mas.getIdEspecie();
               //txtRaza.setText(mas.getIdRaza());
               //txtEspe.setText(mas.getIdEspecie());

                if(response.isSuccessful()){

                }

            }
            @Override
            public void onFailure(Call<Mascotas> call, Throwable t) {
                Toast.makeText(getActivity(), "Falied: "+t, Toast.LENGTH_LONG).show();
            }
        });
    }
    private void SpinnerRazas() {
       /* Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.14:8091/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Services services =  retrofit.create(Services.class);*/
        Call<List<Raza>> call = services.getListRazas();
        call.enqueue(new Callback<List<Raza>>() {
            @Override
            public void onResponse(Call<List<Raza>> call, Response<List<Raza>> response) {
                if(response.isSuccessful()){
                    for( Raza listRaza : response.body()){
                        razaList.add(listRaza);
                        spinnerRaza.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                txtRaza.setText(filtroraza.get(position).getIdRaza());
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });

                        /*if(codRaza.equals(listRaza.getIdRaza())){
                            //Toast.makeText(getActivity(), ""+razaList.indexOf(listRaza), Toast.LENGTH_SHORT).show();
                            spinnerRaza.setSelection(razaList.indexOf(listRaza));

                        }*/
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Raza>> call, Throwable t) {
                Toast.makeText(getActivity(), "Falied: "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void SpinnerEspecies() {
        final ArrayAdapter<Especie> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, especieList);
        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.14:8091/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Services services =  retrofit.create(Services.class);*/
        Call<List<Especie>> call = services.getListEspecies();
        call.enqueue(new Callback<List<Especie>>() {
            @Override
            public void onResponse(Call<List<Especie>> call, Response<List<Especie>> response) {
                if(response.isSuccessful()){
                    for(Especie listEspe : response.body()) {
                        especieList.add(listEspe);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                        spinnerEspecie.setAdapter(adapter);
                        spinnerEspecie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                filtroraza.clear();
                                for (Raza ra : razaList) {
                                    if (ra.getIdEspecie().equals(especieList.get(position).getIdEspecie())) {
                                        filtroraza.add(ra);
                                    }
                                }
                                final ArrayAdapter<Raza> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, filtroraza);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                spinnerRaza.setAdapter(adapter);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                            /*if(codEspecie.equals(listEspe.getIdEspecie())){
                            spinnerEspecie.setSelection(especieList.indexOf(listEspe));
                        }*/
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Especie>> call, Throwable t) {
                Toast.makeText(getActivity(), "Falied: "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private Mascotas editMascota() {
        Mascotas addmasc = new Mascotas();
        addmasc.setIdVeterinaria(MainActivity.idveteri);
        addmasc.setIdMascota(AdapterListmascotasPorCliente.idmascota);
        addmasc.setNombre(txtnameMas.getText().toString());
        addmasc.setGenero(txtGenero.getText().toString());
        addmasc.setIdRaza(txtRaza.getText().toString());
        addmasc.setEdad(Integer.parseInt(txtEdad.getText().toString()));
        addmasc.setNombrePropietario(txtPro.getText().toString());
        return addmasc;
    }
}