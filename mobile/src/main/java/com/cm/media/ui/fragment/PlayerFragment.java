package com.cm.media.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cm.media.R;
import com.cm.media.databinding.PlayerFragmentBinding;
import com.cm.media.entity.vod.VodDetail;
import com.cm.media.entity.vod.VodPlayUrl;
import com.cm.media.ui.adapter.VodUrlAdapter;
import com.cm.media.ui.widget.player.SuperPlayerModel;
import com.cm.media.ui.widget.player.SuperPlayerView;
import com.cm.media.viewmodel.PlayerViewModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

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
        mViewModel.getVodDetailLiveData().observe(this, vodDetail -> {
            if (vodDetail != null) {
                setUpChips(vodDetail);
            }
//            mAdapter = new VodUrlAdapter(vodDetail.getPlays());
//            mBinding.recyclerView.setAdapter(mAdapter);
//            mAdapter.setOnItemClickListener((adapter, view, position) -> {
//                mAdapter.setSelection(position);
//                mAdapter.notifyDataSetChanged();
//                if (mViewModel.getVodDetailLiveData().getValue() != null) {
//                    return;
//                }
//                mViewModel.processPlayUrl(mBinding.webViewContainer, mViewModel.getVodDetailLiveData().getValue().getPlays().get(position));
//            });
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

    private void setUpChips(final VodDetail vodDetail) {
        vodName = vodDetail.getName();
        if (mBinding.chipGroup.getChildCount() > 0) {
            mBinding.chipGroup.removeAllViews();
        }
        int idx = 0;
        int defaultSelection = 0;
        for (VodPlayUrl playUrl : vodDetail.getPlays()) {
            Chip chip = (Chip) LayoutInflater.from(mBinding.chipGroup.getContext()).inflate(R.layout.chip_play_item,
                    mBinding.chipGroup, false);
            chip.setText("第" + String.valueOf(idx + 1) + "集");
            chip.setTag(playUrl);
            if (idx == defaultSelection) {
                chip.setChecked(true);
            }
            chip.setId(++idx);
            mBinding.chipGroup.addView(chip);
        }
        mBinding.chipGroup.setOnCheckedChangeListener((chipGroup, id) -> {
            mViewModel.processPlayUrl(mBinding.webViewContainer, vodDetail.getPlays().get(id - 1));
        });
    }


    @Override
    public void onStop() {
        super.onStop();
    }

}
