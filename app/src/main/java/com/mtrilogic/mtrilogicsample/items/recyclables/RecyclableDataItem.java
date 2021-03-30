package com.mtrilogic.mtrilogicsample.items.recyclables;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RatingBar;

import com.mtrilogic.abstracts.Modelable;
import com.mtrilogic.abstracts.Recyclable;

import com.mtrilogic.adapters.RecyclableAdapter;
import com.mtrilogic.interfaces.RecyclableItemListener;
import com.mtrilogic.mtrilogicsample.R;
import com.mtrilogic.mtrilogicsample.databinding.ItemDataBinding;
import com.mtrilogic.mtrilogicsample.models.DataModel;

@SuppressWarnings({"unused","FieldCanBeLocal"})
public class RecyclableDataItem extends Recyclable<DataModel> implements RatingBar.OnRatingBarChangeListener, CompoundButton.OnCheckedChangeListener {
    private final ItemDataBinding binding;

// ++++++++++++++++| PUBLIC CONSTRUCTORS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public RecyclableDataItem(Context context, int resource, ViewGroup parent, RecyclableItemListener listener){
        super(context, resource, parent, listener);
        binding = ItemDataBinding.bind(itemView);
        binding.ratingBar.setOnRatingBarChangeListener(this);
        binding.chkItem.setOnCheckedChangeListener(this);
        binding.btnDelete.setOnClickListener(v -> autoDelete());
    }

// ++++++++++++++++| PROTECTED OVERRIDE METHODS |+++++++++++++++++++++++++++++++++++++++++++++++++++

    @Override
    protected DataModel getModel(Modelable modelable) {
        return (DataModel) modelable;
    }

    @Override
    protected void onBindHolder(){
        Context context = itemView.getContext();
        binding.chkItem.setChecked(model.isChecked());
        binding.ratingBar.setRating(model.getRating());
        binding.lblTitle.setText(context.getString(R.string.title_item, model.getItemId()));
        binding.lblContent.setText(context.getString(R.string.content_item, position));
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        RecyclableAdapter adapter = listener.getRecyclableAdapter();
        if (adapter != null){
            model.setChecked(isChecked);
            adapter.notifyDataSetChanged();
            listener.onMakeToast("Item [" + position + "] set checked to " + isChecked);
        }
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        RecyclableAdapter adapter = listener.getRecyclableAdapter();
        if(adapter != null && fromUser){
            model.setRating(rating);
            adapter.notifyDataSetChanged();
            listener.onMakeToast("Rating Bar [" + position + "] set rating to " + rating );
        }
    }
}
