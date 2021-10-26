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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ivan.ramos.biopacificv2.MainActivity;
import ivan.ramos.biopacificv2.R;
import ivan.ramos.biopacificv2.activitys.Login;
import ivan.ramos.biopacificv2.adapters.AdapterListmascotasPorCliente;
import ivan.ramos.biopacificv2.models.ListadoMascotasPorVet;
import ivan.ramos.biopacificv2.models.Medicos;
import ivan.ramos.biopacificv2.models.PerfilExamenes;
import ivan.ramos.biopacificv2.models.Perfiles;
import ivan.ramos.biopacificv2.models.Solicitarservicio;
import ivan.ramos.biopacificv2.models.Usuarios;
import ivan.ramos.biopacificv2.service.ApiRetrofit;
import ivan.ramos.biopacificv2.service.Services;
import ivan.ramos.biopacificv2.service.comunicFragments;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class ServiciosPetsDialog extends DialogFragment {
    private MaterialButton btnSolicitar;
    private TextView txtCodPerfil, txtCodMasc,txtCodMedicos;
    SearchableSpinner comboPerfiles, comboMascotas,comboMedicos;
    public static String formattedDate;
    public static String codPerfil;
    private comunicFragments callback;
    private Services services = ApiRetrofit.getRetrofit();
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            callback = (comunicFragments)context;

        }catch(Exception e){
            throw new ClassCastException(context.toString());
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.serviciospets_dialog, null);
        builder.setView(view);
        txtCodPerfil=view.findViewById(R.id.txtCodPerfil);
        txtCodMasc=view.findViewById(R.id.txtCodMasc);
        txtCodMedicos=view.findViewById(R.id.txtCodMedicos);
        btnSolicitar = view.findViewById(R.id.btnSolicitar);
        comboPerfiles = view.findViewById(R.id.ComboPerfiles);
        comboPerfiles.setTitle("Seleccione un Perfil");
        comboPerfiles.setPositiveButton("Cancelar");
        comboMascotas = view.findViewById(R.id.comboMascotas);
        comboMascotas.setTitle("Seleccione una Mascota");
        comboMascotas.setPositiveButton("Cancelar");
        comboMedicos = view.findViewById(R.id.comboMedicos);
        comboMedicos.setTitle("Seleccione un Medico");
        comboMedicos.setPositiveButton("Cancelar");
        spinnerMascotas(MainActivity.idveteri);
        spinnerMedicos(MainActivity.idveteri);
        spinnerPerfiles();
        AlertDialog alertDialog = builder.show();
        ((TextView)view.findViewById(R.id.textTitle)).setText("Solicita tus servicios clinicos");
        alertDialog.setCancelable(false);
        btnSolicitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtCodPerfil.getText().toString().equals("")||txtCodMasc.getText().toString().equals("")||txtCodMedicos.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Por favor rellene los items", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    query();
                }
            }
        });view.findViewById(R.id.imageIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        if(alertDialog.getWindow()!=null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        formattedDate = df.format(c.getTime());
        return alertDialog;
    }

    private void query() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_query, null);
        //  View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_query,a);
        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle)).setText("Atención");
        ((TextView) view.findViewById(R.id.textMessage)).setText("¿Confirma el registro de solicitud?");
        ((MaterialButton) view.findViewById(R.id.btnNO)).setText("No");
        ((MaterialButton) view.findViewById(R.id.btnSi)).setText("Si");
        ((ImageView) view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.ic_info);
        final AlertDialog alertDialog = builder.show();
        alertDialog.setCancelable(false);
        view.findViewById(R.id.btnSi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrarServicio(saveservicio());
                alertDialog.dismiss();
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

    private void RegistrarServicio(Solicitarservicio solicitarservicio) {
        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.14:8091/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Services services =  retrofit.create(Services.class);*/
        Call<Solicitarservicio> call = services.postRegistrarServicio(solicitarservicio);
        call.enqueue(new Callback<Solicitarservicio>() {
            @Override
            public void onResponse(Call<Solicitarservicio> call, Response<Solicitarservicio> response) {
                System.out.println(response);
                if(response.isSuccessful()){
                    dismiss();
                    successful();
                   }
            }
            @Override
            public void onFailure(Call<Solicitarservicio> call, Throwable t) {
                Toast.makeText(getActivity(), "Falied: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void successful() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_success, null);
        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle)).setText("Operación exitosa");
        ((TextView) view.findViewById(R.id.textMessage)).setText("La solicitud ha sido registrada");
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


    private void spinnerPerfiles() {
        final List<Perfiles> perfilesList = new ArrayList<>();
        final ArrayAdapter<Perfiles> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, perfilesList);
        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.14:8091/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Services services =  retrofit.create(Services.class);*/
        Call<List<Perfiles>> call = services.getListPerfiles();
        call.enqueue(new Callback<List<Perfiles>>() {
            @Override
            public void onResponse(Call<List<Perfiles>> call, Response<List<Perfiles>> response) {
                if(response.isSuccessful()){
                    for(Perfiles listPer : response.body()){
                        perfilesList.add(listPer);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                        comboPerfiles.setAdapter(adapter);
                        comboPerfiles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                txtCodPerfil.setText(perfilesList.get(position).getIdPerfil());
                                codPerfil = txtCodPerfil.getText().toString();
                                callback.perfilExamanes();

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

    private void spinnerMascotas(final String idVeterinaria) {
        final List<ListadoMascotasPorVet> mascotasList = new ArrayList<>();
        final ArrayAdapter<ListadoMascotasPorVet> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, mascotasList);
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
                    for(ListadoMascotasPorVet listPer : response.body()){
                        mascotasList.add(listPer);
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
            public void onFailure(Call<List<ListadoMascotasPorVet>> call, Throwable t) {
                Toast.makeText(getActivity(), "Falied: "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void spinnerMedicos(final String idVeterinaria) {
        final List<Medicos> medicosList = new ArrayList<>();
        final ArrayAdapter<Medicos> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, medicosList);
        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.14:8091/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Services services =  retrofit.create(Services.class);*/
        Call<List<Medicos>> call = services.postListMediXVet(idVeterinaria);
        call.enqueue(new Callback<List<Medicos>>() {
            @Override
            public void onResponse(Call<List<Medicos>> call, Response<List<Medicos>> response) {
                if(response.isSuccessful()){
                    for(Medicos listPer : response.body()){
                        medicosList.add(listPer);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                        comboMedicos.setAdapter(adapter);

                        comboMedicos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                txtCodMedicos.setText(medicosList.get(position).getIdMedico());
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Medicos>> call, Throwable t) {
                Toast.makeText(getActivity(), "Falied: "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private Solicitarservicio saveservicio() {
        Solicitarservicio addServicio = new Solicitarservicio();
        addServicio.setIdPerfil(txtCodPerfil.getText().toString());
        addServicio.setIdMascota(txtCodMasc.getText().toString());
        addServicio.setIdMedico(txtCodMedicos.getText().toString());
        addServicio.setFechaRegistro(formattedDate);

        return addServicio;
    }

}
