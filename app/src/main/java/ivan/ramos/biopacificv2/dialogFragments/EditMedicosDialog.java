package ivan.ramos.biopacificv2.dialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;

import ivan.ramos.biopacificv2.MainActivity;
import ivan.ramos.biopacificv2.R;
import ivan.ramos.biopacificv2.adapters.AdapterListMedicosPorCliente;
import ivan.ramos.biopacificv2.adapters.AdapterListmascotasPorCliente;
import ivan.ramos.biopacificv2.fragments.MascotasFragment;
import ivan.ramos.biopacificv2.fragments.MedicosFragment;
import ivan.ramos.biopacificv2.models.Mascotas;
import ivan.ramos.biopacificv2.models.Medicos;
import ivan.ramos.biopacificv2.models.pruebaDNI;
import ivan.ramos.biopacificv2.service.ApiRetrofit;
import ivan.ramos.biopacificv2.service.Services;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditMedicosDialog extends DialogFragment {
    private TextView txtMedicoDNI,txtMedicoNombre, txtMedicoApellido, txtMedicoSexo,txtMedicoEdad;
    private MaterialButton btnEditMedico,dniRest;
    private Retrofit retrofit;
    //private Services services;
    private Services services = ApiRetrofit.getRetrofit();
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.addmedicos_dialog, null);
        builder.setView(view);
        ListarIdMedicoPorVet(AdapterListMedicosPorCliente.id);
        //retrofit = new Retrofit.Builder().baseUrl("http://192.168.0.14:8091/").addConverterFactory(GsonConverterFactory.create()).build();
        //services = retrofit.create(Services.class);
        txtMedicoDNI = view.findViewById(R.id.txtMedicoDNI);
        dniRest = view.findViewById(R.id.dniRest);
        dniRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMedicosDialog.datoDni = txtMedicoDNI.getText().toString();
                if(AddMedicosDialog.datoDni.length()<8){
                    Toast.makeText(getActivity(), "DNI debe tener 8 digitos", Toast.LENGTH_SHORT).show();
                    txtMedicoNombre.setText(null);
                    txtMedicoApellido.setText(null);
                }else{
                    DatosDniRest(AddMedicosDialog.datoDni);
                }
            }
        });
        txtMedicoNombre = view.findViewById(R.id.txtMedicoNombre);
        txtMedicoApellido = view.findViewById(R.id.txtMedicoApellido);
        txtMedicoSexo = view.findViewById(R.id.txtMedicoSexo);
        txtMedicoEdad = view.findViewById(R.id.txtMedicoEdad);
        btnEditMedico = view.findViewById(R.id.btnAddMedico);
        ((TextView)view.findViewById(R.id.textTitle)).setText("EDITAR MEDICO VETERINARIO");
        ((MaterialButton)view.findViewById(R.id.btnAddMedico)).setText("EDITAR");

        final AlertDialog alertDialog = builder.show();
        btnEditMedico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditarMedico(AdapterListMedicosPorCliente.id);
                //MedicosFragment.refrescar.performClick();
            }
        });
        view.findViewById(R.id.imageIcon).setOnClickListener(new View.OnClickListener() {
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

    private void DatosDniRest(String datoDni) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dniruc.apisperu.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Services services =  retrofit.create(Services.class);
        Call<pruebaDNI> call = services.datosDNI(datoDni);
        call.enqueue(new Callback<pruebaDNI>() {
            @Override
            public void onResponse(Call<pruebaDNI> call, Response<pruebaDNI> response) {
                pruebaDNI mas = response.body();
                txtMedicoNombre.setText(mas.getNombres());
                txtMedicoApellido.setText(mas.getApellidoPaterno()+" "+ mas.getApellidoMaterno());
                if(response.isSuccessful()){
                }
            }
            @Override
            public void onFailure(Call<pruebaDNI> call, Throwable t) {
                Toast.makeText(getActivity(), "DNI INVALIDO", Toast.LENGTH_LONG).show();
                txtMedicoNombre.setText(null);
                txtMedicoApellido.setText(null);
            }
        });
    }

    private void EditarMedico(String id) {
        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.14:8091/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Services services =  retrofit.create(Services.class);*/
        Call<Medicos> call = services.editarMedicoPorVet(editMedico(),id);
        call.enqueue(new Callback<Medicos>() {
            @Override
            public void onResponse(Call<Medicos> call, Response<Medicos> response) {
                if(response.isSuccessful()){
                    dismiss();
                    Toast.makeText(getActivity(), "Modificado", Toast.LENGTH_SHORT).show();
                    MedicosFragment.refrescar.performClick();
                }
            }
            @Override
            public void onFailure(Call<Medicos> call, Throwable t) {
                Toast.makeText(getActivity(), "Falied: "+t, Toast.LENGTH_LONG).show();
            }
        });

    }

    private void ListarIdMedicoPorVet(String id) {
        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.14:8091/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Services services =  retrofit.create(Services.class);*/
        Call<Medicos> call = services.getIdMedicoPorVet(id);
        call.enqueue(new Callback<Medicos>() {
            @Override
            public void onResponse(Call<Medicos> call, Response<Medicos> response) {
                Medicos med = response.body();
                txtMedicoDNI.setText(med.getDni());
                txtMedicoNombre.setText(med.getNombre());
                txtMedicoApellido.setText(med.getApellido());
                txtMedicoSexo.setText(med.getSexo());
                txtMedicoEdad.setText(med.getEdad());
                if(response.isSuccessful()){
                }
            }
            @Override
            public void onFailure(Call<Medicos> call, Throwable t) {
                Toast.makeText(getActivity(), "Falied: "+t, Toast.LENGTH_LONG).show();
            }
        });
    }
    private Medicos editMedico() {
        Medicos medicos = new Medicos();
        medicos.setIdVeterinaria(MainActivity.idveteri);
        medicos.setIdMedico(AdapterListMedicosPorCliente.id);
        medicos.setDni(txtMedicoDNI.getText().toString());
        medicos.setNombre(txtMedicoNombre.getText().toString());
        medicos.setApellido(txtMedicoApellido.getText().toString());
        medicos.setSexo(txtMedicoSexo.getText().toString());
        medicos.setEdad(txtMedicoEdad.getText().toString());
        return  medicos;
    }
}

