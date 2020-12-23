package org.lab.biometro.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.lab.biometro.R;
import org.lab.biometro.activity.MainActivity;
import org.lab.biometro.adapter.HeartAdapter;
import org.lab.biometro.model.HeartModel;
import org.lab.biometro.ui.LineHeartChart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class HeartFragment extends Fragment {

    private final MainActivity activity;

    private LineHeartChart cht_heart;
    private HeartAdapter heartAdapter;
    private TextView lbl_date, lbl_heart;
    private ImageView img_calendar;

    private final List<HeartModel> models = new ArrayList<>();
    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dateListener = (view, year, monthOfYear, dayOfMonth) -> {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateLabel();
    };

    @SuppressLint("SetTextI18n")
    private void updateLabel() {
        String myFormat = "yyyy년 MM월 dd일"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        String weekStr = "(일)";
        switch (myCalendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                weekStr = "(일)";
                break;
            case Calendar.MONDAY:
                weekStr = "(월)";
                break;
            case Calendar.TUESDAY:
                weekStr = "(화)";
                break;
            case Calendar.WEDNESDAY:
                weekStr = "(수)";
                break;
            case Calendar.THURSDAY:
                weekStr = "(목)";
                break;
            case Calendar.FRIDAY:
                weekStr = "(금)";
                break;
            case Calendar.SATURDAY:
                weekStr = "(토)";
                break;
        }
        lbl_date.setText(sdf.format(myCalendar.getTime()) + " " + weekStr);

        initListData();
        initChartData();
    }

    public HeartFragment(MainActivity activity) {
        this.activity = activity;
    }

    private void initEvent() {
        img_calendar.setOnClickListener(view -> new DatePickerDialog(activity, R.style.DialogTheme, dateListener, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());
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

        ListView lst_heart = fragment.findViewById(R.id.lst_heart);
        heartAdapter = new HeartAdapter(activity, models);
        lst_heart.setAdapter(heartAdapter);

        lbl_date = fragment.findViewById(R.id.lbl_date);
        lbl_heart = fragment.findViewById(R.id.lbl_heart);
        img_calendar = fragment.findViewById(R.id.img_calendar);
        updateLabel();
        initEvent();
    }

    private void initChartData() {
        final List<LineHeartChart.Data<String>> list = new LinkedList<>();
        for (int i = 0; i < 25; i++) {
            list.add(new LineHeartChart.Data<>(String.format(Locale.getDefault(), "%d시", i)));
        }
        try {
            cht_heart.setXAxisBasisData(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<HeartModel>showModels = new ArrayList<>(HeartModel.getOneShowData(myCalendar, activity.htModels));
        final List<LineHeartChart.Data<Float[]>> data = new LinkedList<>();
        for (int i = 0; i < 25; i++) {
            int min = showModels.get(i).lValue;
            int max = showModels.get(i).hValue;
            if (min > 0) {
                Log.d("min : max ===> ", min + " --- " + max);
            }
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
        models.addAll(HeartModel.getOneDayData(myCalendar, activity.htModels));
        heartAdapter.notifyDataSetChanged();

        int min = 200, max = 0;
        for (HeartModel model: models) {
            if (min > model.lValue) {
                min = model.lValue;
            }
            if (max < model.hValue) {
                max = model.hValue;
            }
        }
        if (models.size() > 0) {
            lbl_heart.setText(String.format(Locale.getDefault(), "%d ~ %d bpm", min, max));
        } else {
            lbl_heart.setText(getString(R.string.empty_bpm));
        }
    }

}
