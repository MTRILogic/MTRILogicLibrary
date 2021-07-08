package com.mtrilogic.abstracts;

import android.view.View;

import androidx.annotation.NonNull;

import com.mtrilogic.adapters.ExpandableAdapter;
import com.mtrilogic.classes.Mapable;
import com.mtrilogic.interfaces.Bindable;
import com.mtrilogic.interfaces.ExpandableItemListener;
import com.mtrilogic.views.ExpandableView;

@SuppressWarnings({"unused","WeakerAccess"})
public abstract class ExpandableGroup<M extends Modelable> implements Bindable<M>, View.OnLongClickListener, View.OnClickListener {
    protected final ExpandableItemListener listener;
    protected final View itemView;
    protected int groupPosition;
    protected boolean expanded;
    protected M model;

// ++++++++++++++++| PUBLIC CONSTRUCTORS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public ExpandableGroup(@NonNull View itemView, @NonNull ExpandableItemListener listener){
        this.itemView = itemView;
        this.listener = listener;
    }

// ++++++++++++++++| PUBLIC METHODS |+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public final View getItemView() {
        itemView.setOnLongClickListener(this);
        itemView.setOnClickListener(this);
        onBindItemView();
        return itemView;
    }

    public final void bindModel(@NonNull Modelable modelable, int groupPosition, boolean expanded){
        model = getModelFromModelable(modelable);
        ExpandableAdapter adapter = listener.getExpandableAdapter();
        this.groupPosition = groupPosition;
        this.expanded = expanded;
        onBindModel();
    }

// ++++++++++++++++| PUBLIC OVERRIDE METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++

    @Override
    public final boolean onLongClick(View v) {
        return listener.onGroupLongClick(model, groupPosition, expanded);
    }

    @Override
    public final void onClick(View v) {
        listener.onGroupClick(model, groupPosition, expanded);
    }

// ++++++++++++++++| PROTECTED METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    protected final void autoDelete(){
        if (listener.getModelableMapable().deleteGroup(model)){
            listener.getExpandableAdapter().notifyDataSetChanged();
        }
    }

    protected final void addChild(@NonNull Modelable child){
        Mapable<Modelable> modelableMapable = listener.getModelableMapable();
        if (modelableMapable.appendChild(groupPosition, child)){
            listener.getExpandableAdapter().notifyDataSetChanged();
            if (modelableMapable.getChildCount(groupPosition) == 1){
                ExpandableView lvwItems = listener.getExpandableView();
                if (!lvwItems.isGroupExpanded(groupPosition)){
                    lvwItems.expandGroup(groupPosition);
                }
            }
        }
    }

    protected final void insertChild(int childPosition, @NonNull Modelable child){

    }

    protected final void deleteChild(@NonNull Modelable child){

    }
}
