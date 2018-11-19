package com.cm.media.repository;

import com.cm.media.entity.Entity;
import com.cm.media.entity.category.Category;
import com.cm.media.entity.vod.Vod;
import com.cm.media.entity.vod.topic.Topic;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

public class RemoteRepo {
    private static volatile RemoteRepo instance;
    private ApiService mService;


    public static RemoteRepo getInstance() {
        if (instance == null) {
            synchronized (RemoteRepo.class) {
                if (instance == null) {
                    instance = new RemoteRepo();
                }
            }
        }
        return instance;
    }

    private RemoteRepo() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.vfans.fun")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mService = retrofit.create(ApiService.class);
    }

    public Observable<Entity<List<Category>>> getRxCategory() {
        return mService.getRxCategory();
    }


    public Observable<Entity<Topic>> getRxTopic(int pageNo, int pageSize) {
        return mService.getRxTopic(pageNo, pageSize);
    }


    public Observable<Entity<List<Vod>>> getRxVodList(int typeId, String valueIds, int pageNo) {
        return mService.getRxVodList(typeId, valueIds, pageNo);
    }
}
