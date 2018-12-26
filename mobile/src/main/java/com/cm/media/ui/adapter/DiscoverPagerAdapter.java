package com.cm.media.ui.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.bumptech.glide.Glide;
import com.cm.media.R;
import com.cm.media.entity.discover.banner.Banner;
import com.cm.media.ui.activity.VodPlayerActivity;

public class DiscoverPagerAdapter extends PagerAdapter {
    private Banner mBanner;

    public DiscoverPagerAdapter(Banner banner) {
        this.mBanner = banner;
    }


    @Override
    public int getCount() {
        return mBanner == null ? 0 : mBanner.getAdList().size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View root = LayoutInflater.from(container.getContext()).inflate(R.layout.pager_item_banner, null);
        final TextView bannerVodName = root.findViewById(R.id.bannerVodName);
        ImageView bannerPost = root.findViewById(R.id.bannerPost);
        bannerVodName.setText(mBanner.getAdList().get(position).getTitle());
        Glide.with(container).load(mBanner.getAdList().get(position).getImg()).into(bannerPost);
        String url =  mBanner.getAdList().get(position).getUrl();
        Uri uri = Uri.parse(url);
        String vid = uri.getLastPathSegment();
        bannerPost.setOnClickListener(view1 -> VodPlayerActivity.startVodPlay(view1.getContext(), Integer.valueOf(vid)));
        container.addView(root);
        return root;
    }

}
