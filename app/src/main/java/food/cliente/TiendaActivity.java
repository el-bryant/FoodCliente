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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.json.JSONArray;
import java.util.ArrayList;
import food.cliente.adapter.TiendaAdapter;
import food.cliente.entity.Tienda;
import food.cliente.publico.Funciones;
import food.cliente.publico.PrefUtil;

/**
 * By: El Bryant
 */

public class TiendaActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static String id_categoria;
    private String nombre_nav = "";
    private ArrayList<Tienda> tiendas;
    private RecyclerView rvTienda;
    private TiendaAdapter tiendaAdapter;
    PrefUtil prefUtil;
    ImageView ivMenu;
    LinearLayout ivCerrar;
    TextView tvNombreNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        ImageView fab = (ImageView) findViewById(R.id.fab);
        rvTienda = (RecyclerView) findViewById(R.id.rvTienda);
        ivMenu = (ImageView) findViewById(R.id.ivMenu);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TiendaActivity.this, CarritoActivity.class);
                startActivity(intent);
            }
        });
        NavigationView nav = (NavigationView) findViewById(R.id.nav_view);
        nav.setNavigationItemSelectedListener(this);
        tvNombreNav = (TextView) nav.getHeaderView(0).findViewById(R.id.tvNombreNav);
        ivCerrar = (LinearLayout) findViewById(R.id.ivCerrar);
        prefUtil = new PrefUtil(this);
        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        rvTienda.setHasFixedSize(true);
        rvTienda.setLayoutManager(new LinearLayoutManager(this));
        id_categoria = getIntent().getStringExtra("id_categoria");
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
        cargarDatos();
        ivCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefUtil.clearAll();
                Intent intent = new Intent(TiendaActivity.this, AccesoActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void cargarDatos() {
        Log.i("cargarDatos", "TiendaActivity");
        tiendas = new ArrayList<>();
        Thread tr = new Thread() {
            @Override
            public void run() {
                final String result = Funciones.primero("https://vespro.io/food/wsApp/obtener_tiendas.php?id_categoria="
                        + id_categoria);
                Log.i("cargarDatos", result);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int r = Funciones.segundo(result);
                        if (r > 0) {
                            try {
                                JSONArray jsonArray = new JSONArray(result);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    tiendas.add(new Tienda(jsonArray.getJSONObject(i).getLong("id_proveedor"),
                                            jsonArray.getJSONObject(i).getString("proveedor"),
                                            jsonArray.getJSONObject(i).getString("imagen")));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        tiendaAdapter = new TiendaAdapter(TiendaActivity.this, tiendas);
                        rvTienda.setAdapter(tiendaAdapter);
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
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        Intent intent;
        switch (id) {
            case R.id.navCategorias:
                intent = new Intent(TiendaActivity.this, CategoriaActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.ivCerrar:
                prefUtil.clearAll();
                intent = new Intent(TiendaActivity.this, AccesoActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.navNotificaciones:
                intent = new Intent(TiendaActivity.this, NotificacionesActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.navPedidos:
                intent = new Intent(TiendaActivity.this, PedidosActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.navPerfil:
                intent = new Intent(TiendaActivity.this, PerfilActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.navPreguntas:
                intent = new Intent(TiendaActivity.this, PreguntasActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.navPromociones:
                intent = new Intent(TiendaActivity.this, PromocionesActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
