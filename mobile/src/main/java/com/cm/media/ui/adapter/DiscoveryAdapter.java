package com.cm.media.ui.adapter;

import android.text.TextUtils;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cm.media.R;
import com.cm.media.entity.discover.Topic;
import com.cm.media.entity.discover.TopicVod;
import com.cm.media.ui.activity.VodPlayerActivity;
import com.cm.media.util.CollectionUtils;

import java.util.List;

public class DiscoveryAdapter extends BaseQuickAdapter<Topic, BaseViewHolder> {

    public DiscoveryAdapter(@Nullable List<Topic> data) {
        super(R.layout.recycler_item_discover, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Topic item) {
        if (item == null) {
            return;
        }
        RecyclerView recyclerView = helper.getView(R.id.recyclerView);
        if (!CollectionUtils.isEmptyList(item.getVideoList())) {
            DiscoveryItemAdapter adapter = new DiscoveryItemAdapter(item.getVideoList());
            recyclerView.setAdapter(adapter);
        }
        if (!TextUtils.isEmpty(item.getTitle())) {
            helper.setText(R.id.title, item.getTitle());
        }
    }


    private class DiscoveryItemAdapter extends BaseQuickAdapter<TopicVod, BaseViewHolder> {

        DiscoveryItemAdapter(@Nullable List<TopicVod> list) {
            super(R.layout.recycler_item_discover_data, list);
        }

        @Override
        protected void convert(BaseViewHolder helper, TopicVod item) {
            if (item == null) {
                return;
            }
            if (!TextUtils.isEmpty(item.getName())) {
                helper.setText(R.id.topicVodName, item.getName());
            }
            Glide.with(mContext).load(item.getImg()).into((ImageView) helper.getView(R.id.topicPost));
            helper.getView(R.id.topicPost).setOnClickListener(view -> VodPlayerActivity.startVodPlay(view.getContext(), item.getId()));
        }
    }
}
