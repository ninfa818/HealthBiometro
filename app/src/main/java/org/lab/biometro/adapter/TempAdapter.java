package org.lab.biometro.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.lab.biometro.model.TempModel;
import org.lab.biometro.ui.TempCell;

import java.util.List;

public class TempAdapter extends BaseAdapter {

    private final Context context;
    private final List<TempModel> models;


    public TempAdapter(Context context, List<TempModel> models) {
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
        TempModel model = models.get(i);

        TempCell cell = new TempCell(context);
        cell.setModel(model);

        return cell;
    }
}
