package com.mtrilogic.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;

import com.mtrilogic.abstracts.Inflatable;
import com.mtrilogic.abstracts.Modelable;
import com.mtrilogic.classes.Listable;
import com.mtrilogic.interfaces.InflatableListener;

@SuppressWarnings({"unused","WeakerAccess"})
public final class InflatableAdapter extends BaseAdapter{
    private final InflatableListener listener;
    private final LayoutInflater inflater;
    private final int typeCount;

// ++++++++++++++++| PUBLIC CONSTRUCTORS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public InflatableAdapter(@NonNull LayoutInflater inflater, @NonNull InflatableListener listener, int typeCount){
        this.inflater = inflater;
        this.listener = listener;
        this.typeCount = Math.max(typeCount, 1);
    }

// +++++++++++++++++| PUBLIC OVERRIDE METHODS |+++++++++++++++++++++++++++++++++++++++++++++++++++++

    @Override
    public final int getCount(){
        return getModelableListable().getItemCount();
    }

    @Override
    public final Modelable getItem(int position){
        return getModelable(position);
    }

    @Override
    public final long getItemId(int position){
        return getItem(position).getItemId();
    }

    @Override
    public final View getView(int position, View convertView, ViewGroup parent){
        Modelable modelable = getItem(position);
        Inflatable<? extends Modelable> inflatable = null;
        if(convertView != null){
            inflatable = (Inflatable<? extends Modelable>) convertView.getTag();
        }
        if (inflatable == null){
            int viewType = modelable.getViewType();
            inflatable = listener.getInflatable(viewType, inflater, parent);
            convertView = inflatable.getItemView();
            convertView.setTag(inflatable);
        }
        inflatable.bindModel(modelable, position);
        return convertView;
    }

    @Override
    public final View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @Override
    public final boolean isEnabled(int position){
        return getItem(position).isEnabled();
    }

    @Override
    public final int getItemViewType(int position){
        return getItem(position).getViewType();
    }

    @Override
    public final int getViewTypeCount(){
        return typeCount;
    }

    @Override
    public final boolean hasStableIds() {
        return true;
    }

// ++++++++++++++++| PRIVATE METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private Listable<Modelable> getModelableListable(){
        return listener.getModelableListable();
    }

    private Modelable getModelable(int position){
        return getModelableListable().getItem(position);
    }
}
