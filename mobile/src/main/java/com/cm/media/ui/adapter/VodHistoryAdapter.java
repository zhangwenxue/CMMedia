package com.cm.media.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cm.media.R;
import com.cm.media.repository.db.entity.VodHistory;
import com.cm.media.ui.activity.VodPlayerActivity;

import java.util.List;

public class VodHistoryAdapter extends BaseItemDraggableAdapter<VodHistory, BaseViewHolder> {

    public VodHistoryAdapter(@Nullable List<VodHistory> data) {
        super(R.layout.recycler_item_vod_history, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VodHistory item) {
        RequestOptions options = RequestOptions.placeholderOf(R.mipmap.place_holder);
        Glide.with(mContext).load(item.getPost()).apply(options).into((ImageView) helper.getView(R.id.vodPost));
        int epiCount = item.getEpiCount();
        int epi = item.getEpisode();
        String title = epiCount > 0 ? item.getName() + "(" + epi + ")" : item.getName();
        helper.setText(R.id.vodName, title);
        helper.getView(R.id.layout).setOnClickListener(view -> VodPlayerActivity.startVodPlay(view.getContext(),
                Integer.valueOf(item.getVid())));
        int progress = 0;
        if (item.getDuration() > 0) {
            progress = (int) ((item.getPosition() * 100) / item.getDuration());
        }
        helper.setProgress(R.id.progressBar, progress, 100);
        View root = helper.getView(R.id.root);
        if (root != null) {
            root.setTag(R.id.root, item);
        }
    }
}
