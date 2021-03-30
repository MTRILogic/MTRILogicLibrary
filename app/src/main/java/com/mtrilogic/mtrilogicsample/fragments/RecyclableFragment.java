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
import android.widget.ImageButton;
import android.widget.TextView;

import com.mtrilogic.abstracts.Fragmentable;
import com.mtrilogic.abstracts.Modelable;
import com.mtrilogic.abstracts.Recyclable;
import com.mtrilogic.adapters.RecyclableAdapter;
import com.mtrilogic.interfaces.RecyclableAdapterListener;
import com.mtrilogic.interfaces.RecyclableListener;
import com.mtrilogic.mtrilogicsample.R;
import com.mtrilogic.mtrilogicsample.items.recyclables.RecyclableDataItem;
import com.mtrilogic.mtrilogicsample.models.DataModel;
import com.mtrilogic.mtrilogicsample.pages.RecyclablePage;
import com.mtrilogic.mtrilogicsample.types.ChildType;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class RecyclableFragment extends Fragmentable<RecyclablePage> implements RecyclableListener, RecyclableAdapterListener{
    private static final String TAG = "RecyclableFragmentTAG";
    private RecyclableAdapter adapter;
    private RecyclerView lvwItems;
    private TextView lblContent;

// PROTECTED OVERRIDE METHODS |*********************************************************************

    @Override
    protected View onCreateViewFragment(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                        @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclable,container,false);
        ArrayList<Modelable> modelables = page.getModelableList();
        adapter = new RecyclableAdapter(this, modelables);
        lvwItems = view.findViewById(R.id.lvw_items);
        lvwItems.setLayoutManager(new LinearLayoutManager(getContext()));
        lvwItems.setAdapter(adapter);
        TextView lblTitle = view.findViewById(R.id.lbl_title);
        lblTitle.setText(getString(R.string.title_item, page.getItemId()));
        lblContent = view.findViewById(R.id.lbl_content);
        ImageButton btnAddData = view.findViewById(R.id.btn_addData);
        btnAddData.setOnClickListener(v -> addModelable());
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
    public Recyclable<? extends Modelable> getRecyclable(int viewType, ViewGroup parent){
        Context context = getContext();
        if (viewType == ChildType.DATA) {
            return new RecyclableDataItem(context, R.layout.item_data, parent, this);
        }
        return null;
    }

    @Override
    public RecyclableAdapter getRecyclableAdapter(){
        return adapter;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return lvwItems;
    }



    // ++++++++++++++++| PRIVATE METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private void addModelable(){
        long idx = page.getIdx();
        Modelable modelable = new DataModel(idx, false);
        if (adapter.addModelable(modelable)){
            adapter.notifyDataSetChanged();
            page.setIdx(++idx);
        }
    }
}
