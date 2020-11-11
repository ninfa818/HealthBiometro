package org.lab.biometro.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.lab.biometro.model.HeartModel;
import org.lab.biometro.ui.HeartCell;

import java.util.List;

public class HeartAdapter extends BaseAdapter {

    private Context context;
    private List<HeartModel> models;


    public HeartAdapter(Context context, List<HeartModel> models) {
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

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        HeartModel model = models.get(i);

        HeartCell cell = new HeartCell(context);
        cell.setModel(model);

        return cell;
    }
}
