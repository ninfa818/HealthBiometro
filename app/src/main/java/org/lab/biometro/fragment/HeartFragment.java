package org.lab.biometro.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

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

        HeartModel model1 = new HeartModel();
        model1.time = "22:00:00";
        model1.value = 118;

        HeartModel model2 = new HeartModel();
        model2.time = "15:00:00";
        model2.value = 65;

        HeartModel model3 = new HeartModel();
        model3.time = "15:00:00";
        model3.value = 108;

        HeartModel model4 = new HeartModel();
        model4.time = "15:00:00";
        model4.value = 70;

        HeartModel model5 = new HeartModel();
        model5.time = "15:00:00";
        model5.value = 61;

        HeartModel model6 = new HeartModel();
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

        heartAdapter.notifyDataSetChanged();
    }

}
