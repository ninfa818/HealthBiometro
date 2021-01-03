package org.lab.biometro.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import org.lab.biometro.R;

public class NotiDetailActivity extends AppCompatActivity {

    private ImageView img_back;
    private RelativeLayout rlt_toolbar_noti;

    private void initEvent() {
        img_back.setOnClickListener(view -> onBackPressed());
        rlt_toolbar_noti.setOnClickListener(view -> {

        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti_detail);

        initView();
        initEvent();
    }

    private void initView() {
        img_back = findViewById(R.id.img_back);
        rlt_toolbar_noti = findViewById(R.id.rlt_toolbar_noti);

    }

    public void onClickListButton(View view) {
        onBackPressed();
    }
}
