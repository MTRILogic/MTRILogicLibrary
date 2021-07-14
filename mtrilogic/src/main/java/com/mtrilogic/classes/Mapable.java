package com.mtrilogic.classes;

import android.os.Bundle;

import com.mtrilogic.abstracts.Modelable;

import java.util.ArrayList;
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
        lastChildListable = null;
        if (groupListable.appendItem(group)){
            lastChildListable = childListableMap.put(group, new Listable<>());
            return true;
        }
        return false;
    }

    public final boolean appendGroupList(ArrayList<M> groupList){
        lastChildListable = null;
        if (groupListable.appendItemList(groupList)){
            for (M group : groupListable.getList()){
                lastChildListable = childListableMap.put(group, new Listable<>());
            }
            return true;
        }
        return false;
    }

    public final boolean appendChild(int groupPosition, @NonNull M child){
        Listable<M> childListable = getChildListable(groupPosition);
        return childListable != null && childListable.appendItem(child);
    }

    public final boolean appendChildList(int groupPosition, @NonNull ArrayList<M> childList){
        Listable<M> childListable = getChildListable(groupPosition);
        return childListable != null && childListable.appendItemList(childList);
    }

    public final boolean insertGroup(int groupPosition, @NonNull M group){
        lastChildListable = null;
        if (groupListable.insertItem(groupPosition, group)){
            lastChildListable = childListableMap.put(group, new Listable<>());
            return true;
        }
        return false;
    }

    public final boolean insertGroupList(int position, @NonNull ArrayList<M> groupList){
        lastChildListable = null;
        if (groupListable.insertItemList(position, groupList)){
            for (M group : groupListable.getList()){
                lastChildListable = childListableMap.put(group, new Listable<>());
            }
            return true;
        }
        return false;
    }

    public final boolean insertChild(int groupPosition, int childPosition, @NonNull M child){
        Listable<M> childListable = getChildListable(groupPosition);
        return childListable != null && childListable.insertItem(childPosition, child);
    }

    public final boolean insertChildList(int groupPosition, int childPosition, @NonNull ArrayList<M> childList){
        Listable<M> childListable = getChildListable(groupPosition);
        return childListable != null && childListable.insertItemList(childPosition, childList);
    }

    public final M getGroup(int groupPosition){
        return groupListable.getItem(groupPosition);
    }

    public final ArrayList<M> getGroupList(){
        return groupListable.getList();
    }

    public final M getChild(int groupPosition, int childPosition){
        Listable<M> childListable = getChildListable(groupPosition);
        return childListable != null ? childListable.getItem(childPosition) : null;
    }

    public final ArrayList<M> getChildList(int groupPosition){
        Listable<M> childListable = getChildListable(groupPosition);
        return childListable != null ? childListable.getList() : null;
    }

    public final boolean setGroup(int groupPosition, @NonNull M group){
        lastChildListable = null;
        if (groupListable.setItem(groupPosition, group)){
            M lastGroup = groupListable.getLastItem();
            lastChildListable = childListableMap.remove(lastGroup);
            lastChildListable = childListableMap.put(group, lastChildListable);
            return true;
        }
        return false;
    }

    public final boolean setChild(int groupPosition, int childPosition, @NonNull M child){
        Listable<M> childListable = getChildListable(groupPosition);
        return childListable != null && childListable.setItem(childPosition, child);
    }

    public final boolean containsGroup(@NonNull M group){
        return groupListable.containsItem(group);
    }

    public final boolean containsGroupList(@NonNull ArrayList<M> groupList){
        return groupListable.containsItemList(groupList);
    }

    public final boolean containsChild(int groupPosition, @NonNull M child){
        Listable<M> childListable = getChildListable(groupPosition);
        return childListable != null && childListable.containsItem(child);
    }

    public final boolean containsChildList(int groupPosition, @NonNull ArrayList<M> childList){
        Listable<M> childListable = getChildListable(groupPosition);
        return childListable != null && childListable.containsItemList(childList);
    }

    public final boolean retainGroupList(@NonNull ArrayList<M> groupList){
        return groupListable.retainItemList(groupList);
    }

    public final boolean retainChildList(int groupPosition, @NonNull ArrayList<M> childList){
        Listable<M> childListable = getChildListable(groupPosition);
        return groupListable.retainItemList(childList);
    }

    public final int getGroupPosition(@NonNull M group){
        return groupListable.getItemPosition(group);
    }

    public final int getChildPosition(int groupPosition, @NonNull M child){
        Listable<M> childListable = getChildListable(groupPosition);
        return childListable != null ? childListable.getItemPosition(child) : Base.INVALID_POSITION;
    }

    public final boolean deleteGroup(@NonNull M group){
        lastChildListable = null;
        if (groupListable.deleteItem(group)){
            lastChildListable = childListableMap.remove(group);
            return true;
        }
        return false;
    }

    public final boolean deleteGroupList(@NonNull ArrayList<M> childList){
        lastChildListable = null;
        if (groupListable.deleteItemList(childList)){
            for (M group : groupListable.getList()){
                lastChildListable = childListableMap.remove(group);
                return true;
            }
        }
        return false;
    }

    public final boolean deleteChild(int groupPosition, @NonNull M child){
        Listable<M> childListable = getChildListable(groupPosition);
        return childListable != null && childListable.deleteItem(child);
    }

    public final boolean deleteChildList(int groupPosition, @NonNull ArrayList<M> childList){
        Listable<M> childListable = getChildListable(groupPosition);
        return childListable != null && childListable.deleteItemList(childList);
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
