package ivan.ramos.biopacificv2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import ivan.ramos.biopacificv2.dialogFragments.AddMascotaDialog;
import ivan.ramos.biopacificv2.dialogFragments.AddMedicosDialog;
import ivan.ramos.biopacificv2.dialogFragments.EditMascotaDialog;
import ivan.ramos.biopacificv2.dialogFragments.EditMedicosDialog;
import ivan.ramos.biopacificv2.dialogFragments.EditVeterinariaDialog;
import ivan.ramos.biopacificv2.dialogFragments.PerfilExamenesDialog;
import ivan.ramos.biopacificv2.dialogFragments.ServiciosPetsDialog;
import ivan.ramos.biopacificv2.service.comunicFragments;

public class MainActivity extends AppCompatActivity implements comunicFragments {
    public static String idveteri;
    public static FloatingActionButton serviciospets;
    private TextView eemail, ppass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        serviciospets=findViewById(R.id.serviciospets);
        serviciospets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                servicioDialog();
            }
        });
       /* AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.inicioFragment, R.id.mascotasFragment, R.id.medicosFragment)
                .build();*/
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        // NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
        idveteri = preferences.getString("idVeterinaria","");
    }

    public void addMascotaDialog() {
        AddMascotaDialog addMascotaDialog = new AddMascotaDialog();
        addMascotaDialog.show(getSupportFragmentManager(),"addMascotaDialog");
    }
    public void servicioDialog(){
        ServiciosPetsDialog serviciosDialog = new ServiciosPetsDialog();
        serviciosDialog.show(getSupportFragmentManager(),"servicioDialog");
    }
    public void addMedicoDialog(){
        AddMedicosDialog addMedicosDialog = new AddMedicosDialog();
        addMedicosDialog.show(getSupportFragmentManager(),"addMedicosDialog");
    }
    private void editMascotaDialog() {
        EditMascotaDialog editMascotaDialog = new EditMascotaDialog();
        editMascotaDialog.show(getSupportFragmentManager(),"editMascotaDialog");
    }
    private void editMedicoDialog() {
        EditMedicosDialog editMedicosDialog = new EditMedicosDialog();
        editMedicosDialog.show(getSupportFragmentManager(),"editMedicosDialog");
    }
    private void editVeterinariaDialog() {
        EditVeterinariaDialog editVeterinariaDialog = new EditVeterinariaDialog();
        editVeterinariaDialog.show(getSupportFragmentManager(),"editVeterinariaDialog");
    }
    private void PerfilExamenesDialog() {
        PerfilExamenesDialog perfilExamenesDialog = new PerfilExamenesDialog();
        perfilExamenesDialog.show(getSupportFragmentManager(),"perfilExamenesDialog");
    }

    @Override
    public void addMascota() {
        addMascotaDialog();
    }
    @Override
    public void addMedicos() {
        addMedicoDialog();
    }

    @Override
    public void editMascota() {
        editMascotaDialog();
    }
    @Override
    public void editMedicos() {
        editMedicoDialog();
    }
    @Override
    public void editVeterinaria() {
        editVeterinariaDialog();
    }
    @Override
    public void perfilExamanes() {
        PerfilExamenesDialog();
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter_recycler, menu);
        MenuItem item = menu.findItem(R.id.filterRecycler);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                MascotasFragment.adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.verPerfil:
                editVeterinariaDialog();
                //Toast.makeText(this, ""+idveteri, Toast.LENGTH_SHORT).show();
                break;
            case R.id.CerrarSession:
                SharedPreferences.Editor editor = getSharedPreferences("Login", MODE_PRIVATE).edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(MainActivity.this, Login.class));
                finish();
        }
        return true;
    }*/

}