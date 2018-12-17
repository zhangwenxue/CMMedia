package com.cm.media.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.cm.media.databinding.PlayerFragmentBinding;
import com.cm.media.entity.vod.VodDetail;
import com.cm.media.ui.adapter.EpisodePagerAdapter;
import com.cm.media.ui.widget.player.SuperPlayerModel;
import com.cm.media.ui.widget.player.SuperPlayerView;
import com.cm.media.viewmodel.PlayerViewModel;


public class PlayerFragment extends Fragment {
    private static final String TAG = "PlayerFragment";
    private static final String ARG_VIDEO_ID = "vid";
    private PlayerViewModel mViewModel;
    private PlayerFragmentBinding mBinding;
    private int mVid;
    private String vodName;


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
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
        mViewModel.getVodDetailLiveData().observe(this, vodDetail -> {
            if (vodDetail != null) {
                setUpEpisodes(vodDetail);
            }
        });

        mViewModel.getPlayingUrlLiveData().observe(this, url -> {
            if (!TextUtils.isEmpty(url)) {
                SuperPlayerModel superPlayerModel = new SuperPlayerModel();
                superPlayerModel.title = vodName;
                superPlayerModel.videoURL = url;
                superPlayerModel.placeholderImage = "http://xiaozhibo-10055601.file.myqcloud.com/coverImg.jpg";
                mBinding.playerView.playWithMode(superPlayerModel);
            }
        });
        mViewModel.start(mBinding.webViewContainer, mVid);
        mBinding.playerView.setPlayerViewCallback(new SuperPlayerView.PlayerViewCallback() {
            @Override
            public void hideViews() {

            }

            @Override
            public void showViews() {

            }

            @Override
            public void onQuit(int playMode) {

            }
        });
    }

    private void setUpEpisodes(final VodDetail vodDetail) {
        if (vodDetail.getPlays().size() <= 32) {
            mBinding.tabLayout.setVisibility(View.GONE);
        }
        StringBuilder builder = new StringBuilder();
        if (!TextUtils.isEmpty(vodDetail.getName())) {
            builder.append(vodDetail.getName());
        }
        if (!TextUtils.isEmpty(vodDetail.getSeason())) {
            if (builder.length() > 0) {
                builder.append(" ");
            }
            builder.append(vodDetail.getSeason());
        }
        if (builder.length() > 0) {
            builder.append("\n");

            if (!TextUtils.isEmpty(vodDetail.getEsTags())) {
                builder.append("类型：").append(vodDetail.getEsTags()).append("\n");
            }
            if (!TextUtils.isEmpty(vodDetail.getArea())) {
                builder.append("地区：").append(vodDetail.getArea()).append("\n");
            }
            if (!TextUtils.isEmpty(vodDetail.getYear())) {
                builder.append("年代：").append(vodDetail.getYear()).append("\n");
            }
        }
        if (!TextUtils.isEmpty(vodDetail.getEsKws())) {
            builder.append("关键词：").append(vodDetail.getEsKws()).append("\n");
        }
        if (!TextUtils.isEmpty(vodDetail.getInfo())) {
            builder.append("简介：").append(vodDetail.getInfo()).append("\n");
        }

        if (builder.length() > 0) {
            mBinding.desc.setText(builder.toString());
        }

        vodName = vodDetail.getName();
        if (mBinding.viewPager.getChildCount() > 0) {
            mBinding.viewPager.removeAllViews();
        }
        if (mBinding.tabLayout.getChildCount() > 0) {
            mBinding.tabLayout.removeAllTabs();
        }

        EpisodePagerAdapter adapter = new EpisodePagerAdapter(0, vodDetail.getPlays());
        mBinding.viewPager.setAdapter(adapter);
        adapter.setListener(playUrl -> mViewModel.processPlayUrl(mBinding.webViewContainer, playUrl));
    }


    @Override
    public void onStop() {
        super.onStop();
    }

}
