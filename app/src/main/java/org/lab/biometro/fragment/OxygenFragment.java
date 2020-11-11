package org.lab.biometro.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.lab.biometro.R;
import org.lab.biometro.activity.MainActivity;
import org.lab.biometro.adapter.OxygenAdapter;
import org.lab.biometro.model.OxygenModel;
import org.lab.biometro.model.OxygenModel;
import org.lab.biometro.ui.LineHeartChart;
import org.lab.biometro.ui.LineOxyChart;
import org.lab.biometro.util.AppUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class OxygenFragment extends Fragment {

    private final MainActivity activity;

    private LineOxyChart cht_oxy;
    private OxygenAdapter oxygenAdapter;
    private List<OxygenModel> models = new ArrayList<>();


    public OxygenFragment(MainActivity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_oxygen, container, false);
        initView(fragment);
        return fragment;
    }

    private void initView(View fragment) {
        cht_oxy = fragment.findViewById(R.id.cht_oxy);
        initChartData();

        ListView lst_oxy = fragment.findViewById(R.id.lst_oxy);
        oxygenAdapter = new OxygenAdapter(activity, models);
        lst_oxy.setAdapter(oxygenAdapter);
        initListData();
    }

    private void initChartData() {
        final List<LineOxyChart.Data<String>> list = new LinkedList<>();
        for (int i = 0; i < 25; i++) {
            list.add(new LineOxyChart.Data<>(String.format(Locale.getDefault(), "%d시", i)));
        }
        try {
            cht_oxy.setXAxisBasisData(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final List<LineOxyChart.Data<Float>> data = new LinkedList<>();
        for (int i = 0; i < 25; i++) {
            int value = AppUtil.randomInRange(90, 100);
            data.add(new LineOxyChart.Data<>((float) value, String.format(Locale.getDefault(), "%d시", i)));
        }

        try {
            cht_oxy.setData(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initListData() {
        models.clear();

        OxygenModel model1 = new OxygenModel();
        model1.time = "22:00:00";
        model1.value = 99;

        OxygenModel model2 = new OxygenModel();
        model2.time = "15:00:00";
        model2.value = 100;

        OxygenModel model3 = new OxygenModel();
        model3.time = "15:00:00";
        model3.value = 98;

        OxygenModel model4 = new OxygenModel();
        model4.time = "15:00:00";
        model4.value = 95;

        OxygenModel model5 = new OxygenModel();
        model5.time = "15:00:00";
        model5.value = 97;

        OxygenModel model6 = new OxygenModel();
        model6.time = "15:00:00";
        model6.value = 100;

        models.add(model1);
        models.add(model2);
        models.add(model3);
        models.add(model4);
        models.add(model5);
        for (int i = 0; i < 10; i++) {
            models.add(model6);
        }

        oxygenAdapter.notifyDataSetChanged();
    }


}
