package com.mtrilogic.pages;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.mtrilogic.abstracts.Modelable;
import com.mtrilogic.classes.Listable;

@SuppressWarnings("unused")
public class ListablePage extends Page {
    private Listable<Modelable> modelableListable;

    // =============================================================================================

    public ListablePage() {
        super();
    }

    public ListablePage(String pageTitle, String tagName, long itemId, int viewType) {
        super(pageTitle, tagName, itemId, viewType);
        modelableListable = new Listable<>();
    }

    protected ListablePage(Bundle data) {
        super(data);
    }


    // =============================================================================================

    public Listable<Modelable> getModelableListable() {
        return modelableListable;
    }

    public void setModelableListable(Listable<Modelable> modelableListable) {
        this.modelableListable = modelableListable;
    }

    // =============================================================================================

    @Override
    protected void onRestoreFromData(@NonNull Bundle data) {
        super.onRestoreFromData(data);
        modelableListable = new Listable<>(data);
    }

    @Override
    protected void onSaveToData(@NonNull Bundle data) {
        super.onSaveToData(data);
        modelableListable.saveToData(data);
    }
}
