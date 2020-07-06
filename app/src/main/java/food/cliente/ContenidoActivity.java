package food.cliente;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

/**
 * By: El Bryant
 */

public class ContenidoActivity extends AppCompatActivity {
    TextView tvTitulo, tvContenido;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenido);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        tvTitulo = (TextView) findViewById(R.id.tvTitulo);
        tvContenido = (TextView) findViewById(R.id.tvContenido);
        String titulo = getIntent().getStringExtra("titulo");
        String contenido = getIntent().getStringExtra("contenido");
        tvTitulo.setText(titulo);
        tvContenido.setText(contenido);
    }
}
