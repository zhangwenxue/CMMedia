package com.cm.media.ui.fragment;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.cm.media.entity.category.Category;
import com.cm.media.entity.vod.topic.Banner;
import com.cm.media.entity.vod.topic.Topic;
import com.cm.media.entity.vod.topic.TopicData;

import java.util.List;

public class VodListViewModel extends ViewModel {

    private final MutableLiveData<Category> categoryLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<TopicData>> topicListLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Banner>> bannerLiveData = new MutableLiveData<>();
    private final MutableLiveData<Topic> topicLiveData = new MutableLiveData<>();

    public MutableLiveData<Category> getCategoryLiveData() {
        return categoryLiveData;
    }

    public MutableLiveData<Topic> getTopicLiveData() {
        return topicLiveData;
    }

    public MutableLiveData<List<TopicData>> getTopicListLiveData() {
        return topicListLiveData;
    }

    public MutableLiveData<List<Banner>> getBannerLiveData() {
        return bannerLiveData;
    }

    void start() {
        checkNotNull(categoryLiveData.getValue());
        int categoryId = categoryLiveData.getValue().getId();
        if (categoryId == 0 && topicListLiveData.getValue() == null) {
            //getHomeData
            loadData(1, 3, null);
        } /*else if(categoryId>0&&){
            //getListData;

        }*/
    }

    void loadData(int pageNo, int pageSize, String filters) {
        checkNotNull(categoryLiveData.getValue());
        if (categoryLiveData.getValue().getId() == 0) {
            loadHomeDataList(pageNo, pageSize);
        } else {
            loadVodDataList(pageNo, pageSize, null);
        }
    }

    private void loadVodDataList(int pageNo, int pageSize, String filters) {

    }

    private void loadHomeDataList(int pageNo, int pageSize) {

    }

    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }
}
