package org.lab.biometro.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.lab.biometro.model.OxygenModel;
import org.lab.biometro.ui.OxygenCell;

import java.util.List;

public class OxygenAdapter extends BaseAdapter {

    private final Context context;
    private final List<OxygenModel> models;


    public OxygenAdapter(Context context, List<OxygenModel> models) {
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
        OxygenModel model = models.get(i);

        OxygenCell cell = new OxygenCell(context);
        cell.setModel(model);

        return cell;
    }
}
