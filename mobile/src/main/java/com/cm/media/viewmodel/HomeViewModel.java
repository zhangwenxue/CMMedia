package com.cm.media.viewmodel;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.cm.media.entity.ViewStatus;
import com.cm.media.entity.category.Category;
import com.cm.media.repository.RemoteRepo;
import com.cm.media.util.CollectionUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<List<Category>> categoryList = new MutableLiveData<>();
    private final MutableLiveData<ViewStatus> viewStatus = new MutableLiveData<>();
    private final MutableLiveData<String> snackBarText = new MutableLiveData<>();
    private Disposable disposable;

    public MutableLiveData<List<Category>> getCategoryList() {
        return categoryList;
    }

    public MutableLiveData<ViewStatus> getViewStatus() {
        return viewStatus;
    }

    public MutableLiveData<String> getSnakeBarText() {
        return snackBarText;
    }

    public void start() {
        viewStatus.postValue(ViewStatus.Companion.generateLoadingStatus("加载中..."));
        List<Category> list = getCategoryList().getValue();
        if (list != null && !list.isEmpty()) {
            viewStatus.postValue(ViewStatus.Companion.generateSuccessStatus(""));
            return;
        }
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            disposable = null;
        }
        disposable = RemoteRepo.getInstance().getRxCategory()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(listEntity -> {
                            List<Category> categories = listEntity.getData();
                            if (!CollectionUtils.isEmptyList(categories)) {
                                viewStatus.postValue(ViewStatus.Companion.generateSuccessStatus(""));
                                categories.add(0, Category.Companion.HOME_CATEGORY());
                                getCategoryList().setValue(categories);
                            } else {
                                viewStatus.postValue(ViewStatus.Companion.generateEmptyStatus("点击重试"));
                            }

                        }, throwable -> {
                            getCategoryList().setValue(null);
                            Log.w("HomeViewModel", throwable.getMessage(), throwable);
                            viewStatus.postValue(ViewStatus.Companion.generateErrorStatus("加载失败，点击重试"));
                            snackBarText.postValue("加载失败");
                        }
                );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            disposable = null;
        }
    }
}
