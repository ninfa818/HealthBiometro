package org.lab.biometro.model;

import org.lab.biometro.util.TimeUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TempMainModel {

    public List<TempModel> models = new ArrayList<>();
    public List<String> labels = new ArrayList<>();
    public float average;

    public TempMainModel(List<PayloadMainModel> payloadMainModels) {
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

            TempModel oxygenModel = new TempModel((Double.parseDouble(model.AvgTemperature) < 35.0? 35.1 : Double.parseDouble(model.AvgTemperature)), model.SortDate);
            models.add(oxygenModel);

            average += Double.parseDouble(model.AvgTemperature);
        }
        average = average / (payloadMainModels.size() == 0? 1 : payloadMainModels.size());
    }

}
