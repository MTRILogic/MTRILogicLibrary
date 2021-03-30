package com.mtrilogic.abstracts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.mtrilogic.adapters.ExpandableAdapter;
import com.mtrilogic.classes.Listable;
import com.mtrilogic.interfaces.ExpandableItemListener;
import com.mtrilogic.views.ExpandableView;

@SuppressWarnings({"unused","WeakerAccess"})
public abstract class ExpandableGroup<M extends Modelable> {
    protected final ExpandableItemListener listener;
    protected final View itemView;
    protected Listable<Modelable> childListable;
    protected int groupPosition;
    protected boolean expanded;
    protected M model;

// ++++++++++++++++| PROTECTED ABSTRACT METHODS |+++++++++++++++++++++++++++++++++++++++++++++++++++

    protected abstract M getModel(Modelable modelable);
    protected abstract void onBindHolder();

// ++++++++++++++++| PUBLIC CONSTRUCTORS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public ExpandableGroup(@NonNull Context context, int resource, @NonNull ViewGroup parent, @NonNull ExpandableItemListener listener){
        itemView = LayoutInflater.from(context).inflate(resource, parent, false);
        this.listener = listener;
    }

// ++++++++++++++++| PUBLIC METHODS |+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public View getItemView() {
        return itemView;
    }

    public void bindHolder(Modelable modelable, int groupPosition, boolean expanded){
        model = getModel(modelable);
        ExpandableAdapter adapter = listener.getExpandableAdapter();
        childListable = adapter != null ? adapter.getChildListable(model) : null;
        this.groupPosition = groupPosition;
        this.expanded = expanded;
        onBindHolder();
    }

// ++++++++++++++++| PROTECTED METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    protected void autoDelete(){
        ExpandableAdapter adapter = listener.getExpandableAdapter();
        if (adapter != null && adapter.deleteGroupModelable(model)){
            adapter.notifyDataSetChanged();
        }
    }

    protected void addNewChildModelable(Modelable childModelable, long idx){
        ExpandableAdapter adapter = listener.getExpandableAdapter();
        if (childModelable != null && adapter != null && adapter.appendChildModelable(model, childModelable)) {
            adapter.notifyDataSetChanged();
            childListable.setIdx(++idx);
            if (adapter.getChildrenCount(groupPosition) == 1){
                ExpandableView lvwItems = listener.getExpandableView();
                if (lvwItems != null && !lvwItems.isGroupExpanded(groupPosition)){
                    lvwItems.expandGroup(groupPosition);
                }
            }
        }
    }
}
