package org.lab.biometro;

import android.app.Application;

import org.lab.biometro.util.SharedPreferenceUtil;

public class Biometro extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferenceUtil.getInstance(this);
    }
}
