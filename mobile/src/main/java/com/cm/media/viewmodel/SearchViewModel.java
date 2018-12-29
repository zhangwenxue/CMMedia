package com.cm.media.viewmodel;

import android.view.View;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.cm.media.entity.ViewStatus;
import com.cm.media.entity.search.SearchResult;
import com.cm.media.repository.RemoteRepo;
import com.cm.media.util.CollectionUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.util.Collections;
import java.util.List;

public class SearchViewModel extends ViewModel {
    private final MutableLiveData<List<SearchResult>> searchResult = new MutableLiveData<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MutableLiveData<ViewStatus> viewStatus = new MutableLiveData<>();
    private String keyWord;

    public MutableLiveData<List<SearchResult>> getSearchResult() {
        return searchResult;
    }

    public MutableLiveData<ViewStatus> getViewStatus() {
        return viewStatus;
    }

    public void search(String keyword) {
        this.keyWord = keyword;
        searchResult.setValue(Collections.emptyList());
        viewStatus.setValue(ViewStatus.Companion.generateLoadingStatus("加载中..."));
        Disposable disposable = RemoteRepo.getInstance().getRxSearchResult(keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listEntity -> {
                    if (!CollectionUtils.isEmptyList(listEntity.getData())) {
                        viewStatus.setValue(ViewStatus.Companion.generateSuccessStatus("成功"));
                    } else {
                        viewStatus.setValue(ViewStatus.Companion.generateEmptyStatus("没有搜索到结果！"));
                    }
                    searchResult.setValue(listEntity.getData());

                }, throwable -> {
                    viewStatus.setValue(ViewStatus.Companion.generateErrorStatus("加载失败，点击重试"));
                    searchResult.setValue(Collections.emptyList());
                    throwable.printStackTrace();
                });
        compositeDisposable.add(disposable);
    }

    public void retry() {
        search(keyWord);
    }

    public void click(View view) {

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}