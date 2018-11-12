package com.cm.media.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.cm.media.R;
import com.cm.media.ui.adapter.HomeTopicAdapter;
import com.cm.media.viewmodel.HomeTopicViewModel;

public class HomeTopicFragment extends Fragment {

    private HomeTopicViewModel mViewModel;

    public static HomeTopicFragment newInstance() {
        return new HomeTopicFragment();
    }

    RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_topic_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomeTopicViewModel.class);
        recyclerView = getView().findViewById(R.id.recyclerView);
        mViewModel.start();
        HomeTopicAdapter adapter = new HomeTopicAdapter();
        recyclerView.setAdapter(adapter);
        mViewModel.getBannerLiveData().observe(this, adapter::updateBanner);
        mViewModel.getTopicListLiveData().observe(this, list -> adapter.updateTopicList(list, true));
    }

}
