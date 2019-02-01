package com.cm.media.ui.adapter;

import android.text.TextUtils;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cm.media.R;
import com.cm.media.entity.vod.topic.TopicData;
import com.cm.media.entity.vod.topic.TopicVod;
import com.cm.media.ui.activity.VodPlayerActivity;
import com.cm.media.util.BlurUtil;
import com.cm.media.util.CollectionUtils;

import java.util.List;

public class RecommendRecyclerAdapter extends BaseQuickAdapter<TopicData, BaseViewHolder> {

    public RecommendRecyclerAdapter(@Nullable List<TopicData> data) {
        super(R.layout.recycler_item_topic, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TopicData item) {
        if (item == null) {
            return;
        }
        RecyclerView recyclerView = helper.getView(R.id.recyclerView);
        if (!CollectionUtils.isEmptyList(item.getVideoList())) {
            TopicRecyclerAdapter adapter = new TopicRecyclerAdapter(item.getVideoList());
            recyclerView.setAdapter(adapter);
        }
        if (!TextUtils.isEmpty(item.getTitle())) {
            helper.setText(R.id.topicName, String.valueOf(item.getTitle()));
        }
    }


    private class TopicRecyclerAdapter extends BaseQuickAdapter<TopicVod, BaseViewHolder> {

        TopicRecyclerAdapter(@Nullable List<TopicVod> list) {
            super(R.layout.recycler_item_topic_data, list);
        }

        @Override
        protected void convert(BaseViewHolder helper, TopicVod item) {
            if (item == null) {
                return;
            }
            if (!TextUtils.isEmpty(item.getName())) {
                helper.setText(R.id.topicVodName, String.valueOf(item.getName()));
            }
            RequestOptions options = RequestOptions.placeholderOf(R.mipmap.place_holder);
            Glide.with(mContext).load(item.getImg()).apply(options).into((ImageView) helper.getView(R.id.topicPost));
            helper.getView(R.id.topicPost).setOnClickListener(view -> VodPlayerActivity.startVodPlay(view.getContext(), item.getId()));
        }
    }
}
