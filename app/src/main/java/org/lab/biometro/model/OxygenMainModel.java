package org.lab.biometro.model;

import org.lab.biometro.util.TimeUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OxygenMainModel {

    public List<OxygenModel> models = new ArrayList<>();
    public List<String> labels = new ArrayList<>();
    public int average = 0;

    public OxygenMainModel(List<PayloadMainModel> payloadMainModels) {
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

            OxygenModel oxygenModel = new OxygenModel((Double.parseDouble(model.AvgSpo2) < 90.0? 91.0 : Double.parseDouble(model.AvgSpo2)), model.SortDate);
            models.add(oxygenModel);

            average += Double.parseDouble(model.AvgSpo2);
        }
        average = average / (payloadMainModels.size() == 0? 1 : payloadMainModels.size());
    }

}
