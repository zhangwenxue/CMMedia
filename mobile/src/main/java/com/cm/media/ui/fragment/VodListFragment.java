package com.cm.media.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.cm.media.databinding.HeaderVodFiltersBinding;
import com.cm.media.databinding.VodListFragmentBinding;
import com.cm.media.entity.category.Category;
import com.cm.media.ui.adapter.FilterRecyclerAdapter;
import com.cm.media.ui.adapter.VodListAdapter;
import com.cm.media.util.CollectionUtils;
import com.cm.media.viewmodel.VodListViewModel;

import java.util.Collections;
import java.util.Objects;

public class VodListFragment extends Fragment {

    public static final String VALUE_CATEGORY = "category";
    private VodListViewModel mViewModel;
    private VodListFragmentBinding mBinding;
    private HeaderVodFiltersBinding mFiltersBinding;
    private VodListAdapter mListAdapter;

    public static VodListFragment newInstance(Category category) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(VALUE_CATEGORY, category);
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
        Category category = Objects.requireNonNull(getArguments()).getParcelable(VALUE_CATEGORY);
        mBinding.setCategory(category);
        mViewModel = ViewModelProviders.of(this).get(VodListViewModel.class);
        mViewModel.setCategory(category);
        mListAdapter = new VodListAdapter(Collections.emptyList());

        FilterRecyclerAdapter filterAdapter = new FilterRecyclerAdapter(category.getCategories());
        mListAdapter.addHeaderView(mFiltersBinding.getRoot());

        mFiltersBinding.headRecyclerView.setAdapter(filterAdapter);


        filterAdapter.setOnFilterChangeListener(filters -> {
            Log.i("***", filters);
            if (!filters.equals(mViewModel.getFilters())) {
                mViewModel.loadData(filters, true);
            }
        });
        mBinding.vodRecyclerView.setAdapter(mListAdapter);
        mViewModel.getVodLiveData().observe(this, vodWrapper -> {
            if (vodWrapper == null) {
                return;
            }
            if (!vodWrapper.filters.equals(mListAdapter.getFilters()) || vodWrapper.isRefresh) {
                mListAdapter.setFilters(vodWrapper.filters);
                mListAdapter.setNewData(vodWrapper.vodList);
                mListAdapter.notifyDataSetChanged();
            } else {
                if (CollectionUtils.isEmptyList(vodWrapper.vodList)) {
                    return;
                }
                if (mListAdapter.getItemCount() > 0) {
                    mListAdapter.addData(vodWrapper.vodList);
                } else {
                    mListAdapter.setNewData(vodWrapper.vodList);
                }
            }
        });
        mViewModel.getIsRefreshFinish().observe(this, isRefreshingFinish -> {
            if (isRefreshingFinish != null && isRefreshingFinish) {
                mBinding.refreshLayout.setRefreshing(false);
            }
        });

        mViewModel.getIsLoadingFinish().observe(this, isLoadingFinish -> {
            if (isLoadingFinish != null && isLoadingFinish) {
                mListAdapter.loadMoreComplete();
            }
        });

        mViewModel.getHasNoMoreData().observe(this, hasNoMoreData -> {
            if (hasNoMoreData != null && hasNoMoreData) {
                mListAdapter.loadMoreEnd(true);
            } else {
                mListAdapter.setEnableLoadMore(true);
            }
        });


        mBinding.refreshLayout.setOnRefreshListener(() -> mViewModel.start(mListAdapter.getFilters()));
        mListAdapter.setOnLoadMoreListener(() -> mViewModel.loadData(mListAdapter.getFilters(), false), mBinding.vodRecyclerView);
        mViewModel.start("");
    }

}
