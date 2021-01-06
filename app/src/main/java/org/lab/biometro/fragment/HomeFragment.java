package org.lab.biometro.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.lab.biometro.R;
import org.lab.biometro.activity.MainActivity;
import org.lab.biometro.listener.OnHttpListener;
import org.lab.biometro.model.HeartMainModel;
import org.lab.biometro.model.OxygenMainModel;
import org.lab.biometro.model.PayloadMainModel;
import org.lab.biometro.model.TempMainModel;
import org.lab.biometro.model.UserModel;
import org.lab.biometro.ui.LineHeartChart;
import org.lab.biometro.ui.LineOxyChart;
import org.lab.biometro.ui.LineTempChart;
import org.lab.biometro.util.AppUtil;
import org.lab.biometro.util.HttpUtil;
import org.lab.biometro.util.SharedPreferenceUtil;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private final MainActivity activity;

    private LineHeartChart cht_heart;
    private LineOxyChart cht_oxy;
    private LineTempChart cht_temp;
    private ImageView img_heart, img_oxygen, img_temp;
    private TextView lbl_heart, lbl_oxygen, lbl_temp;
    private LinearLayout llt_heart_empty, llt_heart_data, llt_oxygen_empty, llt_oxygen_data, llt_temp_empty, llt_temp_date;

    private HeartMainModel heartMainModel;
    private OxygenMainModel oxygenMainModel;
    private TempMainModel tempMainModel;

    private final UserModel currentUser;


    public HomeFragment(MainActivity activity, UserModel userModel) {
        this.activity = activity;
        currentUser = userModel;
    }

    private void initEvent() {
        img_heart.setOnClickListener(view -> {
            if (heartMainModel.average > 0) {
                AppUtil.pageIndex = 0;
                activity.nav_bottom.setSelectedItemId(R.id.navigation_monitor);
            } else {
                Snackbar.make(activity.getContentView(), R.string.no_data, BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
        img_oxygen.setOnClickListener(view -> {
            if (oxygenMainModel.average > 0) {
                AppUtil.pageIndex = 1;
                activity.nav_bottom.setSelectedItemId(R.id.navigation_monitor);
            } else {
                Snackbar.make(activity.getContentView(), R.string.no_data, BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
        img_temp.setOnClickListener(view -> {
            if (tempMainModel.average > 0) {
                AppUtil.pageIndex = 2;
                activity.nav_bottom.setSelectedItemId(R.id.navigation_monitor);
            } else {
                Snackbar.make(activity.getContentView(), R.string.no_data, BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
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
        TextView lbl_name = fragment.findViewById(R.id.lbl_name);
        lbl_name.setText(currentUser.memberName);

        cht_heart = fragment.findViewById(R.id.cht_heart);
        cht_oxy = fragment.findViewById(R.id.cht_oxy);
        cht_temp = fragment.findViewById(R.id.cht_temp);

        img_heart = fragment.findViewById(R.id.img_heart);
        img_oxygen = fragment.findViewById(R.id.img_oxygen);
        img_temp = fragment.findViewById(R.id.img_temp);

        lbl_heart = fragment.findViewById(R.id.lbl_heart);
        llt_heart_empty = fragment.findViewById(R.id.llt_heart_empty);
        llt_heart_data = fragment.findViewById(R.id.llt_heart_data);

        lbl_oxygen = fragment.findViewById(R.id.lbl_oxygen);
        llt_oxygen_empty = fragment.findViewById(R.id.llt_oxygen_empty);
        llt_oxygen_data = fragment.findViewById(R.id.llt_oxygen_data);

        lbl_temp = fragment.findViewById(R.id.lbl_temp);
        llt_temp_empty = fragment.findViewById(R.id.llt_temp_empty);
        llt_temp_date = fragment.findViewById(R.id.llt_temp_date);

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
                try {
                    String payLoadArray = obj.getString("payload");
                    Type type = new TypeToken<List<PayloadMainModel>>(){}.getType();
                    List<PayloadMainModel> models = new Gson().fromJson(payLoadArray, type);

                    Collections.sort(models, (lhs, rhs) -> (lhs.SortDate).compareTo(rhs.SortDate));
                    initHeartData(models);
                    initOxygenData(models);
                    initTempData(models);
                    
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                activity.hideProgress();
            }

            @Override
            public void onEventInternetError(Exception e) {
                activity.hideProgress();
                Snackbar.make(activity.getContentView(), Objects.requireNonNull(e.getMessage()), BaseTransientBottomBar.LENGTH_SHORT).show();
            }

            @Override
            public void onEventServerError(Exception e) {
                activity.hideProgress();
                Snackbar.make(activity.getContentView(), Objects.requireNonNull(e.getMessage()), BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
    }

    private void initHeartData(List<PayloadMainModel> models) {
        heartMainModel = new HeartMainModel(models);
        if (heartMainModel.average > 0) {
            llt_heart_data.setVisibility(View.VISIBLE);
            llt_heart_empty.setVisibility(View.GONE);
            lbl_heart.setText(String.valueOf(heartMainModel.average));

            final List<LineHeartChart.Data<String>> list = new LinkedList<>();
            for (String label : heartMainModel.labels) {
                list.add(new LineHeartChart.Data<>(label));
            }
            try {
                cht_heart.setXAxisBasisData(list);
            } catch (Exception e) {
                e.printStackTrace();
            }

            final List<LineHeartChart.Data<Float[]>> data = new LinkedList<>();
            for (int i = 0; i < heartMainModel.models.size(); i++) {
                data.add(new LineHeartChart.Data<>(
                        new Float[]{(float) heartMainModel.models.get(i).lValue, (float) heartMainModel.models.get(i).hValue},
                        heartMainModel.labels.get(i),
                        String.format(Locale.getDefault(), "%.0f", (float)(heartMainModel.models.get(i).lValue + heartMainModel.models.get(i).hValue) / 2)
                ));
            }
            try {
                cht_heart.setData(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            llt_heart_data.setVisibility(View.GONE);
            llt_heart_empty.setVisibility(View.VISIBLE);
        }
    }

    private void initOxygenData(List<PayloadMainModel> models) {
        oxygenMainModel = new OxygenMainModel(models);
        if (oxygenMainModel.average > 0) {
            llt_oxygen_data.setVisibility(View.VISIBLE);
            llt_oxygen_empty.setVisibility(View.GONE);
            lbl_oxygen.setText(String.valueOf(oxygenMainModel.average));

            final List<LineOxyChart.Data<String>> list = new LinkedList<>();
            for (String label : oxygenMainModel.labels) {
                list.add(new LineOxyChart.Data<>(label));
            }
            try {
                cht_oxy.setXAxisBasisData(list);
            } catch (Exception e) {
                e.printStackTrace();
            }

            final List<LineOxyChart.Data<Float>> data = new LinkedList<>();
            for (int i = 0; i < oxygenMainModel.models.size(); i++) {
                data.add(new LineOxyChart.Data<>((float) oxygenMainModel.models.get(i).value, oxygenMainModel.labels.get(i)));
            }
            try {
                cht_oxy.setData(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            llt_oxygen_data.setVisibility(View.GONE);
            llt_oxygen_empty.setVisibility(View.VISIBLE);
        }
    }

    private void initTempData(List<PayloadMainModel> models) {
        tempMainModel = new TempMainModel(models);
        if (tempMainModel.average > 0) {
            llt_temp_date.setVisibility(View.VISIBLE);
            llt_temp_empty.setVisibility(View.GONE);
            lbl_temp.setText(String.format(Locale.getDefault(), "%.1f", tempMainModel.average));

            final List<LineTempChart.Data<String>> list = new LinkedList<>();
            for (String label : tempMainModel.labels) {
                list.add(new LineTempChart.Data<>(label));
            }
            try {
                cht_temp.setXAxisBasisData(list);
            } catch (Exception e) {
                e.printStackTrace();
            }

            final List<LineTempChart.Data<Float>> data = new LinkedList<>();
            for (int i = 0; i < tempMainModel.models.size(); i++) {
                data.add(new LineTempChart.Data<>(tempMainModel.models.get(i).value, tempMainModel.labels.get(i)));
            }
            try {
                cht_temp.setData(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            llt_temp_date.setVisibility(View.GONE);
            llt_temp_empty.setVisibility(View.VISIBLE);
        }
    }

}
