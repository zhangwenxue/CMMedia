package com.cm.media.ui.adapter;

import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cm.media.R;
import com.cm.media.entity.category.CategoryData;
import com.cm.media.entity.category.CategoryItem;
import com.cm.media.util.CollectionUtils;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

public class FilterRecyclerAdapter extends BaseQuickAdapter<CategoryData, BaseViewHolder> {
    private final SparseIntArray filterMap = new SparseIntArray();

    public interface OnFilterChangedListener {
        void onChange(String filters);
    }

    private OnFilterChangedListener mListener;

    public void setOnFilterChangeListener(OnFilterChangedListener listener) {
        this.mListener = listener;
    }

    public FilterRecyclerAdapter(@Nullable List<CategoryData> data) {
        super(R.layout.recycler_item_category, data);
        filterMap.clear();
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryData item) {
        if (item == null) {
            return;
        }
        if (!CollectionUtils.isEmptyList(item.getValues())) {
            ChipGroup group = helper.getView(R.id.chipGroup);
            for (CategoryItem categoryItem : item.getValues()) {
                Chip chip = (Chip) LayoutInflater.from(group.getContext()).inflate(R.layout.chip_filter, group, false);
                chip.setText(categoryItem.getName());
                chip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                chip.setId(categoryItem.getId());
                ChipGroup.LayoutParams params = new ChipGroup.LayoutParams(ChipGroup.LayoutParams.WRAP_CONTENT, ChipGroup.LayoutParams.WRAP_CONTENT);
                group.addView(chip, params);
            }
            group.setChipSpacingHorizontal(10);
            group.setOnCheckedChangeListener((chipGroup, id) -> {
                if (id == View.NO_ID) {
                    filterMap.removeAt(filterMap.indexOfKey(item.getId()));
                } else {
                    filterMap.append(item.getId(), id);
                }
                notifyFilterChanged();
            });
        }
    }

    private void notifyFilterChanged() {
        if (mListener != null) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < filterMap.size(); i++) {
                int value = filterMap.valueAt(i);
                if (value > 0) {
                    if (builder.length() > 0) {
                        builder.append(",");
                    }
                    builder.append(value);
                }
            }
            mListener.onChange(builder.toString());
        }
    }
}
