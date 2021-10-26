package ivan.ramos.biopacificv2.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import ivan.ramos.biopacificv2.MainActivity;
import ivan.ramos.biopacificv2.R;
import ivan.ramos.biopacificv2.models.IniciarSesion;
import ivan.ramos.biopacificv2.models.Usuarios;
import ivan.ramos.biopacificv2.service.ApiRetrofit;
import ivan.ramos.biopacificv2.service.Services;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity implements View.OnClickListener{
    private TextView lblRegistrarte;
    private MaterialButton btnInicio;
    private TextInputEditText txtUser, txtPass;
    public static String pass,email,idVeterinaria;
    private Services services = ApiRetrofit.getRetrofit();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //services = ApiRetrofit.getRetrofit();
        lblRegistrarte = findViewById(R.id.lblRegistrate);
        btnInicio = findViewById(R.id.btnIniciarSesiom);
        btnInicio.setOnClickListener(this);
        lblRegistrarte.setOnClickListener(this);
        txtUser = findViewById(R.id.txtUser);
        txtPass = findViewById(R.id.txtPass);
        SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
        if (preferences.contains("usuario")){
            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
        }
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




                break;
            default:
                Toast.makeText(this,
                        "Error control no declarado",
                        Toast.LENGTH_LONG).show();
        }
    }
   /* private void EnviarDatosMenuPrincipal(final Usuarios usuarios) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.14:8091/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Services services =  retrofit.create(Services.class);
        Call<IniciarSesion> call = services.postNomVet(usuarios);
        call.enqueue(new Callback<IniciarSesion>() {
            @Override
            public void onResponse(Call<IniciarSesion> call, Response<IniciarSesion> response) {
                if(response.isSuccessful()){
                   IniciarSesion obj = response.body();
                  //  String correo = obj.getCorreo();
                   // String nombre = obj.getNombre();
                    idVeterinaria = obj.getIdVeterinaria();
                    Intent intent = new  Intent(Login.this, MainActivity.class);
                 // intent.putExtra("correo",correo);
                   // intent.putExtra("nombrevet",nombre);
                    email=usuarios.getNombreUsuario();
                    pass=usuarios.getContrasena();
                    startActivity(intent);
                }
            }
            @Override
            public void onFailure(Call<IniciarSesion> call, Throwable t) {
                Toast.makeText(Login.this, "Falied: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    public  Usuarios username (){
        Usuarios usuario = new Usuarios();
        usuario.setNombreUsuario(txtUser.getText().toString());
        usuario.setContrasena(txtPass.getText().toString());

        return usuario;
    }
    public void ValidarCamposLogin() {

        if (txtUser.getText().toString().equals("") || txtPass.getText().toString().equals("") ) {
            Toast.makeText(this, "Completar campos vacios", Toast.LENGTH_SHORT).show();
        }
        else{
            IniciarSesion(username());
        }
    }


    private void IniciarSesion(final Usuarios usuarios) {
       /* Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Services services =  retrofit.create(Services.class);*/
        Call<IniciarSesion> call = services.postvalidariniciosesion(usuarios);
        call.enqueue(new Callback<IniciarSesion>() {
            @Override
            public void onResponse(Call<IniciarSesion> call, Response<IniciarSesion> response) {
                if(response.isSuccessful()){
                    IniciarSesion obj = response.body();
                    idVeterinaria = obj.getIdVeterinaria();
                    email = txtUser.getText().toString();
                    pass = txtPass.getText().toString();
                    SharedPreferences.Editor editor = getSharedPreferences("Login", MODE_PRIVATE).edit();
                    editor.putString("usuario", email);
                    editor.putString("password", pass);
                    editor.putString("idVeterinaria",obj.getIdVeterinaria());
                    editor.apply();
                    Intent intent = new  Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(Login.this, "¡Bienvenido!", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(Login.this, "Email y/o contraseña inválido", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<IniciarSesion> call, Throwable t) {
                Toast.makeText(Login.this, "Falied: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}