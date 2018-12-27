package com.cm.media.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.cm.media.entity.ViewStatus;
import com.cm.media.entity.discover.DiscoverDisplay;
import com.cm.media.repository.RemoteRepo;
import com.cm.media.util.CollectionUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DiscoverItemViewModel extends ViewModel {
    private final MutableLiveData<DiscoverDisplay> discoverDisplay = new MutableLiveData<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MutableLiveData<ViewStatus> viewStatus = new MutableLiveData<>();
    private int topicId;

    public MutableLiveData<DiscoverDisplay> getDiscoverDisplay() {
        return discoverDisplay;
    }

    public MutableLiveData<ViewStatus> getViewStatus() {
        return viewStatus;
    }

    public void start(int topicId) {
        this.topicId = topicId;
        viewStatus.setValue(ViewStatus.Companion.generateLoadingStatus("加载中..."));
        Disposable disposable = RemoteRepo.getInstance().getRxDiscoveryDisplay(topicId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(discoverItemEntity -> {
                            if (CollectionUtils.isEmptyList(discoverItemEntity.getData().getVideoDTOList())) {
                                viewStatus.setValue(ViewStatus.Companion.generateEmptyStatus("没有相关获取到相关数据，点击重试"));
                            } else {
                                viewStatus.setValue(ViewStatus.Companion.generateSuccessStatus(""));
                            }
                            discoverDisplay.setValue(discoverItemEntity.getData());
                        },
                        throwable -> {
                            throwable.printStackTrace();
                            viewStatus.setValue(ViewStatus.Companion.generateErrorStatus("加载失败，点击重试"));
                        });
        compositeDisposable.add(disposable);
    }

    public void retry() {
        start(topicId);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
