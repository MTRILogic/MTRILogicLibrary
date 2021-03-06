package com.mtrilogic.mtrilogicsample.items.recyclables;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.mtrilogic.abstracts.Modelable;
import com.mtrilogic.abstracts.Recyclable;

import com.mtrilogic.interfaces.RecyclableItemListener;
import com.mtrilogic.mtrilogicsample.R;
import com.mtrilogic.mtrilogicsample.databinding.ItemDataBinding;
import com.mtrilogic.mtrilogicsample.models.DataModel;

@SuppressWarnings({"unused","FieldCanBeLocal"})
public class RecyclableDataItem extends Recyclable<DataModel> {
    private ItemDataBinding binding;

// ++++++++++++++++| PUBLIC CONSTRUCTORS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public RecyclableDataItem(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent, @NonNull RecyclableItemListener listener){
        super(ItemDataBinding.inflate(inflater, parent, false).getRoot(), listener);
    }

// ++++++++++++++++| PROTECTED OVERRIDE METHODS |+++++++++++++++++++++++++++++++++++++++++++++++++++

    @Override
    public void onBindItemView() {
        binding = ItemDataBinding.bind(itemView);
        binding.ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if(fromUser){
                model.setRating(rating);
                listener.getRecyclableAdapter().notifyItemChanged(position);
                //listener.onMakeToast("Rating Bar [" + position + "] set rating to " + rating );
            }
        });
        binding.chkItem.setOnCheckedChangeListener((buttonView, isChecked) -> {
            model.setChecked(isChecked);
            listener.getRecyclableAdapter().notifyItemChanged(position);
            //listener.onMakeToast("Item [" + position + "] set checked to " + isChecked);
        });
        binding.btnDelete.setOnClickListener(v -> autoDelete());
    }

    @NonNull
    @Override
    public DataModel getModelFromModelable(@NonNull Modelable modelable) {
        return (DataModel) modelable;
    }

    @Override
    public void onBindModel(){
        Context context = itemView.getContext();
        binding.lblTitle.setText(context.getString(R.string.title_item, model.getItemId()));
        binding.lblContent.setText(context.getString(R.string.content_item, position));
        listener.getRecyclerView().post(() -> {
            binding.ratingBar.setRating(model.getRating());
            binding.chkItem.setChecked(model.isChecked());
        });
    }
}
