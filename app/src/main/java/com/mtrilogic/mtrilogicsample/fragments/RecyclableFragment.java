package com.mtrilogic.mtrilogicsample.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mtrilogic.abstracts.Fragmentable;
import com.mtrilogic.abstracts.Modelable;
import com.mtrilogic.abstracts.Recyclable;
import com.mtrilogic.adapters.RecyclableAdapter;
import com.mtrilogic.classes.Listable;
import com.mtrilogic.interfaces.RecyclableItemListener;
import com.mtrilogic.interfaces.RecyclableListener;
import com.mtrilogic.mtrilogicsample.R;
import com.mtrilogic.mtrilogicsample.databinding.FragmentRecyclableBinding;
import com.mtrilogic.mtrilogicsample.databinding.ItemDataBinding;
import com.mtrilogic.mtrilogicsample.items.recyclables.RecyclableDataItem;
import com.mtrilogic.mtrilogicsample.models.DataModel;
import com.mtrilogic.mtrilogicsample.pages.RecyclablePage;
import com.mtrilogic.mtrilogicsample.types.ChildType;

@SuppressWarnings("unused")
public class RecyclableFragment extends Fragmentable<RecyclablePage> implements RecyclableListener, RecyclableItemListener {
    private FragmentRecyclableBinding binding;
    private RecyclableAdapter adapter;

// PROTECTED OVERRIDE METHODS |*********************************************************************

    @NonNull
    @Override
    protected View onCreateViewFragment(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        adapter = new RecyclableAdapter(inflater, this);

        binding = FragmentRecyclableBinding.inflate(inflater, container, false);
        binding.lvwItems.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.lvwItems.setAdapter(adapter);
        binding.lblTitle.setText(getString(R.string.title_item, page.getItemId()));
        binding.btnAddData.setOnClickListener(v -> addModelable());
        binding.btnDelete.setOnClickListener(v -> autoDelete());
        binding.chkItem.setOnCheckedChangeListener((buttonView, isChecked) -> {
            for (Modelable modelable : page.getModelableListable().getList()){
                DataModel model = (DataModel) modelable;
                model.setChecked(binding.chkItem.isChecked());
            }
            adapter.notifyDataSetChanged();
        });
        return binding.getRoot();
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
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public Recyclable<? extends Modelable> getRecyclable(int viewType, @NonNull LayoutInflater inflater, @NonNull ViewGroup parent){
        Context context = getContext();
        if (viewType == ChildType.DATA) {
            return new RecyclableDataItem(ItemDataBinding.inflate(inflater, parent, false), this);
        }
        return null;
    }

    @NonNull
    @Override
    public RecyclableAdapter getRecyclableAdapter(){
        return adapter;
    }

    @NonNull
    @Override
    public RecyclerView getRecyclerView() {
        return binding.lvwItems;
    }

    @Override
    public boolean onItemLongClick(@NonNull Modelable modelable, int position) {
        return false; // nothing for now
    }

    @Override
    public void onItemClick(@NonNull Modelable modelable, int position) {
        // nothing for now
    }

    @NonNull
    @Override
    public Listable<Modelable> getModelableListable() {
        // null pointer exception ?
        return page.getModelableListable();
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
