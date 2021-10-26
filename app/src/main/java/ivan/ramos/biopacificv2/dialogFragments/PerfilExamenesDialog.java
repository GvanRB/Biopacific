package ivan.ramos.biopacificv2.dialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import ivan.ramos.biopacificv2.R;
import ivan.ramos.biopacificv2.activitys.SimpleBlueDivider;
import ivan.ramos.biopacificv2.adapters.AdapterPerfilExamenes;
import ivan.ramos.biopacificv2.models.PerfilExamenes;
import ivan.ramos.biopacificv2.service.ApiRetrofit;
import ivan.ramos.biopacificv2.service.Services;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PerfilExamenesDialog extends DialogFragment {
    private ArrayList<PerfilExamenes> listPerfilExamenes;
    private RecyclerView Recycler;
    private AdapterPerfilExamenes adapter;
    private Services services = ApiRetrofit.getRetrofit();
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_perfil_examenes, null);
        Recycler = view.findViewById(R.id.recyclerExamenes);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recycler_divider));
        Recycler.addItemDecoration(dividerItemDecoration);
        Recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle)).setText("Examenes que incluye el perfil");
        ((MaterialButton) view.findViewById(R.id.btnSi)).setText("Aceptar");
        ((ImageView) view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.ic_info);
        perfilExamenes(ServiciosPetsDialog.codPerfil);
        final AlertDialog alertDialog = builder.show();
        view.findViewById(R.id.btnSi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        if(alertDialog.getWindow()!=null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        return alertDialog;
    }

    private void perfilExamenes(String codPerfil) {
        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.14:8091/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Services services =  retrofit.create(Services.class);*/
        Call<List<PerfilExamenes>> call = services.getListPerfilExamenes(codPerfil);
        call.enqueue(new Callback<List<PerfilExamenes>>() {
            @Override
            public void onResponse(Call<List<PerfilExamenes>> call, Response<List<PerfilExamenes>> response) {
                if(response.isSuccessful()){
                    listPerfilExamenes = new ArrayList<>(response.body());
                    adapter = new AdapterPerfilExamenes(getContext(),listPerfilExamenes);
                    adapter.notifyDataSetChanged();
                    Recycler.setAdapter(adapter);
                }else {
                    Log.e("TagError", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<List<PerfilExamenes>> call, Throwable t) {
                Toast.makeText(getContext(), "Falied: "+t, Toast.LENGTH_LONG).show();
            }
        });
    }
}
