package org.lab.biometro.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import org.lab.biometro.R;
import org.lab.biometro.listener.OnNotificationCellListener;
import org.lab.biometro.model.NotificationModel;

public class NotificationCell extends LinearLayout {

    private NotificationModel model;
    
    public NotificationCell(Context context) {
        super(context);

        setOrientation(LinearLayout.HORIZONTAL);
        LayoutInflater.from(context).inflate(R.layout.ui_notification, this, true);
    }

    public void setModel(NotificationModel model) {
        this.model = model;
        initView();
    }

    private void initView() {

    }

}
