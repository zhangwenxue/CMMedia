package com.cm.media.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cm.media.R;
import com.cm.media.databinding.HeaderVodFiltersBinding;
import com.cm.media.databinding.VodListFragmentBinding;
import com.cm.media.entity.category.Category;
import com.cm.media.entity.vod.topic.TopicVod;
import com.cm.media.ui.adapter.FilterRecyclerAdapter;
import com.cm.media.viewmodel.VodListViewModel;

import java.util.List;
import java.util.Objects;

public class VodListFragment extends Fragment {

    public static final String VALUE_CATEGOTY = "category";
    private VodListViewModel mViewModel;
    private Category mCategory;
    private VodListFragmentBinding mBinding;
    private HeaderVodFiltersBinding mFiltersBinding;
    private FilterRecyclerAdapter mFilterAdapter;

    public static VodListFragment newInstance(Category category) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(VALUE_CATEGOTY, category);
        VodListFragment fragment = new VodListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = VodListFragmentBinding.inflate(inflater, container, false);
        mFiltersBinding = HeaderVodFiltersBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCategory = Objects.requireNonNull(getArguments()).getParcelable(VALUE_CATEGOTY);
        mBinding.setCategory(mCategory);
        mFilterAdapter = new FilterRecyclerAdapter(mCategory.getCategories());
        mFiltersBinding.recyclerView.setAdapter(mFilterAdapter);
        mFilterAdapter.addHeaderView(mFiltersBinding.getRoot());
        mViewModel = ViewModelProviders.of(this).get(VodListViewModel.class);
        mFilterAdapter.setOnFilterChangeListener(filters -> Log.i("***", filters));
        mBinding.recyclerView.setAdapter(new CustomAdapter(null));
    }

    private class CustomAdapter extends BaseQuickAdapter<Void, BaseViewHolder> {

        CustomAdapter(@Nullable List<Void> list) {
            super(R.layout.recycler_item_topic_data, list);
        }

        @Override
        protected void convert(BaseViewHolder helper, Void item) {
            if (item == null) {
                return;
            }
        }
    }
}
