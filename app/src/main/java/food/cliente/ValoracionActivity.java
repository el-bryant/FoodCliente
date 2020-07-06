package food.cliente;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.json.JSONArray;
import food.cliente.publico.AppController;
import food.cliente.publico.Funciones;

/**
 * By: El Bryant
 */

public class ValoracionActivity extends AppCompatActivity {
    TextView tvNombresRepartidor, tvApellidosRepartidor, tvNombreProducto, tvCantidad, tvPrecio, tvNombreProveedor, tvFechaPedido,
            tvDireccion, tvTotal;
    NetworkImageView nivFotoRepartidor;
    EditText etComentario;
    Button btnEnviarValoracion;
    RatingBar rbValoracion;
    DatabaseReference mDatabase;
    ImageLoader imgLoader = AppController.getInstance().getImageLoader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valoracion);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        tvNombresRepartidor = (TextView) findViewById(R.id.tvNombresRepartidor);
        tvApellidosRepartidor = (TextView) findViewById(R.id.tvApellidosRepartidor);
        tvNombreProducto = (TextView) findViewById(R.id.tvNombreProducto);
        tvCantidad = (TextView) findViewById(R.id.tvCantidad);
        tvPrecio = (TextView) findViewById(R.id.tvPrecio);
        tvNombreProveedor = (TextView) findViewById(R.id.tvNombreProveedor);
        tvFechaPedido = (TextView) findViewById(R.id.tvFechaPedido);
        tvDireccion = (TextView) findViewById(R.id.tvDireccion);
        tvTotal = (TextView) findViewById(R.id.tvTotal);
        nivFotoRepartidor = (NetworkImageView) findViewById(R.id.nivFotoRepartidor);
        etComentario = (EditText) findViewById(R.id.etComentario);
        btnEnviarValoracion = (Button) findViewById(R.id.btnEnviarValoracion);
        rbValoracion = (RatingBar) findViewById(R.id.rbValoracion);
        final String idPedido = getIntent().getStringExtra("idPedido");
        cargarDatos(idPedido);
        btnEnviarValoracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase = FirebaseDatabase.getInstance().getReference().child("pedidos").child(idPedido);
                mDatabase.removeValue();
            }
        });
    }

    public void cargarDatos(final String idPedido) {
        Log.i("cargarDatos", "ValoracionActivity");
        Thread tr = new Thread() {
            @Override
            public void run() {
                final String result = Funciones.primero("https://vespro.io/food/wsApp/datos_valoracion.php?id_pedido="
                        + idPedido);
                Log.i("cargarDatos", result);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int r = Funciones.segundo(result);
                        if (r > 0) {
                            try {
                                JSONArray jsonArray = new JSONArray(result);
                                if (jsonArray.length() > 0) {
                                    String nombresRepartidor = jsonArray.getJSONObject(0).getString("nombres");
                                    String apellidosRepartidor = jsonArray.getJSONObject(0).getString("apellidos");
                                    String nombreProducto = jsonArray.getJSONObject(0).getString("nombre_producto");
                                    String cantidad = jsonArray.getJSONObject(0).getString("cantidad");
                                    Double precio = jsonArray.getJSONObject(0).getDouble("precio");
                                    String nombreProveedor = jsonArray.getJSONObject(0).getString("nombre_proveedor");
                                    String fechaPedido = jsonArray.getJSONObject(0).getString("fecha") + " "
                                            + jsonArray.getJSONObject(0).getString("hora");
                                    String direccion = jsonArray.getJSONObject(0).getString("direccion");
                                    Double total = jsonArray.getJSONObject(0).getDouble("total");
                                    String imagen = jsonArray.getJSONObject(0).getString("imagen");
                                    String nombres = "", apellidos = "";
                                    if (nombresRepartidor.contains(" ")) {
                                        char[] caracteres_n = (nombresRepartidor.substring(0,
                                                nombresRepartidor.indexOf(" ")).toLowerCase()).toCharArray();
                                        caracteres_n[0] = Character.toUpperCase(caracteres_n[0]);
                                        for (int i = 0; i < nombresRepartidor.substring(0,
                                                nombresRepartidor.indexOf(" ")).length(); i ++) {
                                            if (caracteres_n[i] == ' ') {
                                                caracteres_n[i + 1] = Character.toUpperCase(caracteres_n[i + 1]);
                                            }
                                            nombres = nombres + caracteres_n[i];
                                        }
                                    } else {
                                        nombres = nombresRepartidor.substring(0, 1) + nombresRepartidor.substring(1,
                                                nombresRepartidor.length()).toLowerCase();
                                    }
                                    if (apellidosRepartidor.contains(" ")) {
                                        char[] caracteres_a = (apellidosRepartidor.substring(0,
                                                apellidosRepartidor.indexOf(" ")).toLowerCase()).toCharArray();
                                        caracteres_a[0] = Character.toUpperCase(caracteres_a[0]);
                                        for (int i = 0; i < apellidosRepartidor.substring(0,
                                                apellidosRepartidor.indexOf(" ")).length(); i ++) {
                                            if (caracteres_a[i] == ' ') {
                                                caracteres_a[i + 1] = Character.toUpperCase(caracteres_a[i + 1]);
                                            }
                                            apellidos = apellidos + caracteres_a[i];
                                        }
                                    } else {
                                        apellidos = apellidosRepartidor.substring(0, 1) + apellidosRepartidor.substring(1,
                                                apellidosRepartidor.length()).toLowerCase();
                                    }
                                    tvNombresRepartidor.setText(nombres);
                                    tvApellidosRepartidor.setText(apellidos);
                                    tvNombreProducto.setText(nombreProducto);
                                    tvCantidad.setText(cantidad);
                                    tvPrecio.setText("S/ " + String.format("%.2f", precio));
                                    tvNombreProveedor.setText(nombreProveedor);
                                    tvFechaPedido.setText(fechaPedido);
                                    tvDireccion.setText(direccion);
                                    tvTotal.setText("S/ " + String.format("%.2f", total));
                                    nivFotoRepartidor.setImageUrl(imagen, imgLoader);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        };
        tr.start();
    }
}
