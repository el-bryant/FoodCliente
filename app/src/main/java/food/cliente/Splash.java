package food.cliente;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import food.cliente.publico.PrefUtil;

/**
 * By: El Bryant
 */

public class Splash extends AppCompatActivity {
    ProgressBar pbSplash;
    Handler handler;
    PrefUtil prefUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        pbSplash = (ProgressBar) findViewById(R.id.pbSplash);
        prefUtil = new PrefUtil(this);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (prefUtil.getStringValue(PrefUtil.LOGIN_STATUS).equals("1")) {
                    Intent intent = new Intent(Splash.this, CategoriaActivity.class);
                    startActivity(intent);
                    intent.putExtra("nombre", prefUtil.getStringValue("nombre"));
                    finish();
                } else {
                    Intent intent = new Intent(Splash.this, AccesoActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Splash", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("Splash", msg);
//                        Toast.makeText(Splash.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void runtimeEnableAutoInit() {
        // [START fcm_runtime_enable_auto_init]
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        // [END fcm_runtime_enable_auto_init]
    }
}
