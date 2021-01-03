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
import org.lab.biometro.adapter.TempAdapter;
import org.lab.biometro.model.TempModel;
import org.lab.biometro.ui.LineTempChart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class TempFragment extends Fragment {

    private final MainActivity activity;

    private LineTempChart cht_temp;
    private TempAdapter tempAdapter;
    private final List<TempModel> models = new ArrayList<>();

    private TextView lbl_date, lbl_temp;
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
    

    public TempFragment(MainActivity activity) {
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
        View fragment = inflater.inflate(R.layout.fragment_temp, container, false);
        initView(fragment);
        return fragment;
    }

    private void initView(View fragment) {
        cht_temp = fragment.findViewById(R.id.cht_temp);

        ListView lst_temp = fragment.findViewById(R.id.lst_temp);
        tempAdapter = new TempAdapter(activity, models);
        lst_temp.setAdapter(tempAdapter);

        lbl_date = fragment.findViewById(R.id.lbl_date);
        lbl_temp = fragment.findViewById(R.id.lbl_temp);
        img_calendar = fragment.findViewById(R.id.img_calendar);
        updateLabel();
        initEvent();
    }

    private void initChartData() {
        final List<LineTempChart.Data<String>> list = new LinkedList<>();
        for (int i = 0; i < 25; i++) {
            list.add(new LineTempChart.Data<>(String.format(Locale.getDefault(), "%d시", i)));
        }
        try {
            cht_temp.setXAxisBasisData(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<TempModel>showModels = new ArrayList<>(TempModel.getOneShowData(myCalendar, activity.tpModels));
        final List<LineTempChart.Data<Float>> data = new LinkedList<>();
        for (int i = 0; i < 25; i++) {
            float value = showModels.get(i).value;
            data.add(new LineTempChart.Data<>(value, String.format(Locale.getDefault(), "%d시", i)));
        }

        try {
            cht_temp.setData(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    private void initListData() {
        models.clear();
        models.addAll(TempModel.getOneDayData(myCalendar, activity.tpModels));
        tempAdapter.notifyDataSetChanged();

        float avarage = 0;
        for (TempModel model: models) {
            avarage += model.value;
        }
        if (models.size() > 0) {
            lbl_temp.setText(String.format(Locale.getDefault(), "%.1f", avarage / models.size()) + " Cº");
        } else {
            lbl_temp.setText(getString(R.string.empty_percent));
        }
    }

}
