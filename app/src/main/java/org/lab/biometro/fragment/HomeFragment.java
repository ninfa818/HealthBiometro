package org.lab.biometro.fragment;

import android.os.Bundle;
import android.util.Log;
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
import org.lab.biometro.model.HeartModel;
import org.lab.biometro.model.HeartWeekModel;
import org.lab.biometro.model.OxygenModel;
import org.lab.biometro.model.OxygenWeekModel;
import org.lab.biometro.model.PayloadModel;
import org.lab.biometro.model.TempModel;
import org.lab.biometro.model.TempWeekModel;
import org.lab.biometro.model.UserModel;
import org.lab.biometro.ui.LineHeartChart;
import org.lab.biometro.ui.LineOxyChart;
import org.lab.biometro.ui.LineTempChart;
import org.lab.biometro.util.AppUtil;
import org.lab.biometro.util.HttpUtil;
import org.lab.biometro.util.SharedPreferenceUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
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

    private HeartWeekModel heartWeekModel;
    private OxygenWeekModel oxygenWeekModel;
    private TempWeekModel tempWeekModel;

    private final UserModel currentUser;


    public HomeFragment(MainActivity activity, UserModel userModel) {
        this.activity = activity;
        currentUser = userModel;
    }

    private void initEvent() {
        img_heart.setOnClickListener(view -> {
            if (heartWeekModel.average > 0) {
                AppUtil.pageIndex = 0;
                activity.nav_bottom.setSelectedItemId(R.id.navigation_monitor);
            } else {
                Snackbar.make(activity.getContentView(), R.string.no_data, BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
        img_oxygen.setOnClickListener(view -> {
            if (oxygenWeekModel.average > 0) {
                AppUtil.pageIndex = 1;
                activity.nav_bottom.setSelectedItemId(R.id.navigation_monitor);
            } else {
                Snackbar.make(activity.getContentView(), R.string.no_data, BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
        img_temp.setOnClickListener(view -> {
            if (tempWeekModel.average > 0) {
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
                    Type type = new TypeToken<List<PayloadModel>>(){}.getType();
                    List<PayloadModel> models = new Gson().fromJson(payLoadArray, type);
                    sortData(models);
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

    private void sortData(List<PayloadModel> models) {
        Log.d("models ===> ", new Gson().toJson(models));

        activity.htModels.clear();
        activity.ogModels.clear();
        activity.tpModels.clear();

        Collections.sort(models, (lhs, rhs) -> (lhs.CreateDate).compareTo(rhs.CreateDate));

        List<List<PayloadModel>> payloadsAll = new ArrayList<>();
        List<PayloadModel> payloadModels = new ArrayList<>();

        String dateString = "";
        for (PayloadModel model: models) {
            if (!model.CreateDate.equals(dateString)) {
                if (payloadModels.size() > 0) {
                    payloadsAll.add(payloadModels);
                }
                payloadModels = new ArrayList<>();
                dateString = model.CreateDate;
            } else {
                payloadModels.add(model);
            }
        }

        for (List<PayloadModel> payloadModelList : payloadsAll) {
            int htValue = 0;
            int ogValue = 0;
            float tpValue = 0.0f;
            for (PayloadModel payloadModel: payloadModelList) {
                htValue += Integer.parseInt(payloadModel.HeartRate);
                ogValue += Integer.parseInt(payloadModel.Spo2);
                tpValue += Integer.parseInt(payloadModel.Temperature);
            }

            HeartModel htModel = new HeartModel(htValue / payloadModelList.size(), htValue / payloadModelList.size() + AppUtil.randomInRange(40, 60), payloadModelList.get(0).CreateDate);
            activity.htModels.add(htModel);
            OxygenModel ogModel = new OxygenModel(ogValue / payloadModelList.size(), payloadModelList.get(0).CreateDate);
            activity.ogModels.add(ogModel);
            TempModel tpModel = new TempModel(tpValue / payloadModelList.size(), payloadModelList.get(0).CreateDate);
            activity.tpModels.add(tpModel);
        }

        initHeartData();
        initOxygenData();
        initTempData();
    }

    private void initHeartData() {
        heartWeekModel = new HeartWeekModel(activity.htModels);
        if (heartWeekModel.average > 0) {
            llt_heart_data.setVisibility(View.VISIBLE);
            llt_heart_empty.setVisibility(View.GONE);
            lbl_heart.setText(String.valueOf(heartWeekModel.average));

            final List<LineHeartChart.Data<String>> list = new LinkedList<>();
            for (String label : heartWeekModel.labels) {
                list.add(new LineHeartChart.Data<>(label));
            }
            try {
                cht_heart.setXAxisBasisData(list);
            } catch (Exception e) {
                e.printStackTrace();
            }

            final List<LineHeartChart.Data<Float[]>> data = new LinkedList<>();
            for (int i = 0; i < heartWeekModel.models.length; i++) {
                data.add(new LineHeartChart.Data<>(
                        new Float[]{(float) heartWeekModel.models[i].lValue, (float) heartWeekModel.models[i].hValue},
                        heartWeekModel.labels[i],
                        String.format(Locale.getDefault(), "%.0f", (float)(heartWeekModel.models[i].lValue + heartWeekModel.models[i].hValue) / 2)
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

    private void initOxygenData() {
        oxygenWeekModel = new OxygenWeekModel(activity.ogModels);
        if (oxygenWeekModel.average > 0) {
            llt_oxygen_data.setVisibility(View.VISIBLE);
            llt_oxygen_empty.setVisibility(View.GONE);
            lbl_oxygen.setText(String.valueOf(oxygenWeekModel.average));

            final List<LineOxyChart.Data<String>> list = new LinkedList<>();
            for (String label : oxygenWeekModel.labels) {
                list.add(new LineOxyChart.Data<>(label));
            }
            try {
                cht_oxy.setXAxisBasisData(list);
            } catch (Exception e) {
                e.printStackTrace();
            }

            final List<LineOxyChart.Data<Float>> data = new LinkedList<>();
            for (int i = 0; i < oxygenWeekModel.models.length; i++) {
                data.add(new LineOxyChart.Data<>((float)oxygenWeekModel.models[i].value, oxygenWeekModel.labels[i]));
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

    private void initTempData() {
        tempWeekModel = new TempWeekModel(activity.tpModels);
        if (tempWeekModel.average > 0) {
            llt_temp_date.setVisibility(View.VISIBLE);
            llt_temp_empty.setVisibility(View.GONE);
            lbl_temp.setText(String.format(Locale.getDefault(), "%.1f", tempWeekModel.average));

            final List<LineTempChart.Data<String>> list = new LinkedList<>();
            for (String label : tempWeekModel.labels) {
                list.add(new LineTempChart.Data<>(label));
            }
            try {
                cht_temp.setXAxisBasisData(list);
            } catch (Exception e) {
                e.printStackTrace();
            }

            final List<LineTempChart.Data<Float>> data = new LinkedList<>();
            for (int i = 0; i < tempWeekModel.models.length; i++) {
                data.add(new LineTempChart.Data<>(tempWeekModel.models[i].value, tempWeekModel.labels[i]));
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
