package ivan.ramos.biopacificv2.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.github.barteksc.pdfviewer.PDFView;

import ivan.ramos.biopacificv2.R;
import ivan.ramos.biopacificv2.adapters.AdapterServiciosXmascota;

public class PdfViewResult extends AppCompatActivity {
    ProgressBar progressBar;
    PDFView pdfView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewresult);
        pdfView = findViewById(R.id.pdfview);
        progressBar = findViewById(R.id.progressBar);
        toolbar = findViewById(R.id.toolbarFact);
        setSupportActionBar(toolbar);

        new PdfLeer(pdfView,progressBar).execute(AdapterServiciosXmascota.result);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_download, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.download:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(AdapterServiciosXmascota.result)));
        }
        return super.onOptionsItemSelected(item);
    }
}