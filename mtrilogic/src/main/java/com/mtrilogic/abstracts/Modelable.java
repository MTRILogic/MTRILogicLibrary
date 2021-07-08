package com.mtrilogic.abstracts;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings({"unused"})
public abstract class Modelable implements Parcelable {
    private static final String ITEM_ID = "itemId", VIEW_TYPE = "viewType", ENABLED = "enabled";
    private long itemId;
    private int viewType;
    private boolean enabled;

// ++++++++++++++++| PUBLIC CONSTRUCTORS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public Modelable(){}

    public Modelable(long itemId, int viewType, boolean enabled){
        this.itemId = itemId;
        this.viewType = viewType;
        this.enabled = enabled;
    }

// ++++++++++++++++| PROTECTED CONSTRUCTORS |+++++++++++++++++++++++++++++++++++++++++++++++++++++++

    protected Modelable(Bundle data){
        onRestoreFromData(data);
    }

// ++++++++++++++++| PUBLIC OVERRIDE METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++

    @Override
    public final void writeToParcel(Parcel dest, int flags){
        Bundle data = new Bundle();
        onSaveToData(data);
        dest.writeBundle(data);
    }

    @Override
    public final int describeContents(){
        return 0;
    }

// ++++++++++++++++| PUBLIC METHODS |+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public final void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public final long getItemId(){
        return itemId;
    }

    public final void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public final int getViewType(){
        return viewType;
    }

    public final void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public final boolean isEnabled(){
        return enabled;
    }

    // ++++++++++++++++| PROTECTED METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    protected void onRestoreFromData(Bundle data){
        itemId = data.getLong(ITEM_ID);
        viewType = data.getInt(VIEW_TYPE);
        enabled = data.getBoolean(ENABLED);
    }

    protected void onSaveToData(Bundle data){
        data.putLong(ITEM_ID, itemId);
        data.putInt(VIEW_TYPE, viewType);
        data.putBoolean(ENABLED, enabled);
    }
}
