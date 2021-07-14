package com.mtrilogic.mtrilogicsample.items.expandables.groups;

import android.content.Context;

import androidx.annotation.NonNull;

import com.mtrilogic.abstracts.ExpandableGroup;
import com.mtrilogic.abstracts.Modelable;
import com.mtrilogic.interfaces.ExpandableItemListener;
import com.mtrilogic.mtrilogicsample.R;
import com.mtrilogic.mtrilogicsample.databinding.ItemGroupBinding;
import com.mtrilogic.mtrilogicsample.models.DataModel;

import java.util.ArrayList;

@SuppressWarnings({"unused","FieldCanBeLocal"})
public class GroupDataItem extends ExpandableGroup<DataModel> {
    private ItemGroupBinding binding;

// ++++++++++++++++| PUBLIC CONSTRUCTORS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public GroupDataItem(@NonNull ItemGroupBinding binding, @NonNull ExpandableItemListener listener){
        super(binding.getRoot(), listener);
    }

// ++++++++++++++++| PUBLIC OVERRIDE METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++

    @Override
    public void onBindItemView() {
        binding = ItemGroupBinding.bind(itemView);
        binding.chkItem.setOnCheckedChangeListener((buttonView, isChecked) -> {
            model.setChecked(isChecked);
            ArrayList<Modelable> childList = listener.getModelableMapable().getChildList(groupPosition);
            if (childList != null) {
                for (Modelable modelable : childList){
                    DataModel model = (DataModel) modelable;
                    model.setChecked(isChecked);
                }
            }
            listener.getExpandableAdapter().notifyDataSetChanged();
        });

        binding.btnAddData.setOnClickListener(v -> addNewModelable());
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
        binding.lblContent.setText(context.getString(R.string.content_item, groupPosition));
        binding.chkItem.setChecked(model.isChecked());
    }

// ++++++++++++++++| PRIVATE METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private void addNewModelable(){
        DataModel model = new DataModel();
        model.setChecked(this.model.isChecked());
        addChild(model);
    }
}
