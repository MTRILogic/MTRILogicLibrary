package com.mtrilogic.mtrilogicsample.items.inflatables;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mtrilogic.abstracts.Inflatable;
import com.mtrilogic.abstracts.Modelable;
import com.mtrilogic.interfaces.InflatableAdapterListener;
import com.mtrilogic.mtrilogicsample.R;
import com.mtrilogic.mtrilogicsample.models.DataModel;

@SuppressWarnings({"unused"})
public class InflatableDataItem extends Inflatable<DataModel> {
    private static final String TAG = "InflatableDataItemTAG";
    private final TextView lblTitle, lblContent;
    private final CheckBox chkItem;
    private final RatingBar ratingBar;

// ++++++++++++++++| PUBLIC CONSTRUCTORS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public InflatableDataItem(Context context, int resource, ViewGroup parent){
        this(context, resource, parent,(InflatableAdapterListener)context);
    }

    public InflatableDataItem(Context context, int resource, ViewGroup parent, InflatableAdapterListener listener){
        super(context, resource, parent, listener);
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
