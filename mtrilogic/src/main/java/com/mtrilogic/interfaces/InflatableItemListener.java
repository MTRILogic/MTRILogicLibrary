package com.mtrilogic.interfaces;

import androidx.annotation.NonNull;

import com.mtrilogic.abstracts.Modelable;
import com.mtrilogic.adapters.InflatableAdapter;
import com.mtrilogic.classes.Listable;
import com.mtrilogic.views.InflatableView;

@SuppressWarnings("unused")
public interface InflatableItemListener extends ItemListener{
    @NonNull Listable<Modelable> getModelableListable();
    @NonNull InflatableAdapter getInflatableAdapter();
    @NonNull InflatableView getInflatableView();
}
