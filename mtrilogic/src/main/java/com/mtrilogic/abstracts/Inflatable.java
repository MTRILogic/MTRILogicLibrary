package com.mtrilogic.abstracts;

import android.view.View;

import androidx.annotation.NonNull;

import com.mtrilogic.interfaces.Bindable;
import com.mtrilogic.interfaces.InflatableItemListener;

@SuppressWarnings({"unused","WeakerAccess"})
public abstract class Inflatable<M extends Modelable> implements Bindable<M>, View.OnLongClickListener, View.OnClickListener {
    protected final InflatableItemListener listener;
    protected final View itemView;
    protected int position;
    protected M model;

// ++++++++++++++++| PUBLIC CONSTRUCTORS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public Inflatable(@NonNull View itemView, @NonNull InflatableItemListener listener){
        this.itemView = itemView;
        this.listener = listener;
    }

// ++++++++++++++++| PUBLIC OVERRIDE METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++

    @Override
    public boolean onLongClick(View v) {
        return listener.onItemLongClick(model, position);
    }

    @Override
    public void onClick(View v) {
        listener.onItemClick(model, position);
    }

// ++++++++++++++++| PUBLIC METHODS |+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public final View getItemView(){
        itemView.setOnLongClickListener(this);
        itemView.setOnClickListener(this);
        onBindItemView();
        return itemView;
    }

    public final void bindModel(@NonNull Modelable modelable, int position){
        model = getModelFromModelable(modelable);
        this.position = position;
        onBindModel();
    }

// ++++++++++++++++| PROTECTED METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    protected final void autoDelete(){
        if (listener.getModelableListable().deleteItem(model)){
            listener.getInflatableAdapter().notifyDataSetChanged();
        }
    }
}
