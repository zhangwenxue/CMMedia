package com.cm.media.ui.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.cm.media.databinding.HomeTopicFragmentBinding;
import com.cm.media.ui.adapter.HomeTopicAdapter;
import com.cm.media.viewmodel.HomeTopicViewModel;

public class HomeTopicFragment extends Fragment {

    private HomeTopicViewModel mViewModel;
    private HomeTopicFragmentBinding mBinding;

    public static HomeTopicFragment newInstance() {
        return new HomeTopicFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = HomeTopicFragmentBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomeTopicViewModel.class);
        mViewModel.start();
        HomeTopicAdapter adapter = new HomeTopicAdapter();
        mBinding.recyclerView.setAdapter(adapter);
        mViewModel.getBannerLiveData().observe(this, adapter::updateBanner);
        mViewModel.isRefresh().observe(this, aBoolean -> mBinding.refreshLayout.setRefreshing(aBoolean == null ? false : aBoolean));
        mViewModel.getTopicListLiveData().observe(this, pair -> adapter.updateTopicList(pair.second, pair.first));
        adapter.setOnLoadMoreListener(() -> mViewModel.loadData(3, false));
        mBinding.refreshLayout.setOnRefreshListener(() -> mViewModel.loadData(3, true));
    }

}
