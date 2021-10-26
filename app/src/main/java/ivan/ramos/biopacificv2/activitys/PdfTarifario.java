package ivan.ramos.biopacificv2.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import ivan.ramos.biopacificv2.R;

public class PdfTarifario extends AppCompatActivity {
    PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdftarifario);
        pdfView = findViewById(R.id.pdfTarifario);
        pdfView.fromAsset("tarifario.pdf").scrollHandle(new DefaultScrollHandle(this)).load();
    }
}