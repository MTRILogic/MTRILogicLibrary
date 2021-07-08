package com.mtrilogic.pages;

import android.os.Bundle;

import com.mtrilogic.abstracts.Modelable;
import com.mtrilogic.abstracts.Paginable;
import com.mtrilogic.abstracts.PaginableCreator;
import com.mtrilogic.classes.Listable;

@SuppressWarnings("unused")
public class ListablePage extends Paginable {
    public static final Creator<ListablePage> CREATOR = new PaginableCreator<ListablePage>() {
        @Override
        protected ListablePage createFromData(Bundle data) {
            return new ListablePage(data);
        }

        @Override
        public ListablePage[] newArray(int size) {
            return new ListablePage[size];
        }
    };

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
    protected void onRestoreFromData(Bundle data) {
        super.onRestoreFromData(data);
        modelableListable = new Listable<>(data);
    }

    @Override
    protected void onSaveToData(Bundle data) {
        super.onSaveToData(data);
        modelableListable.saveToData(data);
    }
}
