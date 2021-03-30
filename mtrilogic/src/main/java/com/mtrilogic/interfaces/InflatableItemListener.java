package com.mtrilogic.interfaces;

import androidx.annotation.NonNull;

import com.mtrilogic.adapters.InflatableAdapter;
import com.mtrilogic.views.InflatableView;

@SuppressWarnings("unused")
public interface InflatableItemListener extends OnMakeToastListener{
    @NonNull InflatableAdapter getInflatableAdapter();
    @NonNull InflatableView getInflatableView();
}
