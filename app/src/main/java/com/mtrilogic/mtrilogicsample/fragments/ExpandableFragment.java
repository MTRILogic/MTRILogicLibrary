package com.mtrilogic.mtrilogicsample.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.mtrilogic.abstracts.ExpandableChild;
import com.mtrilogic.abstracts.ExpandableGroup;
import com.mtrilogic.abstracts.Fragmentable;
import com.mtrilogic.abstracts.Modelable;
import com.mtrilogic.adapters.ExpandableAdapter;
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
public class ExpandableFragment extends Fragmentable<ExpandablePage> implements CompoundButton.OnCheckedChangeListener, ExpandableListener, ExpandableItemListener {
    private FragmentExpandableBinding binding;
    private ExpandableAdapter adapter;

// PROTECTED OVERRIDE METHODS |*********************************************************************

    @NonNull
    @Override
    protected View onCreateViewFragment(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        adapter = new ExpandableAdapter(inflater, this, GroupType.COUNT, ChildType.COUNT);

        binding = FragmentExpandableBinding.inflate(inflater, container, false);
        binding.lvwItems.setAdapter(adapter);
        binding.chkItem.setOnCheckedChangeListener(this);
        binding.lblTitle.setText(getString(R.string.title_item, page.getItemId()));
        binding.btnAddGroup.setOnClickListener(v -> addGroupModelable());
        binding.btnDelete.setOnClickListener(v -> autoDelete());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onNewPosition();
    }

    @Override
    protected void onNewPosition() {
        binding.lblContent.setText(getString(R.string.content_item, position));
    }

    @Override
    protected void onNewPage() {
        // nothing for now
    }

    // ++++++++++++++++| PUBLIC OVERRIDE METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++

    @Override
    public void onResume() {
        super.onResume();
        Bundle args = getArguments();
        if (args != null){
            binding.lvwItems.restoreFromState(args);
        }
    }

    @Override
    public void onPause() {
        Bundle args = getArguments();
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
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // nothing for now
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

    @Override
    public boolean onChildLongClick(@NonNull Modelable modelable, int groupPosition, int childPosition, boolean lastChild) {
        return false; // nothing for now
    }

    @Override
    public boolean onGroupLongClick(@NonNull Modelable modelable, int groupPosition, boolean expanded) {
        return false; // nothing for now
    }

    @Override
    public void onChildClick(@NonNull Modelable modelable, int groupPosition, int childPosition, boolean lastChild) {
        // nothing for now
    }

    @Override
    public void onGroupClick(@NonNull Modelable modelable, int groupPosition, boolean expanded) {
        // nothing for now
    }

    @NonNull
    @Override
    public Mapable<Modelable> getModelableMapable() {
        // null pointer exception ?
        return page.getModelableMapable();
    }

    // *************************************************************************************************

    private void addGroupModelable(){
        DataModel model = new DataModel();
        if (page.getModelableMapable().appendGroup(model)){
            adapter.notifyDataSetChanged();
        }
    }
}
