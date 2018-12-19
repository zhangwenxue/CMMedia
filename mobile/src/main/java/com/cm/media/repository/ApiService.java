package com.cm.media.repository;

import com.cm.media.entity.Entity;
import com.cm.media.entity.category.Category;
import com.cm.media.entity.vod.Vod;
import com.cm.media.entity.vod.VodDetail;
import com.cm.media.entity.vod.parse.ResolvedVod;
import com.cm.media.entity.vod.topic.Topic;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ApiService {

    @GET("/fans/video/type?store=ikicker&version=1.2.0")
    Call<Entity<List<Category>>> getCategory();

    @GET("/fans/video/type?store=ikicker&version=1.2.0")
    Observable<Entity<List<Category>>> getRxCategory();

    @GET("/fans/topic/home?store=ikicker&version=1.2.0")
    Call<Entity<Topic>> getTopic(@Query("page") int pageNo, @Query("size") int pageSize);

    @GET("/fans/topic/home?&store=ikicker&version=1.2.0")
    Observable<Entity<Topic>> getRxTopic(@Query("page") int pageNo, @Query("size") int pageSize);

    @GET("/fans/search/video/type?store=ikicker&version=1.2.0")
    Call<Entity<List<Vod>>> getVodList(@Query("typeId") int typeId, @Query("valueIds") String valueIds, @Query("page") int pageNo);

    @GET("/fans/search/video/type?store=ikicker&version=1.2.0")
    Observable<Entity<List<Vod>>> getRxVodList(@Query("typeId") int typeId, @Query("valueIds") String valueIds, @Query("page") int pageNo, @Query("size") int size);

    @GET("/fans/video/detail?store=ikicker&version=1.2.0")
    Observable<Entity<VodDetail>> getRxVodDetail(@Query("id") int id);

    @Headers("Content-Type:application/json")
    @POST("/fans/video/resolve?store=ikicker&version=1.2.0")
    Observable<Entity<ResolvedVod>> resolveRxVCinemaUrl(@Body RequestBody urlBody);
}
