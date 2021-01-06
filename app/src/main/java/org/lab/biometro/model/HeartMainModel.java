package org.lab.biometro.model;

import org.lab.biometro.util.TimeUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HeartMainModel {

    public List<HeartModel> models = new ArrayList<>();
    public List<String> labels = new ArrayList<>();
    public int average;

    public HeartMainModel(List<PayloadMainModel> payloadMainModels) {
        models.clear();
        labels.clear();
        average = 0;

        int index = 0;
        for (PayloadMainModel model : payloadMainModels) {
            Date modelDate = TimeUtil.getDateFromString(TimeUtil.DATE_YYYY_MM_DD, model.SortDate);
            String date = TimeUtil.getStringFromDate(TimeUtil.DATE_MMMnDD, modelDate);
            if (index == 0) {
                date = TimeUtil.getStringFromDate("dd", modelDate);
                index++;
            }
            labels.add(date);

            HeartModel heartModel = new HeartModel(Double.parseDouble(model.AvgHeartRate), Double.parseDouble(model.AvgHeartRate) + 60, model.SortDate);
            models.add(heartModel);

            average += Double.parseDouble(model.AvgHeartRate);
        }
        average = average / (payloadMainModels.size() == 0? 1 : payloadMainModels.size());
    }

}
