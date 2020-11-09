package ivan.ramos.biopacificv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import ivan.ramos.biopacificv2.models.Clientes;
import ivan.ramos.biopacificv2.service.Services;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Registro extends AppCompatActivity implements View.OnClickListener {
    private TextView lblEntreAqui;
    private ImageView imgIrIniciarSesion;
    private TextInputEditText txtNomVet,txtDirecc,txtTelf,txtNomContac,
            txtDistri,txtCorreo,txtPassword,txtPassword2;
    private Button btnRegis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        lblEntreAqui = findViewById(R.id.lblEntreAqui);
        imgIrIniciarSesion = findViewById(R.id.imgIrAInicioSesion);
        lblEntreAqui.setOnClickListener(this);
        imgIrIniciarSesion.setOnClickListener(this);

        txtNomVet = findViewById(R.id.txtnameMas);
        txtDirecc = findViewById(R.id.txtMascota);
        txtTelf = findViewById(R.id.txtEstado);
        txtNomContac = findViewById(R.id.txtCliente);
        txtDistri = findViewById(R.id.txtIDcliente);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtPassword = findViewById(R.id.txtPassword);
        txtPassword2 = findViewById(R.id.txtPassword2);

        btnRegis = findViewById(R.id.btnRegis);
        btnRegis.setOnClickListener(this);
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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.14:8090/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Services services =  retrofit.create(Services.class);

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
    public void ValidarCampos() {

        if (txtNomVet.getText().toString().equals("") || txtDirecc.getText().toString().equals("") || txtTelf.getText().toString().equals("") || txtNomContac.getText().toString().equals("")
                || txtDistri.getText().toString().equals("") || txtCorreo.getText().toString().equals("") || txtPassword.getText().toString().equals("") || txtPassword2.getText().toString().equals("")) {
            Toast.makeText(this, "Completar campos vacios", Toast.LENGTH_SHORT).show();
        }
        else{
            ValidarCorreo(txtCorreo.getText().toString());
        }
    }

    public Clientes savecliente(){
        Clientes clientes = new Clientes();
        clientes.setNombre(txtNomVet.getText().toString());
        clientes.setDireccion(txtDirecc.getText().toString());
        clientes.setTelefono(txtTelf.getText().toString());
        clientes.setNombreContacto(txtNomContac.getText().toString());
        clientes.setDistrito(txtDistri.getText().toString());
        clientes.setNombreUsuario(txtCorreo.getText().toString());
        clientes.setContraseña(txtPassword.getText().toString());
        clientes.setContraseña(txtPassword2.getText().toString());
        return clientes;
    }

    private void RegistrarCliente(final Clientes clientes) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.14:8090/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Services services =  retrofit.create(Services.class);
        Call<Clientes> call = services.postRegistrar(clientes);
        call.enqueue(new Callback<Clientes>() {
            @Override
            public void onResponse(Call<Clientes> call, Response<Clientes> response) {
                if(response.isSuccessful()){
                    if(txtPassword.getText().toString().equals(txtPassword2.getText().toString())){
                        Toast.makeText(Registro.this, "Registro exitosamente", Toast.LENGTH_SHORT).show();
                        Intent intent = new  Intent(Registro.this, Login.class);
                        startActivity(intent);

                    }else{
                        Toast.makeText(Registro.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<Clientes> call, Throwable t) {
                Toast.makeText(Registro.this, "Falied: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}