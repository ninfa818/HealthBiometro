package org.lab.biometro.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.lab.biometro.listener.OnNotificationCellListener;
import org.lab.biometro.model.NotificationModel;
import org.lab.biometro.ui.NotificationCell;

import java.util.List;

public class NotificationAdapter extends BaseAdapter {

    private final Context context;
    private List<NotificationModel> models;

    private OnNotificationCellListener onNotificationCellListener;


    public NotificationAdapter(Context context, List<NotificationModel> models) {
        this.context = context;
        this.models = models;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        NotificationModel model = models.get(i);

        NotificationCell cell = new NotificationCell(context);
        cell.setModel(model);
        cell.setOnClickListener(view1 -> {
            if (onNotificationCellListener != null) {
                onNotificationCellListener.onClickCell(model);
            }
        });

        return cell;
    }

    public void setOnNotificationCellListener(OnNotificationCellListener onNotificationCellListener) {
        this.onNotificationCellListener = onNotificationCellListener;
    }
}
