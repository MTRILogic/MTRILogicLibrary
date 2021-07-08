package com.mtrilogic.interfaces;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mtrilogic.abstracts.Modelable;
import com.mtrilogic.adapters.RecyclableAdapter;
import com.mtrilogic.classes.Listable;

@SuppressWarnings("unused")
public interface RecyclableItemListener extends ItemListener{
    @NonNull Listable<Modelable> getModelableListable();
    @NonNull RecyclableAdapter getRecyclableAdapter();
    @NonNull RecyclerView getRecyclerView();
}
