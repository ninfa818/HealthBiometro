package org.lab.biometro.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.lab.biometro.R;
import org.lab.biometro.model.OxygenModel;
import org.lab.biometro.model.TempModel;

import java.util.Locale;

public class TempCell extends LinearLayout {

    private TempModel model;

    private TextView lbl_time, lbl_value;


    public TempCell(Context context) {
        super(context);

        setOrientation(LinearLayout.HORIZONTAL);
        LayoutInflater.from(context).inflate(R.layout.ui_temp, this, true);

        lbl_time = findViewById(R.id.lbl_time);
        lbl_value = findViewById(R.id.lbl_value);
    }

    public void setModel(TempModel model) {
        this.model = model;
        initView();
    }

    private void initView() {
        lbl_time.setText(model.create_at);
        lbl_value.setText(String.format(Locale.getDefault(), "%.1f", model.value));
    }

}
