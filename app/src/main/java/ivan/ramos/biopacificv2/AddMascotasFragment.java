package ivan.ramos.biopacificv2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import ivan.ramos.biopacificv2.models.Agregarmascota;
import ivan.ramos.biopacificv2.service.Services;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddMascotasFragment extends Fragment {
    private TextInputEditText txtnameMas, txtEspecie,txtRaza,txtSexo;
    private Button btnRegisMasc;

    public AddMascotasFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_mascotas, container, false);
        txtnameMas = view.findViewById(R.id.txtnameMas);
        txtSexo = view.findViewById(R.id.txtCliente);
        txtRaza = view.findViewById(R.id.txtEstado);
        txtEspecie = view.findViewById(R.id.txtMascota);
        btnRegisMasc = view.findViewById(R.id.btnRegisMasc);
        btnRegisMasc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ValidarCampos();
            }
        });

        return  view;
    }

    private void ValidarCampos() {
        if(txtnameMas.getText().toString().equals("")|| txtEspecie.getText().toString().equals("")||txtRaza.getText().toString().equals("")
                ||txtSexo.getText().toString().equals("")){
            Toast.makeText(getContext(), "Completar todos los campos", Toast.LENGTH_SHORT).show();
        }else{
            RegistraMascota(saveMascota());

        }
    }

    private void RegistraMascota(Agregarmascota saveMascota) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.14:8090/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Services services =  retrofit.create(Services.class);
        Call<Agregarmascota> call = services.postAddMascota(saveMascota);
        call.enqueue(new Callback<Agregarmascota>() {
            @Override
            public void onResponse(Call<Agregarmascota> call, Response<Agregarmascota> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getContext(),"Registro exitosamente", Toast.LENGTH_SHORT).show();
                    txtnameMas.setText("");
                    txtSexo.setText("");
                    txtRaza.setText("");
                    txtEspecie.setText("");
                   /*  Fragment masc = new MascotasFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transac=fragmentManager.beginTransaction();
                    transac.replace(R.id.nav_host_fragment,masc);
                    transac.addToBackStack(null);
                    transac.commit();*/

                    //Intent intent = new  Intent(getContext(), Inicio.class);
                    //startActivity(intent);

                }
            }
            @Override
            public void onFailure(Call<Agregarmascota> call, Throwable t) {
                Toast.makeText(getContext(), "Falied: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Agregarmascota saveMascota() {
        Agregarmascota addmasc = new Agregarmascota();
        addmasc.setIdCliente(Login.idCliente);
        addmasc.setNombre(txtnameMas.getText().toString());
        addmasc.setGenero(txtSexo.getText().toString());
        addmasc.setEspecie(txtEspecie.getText().toString());
        addmasc.setRaza(txtRaza.getText().toString());
        return addmasc;
    }
}