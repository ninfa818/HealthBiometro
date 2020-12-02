package org.lab.biometro.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;
import org.lab.biometro.R;
import org.lab.biometro.fragment.HomeFragment;
import org.lab.biometro.fragment.MonitorFragment;
import org.lab.biometro.fragment.SettingFragment;
import org.lab.biometro.listener.OnGetUserInfo;
import org.lab.biometro.listener.OnHttpListener;
import org.lab.biometro.util.AppUtil;
import org.lab.biometro.util.HttpUtil;
import org.lab.biometro.util.SharedPreferenceUtil;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public BottomNavigationView nav_bottom;
    private ImageView img_toolbar_back, img_toolbar_logo;
    private View viw_toolbar_badge;
    private TextView lbl_toolbar_title;
    private RelativeLayout rlt_toolbar_noti;

    private OnGetUserInfo onGetUserInfo;

    private void initEvent() {
        img_toolbar_back.setOnClickListener(view -> nav_bottom.setSelectedItemId(R.id.navigation_home));
        rlt_toolbar_noti.setOnClickListener(view -> {

        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        initEvent();
    }

    private void initData() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + SharedPreferenceUtil.getToken());

        showProgress();
        HttpUtil.onHttpRequest(HttpUtil.url_userinfo, headers, new HashMap<>(), new OnHttpListener() {
            @Override
            public void onEventCallBack(JSONObject obj, int ret) {
                hideProgress();
//                if (ret == 1) {
//
//                } else {
//                    Snackbar.make(getContentView(), R.string.failed_get_user, BaseTransientBottomBar.LENGTH_SHORT)
//                            .addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
//                                @Override
//                                public void onDismissed(Snackbar transientBottomBar, int event) {
//                                    super.onDismissed(transientBottomBar, event);
//                                    MainActivity.this.existApp();
//                                }
//                            }).show();
//                }
                Snackbar.make(getContentView(), R.string.failed_get_user, BaseTransientBottomBar.LENGTH_SHORT)
                        .addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                            @Override
                            public void onDismissed(Snackbar transientBottomBar, int event) {
                                super.onDismissed(transientBottomBar, event);
                                MainActivity.this.existApp();
                            }
                        }).show();
            }

            @Override
            public void onEventInternetError(Exception e) {
                hideProgress();
                Snackbar.make(getContentView(), e.getMessage(), BaseTransientBottomBar.LENGTH_SHORT)
                        .addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                            @Override
                            public void onDismissed(Snackbar transientBottomBar, int event) {
                                super.onDismissed(transientBottomBar, event);
                                MainActivity.this.existApp();
                            }
                        }).show();
            }

            @Override
            public void onEventServerError(Exception e) {
                hideProgress();
                Snackbar.make(getContentView(), e.getMessage(), BaseTransientBottomBar.LENGTH_SHORT)
                        .addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                            @Override
                            public void onDismissed(Snackbar transientBottomBar, int event) {
                                super.onDismissed(transientBottomBar, event);
                                MainActivity.this.existApp();
                            }
                        }).show();
            }
        });
    }

    private void loadFragmentByIndex(int index) {
        Fragment frg = null;
        switch (index) {
            case 0:
                img_toolbar_back.setVisibility(View.GONE);
                img_toolbar_logo.setVisibility(View.VISIBLE);
                lbl_toolbar_title.setVisibility(View.GONE);
                viw_toolbar_badge.setVisibility(View.GONE);
                frg = new HomeFragment(this);
                break;
            case 1:
                img_toolbar_back.setVisibility(View.VISIBLE);
                img_toolbar_logo.setVisibility(View.GONE);
                lbl_toolbar_title.setVisibility(View.VISIBLE);
                lbl_toolbar_title.setText(getString(R.string.monitoring));
                viw_toolbar_badge.setVisibility(View.VISIBLE);
                frg = new MonitorFragment(this);
                break;
            case 2:
                img_toolbar_back.setVisibility(View.VISIBLE);
                img_toolbar_logo.setVisibility(View.GONE);
                lbl_toolbar_title.setVisibility(View.VISIBLE);
                lbl_toolbar_title.setText(getString(R.string.setting));
                viw_toolbar_badge.setVisibility(View.VISIBLE);
                frg = new SettingFragment(this);
                break;
        }
        onLoadFragment(frg);
    }

    private void onLoadFragment(Fragment frg) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction frgTran = fm.beginTransaction();
        frgTran.replace(R.id.frg_body, frg);
        frgTran.commit();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                AppUtil.pageIndex = 0;
                loadFragmentByIndex(0);
                break;
            case R.id.navigation_monitor:
                loadFragmentByIndex(1);
                break;
            case R.id.navigation_setting:
                AppUtil.pageIndex = 0;
                loadFragmentByIndex(2);
                break;
        }
        return true;
    }

    @Override
    public void initView() {
        super.initView();

        nav_bottom = findViewById(R.id.nav_bottom);
        nav_bottom.setOnNavigationItemSelectedListener(this);

        img_toolbar_back = findViewById(R.id.img_toolbar_back);
        img_toolbar_logo = findViewById(R.id.img_toolbar_logo);
        lbl_toolbar_title = findViewById(R.id.lbl_toolbar_title);
        rlt_toolbar_noti = findViewById(R.id.rlt_toolbar_noti);
        viw_toolbar_badge = findViewById(R.id.viw_toolbar_badge);

        loadFragmentByIndex(0);
    }

    public void setOnGetUserInfo(OnGetUserInfo onGetUserInfo) {
        this.onGetUserInfo = onGetUserInfo;
    }
}