package org.lab.biometro.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;
import org.lab.biometro.R;
import org.lab.biometro.dialog.AlertOneDialog;
import org.lab.biometro.listener.OnClickAlertDialogListener;
import org.lab.biometro.listener.OnHttpListener;
import org.lab.biometro.util.AppUtil;
import org.lab.biometro.util.HttpUtil;
import org.lab.biometro.util.SharedPreferenceUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private View content;
    private ProgressDialog dialog;
    private EditText txt_email, txt_pass;
    private CheckBox chk_saved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        content = findViewById(R.id.content);
        dialog = ProgressDialog.show(this, "", getString(R.string.alt_connect_server));
        dialog.dismiss();

        txt_email = findViewById(R.id.txt_email);
        txt_pass = findViewById(R.id.txt_password);
        chk_saved = findViewById(R.id.chk_saved);

        if (SharedPreferenceUtil.isSaved()) {
            String emailStr = SharedPreferenceUtil.getEmail();
            String passStr = SharedPreferenceUtil.getPassword();
            txt_email.setText(emailStr);
            txt_pass.setText(passStr);
            chk_saved.setChecked(true);
        }
    }

    public void onClickLoginButton(View view) {
        String email = txt_email.getText().toString();
        String password = txt_pass.getText().toString();
        if (email.isEmpty()) {
            Snackbar.make(content, R.string.toast_empty_email, BaseTransientBottomBar.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()) {
            Snackbar.make(content, R.string.toast_empty_pass, BaseTransientBottomBar.LENGTH_SHORT).show();
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put("account", email);
        params.put("pwd", password);
        dialog.show();
        HttpUtil.onHttpRequest(HttpUtil.url_login, params, new OnHttpListener() {
            @Override
            public void onEventCallBack(JSONObject obj, int ret) {
                dialog.dismiss();
                if (ret == 1) {
                    if (chk_saved.isChecked()) {
                        SharedPreferenceUtil.setSaved(true);
                        SharedPreferenceUtil.setEmail(email);
                        SharedPreferenceUtil.setPassword(password);
                    }
                    Log.d("url_login", obj.toString());
                    AlertOneDialog alt_success = new AlertOneDialog(LoginActivity.this
                            , getString(R.string.login_success)
                            , getString(R.string.login_success_desc)
                            , getString(R.string.confirm));
                    alt_success.setOnClickAlertDialogListener(new OnClickAlertDialogListener() {
                        @Override
                        public void onClickConfirmButton() {
                            try {
                                String token = obj.getString("token");
                                SharedPreferenceUtil.setToken(token);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            AppUtil.showOtherActivity(LoginActivity.this, MainActivity.class, 0);
                        }
                    });
                    alt_success.show();
                } else {
                    AlertOneDialog alt_failed = new AlertOneDialog(LoginActivity.this
                            , getString(R.string.login_failed)
                            , getString(R.string.login_failed_desc)
                            , getString(R.string.confirm));
                    alt_failed.show();
                }
            }

            @Override
            public void onEventInternetError(Exception e) {
                dialog.dismiss();
                Snackbar.make(content, Objects.requireNonNull(e.getMessage()), BaseTransientBottomBar.LENGTH_SHORT).show();
            }

            @Override
            public void onEventServerError(Exception e) {
                dialog.dismiss();
                Snackbar.make(content, Objects.requireNonNull(e.getMessage()), BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
    }

    public void onClickRegisterLbl(View view) {
        AppUtil.showOtherActivity(this, RegisterActivity.class, 0);
    }

}