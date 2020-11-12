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
import org.lab.biometro.adapter.HeartAdapter;
import org.lab.biometro.model.HeartModel;
import org.lab.biometro.ui.LineHeartChart;
import org.lab.biometro.util.AppUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class HeartFragment extends Fragment {

    private final MainActivity activity;

    private LineHeartChart cht_heart;
    private HeartAdapter heartAdapter;
    private List<HeartModel> models = new ArrayList<>();


    public HeartFragment(MainActivity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_heart, container, false);
        initView(fragment);
        return fragment;
    }

    private void initView(View fragment) {
        cht_heart = fragment.findViewById(R.id.cht_heart);
        initHeartData();

        ListView lst_heart = fragment.findViewById(R.id.lst_heart);
        heartAdapter = new HeartAdapter(activity, models);
        lst_heart.setAdapter(heartAdapter);

        initListData();
    }

    private void initHeartData() {
        final List<LineHeartChart.Data<String>> list = new LinkedList<>();
        for (int i = 0; i < 25; i++) {
            list.add(new LineHeartChart.Data<>(String.format(Locale.getDefault(), "%d시", i)));
        }
        try {
            cht_heart.setXAxisBasisData(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final List<LineHeartChart.Data<Float[]>> data = new LinkedList<>();
        for (int i = 0; i < 25; i++) {
            int min = AppUtil.randomInRange(60, 90);
            int max = AppUtil.randomInRange(80, 140);
            data.add(new LineHeartChart.Data<>(new Float[]{(float) min, (float) max}, String.format(Locale.getDefault(), "%d시", i), String.format(Locale.getDefault(), "%.0f", (float) (min + max))));
        }

        try {
            cht_heart.setData(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initListData() {
        models.clear();

        for (int i = 0; i < 24; i++) {
            HeartModel model = new HeartModel();
            model.time = String.format(Locale.getDefault(), "%02d:00:00", (23 - i));
            model.value = AppUtil.randomInRange(70, 130);
            models.add(model);
        }

        heartAdapter.notifyDataSetChanged();
    }

}
