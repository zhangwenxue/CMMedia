package com.cm.media.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.cm.media.databinding.PlayerFragmentBinding;
import com.cm.media.entity.vod.VodDetail;
import com.cm.media.entity.vod.VodPlayUrl;
import com.cm.media.repository.db.entity.VodHistory;
import com.cm.media.ui.adapter.EpisodePagerAdapter;
import com.cm.media.ui.widget.player.SuperPlayerConst;
import com.cm.media.ui.widget.player.SuperPlayerModel;
import com.cm.media.ui.widget.player.SuperPlayerUrl;
import com.cm.media.ui.widget.player.SuperPlayerView;
import com.cm.media.ui.widget.player.util.TCTimeUtils;
import com.cm.media.viewmodel.PlayerViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


public class PlayerFragment extends Fragment implements SuperPlayerView.PlayerViewCallback {
    private static final String TAG = "PlayerFragment";
    private static final String ARG_VIDEO_ID = "vid";
    private PlayerViewModel mViewModel;
    private PlayerFragmentBinding mBinding;
    private String vodName;
    private String vodPost;
    private CMDialog mParseDialog;
    private VodPlayUrl curVodPlayUrl;
    private EpisodePagerAdapter mEpisodeAdapter;
    private VodHistory mVodHistory;

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
        int mVid = getArguments().getInt(ARG_VIDEO_ID);
        mViewModel = ViewModelProviders.of(this).get(PlayerViewModel.class);
        mBinding.setViewModel(mViewModel);
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
        mBinding.playerView.setPlayerViewCallback(this);

        mParseDialog = new CMDialog();

        mViewModel.getVodDetailLiveData().observe(this, pair -> {
            if (pair != null) {
                mVodHistory = pair.first;
                if (pair.second != null) {
                    vodPost = pair.second.getImg();
                    setUpEpisodes(pair.second, pair.first);
                }

            }
        });
        mViewModel.getViewStatus().observe(this, viewStatus -> mBinding.setViewStatus(viewStatus));
        mViewModel.getParseState().observe(this, state -> {
            if (state == 0) {
                mParseDialog.showLoading(getChildFragmentManager());
            } else if (state > 0) {
                mParseDialog.onSuccess();
            } else {
                mParseDialog.showError(getChildFragmentManager());
            }
        });
        mParseDialog.setCallback(new CMDialog.Callback() {
            @Override
            public void exitWhenSuccess() {

            }

            @Override
            public void onRetry() {
                mViewModel.processPlayUrl(curVodPlayUrl);
            }
        });
        mViewModel.getRealPlayUrl().observe(this, realPlayUrl -> {
            if (realPlayUrl == null || realPlayUrl.getUrls().length == 0) {
                Snackbar.make(mBinding.tabLayout, "地址解析失败", Snackbar.LENGTH_SHORT).show();
                return;
            }
            final long duration = mVodHistory == null ? 0 : mVodHistory.getPosition();
            boolean history = mVodHistory != null && mVodHistory.getEpisode() == realPlayUrl.getEpisode();
            SuperPlayerModel superPlayerModel = new SuperPlayerModel();

            superPlayerModel.title = vodName;
            if (realPlayUrl.getUrls().length == 1) {
                superPlayerModel.videoURL = realPlayUrl.getUrls()[0].second;
            } else {
                List<SuperPlayerUrl> list = new ArrayList<>(realPlayUrl.getUrls().length);
                for (Pair<String, String> pairUrl : realPlayUrl.getUrls()) {
                    list.add(new SuperPlayerUrl(pairUrl.first, pairUrl.second));
                }
                superPlayerModel.multiVideoURLs = list;
                superPlayerModel.videoURL = list.get(0).url;
            }
            superPlayerModel.placeholderImage = vodPost;
            mBinding.playerView.playWithMode(superPlayerModel);

            mBinding.playerView.setPlayCallback(new SuperPlayerView.PlayCallback() {
                @Override
                public void onStartPlay() {
                    super.onStartPlay();
                    mVodHistory.setEpisode(realPlayUrl.getEpisode());
                    if (history && duration > 0) {
                        mBinding.playerView.seekTo((int) duration);
                        Snackbar.make(mBinding.tabLayout, "从" + TCTimeUtils.formattedTime(duration) + "处继续观看", Snackbar.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onPause() {
                    super.onPause();
                    if (mVodHistory.getDuration() == 0) {
                        mVodHistory.setDuration(mBinding.playerView.getDurationSeconds());
                    }
                    mVodHistory.setPosition(mBinding.playerView.getCurrentSeconds());
                    mViewModel.updateHistory(mVodHistory);
                }

                @Override
                public void onFinished() {
                    super.onFinished();
                    if (realPlayUrl.getEpisode() < realPlayUrl.getEpisodeCount()) {
                        mEpisodeAdapter.setSelectionWithViewPager(mBinding.viewPager, realPlayUrl.getEpisode(), true);
                    }
                }
            });
        });
        mBinding.playerView.setPlayerViewCallback(this);
        mViewModel.start(getActivity(), mVid);
    }


    private void setUpEpisodes(final VodDetail vodDetail, final VodHistory history) {
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
        if (vodDetail.getDetail() != null && !TextUtils.isEmpty(vodDetail.getDetail().getSummary())) {
            builder.append("简介：").append(vodDetail.getDetail().getSummary()).append("\n");
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
        if (mEpisodeAdapter == null) {
            mEpisodeAdapter = new EpisodePagerAdapter(vodDetail.getPlays());
        }
        mBinding.viewPager.setAdapter(mEpisodeAdapter);
        mBinding.playerView.setupEpisodes(vodDetail);

        mEpisodeAdapter.setListener((position, playUrl) -> {
            curVodPlayUrl = playUrl;
            mViewModel.processPlayUrl(curVodPlayUrl);
            mBinding.playerView.getEpisodeAdapter().setSelectionWithViewPager(mBinding.playerView.getViewPager(), position, false);
        });

        mBinding.playerView.getEpisodeAdapter().setListener((position, playUrl) -> {
            curVodPlayUrl = playUrl;
            mViewModel.processPlayUrl(curVodPlayUrl);
            mEpisodeAdapter.setSelectionWithViewPager(mBinding.viewPager, position, false);
        });
        mEpisodeAdapter.setSelectionWithViewPager(mBinding.viewPager, history.getEpisode() - 1, true);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mBinding.playerView.getPlayState() == SuperPlayerConst.PLAYSTATE_PLAY) {
            Log.i(TAG, "onResume state :" + mBinding.playerView.getPlayState());
            mBinding.playerView.onResume();
            if (mBinding.playerView.getPlayMode() == SuperPlayerConst.PLAYMODE_FLOAT) {
                mBinding.playerView.requestPlayMode(SuperPlayerConst.PLAYMODE_WINDOW);
            }
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (mBinding.playerView.getPlayMode() != SuperPlayerConst.PLAYMODE_FLOAT) {
            mBinding.playerView.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinding.playerView.release();
        if (mBinding.playerView.getPlayMode() != SuperPlayerConst.PLAYMODE_FLOAT) {
            mBinding.playerView.resetPlayer();
        }
    }

    @Override
    public void hideViews() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null && activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().hide();
        }
    }

    @Override
    public void showViews() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null && activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().show();
        }
    }

    @Override
    public void onQuit(int playMode) {
    }


    private void exitPlay() {
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        getActivity().finish();
    }
}
