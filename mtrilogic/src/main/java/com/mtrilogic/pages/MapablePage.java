package com.mtrilogic.pages;

import android.os.Bundle;

import com.mtrilogic.abstracts.Modelable;
import com.mtrilogic.abstracts.Paginable;
import com.mtrilogic.abstracts.PaginableCreator;
import com.mtrilogic.classes.Mapable;

@SuppressWarnings("unused")
public class MapablePage extends Paginable {
    public static final Creator<MapablePage> CREATOR = new PaginableCreator<MapablePage>() {
        @Override
        protected MapablePage createFromData(Bundle data) {
            return new MapablePage(data);
        }

        @Override
        public MapablePage[] newArray(int size) {
            return new MapablePage[size];
        }
    };

    private Mapable<Modelable> modelableMapable;

    // =============================================================================================

    public MapablePage() {
        super();
    }

    public MapablePage(String pageTitle, String tagName, long itemId, int viewType) {
        super(pageTitle, tagName, itemId, viewType);
        modelableMapable = new Mapable<>();
    }

    protected MapablePage(Bundle data) {
        super(data);
    }

    // =============================================================================================

    public Mapable<Modelable> getModelableMapable() {
        return modelableMapable;
    }

    public void setModelableMapable(Mapable<Modelable> modelableMapable) {
        this.modelableMapable = modelableMapable;
    }

    // =============================================================================================

    @Override
    protected void onRestoreFromData(Bundle data) {
        super.onRestoreFromData(data);
        modelableMapable = new Mapable<>(data);
    }

    @Override
    protected void onSaveToData(Bundle data) {
        super.onSaveToData(data);
        modelableMapable.saveToData(data);
    }
}
