package com.mtrilogic.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mtrilogic.abstracts.Modelable;
import com.mtrilogic.abstracts.Recyclable;
import com.mtrilogic.classes.Listable;
import com.mtrilogic.interfaces.RecyclableListener;

@SuppressWarnings({"unused"})
public final class RecyclableAdapter extends RecyclerView.Adapter<Recyclable<? extends Modelable>>{
    private final RecyclableListener listener;
    private final LayoutInflater inflater;

// ++++++++++++++++| PUBLIC CONSTRUCTORS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public RecyclableAdapter(@NonNull LayoutInflater inflater, @NonNull RecyclableListener listener){
        this.inflater = inflater;
        this.listener = listener;
        setHasStableIds(true);
    }

// ++++++++++++++++| PUBLIC OVERRIDE METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++

    @NonNull
    @Override
    public final Recyclable<? extends Modelable> onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        Recyclable<? extends Modelable> recyclable = listener.getRecyclable(viewType, inflater, parent);
        recyclable.onBindItemView();
        return recyclable;
    }

    @Override
    public final void onBindViewHolder(@NonNull Recyclable holder, int position){
        Modelable modelable = getModelable(position);
        holder.bindModel(modelable, position);
    }

    @Override
    public final int getItemCount(){
        return getModelableListable().getItemCount();
    }

    @Override
    public final int getItemViewType(int position){
        return getModelable(position).getViewType();
    }

    @Override
    public final long getItemId(int position){
        return getModelable(position).getItemId();
    }

// ++++++++++++++++| PRIVATE METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private Listable<Modelable> getModelableListable(){
        return listener.getModelableListable();
    }

    private Modelable getModelable(int position){
        return getModelableListable().getItem(position);
    }
}
