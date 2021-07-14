package com.mtrilogic.classes;

import android.os.Bundle;

import com.mtrilogic.abstracts.Modelable;

import java.util.ArrayList;

import androidx.annotation.NonNull;

@SuppressWarnings("unused")
public final class Listable<M extends Modelable>{
    private static final String LIST = "list", IDX = "idx";

    private final ArrayList<M> list;
    private long idx;

    private M lastItem;

// ++++++++++++++++| PUBLIC CONSTRUCTORS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public Listable(){
        list = new ArrayList<>();
    }

    public Listable(@NonNull Bundle data){
        list = data.getParcelableArrayList(LIST);
        idx = data.getLong(IDX);
    }

    public Listable(@NonNull Bundle data, long itemId){
        list = data.getParcelableArrayList(LIST + itemId);
        idx = data.getLong(IDX + itemId);
    }

// ++++++++++++++++| PUBLIC METHODS |+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public final long getIdx(){
        return idx;
    }

    public final void setIdx(long idx){
        this.idx = idx;
    }

    public final void saveToData(@NonNull Bundle data){
        data.putParcelableArrayList(LIST, list);
        data.putLong(IDX, idx);
    }

    public final void saveToData(@NonNull Bundle data, long itemId){
        data.putParcelableArrayList(LIST + itemId, list);
        data.putLong(IDX + itemId, idx);
    }

    public final boolean appendItem(@NonNull M item){
        if (list.add(item)){
            item.setItemId(idx++);
            return true;
        }
        return false;
    }

    public final boolean appendItemList(@NonNull ArrayList<M> itemList){
        if (list.addAll(itemList)){
            for (Modelable modelable : itemList){
                modelable.setItemId(idx++);
            }
            return true;
        }
        return false;
    }

    public final boolean insertItem(int position, @NonNull M item){
        if(isValidPosition(position)){
            list.add(position, item);
            item.setItemId(idx++);
            return true;
        }
        return false;
    }

    public final boolean insertItemList(int position, @NonNull ArrayList<M> itemList){
        if (isValidPosition(position) && list.addAll(position, itemList)){
            for (Modelable modelable : itemList){
                modelable.setItemId(idx++);
            }
            return true;
        }
        return false;
    }

    public final M getItem(int position){
        return isValidPosition(position) ? list.get(position) : null;
    }

    public final ArrayList<M> getList(){
        return list;
    }

    public final boolean setItem(int position, @NonNull M item){
        lastItem = null;
        if (isValidPosition(position)){
            lastItem = list.set(position, item);
            return true;
        }
        return false;
    }

    //@SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public final boolean containsItem(@NonNull M item){
        return list.contains(item);
    }

    public final boolean containsItemList(@NonNull ArrayList<M> itemList){
        return list.containsAll(itemList);
    }

    public final boolean retainItemList(@NonNull ArrayList<M> itemList){
        return list.retainAll(itemList);
    }

    public final int getItemPosition(@NonNull M item){
        return list.indexOf(item);
    }

    public final boolean deleteItem(@NonNull M item){
        return list.remove(item);
    }

    public final boolean deleteItemList(@NonNull ArrayList<M> itemList){
        return list.removeAll(itemList);
    }

    public final int getItemCount(){
        return list.size();
    }

    public M getLastItem() {
        return lastItem;
    }

    public final void reset(){
        list.clear();
        idx = 0;
    }

// ++++++++++++++++| PRIVATE METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private boolean isValidPosition(int position){
        return position > Base.INVALID_POSITION && position < list.size();
    }
}
