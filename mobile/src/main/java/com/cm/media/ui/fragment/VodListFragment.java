package com.cm.media.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.cm.media.databinding.VodListFragmentBinding;
import com.cm.media.entity.category.Category;

public class VodListFragment extends Fragment {

    public static final String VALUE_CATEGOTY = "category";
    private VodListViewModel mViewModel;
    private Category mCategory;
    private VodListFragmentBinding mBinding;

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
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCategory = getArguments().getParcelable(VALUE_CATEGOTY);
        mBinding.setCategory(mCategory);
        mViewModel = ViewModelProviders.of(this).get(VodListViewModel.class);
    }

}
