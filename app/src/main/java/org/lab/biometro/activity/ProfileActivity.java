package org.lab.biometro.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.lab.biometro.R;
import org.lab.biometro.model.UserModel;
import org.lab.biometro.util.SharedPreferenceUtil;

public class ProfileActivity extends AppCompatActivity {

    private ImageView img_back;
    private RelativeLayout rlt_toolbar_noti;

    private TextView lbl_header_name;
    private EditText txt_name, txt_phone, txt_email;

    private void initEvent() {
        img_back.setOnClickListener(view -> onBackPressed());
        rlt_toolbar_noti.setOnClickListener(view -> {

        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initView();
        initEvent();
    }

    private void initView() {
        img_back = findViewById(R.id.img_back);
        rlt_toolbar_noti = findViewById(R.id.rlt_toolbar_noti);

        lbl_header_name = findViewById(R.id.lbl_header_name);
        txt_name = findViewById(R.id.txt_name);
        txt_phone = findViewById(R.id.txt_phone);
        txt_email = findViewById(R.id.txt_email);

        initData();
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        UserModel currentUser = SharedPreferenceUtil.getCurrentUser();

        lbl_header_name.setText(currentUser.memberName + " 님");
        txt_name.setText(currentUser.memberName);
        txt_phone.setText(currentUser.mobile);
        txt_email.setText(currentUser.account);
    }


}
