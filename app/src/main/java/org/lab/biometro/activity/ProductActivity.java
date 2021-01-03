package org.lab.biometro.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lab.biometro.R;
import org.lab.biometro.listener.OnHttpListener;
import org.lab.biometro.model.UserModel;
import org.lab.biometro.util.HttpUtil;
import org.lab.biometro.util.SharedPreferenceUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ProductActivity extends BaseActivity {

    private ImageView img_back;
    private RelativeLayout rlt_toolbar_noti;

    private EditText txt_name, txt_phone;
    private UserModel userModel;

    private void initEvent() {
        img_back.setOnClickListener(view -> {
            String name = txt_name.getText().toString();
            String phone = txt_phone.getText().toString();
            boolean isEdited = false;
            if (!name.equals(userModel.safePerson) && name.length() > 0) {
                isEdited = true;
            }

            if (!phone.equals(userModel.safeMobile) && phone.length() > 0) {
                isEdited = true;
            }
            if (isEdited) {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + SharedPreferenceUtil.getToken());

                if (name.length() == 0) {
                    name = userModel.safePerson;
                }
                if (phone.length() == 0) {
                    phone = userModel.safeMobile;
                }
                Map<String, String> params = new HashMap<>();
                params.put("account", userModel.account);
                params.put("safePerson", name);
                params.put("safeMobile", phone);

                showProgress();
                HttpUtil.onHttpRequest(HttpUtil.url_setsafe, headers, params, new OnHttpListener() {
                    @Override
                    public void onEventCallBack(JSONObject obj, int ret) {
                        hideProgress();
                        if (ret == 1) {
                            Snackbar.make(getContentView(), R.string.success_set_safeuser, BaseTransientBottomBar.LENGTH_SHORT)
                                    .addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                                        @Override
                                        public void onDismissed(Snackbar transientBottomBar, int event) {
                                            super.onDismissed(transientBottomBar, event);
                                            onBackPressed();
                                        }
                                    }).show();
                        } else {
                            Snackbar.make(getContentView(), R.string.failed_set_safeuser, BaseTransientBottomBar.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onEventInternetError(Exception e) {
                        hideProgress();
                        Snackbar.make(getContentView(), Objects.requireNonNull(e.getMessage()), BaseTransientBottomBar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onEventServerError(Exception e) {
                        hideProgress();
                        Snackbar.make(getContentView(), Objects.requireNonNull(e.getMessage()), BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                });
            } else {
                onBackPressed();
            }
        });
        rlt_toolbar_noti.setOnClickListener(view -> {

        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        initView();
        initEvent();
    }

    @Override
    public void initView() {
        super.initView();

        img_back = findViewById(R.id.img_back);
        rlt_toolbar_noti = findViewById(R.id.rlt_toolbar_noti);

        txt_name = findViewById(R.id.txt_name);
        txt_phone = findViewById(R.id.txt_phone);

        initData();
    }

    private void initData() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + SharedPreferenceUtil.getToken());

        Map<String, String> params = new HashMap<>();
        params.put("account", SharedPreferenceUtil.getEmail());

        showProgress();
        HttpUtil.onHttpRequest(HttpUtil.url_safeinfo, headers, params, new OnHttpListener() {
            @Override
            public void onEventCallBack(JSONObject obj, int ret) {
                hideProgress();
                if (ret == 1) {
                    try {
                        JSONArray aryUser = obj.getJSONArray("payload");
                        JSONObject personJson = aryUser.getJSONObject(0);

                        userModel = SharedPreferenceUtil.getCurrentUser();
                        userModel.safePerson = personJson.getString("SafePerson");
                        userModel.safeMobile = personJson.getString("SafeMobile");
                        SharedPreferenceUtil.saveCurrentUser(userModel);

                        setData();
                    } catch (JSONException e) {
                        Snackbar.make(getContentView(), Objects.requireNonNull(e.getMessage()), BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                } else {
                    Snackbar.make(getContentView(), R.string.failed_get_user, BaseTransientBottomBar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onEventInternetError(Exception e) {
                hideProgress();
                Snackbar.make(getContentView(), Objects.requireNonNull(e.getMessage()), BaseTransientBottomBar.LENGTH_SHORT).show();
            }

            @Override
            public void onEventServerError(Exception e) {
                hideProgress();
                Snackbar.make(getContentView(), Objects.requireNonNull(e.getMessage()), BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
    }

    private void setData() {
        txt_name.setText(userModel.safePerson);
        txt_phone.setText(userModel.safeMobile);
    }

}
