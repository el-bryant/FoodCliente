package food.cliente;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
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
import org.json.JSONArray;
import java.util.ArrayList;
import food.cliente.adapter.AcercaDeAdapter;
import food.cliente.entity.AcercaDe;
import food.cliente.publico.Funciones;
import food.cliente.publico.PrefUtil;

/**
 * By: El Bryant
 */

public class PreguntasActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    PrefUtil prefUtil;
    ImageView ivMenu;
    LinearLayout ivCerrar;
    private String nombre_nav = "";
    TextView tvNombreNav;
    AcercaDeAdapter acercaDeAdapter;
    ArrayList<AcercaDe> acercaDe;
    RecyclerView rvPreguntas;
    public static FrameLayout cvContenido;
    public static TextView tvTituloCard, tvContenidoCard;
    Button btnAceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        NavigationView nav = (NavigationView) findViewById(R.id.nav_view);
        nav.setNavigationItemSelectedListener(this);
        tvNombreNav = (TextView) nav.getHeaderView(0).findViewById(R.id.tvNombreNav);
        ivMenu = (ImageView) findViewById(R.id.ivMenu);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ivCerrar = (LinearLayout) findViewById(R.id.ivCerrar);
        rvPreguntas = (RecyclerView) findViewById(R.id.rvPreguntas);
        cvContenido = (FrameLayout) findViewById(R.id.cvContenido);
        tvTituloCard = (TextView) findViewById(R.id.tvTituloCard);
        tvContenidoCard = (TextView) findViewById(R.id.tvContenidoCard);
        btnAceptar = (Button) findViewById(R.id.btnAceptar);
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
        ivCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefUtil.clearAll();
                Intent intent = new Intent(PreguntasActivity.this, AccesoActivity.class);
                startActivity(intent);
                finish();
            }
        });
        ImageView fab = (ImageView) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PreguntasActivity.this, CarritoActivity.class);
                startActivity(intent);
            }
        });
        rvPreguntas.setHasFixedSize(true);
        rvPreguntas.setLayoutManager(new LinearLayoutManager(this));
        cargarPreguntas();
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cvContenido.setVisibility(View.GONE);
                tvTituloCard.setText("");
                tvContenidoCard.setText("");
            }
        });
    }

    public void cargarPreguntas() {
        Log.i("cargarPreguntas", "PreguntasActivity");
        acercaDe = new ArrayList<>();
        Thread tr = new Thread() {
            @Override
            public void run() {
                final String result = Funciones.primero("https://vespro.io/food/wsApp/obtener_preguntas.php");
                Log.i("cargarPreguntas", result);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int r = Funciones.segundo(result);
                        if (r > 0) {
                            try {
                                JSONArray jsonArray = new JSONArray(result);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    String idAcercaDe = jsonArray.getJSONObject(i).getString("id_acerca_de");
                                    String titulo = jsonArray.getJSONObject(i).getString("titulo");
                                    String contenido = jsonArray.getJSONObject(i).getString("contenido");
                                    acercaDe.add(new AcercaDe(idAcercaDe, titulo, contenido));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        acercaDeAdapter = new AcercaDeAdapter(PreguntasActivity.this, acercaDe);
                        rvPreguntas.setAdapter(acercaDeAdapter);
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
            Intent intent = new Intent(PreguntasActivity.this, CategoriaActivity.class);
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
                intent = new Intent(PreguntasActivity.this, CategoriaActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.ivCerrar:
                prefUtil.clearAll();
                intent = new Intent(PreguntasActivity.this, AccesoActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.navNotificaciones:
                intent = new Intent(PreguntasActivity.this, NotificacionesActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.navPedidos:
                intent = new Intent(PreguntasActivity.this, PedidosActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.navPerfil:
                intent = new Intent(PreguntasActivity.this, PerfilActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.navPreguntas:
                intent = new Intent(PreguntasActivity.this, PreguntasActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.navPromociones:
                intent = new Intent(PreguntasActivity.this, PromocionesActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
