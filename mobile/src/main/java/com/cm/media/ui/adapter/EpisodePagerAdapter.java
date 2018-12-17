package com.cm.media.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import com.bumptech.glide.Glide;
import com.cm.media.R;
import com.cm.media.entity.vod.VodPlayUrl;
import com.cm.media.ui.activity.VodPlayerActivity;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;
import java.util.Spliterator;

public class EpisodePagerAdapter extends PagerAdapter {
    public interface OnEpiSelectListener {
        void onEpiSelected(VodPlayUrl playUrl);
    }

    private static final int SPLIT = 32;
    private List<VodPlayUrl> mList;
    private int defaultSelection;
    private OnEpiSelectListener mListener;


    public EpisodePagerAdapter(int defEpiIndex, List<VodPlayUrl> list) {
        this.mList = list;
        this.defaultSelection = defEpiIndex;
    }

    public void setListener(OnEpiSelectListener listener) {
        this.mListener = listener;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : Math.max(mList.size() / SPLIT, 1);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        int startIndex = position * SPLIT;
        int length = Math.min(SPLIT, mList.size() - startIndex);
        return (startIndex + 1) + "-" + (startIndex+length);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View root = LayoutInflater.from(container.getContext()).inflate(R.layout.pager_item_episodes, null);
        ChipGroup chipGroup = root.findViewById(R.id.chips);
        int startIndex = position * SPLIT;
        int length = Math.min(SPLIT, mList.size() - startIndex);
        List<VodPlayUrl> subList = mList.subList(startIndex, startIndex + length);
        int idx = startIndex;
        for (VodPlayUrl playUrl : subList) {
            Chip chip = (Chip) LayoutInflater.from(container.getContext()).inflate(R.layout.chip_play_item,
                    chipGroup, false);
            chip.setText("第" + String.valueOf(idx + 1) + "集");
            chip.setTag(playUrl);
            if (idx == defaultSelection) {
                chip.setChecked(true);
            }
            chip.setId(++idx);
            chipGroup.addView(chip);
        }
        chipGroup.setOnCheckedChangeListener((group, id) -> {
            if (mListener != null) {
                mListener.onEpiSelected(subList.get(position));
            }
        });
        container.addView(root);
        return root;
    }
}
