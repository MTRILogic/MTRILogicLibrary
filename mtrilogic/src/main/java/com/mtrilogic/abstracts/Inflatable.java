package com.mtrilogic.abstracts;

import android.view.View;

import androidx.annotation.NonNull;

import com.mtrilogic.adapters.InflatableAdapter;
import com.mtrilogic.interfaces.Bindable;
import com.mtrilogic.interfaces.InflatableItemListener;

@SuppressWarnings({"unused","WeakerAccess"})
public abstract class Inflatable<M extends Modelable> implements Bindable<M> {
    protected final InflatableItemListener listener;
    protected final View itemView;
    protected int position;
    protected M model;

// ++++++++++++++++| PUBLIC CONSTRUCTORS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public Inflatable(@NonNull View itemView, @NonNull InflatableItemListener listener){
        this.itemView = itemView;
        this.listener = listener;
        onBindItemView();
    }

// ++++++++++++++++| PUBLIC METHODS |+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public View getItemView(){
        return itemView;
    }

    public void bindModel(@NonNull Modelable modelable, int position){
        model = getModelFromModelable(modelable);
        this.position = position;
        onBindModel();
    }

// ++++++++++++++++| PROTECTED METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    protected void autoDelete(){
        InflatableAdapter adapter = listener.getInflatableAdapter();
        if (adapter.removeModelable(model)){
            adapter.notifyDataSetChanged();
        }
    }
}
