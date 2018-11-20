package com.cm.media.ui.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.cm.media.databinding.PlayerFragmentBinding;
import com.cm.media.ui.widget.media.AndroidMediaController;
import com.cm.media.viewmodel.PlayerViewModel;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class PlayerFragment extends Fragment {
    private static final String ARG_VIDEO_ID = "vid";
    private PlayerViewModel mViewModel;
    private PlayerFragmentBinding mBinding;
    private AndroidMediaController mMediaController;
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
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() == null) {
            return;
        }
        mVid = getArguments().getInt(ARG_VIDEO_ID);
        mViewModel = ViewModelProviders.of(this).get(PlayerViewModel.class);
        mMediaController = new AndroidMediaController(getActivity(), true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mBinding.toolbar);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        mMediaController = new AndroidMediaController(getActivity(), false);
        mMediaController.setSupportActionBar(actionBar);

        // init player
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        mBinding.videoView.setMediaController(mMediaController);
        mBinding.videoView.setHudView(mBinding.hudView);
        mViewModel.getPlayingUrlLiveData().observe(this, url -> {
            if (!TextUtils.isEmpty(url)) {
                mBinding.videoView.setVideoPath(url);
                mBinding.videoView.start();
            }
        });
        mViewModel.start(mVid);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (!mBinding.videoView.isBackgroundPlayEnabled()) {
            mBinding.videoView.stopPlayback();
            mBinding.videoView.release(true);
            mBinding.videoView.stopBackgroundPlay();
        } else {
            mBinding.videoView.enterBackground();
        }
        IjkMediaPlayer.native_profileEnd();
    }

}
