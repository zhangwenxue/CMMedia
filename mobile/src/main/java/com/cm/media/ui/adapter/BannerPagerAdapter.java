package com.cm.media.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.cm.media.databinding.PagerItemBannerBinding;
import com.cm.media.entity.vod.topic.Banner;
import com.cm.media.entity.vod.topic.BannerVod;

import java.util.ArrayList;
import java.util.List;

public class BannerPagerAdapter extends PagerAdapter {
    private Banner mBanner;
    private List<PagerItemBannerBinding> mViewList;

    public BannerPagerAdapter(Context context, Banner banner) {
        this.mBanner = banner;
        if (mBanner != null) {
            mViewList = new ArrayList<>(mViewList.size());
            for (BannerVod ignored : mBanner.getAdList()) {
                PagerItemBannerBinding bannerBinding = PagerItemBannerBinding.inflate(LayoutInflater.from(context));
                mViewList.add(bannerBinding);
            }
        }
    }


    @Override
    public int getCount() {
        return mViewList == null ? 0 : mViewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(mViewList.get(position).getRoot());
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        PagerItemBannerBinding binding = mViewList.get(position);
        View view = mViewList.get(position).getRoot();
        binding.bannerVodName.setText(mBanner.getAdList().get(position).getTitle());
        Glide.with(container).load(mBanner.getAdList().get(position).getImg()).into(binding.bannerPost);
        container.addView(view);
        return view;
    }

}
