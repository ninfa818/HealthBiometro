package org.lab.biometro.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.lab.biometro.R;
import org.lab.biometro.model.HeartModel;

import java.util.Locale;

public class HeartCell extends LinearLayout {

    private HeartModel model;

    private final TextView lbl_time;
    private final TextView lbl_value;


    public HeartCell(Context context) {
        super(context);

        setOrientation(LinearLayout.HORIZONTAL);
        LayoutInflater.from(context).inflate(R.layout.ui_heart, this, true);

        lbl_time = findViewById(R.id.lbl_time);
        lbl_value = findViewById(R.id.lbl_value);
    }

    public void setModel(HeartModel model) {
        this.model = model;
        initView();
    }

    private void initView() {
        lbl_time.setText(model.create_at);
        lbl_value.setText(String.format(Locale.getDefault(), "%d - %d", model.lValue, model.hValue));
    }

}
