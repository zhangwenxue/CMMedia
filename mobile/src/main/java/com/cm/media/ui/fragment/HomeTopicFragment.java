package com.cm.media.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.cm.media.databinding.HomeTopicFragmentBinding;
import com.cm.media.databinding.RecyclerItemBannerBinding;
import com.cm.media.entity.vod.topic.TopicData;
import com.cm.media.ui.adapter.BannerPagerAdapter;
import com.cm.media.ui.adapter.RecommendRecyclerAdapter;
import com.cm.media.viewmodel.HomeTopicViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeTopicFragment extends Fragment {

    private HomeTopicViewModel mViewModel;
    private HomeTopicFragmentBinding mBinding;
    private RecyclerItemBannerBinding mBannerBinding;
    private RecommendRecyclerAdapter mRecommendRecyclerAdapter;
    private List<TopicData> mTopicList = new ArrayList<>();

    public static HomeTopicFragment newInstance() {
        return new HomeTopicFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = HomeTopicFragmentBinding.inflate(inflater, container, false);
        mBannerBinding = RecyclerItemBannerBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomeTopicViewModel.class);
        mViewModel.start();
        mRecommendRecyclerAdapter = new RecommendRecyclerAdapter(mTopicList);
        mRecommendRecyclerAdapter.addHeaderView(mBannerBinding.getRoot());
        mBinding.recyclerView.setAdapter(mRecommendRecyclerAdapter);
        mViewModel.getBannerLiveData().observe(this, banner -> {
            if (banner != null) {
                BannerPagerAdapter adapter = new BannerPagerAdapter(getActivity(), banner);
                mBannerBinding.bannerViewPager.setAdapter(adapter);
            }
        });
        mViewModel.getTopicListLiveData().observe(this, pair -> {
            if (pair != null && pair.first != null) {
                if (pair.first) {
                    mRecommendRecyclerAdapter.setNewData(pair.second);
                    if (mViewModel.getHasNoMoreTopicsLiveData() != null && mViewModel.getHasNoMoreTopicsLiveData().getValue() != null) {
                        mRecommendRecyclerAdapter.setEnableLoadMore(!mViewModel.getHasNoMoreTopicsLiveData().getValue());
                    }
                } else if (pair.second != null) {
                    mRecommendRecyclerAdapter.addData(pair.second);
                }
            }
        });
        mBinding.refreshLayout.setOnRefreshListener(() -> mViewModel.loadData(3, true));
        mRecommendRecyclerAdapter.setOnLoadMoreListener(() -> mViewModel.loadData(3, false), mBinding.recyclerView);
        mViewModel.getIsLoadingFinish().observe(this, isLoadingFinish -> {
            if (isLoadingFinish != null && isLoadingFinish) {
                mRecommendRecyclerAdapter.loadMoreComplete();
            }
        });
        mViewModel.getIsRefreshFinish().observe(this, isRefreshFinish -> {
            if (isRefreshFinish != null && isRefreshFinish) {
                mBinding.refreshLayout.setRefreshing(false);
            }
        });
        mViewModel.getHasNoMoreTopicsLiveData().observe(this, hasNoMoreTopic -> {
            if (hasNoMoreTopic != null && hasNoMoreTopic) {
                mRecommendRecyclerAdapter.loadMoreEnd();
                mRecommendRecyclerAdapter.setEnableLoadMore(false);
            }
        });
    }

}
