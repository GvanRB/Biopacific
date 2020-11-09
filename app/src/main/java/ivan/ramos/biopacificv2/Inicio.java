package ivan.ramos.biopacificv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Inicio extends AppCompatActivity implements View.OnClickListener {
    private Button btnIrInicioSesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        btnIrInicioSesion = findViewById(R.id.btnSiguiente1);
        btnIrInicioSesion.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSiguiente1:
                Intent irLogin = new Intent(Inicio.this, Login.class);
                startActivity(irLogin);
                break;
            default:
                Toast.makeText(getApplicationContext(),
                        "Error control no declarado",
                        Toast.LENGTH_LONG).show();
                break;
        }
    }
}