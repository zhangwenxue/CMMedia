package com.cm.media.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import com.cm.dlna.DLNAManager;
import com.cm.media.R;
import com.cm.media.util.CollectionUtils;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import org.fourthline.cling.model.meta.Device;

import java.util.List;

public class DLNADisplayView extends LinearLayout {
    private Pair<String, String> mPlayParam;
    private Button mSearchButton;
    private ProgressBar mLoadingView;


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
        if (mPlayParam != null && DLNAManager.getInstance().getDevice() != null) {
            DLNAManager.getInstance().play(mPlayParam);
            Toast.makeText(getContext(), "即将投屏到 " + DLNAManager.getInstance().getDevice().getDetails().getFriendlyName(), Toast.LENGTH_SHORT).show();
        }
    }

    public boolean hasDevice() {
        return DLNAManager.getInstance().getDevice() != null;
    }

    public void search() {
        DLNAManager.getInstance().search();
        mSearchButton.setVisibility(GONE);
        mLoadingView.setVisibility(VISIBLE);
        postDelayed(mEndRunnable, 5000);
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
        setupDeviceList(chipGroup, DLNAManager.getInstance().getDeviceList());
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
        if (selectedDevice != null) {
            mSearchButton.setText(selectedDevice.getDetails().getFriendlyName());
        }
        for (Device device : deviceList) {
            Chip chip = (Chip) LayoutInflater.from(getContext()).inflate(R.layout.chip_dlna_item, null, false);
            chip.setText(device.getDetails().getFriendlyName());
            if (device.equals(selectedDevice)) {
                chip.setChecked(true);
            }
            ChipGroup.LayoutParams params = new ChipGroup.LayoutParams(ChipGroup.LayoutParams.MATCH_PARENT, ChipGroup.LayoutParams.WRAP_CONTENT);
            chip.setOnClickListener(v -> {
                DLNAManager.getInstance().setDevice(device);
                mSearchButton.setText(device.getDetails().getFriendlyName());
                if (mPlayParam != null) {
                    play();
                    DLNAManager.getInstance().setSameEpisodes();
                }
            });
            chipGroup.addView(chip, params);
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

}
