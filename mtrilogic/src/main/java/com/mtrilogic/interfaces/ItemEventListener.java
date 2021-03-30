package com.mtrilogic.interfaces;

import com.mtrilogic.abstracts.Modelable;

@SuppressWarnings("unused")
public interface ItemEventListener extends OnMakeToastListener {
    void onItemClick(Modelable modelable, int position);
    void onItemLongClick(Modelable modelable, int position);
}
