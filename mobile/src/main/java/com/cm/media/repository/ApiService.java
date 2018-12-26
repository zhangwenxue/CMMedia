package com.cm.media.repository;

import com.cm.media.entity.Entity;
import com.cm.media.entity.category.Category;
import com.cm.media.entity.discover.DiscoverItem;
import com.cm.media.entity.discover.Discovery;
import com.cm.media.entity.vod.Vod;
import com.cm.media.entity.vod.VodDetail;
import com.cm.media.entity.vod.parse.ResolvedVod;
import com.cm.media.entity.vod.topic.Topic;
import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ApiService {

    @GET("/fans/video/type?store=ikicker&version=1.2.0")
    Call<Entity<List<Category>>> getCategory();

    @GET("/fans/video/type?store=ikicker&version=1.2.0")
    Flowable<Entity<List<Category>>> getRxCategory();

    @GET("/fans/topic/home?store=ikicker&version=1.2.0")
    Call<Entity<Topic>> getTopic(@Query("page") int pageNo, @Query("size") int pageSize);

    @GET("/fans/topic/home?&store=ikicker&version=1.2.0")
    Flowable<Entity<Topic>> getRxTopic(@Query("page") int pageNo, @Query("size") int pageSize);

    @GET("/fans/search/video/type?store=ikicker&version=1.2.0")
    Call<Entity<List<Vod>>> getVodList(@Query("typeId") int typeId, @Query("valueIds") String valueIds, @Query("page") int pageNo);

    @GET("/fans/search/video/type?store=ikicker&version=1.2.0")
    Flowable<Entity<List<Vod>>> getRxVodList(@Query("typeId") int typeId, @Query("valueIds") String valueIds, @Query("page") int pageNo, @Query("size") int size);

    @GET("/fans/video/detail?store=ikicker&version=1.2.0")
    Flowable<Entity<VodDetail>> getRxVodDetail(@Query("id") int id);

    @GET("/fans/topic/discover?store=ikicker&version=1.2.0")
    Flowable<Entity<Discovery>> getRxDiscoveryList(@Query("page") int page, @Query("size") int size);

    @GET("/fans/topic/discover/me?store=ikicker&version=1.2.0")
    Flowable<Entity<DiscoverItem>> getRxDiscoveryItemList(@Query("page") int page, @Query("size") int size);

    @Headers("Content-Type:application/json")
    @POST("/fans/video/resolve?store=ikicker&version=1.2.0")
    Flowable<Entity<ResolvedVod>> resolveRxVCinemaUrl(@Body RequestBody urlBody);
}
