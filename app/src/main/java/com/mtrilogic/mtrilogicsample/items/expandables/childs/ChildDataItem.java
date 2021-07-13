package com.mtrilogic.mtrilogicsample.items.expandables.childs;

import android.content.Context;
import android.widget.CompoundButton;
import android.widget.RatingBar;

import androidx.annotation.NonNull;

import com.mtrilogic.abstracts.ExpandableChild;
import com.mtrilogic.abstracts.Modelable;
import com.mtrilogic.interfaces.ExpandableItemListener;
import com.mtrilogic.mtrilogicsample.R;
import com.mtrilogic.mtrilogicsample.databinding.ItemChildDataBinding;
import com.mtrilogic.mtrilogicsample.models.DataModel;

@SuppressWarnings({"unused","FieldCanBeLocal"})
public class ChildDataItem extends ExpandableChild<DataModel> implements RatingBar.OnRatingBarChangeListener, CompoundButton.OnCheckedChangeListener {
    private ItemChildDataBinding binding;

// ++++++++++++++++| PUBLIC CONSTRUCTORS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public ChildDataItem(@NonNull ItemChildDataBinding binding, @NonNull ExpandableItemListener listener){
        super(binding.getRoot(), listener);
    }

// ++++++++++++++++| PROTECTED OVERRIDE METHODS |+++++++++++++++++++++++++++++++++++++++++++++++++++

    @Override
    public void onBindItemView() {
        binding = ItemChildDataBinding.bind(itemView);
        binding.ratingBar.setOnRatingBarChangeListener(this);
        binding.chkItem.setOnCheckedChangeListener(this);
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
        binding.lblContent.setText(context.getString(R.string.content_item, childPosition));
        binding.ratingBar.setRating(model.getRating());
        binding.chkItem.setChecked(model.isChecked());
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        model.setChecked(isChecked);
        listener.getExpandableAdapter().notifyDataSetChanged();
        //listener.onMakeToast("Item [" + groupPosition + "," + childPosition + "] set checked to " + isChecked);
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        if(fromUser){
            model.setRating(rating);
            listener.getExpandableAdapter().notifyDataSetChanged();
            //listener.onMakeToast("Rating Bar[" + groupPosition + "][" + childPosition + "] set rating to " + rating );
        }
    }
}
