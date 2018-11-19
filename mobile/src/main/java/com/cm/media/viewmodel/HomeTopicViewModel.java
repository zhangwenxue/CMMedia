package com.cm.media.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v4.util.Pair;
import com.cm.media.entity.vod.topic.Banner;
import com.cm.media.entity.vod.topic.Topic;
import com.cm.media.entity.vod.topic.TopicData;
import com.cm.media.repository.RemoteRepo;
import com.cm.media.util.CollectionUtils;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

public class HomeTopicViewModel extends ViewModel {
    private int pageNo = 1;
    private final MutableLiveData<Pair<Boolean, List<TopicData>>> topicListLiveData = new MutableLiveData<>();
    private final MutableLiveData<Banner> bannerLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> hasNoMoreTopics = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoadingFinish = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isRefreshFinish = new MutableLiveData<>();

    public MutableLiveData<Pair<Boolean, List<TopicData>>> getTopicListLiveData() {
        return topicListLiveData;
    }


    public MutableLiveData<Banner> getBannerLiveData() {
        return bannerLiveData;
    }

    public MutableLiveData<Boolean> getHasNoMoreTopicsLiveData() {
        return hasNoMoreTopics;
    }

    public MutableLiveData<Boolean> getIsLoadingFinish() {
        return isLoadingFinish;
    }

    public MutableLiveData<Boolean> getIsRefreshFinish() {
        return isRefreshFinish;
    }

    public void start() {
        if (getTopicListLiveData().getValue() == null) {
            loadData(3, true);
        }

    }


    public void loadData(int pageSize, boolean refresh) {
        if (refresh) {
            isRefreshFinish.setValue(false);
            hasNoMoreTopics.setValue(false);
        } else {
            isLoadingFinish.setValue(false);
        }
        pageNo = refresh ? 1 : pageNo;
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.vfans.fun")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        Disposable disposable = RemoteRepo.getInstance().getRxTopic(pageNo, pageSize)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(topicEntity -> {
                    Topic topic = topicEntity.getData();
                    if (topic != null) {
                        List<TopicData> list = topic.getTopics();
                        if (!CollectionUtils.isEmptyList(list)) {
                            if (list.size() < pageSize) {
                                hasNoMoreTopics.setValue(true);
                            } else {
                                pageNo++;
                            }
                        } else {
                            hasNoMoreTopics.setValue(true);
                        }
                        topicListLiveData.setValue(new Pair<>(refresh, list));
                        if (bannerLiveData.getValue() == null) {
                            Banner banner = topic.getBanner();
                            bannerLiveData.setValue(banner);
                        }
                    }
                    if (refresh) {
                        isRefreshFinish.setValue(true);
                    } else {
                        isLoadingFinish.setValue(true);
                    }
                }, throwable -> {
                    if (refresh) {
                        isRefreshFinish.setValue(true);
                    } else {
                        isLoadingFinish.setValue(true);
                    }
                });
    }

}
