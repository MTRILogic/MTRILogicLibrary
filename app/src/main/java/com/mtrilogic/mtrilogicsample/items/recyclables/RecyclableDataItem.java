package com.mtrilogic.mtrilogicsample.items.recyclables;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mtrilogic.abstracts.Modelable;
import com.mtrilogic.abstracts.Recyclable;

import com.mtrilogic.interfaces.RecyclableAdapterListener;
import com.mtrilogic.mtrilogicsample.R;
import com.mtrilogic.mtrilogicsample.models.DataModel;

@SuppressWarnings({"unused","FieldCanBeLocal"})
public class RecyclableDataItem extends Recyclable<DataModel> {
    private final TextView lblTitle, lblContent;
    private final CheckBox chkItem;
    private final RatingBar ratingBar;

// ++++++++++++++++| PUBLIC CONSTRUCTORS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public RecyclableDataItem(Context context, int resource, ViewGroup parent){
        this(context, resource, parent, (RecyclableAdapterListener)context);
    }

    public RecyclableDataItem(Context context, int resource, ViewGroup parent, RecyclableAdapterListener listener){
        super(context, resource, parent, listener);
        itemView.setOnClickListener(v -> {

        });
        ratingBar = itemView.findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if(adapter != null && fromUser){
                model.setRating(rating);
                adapter.notifyDataSetChanged();
                listener.onMakeToast("Rating Bar [" + position + "] set to " + rating );
            }
        });
        chkItem = itemView.findViewById(R.id.chk_item);
        chkItem.setOnClickListener(v -> updateChecked());
        lblTitle = itemView.findViewById(R.id.lbl_title);
        lblContent = itemView.findViewById(R.id.lbl_content);
        ImageButton btnDelete = itemView.findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(v -> autoDelete());
    }

// ++++++++++++++++| PROTECTED OVERRIDE METHODS |+++++++++++++++++++++++++++++++++++++++++++++++++++

    @Override
    protected DataModel getModel(Modelable modelable) {
        return (DataModel) modelable;
    }

    @Override
    protected void onBindHolder(){
        chkItem.setChecked(model.isChecked());
        ratingBar.setRating(model.getRating());
        lblTitle.setText(context.getString(R.string.title_item, model.getItemId()));
        lblContent.setText(context.getString(R.string.content_item, position));
    }

// ++++++++++++++++| PRIVATE METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private void updateChecked(){
        if (adapter != null){
            boolean checked = chkItem.isChecked();
            model.setChecked(checked);
            adapter.notifyDataSetChanged();
            listener.onMakeToast("Item [" + position + "] set to " + checked);
        }
    }
}
