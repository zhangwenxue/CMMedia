package com.cm.media.ui.fragment;

import android.os.Bundle;
import android.view.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.cm.media.R;
import com.cm.media.databinding.HomeFragmentBinding;
import com.cm.media.ui.activity.SearchActivity;
import com.cm.media.ui.adapter.VodFragmentAdapter;
import com.cm.media.viewmodel.HomeViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class HomeFragment extends Fragment {

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        HomeViewModel viewModel = ViewModelProviders.of(this).get(HomeViewModel.class.getSimpleName(), HomeViewModel.class);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(mBinding.toolbar);
        mBinding.setViewModel(viewModel);
        viewModel.start();
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
        mAdapter = new VodFragmentAdapter(getChildFragmentManager());
        mBinding.viewPager.setAdapter(mAdapter);
        viewModel.getCategoryList().observe(this, categories -> {
            if (categories == null || categories.size() <= 0) {
                mBinding.tabLayout.removeAllTabs();
                return;
            }
            if (mBinding.tabLayout.getTabCount() > 0) {
                mBinding.tabLayout.removeAllTabs();
            }
            mAdapter.replaceData(categories);
        });
        viewModel.getViewStatus().observe(this, viewStatus -> {
            if (viewStatus != null) {
                mBinding.setViewStatus(viewStatus);
            }
        });
        viewModel.getSnakeBarText().observe(this, text ->
                Snackbar.make(mBinding.toolbar, text, Snackbar.LENGTH_SHORT).show());

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_search) {
            SearchActivity.navi2Search(getActivity());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
