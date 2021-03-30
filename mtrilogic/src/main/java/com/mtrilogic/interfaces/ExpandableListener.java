package com.mtrilogic.interfaces;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.mtrilogic.abstracts.ExpandableChild;
import com.mtrilogic.abstracts.ExpandableGroup;
import com.mtrilogic.abstracts.Modelable;

@SuppressWarnings("unused")
public interface ExpandableListener extends OnMakeToastListener{
    ExpandableGroup<? extends Modelable> getExpandableGroup(int viewType, @NonNull LayoutInflater inflater, @NonNull ViewGroup parent);
    ExpandableChild<? extends Modelable> getExpandableChild(int viewType, @NonNull LayoutInflater inflater, @NonNull ViewGroup parent);
}
