package com.cm.media.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.cm.media.R;
import com.cm.media.entity.vod.topic.Banner;
import com.cm.media.entity.vod.topic.BannerVod;
import com.cm.media.entity.vod.topic.TopicData;
import com.cm.media.ui.widget.AutoPlayLayout;
import com.cm.media.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeTopicAdapter extends RecyclerView.Adapter<HomeTopicAdapter.Holder> {
    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    private static final int VIEW_TYPE_BANNER = 0x01;
    private static final int VIEW_TYPE_TOPIC = 0x02;
    private List<TopicData> mDataList;
    private Banner mBanner;
    private OnLoadMoreListener mListener;

    public void updateBanner(Banner banner) {
        if (mBanner == null) {
            if (banner != null) {
                mBanner = banner;
                notifyItemInserted(0);
            }
        } else {
            if (banner == null) {
                mBanner = null;
                if (getItemCount() > 0 && getItemViewType(0) == VIEW_TYPE_BANNER) {
                    notifyItemRemoved(0);
                }
                return;
            }
            if (!mBanner.equals(banner)) {
                if (getItemCount() > 0 && getItemViewType(0) == VIEW_TYPE_BANNER) {
                    notifyItemChanged(0);
                } else if (getItemCount() > 0 && getItemViewType(0) != VIEW_TYPE_BANNER) {
                    notifyItemInserted(0);
                }
            }
        }
    }

    public void updateTopicList(List<TopicData> list, boolean refresh) {
        if (CollectionUtils.isEmptyList(list)) {
            return;
        }
        if (CollectionUtils.isEmptyList(mDataList) || refresh) {
            mDataList = list;
            notifyDataSetChanged();
            return;
        }
        mDataList.addAll(list);
        notifyItemRangeInserted(getItemCount(), list.size());
    }


    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_BANNER) {
            return new BannerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_banner, parent, false));
        } else {
            return new TopicViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_topic, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        if (position == 0) {
            BannerHolder h = (BannerHolder) holder;
            List<View> list = new ArrayList<>(mBanner.getAdList().size());
            for (BannerVod ignored : mBanner.getAdList()) {
                list.add(LayoutInflater.from(h.viewPager.getContext()).inflate(R.layout.pager_item_banner, h.viewPager, false));
            }
            BannerPagerAdapter adapter = new BannerPagerAdapter(list);
            h.viewPager.setAdapter(adapter);
            h.autoPlayLayout.setupWithViewPager(h.viewPager);
            return;
        }
        TopicViewHolder topicViewHolder = (TopicViewHolder) holder;
        topicViewHolder.topicName.setText(mDataList.get(position - 1).getTitle());
        topicViewHolder.recyclerView.setAdapter(new TopicAdapter(mDataList.get(position - 1)));
        if (position == getItemCount() - 1) {//已经到达列表的底部
            if (mListener != null) {
                mListener.onLoadMore();
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mBanner != null) {
            return position == 0 ? VIEW_TYPE_BANNER : VIEW_TYPE_TOPIC;
        } else {
            return VIEW_TYPE_TOPIC;
        }

    }

    @Override
    public int getItemCount() {
        int bannerSize = mBanner == null ? 0 : 1;
        return mDataList == null ? bannerSize : mDataList.size() + bannerSize;
    }

    abstract class Holder extends RecyclerView.ViewHolder {
        Holder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class BannerHolder extends Holder {
        ViewPager viewPager;
        AutoPlayLayout autoPlayLayout;

        BannerHolder(@NonNull View itemView) {
            super(itemView);
            viewPager = itemView.findViewById(R.id.bannerViewPager);
            autoPlayLayout = itemView.findViewById(R.id.autoPlayLayout);
        }
    }

    class TopicViewHolder extends Holder {
        TextView topicName;
        RecyclerView recyclerView;

        TopicViewHolder(@NonNull View itemView) {
            super(itemView);
            topicName = itemView.findViewById(R.id.topicName);
            recyclerView = itemView.findViewById(R.id.recyclerView);
        }
    }


    class BannerPagerAdapter extends PagerAdapter {

        List<View> mViewList;

        BannerPagerAdapter(List<View> list) {
            this.mViewList = list;
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
            View view = mViewList.get(position);
            ImageView imageView = view.findViewById(R.id.bannerPost);
            TextView bannerVodName = view.findViewById(R.id.bannerVodName);
            bannerVodName.setText(mBanner.getAdList().get(position).getTitle());
            Glide.with(container).load(mBanner.getAdList().get(position).getImg()).into(imageView);
            container.addView(view);
            return view;
        }


    }

    class TopicAdapter extends RecyclerView.Adapter<TopicHolder> {
        private TopicData mTopicData;

        TopicAdapter(TopicData topicData) {
            this.mTopicData = topicData;
        }

        @NonNull
        @Override

        public TopicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new TopicHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_topic_data, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull TopicHolder holder, int position) {
            Glide.with(holder.itemView).load(mTopicData.getVideoList().get(position).getImg()).into(holder.topicPost);
            holder.topicVodName.setText(mTopicData.getVideoList().get(position).getName());
            holder.topicVodName.setPressed(true);
        }

        @Override
        public int getItemCount() {
            return mTopicData == null ? 0 : mTopicData.getVideoList().size();
        }
    }

    class TopicHolder extends RecyclerView.ViewHolder {
        ImageView topicPost;
        TextView topicVodName;

        TopicHolder(@NonNull View itemView) {
            super(itemView);
            topicPost = itemView.findViewById(R.id.topicPost);
            topicVodName = itemView.findViewById(R.id.topicVodName);
        }
    }

}
