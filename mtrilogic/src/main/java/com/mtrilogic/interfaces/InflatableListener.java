package com.mtrilogic.interfaces;

import android.view.ViewGroup;

import com.mtrilogic.abstracts.Inflatable;
import com.mtrilogic.abstracts.Modelable;

@SuppressWarnings("unused")
public interface InflatableListener extends OnMakeToastListener{
    Inflatable<? extends Modelable> getInflatable(int viewType, ViewGroup parent);
}
