package org.lab.biometro.model;

import org.lab.biometro.util.AppUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class OxygenWeekModel {

    public OxygenModel[] models = new OxygenModel[7];
    public String[] labels = new String[7];
    public int average = 0;

    public OxygenWeekModel(List<OxygenModel> models) {
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
            List<OxygenModel> OxygenModels = new ArrayList<>();
            int value = 0;
            for (OxygenModel model: models) {
                if (model.create_at.contains(fullDate)) {
                    OxygenModels.add(model);
                    value += model.value;
                }
            }
            int modelValue = value / (OxygenModels.size() == 0? 1 : OxygenModels.size());
            this.average += modelValue;
            if (modelValue > 0) {
                count++;
            }
            if (modelValue == 0) modelValue = 90;
            this.models[i] = new OxygenModel(modelValue, fullDate);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        this.average = this.average / (count == 0? 1 : count);
    }

}
