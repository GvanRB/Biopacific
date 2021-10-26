package ivan.ramos.biopacificv2.dialogFragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;

import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

import ivan.ramos.biopacificv2.MainActivity;
import ivan.ramos.biopacificv2.R;
import ivan.ramos.biopacificv2.activitys.Login;
import ivan.ramos.biopacificv2.adapters.AdapterListmascotasPorCliente;
import ivan.ramos.biopacificv2.fragments.MascotasFragment;
import ivan.ramos.biopacificv2.models.Mascotas;
import ivan.ramos.biopacificv2.models.Especie;
import ivan.ramos.biopacificv2.models.Raza;
import ivan.ramos.biopacificv2.service.ApiRetrofit;
import ivan.ramos.biopacificv2.service.Services;
import ivan.ramos.biopacificv2.service.comunicFragments;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class AddMascotaDialog  extends DialogFragment {
    private TextView txtRaza;
    private TextInputEditText txtnameMas, txtGenero,txtEdad,txtPro;
    private MaterialButton cancelar, registrar;
    SearchableSpinner spinnerEspecie, spinnerRaza;
    final List<Raza> razaList = new ArrayList<>();
    List<Raza> filtroraza = new ArrayList<>();
    private Services services = ApiRetrofit.getRetrofit();
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.addmascota_dialog, null);
        builder.setView(view);
        spinnerEspecie =view.findViewById(R.id.spinnerEspecie);
        spinnerRaza =view.findViewById(R.id.spinnerRaza);
        txtnameMas = view.findViewById(R.id.txtnameMas);
        txtGenero = view.findViewById(R.id.txtGenero);
        txtRaza = view.findViewById(R.id.txtRaza);
        txtEdad = view.findViewById(R.id.txtEdad);
        txtPro = view.findViewById(R.id.txtPro);
        cancelar =view.findViewById(R.id.cancelar);
        registrar =view.findViewById(R.id.registrarpets);
        registrar.setEnabled(false);
        SpinnerEspecies();
        spinnerEspecie.setTitle("Seleccione una Especie");
        spinnerEspecie.setPositiveButton("Cancelar");
        SpinnerRazas();
        spinnerRaza.setTitle("Seleccione una Raza");
        spinnerRaza.setPositiveButton("Cancelar");
        ((TextView)view.findViewById(R.id.textTitle)).setText("Registro de Mascota");
        final AlertDialog alertDialog = builder.show();
        txtnameMas.addTextChangedListener(new CustomTextWatcher(txtnameMas));
        txtGenero.addTextChangedListener(new CustomTextWatcher(txtGenero));
        txtEdad.addTextChangedListener(new CustomTextWatcher(txtEdad));
        txtPro.addTextChangedListener(new CustomTextWatcher(txtPro));
        alertDialog.setCancelable(false);
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistraMascota(saveMascota());
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        }); view.findViewById(R.id.imageIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        if(alertDialog.getWindow()!=null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        return alertDialog;
    }

    public class CustomTextWatcher implements TextWatcher{
        private EditText mEditext;
        public CustomTextWatcher(EditText e){
            mEditext = e;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(!txtnameMas.getText().toString().equals("") && !txtRaza.getText().toString().equals("")
                    && !txtGenero.getText().toString().equals("")&&!txtEdad.getText().toString().equals("")&&!txtPro.getText().toString().equals("")){
                registrar.setEnabled(true);
            }else{
                registrar.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private void successful() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_success, null);
        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle)).setText("Operación exitosa");
        ((TextView) view.findViewById(R.id.textMessage)).setText("La mascota '" +txtnameMas.getText().toString()+"' ha sido registrada");
        ((MaterialButton) view.findViewById(R.id.btnSi)).setText("Ok");
        ((ImageView) view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.ic_success);
        final AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.btnSi).setOnClickListener(new View.OnClickListener() {
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


    private void RegistraMascota(Mascotas saveMascota) {
        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.14:8091/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Services services =  retrofit.create(Services.class);*/
        Call<Mascotas> call = services.postAddMascota(saveMascota);
        call.enqueue(new Callback<Mascotas>() {
            @Override
            public void onResponse(Call<Mascotas> call, Response<Mascotas> response) {
                if(response.isSuccessful()){
                    dismiss();
                    successful();
                    MascotasFragment.btnBack.performClick();
                }
            }
            @Override
            public void onFailure(Call<Mascotas> call, Throwable t) {
                Toast.makeText(getContext(), "Falied: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Mascotas saveMascota() {
        Mascotas addmasc = new Mascotas();
        addmasc.setIdVeterinaria(MainActivity.idveteri);
        addmasc.setNombre(txtnameMas.getText().toString());
        addmasc.setGenero(txtGenero.getText().toString());
        addmasc.setIdRaza(txtRaza.getText().toString());
        addmasc.setEdad(Integer.parseInt(txtEdad.getText().toString()));
        addmasc.setNombrePropietario(txtPro.getText().toString());
        return addmasc;
    }
    private void SpinnerEspecies() {
        final List<Especie> especieList = new ArrayList<>();
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
                    for( Especie listEspe : response.body()){
                        especieList.add(listEspe);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                        spinnerEspecie.setAdapter(adapter);
                        spinnerEspecie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                filtroraza.clear();
                                for(Raza ra : razaList){
                                    if(ra.getIdEspecie().equals(especieList.get(position).getIdEspecie())){
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
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Especie>> call, Throwable t) {
                Toast.makeText(getActivity(), "Falied: "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void SpinnerRazas(){
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
                    for(Raza listRaza : response.body()){
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
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Raza>> call, Throwable t) {
                Toast.makeText(getActivity(), "Falied: "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


}







    /*  builder.setNegativeButton(Html.fromHtml("<font color='#FFFFFF'>Cancelar</font>"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setPositiveButton(Html.fromHtml("<font color='#FFFFFF'>Registrar</font>"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ValidarCampos();
            }
        });*/
/* private void ValidarCampos() {
        if(txtnameMas.getText().toString().equals("") && txtEspecie.getText().toString().equals("") && txtRaza.getText().toString().equals("")
                && txtSexo.getText().toString().equals("")){
                registrar.setEnabled(false);
            //Toast.makeText(getContext(), "Completar todos los campos", Toast.LENGTH_SHORT).show();
        }else{
            registrar.setEnabled(true);
        }
    }*/
