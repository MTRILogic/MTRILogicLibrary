package com.mtrilogic.interfaces;

import android.view.ViewGroup;

import com.mtrilogic.abstracts.Modelable;
import com.mtrilogic.abstracts.Recyclable;

@SuppressWarnings("unused")
public interface RecyclableListener extends OnMakeToastListener{
    Recyclable<? extends Modelable> getRecyclable(int viewType, ViewGroup parent);
}
