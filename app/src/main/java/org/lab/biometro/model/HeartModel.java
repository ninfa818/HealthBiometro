package org.lab.biometro.model;

import org.lab.biometro.util.AppUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HeartModel {
    public String create_at = "2020-11-13 11:29:42";
    public int lValue = 100;
    public int hValue = 100;

    public HeartModel(int lValue, int hValue, String create_at) {
        this.lValue = lValue;
        this.hValue = hValue;
        this.create_at = create_at;
    }

    public HeartModel() {

    }

    static public List<HeartModel> getOneDayData(Calendar calendar, List<HeartModel> allData) {
        List<HeartModel> models = new ArrayList<>();

        DateFormat fullFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        if (AppUtil.is_demo_mode) {
            try {
                calendar.setTime(fullFormat.parse("2020-10-14"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        String fullDate = fullFormat.format(calendar.getTime());
        for (HeartModel model: allData) {
            if (model.create_at.contains(fullDate)) {
                models.add(model);
            }
        }

        return models;
    }

    static public List<HeartModel> getOneShowData(Calendar calendar, List<HeartModel> allData) {
        List<HeartModel> models = new ArrayList<>();

        DateFormat fullFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        if (AppUtil.is_demo_mode) {
            try {
                calendar.setTime(fullFormat.parse("2020-10-14"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        String fullDate = fullFormat.format(calendar.getTime());

        for (int i = 0; i < 25; i++) {
            String dateTime = String.format(Locale.getDefault(), "%s %02d", fullDate, i);
            int lAvarage = 0, hAvarage = 0, count = 0;
            for (HeartModel model: allData) {
                if (model.create_at.contains(dateTime)) {
                    lAvarage += model.lValue;
                    hAvarage += model.hValue;
                    count++;
                }
            }
            models.add(new HeartModel(lAvarage / (count == 0? 1: count), hAvarage / (count == 0? 1: count), dateTime));
        }

        return models;
    }

}
