package com.cm.media.ui.adapter;

import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.cm.media.R;
import com.cm.media.entity.vod.VodPlayUrl;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.lang.ref.WeakReference;
import java.util.List;

public class EpisodePagerAdapter extends PagerAdapter {
    public interface OnEpiSelectListener {
        void onEpiSelected(VodPlayUrl playUrl);
    }

    private static final int SPLIT = 32;
    private List<VodPlayUrl> mList;
    private int defaultSelection = 0;
    private OnEpiSelectListener mListener;
    private SparseArray<WeakReference<View>> mViewRefList = new SparseArray<>();


    public EpisodePagerAdapter(List<VodPlayUrl> list) {
        this.mList = list;
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
        return (startIndex + 1) + "-" + (startIndex + length);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        if (mViewRefList.get(position) != null && mViewRefList.get(position).get() != null) {
            container.addView(mViewRefList.get(position).get());
            return mViewRefList.get(position).get();
        }
        ChipGroup chipGroup = (ChipGroup) LayoutInflater.from(container.getContext()).inflate(R.layout.pager_item_episodes, null);
        int startIndex = position * SPLIT;
        int length = Math.min(SPLIT, mList.size() - startIndex);
        List<VodPlayUrl> subList = mList.subList(startIndex, startIndex + length);
        int idx = startIndex;
        for (VodPlayUrl playUrl : subList) {
            Chip chip = (Chip) LayoutInflater.from(container.getContext()).inflate(R.layout.chip_play_item,
                    chipGroup, false);
            if (!TextUtils.isEmpty(subList.get(idx - startIndex).getTitle())) {
                chip.setText(subList.get(idx - startIndex).getTitle());
            } else {
                chip.setText("第" + String.valueOf(idx + 1) + "集");
            }
            chip.setTag(playUrl);
            chip.setChecked(idx == defaultSelection);
            chip.setId(idx++);
            chipGroup.addView(chip);
        }
        chipGroup.setOnCheckedChangeListener(mCheckedChangeListener);
        container.addView(chipGroup);
        mViewRefList.append(position, new WeakReference<>(chipGroup));
        return chipGroup;
    }

    private ChipGroup.OnCheckedChangeListener mCheckedChangeListener = (chipGroup, id) -> {
        setSelection(id);
    };

    public void setSelectionWithViewPager(ViewPager pager, int selection) {
        setSelection(selection);
        if (pager == null) {
            return;
        }
        int pagerIndex = selection / SPLIT;
        if (pager.getChildCount() >= pagerIndex) {
            pager.setCurrentItem(pagerIndex, true);
        }
    }

    private void setSelection(int selection) {
        if (selection == -1) {
            setSelectImpl(defaultSelection, true);
            return;
        }
        if (defaultSelection != selection) {
            setSelectImpl(defaultSelection, false);
        }
        defaultSelection = selection;
        setSelectImpl(defaultSelection, true);
    }

    private void setSelectImpl(int selection, boolean select) {
        int pagerIndex = selection / SPLIT;
        if (mViewRefList.get(pagerIndex) == null || mViewRefList.get(pagerIndex).get() == null) {
            return;
        }
        ChipGroup group = (ChipGroup) mViewRefList.get(pagerIndex).get();
        Chip chip = group.findViewById(selection);
        group.setOnCheckedChangeListener(null);
        if (chip != null) {
            chip.setChecked(select);
        }
        group.setOnCheckedChangeListener(mCheckedChangeListener);
        if (select && mListener != null) {
            mListener.onEpiSelected(mList.get(selection));
        }
    }
}
