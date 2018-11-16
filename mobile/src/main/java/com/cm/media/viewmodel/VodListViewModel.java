package com.cm.media.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.cm.media.entity.category.Category;
import com.cm.media.entity.vod.topic.Banner;
import com.cm.media.entity.vod.topic.Topic;
import com.cm.media.entity.vod.topic.TopicData;

import java.util.List;

public class VodListViewModel extends ViewModel {
    private Category category;

    public void setCategory(Category category) {
        this.category = category;
    }

    void start() {

    }


    void loadData(int pageNo, int pageSize, String filters) {
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
