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
import com.mtrilogic.classes.Listable;
import com.mtrilogic.interfaces.InflatableItemListener;
import com.mtrilogic.interfaces.InflatableListener;
import com.mtrilogic.mtrilogicsample.R;
import com.mtrilogic.mtrilogicsample.databinding.FragmentInflatableBinding;
import com.mtrilogic.mtrilogicsample.items.inflatables.InflatableDataItem;
import com.mtrilogic.mtrilogicsample.models.DataModel;
import com.mtrilogic.mtrilogicsample.pages.InflatablePage;
import com.mtrilogic.mtrilogicsample.types.ChildType;
import com.mtrilogic.views.InflatableView;

@SuppressWarnings("unused")
public class InflatableFragment extends Fragmentable<InflatablePage> implements InflatableListener, InflatableItemListener {
    private FragmentInflatableBinding binding;
    private InflatableAdapter adapter;

// PROTECTED OVERRIDE METHODS |*********************************************************************

    @NonNull
    @Override
    protected View onCreateViewFragment(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        adapter = new InflatableAdapter(inflater, this, ChildType.COUNT);

        binding = FragmentInflatableBinding.inflate(inflater, container, false);
        binding.lvwItems.setAdapter(adapter);
        binding.lblTitle.setText(getString(R.string.title_item, page.getItemId()));
        binding.btnAddData.setOnClickListener(v -> addModelable());
        binding.btnDelete.setOnClickListener(v -> autoDelete());
        binding.chkItem.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isPressed()){
                for (Modelable modelable : page.getModelableListable().getList()){
                    DataModel model = (DataModel) modelable;
                    model.setChecked(binding.chkItem.isChecked());
                }
                adapter.notifyDataSetChanged();
            }
        });
        return binding.getRoot();
    }

    @Override
    protected void onNewPosition() {
        binding.lblContent.setText(getString(R.string.content_item, position));
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
            return new InflatableDataItem(inflater, parent, this);
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

    @NonNull
    @Override
    public Listable<Modelable> getModelableListable() {
        // null pointer exception ?
        return page.getModelableListable();
    }

    @Override
    public boolean onItemLongClick(@NonNull Modelable modelable, int position) {
        return false; // nothing for now
    }

    @Override
    public void onItemClick(@NonNull Modelable modelable, int position) {
        // nothing for now
    }

    // ++++++++++++++++| PRIVATE METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private void addModelable(){
        DataModel model = new DataModel();
        model.setChecked(binding.chkItem.isChecked());
        if (page.getModelableListable().appendItem(model)){
            adapter.notifyDataSetChanged();
        }
    }
}
