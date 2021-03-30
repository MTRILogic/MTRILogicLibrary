package com.mtrilogic.interfaces;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mtrilogic.adapters.RecyclableAdapter;

@SuppressWarnings("unused")
public interface RecyclableItemListener extends OnMakeToastListener{
    @NonNull RecyclableAdapter getRecyclableAdapter();
    @NonNull RecyclerView getRecyclerView();
}
