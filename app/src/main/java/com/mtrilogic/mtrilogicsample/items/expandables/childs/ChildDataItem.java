package com.mtrilogic.mtrilogicsample.items.expandables.childs;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RatingBar;

import com.mtrilogic.abstracts.ExpandableChild;
import com.mtrilogic.abstracts.Modelable;
import com.mtrilogic.adapters.ExpandableAdapter;
import com.mtrilogic.interfaces.ExpandableItemListener;
import com.mtrilogic.mtrilogicsample.R;
import com.mtrilogic.mtrilogicsample.databinding.ItemChildDataBinding;
import com.mtrilogic.mtrilogicsample.models.DataModel;

@SuppressWarnings({"unused","FieldCanBeLocal"})
public class ChildDataItem extends ExpandableChild<DataModel> implements RatingBar.OnRatingBarChangeListener, CompoundButton.OnCheckedChangeListener {
    private final ItemChildDataBinding binding;

// ++++++++++++++++| PUBLIC CONSTRUCTORS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public ChildDataItem(Context context, int resource, ViewGroup parent, ExpandableItemListener listener){
        super(context, resource, parent, listener);
        binding = ItemChildDataBinding.bind(itemView);
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
        binding.lblContent.setText(context.getString(R.string.content_item, childPosition));
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        ExpandableAdapter adapter = listener.getExpandableAdapter();
        if (adapter != null) {
            model.setChecked(isChecked);
            adapter.notifyDataSetChanged();
            listener.onMakeToast("Item [" + groupPosition + "," + childPosition + "] set checked to " + isChecked);
        }
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        ExpandableAdapter adapter = listener.getExpandableAdapter();
        if(adapter != null && fromUser){
            model.setRating(rating);
            adapter.notifyDataSetChanged();
            listener.onMakeToast("Rating Bar[" + groupPosition + "][" + childPosition + "] set rating to " + rating );
        }
    }
}
