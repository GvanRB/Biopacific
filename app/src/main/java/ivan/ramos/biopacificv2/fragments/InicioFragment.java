package ivan.ramos.biopacificv2.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;

import ivan.ramos.biopacificv2.R;
import ivan.ramos.biopacificv2.activitys.Login;
import ivan.ramos.biopacificv2.activitys.PdfTarifario;
import ivan.ramos.biopacificv2.service.comunicFragments;

import static android.content.Context.MODE_PRIVATE;


public class InicioFragment extends Fragment {
    private comunicFragments callback;

    public InicioFragment() {
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            callback = (comunicFragments)context;

        }catch(Exception e){
            throw new ClassCastException(context.toString());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbarHome);
        AppCompatActivity activity = (AppCompatActivity)getActivity();
        activity.setSupportActionBar(toolbar);
        return view ;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.filter_recycler,menu);
        MenuItem item = menu.findItem(R.id.filterRecycler);
        item.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.verPerfil:
                callback.editVeterinaria();
                break;
            case R.id.CerrarSession:
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("Login", MODE_PRIVATE).edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(getActivity(), Login.class));
                getActivity().onBackPressed();
                break;
            case R.id.verServicios:
                startActivity(new Intent(getActivity(), PdfTarifario.class));
                break;
        }
        return true;
    }
}