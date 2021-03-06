package com.cm.media.ui.adapter;

import android.widget.ImageView;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cm.media.R;
import com.cm.media.entity.search.SearchResult;
import com.cm.media.ui.activity.VodPlayerActivity;

import java.util.List;

public class SearchResultListAdapter extends BaseQuickAdapter<SearchResult, BaseViewHolder> {


    public SearchResultListAdapter(@Nullable List<SearchResult> data) {
        super(R.layout.recycler_item_vod, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchResult item) {
        helper.setText(R.id.vodName, String.valueOf(item.getName()));
        RequestOptions options = RequestOptions.placeholderOf(R.mipmap.place_holder);
        Glide.with(mContext).load(item.getImg()).apply(options).into((ImageView) helper.getView(R.id.vodPost));
        helper.getView(R.id.vodPost).setOnClickListener(view -> VodPlayerActivity.startVodPlay(view.getContext(), item.getId()));
    }
}
