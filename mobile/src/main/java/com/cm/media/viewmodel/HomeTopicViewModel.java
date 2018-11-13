package com.cm.media.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Pair;
import com.cm.media.ApiService;
import com.cm.media.entity.vod.topic.Banner;
import com.cm.media.entity.vod.topic.Topic;
import com.cm.media.entity.vod.topic.TopicData;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

public class HomeTopicViewModel extends ViewModel {
    private int pageNo = 1;
    private final MutableLiveData<Boolean> isRefresh = new MutableLiveData<>();
    private final MutableLiveData<Pair<Boolean, List<TopicData>>> topicListLiveData = new MutableLiveData<>();
    private final MutableLiveData<Banner> bannerLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> hasNoMoreData = new MutableLiveData<>();


    public MutableLiveData<Pair<Boolean, List<TopicData>>> getTopicListLiveData() {
        return topicListLiveData;
    }

    public MutableLiveData<Banner> getBannerLiveData() {
        return bannerLiveData;
    }

    public void start() {
        if (getTopicListLiveData().getValue() == null) {
            pageNo = 1;
            hasNoMoreData.setValue(false);
            isRefresh.setValue(true);
            loadData(3, true);
        }

    }

    public MutableLiveData<Boolean> isRefresh() {
        return isRefresh;
    }

    public void loadData(int pageSize, boolean refresh) {
        if (hasNoMoreData.getValue()) {
            return;
        }
        pageNo = refresh ? 0 : pageNo;
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.vfans.fun")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);
        Disposable disposable = service.getRxTopic(pageNo, pageSize)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(topicEntity -> {
                    isRefresh.setValue(false);
                    Topic topic = topicEntity.getData();
                    if (topic != null) {
                        List<TopicData> list = topic.getTopics();
                        if (!list.isEmpty()) {
                            topicListLiveData.setValue(new Pair<>(refresh, list));
                        }
                        hasNoMoreData.setValue(list.isEmpty() || list.size() < pageSize);

                        if (bannerLiveData.getValue() == null) {
                            Banner banner = topic.getBanner();
                            bannerLiveData.setValue(banner);
                        }
                        pageNo++;
                    }
                }, throwable -> isRefresh.setValue(false));
        pageNo++;
    }

}
