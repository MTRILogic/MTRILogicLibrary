package com.mtrilogic.interfaces;

import androidx.annotation.NonNull;

import com.mtrilogic.adapters.ExpandableAdapter;
import com.mtrilogic.views.ExpandableView;

@SuppressWarnings("unused")
public interface ExpandableItemListener extends OnMakeToastListener{
    @NonNull ExpandableAdapter getExpandableAdapter();
    @NonNull ExpandableView getExpandableView();
}
