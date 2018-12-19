package com.cm.media.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.cm.media.databinding.HeaderVodFiltersBinding;
import com.cm.media.databinding.VodListFragmentBinding;
import com.cm.media.entity.ViewStatus;
import com.cm.media.entity.category.Category;
import com.cm.media.ui.adapter.FilterRecyclerAdapter;
import com.cm.media.ui.adapter.VodListAdapter;
import com.cm.media.ui.widget.IndicatorView;
import com.cm.media.util.CollectionUtils;
import com.cm.media.viewmodel.VodListViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
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
        return mBinding.refreshLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Category category = Objects.requireNonNull(getArguments()).getParcelable(VALUE_CATEGORY);
        mViewModel = ViewModelProviders.of(this).get(VodListViewModel.class);
        mViewModel.setCategory(category);
        mListAdapter = new VodListAdapter(Collections.emptyList());
        mListAdapter.bindToRecyclerView(mBinding.vodRecyclerView);
        mListAdapter.disableLoadMoreIfNotFullPage();

        FilterRecyclerAdapter filterAdapter = new FilterRecyclerAdapter(category.getCategories());
        mListAdapter.addHeaderView(mFiltersBinding.headRecyclerView);
        mFiltersBinding.headRecyclerView.setAdapter(filterAdapter);

        IndicatorView indicatorView = new IndicatorView(getContext());
        indicatorView.setOnClickListener(v -> mViewModel.refresh());
        mListAdapter.addHeaderView(indicatorView, 1);
        filterAdapter.setOnFilterChangeListener(filters -> {
            Log.i("***", filters);
            if (!filters.equals(mViewModel.getFilters())) {
                mViewModel.loadData(filters, true);
            }
        });

        mBinding.vodRecyclerView.setAdapter(mListAdapter);

        mViewModel.getViewStatus().observe(this, viewStatus -> {
            indicatorView.setVisibility(viewStatus.getState() == ViewStatus.STATE_SUCCESS ? View.GONE : View.VISIBLE);
            indicatorView.setMessage(viewStatus.getMessage());
            indicatorView.setState(viewStatus.getState());
        });
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
                    mListAdapter.setNewData(new ArrayList<>());
                    return;
                }
                if (mListAdapter.getItemCount() > 0) {
                    mListAdapter.addData(vodWrapper.vodList);
                } else {
                    mListAdapter.setNewData(vodWrapper.vodList);
                }
            }
        });
        mViewModel.getLoadMoreEnable().observe(this, enable -> mListAdapter.setEnableLoadMore(enable));
        mViewModel.getRefreshState().observe(this, stringIntegerPair -> {
            if (stringIntegerPair == null || stringIntegerPair.second == null) {
                return;
            }
            switch (stringIntegerPair.second) {
                case VodListViewModel.STATE_REFRESH_IDLE:
                    mBinding.refreshLayout.setRefreshing(false);
                    break;
                case VodListViewModel.STATE_REFRESH_START:
                    break;
                case VodListViewModel.STATE_REFRESH_SUCCESS:
                    mBinding.refreshLayout.setRefreshing(false);
                    break;
                case VodListViewModel.STATE_REFRESH_EMPTY:
                    mBinding.refreshLayout.setRefreshing(false);
                    //Snackbar.make(mBinding.refreshLayout, "列表为空", Snackbar.LENGTH_SHORT).show();
                    break;
                case VodListViewModel.STATE_REFRESH_FAILED:
                    mBinding.refreshLayout.setRefreshing(false);
                    //Snackbar.make(mBinding.refreshLayout, "加载失败", Snackbar.LENGTH_SHORT).show();
                    break;
                default:
                    break;

            }
        });
        mViewModel.getLoadMoreState().
                observe(this, stringIntegerPair ->
                {
                    if (stringIntegerPair == null || stringIntegerPair.second == null) {
                        return;
                    }
                    switch (stringIntegerPair.second) {
                        case VodListViewModel.STATE_LOAD_MORE_IDLE:
                            mListAdapter.setEnableLoadMore(false);
                            break;
                        case VodListViewModel.STATE_LOAD_MORE_START:
                            mListAdapter.setEnableLoadMore(true);
                            break;
                        case VodListViewModel.STATE_LOAD_MORE_FINISH:
                            mListAdapter.loadMoreComplete();
                            break;
                        case VodListViewModel.STATE_LOAD_MORE_END:
                            mListAdapter.loadMoreEnd();
                            break;
                        case VodListViewModel.STATE_LOAD_MORE_FAILED:
                            mListAdapter.loadMoreFail();
                            break;
                        default:
                            break;
                    }
                });

        mBinding.refreshLayout.setOnRefreshListener(() -> mViewModel.refresh());
        mListAdapter.setOnLoadMoreListener(() -> {
            if (mViewModel.getLoadMoreState().getValue() != null && mViewModel.getLoadMoreState().getValue().second
                    != null && mViewModel.getLoadMoreState().getValue().second == VodListViewModel.STATE_LOAD_MORE_END) {
                mListAdapter.loadMoreEnd();
                return;
            }
            mViewModel.loadData(mListAdapter.getFilters(), false);
        }, mBinding.vodRecyclerView);
        mViewModel.refresh();
    }

}
