package com.mtrilogic.abstracts;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.mtrilogic.interfaces.Bindable;
import com.mtrilogic.interfaces.RecyclableItemListener;

@SuppressWarnings({"unused","WeakerAccess"})
public abstract class Recyclable<M extends Modelable> extends RecyclerView.ViewHolder implements Bindable<M>, View.OnLongClickListener, View.OnClickListener {
    protected final RecyclableItemListener listener;
    protected int position;
    protected M model;

// ++++++++++++++++| PUBLIC CONSTRUCTORS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public Recyclable(@NonNull View itemView, @NonNull RecyclableItemListener listener){
        super(itemView);
        this.listener = listener;
    }

// ++++++++++++++++| PUBLIC OVERRIDE METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++

    @Override
    public final boolean onLongClick(View v) {
        return listener.onItemLongClick(model, position);
    }

    @Override
    public final void onClick(View v) {
        listener.onItemClick(model, position);
    }

// ++++++++++++++++| PUBLIC METHODS |+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public final void bindModel(@NonNull Modelable modelable, int position){
        model = getModelFromModelable(modelable);
        this.position = position;
        onBindModel();
    }

    public final void bindItemView(){
        itemView.setOnLongClickListener(this);
        itemView.setOnClickListener(this);
        onBindItemView();
    }

// ++++++++++++++++| PROTECTED METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    protected final void autoDelete(){
        if (listener.getModelableListable().deleteItem(model)){
            listener.getRecyclableAdapter().notifyDataSetChanged();
        }
    }
}
