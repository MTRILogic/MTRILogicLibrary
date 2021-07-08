package com.mtrilogic.abstracts;

import android.view.View;

import androidx.annotation.NonNull;

import com.mtrilogic.classes.Mapable;
import com.mtrilogic.interfaces.Bindable;
import com.mtrilogic.interfaces.ExpandableItemListener;
import com.mtrilogic.views.ExpandableView;

@SuppressWarnings({"unused","WeakerAccess"})
public abstract class ExpandableChild <M extends Modelable> implements Bindable<M>, View.OnLongClickListener, View.OnClickListener {
    protected final ExpandableItemListener listener;
    protected final View itemView;
    protected int groupPosition;
    protected int childPosition;
    protected boolean lastChild;
    protected M model;

// ++++++++++++++++| PROTECTED CONSTRUCTORS |+++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public ExpandableChild(@NonNull View itemView, @NonNull ExpandableItemListener listener){
        this.itemView = itemView;
        this.listener = listener;
    }

// ++++++++++++++++| PUBLIC METHODS |+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public final View getItemView(){
        itemView.setOnLongClickListener(this);
        itemView.setOnClickListener(this);
        onBindItemView();
        return itemView;
    }

    public final void bindModel(@NonNull Modelable modelable, int groupPosition, int childPosition, boolean lastChild){
        model = getModelFromModelable(modelable);
        this.groupPosition = groupPosition;
        this.childPosition = childPosition;
        this.lastChild = lastChild;
        onBindModel();
    }

// ++++++++++++++++| PUBLIC OVERRIDE METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++

    @Override
    public final boolean onLongClick(View v) {
        return listener.onChildLongClick(model, groupPosition, childPosition, lastChild);
    }

    @Override
    public final void onClick(View v) {
        listener.onChildClick(model, groupPosition, childPosition, lastChild);
    }

// ++++++++++++++++| PROTECTED METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    protected final void autoDelete(){
        Mapable<Modelable> modelableMapable = listener.getModelableMapable();
        if (modelableMapable.deleteChild(groupPosition, model)){
            listener.getExpandableAdapter().notifyDataSetChanged();
            if (modelableMapable.getChildCount(groupPosition) == 0){
                ExpandableView lvlItems = listener.getExpandableView();
                if (lvlItems.isGroupExpanded(groupPosition)){
                    lvlItems.collapseGroup(groupPosition);
                }
            }
        }
    }
}
