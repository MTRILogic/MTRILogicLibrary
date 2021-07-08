package com.mtrilogic.classes;

import android.os.Bundle;

import com.mtrilogic.abstracts.Modelable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import androidx.annotation.NonNull;

@SuppressWarnings("unused")
public final class Mapable<M extends Modelable>{
    private final LinkedHashMap<M, Listable<M>> childListableMap;
    private final Listable<M> groupListable;

    private Listable<M> lastChildListable;

// ++++++++++++++++| PUBLIC CONSTRUCTORS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public Mapable(){
        childListableMap = new LinkedHashMap<>();
        groupListable = new Listable<>();
    }

    public Mapable(@NonNull Bundle data){
        groupListable = new Listable<>(data);
        childListableMap = new LinkedHashMap<>();
        for (M model : groupListable.getList()){
            childListableMap.put(model, new Listable<>(data, model.getItemId()));
        }
    }

// ++++++++++++++++| PUBLIC METHODS |+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public final HashMap<M, Listable<M>> getChildListableMap() {
        return childListableMap;
    }

    public final Listable<M> getGroupListable() {
        return groupListable;
    }

    public final Listable<M> getLastChildListable() {
        return lastChildListable;
    }

    public final void saveToData(@NonNull Bundle data){
        if (childListableMap != null){
            groupListable.saveToData(data);
            for (Map.Entry<M, Listable<M>> entry : childListableMap.entrySet()){
                entry.getValue().saveToData(data, entry.getKey().getItemId());
            }
        }
    }

    public final boolean appendGroup(@NonNull M group){
        if (groupListable.appendItem(group)){
            lastChildListable = childListableMap.put(group, new Listable<>());
            return true;
        }
        return false;
    }

    public final boolean appendChild(int groupPosition, @NonNull M child){
        Listable<M> childListable = getChildListable(groupPosition);
        return childListable != null && childListable.appendItem(child);
    }

    public final boolean insertGroup(int groupPosition, @NonNull M group){
        if (groupListable.insertItem(groupPosition, group)){
            lastChildListable = childListableMap.put(group, new Listable<>());
            return true;
        }
        return false;
    }

    public final boolean insertChild(int groupPosition, int childPosition, @NonNull M child){
        Listable<M> childListable = getChildListable(groupPosition);
        return childListable != null && childListable.insertItem(childPosition, child);
    }

    public final M getGroup(int groupPosition){
        return groupListable.getItem(groupPosition);
    }

    public final M getChild(int groupPosition, int childPosition){
        Listable<M> childListable = getChildListable(groupPosition);
        return childListable != null ? childListable.getItem(childPosition) : null;
    }

    public final M setGroup(int groupPosition, @NonNull M group){
        M lastGroup = groupListable.setItem(groupPosition, group);
        lastChildListable = childListableMap.remove(lastGroup);
        childListableMap.put(group, lastChildListable);
        return lastGroup;
    }

    public final M setChild(int groupPosition, int childPosition, @NonNull M child){
        Listable<M> childListable = getChildListable(groupPosition);
        return childListable != null ? childListable.setItem(childPosition, child) : null;
    }

    public final boolean containsGroup(@NonNull M group){
        return groupListable.containsItem(group);
    }

    public final boolean containsChild(int groupPosition, @NonNull M child){
        Listable<M> childListable = getChildListable(groupPosition);
        return childListable != null && childListable.containsItem(child);
    }

    public final int getGroupPosition(@NonNull M group){
        return groupListable.getItemPosition(group);
    }

    public final int getChildPosition(int groupPosition, @NonNull M child){
        Listable<M> childListable = getChildListable(groupPosition);
        return childListable != null ? childListable.getItemPosition(child) : Base.INVALID_POSITION;
    }

    public final boolean deleteGroup(@NonNull M group){
        if (groupListable.deleteItem(group)){
            lastChildListable = childListableMap.remove(group);
            return true;
        }
        return false;
    }

    public final boolean deleteChild(int groupPosition, @NonNull M child){
        Listable<M> childListable = getChildListable(groupPosition);
        return childListable != null && childListable.deleteItem(child);
    }

    public final int getGroupCount(){
        return groupListable.getItemCount();
    }

    public final int getChildCount(int groupPosition){
        Listable<M> childListable = getChildListable(groupPosition);
        return childListable != null ? childListable.getItemCount() : Base.INVALID_POSITION;
    }

    public final void reset(){
        childListableMap.clear();
        groupListable.reset();
    }

    // =============================================================================================

    private Listable<M> getChildListable(int groupPosition){
        return childListableMap.get(getGroup(groupPosition));
    }
}
