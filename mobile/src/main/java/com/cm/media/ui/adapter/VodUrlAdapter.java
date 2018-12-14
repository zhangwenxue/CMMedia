package com.cm.media.ui.adapter;

import android.widget.CheckBox;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cm.media.R;
import com.cm.media.entity.vod.VodPlayUrl;

import java.util.List;

public class VodUrlAdapter extends BaseQuickAdapter<VodPlayUrl, BaseViewHolder> {
    private int mSelection = 0;
    private List<VodPlayUrl> urlList;

    public void setSelection(int selection) {
        this.mSelection = selection;
    }

    public VodUrlAdapter(@Nullable List<VodPlayUrl> data) {
        super(R.layout.recycler_item_vod_url, data);
        urlList = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, VodPlayUrl item) {
        boolean checked = helper.getAdapterPosition() == mSelection;
        ((CheckBox) helper.getView(R.id.checkBox)).setChecked(checked);
        helper.setText(R.id.vodUrl, item.getName());
        helper.addOnClickListener(R.id.root);
    }
}
