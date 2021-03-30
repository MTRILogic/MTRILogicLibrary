package com.mtrilogic.abstracts;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.mtrilogic.adapters.RecyclableAdapter;
import com.mtrilogic.interfaces.Bindable;
import com.mtrilogic.interfaces.RecyclableItemListener;

@SuppressWarnings({"unused","WeakerAccess"})
public abstract class Recyclable<M extends Modelable> extends RecyclerView.ViewHolder implements Bindable<M> {
    protected final RecyclableItemListener listener;
    protected int position;
    protected M model;

// ++++++++++++++++| PUBLIC CONSTRUCTORS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public Recyclable(@NonNull View itemView, @NonNull RecyclableItemListener listener){
        super(itemView);
        this.listener = listener;
        onBindItemView();
    }

// ++++++++++++++++| PUBLIC METHODS |+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void bindModel(@NonNull Modelable modelable, int position){
        model = getModelFromModelable(modelable);
        this.position = position;
        onBindModel();
    }

// ++++++++++++++++| PROTECTED METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    protected void autoDelete(){
        RecyclableAdapter adapter = listener.getRecyclableAdapter();
        if (adapter.removeModelable(model)){
            adapter.notifyDataSetChanged();
        }
    }
}
