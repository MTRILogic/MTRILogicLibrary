package com.mtrilogic.mtrilogicsample.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mtrilogic.abstracts.ExpandableChild;
import com.mtrilogic.abstracts.ExpandableGroup;
import com.mtrilogic.abstracts.Fragmentable;
import com.mtrilogic.abstracts.Modelable;
import com.mtrilogic.adapters.ExpandableAdapter;
import com.mtrilogic.classes.Listable;
import com.mtrilogic.classes.Mapable;
import com.mtrilogic.interfaces.ExpandableItemListener;
import com.mtrilogic.interfaces.ExpandableListener;
import com.mtrilogic.mtrilogicsample.databinding.FragmentExpandableBinding;
import com.mtrilogic.mtrilogicsample.databinding.ItemChildDataBinding;
import com.mtrilogic.mtrilogicsample.databinding.ItemGroupBinding;
import com.mtrilogic.mtrilogicsample.items.expandables.childs.ChildDataItem;
import com.mtrilogic.mtrilogicsample.items.expandables.groups.GroupDataItem;
import com.mtrilogic.mtrilogicsample.models.DataModel;
import com.mtrilogic.mtrilogicsample.pages.ExpandablePage;
import com.mtrilogic.mtrilogicsample.R;
import com.mtrilogic.mtrilogicsample.types.GroupType;
import com.mtrilogic.mtrilogicsample.types.ChildType;
import com.mtrilogic.views.ExpandableView;

@SuppressWarnings("unused")
public class ExpandableFragment extends Fragmentable<ExpandablePage> implements ExpandableListener, ExpandableItemListener {
    private FragmentExpandableBinding binding;
    private ExpandableAdapter adapter;

// PROTECTED OVERRIDE METHODS |*********************************************************************

    @Override
    protected View onCreateViewFragment(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentExpandableBinding.inflate(inflater, container, false);

        Listable<Modelable> groupListable = page.getGroupListable();
        Mapable<Modelable> childMapable = page.getChildMapable();
        adapter = new ExpandableAdapter(inflater, this, groupListable, childMapable, GroupType.COUNT, ChildType.COUNT);
        binding.lvwItems.setAdapter(adapter);

        binding.lblTitle.setText(getString(R.string.title_item, page.getItemId()));
        binding.btnAddGroup.setOnClickListener(v -> addGroupModelable());
        binding.btnDelete.setOnClickListener(v -> autoDelete());
        return binding.getRoot();
    }

    @Override
    protected void onNewPosition() {
        binding.lblContent.setText(getString(R.string.content_item, position));
    }

// ++++++++++++++++| PUBLIC OVERRIDE METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (args != null){
            binding.lvwItems.restoreFromState(args);
        }
    }

    @Override
    public void onPause() {
        if (args != null){
            binding.lvwItems.saveToState(args);
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public ExpandableGroup<? extends Modelable> getExpandableGroup(int viewType, @NonNull LayoutInflater inflater, @NonNull ViewGroup parent){
        if (viewType == GroupType.GROUP) {
            return new GroupDataItem(ItemGroupBinding.inflate(inflater, parent, false), this);
        }
        return null;
    }

    @Override
    public ExpandableChild<? extends Modelable> getExpandableChild(int viewType, @NonNull LayoutInflater inflater, @NonNull ViewGroup parent){
        Context context = getContext();
        if (viewType == ChildType.DATA) {
            return new ChildDataItem(ItemChildDataBinding.inflate(inflater, parent, false), this);
        }
        return null;
    }

    @NonNull
    @Override
    public ExpandableAdapter getExpandableAdapter(){
        return adapter;
    }

    @NonNull
    @Override
    public ExpandableView getExpandableView() {
        return binding.lvwItems;
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
