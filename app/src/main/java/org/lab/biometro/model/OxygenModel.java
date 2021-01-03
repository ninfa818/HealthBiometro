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

public class OxygenModel {

    public String create_at;
    public int value;

    public OxygenModel(int value, String create_at) {
        this.value = value;
        this.create_at = create_at;
    }

    static public List<OxygenModel> getOneDayData(Calendar calendar, List<OxygenModel> allData) {
        List<OxygenModel> models = new ArrayList<>();

        DateFormat fullFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        if (AppUtil.is_demo_mode) {
            try {
                calendar.setTime(Objects.requireNonNull(fullFormat.parse("2020-10-14")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        String fullDate = fullFormat.format(calendar.getTime());
        for (OxygenModel model: allData) {
            if (model.create_at.contains(fullDate)) {
                models.add(model);
            }
        }

        return models;
    }

    static public List<OxygenModel> getOneShowData(Calendar calendar, List<OxygenModel> allData) {
        List<OxygenModel> models = new ArrayList<>();

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
            int avarage = 0, count = 0;
            for (OxygenModel model: allData) {
                if (model.create_at.contains(dateTime)) {
                    avarage += model.value;
                    count++;
                }
            }
            int value = avarage / (count == 0? 1: count);
            if (value == 0) {
                value = 90;
            } else if (value < 91) value = 91;
            models.add(new OxygenModel(value, dateTime));
        }

        return models;
    }

}
