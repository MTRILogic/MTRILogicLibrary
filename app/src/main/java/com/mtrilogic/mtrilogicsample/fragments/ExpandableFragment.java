package com.mtrilogic.mtrilogicsample.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mtrilogic.abstracts.ExpandableChild;
import com.mtrilogic.abstracts.ExpandableGroup;
import com.mtrilogic.abstracts.Fragmentable;
import com.mtrilogic.abstracts.Modelable;
import com.mtrilogic.adapters.ExpandableAdapter;
import com.mtrilogic.classes.Listable;
import com.mtrilogic.classes.Mapable;
import com.mtrilogic.interfaces.ExpandableAdapterListener;
import com.mtrilogic.interfaces.ExpandableListener;
import com.mtrilogic.mtrilogicsample.items.expandables.childs.ChildDataItem;
import com.mtrilogic.mtrilogicsample.items.expandables.groups.GroupDataItem;
import com.mtrilogic.mtrilogicsample.models.DataModel;
import com.mtrilogic.mtrilogicsample.pages.ExpandablePage;
import com.mtrilogic.mtrilogicsample.R;
import com.mtrilogic.mtrilogicsample.types.GroupType;
import com.mtrilogic.mtrilogicsample.types.ChildType;
import com.mtrilogic.views.ExpandableView;

@SuppressWarnings("unused")
public class ExpandableFragment extends Fragmentable<ExpandablePage> implements ExpandableListener, ExpandableAdapterListener {
    private static final String TAG = "ExpandableFragmentTAG";
    private ExpandableAdapter adapter;
    private ExpandableView lvwItems;
    private TextView lblContent;

// PROTECTED OVERRIDE METHODS |*********************************************************************

    @Override
    protected View onCreateViewFragment(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expandable,container,false);
        Listable<Modelable> groupListable = page.getGroupListable();
        Mapable<Modelable> childMapable = page.getChildMapable();
        adapter = new ExpandableAdapter(this, groupListable, childMapable, GroupType.COUNT, ChildType.COUNT);
        lvwItems = view.findViewById(R.id.lvw_items);
        lvwItems.setAdapter(adapter);
        TextView lblTitle = view.findViewById(R.id.lbl_title);
        lblTitle.setText(getString(R.string.title_item, page.getItemId()));
        lblContent = view.findViewById(R.id.lbl_content);
        ImageButton btnAddGroup = view.findViewById(R.id.btn_addGroup);
        btnAddGroup.setOnClickListener(v -> addGroupModelable());
        ImageButton btnDelete = view.findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(v -> autoDelete());
        return view;
    }

    @Override
    protected void onNewPosition() {
        lblContent.setText(getString(R.string.content_item, position));
    }

// ++++++++++++++++| PUBLIC OVERRIDE METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (args != null && lvwItems != null){
            lvwItems.restoreFromState(args);
        }
    }

    @Override
    public void onPause() {
        if (args != null && lvwItems != null){
            lvwItems.saveToState(args);
        }
        super.onPause();
    }

    @Override
    public ExpandableGroup<? extends Modelable> getExpandableGroup(int viewType, ViewGroup parent){
        if (viewType == GroupType.GROUP) {
            return new GroupDataItem(getContext(), R.layout.item_group, parent, this);
        }
        return null;
    }

    @Override
    public ExpandableChild<? extends Modelable> getExpandableChild(int viewType, ViewGroup parent){
        Context context = getContext();
        if (viewType == ChildType.DATA) {
            return new ChildDataItem(context, R.layout.item_child_data, parent, this);
        }
        return null;
    }

    @Override
    public ExpandableAdapter getExpandableAdapter(){
        return adapter;
    }

    @Override
    public ExpandableView getExpandableView() {
        return lvwItems;
    }

// *************************************************************************************************

    private void addGroupModelable(){
        Listable<Modelable> groupListable = page.getGroupListable();
        long idx = groupListable.getIdx();
        DataModel model = new DataModel(idx, false);
        if(adapter.appendGroupModelable(model, new Listable<>())){
            adapter.notifyDataSetChanged();
            groupListable.setIdx(++idx);
        }
    }
}
