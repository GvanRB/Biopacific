package ivan.ramos.biopacificv2.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import ivan.ramos.biopacificv2.MainActivity;
import ivan.ramos.biopacificv2.R;
import ivan.ramos.biopacificv2.adapters.AdapterOnboarding;
import ivan.ramos.biopacificv2.models.onboardingModel;
import me.relex.circleindicator.CircleIndicator3;

public class ViewPager2 extends AppCompatActivity {
    private AdapterOnboarding AdapterOnboarding;
    private MaterialButton button, back;
    private TextView skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager2);
        SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
        skip = findViewById(R.id.skip);
        button=findViewById(R.id.buttonOrboarding);
        back=findViewById(R.id.back);
        setupOnboardingmodels();
        androidx.viewpager2.widget.ViewPager2 Viewpager = findViewById(R.id.onboardingViewpager);
        Viewpager.setAdapter(AdapterOnboarding);
        CircleIndicator3 circle = findViewById(R.id.visorpuntos);
        circle.setViewPager(Viewpager);

        if (preferences.contains("usuario")){
            startActivity(new Intent(ViewPager2.this, MainActivity.class));
            finish();
        }else{
            Viewpager.registerOnPageChangeCallback(new androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    if(position == AdapterOnboarding.getItemCount()-1){
                        button.setText("Comenzar");
                    }else{
                        button.setText("Siguiente");
                    }
                }
            });
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Viewpager.getCurrentItem() + 1 < AdapterOnboarding.getItemCount()){
                    Viewpager.setCurrentItem(Viewpager.getCurrentItem()+1);
                }else{
                    startActivity(new Intent(ViewPager2.this, Login.class));
                    finish();
                }
            }
        });
        //back.setText("Atrás");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Viewpager.setCurrentItem(Viewpager.getCurrentItem()-1);

            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewPager2.this, Login.class));
                finish();
            }
        });
    }
    private void setupOnboardingmodels(){
        List<onboardingModel> onboardingModels = new ArrayList<>();
        onboardingModel itemsinfo = new onboardingModel();
        itemsinfo.setTitle("¡Empezamos!");
        itemsinfo.setDescrip("Solicite sus servicios desde cualquier vista de forma segura y sencilla");
        itemsinfo.setImage(R.drawable.biopacific2);

        onboardingModel itemsinstr = new onboardingModel();
        itemsinstr.setTitle("¿Cómo solicitar un servicio?");
        itemsinstr.setDescrip("Debes registrar antes a las mascotas y medicos veterinarios y dirigirte a la vista \"solicitar servicios\"");
        itemsinstr.setImage(R.drawable.vista2);

        onboardingModel itemshelp = new onboardingModel();
        itemshelp.setTitle("Visualice el estado del servicio");
        itemshelp.setDescrip("Al presionar en una mascota podra ver sus servicios solicitados y el estado en que se encuentren");
        itemshelp.setImage(R.drawable.vista3);

        onboardingModel itemsmedic = new onboardingModel();
        itemsmedic.setTitle("¿Cómo visualizo los resultados?");
        itemsmedic.setDescrip("Solo si el estado del servicio esta finalizado, podra visualizar sus resultados");
        itemsmedic.setImage(R.drawable.vista4);

        onboardingModels.add(itemsinfo);
        onboardingModels.add(itemsinstr);
        onboardingModels.add(itemshelp);
        onboardingModels.add(itemsmedic);

        AdapterOnboarding = new AdapterOnboarding(onboardingModels);
    }


}