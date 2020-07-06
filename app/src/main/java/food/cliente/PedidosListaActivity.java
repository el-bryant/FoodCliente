package food.cliente;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.navigation.NavigationView;
import org.json.JSONArray;
import java.util.ArrayList;
import food.cliente.adapter.PedidoListaAdapter;
import food.cliente.entity.PedidoLista;
import food.cliente.publico.Funciones;
import food.cliente.publico.PrefUtil;

/**
 * By: El Bryant
 */

public class PedidosListaActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView rvPedidosLista;
    ArrayList<PedidoLista> pedidoLista;
    PedidoListaAdapter pedidoListaAdapter;
    ImageView ivMenu;
    LinearLayout ivCerrar;
    PrefUtil prefUtil;
    private String nombre_nav = "";
    TextView tvNombreNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_lista);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        String fechaDesde = getIntent().getStringExtra("fecha_inicio");
        String fechaHasta = getIntent().getStringExtra("fecha_fin");
        Toast.makeText(this, "fechas: " + fechaDesde + " / " + fechaHasta, Toast.LENGTH_SHORT).show();
        NavigationView nav = (NavigationView) findViewById(R.id.nav_view);
        nav.setNavigationItemSelectedListener(this);
        tvNombreNav = (TextView) nav.getHeaderView(0).findViewById(R.id.tvNombreNav);
        ivMenu = (ImageView) findViewById(R.id.ivMenu);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ivCerrar = (LinearLayout) findViewById(R.id.ivCerrar);
        rvPedidosLista = (RecyclerView) findViewById(R.id.rvPedidos);
        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        prefUtil = new PrefUtil(this);
        if (prefUtil.getStringValue("nombre").contains(" ")) {
            char[] caracteres_nav = (prefUtil.getStringValue("nombre").substring(0,
                    prefUtil.getStringValue("nombre").indexOf(" ")).toLowerCase()).toCharArray();
            caracteres_nav[0] = Character.toUpperCase(caracteres_nav[0]);
            for (int i = 0; i < prefUtil.getStringValue("nombre").substring(0,
                    prefUtil.getStringValue("nombre").indexOf(" ")).length(); i ++) {
                if (caracteres_nav[i] == ' ') {
                    caracteres_nav[i + 1] = Character.toUpperCase(caracteres_nav[i + 1]);
                }
                nombre_nav = nombre_nav + caracteres_nav[i];
            }
        } else {
            nombre_nav = prefUtil.getStringValue("nombre").substring(0, 1) +
                    prefUtil.getStringValue("nombre").substring(1,
                            prefUtil.getStringValue("nombre").length()).toLowerCase();
        }
        tvNombreNav.setText("¡Hola, " + nombre_nav + "!");
        rvPedidosLista.setHasFixedSize(true);
        rvPedidosLista.setLayoutManager(new LinearLayoutManager(this));
        cargarDatos(fechaDesde, fechaHasta);
    }

    public void cargarDatos(final String fechaDesde, final String fechaHasta) {
        Log.i("cargarDatos", "PedidosListaActivity");
        pedidoLista = new ArrayList<>();
        Thread tr = new Thread() {
            @Override
            public void run() {
                final String result = Funciones.primero("https://vespro.io/food/wsApp/obtener_pedidos.php?fecha_desde="
                        + fechaDesde + "&fecha_hasta=" + fechaHasta + "&dni_cliente=" + prefUtil.getStringValue("dni_cliente"));
                Log.i("cargarDatos", result);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int r = Funciones.segundo(result);
                        if (r > 0) {
                            try {
                                JSONArray jsonArray = new JSONArray(result);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    String idPedido = jsonArray.getJSONObject(i).getString("id_detalle_pedido");
                                    String iconoTienda = jsonArray.getJSONObject(i).getString("imagen");
                                    String fecha = jsonArray.getJSONObject(i).getString("fecha");
                                    String hora = jsonArray.getJSONObject(i).getString("hora");
                                    String nombreProducto = jsonArray.getJSONObject(i).getString("nombre");
                                    String monto = "S/ " + jsonArray.getJSONObject(i).getString("total");
                                    pedidoLista.add(new PedidoLista(idPedido, iconoTienda, fecha, hora, nombreProducto, monto));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        pedidoListaAdapter = new PedidoListaAdapter(PedidosListaActivity.this, pedidoLista);
                        rvPedidosLista.setAdapter(pedidoListaAdapter);
                    }
                });
            }
        };
        tr.start();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(PedidosListaActivity.this, PedidosActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        Intent intent;
        switch (id) {
            case R.id.navCategorias:
                intent = new Intent(PedidosListaActivity.this, CategoriaActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.ivCerrar:
                prefUtil.clearAll();
                intent = new Intent(PedidosListaActivity.this, AccesoActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.navNotificaciones:
                intent = new Intent(PedidosListaActivity.this, NotificacionesActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.navPedidos:
                intent = new Intent(PedidosListaActivity.this, PedidosActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.navPerfil:
                intent = new Intent(PedidosListaActivity.this, PerfilActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.navPreguntas:
                intent = new Intent(PedidosListaActivity.this, PreguntasActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.navPromociones:
                intent = new Intent(PedidosListaActivity.this, PromocionesActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
