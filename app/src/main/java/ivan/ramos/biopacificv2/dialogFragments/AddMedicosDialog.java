package ivan.ramos.biopacificv2.dialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;

import ivan.ramos.biopacificv2.MainActivity;
import ivan.ramos.biopacificv2.R;
import ivan.ramos.biopacificv2.activitys.Login;
import ivan.ramos.biopacificv2.fragments.MascotasFragment;
import ivan.ramos.biopacificv2.fragments.MedicosFragment;
import ivan.ramos.biopacificv2.models.Mascotas;
import ivan.ramos.biopacificv2.models.Medicos;
import ivan.ramos.biopacificv2.models.pruebaDNI;
import ivan.ramos.biopacificv2.service.ApiRetrofit;
import ivan.ramos.biopacificv2.service.Services;
import ivan.ramos.biopacificv2.service.comunicFragments;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddMedicosDialog extends DialogFragment {
    private TextView txtMedicoDNI,txtMedicoNombre, txtMedicoApellido, txtMedicoSexo,txtMedicoEdad;
    private MaterialButton btnAddMedico, dniRest;
    private Retrofit retrofit;
    //private Services services;
    public static String datoDni;
    private Services services = ApiRetrofit.getRetrofit();
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.addmedicos_dialog, null);
        builder.setView(view);
        //retrofit = new Retrofit.Builder().baseUrl("http://192.168.0.14:8091/").addConverterFactory(GsonConverterFactory.create()).build();
        //services = retrofit.create(Services.class);
        txtMedicoDNI = view.findViewById(R.id.txtMedicoDNI);
        dniRest = view.findViewById(R.id.dniRest);
        dniRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datoDni = txtMedicoDNI.getText().toString();
                if(datoDni.length()<8){
                    Toast.makeText(getActivity(), "DNI debe tener 8 digitos", Toast.LENGTH_SHORT).show();
                    txtMedicoNombre.setText(null);
                    txtMedicoApellido.setText(null);
                }else{
                    DatosDniRest(datoDni);
                }
            }
        });
        txtMedicoNombre = view.findViewById(R.id.txtMedicoNombre);
        txtMedicoApellido = view.findViewById(R.id.txtMedicoApellido);
        txtMedicoSexo = view.findViewById(R.id.txtMedicoSexo);
        txtMedicoEdad = view.findViewById(R.id.txtMedicoEdad);
        btnAddMedico = view.findViewById(R.id.btnAddMedico);
        ((TextView)view.findViewById(R.id.textTitle)).setText("MEDICO VETERINARIO");
        final AlertDialog alertDialog = builder.show();
        btnAddMedico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrarMedico(saveMedico());
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
        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dniruc.apisperu.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Services services =  retrofit.create(Services.class);*/
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

    private void RegistrarMedico(Medicos saveMedico) {
        Call<Medicos> call = services.postAddMedico(saveMedico);
        call.enqueue(new Callback<Medicos>() {
            @Override
            public void onResponse(Call<Medicos> call, Response<Medicos> response) {
                if(response.isSuccessful()){
                    dismiss();
                    successful();

                }else{
                    Log.e("TagError",response.errorBody().toString());
                }
            }
            @Override
            public void onFailure(Call<Medicos> call, Throwable t) {
            }
        });
    }
    private void successful() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_success, null);
        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle)).setText("Operación exitosa");
        ((TextView) view.findViewById(R.id.textMessage)).setText("Medico Veterinario '" +txtMedicoApellido.getText().toString()+"' ha sido registrado");
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

    private Medicos saveMedico() {
        Medicos medicos = new Medicos();
        medicos.setIdVeterinaria(MainActivity.idveteri);
        medicos.setDni(txtMedicoDNI.getText().toString());
        medicos.setNombre(txtMedicoNombre.getText().toString());
        medicos.setApellido(txtMedicoApellido.getText().toString());
        medicos.setSexo(txtMedicoSexo.getText().toString());
        medicos.setEdad(txtMedicoEdad.getText().toString());
        return  medicos;
    }
}
