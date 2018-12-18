package com.cm.media.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.cm.media.databinding.HomeFragmentBinding;
import com.cm.media.ui.adapter.VodFragmentAdapter;
import com.cm.media.viewmodel.HomeViewModel;
import com.google.android.material.snackbar.Snackbar;

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
        return mBinding.layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class.getSimpleName(), HomeViewModel.class);
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
        mViewModel.getViewStatus().observe(this, viewStatus -> {
            if (viewStatus != null) {
                mBinding.setViewStatus(viewStatus);
            }
        });
        mViewModel.getSnakeBarText().observe(this, text ->
                Snackbar.make(mBinding.toolbar, text, Snackbar.LENGTH_SHORT).show());

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
