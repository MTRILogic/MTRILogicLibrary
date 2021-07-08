package com.mtrilogic.interfaces;

import androidx.annotation.NonNull;

import com.mtrilogic.abstracts.Modelable;
import com.mtrilogic.adapters.ExpandableAdapter;
import com.mtrilogic.classes.Mapable;
import com.mtrilogic.views.ExpandableView;

@SuppressWarnings("unused")
public interface ExpandableItemListener extends OnMakeToastListener{
    boolean onChildLongClick(@NonNull Modelable modelable, int groupPosition, int childPosition, boolean lastChild);
    boolean onGroupLongClick(@NonNull Modelable modelable, int groupPosition, boolean expanded);
    void onChildClick(@NonNull Modelable modelable, int groupPosition, int childPosition, boolean lastChild);
    void onGroupClick(@NonNull Modelable modelable, int groupPosition, boolean expanded);
    @NonNull Mapable<Modelable> getModelableMapable();
    @NonNull ExpandableAdapter getExpandableAdapter();
    @NonNull ExpandableView getExpandableView();
}
