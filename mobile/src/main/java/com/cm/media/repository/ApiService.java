package com.cm.media.repository;

import com.cm.media.entity.Entity;
import com.cm.media.entity.category.Category;
import com.cm.media.entity.vod.Vod;
import com.cm.media.entity.vod.topic.Topic;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface ApiService {

    @GET("/fans/video/type?store=ikicker&version=1.1.8")
    Call<Entity<List<Category>>> getCategory();

    @GET("/fans/video/type?store=ikicker&version=1.1.8")
    Observable<Entity<List<Category>>> getRxCategory();

    @GET("/fans/topic/home?store=ikicker&version=1.1.8")
    Call<Entity<Topic>> getTopic(@Query("page") int pageNo, @Query("size") int pageSize);

    @GET("/fans/topic/home?&store=ikicker&version=1.1.8")
    Observable<Entity<Topic>> getRxTopic(@Query("page") int pageNo, @Query("size") int pageSize);

    @GET("/fans/search/video/type?store=ikicker&version=1.1.8")
    Call<Entity<List<Vod>>> getVodList(@Query("typeId") int typeId, @Query("valueIds") String valueIds, @Query("page") int pageNo);

    @GET("/fans/search/video/type?store=ikicker&version=1.1.8")
    Observable<Entity<List<Vod>>> getRxVodList(@Query("typeId") int typeId, @Query("valueIds") String valueIds, @Query("page") int pageNo);
}
