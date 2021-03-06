package com.mtrilogic.interfaces;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.mtrilogic.abstracts.Modelable;
import com.mtrilogic.abstracts.Recyclable;
import com.mtrilogic.classes.Listable;

@SuppressWarnings("unused")
public interface RecyclableListener extends OnMakeToastListener{
    Recyclable<? extends Modelable> getRecyclable(int viewType, @NonNull LayoutInflater inflater, @NonNull ViewGroup parent);
    @NonNull Listable<Modelable> getModelableListable();
}
