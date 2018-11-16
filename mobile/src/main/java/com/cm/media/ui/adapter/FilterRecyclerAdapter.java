package com.cm.media.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseIntArray;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cm.media.R;
import com.cm.media.entity.category.CategoryData;
import com.cm.media.entity.category.CategoryItem;
import com.cm.media.entity.vod.topic.TopicVod;
import com.cm.media.util.CollectionUtils;

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
        RecyclerView recyclerView = helper.getView(R.id.recyclerView);
        if (!CollectionUtils.isEmptyList(item.getValues())) {
            FilterItemAdapter adapter = new FilterItemAdapter(item.getValues());
            int key = item.getId();
            adapter.setListener(value -> {
                filterMap.append(key, value);
                notifyFilterChanged();
            });
            recyclerView.setAdapter(adapter);
        }
        if (!TextUtils.isEmpty(item.getName())) {
            helper.setText(R.id.topicName, item.getName());
        }
    }

    private void notifyFilterChanged() {
        if (mListener != null) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < filterMap.size(); i++) {
                int value = filterMap.valueAt(i);
                if (value > 0) {
                    if (builder.length() > 0) {
                        builder.append(value);
                    }
                }
            }
            if (builder.length() > 0) {
                mListener.onChange(builder.toString());
            }
        }
    }

    private class FilterItemAdapter extends BaseQuickAdapter<CategoryItem, BaseViewHolder> {
        private OnValueChangeListener mListener;
        private int mSelectedId = 0;

        void setListener(OnValueChangeListener mListener) {
            this.mListener = mListener;
        }

        FilterItemAdapter(@Nullable List<CategoryItem> list) {
            super(R.layout.recycler_item_topic_data, list);
        }

        @Override
        protected void convert(BaseViewHolder helper, CategoryItem item) {
            if (item == null) {
                return;
            }
            if (!TextUtils.isEmpty(item.getName())) {
                helper.setText(R.id.topicVodName, item.getName());
            }
            helper.setChecked(R.id.checkItem, mSelectedId == item.getId());
            helper.setOnCheckedChangeListener(R.id.checkItem, (compoundButton, b) -> {
                mSelectedId = item.getId();
                notifyDataSetChanged();
                if (mListener != null) {
                    mListener.onValueChange(item.getId());
                }
            });
        }
    }

    interface OnValueChangeListener {
        void onValueChange(int value);
    }
}
