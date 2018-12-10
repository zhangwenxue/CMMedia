package com.cm.media.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.bumptech.glide.Glide;
import com.cm.media.R;
import com.cm.media.databinding.PagerItemBannerBinding;
import com.cm.media.entity.vod.topic.Banner;
import com.cm.media.entity.vod.topic.BannerVod;
import com.cm.media.ui.activity.VodPlayerActivity;

import java.util.ArrayList;
import java.util.List;

public class BannerPagerAdapter extends PagerAdapter {
    private Banner mBanner;
    private List<View> mViewList;

    public BannerPagerAdapter(Context context, Banner banner) {
        this.mBanner = banner;
        if (mBanner != null) {
            mViewList = new ArrayList<>(mBanner.getAdList().size());
            for (BannerVod ignored : mBanner.getAdList()) {
                View view = LayoutInflater.from(context).inflate(R.layout.pager_item_banner, null, false);
                mViewList.add(view);
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
        container.removeView(mViewList.get(position));
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View root = mViewList.get(position);
        TextView bannerVodName = root.findViewById(R.id.bannerVodName);
        ImageView bannerPost = root.findViewById(R.id.bannerPost);
        bannerVodName.setText(mBanner.getAdList().get(position).getTitle());
        Glide.with(container).load(mBanner.getAdList().get(position).getImg()).into(bannerPost);
        bannerPost.setOnClickListener(view1 -> VodPlayerActivity.startVodPlay(view1.getContext(), mBanner.getAdList().get(position).getId()));
        container.addView(root);
        return root;
    }

}
