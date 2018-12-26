package com.cm.media.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.cm.media.databinding.DiscoverFragmentBinding;
import com.cm.media.databinding.RecyclerItemDiscoverBannerBinding;
import com.cm.media.entity.discover.Topic;
import com.cm.media.ui.adapter.DiscoverPagerAdapter;
import com.cm.media.ui.adapter.DiscoveryAdapter;
import com.cm.media.viewmodel.DiscoverViewModel;

import java.util.ArrayList;
import java.util.List;

public class DiscoverFragment extends Fragment {

    private DiscoverViewModel mViewModel;
    private DiscoveryAdapter mAdapter;
    private DiscoverFragmentBinding binding;
    private RecyclerItemDiscoverBannerBinding bannerBinding;
    private List<Topic> mTopicList = new ArrayList<>();

    public static DiscoverFragment newInstance() {
        return new DiscoverFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DiscoverFragmentBinding.inflate(inflater, container, false);
        bannerBinding = RecyclerItemDiscoverBannerBinding.inflate(inflater);
        return binding.root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DiscoverViewModel.class);
        mAdapter = new DiscoveryAdapter(mTopicList);
        mAdapter.bindToRecyclerView(binding.recyclerView);
        mAdapter.disableLoadMoreIfNotFullPage();
        mAdapter.addHeaderView(bannerBinding.layout);
        binding.recyclerView.setAdapter(mAdapter);
        bannerBinding.viewPager.setAutoScroll(2400);
        bannerBinding.viewPager.setMultiScreen(0.9f);
        bannerBinding.viewPager.setInfiniteLoop(true);
        bannerBinding.viewPager.setItemMargin(8, 8, 8, 8);
        bannerBinding.viewPager.setAutoMeasureHeight(true);

        mViewModel.getBanner().observe(this, banner -> {
            if (banner != null) {
                DiscoverPagerAdapter adapter = new DiscoverPagerAdapter(banner);
                bannerBinding.viewPager.setAdapter(adapter);
            }
        });
        mViewModel.getTopicList().observe(this, pair -> {
            if (pair == null) {
                return;
            }
            if (pair.first != null && pair.first) {
                mAdapter.setNewData(pair.second);
            } else if (pair.second != null) {
                mAdapter.addData(pair.second);
            }
        });
        binding.refreshLayout.setOnRefreshListener(() -> {
            mViewModel.fetchDiscoveryList(true);
        });
        mAdapter.setOnLoadMoreListener(() -> {
            mViewModel.fetchDiscoveryList(false);
        }, binding.recyclerView);
        mViewModel.getLoadMoreState().
                observe(this, state ->
                {
                    if (state == null) {
                        return;
                    }
                    switch (state) {
                        case DiscoverViewModel.STATE_LOAD_MORE_IDLE:
                            mAdapter.setEnableLoadMore(false);
                            break;
                        case DiscoverViewModel.STATE_LOAD_MORE_START:
                            mAdapter.setEnableLoadMore(true);
                            break;
                        case DiscoverViewModel.STATE_LOAD_MORE_FINISH:
                            mAdapter.loadMoreComplete();
                            break;
                        case DiscoverViewModel.STATE_LOAD_MORE_END:
                            mAdapter.loadMoreEnd();
                            break;
                        case DiscoverViewModel.STATE_LOAD_MORE_FAILED:
                            mAdapter.loadMoreFail();
                            break;
                        default:
                            break;
                    }
                });
        mViewModel.getRefreshState().observe(this, state -> {
            if (state == null) {
                return;
            }
            switch (state) {
                case DiscoverViewModel.STATE_REFRESH_IDLE:
                    binding.refreshLayout.setRefreshing(false);
                    break;
                case DiscoverViewModel.STATE_REFRESH_START:
                    break;
                case DiscoverViewModel.STATE_REFRESH_SUCCESS:
                    binding.refreshLayout.setRefreshing(false);
                    break;
                case DiscoverViewModel.STATE_REFRESH_EMPTY:
                    binding.refreshLayout.setRefreshing(false);
                    break;
                case DiscoverViewModel.STATE_REFRESH_FAILED:
                    binding.refreshLayout.setRefreshing(false);
                    break;
                default:
                    break;

            }
        });
        mViewModel.start();
    }

}
