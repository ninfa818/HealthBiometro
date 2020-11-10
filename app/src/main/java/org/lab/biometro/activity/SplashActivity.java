package org.lab.biometro.activity;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import org.lab.biometro.R;
import org.lab.biometro.util.AppUtil;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            AppUtil.showOtherActivity(this, LoginActivity.class, 0);
        }, 1500);
    }
}