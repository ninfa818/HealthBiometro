package org.lab.biometro.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
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
import org.lab.biometro.adapter.OxygenAdapter;
import org.lab.biometro.model.HeartModel;
import org.lab.biometro.model.OxygenModel;
import org.lab.biometro.ui.LineOxyChart;
import org.lab.biometro.util.AppUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class OxygenFragment extends Fragment {

    private final MainActivity activity;

    private LineOxyChart cht_oxy;
    private OxygenAdapter oxygenAdapter;
    private List<OxygenModel> models = new ArrayList<>();

    private TextView lbl_date, lbl_oxygen;
    private ImageView img_calendar;

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

    public OxygenFragment(MainActivity activity) {
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
        View fragment = inflater.inflate(R.layout.fragment_oxygen, container, false);
        initView(fragment);
        return fragment;
    }

    private void initView(View fragment) {
        cht_oxy = fragment.findViewById(R.id.cht_oxy);

        ListView lst_oxy = fragment.findViewById(R.id.lst_oxy);
        oxygenAdapter = new OxygenAdapter(activity, models);
        lst_oxy.setAdapter(oxygenAdapter);

        lbl_date = fragment.findViewById(R.id.lbl_date);
        img_calendar = fragment.findViewById(R.id.img_calendar);
        lbl_oxygen = fragment.findViewById(R.id.lbl_oxygen);
        updateLabel();
        initEvent();
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

        List<OxygenModel>showModels = new ArrayList<>(OxygenModel.getOneShowData(myCalendar, activity.ogModels));
        final List<LineOxyChart.Data<Float>> data = new LinkedList<>();
        for (int i = 0; i < 25; i++) {
            int value = showModels.get(i).value;
            data.add(new LineOxyChart.Data<>((float) value, String.format(Locale.getDefault(), "%d시", i)));
        }

        try {
            cht_oxy.setData(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    private void initListData() {
        models.clear();
        models.addAll(OxygenModel.getOneDayData(myCalendar, activity.ogModels));
        oxygenAdapter.notifyDataSetChanged();

        int avarage = 0;
        for (OxygenModel model: models) {
            avarage += model.value;
        }
        if (models.size() > 0) {
            lbl_oxygen.setText(String.format(Locale.getDefault(), "%d", avarage / models.size()) + " %");
        } else {
            lbl_oxygen.setText(getString(R.string.empty_percent));
        }
    }


}
