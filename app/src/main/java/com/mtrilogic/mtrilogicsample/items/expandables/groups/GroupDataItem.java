package com.mtrilogic.mtrilogicsample.items.expandables.groups;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mtrilogic.abstracts.ExpandableGroup;
import com.mtrilogic.abstracts.Modelable;
import com.mtrilogic.interfaces.ExpandableAdapterListener;
import com.mtrilogic.mtrilogicsample.R;
import com.mtrilogic.mtrilogicsample.models.DataModel;

import java.util.ArrayList;

@SuppressWarnings({"unused","FieldCanBeLocal"})
public class GroupDataItem extends ExpandableGroup<DataModel> implements CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "GroupDataItemTAG";
    private final TextView lblTitle, lblContent;
    private final CheckBox chkItem;

// ++++++++++++++++| PUBLIC CONSTRUCTORS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public GroupDataItem(Context context, int resource, ViewGroup parent){
        this(context, resource, parent,(ExpandableAdapterListener)context);
    }

    public GroupDataItem(Context context, int resource, ViewGroup parent, ExpandableAdapterListener listener){
        super(context, resource, parent, listener);
        chkItem = itemView.findViewById(R.id.chk_item);
        chkItem.setOnCheckedChangeListener(this);
        chkItem.setFocusable(false);
        lblTitle = itemView.findViewById(R.id.lbl_title);
        lblContent = itemView.findViewById(R.id.lbl_content);
        ImageButton btnAddData = itemView.findViewById(R.id.btn_addData);
        btnAddData.setOnClickListener(v -> addNewModelable());
        btnAddData.setFocusable(false);
        ImageButton btnDelete = itemView.findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(v -> autoDelete());
        btnDelete.setFocusable(false);
    }

// ++++++++++++++++| PUBLIC OVERRIDE METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (childListable != null) {
            model.setChecked(isChecked);
            ArrayList<Modelable> childModelableList = childListable.getModelableList();
            for (Modelable childModelable : childModelableList) {
                DataModel model = (DataModel) childModelable;
                model.setChecked(isChecked);
            }
            adapter.notifyDataSetChanged();
        }
    }

    // ++++++++++++++++| PROTECTED OVERRIDE METHODS |+++++++++++++++++++++++++++++++++++++++++++++++++++

    @Override
    protected DataModel getModel(Modelable modelable) {
        return (DataModel) modelable;
    }

    @Override
    protected void onBindHolder(){
        chkItem.setChecked(model.isChecked());
        lblTitle.setText(context.getString(R.string.title_item, model.getItemId()));
        lblContent.setText(context.getString(R.string.content_item, groupPosition));
    }

// ++++++++++++++++| PRIVATE METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private void addNewModelable(){
        if (childListable != null){
            long idx = childListable.getIdx();
            listener.onMakeToast("Este dato es " + this.model.isChecked());
            DataModel model = new DataModel(idx, this.model.isChecked());
            addNewChildModelable(model, idx);
        }else {
            listener.onMakeToast("Es nullo este lugar");
        }
    }
}
