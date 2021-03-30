package com.mtrilogic.mtrilogicsample.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mtrilogic.abstracts.Fragmentable;
import com.mtrilogic.abstracts.Inflatable;
import com.mtrilogic.abstracts.Modelable;
import com.mtrilogic.adapters.InflatableAdapter;
import com.mtrilogic.interfaces.InflatableItemListener;
import com.mtrilogic.interfaces.InflatableListener;
import com.mtrilogic.mtrilogicsample.R;
import com.mtrilogic.mtrilogicsample.databinding.FragmentInflatableBinding;
import com.mtrilogic.mtrilogicsample.databinding.ItemDataBinding;
import com.mtrilogic.mtrilogicsample.items.inflatables.InflatableDataItem;
import com.mtrilogic.mtrilogicsample.models.DataModel;
import com.mtrilogic.mtrilogicsample.pages.InflatablePage;
import com.mtrilogic.mtrilogicsample.types.ChildType;
import com.mtrilogic.views.InflatableView;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class InflatableFragment extends Fragmentable<InflatablePage> implements InflatableListener, InflatableItemListener {
    private FragmentInflatableBinding binding;
    private InflatableAdapter adapter;

// PROTECTED OVERRIDE METHODS |*********************************************************************

    @Override
    protected View onCreateViewFragment(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentInflatableBinding.inflate(inflater, container, false);

        ArrayList<Modelable> modelableList = page.getModelableList();
        adapter = new InflatableAdapter(inflater, this, modelableList, ChildType.COUNT);
        binding.lvwItems.setAdapter(adapter);

        binding.lblTitle.setText(getString(R.string.title_item, page.getItemId()));
        binding.btnAddData.setOnClickListener(v -> addModelable());
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
        if (args != null) {
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
    public Inflatable<? extends Modelable> getInflatable(int viewType, @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        Context context = getContext();
        if (viewType == ChildType.DATA) {
            return new InflatableDataItem(ItemDataBinding.inflate(inflater, parent, false), this);
        }
        return null;
    }

    @NonNull
    @Override
    public InflatableAdapter getInflatableAdapter(){
        return adapter;
    }

    @NonNull
    @Override
    public InflatableView getInflatableView() {
        return binding.lvwItems;
    }

// ++++++++++++++++| PRIVATE METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private void addModelable(){
        long idx = page.getIdx();
        DataModel model = new DataModel(idx, false);
        if (adapter.addModelable(model)){
            adapter.notifyDataSetChanged();
            page.setIdx(++idx);
        }
    }
}
