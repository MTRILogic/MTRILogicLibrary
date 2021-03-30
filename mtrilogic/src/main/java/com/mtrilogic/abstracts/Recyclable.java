package com.mtrilogic.abstracts;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mtrilogic.adapters.RecyclableAdapter;
import com.mtrilogic.interfaces.RecyclableItemListener;

@SuppressWarnings({"unused","WeakerAccess"})
public abstract class Recyclable<M extends Modelable> extends RecyclerView.ViewHolder {
    protected final RecyclableItemListener listener;
    protected int position;
    protected M model;

// ++++++++++++++++| PROTECTED ABSTRACT METHODS |+++++++++++++++++++++++++++++++++++++++++++++++++++

    protected abstract M getModel(Modelable modelable);

    protected abstract void onBindHolder();

// ++++++++++++++++| PUBLIC CONSTRUCTORS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public Recyclable(@NonNull Context context, int resource, @NonNull ViewGroup parent, @NonNull RecyclableItemListener listener){
        super(LayoutInflater.from(context).inflate(resource, parent, false));
        this.listener = listener;
    }

// ++++++++++++++++| PUBLIC METHODS |+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void bindHolder(Modelable modelable, int position){
        model = getModel(modelable);
        this.position = position;
        onBindHolder();
    }

// ++++++++++++++++| PROTECTED METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    protected void autoDelete(){
        RecyclableAdapter adapter = listener.getRecyclableAdapter();
        if (adapter != null){
            if (adapter.removeModelable(model)){
                adapter.notifyDataSetChanged();
            }
        }
    }
}
