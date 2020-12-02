package org.lab.biometro.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;
import org.lab.biometro.R;
import org.lab.biometro.activity.MainActivity;
import org.lab.biometro.listener.OnGetUserInfo;
import org.lab.biometro.listener.OnHttpListener;
import org.lab.biometro.model.UserModel;
import org.lab.biometro.ui.LineHeartChart;
import org.lab.biometro.ui.LineOxyChart;
import org.lab.biometro.ui.LineTempChart;
import org.lab.biometro.util.AppUtil;
import org.lab.biometro.util.HttpUtil;
import org.lab.biometro.util.SharedPreferenceUtil;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HomeFragment extends Fragment {

    private final MainActivity activity;

    private LineHeartChart cht_heart;
    private LineOxyChart cht_oxy;
    private LineTempChart cht_temp;
    private ImageView img_heart, img_oxygen, img_temp;
    private TextView lbl_name;


    public HomeFragment(MainActivity activity) {
        this.activity = activity;
    }

    private void initEvent() {
        img_heart.setOnClickListener(view -> {
            AppUtil.pageIndex = 0;
            activity.nav_bottom.setSelectedItemId(R.id.navigation_monitor);
        });
        img_oxygen.setOnClickListener(view -> {
            AppUtil.pageIndex = 1;
            activity.nav_bottom.setSelectedItemId(R.id.navigation_monitor);
        });
        img_temp.setOnClickListener(view -> {
            AppUtil.pageIndex = 2;
            activity.nav_bottom.setSelectedItemId(R.id.navigation_monitor);
        });
        activity.setOnGetUserInfo(userModel -> lbl_name.setText(userModel.memberName));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_home, container, false);
        initView(fragment);
        initEvent();
        return fragment;
    }

    private void initView(View fragment) {
        lbl_name = fragment.findViewById(R.id.lbl_name);

        cht_heart = fragment.findViewById(R.id.cht_heart);
        initHeartData();

        cht_oxy = fragment.findViewById(R.id.cht_oxy);
        initOxygenData();

        cht_temp = fragment.findViewById(R.id.cht_temp);
        initTempData();

        img_heart = fragment.findViewById(R.id.img_heart);
        img_oxygen = fragment.findViewById(R.id.img_oxygen);
        img_temp = fragment.findViewById(R.id.img_temp);

        initData();
    }

    private void initData() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + SharedPreferenceUtil.getToken());

        Map<String, String> params = new HashMap<>();
        params.put("account", SharedPreferenceUtil.getEmail());

        activity.showProgress();
        HttpUtil.onHttpRequest(HttpUtil.url_maindata, headers, params, new OnHttpListener() {
            @Override
            public void onEventCallBack(JSONObject obj, int ret) {
                activity.hideProgress();
            }

            @Override
            public void onEventInternetError(Exception e) {
                activity.hideProgress();
                Snackbar.make(activity.getContentView(), e.getMessage(), BaseTransientBottomBar.LENGTH_SHORT).show();
            }

            @Override
            public void onEventServerError(Exception e) {
                activity.hideProgress();
                Snackbar.make(activity.getContentView(), e.getMessage(), BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
    }

    private void initHeartData() {
        final List<LineHeartChart.Data<String>> list = new LinkedList<>();
        list.add(new LineHeartChart.Data<>("9.20"));
        list.add(new LineHeartChart.Data<>("21"));
        list.add(new LineHeartChart.Data<>("22"));
        list.add(new LineHeartChart.Data<>("23"));
        list.add(new LineHeartChart.Data<>("24"));
        list.add(new LineHeartChart.Data<>("25"));
        list.add(new LineHeartChart.Data<>("9.26"));
        try {
            cht_heart.setXAxisBasisData(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final List<LineHeartChart.Data<Float[]>> data = new LinkedList<>();
        data.add(new LineHeartChart.Data<>(new Float[]{61f, 102f}, "9.20", String.format(Locale.getDefault(), "%.0f", 81f)));
        data.add(new LineHeartChart.Data<>(new Float[]{72f, 120f}, "21", String.format(Locale.getDefault(), "%.0f", 96f)));
        data.add(new LineHeartChart.Data<>(new Float[]{80f, 125f}, "22", String.format(Locale.getDefault(), "%.0f", 102f)));
        data.add(new LineHeartChart.Data<>(new Float[]{90f, 135f}, "23", String.format(Locale.getDefault(), "%.0f", 112f)));
        data.add(new LineHeartChart.Data<>(new Float[]{59f, 115f}, "24", String.format(Locale.getDefault(), "%.0f", 87f)));
        data.add(new LineHeartChart.Data<>(new Float[]{65f, 90f}, "25", String.format(Locale.getDefault(), "%.0f", 77f)));
        data.add(new LineHeartChart.Data<>(new Float[]{75f, 125f}, "9.26", String.format(Locale.getDefault(), "%.0f", 100f)));
        try {
            cht_heart.setData(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initOxygenData() {
        final List<LineOxyChart.Data<String>> list = new LinkedList<>();
        list.add(new LineOxyChart.Data<>("9.20"));
        list.add(new LineOxyChart.Data<>("21"));
        list.add(new LineOxyChart.Data<>("22"));
        list.add(new LineOxyChart.Data<>("23"));
        list.add(new LineOxyChart.Data<>("24"));
        list.add(new LineOxyChart.Data<>("25"));
        list.add(new LineOxyChart.Data<>("9.26"));
        try {
            cht_oxy.setXAxisBasisData(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final List<LineOxyChart.Data<Float>> data = new LinkedList<>();
        data.add(new LineOxyChart.Data<>(99f, "9.20"));
        data.add(new LineOxyChart.Data<>(100f, "21"));
        data.add(new LineOxyChart.Data<>(98f, "22"));
        data.add(new LineOxyChart.Data<>(97f, "23"));
        data.add(new LineOxyChart.Data<>(98f, "24"));
        data.add(new LineOxyChart.Data<>(99f, "25"));
        data.add(new LineOxyChart.Data<>(100f, "9.26"));
        try {
            cht_oxy.setData(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initTempData() {
        final List<LineTempChart.Data<String>> list = new LinkedList<>();
        list.add(new LineTempChart.Data<>("9.20"));
        list.add(new LineTempChart.Data<>("21"));
        list.add(new LineTempChart.Data<>("22"));
        list.add(new LineTempChart.Data<>("23"));
        list.add(new LineTempChart.Data<>("24"));
        list.add(new LineTempChart.Data<>("25"));
        list.add(new LineTempChart.Data<>("9.26"));
        try {
            cht_temp.setXAxisBasisData(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final List<LineTempChart.Data<Float>> data = new LinkedList<>();
        data.add(new LineTempChart.Data<>(36.0f, "9.20"));
        data.add(new LineTempChart.Data<>(36.5f, "21"));
        data.add(new LineTempChart.Data<>(37.2f, "22"));
        data.add(new LineTempChart.Data<>(36.3f, "23"));
        data.add(new LineTempChart.Data<>(36.9f, "24"));
        data.add(new LineTempChart.Data<>(36.5f, "25"));
        data.add(new LineTempChart.Data<>(37.2f, "9.26"));
        try {
            cht_temp.setData(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
