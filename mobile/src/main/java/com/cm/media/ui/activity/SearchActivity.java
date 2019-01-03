package com.cm.media.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.SystemClock;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.cm.media.R;
import com.cm.media.databinding.ActivitySearchBinding;
import com.cm.media.entity.ViewStatus;
import com.cm.media.entity.search.SearchResult;
import com.cm.media.repository.AppExecutor;
import com.cm.media.repository.db.AppDatabase;
import com.cm.media.repository.db.entity.SearchHistory;
import com.cm.media.ui.adapter.SearchResultListAdapter;
import com.cm.media.viewmodel.SearchViewModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static androidx.cursoradapter.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER;

public class SearchActivity extends BaseThemeActivity {
    private ActivitySearchBinding binding;
    private SearchViewModel viewModel;
    private String keywords;

    public static void navi2Search(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.root);
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        binding.setViewModel(viewModel);
        SearchResultListAdapter adapter = new SearchResultListAdapter(Collections.emptyList());
        binding.recyclerView.setAdapter(adapter);
        viewModel.getSearchResult().observe(this, adapter::setNewData);
        viewModel.getViewStatus().observe(this, viewStatus -> binding.setViewStatus(viewStatus));
        viewModel.getHistories().observe(this, this::setUpHistories);
        viewModel.start(this);
    }

    private void setUpHistories(List<SearchHistory> searchHistories) {
        if (binding.chipGroup.getChildCount() > 0) {
            binding.chipGroup.removeAllViews();
        }
        for (SearchHistory searchHistory : searchHistories) {
            Chip chip = new Chip(this);
            chip.setText(searchHistory.getValue());
            binding.chipGroup.addView(chip);
            chip.setCloseIconVisible(true);
            chip.setOnClickListener(v -> {
                viewModel.search(searchHistory.getValue());
            });
            chip.setOnCloseIconClickListener(v -> viewModel.deleteHistory(SearchActivity.this, searchHistory));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setIconified(false);
        searchView.setQueryHint("输入搜索关键字");

        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                keywords = s;
                viewModel.search(keywords);
                viewModel.insertHistory(SearchActivity.this, s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

}
