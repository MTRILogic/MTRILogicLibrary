package com.mtrilogic.abstracts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.mtrilogic.adapters.InflatableAdapter;
import com.mtrilogic.interfaces.InflatableItemListener;

@SuppressWarnings({"unused","WeakerAccess"})
public abstract class Inflatable<M extends Modelable> {
    protected final InflatableItemListener listener;
    protected final View itemView;
    protected int position;
    protected M model;

// ++++++++++++++++| PROTECTED ABSTRACT METHODS |+++++++++++++++++++++++++++++++++++++++++++++++++++

    protected abstract M getModel(Modelable modelable);
    protected abstract void onBindHolder();

// ++++++++++++++++| PUBLIC CONSTRUCTORS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public Inflatable(@NonNull Context context, int resource, @NonNull ViewGroup parent, @NonNull InflatableItemListener listener){
        itemView = LayoutInflater.from(context).inflate(resource, parent, false);
        this.listener = listener;
    }

// ++++++++++++++++| PUBLIC METHODS |+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public View getItemView(){
        return itemView;
    }

    public void bindHolder(Modelable modelable, int position){
        model = getModel(modelable);
        this.position = position;
        onBindHolder();
    }

// ++++++++++++++++| PROTECTED METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    protected void autoDelete(){
        InflatableAdapter adapter = listener.getInflatableAdapter();
        if (adapter != null){
            if (adapter.removeModelable(model)){
                adapter.notifyDataSetChanged();
            }
        }
    }
}
