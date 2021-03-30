package com.mtrilogic.interfaces;

import androidx.annotation.NonNull;

import com.mtrilogic.abstracts.Modelable;

@SuppressWarnings("unused")
public interface ItemListener extends OnMakeToastListener {
    void onItemClick(@NonNull Modelable modelable, int position);
    void onItemLongClick(@NonNull Modelable modelable, int position);
}
