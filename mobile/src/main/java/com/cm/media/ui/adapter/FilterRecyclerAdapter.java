package com.cm.media.ui.adapter;

import android.os.Handler;
import android.os.Looper;
import android.util.SparseIntArray;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cm.media.R;
import com.cm.media.entity.category.CategoryData;
import com.cm.media.entity.category.CategoryItem;
import com.cm.media.util.CollectionUtils;

import java.util.List;

public class FilterRecyclerAdapter extends BaseQuickAdapter<CategoryData, BaseViewHolder> {
    private final SparseIntArray filterMap = new SparseIntArray();
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

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
        RecyclerView recyclerView = helper.getView(R.id.categoryRecyclerView);
        TextView header = new TextView(recyclerView.getContext());
        header.setText(item.getName() + ":");
        if (!CollectionUtils.isEmptyList(item.getValues())) {
            FilterItemAdapter adapter = new FilterItemAdapter(item.getValues());
            //adapter.addHeaderView(header);
            recyclerView.setAdapter(adapter);
            int key = item.getId();
            adapter.setListener(value -> {
                if (value < 0) {
                    filterMap.removeAt(filterMap.indexOfKey(key));
                } else {
                    filterMap.append(key, value);
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

    private class FilterItemAdapter extends BaseQuickAdapter<CategoryItem, BaseViewHolder> {
        private OnValueChangeListener mListener;
        private int mSelectedId = 0;

        void setListener(OnValueChangeListener mListener) {
            this.mListener = mListener;
        }

        FilterItemAdapter(@Nullable List<CategoryItem> list) {
            super(R.layout.recycler_item_category_item, list);
        }

        @Override
        protected void convert(BaseViewHolder helper, CategoryItem item) {
            if (item == null) {
                return;
            }
            CheckBox checkBox = helper.getView(R.id.checkItem);
            checkBox.setText(item.getName());
            checkBox.setOnCheckedChangeListener(null);
            if (checkBox.isChecked() && mSelectedId != item.getId()) {
                checkBox.setChecked(false);
            }
            if (!checkBox.isChecked() && mSelectedId == item.getId()) {
                checkBox.setChecked(true);
            }
            checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
                if (b) {
                    if (mSelectedId != item.getId()) {
                        mSelectedId = item.getId();
                    }
                } else {
                    mSelectedId = -1;
                }
                if (mListener != null) {
                    mListener.onValueChange(mSelectedId);
                }
                HANDLER.post(this::notifyDataSetChanged);
            });

        }
    }

    interface OnValueChangeListener {
        void onValueChange(int value);
    }
}
