package com.cm.media.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cm.dlna.DLNAManager;
import com.cm.media.R;
import com.cm.media.util.CollectionUtils;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import org.fourthline.cling.model.meta.Device;

import java.util.Collections;
import java.util.List;

public class DLNADisplayView extends LinearLayout {
    private Pair<String, String> mPlayParam;
    private Button mSearchButton;
    private ProgressBar mLoadingView;
    private Switch mSwitch;

    public DLNADisplayView(@NonNull Context context) {
        this(context, null);
    }

    public DLNADisplayView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DLNADisplayView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public DLNADisplayView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void setPlayParam(Pair<String, String> playParam) {
        mPlayParam = playParam;
    }

    public void play() {
        DLNAManager.getInstance().play(mPlayParam);
    }

    public boolean hasDevice() {
        return DLNAManager.getInstance().getDevice() != null;
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_dlna_devices, this, true);
        ChipGroup chipGroup = findViewById(R.id.chipGroup);
        mSearchButton = findViewById(R.id.refreshBtn);
        mSearchButton.setOnClickListener(v -> {
            DLNAManager.getInstance().search();
            mSearchButton.setVisibility(GONE);
            mLoadingView.setVisibility(VISIBLE);
            postDelayed(mEndRunnable, 5000);
        });
        mLoadingView = findViewById(R.id.progress);
        mSwitch = findViewById(R.id.switch_player);
        DLNAManager.getInstance().setSearchCallback(list -> setupDeviceList(chipGroup, list));
    }

    private void setupDeviceList(ChipGroup chipGroup, List<Device> deviceList) {
        if (chipGroup != null && chipGroup.getChildCount() > 0) {
            chipGroup.removeAllViews();
        }
        if (CollectionUtils.isEmptyCollection(deviceList)) {
            return;
        }
        Device selectedDevice = DLNAManager.getInstance().getDevice();
        for (Device device : deviceList) {
            Chip chip = (Chip) LayoutInflater.from(getContext()).inflate(R.layout.chip_play_item, null, false);
            chip.setText(device.getDetails().getFriendlyName());
            if (device != null && device.equals(selectedDevice)) {
                chip.setChecked(true);
            }
            chip.setOnClickListener(v -> {
                DLNAManager.getInstance().setDevice(device);
                if (mPlayParam != null) {
                    play();
                }
            });
            chipGroup.addView(chip);
        }

    }


    private final Runnable mEndRunnable = () -> {
        mSearchButton.setVisibility(VISIBLE);
        mLoadingView.setVisibility(GONE);
    };

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(mEndRunnable);
    }


    private class DeviceAdapter extends BaseQuickAdapter<Device, BaseViewHolder> {
        DeviceAdapter(@Nullable List<Device> data) {
            super(android.R.layout.simple_list_item_1, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Device item) {
            TextView textView = helper.getView(android.R.id.text1);
            textView.setText(item.getDetails().getFriendlyName());
        }
    }

}
