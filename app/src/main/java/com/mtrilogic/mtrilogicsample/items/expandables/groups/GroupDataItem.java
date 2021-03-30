package com.mtrilogic.mtrilogicsample.items.expandables.groups;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.mtrilogic.abstracts.ExpandableGroup;
import com.mtrilogic.abstracts.Modelable;
import com.mtrilogic.interfaces.ExpandableItemListener;
import com.mtrilogic.mtrilogicsample.R;
import com.mtrilogic.mtrilogicsample.databinding.ItemGroupBinding;
import com.mtrilogic.mtrilogicsample.models.DataModel;

import java.util.ArrayList;

@SuppressWarnings({"unused","FieldCanBeLocal"})
public class GroupDataItem extends ExpandableGroup<DataModel> implements CompoundButton.OnCheckedChangeListener {
    private final ItemGroupBinding binding;

// ++++++++++++++++| PUBLIC CONSTRUCTORS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public GroupDataItem(Context context, int resource, ViewGroup parent, ExpandableItemListener listener){
        super(context, resource, parent, listener);
        binding = ItemGroupBinding.bind(itemView);
        binding.chkItem.setOnCheckedChangeListener(this);
        binding.chkItem.setFocusable(false);
        binding.btnAddData.setOnClickListener(v -> addNewModelable());
        binding.btnAddData.setFocusable(false);
        binding.btnDelete.setOnClickListener(v -> autoDelete());
        binding.btnDelete.setFocusable(false);
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
            listener.getExpandableAdapter().notifyDataSetChanged();
        }
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
        binding.lblTitle.setText(context.getString(R.string.title_item, model.getItemId()));
        binding.lblContent.setText(context.getString(R.string.content_item, groupPosition));
    }

// ++++++++++++++++| PRIVATE METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private void addNewModelable(){
        if (childListable != null){
            long idx = childListable.getIdx();
            DataModel model = new DataModel(idx, this.model.isChecked());
            addNewChildModelable(model, idx);
        }else {
            listener.onMakeToast("Es nullo este lugar");
        }
    }
}
