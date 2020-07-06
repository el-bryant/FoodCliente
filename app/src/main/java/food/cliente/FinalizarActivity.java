package food.cliente;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import food.cliente.publico.PrefUtil;

/**
 * By: El Bryant
 */

public class FinalizarActivity extends AppCompatActivity {
    Button btnAceptar;
    PrefUtil prefUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        btnAceptar = (Button) findViewById(R.id.btnAceptar);
        prefUtil = new PrefUtil(this);
        prefUtil.saveGenericValue("espera", "si");
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinalizarActivity.this, CategoriaActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}