package org.lab.biometro.model;

import org.lab.biometro.util.AppUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class TempModel {

    public String create_at;
    public float value;

    public TempModel(double value, String create_at) {
        this.value = (float) value;
        this.create_at = create_at;
    }

    static public List<TempModel> getOneDayData(Calendar calendar, List<TempModel> allData) {
        List<TempModel> models = new ArrayList<>();

        DateFormat fullFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        if (AppUtil.is_demo_mode) {
            try {
                calendar.setTime(Objects.requireNonNull(fullFormat.parse("2020-10-14")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        String fullDate = fullFormat.format(calendar.getTime());
        for (TempModel model: allData) {
            if (model.create_at.contains(fullDate)) {
                models.add(model);
            }
        }

        return models;
    }

    static public List<TempModel> getOneShowData(Calendar calendar, List<TempModel> allData) {
        List<TempModel> models = new ArrayList<>();

        DateFormat fullFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        if (AppUtil.is_demo_mode) {
            try {
                calendar.setTime(Objects.requireNonNull(fullFormat.parse("2020-10-14")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        String fullDate = fullFormat.format(calendar.getTime());

        for (int i = 0; i < 25; i++) {
            String dateTime = String.format(Locale.getDefault(), "%s %02d", fullDate, i);
            float avarage = 0, count = 0;
            for (TempModel model: allData) {
                if (model.create_at.contains(dateTime)) {
                    avarage += model.value;
                    count++;
                }
            }
            float value = avarage / (count == 0? 1: count);
            if (value == 0) {
                value = 35.0f;
            } else if (value < 35.1f) value = 35.1f;
            models.add(new TempModel(value, dateTime));
        }

        return models;
    }

}
