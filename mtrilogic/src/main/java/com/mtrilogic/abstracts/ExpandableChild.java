package com.mtrilogic.abstracts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.mtrilogic.adapters.ExpandableAdapter;
import com.mtrilogic.interfaces.ExpandableItemListener;
import com.mtrilogic.views.ExpandableView;

@SuppressWarnings({"unused","WeakerAccess"})
public abstract class ExpandableChild <M extends Modelable> {
    protected final ExpandableItemListener listener;
    protected final View itemView;
    protected int groupPosition;
    protected int childPosition;
    protected boolean lastChild;
    protected M model;

// ++++++++++++++++| PUBLIC ABSTRACT METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++

    protected abstract M getModel(Modelable modelable);

    protected abstract void onBindHolder();

// ++++++++++++++++| PROTECTED CONSTRUCTORS |+++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public ExpandableChild(@NonNull Context context, int resource, @NonNull ViewGroup parent, @NonNull ExpandableItemListener listener){
        itemView = LayoutInflater.from(context).inflate(resource, parent, false);
        this.listener = listener;
    }

// ++++++++++++++++| PUBLIC METHODS |+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public View getItemView(){
        return itemView;
    }

    public void bindHolder(Modelable modelable, int groupPosition, int childPosition, boolean lastChild){
        model = getModel(modelable);
        this.groupPosition = groupPosition;
        this.childPosition = childPosition;
        this.lastChild = lastChild;
        onBindHolder();
    }

// ++++++++++++++++| PROTECTED METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    protected void autoDelete(){
        ExpandableAdapter adapter = listener.getExpandableAdapter();
        if (adapter != null){
            if (adapter.deleteChildModelable(adapter.getGroupModelable(groupPosition), model)){
                adapter.notifyDataSetChanged();
                if (adapter.getChildrenCount(groupPosition) == 0){
                    ExpandableView lvwItems = listener.getExpandableView();
                    if (lvwItems != null && lvwItems.isGroupExpanded(groupPosition)) {
                        lvwItems.collapseGroup(groupPosition);
                    }
                }
            }
        }
    }
}
