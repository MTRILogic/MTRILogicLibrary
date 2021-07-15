package com.mtrilogic.mtrilogicsample.models;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.mtrilogic.abstracts.Modelable;
import com.mtrilogic.abstracts.ModelableCreator;
import com.mtrilogic.mtrilogicsample.types.ChildType;

@SuppressWarnings({"unused","WeakerAccess"})
public class DataModel extends Modelable{
    public static final Creator<DataModel> CREATOR = new ModelableCreator<DataModel>() {
        @Override
        public DataModel createFromData(Bundle data) {
            return new DataModel(data);
        }

        @Override
        public DataModel[] newArray(int size) {
            return new DataModel[size];
        }
    };

    private static final String TITLE = "title", CONTENT = "content", CHECKED = "checked", RATING = "rating";

    private String title, content;
    private boolean checked;
    private float rating;

// ++++++++++++++++| PUBLIC CONSTRUCTORS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public DataModel(){}

    private DataModel(Bundle data){
        super(data);
    }

    public DataModel(long itemId, boolean checked){
        super(itemId, ChildType.DATA, false);
    }

// ++++++++++++++++| PUBLIC METHODS |+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public boolean isChecked(){
        return checked;
    }

    public void setChecked(boolean checked){
        this.checked = checked;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    // ++++++++++++++++| PROTECTED OVERRIDE METHODS |+++++++++++++++++++++++++++++++++++++++++++++++++++

    @Override
    protected void onRestoreFromData(@NonNull Bundle data) {
        super.onRestoreFromData(data);
        title = data.getString(TITLE);
        content = data.getString(CONTENT);
        checked = data.getBoolean(CHECKED);
        rating = data.getFloat(RATING);
    }

    @Override
    protected void onSaveToData(@NonNull Bundle data) {
        super.onSaveToData(data);
        data.putString(TITLE, title);
        data.putString(CONTENT, content);
        data.putBoolean(CHECKED, checked);
        data.putFloat(RATING, rating);
    }
}
