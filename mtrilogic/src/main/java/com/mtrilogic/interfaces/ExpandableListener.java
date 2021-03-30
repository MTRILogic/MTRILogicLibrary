package com.mtrilogic.interfaces;

import android.view.ViewGroup;

import com.mtrilogic.abstracts.ExpandableChild;
import com.mtrilogic.abstracts.ExpandableGroup;
import com.mtrilogic.abstracts.Modelable;

@SuppressWarnings("unused")
public interface ExpandableListener extends OnMakeToastListener{
    ExpandableGroup<? extends Modelable> getExpandableGroup(int viewType, ViewGroup parent);
    ExpandableChild<? extends Modelable> getExpandableChild(int viewType, ViewGroup parent);
}
