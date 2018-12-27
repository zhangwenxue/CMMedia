package com.cm.media.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.cm.media.R;
import com.cm.media.databinding.ActivityDiscoverBinding;
import com.cm.media.entity.ViewStatus;
import com.cm.media.entity.discover.DiscoverDisplay;
import com.cm.media.entity.discover.DiscoverDisplayItem;
import com.cm.media.ui.adapter.DiscoverDisplayAdapter;
import com.cm.media.viewmodel.DiscoverItemViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class DiscoverActivity extends AppCompatActivity {

    public static void startDiscoverActivity(Context context, int topicId) {
        Intent intent = new Intent(context, DiscoverActivity.class);
        intent.putExtra("topicId", topicId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDiscoverBinding binding = ActivityDiscoverBinding.inflate(getLayoutInflater());
        setContentView(binding.layout);
        DiscoverItemViewModel discoverItemViewModel = ViewModelProviders.of(this).get(DiscoverItemViewModel.class);
        binding.setViewModel(discoverItemViewModel);
        int topicId = getIntent().getIntExtra("topicId", 0);
        if (topicId <= 0) {
            Snackbar.make(binding.recyclerView, "参数错误", Snackbar.LENGTH_SHORT).show();
            finish();
        }
        discoverItemViewModel.start(topicId);

        DiscoverDisplayAdapter adapter = new DiscoverDisplayAdapter(new ArrayList<>());
        adapter.setEnableLoadMore(false);
        binding.recyclerView.setAdapter(adapter);
        discoverItemViewModel.getDiscoverDisplay().observe(this, discoverDisplay -> {
            if (discoverDisplay != null) {
                adapter.setNewData(discoverDisplay.getVideoDTOList());
            }
        });
        discoverItemViewModel.getViewStatus().observe(this, binding::setViewStatus);
    }

}
