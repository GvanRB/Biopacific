package ivan.ramos.biopacificv2.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import ivan.ramos.biopacificv2.R;


public class Splash extends AppCompatActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final Intent intLogin = new Intent(this, ViewPager2.class);

        Thread timer = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1900);
                } catch (InterruptedException ex) {

                } finally {
                    startActivity(intLogin);
                    finish();
                }
            }
        };
        timer.start();
    }

}