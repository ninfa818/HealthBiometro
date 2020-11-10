package org.lab.biometro.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.lab.biometro.R;
import org.lab.biometro.dialog.AlertOneDialog;
import org.lab.biometro.listener.OnClickAlertDialogListener;
import org.lab.biometro.util.AppUtil;

public class LoginActivity extends AppCompatActivity {

    private EditText txt_email, txt_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        txt_email = findViewById(R.id.txt_email);
        txt_pass = findViewById(R.id.txt_password);
    }

    public void onClickLoginButton(View view) {
        String email = txt_email.getText().toString();
        String password = txt_pass.getText().toString();
        if (email.isEmpty()) {
            Toast.makeText(this, R.string.toast_empty_email, Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()) {
            Toast.makeText(this, R.string.toast_empty_pass, Toast.LENGTH_SHORT).show();
            return;
        }
        if (email.equals("sample@naver.com") && password.equals("123456")) {
            AlertOneDialog alt_success = new AlertOneDialog(this
                    , getString(R.string.login_success)
                    , getString(R.string.login_success_desc)
                    , getString(R.string.confirm));
            alt_success.setOnClickAlertDialogListener(new OnClickAlertDialogListener() {
                @Override
                public void onClickConfirmButton() {
                    AppUtil.showOtherActivity(LoginActivity.this, MainActivity.class, 0);
                }
            });
            alt_success.show();
        } else {
            AlertOneDialog alt_failed = new AlertOneDialog(this
                    , getString(R.string.login_failed)
                    , getString(R.string.login_failed_desc)
                    , getString(R.string.confirm));
            alt_failed.show();
        }
    }

    public void onClickRegisterLbl(View view) {
        AppUtil.showOtherActivity(this, RegisterActivity.class, 0);
    }

}