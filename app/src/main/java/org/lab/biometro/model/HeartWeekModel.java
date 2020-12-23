package org.lab.biometro.model;

import org.lab.biometro.util.AppUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HeartWeekModel {

    public HeartModel[] models = new HeartModel[7];
    public String[] labels = new String[7];
    public int average = 0;

    public HeartWeekModel (List<HeartModel> models) {
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
            List<HeartModel> heartModels = new ArrayList<>();
            int lValue = 0, hValue = 0;
            for (HeartModel model: models) {
                if (model.create_at.contains(fullDate)) {
                    heartModels.add(model);
                    lValue += model.lValue;
                    hValue += model.hValue;
                }
            }
            this.models[i] = new HeartModel(lValue / (heartModels.size() == 0? 1 : heartModels.size())
                    , hValue / (heartModels.size() == 0? 1 : heartModels.size())
                    , fullDate);
            this.average += (this.models[i].lValue + this.models[i].hValue) / 2;
            if (this.models[i].lValue > 0) {
                count++;
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        this.average = this.average / (count == 0? 1 : count);
    }

}
