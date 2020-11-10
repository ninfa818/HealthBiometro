package org.lab.biometro.activity;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import org.lab.biometro.R;
import org.lab.biometro.util.AppUtil;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        final Handler handler = new Handler();
        handler.postDelayed(() -> AppUtil.showOtherActivity(this, SplashActivity.class, 0), 1500);
    }

}