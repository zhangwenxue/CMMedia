package com.cm.media.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.cm.media.databinding.HomeFragmentBinding;
import com.cm.media.viewmodel.HomeViewModel;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    private HomeFragmentBinding mBinding;
    private VodFragmentAdapter mAdapter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = HomeFragmentBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        mBinding.setViewModel(mViewModel);
        mViewModel.start();
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
        mAdapter = new VodFragmentAdapter(getChildFragmentManager());
        mBinding.viewPager.setAdapter(mAdapter);
        mViewModel.getCategoryList().observe(this, categories -> {
            if (categories == null || categories.size() <= 0) {
                mBinding.tabLayout.removeAllTabs();
                return;
            }
            if (mBinding.tabLayout.getTabCount() > 0) {
                mBinding.tabLayout.removeAllTabs();
            }
            mAdapter.replaceData(categories);
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
