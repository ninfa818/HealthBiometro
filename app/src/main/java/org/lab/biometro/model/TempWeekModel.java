package org.lab.biometro.model;

import org.lab.biometro.util.AppUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TempWeekModel {

    public TempModel[] models = new TempModel[7];
    public String[] labels = new String[7];
    public float average = 0.0f;

    public TempWeekModel(List<TempModel> models) {
        DateFormat format = new SimpleDateFormat("dd", Locale.getDefault());
//        DateFormat outFormat = new SimpleDateFormat("MM.dd", Locale.getDefault());
        DateFormat fullFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();

        if (AppUtil.is_demo_mode) {
            try {
                calendar.setTime(fullFormat.parse("2020-10-18"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        int count = 0;
        for (int i = 0; i < 7; i++) {
            this.labels[i] = format.format(calendar.getTime());

            String fullDate = fullFormat.format(calendar.getTime());
            List<TempModel> TempModels = new ArrayList<>();
            float value = 0.0f;
            for (TempModel model: models) {
                if (model.create_at.contains(fullDate)) {
                    TempModels.add(model);
                    value += model.value;
                }
            }
            float modelValue = value / (TempModels.size() == 0? 1 : TempModels.size());
            this.average += modelValue;
            if (modelValue > 0) {
                count++;
            }
            if (modelValue == 0) modelValue = 35.0f;
            this.models[i] = new TempModel(modelValue, fullDate);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        this.average = this.average / (count == 0? 1 : count);
    }

}
