package ivan.ramos.biopacificv2.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import ivan.ramos.biopacificv2.R;
import ivan.ramos.biopacificv2.models.Veterinarias;
import ivan.ramos.biopacificv2.models.Distritos;
import ivan.ramos.biopacificv2.service.ApiRetrofit;
import ivan.ramos.biopacificv2.service.Services;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class   Registro extends AppCompatActivity implements View.OnClickListener {
    private TextView lblEntreAqui,txtDistri;
    private ImageView imgIrIniciarSesion;
    private TextInputEditText txtNomVet,txtRuc,txtDirecc,txtTelf,txtRazonSocial,txtCorreo,txtPassword,txtPassword2;
    SearchableSpinner spinnerDistrito;
    private MaterialButton btnRegis;
    private Services services = ApiRetrofit.getRetrofit();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        lblEntreAqui = findViewById(R.id.lblEntreAqui);
        imgIrIniciarSesion = findViewById(R.id.imgIrAInicioSesion);

        lblEntreAqui.setOnClickListener(this);
        imgIrIniciarSesion.setOnClickListener(this);
        txtNomVet = findViewById(R.id.txtNomVet);
        txtRuc = findViewById(R.id.txtRuc);
        txtDirecc = findViewById(R.id.txtDirecc);
        txtTelf = findViewById(R.id.txtTelf);
        txtRazonSocial = findViewById(R.id.txtRazonSocial);
        txtDistri = findViewById(R.id.txtDistri);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtPassword = findViewById(R.id.txtPassword);
        txtPassword2 = findViewById(R.id.txtPassword2);
        btnRegis = findViewById(R.id.btnRegis);
        btnRegis.setOnClickListener(this);
        spinnerDistrito = findViewById(R.id.spinnerDistrito);
        spinnerDistritos();
        spinnerDistrito.setTitle("Seleccione un distrito");
        spinnerDistrito.setPositiveButton("Cancelar");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lblEntreAqui:
                Intent irLogin = new Intent(Registro.this, Login.class);
                startActivity(irLogin);
                break;
            case R.id.imgIrAInicioSesion:
                Intent irLogins = new Intent(Registro.this, Login.class);
                startActivity(irLogins);
                break;
            case R.id.btnRegis:
                ValidarCampos();
                break;
            default:
                Toast.makeText(getApplicationContext(),
                        "Error control no declarado",
                        Toast.LENGTH_LONG).show();
        }
    }

    private void ValidarCorreo(final String correo) {

        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.14:8091/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Services services =  retrofit.create(Services.class);*/
        Call<Boolean> call = services.getValidarCorreo(correo);
        call.enqueue(new Callback<Boolean>() {
            @Override

            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    if(response.body()){

                        Toast.makeText(Registro.this, "Correo electronico existente", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        RegistrarCliente(savecliente());
                    }
                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(Registro.this, "Falied: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
    public void ValidarCampos() {
        if(!validarEmail(txtCorreo.toString())){
            txtCorreo.setError("Inserte un Email");
            //Toast.makeText(this, "Inserta un Email", Toast.LENGTH_SHORT).show();
        }
        if (txtNomVet.getText().toString().equals("") || txtRuc.getText().toString().equals("") ||txtDirecc.getText().toString().equals("") || txtTelf.getText().toString().equals("") || txtRazonSocial.getText().toString().equals("")
                || txtDistri.getText().toString().equals("") || txtCorreo.getText().toString().equals("") || txtPassword.getText().toString().equals("") || txtPassword2.getText().toString().equals("")) {
            Toast.makeText(this, "Completar campos vacios", Toast.LENGTH_SHORT).show();
        }
        else{
            ValidarCorreo(txtCorreo.getText().toString());
        }
    }

    public Veterinarias savecliente(){
        Veterinarias veterinarias = new Veterinarias();
        veterinarias.setNombre(txtNomVet.getText().toString());
        veterinarias.setRuc(txtRuc.getText().toString());
        veterinarias.setDireccion(txtDirecc.getText().toString());
        veterinarias.setTelefono(txtTelf.getText().toString());
        veterinarias.setRazonSocial(txtRazonSocial.getText().toString());
        veterinarias.setIdDistrito(txtDistri.getText().toString());
        veterinarias.setCorreo(txtCorreo.getText().toString());
        veterinarias.setContrasena(txtPassword.getText().toString());
        veterinarias.setContrasena(txtPassword2.getText().toString());
        return veterinarias;
    }

    private void RegistrarCliente(final Veterinarias veterinarias) {
       /* Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.14:8091/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Services services =  retrofit.create(Services.class);*/
        Call<Veterinarias> call = services.postRegistrar(veterinarias);
        if(txtPassword.getText().toString().equals(txtPassword2.getText().toString())){
            Toast.makeText(Registro.this, "Registro exitosamente", Toast.LENGTH_SHORT).show();
            Intent intent = new  Intent(Registro.this, Login.class);
            call.enqueue(new Callback<Veterinarias>() {
                @Override
                public void onResponse(Call<Veterinarias> call, Response<Veterinarias> response) {
                    if(response.isSuccessful()){
                    }
                }
                @Override
                public void onFailure(Call<Veterinarias> call, Throwable t) {
                    Toast.makeText(Registro.this, "Falied: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            startActivity(intent);

        }else{
            Toast.makeText(Registro.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }
    }

    private void spinnerDistritos() {
        final List<Distritos> distritosList = new ArrayList<>();
        final ArrayAdapter<Distritos> adapter = new ArrayAdapter<>(Registro.this, android.R.layout.simple_spinner_item, distritosList);
        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/")
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
                    }
                }


            }
            @Override
            public void onFailure(Call<List<Distritos>> call, Throwable t) {
                Toast.makeText(Registro.this, "Falied: "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


}