package com.cm.media.viewmodel;

import androidx.core.util.Pair;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.cm.media.entity.discover.Discovery;
import com.cm.media.entity.discover.Topic;
import com.cm.media.entity.discover.banner.Banner;
import com.cm.media.repository.RemoteRepo;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.util.List;

public class DiscoverViewModel extends ViewModel {
    public static final int STATE_REFRESH_IDLE = 0x00;
    public static final int STATE_REFRESH_START = 0x01;
    public static final int STATE_REFRESH_SUCCESS = 0x02;
    public static final int STATE_REFRESH_EMPTY = 0x03;
    public static final int STATE_REFRESH_FAILED = -0x04;

    public static final int STATE_LOAD_MORE_IDLE = 0x00;
    public static final int STATE_LOAD_MORE_START = 0x01;
    public static final int STATE_LOAD_MORE_FINISH = 0x02;
    public static final int STATE_LOAD_MORE_END = 0x03;
    public static final int STATE_LOAD_MORE_FAILED = -0x01;

    public static final int PAGE_SIZE = 15;
    private final MutableLiveData<Banner> banner = new MutableLiveData<>();
    private final MutableLiveData<Pair<Boolean, List<Topic>>> topicList = new MutableLiveData<>();
    private final MutableLiveData<Integer> refreshState = new MutableLiveData<>();
    private final MutableLiveData<Integer> loadMoreState = new MutableLiveData<>();


    private int pageNo = 1;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MutableLiveData<Banner> getBanner() {
        return banner;
    }

    public MutableLiveData<Integer> getRefreshState() {
        return refreshState;
    }

    public MutableLiveData<Integer> getLoadMoreState() {
        return loadMoreState;
    }

    public MutableLiveData<Pair<Boolean, List<Topic>>> getTopicList() {
        return topicList;
    }

    public void start() {
        fetchDiscoveryList(true);
    }

    public void fetchDiscoveryList(boolean refresh) {
        refreshState.setValue(refresh ? STATE_REFRESH_START : STATE_REFRESH_IDLE);
        loadMoreState.setValue(refresh ? STATE_LOAD_MORE_IDLE : STATE_LOAD_MORE_START);
        pageNo = refresh ? 1 : pageNo;
        Disposable disposable = RemoteRepo.getInstance().getRxDiscoveryList(pageNo, PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(discoveryEntity -> {
                    if (discoveryEntity != null) {
                        Discovery discovery = discoveryEntity.getData();
                        List<Topic> topicList = discovery.getTopics();
                        if (refresh) {
                            banner.setValue(discovery.getBanner());
                        }
                        if (topicList.size() < PAGE_SIZE) {
                            loadMoreState.setValue(STATE_LOAD_MORE_END);
                        } else {
                            pageNo++;
                        }
                        DiscoverViewModel.this.topicList.setValue(new Pair<>(refresh, topicList));
                        if (refresh) {
                            refreshState.setValue(topicList.size() > 0 ? STATE_REFRESH_SUCCESS : STATE_REFRESH_EMPTY);
                        } else {
                            loadMoreState.setValue(STATE_LOAD_MORE_FINISH);
                        }
                    }
                }, throwable -> {
                    if (refresh) {
                        refreshState.setValue(STATE_REFRESH_FAILED);
                    } else {
                        loadMoreState.setValue(STATE_LOAD_MORE_FAILED);
                    }
                });
        compositeDisposable.add(disposable);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
