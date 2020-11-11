package org.lab.biometro.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.lab.biometro.R;
import org.lab.biometro.model.HeartModel;
import org.lab.biometro.model.OxygenModel;

public class OxygenCell extends LinearLayout {

    private OxygenModel model;

    private TextView lbl_time, lbl_value;


    public OxygenCell(Context context) {
        super(context);

        setOrientation(LinearLayout.HORIZONTAL);
        LayoutInflater.from(context).inflate(R.layout.ui_oxygen, this, true);

        lbl_time = findViewById(R.id.lbl_time);
        lbl_value = findViewById(R.id.lbl_value);
    }

    public void setModel(OxygenModel model) {
        this.model = model;
        initView();
    }

    private void initView() {
        lbl_time.setText(model.time);
        lbl_value.setText(String.valueOf(model.value));
    }

}
