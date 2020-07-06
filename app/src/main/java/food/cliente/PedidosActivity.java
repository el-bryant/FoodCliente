package food.cliente;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import java.util.Calendar;
import food.cliente.publico.PrefUtil;

/**
 * By: El Bryant
 */

public class PedidosActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    PrefUtil prefUtil;
    ImageView ivMenu;
    LinearLayout ivCerrar;
    private String nombre_nav = "";
    TextView tvNombreNav;
    CardView cvHoy, cvSemana, cvMes, cvAnio, cvEntreFechas;
    private int diaDesde, mesDesde, anioDesde, diaHasta, mesHasta, anioHasta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        NavigationView nav = (NavigationView) findViewById(R.id.nav_view);
        nav.setNavigationItemSelectedListener(this);
        tvNombreNav = (TextView) nav.getHeaderView(0).findViewById(R.id.tvNombreNav);
        ivMenu = (ImageView) findViewById(R.id.ivMenu);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ivCerrar = (LinearLayout) findViewById(R.id.ivCerrar);
        cvHoy = (CardView) findViewById(R.id.cvHoy);
        cvSemana = (CardView) findViewById(R.id.cvSemana);
        cvMes = (CardView) findViewById(R.id.cvMes);
        cvAnio = (CardView) findViewById(R.id.cvAnio);
        cvEntreFechas = (CardView) findViewById(R.id.cvEntreFechas);
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
                Intent intent = new Intent(PedidosActivity.this, AccesoActivity.class);
                startActivity(intent);
                finish();
            }
        });
        cvHoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                diaDesde = calendar.get(Calendar.DAY_OF_MONTH);
                mesDesde = calendar.get(Calendar.MONTH);
                anioDesde = calendar.get(Calendar.YEAR);
                diaHasta = calendar.get(Calendar.DAY_OF_MONTH);
                mesHasta = calendar.get(Calendar.MONTH);
                anioHasta = calendar.get(Calendar.YEAR);
                String fechaDesde = anioDesde + "-" + (mesDesde + 1) + "-" + diaDesde;
                String fechaHasta = anioHasta + "-" + (mesHasta + 1) + "-" + diaHasta;
                Intent intent = new Intent(PedidosActivity.this, PedidosListaActivity.class);
                intent.putExtra("fecha_inicio", fechaDesde);
                intent.putExtra("fecha_fin", fechaHasta);
                startActivity(intent);
                finish();
            }
        });
        cvSemana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                diaDesde = calendar.get(Calendar.DAY_OF_MONTH);
                mesDesde = calendar.get(Calendar.MONTH);
                anioDesde = calendar.get(Calendar.YEAR);
                calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
                diaHasta = calendar.get(Calendar.DAY_OF_MONTH);
                mesHasta = calendar.get(Calendar.MONTH);
                anioHasta = calendar.get(Calendar.YEAR);
                String fechaDesde = anioDesde + "-" + (mesDesde + 1) + "-" + diaDesde;
                String fechaHasta = anioHasta + "-" + (mesHasta + 1) + "-" + diaHasta;
                Intent intent = new Intent(PedidosActivity.this, PedidosListaActivity.class);
                intent.putExtra("fecha_inicio", fechaDesde);
                intent.putExtra("fecha_fin", fechaHasta);
                startActivity(intent);
                finish();
            }
        });
        cvMes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                diaDesde = 1;
                mesDesde = calendar.get(Calendar.MONTH);
                anioDesde = calendar.get(Calendar.YEAR);
                diaHasta = calendar.get(Calendar.DAY_OF_MONTH);
                mesHasta = calendar.get(Calendar.MONTH);
                anioHasta = calendar.get(Calendar.YEAR);
                String fechaDesde = anioDesde + "-" + (mesDesde + 1) + "-" + diaDesde;
                String fechaHasta = anioHasta + "-" + (mesHasta + 1) + "-" + diaHasta;
                Intent intent = new Intent(PedidosActivity.this, PedidosListaActivity.class);
                intent.putExtra("fecha_inicio", fechaDesde);
                intent.putExtra("fecha_fin", fechaHasta);
                startActivity(intent);
                finish();
            }
        });
        cvAnio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                diaDesde = 1;
                mesDesde = 0;
                anioDesde = calendar.get(Calendar.YEAR);
                diaHasta = calendar.get(Calendar.DAY_OF_MONTH);
                mesHasta = calendar.get(Calendar.MONTH);
                anioHasta = calendar.get(Calendar.YEAR);
                String fechaDesde = anioDesde + "-" + (mesDesde + 1) + "-" + diaDesde;
                String fechaHasta = anioHasta + "-" + (mesHasta + 1) + "-" + diaHasta;
                Intent intent = new Intent(PedidosActivity.this, PedidosListaActivity.class);
                intent.putExtra("fecha_inicio", fechaDesde);
                intent.putExtra("fecha_fin", fechaHasta);
                startActivity(intent);
                finish();
            }
        });
//        cvEntreFechas.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Calendar calendar = Calendar.getInstance();
//                diaDesde = calendar.get(Calendar.DAY_OF_MONTH);
//                mesDesde = calendar.get(Calendar.MONTH);
//                anioDesde = calendar.get(Calendar.YEAR);
//                diaHasta = calendar.get(Calendar.DAY_OF_MONTH);
//                mesHasta = calendar.get(Calendar.MONTH);
//                anioHasta = calendar.get(Calendar.YEAR);
//                String fechaDesde = anioDesde + "-" + (mesDesde + 1) + "-" + diaDesde;
//                String fechaHasta = anioHasta + "-" + (mesHasta + 1) + "-" + diaHasta;
//                Intent intent = new Intent(PedidosActivity.this, PedidosListaActivity.class);
//                intent.putExtra("fecha_inicio", fechaDesde);
//                intent.putExtra("fecha_fin", fechaHasta);
//                startActivity(intent);
//                finish();
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(PedidosActivity.this, CategoriaActivity.class);
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
                intent = new Intent(PedidosActivity.this, CategoriaActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.ivCerrar:
                prefUtil.clearAll();
                intent = new Intent(PedidosActivity.this, AccesoActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.navNotificaciones:
                intent = new Intent(PedidosActivity.this, NotificacionesActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.navPedidos:
                intent = new Intent(PedidosActivity.this, PedidosActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.navPerfil:
                intent = new Intent(PedidosActivity.this, PerfilActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.navPreguntas:
                intent = new Intent(PedidosActivity.this, PreguntasActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.navPromociones:
                intent = new Intent(PedidosActivity.this, PromocionesActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
