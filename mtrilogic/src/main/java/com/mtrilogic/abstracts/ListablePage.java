package com.mtrilogic.abstracts;

import android.os.Bundle;

import java.util.ArrayList;

@SuppressWarnings({"unused","WeakerAccess"})
public abstract class ListablePage extends Paginable {
    private static final String LIST = "list", IDX = "idx";
    private ArrayList<Modelable> modelableList;
    private long idx;

// ++++++++++++++++| PUBLIC CONSTRUCTORS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public ListablePage(){}

    public ListablePage(Bundle data){
        super(data);
    }

    public ListablePage(String pageTitle, String tagName, long itemId, int viewType){
        this(pageTitle, tagName, itemId, viewType, new ArrayList<>(), 0);
    }

    public ListablePage(String pageTitle, String tagName, long itemId, int viewType,
                        ArrayList<Modelable> modelableList, long idx){
        super(pageTitle, tagName, itemId, viewType);
        this.modelableList = modelableList;
        this.idx = idx;
    }

// ++++++++++++++++| PUBLIC METHODS |+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public ArrayList<Modelable> getModelableList(){
        return modelableList;
    }

    public void setModelableList(ArrayList<Modelable> modelableList){
        this.modelableList = modelableList;
    }

    public long getIdx(){
        return idx;
    }

    public void setIdx(long idx){
        this.idx = idx;
    }

// ++++++++++++++++| PROTECTED METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    @Override
    protected void onRestoreFromData(Bundle data) {
        super.onRestoreFromData(data);
        modelableList = data.getParcelableArrayList(LIST);
        idx = data.getLong(IDX);
    }

    @Override
    protected void onSaveToData(Bundle data) {
        super.onSaveToData(data);
        data.putParcelableArrayList(LIST, modelableList);
        data.putLong(IDX, idx);
    }
}