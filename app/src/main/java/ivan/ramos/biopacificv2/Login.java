package ivan.ramos.biopacificv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import ivan.ramos.biopacificv2.models.Dataclientemenu;
import ivan.ramos.biopacificv2.models.Usuarios;
import ivan.ramos.biopacificv2.service.Services;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity implements View.OnClickListener{
    private TextView lblRegistrarte;
    private Button btnInicio;
    private TextInputEditText txtUser, txtPass;
    public static String pass,email,idCliente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        lblRegistrarte = findViewById(R.id.lblRegistrate);
        btnInicio = findViewById(R.id.btnIniciarSesiom);
        btnInicio.setOnClickListener(this);
        lblRegistrarte.setOnClickListener(this);
        txtUser = findViewById(R.id.txtUser);
        txtPass = findViewById(R.id.txtPass);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lblRegistrate:
                Intent irRegistro = new Intent(Login.this, Registro.class);
                startActivity(irRegistro);
                break;
            case R.id.btnIniciarSesiom:
                ValidarCamposLogin();
                EnviarDatosMenuPrincipal(username());
                break;
            default:
                Toast.makeText(this,
                        "Error control no declarado",
                        Toast.LENGTH_LONG).show();
        }
    }
    private void EnviarDatosMenuPrincipal(final Usuarios usuarios) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.14:8090/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Services services =  retrofit.create(Services.class);
        Call<Dataclientemenu> call = services.postNomVet(usuarios);
        call.enqueue(new Callback<Dataclientemenu>() {
            @Override
            public void onResponse(Call<Dataclientemenu> call, Response<Dataclientemenu> response) {
                if(response.isSuccessful()){
                    Dataclientemenu obj = response.body();
                    String correo = obj.getCorreo();
                    String nombre = obj.getNombre();
                    idCliente = obj.getIdCliente();
                    Intent intent = new  Intent(Login.this, MainActivity.class);
                    intent.putExtra("correo",correo);
                    intent.putExtra("nombrevet",nombre);
                    email=usuarios.getNombreUsuario();
                    pass=usuarios.getContraseña();
                    startActivity(intent);
                }
            }
            @Override
            public void onFailure(Call<Dataclientemenu> call, Throwable t) {
                Toast.makeText(Login.this, "Falied: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public  Usuarios username (){
        Usuarios usuario = new Usuarios();
        usuario.setNombreUsuario(txtUser.getText().toString());
        usuario.setContraseña(txtPass.getText().toString());
        return usuario;
    }
    public void ValidarCamposLogin() {

        if (txtUser.getText().toString().equals("") || txtPass.getText().toString().equals("") ) {
            Toast.makeText(this, "Completar campos vacios", Toast.LENGTH_SHORT).show();
        }
        else{
            validar(username());
        }
    }


    private void validar(final Usuarios usuarios) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.14:8090/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Services services =  retrofit.create(Services.class);
        Call<Boolean> call = services.postvalidariniciosesion(usuarios);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    if(response.body()){
                        Toast.makeText(Login.this, "¡Bienvenido!", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(Login.this, "Email y/o contraseña inválido", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(Login.this, "Falied: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}