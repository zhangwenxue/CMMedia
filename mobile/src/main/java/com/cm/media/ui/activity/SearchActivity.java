package com.cm.media.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.cm.media.R;
import com.cm.media.databinding.ActivitySearchBinding;
import com.cm.media.entity.ViewStatus;
import com.cm.media.entity.search.SearchResult;
import com.cm.media.ui.adapter.SearchResultListAdapter;
import com.cm.media.viewmodel.SearchViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.Collections;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding binding;
    private SearchViewModel viewModel;

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
        binding.setViewModel(viewModel);
        SearchResultListAdapter adapter = new SearchResultListAdapter(Collections.emptyList());
        binding.recyclerView.setAdapter(adapter);
        viewModel.getSearchResult().observe(this, adapter::setNewData);
        viewModel.getViewStatus().observe(this, viewStatus -> binding.setViewStatus(viewStatus));
        binding.editKeyword.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (binding.editKeyword.getText() == null || binding.editKeyword.getText().toString().trim().length() == 0) {
                    Snackbar.make(binding.toolbar, "请输入搜索关键词", Snackbar.LENGTH_SHORT).show();
                    return false;
                }
                viewModel.search(binding.editKeyword.getText().toString().trim());
                return true;
            }
            return false;
        });
    }
}
