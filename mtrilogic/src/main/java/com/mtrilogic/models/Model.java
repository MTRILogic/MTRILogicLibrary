package com.mtrilogic.models;

import android.os.Bundle;

import com.mtrilogic.abstracts.Modelable;
import com.mtrilogic.abstracts.ModelableCreator;

@SuppressWarnings("unused")
public class Model extends Modelable {
    public static final Creator<Model> CREATOR = new ModelableCreator<Model>() {
        @Override
        protected Model createFromData(Bundle data) {
            return new Model(data);
        }

        @Override
        public Model[] newArray(int size) {
            return new Model[size];
        }
    };

    public Model() {
        super();
    }

    public Model(long itemId, int viewType, boolean enabled) {
        super(itemId, viewType, enabled);
    }

    private Model(Bundle data) {
        super(data);
    }
}
