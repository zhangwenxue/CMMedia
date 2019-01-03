package com.cm.media.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.bumptech.glide.Glide;
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
import java.util.Objects;

public class DiscoverActivity extends BaseThemeActivity {

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
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        binding.toolbar.setNavigationOnClickListener((view) -> finish());
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
                binding.toolbar.setTitle(discoverDisplay.getTitle());
                binding.toolbar.setSubtitle(discoverDisplay.getInfo());
                Glide.with(DiscoverActivity.this).load(discoverDisplay.getImg()).into(binding.parallelImg);
            }
        });
        discoverItemViewModel.getViewStatus().observe(this, binding::setViewStatus);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_search) {

        }
        return super.onOptionsItemSelected(item);
    }

}
