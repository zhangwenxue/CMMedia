package com.cm.media.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.cm.media.databinding.PlayerFragmentBinding;
import com.cm.media.viewmodel.PlayerViewModel;

public class PlayerFragment extends Fragment {
    private static final String ARG_VIDEO_ID = "vid";
    private PlayerViewModel mViewModel;
    private PlayerFragmentBinding mBinding;
    private int mVid;


    public static PlayerFragment newInstance(int vid) {
        Bundle argument = new Bundle();
        argument.putInt(ARG_VIDEO_ID, vid);
        PlayerFragment fragment = new PlayerFragment();
        fragment.setArguments(argument);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = PlayerFragmentBinding.inflate(inflater, container, false);
        return mBinding.layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() == null) {
            return;
        }
        mVid = getArguments().getInt(ARG_VIDEO_ID);
        mViewModel = ViewModelProviders.of(this).get(PlayerViewModel.class);
        mViewModel.getPlayingUrlLiveData().observe(this, url -> {
            if (!TextUtils.isEmpty(url)) {
            }
        });
        mViewModel.start(mBinding.webViewContainer, mVid);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
