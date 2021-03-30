package com.mtrilogic.abstracts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.mtrilogic.adapters.ExpandableAdapter;
import com.mtrilogic.classes.Listable;
import com.mtrilogic.interfaces.ExpandableAdapterListener;
import com.mtrilogic.interfaces.OnMakeToastListener;
import com.mtrilogic.views.ExpandableView;

@SuppressWarnings({"unused","WeakerAccess"})
public abstract class ExpandableGroup<M extends Modelable> {
    protected final OnMakeToastListener listener;
    protected final View itemView;
    protected ExpandableAdapter adapter;
    protected Listable<Modelable> childListable;
    protected ExpandableView lvwItems;
    protected Context context;
    protected int groupPosition;
    protected boolean expanded;
    protected M model;

// ++++++++++++++++| PROTECTED ABSTRACT METHODS |+++++++++++++++++++++++++++++++++++++++++++++++++++

    protected abstract M getModel(Modelable modelable);

    protected abstract void onBindHolder();

// ++++++++++++++++| PUBLIC CONSTRUCTORS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public ExpandableGroup(@NonNull Context context, int resource, @NonNull ViewGroup parent,
                           @NonNull ExpandableAdapterListener listener){
        itemView = LayoutInflater.from(context).inflate(resource, parent, false);
        this.context = context;
        this.listener = listener;
        adapter = listener.getExpandableAdapter();
        lvwItems = listener.getExpandableView();
    }

// ++++++++++++++++| PUBLIC METHODS |+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public View getItemView() {
        return itemView;
    }

    public void bindHolder(Modelable modelable, int groupPosition, boolean expanded){
        model = getModel(modelable);
        childListable = adapter != null ? adapter.getChildListable(model) : null;
        this.groupPosition = groupPosition;
        this.expanded = expanded;
        onBindHolder();
    }

// ++++++++++++++++| PROTECTED METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    protected void autoDelete(){
        if (adapter != null && adapter.deleteGroupModelable(model)){
            adapter.notifyDataSetChanged();
        }
    }

    protected void addNewChildModelable(Modelable childModelable, long idx){
        if (childModelable != null && adapter != null && adapter.appendChildModelable(model, childModelable)) {
            adapter.notifyDataSetChanged();
            childListable.setIdx(++idx);
            if (adapter.getChildrenCount(groupPosition) == 1){
                if (lvwItems != null && !lvwItems.isGroupExpanded(groupPosition)){
                    lvwItems.expandGroup(groupPosition);
                }
            }
        }
    }
}
