package ivan.ramos.biopacificv2.dialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import com.google.android.material.textfield.TextInputEditText;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

import ivan.ramos.biopacificv2.MainActivity;
import ivan.ramos.biopacificv2.R;
import ivan.ramos.biopacificv2.models.Distritos;
import ivan.ramos.biopacificv2.models.Veterinarias;
import ivan.ramos.biopacificv2.service.ApiRetrofit;
import ivan.ramos.biopacificv2.service.Services;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditVeterinariaDialog extends DialogFragment implements View.OnClickListener{
    private TextView txtDistri,txtEstado,txtUsuario;
    private ImageView imgVolver;
    private TextInputEditText txtNomVet,txtRuc,txtDirecc,txtTelf,txtRazonSocial;
    SearchableSpinner spinnerDistrito;
    private MaterialButton btnEditVet;
    String codDist;
    List<Distritos> distritosList = new ArrayList<>();
    private Services services = ApiRetrofit.getRetrofit();

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.editveterinaria_dialog, null);
        builder.setView(view);
        ListarIdVeterinaria(MainActivity.idveteri);
        final AlertDialog alertDialog = builder.show();
        imgVolver = view.findViewById(R.id.imgVolver);
        imgVolver.setOnClickListener(this);
        txtNomVet = view.findViewById(R.id.txtNomVet);
        txtRuc = view.findViewById(R.id.txtRuc);
        txtDirecc = view.findViewById(R.id.txtDirecc);
        txtTelf = view.findViewById(R.id.txtTelf);
        txtRazonSocial = view.findViewById(R.id.txtRazonSocial);
        txtDistri = view.findViewById(R.id.txtDistri);
        txtEstado = view.findViewById(R.id.txtEstado);
        txtUsuario= view.findViewById(R.id.txtUsuario);
        btnEditVet = view.findViewById(R.id.btnEditVet);
        btnEditVet.setOnClickListener(this);
        spinnerDistrito = view.findViewById(R.id.spinnerDistrito);
        spinnerDistrito();
        spinnerDistrito.setTitle("Selecciona un distrito");
        spinnerDistrito.setPositiveButton("Cancelar");
        if(alertDialog.getWindow()!=null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        return alertDialog;
    }

    private void ListarIdVeterinaria(String idveteri) {
       /* Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.14:8091/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Services services =  retrofit.create(Services.class);*/
        Call<Veterinarias> call = services.getIdVeterinaria(idveteri);
        call.enqueue(new Callback<Veterinarias>() {
            @Override
            public void onResponse(Call<Veterinarias> call, Response<Veterinarias> response) {
                Veterinarias vet = response.body();
                txtNomVet.setText(vet.getNombre());
                txtRuc.setText(vet.getRuc());
                txtRazonSocial.setText(vet.getRazonSocial());
                codDist = vet.getIdDistrito();
                //txtDistri.setText(vet.getIdDistrito());
                txtDirecc.setText(vet.getDireccion());
                txtTelf.setText(vet.getTelefono());
                txtUsuario.setText(vet.getIdUsuario());
                txtEstado.setText(vet.getEstado().toString());
                if(response.isSuccessful()){
                }
            }
            @Override
            public void onFailure(Call<Veterinarias> call, Throwable t) {
                Toast.makeText(getActivity(), "Falied: "+t, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void spinnerDistrito() {
        ArrayAdapter<Distritos> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, distritosList);
        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.14:8091/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Services services =  retrofit.create(Services.class);*/
        Call<List<Distritos>> call = services.getListDistritos();
        call.enqueue(new Callback<List<Distritos>>() {
            @Override
            public void onResponse(Call<List<Distritos>> call, Response<List<Distritos>> response) {
                if(response.isSuccessful()){
                    for(Distritos listDis : response.body()){
                        distritosList.add(listDis);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                        spinnerDistrito.setAdapter(adapter);
                        spinnerDistrito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                txtDistri.setText(distritosList.get(position).getIdDistrito());
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                       /* if(codDist.equals(listDis.getIdDistrito())){
                            spinnerDistrito.setSelection(distritosList.indexOf(listDis));
                            //Toast.makeText(getActivity(), ""+codDist, Toast.LENGTH_SHORT).show();
                        }*/
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Distritos>> call, Throwable t) {
                Toast.makeText(getActivity(), "Falied: "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgVolver:
                dismiss();
                //Intent irLogins = new Intent(getActivity(), MainActivity.class);
                //startActivity(irLogins);
                break;
            case R.id.btnEditVet:
                ValidarCampos();
                break;
            default:
                Toast.makeText(getActivity(),
                        "Error control no declarado",
                        Toast.LENGTH_LONG).show();
        }
    }
    private void ValidarCampos() {
        if (txtNomVet.getText().toString().equals("") || txtRuc.getText().toString().equals("") ||txtDirecc.getText().toString().equals("") || txtTelf.getText().toString().equals("") || txtRazonSocial.getText().toString().equals("")
                || txtDistri.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Completar campos vacios", Toast.LENGTH_SHORT).show();
        }else {
            EditarVet(MainActivity.idveteri);
        }
    }

    private void EditarVet(String idveteri) {
        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.14:8091/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Services services =  retrofit.create(Services.class);*/
        Call<Veterinarias> call = services.editarMedicoPorVet(editVeterinaria(),idveteri);
        call.enqueue(new Callback<Veterinarias>() {
            @Override
            public void onResponse(Call<Veterinarias> call, Response<Veterinarias> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getActivity(), "Perfil Actualizado", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }
            @Override
            public void onFailure(Call<Veterinarias> call, Throwable t) {
                Toast.makeText(getActivity(), "Falied: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private Veterinarias editVeterinaria() {
        Veterinarias veterinarias = new Veterinarias();
        veterinarias.setIdVeterinaria(MainActivity.idveteri);
        veterinarias.setNombre(txtNomVet.getText().toString());
        veterinarias.setRuc(txtRuc.getText().toString());
        veterinarias.setDireccion(txtDirecc.getText().toString());
        veterinarias.setTelefono(txtTelf.getText().toString());
        veterinarias.setRazonSocial(txtRazonSocial.getText().toString());
        veterinarias.setIdDistrito(txtDistri.getText().toString());
        veterinarias.setIdUsuario(txtUsuario.getText().toString());
        veterinarias.setEstado(Integer.parseInt(txtEstado.getText().toString()));
        return veterinarias;
    }
}
