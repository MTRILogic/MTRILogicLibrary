package com.mtrilogic.mtrilogicsample.items.inflatables;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.mtrilogic.abstracts.Inflatable;
import com.mtrilogic.abstracts.Modelable;
import com.mtrilogic.interfaces.InflatableItemListener;
import com.mtrilogic.mtrilogicsample.R;
import com.mtrilogic.mtrilogicsample.databinding.ItemDataBinding;
import com.mtrilogic.mtrilogicsample.models.DataModel;

@SuppressWarnings({"unused"})
public class InflatableDataItem extends Inflatable<DataModel> {
    private ItemDataBinding binding;

// ++++++++++++++++| PUBLIC CONSTRUCTORS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public InflatableDataItem(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent, @NonNull InflatableItemListener listener){
        super(ItemDataBinding.inflate(inflater, parent, false).getRoot(), listener);
    }

// ++++++++++++++++| PUBLIC OVERRIDE METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++

    @Override
    public void onBindItemView() {
        binding = ItemDataBinding.bind(itemView);
        binding.ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if(fromUser){
                model.setRating(rating);
                listener.getInflatableAdapter().notifyDataSetChanged();
                //listener.onMakeToast("Rating Bar [" + position + "] set rating to " + rating );
            }
        });
        binding.chkItem.setOnCheckedChangeListener((buttonView, isChecked) -> {
            model.setChecked(isChecked);
            listener.getInflatableAdapter().notifyDataSetChanged();
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
        binding.chkItem.setChecked(model.isChecked());
        binding.ratingBar.setRating(model.getRating());
        binding.lblTitle.setText(context.getString(R.string.title_item, model.getItemId()));
        binding.lblContent.setText(context.getString(R.string.content_item, position));
    }
}
