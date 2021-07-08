package com.mtrilogic.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.mtrilogic.abstracts.ExpandableChild;
import com.mtrilogic.abstracts.ExpandableGroup;
import com.mtrilogic.abstracts.Modelable;
import com.mtrilogic.classes.Mapable;
import com.mtrilogic.interfaces.ExpandableListener;

import androidx.annotation.NonNull;

@SuppressWarnings({"unused","WeakerAccess","UnusedReturnValue"})
public final class ExpandableAdapter extends BaseExpandableListAdapter{
    private final ExpandableListener listener;
    private final LayoutInflater inflater;
    private final int groupTypeCount, childTypeCount;

// ++++++++++++++++| PUBLIC CONSTRUCTORS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public ExpandableAdapter(@NonNull LayoutInflater inflater, @NonNull ExpandableListener listener, int groupTypeCount, int childTypeCount){
        this.inflater = inflater;
        this.listener = listener;
        this.groupTypeCount = Math.max(groupTypeCount, 1);
        this.childTypeCount = Math.max(childTypeCount, 1);
    }

    // ++++++++++++++++| PUBLIC OVERRIDE METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++

    @Override
    public final int getGroupCount(){
        return getModelableMapable().getGroupCount();
    }

    @Override
    public final int getChildrenCount(int groupPosition){
        return getModelableMapable().getChildCount(groupPosition);
    }

    @Override
    public final Modelable getGroup(int groupPosition){
        return getModelableMapable().getGroup(groupPosition);
    }

    @Override
    public final Modelable getChild(int groupPosition, int childPosition){
        return getModelableMapable().getChild(groupPosition, childPosition);
    }

    @Override
    public final long getGroupId(int groupPosition){
        return getGroup(groupPosition).getItemId();
    }

    @Override
    public final long getChildId(int groupPosition, int childPosition){
        return getChild(groupPosition, childPosition).getItemId();
    }

    @Override
    public final boolean hasStableIds(){
        return true;
    }

    @Override
    public final View getGroupView(int groupPosition, boolean expanded, View view, ViewGroup parent){
        Modelable groupModelable = getGroup(groupPosition);
        ExpandableGroup<? extends Modelable> expandableGroup = null;
        if(view != null){
            expandableGroup = (ExpandableGroup<? extends Modelable>)view.getTag();
        }
        if (expandableGroup == null){
            int viewType = groupModelable.getViewType();
            expandableGroup = listener.getExpandableGroup(viewType, inflater, parent);
            view = expandableGroup.getItemView();
            view.setTag(expandableGroup);
        }
        expandableGroup.bindModel(groupModelable, groupPosition, expanded);
        return view;
    }

    @Override
    public final View getChildView(int groupPosition, int childPosition, boolean lastChild, View view, ViewGroup parent){
        Modelable childModelable = getChild(groupPosition, childPosition);
        ExpandableChild<? extends Modelable> expandableChild = null;
        if(view != null){
            expandableChild = (ExpandableChild<? extends Modelable>)view.getTag();
        }
        if (expandableChild == null){
            int viewType = childModelable.getViewType();
            expandableChild = listener.getExpandableChild(viewType, inflater, parent);
            view = expandableChild.getItemView();
            view.setTag(expandableChild);
        }
        expandableChild.bindModel(childModelable, groupPosition, childPosition, lastChild);
        return view;
    }

    @Override
    public final boolean isChildSelectable(int groupPosition, int childPosition){
        return getChild(groupPosition, childPosition).isEnabled();
    }

    @Override
    public final int getGroupType(int groupPosition){
        return getGroup(groupPosition).getViewType();
    }

    @Override
    public final int getChildType(int groupPosition, int childPosition){
        return getChild(groupPosition, childPosition).getViewType();
    }

    @Override
    public final int getGroupTypeCount(){
        return groupTypeCount;
    }

    @Override
    public final int getChildTypeCount(){
        return childTypeCount;
    }

    @Override
    public final long getCombinedChildId(long groupId, long childId){
        return 0x7000000000000000L | ((groupId & 0x7FFFFFFF) << 32) | childId;
    }

// ++++++++++++++++| PRIVATE METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private Mapable<Modelable> getModelableMapable(){
        return listener.getModelableMapable();
    }
}
