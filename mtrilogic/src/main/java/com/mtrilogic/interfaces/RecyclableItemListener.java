package com.mtrilogic.interfaces;

import androidx.recyclerview.widget.RecyclerView;

import com.mtrilogic.adapters.RecyclableAdapter;

@SuppressWarnings("unused")
public interface RecyclableItemListener extends OnMakeToastListener{
    RecyclableAdapter getRecyclableAdapter();
    RecyclerView getRecyclerView();
}
