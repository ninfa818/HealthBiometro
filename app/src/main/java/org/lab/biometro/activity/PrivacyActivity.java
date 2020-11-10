package org.lab.biometro.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.lab.biometro.R;

public class PrivacyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        ImageView img_close = findViewById(R.id.img_close);
        img_close.setOnClickListener(view -> onBackPressed());

        Button btn_confirm = findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(view -> onBackPressed());
    }
}