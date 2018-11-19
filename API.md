
首页

主页滚动图：https://www.vfans.fun/fans/topic/home?page=1&size=3&store=ikicker&version=1.1.8
https://www.vfans.fun/fans/topic/discover/me?page=1&size=15&store=ikicker&version=1.1.8
https://www.vfans.fun/fans/topic/discover?page=1&size=3&store=ikicker&version=1.1.8
类型（国家、古装/科幻/战争、时间）：https://www.vfans.fun/fans/video/type?store=ikicker&version=1.1.8
App更新：https://www.vfans.fun/fans/app/version?store=ikicker&version=1.1.8
https://www.vfans.fun/fans/topic/home?page=1&size=3&store=ikicker&version=1.1.8
https://www.vfans.fun/fans/search/video/type?typeId=1&valueIds=&page=1&store=ikicker&version=1.1.8

首页推荐下拉刷新：https://www.vfans.fun/fans/topic/home?page=1&size=3&store=ikicker&version=1.1.8
首页推荐上拉加载：
    1、https://www.vfans.fun/fans/topic/home?page=2&size=3&store=ikicker&version=1.1.8
    2、https://www.vfans.fun/fans/topic/home?page=3&size=3&store=ikicker&version=1.1.8
首页右滑 推荐->电视剧：https://www.vfans.fun/fans/search/video/type?typeId=2&valueIds=&page=1&store=ikicker&version=1.1.8
电视剧下拉：https://www.vfans.fun/fans/search/video/type?typeId=1&valueIds=&page=1&store=ikicker&version=1.1.8
电视剧上拉：https://www.vfans.fun/fans/search/video/type?typeId=1&valueIds=&page=3&store=ikicker&version=1.1.8
电视剧 查询条件“内地”：https://www.vfans.fun/fans/search/video/type?typeId=1&valueIds=153&page=1&store=ikicker&version=1.1.8
       查询条件“美国” valueIds = 156
       查询条件“韩国” valueIds = 157
       查询条件“日本” valueIds = 158
       查询条件“香港” valueIds = 154
       查询条件“泰国” valueIds = 159
       查询条件“新加坡” valueIds = 160
       查询条件“新加坡” valueIds = 160
       查询条件“全部” valueIds = 181

       查询条件“美国，古装”https://www.vfans.fun/fans/search/video/type?typeId=1&valueIds=156,10&page=1&store=ikicker&version=1.1.8
       查询条件“美国，全部”https://www.vfans.fun/fans/search/video/type?typeId=1&valueIds=156,182&page=1&store=ikicker&version=1.1.8
       查询条件“美国，古装，2018” https://www.vfans.fun/fans/search/video/type?typeId=1&valueIds=26,156,10&page=1&store=ikicker&version=1.1.8
电影首页：https://www.vfans.fun/fans/search/video/type?typeId=2&valueIds=&page=1&store=ikicker&version=1.1.8
       查询条件“美国，喜剧，2018”https://www.vfans.fun/fans/search/video/type?typeId=2&valueIds=241,151,97&page=1&store=ikicker&version=1.1.8
搜索：https://www.vfans.fun/fans/search/words?store=ikicker&version=1.1.8
美剧：https://www.vfans.fun/fans/search/video/type?typeId=10&valueIds=&page=1&store=ikicker&version=1.1.8

=======================================

video detail：https://www.vfans.fun/fans/video/detail?id=32414&store=ikicker&version=1.1.8

==========================================
发现下拉：https://www.vfans.fun/fans/topic/discover/me?page=1&size=15&store=ikicker&version=1.1.8
          https://www.vfans.fun/fans/topic/discover?page=1&size=3&store=ikicker&version=1.1.8
          https://www.vfans.fun/fans/topic/discover/me?page=2&size=15&store=ikicker&version=1.1.8
          https://www.vfans.fun/fans/topic/discover/me?page=3&size=15&store=ikicker&version=1.1.8

主题：https://www.vfans.fun/fans/topic/detail?topicId=175&store=ikicker&version=1.1.8
主题详情： http://devicemgr.hpplay.cn/tvshare/getall?hid=2ABF379686A860B7D7941523ADD19775D&uid=7462533768709434558&token=1b8b22ab18e289151541494895&appid=10546
           https://www.vfans.fun/fans/video/resolve?store=ikicker&version=1.1.8
                     ...
           https://www.vfans.fun/fans/video/detail?id=21891&store=ikicker&version=1.1.8
           https://www.vfans.fun/fans/video/resolve?store=ikicker&version=1.1.8
            {"url":"https:\/\/p.doras.api.vcinema.cn\/v5.0\/movie\/get_movie_url\/10511"}



            package cn.ikicker.moviefans.ui.activity;

            import android.app.Activity;
            import android.content.Context;
            import android.content.Intent;
            import android.content.pm.PackageInfo;
            import android.content.pm.PackageManager;
            import android.content.res.Configuration;
            import android.content.res.Resources;
            import android.graphics.Bitmap;
            import android.os.Build.VERSION;
            import android.os.Bundle;
            import android.os.CountDownTimer;
            import android.os.Environment;
            import android.os.Handler;
            import android.os.Message;
            import android.support.v4.app.FragmentActivity;
            import android.support.v4.widget.NestedScrollView;
            import android.support.v7.widget.GridLayoutManager;
            import android.support.v7.widget.LinearLayoutManager;
            import android.support.v7.widget.RecyclerView;
            import android.support.v7.widget.RecyclerView.Adapter;
            import android.support.v7.widget.RecyclerView.ItemDecoration;
            import android.support.v7.widget.RecyclerView.LayoutManager;
            import android.text.TextUtils;
            import android.view.LayoutInflater;
            import android.view.MotionEvent;
            import android.view.View;
            import android.view.View.OnClickListener;
            import android.view.View.OnTouchListener;
            import android.view.Window;
            import android.view.WindowManager.LayoutParams;
            import android.widget.ImageView;
            import android.widget.LinearLayout;
            import android.widget.RelativeLayout;
            import android.widget.SeekBar;
            import android.widget.TextView;
            import cn.ikicker.junecore.d.j;
            import cn.ikicker.junecore.d.k;
            import cn.ikicker.moviefans.R.id;
            import cn.ikicker.moviefans.db.MovieHistory;
            import cn.ikicker.moviefans.db.MovieHistoryDao;
            import cn.ikicker.moviefans.db.User;
            import cn.ikicker.moviefans.model.entity.Detail;
            import cn.ikicker.moviefans.model.entity.Play;
            import cn.ikicker.moviefans.model.entity.PlayData;
            import cn.ikicker.moviefans.model.entity.Stream;
            import cn.ikicker.moviefans.model.entity.SuggestData;
            import cn.ikicker.moviefans.model.entity.VideoData;
            import cn.ikicker.moviefans.mvp.a.q.a;
            import cn.ikicker.moviefans.mvp.a.q.b;
            import cn.ikicker.moviefans.mvp.model.PlayerModel;
            import cn.ikicker.moviefans.mvp.presenter.PlayerPresenter;
            import cn.ikicker.moviefans.ui.adapter.HalfCacheAdapter;
            import cn.ikicker.moviefans.ui.adapter.PlayerEpisodeAdapter;
            import cn.ikicker.moviefans.ui.adapter.PlayerGridEpisodeAdapter;
            import cn.ikicker.moviefans.ui.adapter.PlayerRecommendAdapter;
            import cn.ikicker.moviefans.util.a.b.b;
            import cn.ikicker.moviefans.util.c.a;
            import cn.ikicker.moviefans.util.d.c;
            import cn.ikicker.shareloginlib.content.ShareContent;
            import cn.ikicker.shareloginlib.content.ShareContentWebPage;
            import cn.ikicker.shareloginlib.e.b;
            import cn.jzvd.custom.view.IkickerJZVideoPlayerStandard;
            import cn.jzvd.custom.view.IkickerJZVideoPlayerStandard.a;
            import cn.jzvd.custom.view.IkickerJZVideoPlayerStandard.d;
            import cn.jzvd.custom.view.IkickerJZVideoPlayerStandard.e;
            import cn.jzvd.custom.view.VideoPlay;
            import cn.jzvd.upnp.a.c;
            import com.bumptech.glide.BitmapRequestBuilder;
            import com.bumptech.glide.BitmapTypeRequest;
            import com.bumptech.glide.DrawableTypeRequest;
            import com.bumptech.glide.Glide;
            import com.bumptech.glide.RequestManager;
            import com.bumptech.glide.request.FutureTarget;
            import io.reactivex.Observable;
            import io.reactivex.ObservableEmitter;
            import io.reactivex.ObservableOnSubscribe;
            import io.reactivex.android.schedulers.AndroidSchedulers;
            import io.reactivex.functions.Consumer;
            import io.reactivex.schedulers.Schedulers;
            import java.text.DateFormat;
            import java.text.SimpleDateFormat;
            import java.util.Arrays;
            import java.util.Collection;
            import java.util.HashMap;
            import java.util.Iterator;
            import java.util.LinkedHashMap;
            import java.util.List;
            import kotlin.Metadata;
            import kotlin.Pair;
            import kotlin.TypeCastException;
            import kotlin.Unit;
            import kotlin.jvm.functions.Function0;
            import kotlin.jvm.functions.Function1;
            import kotlin.jvm.internal.Intrinsics;
            import kotlin.jvm.internal.Lambda;
            import kotlin.jvm.internal.Ref.ObjectRef;
            import kotlin.jvm.internal.StringCompanionObject;
            import kotlin.text.StringsKt;
            import org.json.JSONObject;

            @Metadata(bv={1, 0, 2}, d1={""}, d2={"Lcn/ikicker/moviefans/ui/activity/PlayerActivity;", "Lcn/ikicker/moviefans/app/BaseActivity;", "Lcn/ikicker/moviefans/mvp/presenter/PlayerPresenter;", "Lcn/ikicker/moviefans/mvp/contract/PlayerContract$View;", "()V", "countDownTimer", "Landroid/os/CountDownTimer;", "episodeAdapter", "Lcn/ikicker/moviefans/ui/adapter/PlayerEpisodeAdapter;", "getEpisodeAdapter", "()Lcn/ikicker/moviefans/ui/adapter/PlayerEpisodeAdapter;", "setEpisodeAdapter", "(Lcn/ikicker/moviefans/ui/adapter/PlayerEpisodeAdapter;)V", "filterPlayerCount", "", "funcstr", "", "handler", "Landroid/os/Handler;", "getHandler", "()Landroid/os/Handler;", "setHandler", "(Landroid/os/Handler;)V", "infoObjects", "", "", "getInfoObjects", "()[Ljava/lang/Object;", "setInfoObjects", "([Ljava/lang/Object;)V", "[Ljava/lang/Object;", "m3u8Server", "Lcn/jzvd/m3u8/server/M3U8HttpServer;", "getM3u8Server", "()Lcn/jzvd/m3u8/server/M3U8HttpServer;", "name", "getName", "()Ljava/lang/String;", "setName", "(Ljava/lang/String;)V", "objects", "getObjects", "setObjects", "parseSourceArr", "[Ljava/lang/String;", "playNum", "playUrl", "pullCacheView", "Landroid/view/View;", "pullEpisodeView", "pullInfoView", "timeout", "trytime", "videoDetail", "Lcn/ikicker/moviefans/model/entity/VideoData;", "getVideoDetail", "()Lcn/ikicker/moviefans/model/entity/VideoData;", "setVideoDetail", "(Lcn/ikicker/moviefans/model/entity/VideoData;)V", "cancelTimer", "", "dissLoading", "filterByJs", "url", "videoId", "type", "fullScreen", "getContx", "Landroid/content/Context;", "getDetail", "id", "initData", "savedInstanceState", "Landroid/os/Bundle;", "initEpisodeListView", "plays", "", "Lcn/jzvd/custom/view/VideoPlay;", "initPresenter", "initPullCacheView", "initPullEpisodeView", "initPullInfoView", "initRecommendListView", "data", "Lcn/ikicker/moviefans/model/entity/SuggestData;", "initVideoPlayer", "initView", "initcast", "insertMovieHis", "killMyself", "likeVideo", "onBackPressed", "onConfigurationChanged", "newConfig", "Landroid/content/res/Configuration;", "onDestroy", "onPause", "onResume", "onWindowFocusChanged", "hasFocus", "", "parseRealPlayUrl", "popAnim", "preOnCreate", "prepareVideo", "pushAnim", "scrollTop", "scrollViewToTop", "sendUrlToPlayer", "setFollow", "flag", "setPlayUrl", "videoPlay", "setVideoData", "share", "showCacheView", "showEpisodeListView", "showInfoView", "showLoading", "showMessage", "message", "startTime", "Companion", "app_ikickerRelease"}, k=1, mv={1, 1, 7})
            public final class PlayerActivity extends cn.ikicker.moviefans.app.a<PlayerPresenter>
              implements q.b
            {
              public static final a a = new a(null);
              private String b = "";
              private VideoData c;
              private Object[] d = new Object[2];
              private Object[] e = new Object[6];
              private PlayerEpisodeAdapter f;
              private int g;
              private int h;
              private String i = "";
              private int j = 5;
              private int k = 2;
              private CountDownTimer l;
              private String m = "";
              private View n;
              private View o;
              private View p;
              private final cn.jzvd.m3u8.c.a q = new cn.jzvd.m3u8.c.a();
              private String[] r = (String[])new String[] { "http://vip.jlsprh.com/index.php?url=", "http://www.1717yun.com/jx/ty.php?url=", "http://www.itono.cn/vip/xlyy.php?url=" };
              private Handler s = (Handler)new b(this);
              private HashMap t;

              private final void A()
              {
                // Byte code:
                //   0: aload_0
                //   1: new 60	cn/ikicker/moviefans/ui/activity/PlayerActivity$likeVideo$1
                //   4: dup
                //   5: aload_0
                //   6: invokespecial 297	cn/ikicker/moviefans/ui/activity/PlayerActivity$likeVideo$1:<init>	(Lcn/ikicker/moviefans/ui/activity/PlayerActivity;)V
                //   9: checkcast 299	kotlin/jvm/functions/Function1
                //   12: new 62	cn/ikicker/moviefans/ui/activity/PlayerActivity$likeVideo$2
                //   15: dup
                //   16: aload_0
                //   17: invokespecial 300	cn/ikicker/moviefans/ui/activity/PlayerActivity$likeVideo$2:<init>	(Lcn/ikicker/moviefans/ui/activity/PlayerActivity;)V
                //   20: checkcast 302	kotlin/jvm/functions/Function0
                //   23: invokevirtual 305	cn/ikicker/moviefans/ui/activity/PlayerActivity:a	(Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function0;)V
                //   26: return
              }

              private final void B()
              {
                if (this.c != null)
                  try
                  {
                    MovieHistory localMovieHistory = new cn/ikicker/moviefans/db/MovieHistory;
                    localMovieHistory.<init>();
                    Object localObject = this.c;
                    if (localObject == null)
                      Intrinsics.throwNpe();
                    localMovieHistory.setCover(((VideoData)localObject).getImg());
                    localObject = new java/text/SimpleDateFormat;
                    ((SimpleDateFormat)localObject).<init>("yyyy-MM-dd");
                    localMovieHistory.setDatetime(com.blankj.utilcode.util.b.a((DateFormat)localObject));
                    localObject = this.c;
                    if (localObject == null)
                      Intrinsics.throwNpe();
                    localMovieHistory.setMovidId(((VideoData)localObject).getId());
                    localObject = this.c;
                    if (localObject == null)
                      Intrinsics.throwNpe();
                    localMovieHistory.setName(((VideoData)localObject).getName());
                    localMovieHistory.setSelected(0);
                    localMovieHistory.setPercent(((IkickerJZVideoPlayerStandard)a(R.id.jzVideo)).M.getProgress());
                    cn.ikicker.moviefans.db.a.a().d().d(localMovieHistory);
                    return;
                  }
                  catch (Exception localException)
                  {
                    localException.printStackTrace();
                  }
              }

              private final void C()
              {
                float f1 = k.a(328.0F);
                cn.ikicker.moviefans.util.a.c.a(new View[] { (View)(RelativeLayout)a(R.id.playerContent) }).b(new float[] { f1, 0.0F }).a(250L).a((cn.ikicker.moviefans.util.a.b.a)w.a).a((b.b)new x(this)).c();
              }

              private final void D()
              {
                ((RelativeLayout)a(R.id.movieHanle)).setVisibility(0);
                float f1 = k.a(328.0F);
                cn.ikicker.moviefans.util.a.c.a(new View[] { (View)(RelativeLayout)a(R.id.playerContent) }).b(new float[] { 0.0F, f1 }).a(250L).a((cn.ikicker.moviefans.util.a.b.a)u.a).a((b.b)new v(this)).c();
              }

              private final void E()
              {
                new Handler().postDelayed((Runnable)new z(this), 250L);
              }

              private final void F()
              {
                WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
                localLayoutParams.flags |= 1024;
                getWindow().setAttributes(localLayoutParams);
                getWindow().addFlags(512);
              }

              private final void G()
              {
                IkickerJZVideoPlayerStandard.o.clear();
                cn.jzvd.upnp.a.a().a((Activity)this, (a.c)new s());
              }

              private final void a(VideoPlay paramVideoPlay, final int paramInt1, final int paramInt2)
              {
                Object localObject1 = this.c;
                Object localObject2;
                if ((localObject1 != null) && (((VideoData)localObject1).getType() == 1))
                {
                  localObject2 = this.e;
                  localObject1 = new StringBuilder();
                  ((StringBuilder)localObject1).append("");
                  ((StringBuilder)localObject1).append(this.b);
                  ((StringBuilder)localObject1).append("   第");
                  ((StringBuilder)localObject1).append(paramVideoPlay.episode);
                  ((StringBuilder)localObject1).append(38598);
                  localObject2[0] = ((StringBuilder)localObject1).toString();
                }
                else
                {
                  this.e[0] = this.b;
                }
                localObject1 = cn.ikicker.moviefans.util.c.a.a().a(paramInt1);
                if (TextUtils.isEmpty((CharSequence)localObject1))
                {
                  cn.jzvd.a.g.setMediaInterface((cn.jzvd.a.a)new cn.jzvd.custom.a.a());
                  this.k = 2;
                  this.j = 5;
                  IkickerJZVideoPlayerStandard.s = paramVideoPlay.playUrl;
                  localObject1 = this.c;
                  if ((localObject1 != null) && (((VideoData)localObject1).getSource() == 0))
                  {
                    localObject2 = StringsKt.split$default((CharSequence)paramVideoPlay.playUrl, new String[] { "/" }, false, 0, 6, null);
                    StringBuffer localStringBuffer = new StringBuffer();
                    int i2 = ((List)localObject2).size();
                    for (int i1 = 3; i1 < i2; i1++)
                    {
                      if (i1 == ((List)localObject2).size() - 1);
                      for (localObject1 = (String)((List)localObject2).get(i1); ; localObject1 = ((StringBuilder)localObject1).toString())
                      {
                        localStringBuffer.append((String)localObject1);
                        break;
                        localObject1 = new StringBuilder();
                        ((StringBuilder)localObject1).append((String)((List)localObject2).get(i1));
                        ((StringBuilder)localObject1).append("/");
                      }
                    }
                    localObject1 = localStringBuffer.toString();
                    long l1 = System.currentTimeMillis() / 1000;
                    localObject2 = new StringBuilder();
                    ((StringBuilder)localObject2).append("");
                    ((StringBuilder)localObject2).append("zToHXm3h7zzmnL");
                    ((StringBuilder)localObject2).append('|');
                    ((StringBuilder)localObject2).append(l1);
                    ((StringBuilder)localObject2).append('|');
                    ((StringBuilder)localObject2).append((String)localObject1);
                    localObject2 = cn.ikicker.junecore.d.g.a(((StringBuilder)localObject2).toString());
                    localObject1 = new StringBuilder();
                    ((StringBuilder)localObject1).append("");
                    ((StringBuilder)localObject1).append(paramVideoPlay.playUrl);
                    ((StringBuilder)localObject1).append("?stTime=");
                    ((StringBuilder)localObject1).append(l1);
                    ((StringBuilder)localObject1).append("&token=");
                    ((StringBuilder)localObject1).append((String)localObject2);
                    paramVideoPlay = ((StringBuilder)localObject1).toString();
                    break label770;
                  }
                  localObject1 = this.c;
                  if ((localObject1 != null) && (((VideoData)localObject1).getSource() == 7))
                  {
                    localObject1 = new JSONObject();
                    ((JSONObject)localObject1).put("url", paramVideoPlay.playUrl);
                    paramVideoPlay = d();
                    if (paramVideoPlay == null)
                      Intrinsics.throwNpe();
                    paramVideoPlay = (PlayerPresenter)paramVideoPlay;
                    localObject1 = ((JSONObject)localObject1).toString();
                    Intrinsics.checkExpressionValueIsNotNull(localObject1, "json.toString()");
                    paramVideoPlay.a((String)localObject1, (Function1)new Lambda(paramInt1)
                    {
                      public final void a(PlayData paramAnonymousPlayData)
                      {
                        if ((paramAnonymousPlayData != null) && (paramAnonymousPlayData.getStreams() != null) && (paramAnonymousPlayData.getStreams().size() > 0))
                        {
                          paramAnonymousPlayData = ((Iterable)paramAnonymousPlayData.getStreams()).iterator();
                          while (paramAnonymousPlayData.hasNext())
                          {
                            Object localObject = (Stream)paramAnonymousPlayData.next();
                            if (((Stream)localObject).getPlay_list() != null)
                              if (((Stream)localObject).getPlay_list().size() == 1)
                              {
                                PlayerActivity.a(this.a, ((Play)((Stream)localObject).getPlay_list().get(0)).getUrl(), paramInt1, paramInt2);
                              }
                              else
                              {
                                Iterator localIterator = ((Iterable)((Stream)localObject).getPlay_list()).iterator();
                                while (localIterator.hasNext())
                                {
                                  localObject = (Play)localIterator.next();
                                  if (((Play)localObject).getDefault() == 1)
                                    PlayerActivity.a(this.a, ((Play)localObject).getUrl(), paramInt1, paramInt2);
                                }
                              }
                          }
                        }
                      }
                    });
                    return;
                  }
                  localObject1 = this.c;
                  if (((localObject1 != null) && (((VideoData)localObject1).getSource() == 8)) || (StringsKt.contains$default((CharSequence)paramVideoPlay.playUrl, (CharSequence)"m3u", false, 2, null)))
                  {
                    paramVideoPlay = paramVideoPlay.playUrl;
                    localObject1 = "videoPlay.playUrl";
                  }
                  else
                  {
                    localObject1 = new StringBuilder();
                    ((StringBuilder)localObject1).append(this.r[this.k]);
                    ((StringBuilder)localObject1).append((String)StringsKt.split$default((CharSequence)paramVideoPlay.playUrl, new String[] { "?" }, false, 0, 6, null).get(0));
                    c(((StringBuilder)localObject1).toString(), paramInt1, paramInt2);
                    paramVideoPlay = paramVideoPlay.playUrl;
                    Intrinsics.checkExpressionValueIsNotNull(paramVideoPlay, "videoPlay.playUrl");
                    b(paramVideoPlay, paramInt1, paramInt2);
                  }
                }
                else
                {
                  this.q.a();
                  paramVideoPlay = this.q.a((String)localObject1);
                  cn.jzvd.a.g.setMediaInterface((cn.jzvd.a.a)new cn.jzvd.custom.a.a());
                  cn.ikicker.junecore.d.b.b("serverUrl", paramVideoPlay);
                  localObject1 = "serverUrl";
                }
                Intrinsics.checkExpressionValueIsNotNull(paramVideoPlay, (String)localObject1);
                label770: d(paramVideoPlay, paramInt1, paramInt2);
              }

              private final void a(final List<VideoPlay> paramList)
              {
                IkickerJZVideoPlayerStandard.q = this.h;
                cn.jzvd.a.g.z = true;
                cn.jzvd.a.g.x = 6;
                cn.jzvd.a.g.y = 1;
                Object localObject = new LinkedHashMap();
                this.d[0] = localObject;
                this.d[1] = Boolean.valueOf(false);
                this.e[1] = paramList;
                localObject = new Ref.ObjectRef();
                ((Ref.ObjectRef)localObject).element = ((VideoPlay)paramList.get(this.h));
                a((VideoPlay)((Ref.ObjectRef)localObject).element, ((VideoPlay)((Ref.ObjectRef)localObject).element).id, 0);
                ((IkickerJZVideoPlayerStandard)a(R.id.jzVideo)).a((Activity)this);
                ((IkickerJZVideoPlayerStandard)a(R.id.jzVideo)).setShowBackListener((IkickerJZVideoPlayerStandard.e)new p(this));
                IkickerJZVideoPlayerStandard.u = (IkickerJZVideoPlayerStandard.d)new q(this);
                IkickerJZVideoPlayerStandard.t = (IkickerJZVideoPlayerStandard.a)new r(this, paramList, (Ref.ObjectRef)localObject);
              }

              private final void b(final String paramString, final int paramInt1, final int paramInt2)
              {
                this.l = ((CountDownTimer)new ae(this, paramString, paramInt1, paramInt2, this.j * 1000, 1000L));
                paramString = this.l;
                if (paramString != null)
                  paramString.start();
              }

              private final void b(final List<VideoPlay> paramList)
              {
                ((RecyclerView)a(R.id.episodeListView)).setLayoutManager((RecyclerView.LayoutManager)new LinearLayoutManager((Context)this, 0, false));
                this.f = new PlayerEpisodeAdapter(paramList);
                ((RecyclerView)a(R.id.episodeListView)).setAdapter((RecyclerView.Adapter)this.f);
                PlayerEpisodeAdapter localPlayerEpisodeAdapter = this.f;
                if (localPlayerEpisodeAdapter == null)
                  Intrinsics.throwNpe();
                localPlayerEpisodeAdapter.a(this.h);
                localPlayerEpisodeAdapter = this.f;
                if (localPlayerEpisodeAdapter == null)
                  Intrinsics.throwNpe();
                localPlayerEpisodeAdapter.a((Function1)new Lambda(paramList)
                {
                  public final void a(int paramAnonymousInt)
                  {
                    PlayerActivity.f(this.a);
                    Object localObject = (VideoPlay)paramList.get(paramAnonymousInt);
                    PlayerActivity.a(this.a, (VideoPlay)localObject, ((VideoPlay)localObject).id, 1);
                    IkickerJZVideoPlayerStandard.q = paramAnonymousInt;
                    localObject = this.a.p();
                    if (localObject == null)
                      Intrinsics.throwNpe();
                    ((PlayerEpisodeAdapter)localObject).a(paramAnonymousInt);
                    localObject = this.a.p();
                    if (localObject == null)
                      Intrinsics.throwNpe();
                    ((PlayerEpisodeAdapter)localObject).notifyDataSetChanged();
                  }
                });
              }

              private final void c(String paramString, final int paramInt1, final int paramInt2)
              {
                StringBuilder localStringBuilder = new StringBuilder();
                localStringBuilder.append("url: ");
                localStringBuilder.append(paramString);
                cn.ikicker.junecore.d.b.b("parseRealPlayUrl---", localStringBuilder.toString());
                this.g = 0;
                if (paramInt2 != 2)
                {
                  Glide.with((Context)getApplication()).load(Integer.valueOf(2131558440)).into(((IkickerJZVideoPlayerStandard)a(R.id.jzVideo)).aP);
                  ((IkickerJZVideoPlayerStandard)a(R.id.jzVideo)).l();
                }
                cn.ikicker.moviefans.util.d.a().a(paramString).a((d.c)new t(this, paramInt1, paramInt2)).b();
              }

              private final void c(final List<SuggestData> paramList)
              {
                ((RecyclerView)a(R.id.recommendListView)).setLayoutManager((RecyclerView.LayoutManager)new LinearLayoutManager((Context)this, 1, false));
                PlayerRecommendAdapter localPlayerRecommendAdapter = new PlayerRecommendAdapter(paramList);
                ((RecyclerView)a(R.id.recommendListView)).setAdapter((RecyclerView.Adapter)localPlayerRecommendAdapter);
                localPlayerRecommendAdapter.a((Function1)new Lambda(paramList)
                {
                  public final void a(int paramAnonymousInt)
                  {
                    ((NestedScrollView)this.a.a(R.id.bottomScrollLay)).setVisibility(8);
                    cn.jzvd.a.g.F();
                    PlayerActivity.c(this.a, ((SuggestData)paramList.get(paramAnonymousInt)).getId());
                  }
                });
              }

              private final void d(final int paramInt)
              {
                h();
                a((Function1)new Lambda(paramInt)
                {
                  public final void a(User paramAnonymousUser)
                  {
                    Intrinsics.checkParameterIsNotNull(paramAnonymousUser, "user");
                    PlayerPresenter localPlayerPresenter = PlayerActivity.e(this.a);
                    if (localPlayerPresenter == null)
                      Intrinsics.throwNpe();
                    int i = paramInt;
                    StringBuilder localStringBuilder = new StringBuilder();
                    localStringBuilder.append("Bearer ");
                    localStringBuilder.append(paramAnonymousUser.token);
                    localPlayerPresenter.a(i, localStringBuilder.toString());
                  }
                }
                , (Function0)new Lambda(paramInt)
                {
                  public final void a()
                  {
                    PlayerPresenter localPlayerPresenter = PlayerActivity.e(this.a);
                    if (localPlayerPresenter == null)
                      Intrinsics.throwNpe();
                    localPlayerPresenter.a(paramInt);
                  }
                });
              }

              private final void d(String paramString, int paramInt1, int paramInt2)
              {
                Message localMessage = new Message();
                localMessage.what = 1;
                localMessage.obj = paramString;
                localMessage.arg1 = paramInt2;
                localMessage.arg2 = paramInt1;
                this.s.sendMessage(localMessage);
              }

              private final void s()
              {
                Object localObject2 = getLayoutInflater();
                Object localObject1 = null;
                this.n = ((LayoutInflater)localObject2).inflate(2131427491, null);
                if (this.c != null)
                {
                  localObject2 = this.n;
                  if (localObject2 != null)
                  {
                    localObject3 = (TextView)((View)localObject2).findViewById(2131296605);
                    if (localObject3 != null)
                    {
                      localObject2 = this.c;
                      if (localObject2 == null)
                        Intrinsics.throwNpe();
                      ((TextView)localObject3).setText((CharSequence)((VideoData)localObject2).getName());
                    }
                  }
                  localObject2 = this.n;
                  if (localObject2 != null)
                  {
                    localObject3 = (TextView)((View)localObject2).findViewById(2131296602);
                    if (localObject3 != null)
                    {
                      StringBuilder localStringBuilder = new StringBuilder();
                      localStringBuilder.append('(');
                      localObject2 = this.c;
                      if (localObject2 == null)
                        Intrinsics.throwNpe();
                      localStringBuilder.append(((VideoData)localObject2).getPlays().size());
                      localStringBuilder.append("全)");
                      ((TextView)localObject3).setText((CharSequence)localStringBuilder.toString());
                    }
                  }
                  localObject2 = this.n;
                  if (localObject2 != null)
                    localObject1 = (RecyclerView)((View)localObject2).findViewById(2131296455);
                  if (localObject1 != null)
                    ((RecyclerView)localObject1).setLayoutManager((RecyclerView.LayoutManager)new GridLayoutManager((Context)this, 6));
                  if (localObject1 != null)
                    ((RecyclerView)localObject1).addItemDecoration((RecyclerView.ItemDecoration)cn.ikicker.moviefans.ui.widget.a.a().a(false).b(k.a(5.0F)).a(k.a(5.0F)).a());
                  localObject2 = new Ref.ObjectRef();
                  Object localObject3 = this.c;
                  if (localObject3 == null)
                    Intrinsics.throwNpe();
                  ((Ref.ObjectRef)localObject2).element = new PlayerGridEpisodeAdapter(((VideoData)localObject3).getPlays(), IkickerJZVideoPlayerStandard.q);
                  if (localObject1 != null)
                    ((RecyclerView)localObject1).setAdapter((RecyclerView.Adapter)((Ref.ObjectRef)localObject2).element);
                  ((PlayerGridEpisodeAdapter)((Ref.ObjectRef)localObject2).element).a((Function1)new Lambda((Ref.ObjectRef)localObject2)
                  {
                    public final void a(int paramAnonymousInt)
                    {
                      PlayerActivity.f(this.a);
                      Object localObject = this.a.m();
                      if (localObject == null)
                        Intrinsics.throwNpe();
                      localObject = (VideoPlay)((VideoData)localObject).getPlays().get(paramAnonymousInt);
                      PlayerActivity.a(this.a, (VideoPlay)localObject, ((VideoPlay)localObject).id, 1);
                      IkickerJZVideoPlayerStandard.q = paramAnonymousInt;
                      ((PlayerGridEpisodeAdapter)this.b.element).a(paramAnonymousInt);
                      ((PlayerGridEpisodeAdapter)this.b.element).notifyDataSetChanged();
                      localObject = this.a.p();
                      if (localObject == null)
                        Intrinsics.throwNpe();
                      ((PlayerEpisodeAdapter)localObject).a(paramAnonymousInt);
                      localObject = this.a.p();
                      if (localObject == null)
                        Intrinsics.throwNpe();
                      ((PlayerEpisodeAdapter)localObject).notifyDataSetChanged();
                      PlayerActivity.g(this.a);
                    }
                  });
                }
                localObject1 = this.n;
                if (localObject1 != null)
                {
                  localObject1 = (RelativeLayout)((View)localObject1).findViewById(2131296401);
                  if (localObject1 != null)
                    ((RelativeLayout)localObject1).setOnClickListener((View.OnClickListener)new n(this));
                }
              }

              private final void t()
              {
                Object localObject1 = getLayoutInflater();
                final Ref.ObjectRef localObjectRef = null;
                this.o = ((LayoutInflater)localObject1).inflate(2131427490, null);
                localObject1 = this.o;
                if (localObject1 != null)
                  localObject1 = (RecyclerView)((View)localObject1).findViewById(2131296330);
                else
                  localObject1 = null;
                if (localObject1 != null)
                  ((RecyclerView)localObject1).setLayoutManager((RecyclerView.LayoutManager)new GridLayoutManager((Context)this, 6));
                if (localObject1 != null)
                  ((RecyclerView)localObject1).addItemDecoration((RecyclerView.ItemDecoration)cn.ikicker.moviefans.ui.widget.a.a().a(false).b(k.a(5.0F)).a(k.a(5.0F)).a());
                if (this.c != null)
                {
                  localObject2 = this.c;
                  if (localObject2 != null)
                    localObject2 = ((VideoData)localObject2).getPlays();
                  else
                    localObject2 = null;
                  if (localObject2 != null)
                  {
                    localObject2 = this.c;
                    if (localObject2 != null)
                      localObject2 = ((VideoData)localObject2).getPlays();
                    else
                      localObject2 = null;
                    if (localObject2 == null)
                      Intrinsics.throwNpe();
                    if (((List)localObject2).size() > 0)
                    {
                      localObject3 = new Ref.ObjectRef();
                      localObject2 = this.c;
                      if (localObject2 != null)
                        localObject2 = ((VideoData)localObject2).getPlays();
                      else
                        localObject2 = null;
                      ((Ref.ObjectRef)localObject3).element = new HalfCacheAdapter((List)localObject2);
                      if (localObject1 != null)
                        ((RecyclerView)localObject1).setAdapter((RecyclerView.Adapter)((Ref.ObjectRef)localObject3).element);
                      ((HalfCacheAdapter)((Ref.ObjectRef)localObject3).element).a((Function1)new Lambda((Ref.ObjectRef)localObject3)
                      {
                        public final void a(int paramAnonymousInt)
                        {
                          Object localObject2 = cn.ikicker.moviefans.util.c.a.a();
                          Object localObject1 = this.a.m();
                          if (localObject1 == null)
                            Intrinsics.throwNpe();
                          if (((cn.ikicker.moviefans.util.c)localObject2).b(((VideoPlay)((VideoData)localObject1).getPlays().get(paramAnonymousInt)).id) == null)
                          {
                            localObject1 = this.a.m();
                            if (localObject1 == null)
                              Intrinsics.throwNpe();
                            Object localObject3;
                            if (((VideoData)localObject1).getType() == 1)
                            {
                              localObject1 = cn.ikicker.moviefans.util.c.a.a();
                              localObject2 = this.a.m();
                              if (localObject2 == null)
                                Intrinsics.throwNpe();
                              localObject3 = this.a.m();
                              if (localObject3 == null)
                                Intrinsics.throwNpe();
                              VideoPlay localVideoPlay = (VideoPlay)((VideoData)localObject3).getPlays().get(paramAnonymousInt);
                              localObject3 = new StringBuilder();
                              ((StringBuilder)localObject3).append("");
                              ((StringBuilder)localObject3).append('第');
                              ((StringBuilder)localObject3).append(paramAnonymousInt + 1);
                              ((StringBuilder)localObject3).append(38598);
                              ((cn.ikicker.moviefans.util.c)localObject1).a((VideoData)localObject2, localVideoPlay, ((StringBuilder)localObject3).toString());
                            }
                            else
                            {
                              localObject3 = cn.ikicker.moviefans.util.c.a.a();
                              localObject1 = this.a.m();
                              if (localObject1 == null)
                                Intrinsics.throwNpe();
                              localObject2 = this.a.m();
                              if (localObject2 == null)
                                Intrinsics.throwNpe();
                              ((cn.ikicker.moviefans.util.c)localObject3).a((VideoData)localObject1, (VideoPlay)((VideoData)localObject2).getPlays().get(paramAnonymousInt), "");
                            }
                          }
                          ((HalfCacheAdapter)this.b.element).notifyDataSetChanged();
                        }
                      });
                    }
                  }
                }
                localObject1 = this.o;
                if (localObject1 != null)
                {
                  localObject1 = (RelativeLayout)((View)localObject1).findViewById(2131296401);
                  if (localObject1 != null)
                    ((RelativeLayout)localObject1).setOnClickListener((View.OnClickListener)new j(this));
                }
                Object localObject2 = new Ref.ObjectRef();
                localObject1 = this.o;
                if (localObject1 != null)
                  localObject1 = (TextView)((View)localObject1).findViewById(2131296672);
                else
                  localObject1 = null;
                ((Ref.ObjectRef)localObject2).element = localObject1;
                Object localObject3 = this.o;
                localObject1 = localObjectRef;
                if (localObject3 != null)
                  localObject1 = (RelativeLayout)((View)localObject3).findViewById(2131296664);
                localObjectRef = new Ref.ObjectRef();
                localObjectRef.element = new cn.ikicker.moviefans.ui.widget.e((Activity)this);
                ((cn.ikicker.moviefans.ui.widget.e)localObjectRef.element).s();
                ((cn.ikicker.moviefans.ui.widget.e)localObjectRef.element).a((Function1)new Lambda(localObjectRef)
                {
                  public final void a(int paramAnonymousInt)
                  {
                    TextView localTextView = (TextView)this.a.element;
                    if (localTextView != null)
                      localTextView.setText((CharSequence)((cn.ikicker.moviefans.ui.widget.e)localObjectRef.element).r().get(paramAnonymousInt));
                  }
                });
                if (localObject1 != null)
                  ((RelativeLayout)localObject1).setOnClickListener((View.OnClickListener)new k(localObjectRef));
                ((TextView)a(R.id.cacheAll)).setOnClickListener((View.OnClickListener)l.a);
                ((LinearLayout)a(R.id.lookCache)).setOnClickListener((View.OnClickListener)new m(this));
              }

              private final void u()
              {
                this.p = getLayoutInflater().inflate(2131427492, null);
                if (this.c != null)
                {
                  localObject1 = this.p;
                  Object localObject2;
                  if (localObject1 != null)
                  {
                    localObject1 = (TextView)((View)localObject1).findViewById(2131296605);
                    if (localObject1 != null)
                    {
                      localObject2 = this.c;
                      if (localObject2 == null)
                        Intrinsics.throwNpe();
                      ((TextView)localObject1).setText((CharSequence)((VideoData)localObject2).getName());
                    }
                  }
                  localObject1 = this.c;
                  if (localObject1 == null)
                    Intrinsics.throwNpe();
                  if (((VideoData)localObject1).getDetail() != null)
                  {
                    localObject1 = this.c;
                    if (localObject1 == null)
                      Intrinsics.throwNpe();
                    localObject1 = ((VideoData)localObject1).getDetail();
                    localObject2 = this.p;
                    if (localObject2 != null)
                    {
                      TextView localTextView = (TextView)((View)localObject2).findViewById(2131296651);
                      if (localTextView != null)
                      {
                        localObject2 = new StringBuilder();
                        ((StringBuilder)localObject2).append('(');
                        ((StringBuilder)localObject2).append(((Detail)localObject1).getEpisodeNum());
                        ((StringBuilder)localObject2).append("全)");
                        localTextView.setText((CharSequence)((StringBuilder)localObject2).toString());
                      }
                    }
                    if (TextUtils.isEmpty((CharSequence)((Detail)localObject1).getDirector()))
                    {
                      localObject2 = this.p;
                      if (localObject2 != null)
                      {
                        localObject2 = (RelativeLayout)((View)localObject2).findViewById(2131296432);
                        if (localObject2 != null)
                          ((RelativeLayout)localObject2).setVisibility(8);
                      }
                    }
                    else
                    {
                      localObject2 = this.p;
                      if (localObject2 != null)
                      {
                        localObject2 = (TextView)((View)localObject2).findViewById(2131296431);
                        if (localObject2 != null)
                          ((TextView)localObject2).setText((CharSequence)((Detail)localObject1).getDirector());
                      }
                    }
                    if (TextUtils.isEmpty((CharSequence)((Detail)localObject1).getActor()))
                    {
                      localObject2 = this.p;
                      if (localObject2 != null)
                      {
                        localObject2 = (RelativeLayout)((View)localObject2).findViewById(2131296588);
                        if (localObject2 != null)
                          ((RelativeLayout)localObject2).setVisibility(8);
                      }
                    }
                    else
                    {
                      localObject2 = this.p;
                      if (localObject2 != null)
                      {
                        localObject2 = (TextView)((View)localObject2).findViewById(2131296587);
                        if (localObject2 != null)
                          ((TextView)localObject2).setText((CharSequence)((Detail)localObject1).getActor());
                      }
                    }
                    if (TextUtils.isEmpty((CharSequence)((Detail)localObject1).getTagText()))
                    {
                      localObject2 = this.p;
                      if (localObject2 != null)
                      {
                        localObject2 = (RelativeLayout)((View)localObject2).findViewById(2131296378);
                        if (localObject2 != null)
                          ((RelativeLayout)localObject2).setVisibility(8);
                      }
                    }
                    else
                    {
                      localObject2 = this.p;
                      if (localObject2 != null)
                      {
                        localObject2 = (TextView)((View)localObject2).findViewById(2131296377);
                        if (localObject2 != null)
                          ((TextView)localObject2).setText((CharSequence)((Detail)localObject1).getTagText());
                      }
                    }
                    if (localObject1 == null)
                      Intrinsics.throwNpe();
                    if (!TextUtils.isEmpty((CharSequence)((Detail)localObject1).getSummary()))
                    {
                      localObject2 = this.p;
                      if (localObject2 != null)
                      {
                        localObject2 = (TextView)((View)localObject2).findViewById(2131296530);
                        if (localObject2 != null)
                          ((TextView)localObject2).setText((CharSequence)((Detail)localObject1).getSummary());
                      }
                    }
                    else
                    {
                      localObject1 = this.p;
                      if (localObject1 != null)
                      {
                        localObject1 = (TextView)((View)localObject1).findViewById(2131296530);
                        if (localObject1 != null)
                          ((TextView)localObject1).setVisibility(8);
                      }
                    }
                  }
                }
                Object localObject1 = this.p;
                if (localObject1 != null)
                {
                  localObject1 = (RelativeLayout)((View)localObject1).findViewById(2131296401);
                  if (localObject1 != null)
                    ((RelativeLayout)localObject1).setOnClickListener((View.OnClickListener)new o(this));
                }
              }

              private final void v()
              {
                try
                {
                  if ((cn.jzvd.a.b.a().f != null) && (cn.jzvd.a.b.a().f.g()))
                    cn.jzvd.a.b.a().f.h();
                  ((IkickerJZVideoPlayerStandard)a(R.id.jzVideo)).C();
                  return;
                }
                catch (Exception localException)
                {
                  localException.printStackTrace();
                }
              }

              private final void w()
              {
                new Handler().postDelayed((Runnable)new y(this), 368L);
              }

              private final void x()
              {
                ((LinearLayout)a(R.id.cacheInfoLayout)).setVisibility(8);
                ((RelativeLayout)a(R.id.playerContent)).setVisibility(0);
                ((RelativeLayout)a(R.id.playerContent)).removeAllViews();
                ((RelativeLayout)a(R.id.playerContent)).addView(this.p);
                C();
              }

              private final void y()
              {
                ((LinearLayout)a(R.id.cacheInfoLayout)).setVisibility(8);
                ((RelativeLayout)a(R.id.playerContent)).setVisibility(0);
                ((RelativeLayout)a(R.id.playerContent)).removeAllViews();
                ((RelativeLayout)a(R.id.playerContent)).addView(this.n);
                C();
              }

              private final void z()
              {
                ((TextView)a(R.id.cacheNum)).setText((CharSequence)String.valueOf(cn.ikicker.moviefans.util.c.a.a().a()));
                Observable.create((ObservableOnSubscribe)new ac(this)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Consumer)new ad(this));
                ((LinearLayout)a(R.id.cacheInfoLayout)).setVisibility(0);
                ((RelativeLayout)a(R.id.playerContent)).setVisibility(0);
                ((RelativeLayout)a(R.id.playerContent)).removeAllViews();
                ((RelativeLayout)a(R.id.playerContent)).addView(this.o);
                C();
              }

              public int a(Bundle paramBundle)
              {
                return 2131427368;
              }

              public View a(int paramInt)
              {
                if (this.t == null)
                  this.t = new HashMap();
                View localView2 = (View)this.t.get(Integer.valueOf(paramInt));
                View localView1 = localView2;
                if (localView2 == null)
                {
                  localView1 = findViewById(paramInt);
                  this.t.put(Integer.valueOf(paramInt), localView1);
                }
                return localView1;
              }

              public void a()
              {
                a((cn.ikicker.junecore.mvp.c)new PlayerPresenter((q.a)new PlayerModel(), (q.b)this));
              }

              public void a(VideoData paramVideoData)
              {
                Intrinsics.checkParameterIsNotNull(paramVideoData, "data");
                this.c = paramVideoData;
                Object localObject1 = this.c;
                if (localObject1 == null)
                  Intrinsics.throwNpe();
                b(((VideoData)localObject1).getSubscribe());
                this.b = paramVideoData.getName();
                ((TextView)a(R.id.movieTitle)).setText((CharSequence)this.b);
                Object localObject2 = getResources().getStringArray(2130903040);
                Object localObject3;
                if ((paramVideoData.getSource() != 1) && (paramVideoData.getSource() != 2) && (paramVideoData.getSource() != 3) && (paramVideoData.getSource() != 4) && (paramVideoData.getSource() != 5))
                {
                  localObject1 = (TextView)a(R.id.fromText);
                  localObject2 = StringCompanionObject.INSTANCE;
                  localObject3 = getString(2131624020);
                  Intrinsics.checkExpressionValueIsNotNull(localObject3, "getString(R.string.from_text)");
                  localObject2 = new Object[1];
                  localObject2[0] = getString(2131624019);
                  localObject2 = String.format((String)localObject3, Arrays.copyOf((Object[])localObject2, localObject2.length));
                  Intrinsics.checkExpressionValueIsNotNull(localObject2, "java.lang.String.format(format, *args)");
                  ((TextView)localObject1).setText((CharSequence)localObject2);
                }
                else
                {
                  localObject1 = (TextView)a(R.id.fromText);
                  localObject3 = StringCompanionObject.INSTANCE;
                  localObject3 = getString(2131624020);
                  Intrinsics.checkExpressionValueIsNotNull(localObject3, "getString(R.string.from_text)");
                  Object[] arrayOfObject = new Object[1];
                  arrayOfObject[0] = ((Object[])localObject2)[paramVideoData.getSource()];
                  localObject2 = String.format((String)localObject3, Arrays.copyOf(arrayOfObject, arrayOfObject.length));
                  Intrinsics.checkExpressionValueIsNotNull(localObject2, "java.lang.String.format(format, *args)");
                  ((TextView)localObject1).setText((CharSequence)localObject2);
                }
                if ((paramVideoData.getDetail() != null) && (!TextUtils.isEmpty((CharSequence)paramVideoData.getDetail().getSummary())))
                  ((TextView)a(R.id.movieInfo)).setText((CharSequence)paramVideoData.getDetail().getSummary());
                else
                  ((TextView)a(R.id.movieInfo)).setVisibility(8);
                ((TextView)a(R.id.movieUpdate)).setText((CharSequence)paramVideoData.getUpdateText());
                localObject1 = (TextView)a(R.id.episodeCount);
                localObject2 = new StringBuilder();
                ((StringBuilder)localObject2).append("/全");
                ((StringBuilder)localObject2).append(((Collection)paramVideoData.getPlays()).size());
                ((StringBuilder)localObject2).append(38598);
                ((TextView)localObject1).setText((CharSequence)((StringBuilder)localObject2).toString());
                if ((paramVideoData.getPlays() != null) && (paramVideoData.getPlays().size() > 0))
                {
                  a(paramVideoData.getPlays());
                  b(paramVideoData.getPlays());
                }
                paramVideoData = this.c;
                if (paramVideoData == null)
                  Intrinsics.throwNpe();
                if (paramVideoData.getSuggests() != null)
                {
                  paramVideoData = this.c;
                  if (paramVideoData == null)
                    Intrinsics.throwNpe();
                  if (paramVideoData.getSuggests().size() > 0)
                  {
                    paramVideoData = this.c;
                    if (paramVideoData == null)
                      Intrinsics.throwNpe();
                    c(paramVideoData.getSuggests());
                  }
                }
                paramVideoData = this.c;
                if (paramVideoData == null)
                  Intrinsics.throwNpe();
                if (paramVideoData.getType() == 2)
                  ((LinearLayout)a(R.id.episodeView)).setVisibility(8);
                else
                  ((LinearLayout)a(R.id.episodeView)).setVisibility(0);
                u();
                s();
                t();
                r();
                w();
                F();
              }

              public void a(String paramString)
              {
                cn.ikicker.junecore.app.a.a().a(paramString);
              }

              public final void a(String paramString, int paramInt1, int paramInt2)
              {
                Intrinsics.checkParameterIsNotNull(paramString, "url");
                paramString = new cn.ikicker.moviefans.app.d().a(this.m, "filterUrl", new Object[] { Integer.valueOf(1), paramString });
                if (!"noUrl".equals(paramString))
                {
                  cn.ikicker.junecore.d.b.b("result****", paramString);
                  if (this.g == 0)
                  {
                    Intrinsics.checkExpressionValueIsNotNull(paramString, "result");
                    d(paramString, paramInt1, paramInt2);
                    q();
                  }
                  this.g += 1;
                }
                cn.ikicker.junecore.d.b.b("result----", paramString);
              }

              public void b()
              {
                try
                {
                  cn.jzvd.a.g.F();
                }
                catch (Exception localException)
                {
                  localException.printStackTrace();
                }
                finish();
              }

              public void b(int paramInt)
              {
                IkickerJZVideoPlayerStandard.r = paramInt;
                ((IkickerJZVideoPlayerStandard)a(R.id.jzVideo)).setLikeImg(paramInt);
                Object localObject;
                if (paramInt == 1)
                {
                  localObject = this.c;
                  if (localObject == null)
                    Intrinsics.throwNpe();
                  ((VideoData)localObject).setSubscribe(1);
                  localObject = (ImageView)a(R.id.likeImg);
                }
                for (paramInt = 2131558435; ; paramInt = 2131558434)
                {
                  ((ImageView)localObject).setImageResource(paramInt);
                  return;
                  localObject = this.c;
                  if (localObject == null)
                    Intrinsics.throwNpe();
                  ((VideoData)localObject).setSubscribe(0);
                  localObject = (ImageView)a(R.id.likeImg);
                }
              }

              public void b(Bundle paramBundle)
              {
                paramBundle = (Context)this;
                if (!TextUtils.isEmpty((CharSequence)cn.ikicker.junecore.d.a.a(paramBundle).a("rule")))
                  paramBundle = cn.ikicker.junecore.d.a.a(paramBundle).a("rule");
                for (Object localObject = "ACache.get(this@PlayerAc…vity).getAsString(\"rule\")"; ; localObject = "FileUtil.getAssetsFile(\"tools.js\")")
                {
                  Intrinsics.checkExpressionValueIsNotNull(paramBundle, (String)localObject);
                  this.m = paramBundle;
                  break;
                  paramBundle = cn.ikicker.junecore.d.f.a("tools.js");
                }
                paramBundle = (CharSequence)new cn.ikicker.moviefans.app.d().a(this.m, "test", (Object[])new String[] { "ss" });
                if (!TextUtils.isEmpty(paramBundle))
                {
                  localObject = ((Iterable)StringsKt.split$default(paramBundle, new String[] { "#" }, false, 0, 6, null)).iterator();
                  for (i1 = 0; ((Iterator)localObject).hasNext(); i1++)
                  {
                    paramBundle = (String)((Iterator)localObject).next();
                    this.r[i1] = paramBundle;
                  }
                }
                ((RelativeLayout)a(R.id.backLay)).setOnClickListener((View.OnClickListener)new c(this));
                ((RelativeLayout)a(R.id.playerContent)).setVisibility(8);
                ((RelativeLayout)a(R.id.playerContent)).setOnTouchListener((View.OnTouchListener)d.a);
                ((TextView)a(R.id.movieInfo)).setOnClickListener((View.OnClickListener)new e(this));
                ((RelativeLayout)a(R.id.episodeLay)).setOnClickListener((View.OnClickListener)new f(this));
                ((LinearLayout)a(R.id.downLay)).setOnClickListener((View.OnClickListener)new g(this));
                ((LinearLayout)a(R.id.likeLay)).setOnClickListener((View.OnClickListener)new h(this));
                ((LinearLayout)a(R.id.shareLay)).setOnClickListener((View.OnClickListener)new i(this));
                if (getIntent().hasExtra("num"))
                {
                  paramBundle = getIntent().getExtras().get("num");
                  if (paramBundle == null)
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
                  this.h = ((Integer)paramBundle).intValue();
                }
                paramBundle = getIntent().getExtras().get("id");
                if (paramBundle == null)
                  throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
                d(((Integer)paramBundle).intValue());
                int i1 = (int)(k.b * 0.31D);
                ((IkickerJZVideoPlayerStandard)a(R.id.jzVideo)).getLayoutParams().height = i1;
                ((RelativeLayout)a(R.id.backLay)).setVisibility(0);
                if (!j.a())
                  G();
                cn.ikicker.moviefans.util.d.a().a((Activity)this);
              }

              public Context c()
              {
                return (Context)this;
              }

              public final void c(final int paramInt)
              {
                final Ref.ObjectRef localObjectRef = new Ref.ObjectRef();
                Object localObject2 = new StringBuilder();
                ((StringBuilder)localObject2).append("https://www.vfans.fun/sharevdetail?id=");
                Object localObject1 = this.c;
                if (localObject1 == null)
                  Intrinsics.throwNpe();
                ((StringBuilder)localObject2).append(((VideoData)localObject1).getId());
                ((StringBuilder)localObject2).append("&store=");
                ((StringBuilder)localObject2).append(cn.ikicker.moviefans.util.b.a.b((Context)this));
                ((StringBuilder)localObject2).append("&version=");
                ((StringBuilder)localObject2).append(getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
                localObjectRef.element = ((StringBuilder)localObject2).toString();
                localObject1 = new Ref.ObjectRef();
                localObject2 = this.c;
                if (localObject2 == null)
                  Intrinsics.throwNpe();
                ((Ref.ObjectRef)localObject1).element = ((VideoData)localObject2).getName();
                localObject2 = new Ref.ObjectRef();
                ((Ref.ObjectRef)localObject2).element = getString(2131624114);
                Observable.create((ObservableOnSubscribe)new aa(this)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Consumer)new ab(this, (Ref.ObjectRef)localObject1, (Ref.ObjectRef)localObject2, localObjectRef, paramInt));
              }

              public void g()
              {
                requestWindowFeature(1);
                getWindow().setFlags(1024, 1024);
              }

              public final VideoData m()
              {
                return this.c;
              }

              public final Object[] n()
              {
                return this.d;
              }

              public final Object[] o()
              {
                return this.e;
              }

              public void onBackPressed()
              {
                q();
                if (cn.jzvd.a.g.G())
                  return;
                super.onBackPressed();
              }

              public void onConfigurationChanged(Configuration paramConfiguration)
              {
                Intrinsics.checkParameterIsNotNull(paramConfiguration, "newConfig");
                super.onConfigurationChanged(paramConfiguration);
                w();
                if (paramConfiguration.orientation != 1)
                  int i1 = paramConfiguration.orientation;
              }

              protected void onDestroy()
              {
                try
                {
                  cn.jzvd.a.g.F();
                  ((IkickerJZVideoPlayerStandard)a(R.id.jzVideo)).D();
                  ((IkickerJZVideoPlayerStandard)a(R.id.jzVideo)).b((Activity)this);
                  ((IkickerJZVideoPlayerStandard)a(R.id.jzVideo)).E();
                  IkickerJZVideoPlayerStandard.o.clear();
                  cn.jzvd.upnp.a.a().c((Activity)this);
                  this.q.b();
                }
                catch (Exception localException)
                {
                  localException.printStackTrace();
                }
                super.onDestroy();
              }

              protected void onPause()
              {
                try
                {
                  long l1 = ((IkickerJZVideoPlayerStandard)a(R.id.jzVideo)).getCurrentPositionWhenPlaying();
                  cn.ikicker.junecore.d.a.a((Context)getApplication()).a(IkickerJZVideoPlayerStandard.s, String.valueOf(l1));
                  cn.jzvd.a.g.J();
                }
                catch (Exception localException)
                {
                  localException.printStackTrace();
                }
                cn.jzvd.a.g.x = 6;
                cn.jzvd.a.g.y = 1;
                B();
                super.onPause();
              }

              protected void onResume()
              {
                try
                {
                  if (cn.jzvd.a.g.aK != 1)
                    cn.jzvd.a.g.I();
                }
                catch (Exception localException)
                {
                  localException.printStackTrace();
                }
                super.onResume();
              }

              public void onWindowFocusChanged(boolean paramBoolean)
              {
                super.onWindowFocusChanged(paramBoolean);
                if ((paramBoolean) && (Build.VERSION.SDK_INT >= 19))
                  getWindow().getDecorView().setSystemUiVisibility(5894);
              }

              public final PlayerEpisodeAdapter p()
              {
                return this.f;
              }

              public final void q()
              {
                if (this.l != null)
                {
                  CountDownTimer localCountDownTimer = this.l;
                  if (localCountDownTimer == null)
                    Intrinsics.throwNpe();
                  localCountDownTimer.cancel();
                }
              }

              public void r()
              {
                i();
              }

              @Metadata(bv={1, 0, 2}, d1={""}, d2={"Lcn/ikicker/moviefans/ui/activity/PlayerActivity$Companion;", "", "()V", "CACHE_VIDEO", "", "CHANGE_VIDEO", "HALF_SHARE", "MESSAGE_TYPE_PLAY", "SOURCE_CC", "SOURCE_CUSTOM", "SOURCE_HANJUTV", "SOURCE_IQIYI", "SOURCE_MEIJU", "SOURCE_MGTV", "SOURCE_NANGUA", "SOURCE_QQ", "SOURCE_SOHU", "SOURCE_YOUKU", "START_PLAY", "TENCENT_TYPE", "TIMEOUT", "TRYTIME", "app_ikickerRelease"}, k=1, mv={1, 1, 7})
              public static final class a
              {
              }

              @Metadata(bv={1, 0, 2}, d1={""}, d2={"<anonymous>", "", "e", "Lio/reactivex/ObservableEmitter;", "Landroid/graphics/Bitmap;", "kotlin.jvm.PlatformType", "subscribe"}, k=3, mv={1, 1, 7})
              static final class aa<T>
                implements ObservableOnSubscribe<Bitmap>
              {
                aa(PlayerActivity paramPlayerActivity)
                {
                }

                public final void subscribe(ObservableEmitter<Bitmap> paramObservableEmitter)
                {
                  Intrinsics.checkParameterIsNotNull(paramObservableEmitter, "e");
                  RequestManager localRequestManager = Glide.with((FragmentActivity)this.a);
                  VideoData localVideoData = this.a.m();
                  if (localVideoData == null)
                    Intrinsics.throwNpe();
                  paramObservableEmitter.onNext((Bitmap)localRequestManager.load(localVideoData.getImg()).asBitmap().centerCrop().into(100, 100).get());
                }
              }

              @Metadata(bv={1, 0, 2}, d1={""}, d2={"<anonymous>", "", "coverBmp", "Landroid/graphics/Bitmap;", "kotlin.jvm.PlatformType", "accept"}, k=3, mv={1, 1, 7})
              static final class ab<T>
                implements Consumer<Bitmap>
              {
                ab(PlayerActivity paramPlayerActivity, Ref.ObjectRef paramObjectRef1, Ref.ObjectRef paramObjectRef2, Ref.ObjectRef paramObjectRef3, int paramInt)
                {
                }

                public final void a(final Bitmap paramBitmap)
                {
                  paramBitmap = new ShareContentWebPage((String)this.b.element, (String)this.c.element, (String)localObjectRef.element, paramBitmap, null);
                  if (paramInt == -1)
                  {
                    new cn.ikicker.shareloginlib.b.a((Activity)this.a, (cn.ikicker.shareloginlib.b.a.a)new a(this, paramBitmap)).a((LinearLayout)this.a.a(R.id.shareLay));
                    return;
                  }
                  cn.ikicker.shareloginlib.e locale;
                  Activity localActivity;
                  String str;
                  ShareContent localShareContent;
                  switch (paramInt)
                  {
                  default:
                    return;
                  case 2:
                    locale = cn.ikicker.shareloginlib.e.a();
                    localActivity = (Activity)this.a;
                    str = "WEIXIN_FRIEND_ZONE";
                    localShareContent = (ShareContent)paramBitmap;
                    paramBitmap = new cn.ikicker.moviefans.app.f();
                    break;
                  case 1:
                    locale = cn.ikicker.shareloginlib.e.a();
                    localActivity = (Activity)this.a;
                    str = "WEIXIN_FRIEND";
                    localShareContent = (ShareContent)paramBitmap;
                    paramBitmap = new cn.ikicker.moviefans.app.f();
                  }
                  locale.a(localActivity, str, localShareContent, (e.b)paramBitmap);
                }

                @Metadata(bv={1, 0, 2}, d1={""}, d2={"cn/ikicker/moviefans/ui/activity/PlayerActivity$share$2$sharePop$1", "Lcn/ikicker/shareloginlib/ui/SharePopupinow$OnItemClicklistener;", "(Lcn/ikicker/moviefans/ui/activity/PlayerActivity$share$2;Lcn/ikicker/shareloginlib/content/ShareContentWebPage;)V", "onItemClick", "", "index", "", "app_ikickerRelease"}, k=1, mv={1, 1, 7})
                public static final class a
                  implements cn.ikicker.shareloginlib.b.a.a
                {
                  a(ShareContentWebPage paramShareContentWebPage)
                  {
                  }

                  public void a(int paramInt)
                  {
                    cn.ikicker.shareloginlib.e locale;
                    Activity localActivity;
                    String str;
                    ShareContent localShareContent;
                    cn.ikicker.moviefans.app.f localf;
                    switch (paramInt)
                    {
                    default:
                      return;
                    case 1:
                      locale = cn.ikicker.shareloginlib.e.a();
                      localActivity = (Activity)this.a.a;
                      str = "WEIXIN_FRIEND_ZONE";
                      localShareContent = (ShareContent)paramBitmap;
                      localf = new cn.ikicker.moviefans.app.f();
                      break;
                    case 0:
                      locale = cn.ikicker.shareloginlib.e.a();
                      localActivity = (Activity)this.a.a;
                      str = "WEIXIN_FRIEND";
                      localShareContent = (ShareContent)paramBitmap;
                      localf = new cn.ikicker.moviefans.app.f();
                    }
                    locale.a(localActivity, str, localShareContent, (e.b)localf);
                  }
                }
              }

              @Metadata(bv={1, 0, 2}, d1={""}, d2={"<anonymous>", "", "e", "Lio/reactivex/ObservableEmitter;", "", "kotlin.jvm.PlatformType", "subscribe"}, k=3, mv={1, 1, 7})
              static final class ac<T>
                implements ObservableOnSubscribe<String>
              {
                ac(PlayerActivity paramPlayerActivity)
                {
                }

                public final void subscribe(ObservableEmitter<String> paramObservableEmitter)
                {
                  Intrinsics.checkParameterIsNotNull(paramObservableEmitter, "e");
                  StringBuilder localStringBuilder = new StringBuilder();
                  localStringBuilder.append(this.a.getString(2131623973));
                  localStringBuilder.append(com.blankj.utilcode.util.a.b(Environment.getExternalStorageDirectory()));
                  paramObservableEmitter.onNext(localStringBuilder.toString());
                }
              }

              @Metadata(bv={1, 0, 2}, d1={""}, d2={"<anonymous>", "", "spaceText", "", "kotlin.jvm.PlatformType", "accept"}, k=3, mv={1, 1, 7})
              static final class ad<T>
                implements Consumer<String>
              {
                ad(PlayerActivity paramPlayerActivity)
                {
                }

                public final void a(String paramString)
                {
                  ((TextView)this.a.a(R.id.diskCanUse)).setText((CharSequence)paramString);
                }
              }

              @Metadata(bv={1, 0, 2}, d1={""}, d2={"cn/ikicker/moviefans/ui/activity/PlayerActivity$startTime$1", "Landroid/os/CountDownTimer;", "(Lcn/ikicker/moviefans/ui/activity/PlayerActivity;Ljava/lang/String;IIJJ)V", "onFinish", "", "onTick", "millisUntilFinished", "", "app_ikickerRelease"}, k=1, mv={1, 1, 7})
              public static final class ae extends CountDownTimer
              {
                ae(String paramString, int paramInt1, int paramInt2, long paramLong1, long paramLong2)
                {
                  super(localObject);
                }

                public void onFinish()
                {
                  if (PlayerActivity.i(this.a) > 0)
                  {
                    if (PlayerActivity.j(this.a) == 0)
                    {
                      PlayerActivity.a(this.a, 5);
                      Object localObject = this.a;
                      PlayerActivity.b((PlayerActivity)localObject, PlayerActivity.i((PlayerActivity)localObject) - 1);
                      PlayerActivity.b(this.a, paramString, paramInt1, paramInt2);
                      PlayerActivity localPlayerActivity = this.a;
                      localObject = new StringBuilder();
                      ((StringBuilder)localObject).append(PlayerActivity.k(this.a)[PlayerActivity.i(this.a)]);
                      ((StringBuilder)localObject).append((String)StringsKt.split$default((CharSequence)paramString, new String[] { "?" }, false, 0, 6, null).get(0));
                      PlayerActivity.c(localPlayerActivity, ((StringBuilder)localObject).toString(), paramInt1, paramInt2);
                      localObject = new StringBuilder();
                      ((StringBuilder)localObject).append("trytime: ");
                      ((StringBuilder)localObject).append(PlayerActivity.i(this.a));
                      cn.ikicker.junecore.d.b.b("startTime", ((StringBuilder)localObject).toString());
                    }
                  }
                  else
                  {
                    cn.ikicker.junecore.d.b.b("startTime", "trytime: 加载失败");
                    ((IkickerJZVideoPlayerStandard)this.a.a(R.id.jzVideo)).B();
                  }
                  this.a.q();
                }

                public void onTick(long paramLong)
                {
                  PlayerActivity localPlayerActivity = this.a;
                  PlayerActivity.a(localPlayerActivity, PlayerActivity.h(localPlayerActivity) - 1);
                }
              }

              @Metadata(bv={1, 0, 2}, d1={""}, d2={"cn/ikicker/moviefans/ui/activity/PlayerActivity$handler$1", "Landroid/os/Handler;", "(Lcn/ikicker/moviefans/ui/activity/PlayerActivity;)V", "handleMessage", "", "msg", "Landroid/os/Message;", "app_ikickerRelease"}, k=1, mv={1, 1, 7})
              public static final class b extends Handler
              {
                public void handleMessage(Message paramMessage)
                {
                  Intrinsics.checkParameterIsNotNull(paramMessage, "msg");
                  super.handleMessage(paramMessage);
                  if (paramMessage.what != 1)
                    return;
                  ((IkickerJZVideoPlayerStandard)this.a.a(R.id.jzVideo)).C();
                  try
                  {
                    Object localObject = paramMessage.obj.toString();
                    int i = paramMessage.arg1;
                    paramMessage = this.a.n()[0];
                    if (paramMessage == null)
                    {
                      paramMessage = new kotlin/TypeCastException;
                      paramMessage.<init>("null cannot be cast to non-null type java.util.LinkedHashMap<kotlin.String, kotlin.String>");
                      throw paramMessage;
                    }
                    paramMessage = (LinkedHashMap)paramMessage;
                    paramMessage.clear();
                    paramMessage.put(this.a.getString(2131624024), localObject);
                    PlayerActivity.a(this.a, (String)localObject);
                    if (i == 0)
                    {
                      localObject = (IkickerJZVideoPlayerStandard)this.a.a(R.id.jzVideo);
                      paramMessage = this.a.n();
                      Object[] arrayOfObject = this.a.o();
                      ((IkickerJZVideoPlayerStandard)localObject).a(paramMessage, 0, 0, Arrays.copyOf(arrayOfObject, arrayOfObject.length));
                      if (((IkickerJZVideoPlayerStandard)this.a.a(R.id.jzVideo)).o())
                        ((IkickerJZVideoPlayerStandard)this.a.a(R.id.jzVideo)).c();
                    }
                    else if (i == 1)
                    {
                      if (((IkickerJZVideoPlayerStandard)this.a.a(R.id.jzVideo)).o())
                        ((IkickerJZVideoPlayerStandard)this.a.a(R.id.jzVideo)).a((String)paramMessage.get(this.a.getString(2131624024)), String.valueOf(this.a.o()[0]));
                    }
                    else;
                  }
                  catch (Exception paramMessage)
                  {
                    paramMessage.printStackTrace();
                  }
                }
              }

              @Metadata(bv={1, 0, 2}, d1={""}, d2={"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k=3, mv={1, 1, 7})
              static final class c
                implements View.OnClickListener
              {
                c(PlayerActivity paramPlayerActivity)
                {
                }

                public final void onClick(View paramView)
                {
                  this.a.b();
                }
              }

              @Metadata(bv={1, 0, 2}, d1={""}, d2={"<anonymous>", "", "v", "Landroid/view/View;", "kotlin.jvm.PlatformType", "event", "Landroid/view/MotionEvent;", "onTouch"}, k=3, mv={1, 1, 7})
              static final class d
                implements View.OnTouchListener
              {
                public static final d a = new d();

                public final boolean onTouch(View paramView, MotionEvent paramMotionEvent)
                {
                  return true;
                }
              }

              @Metadata(bv={1, 0, 2}, d1={""}, d2={"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k=3, mv={1, 1, 7})
              static final class e
                implements View.OnClickListener
              {
                e(PlayerActivity paramPlayerActivity)
                {
                }

                public final void onClick(View paramView)
                {
                  PlayerActivity.a(this.a);
                }
              }

              @Metadata(bv={1, 0, 2}, d1={""}, d2={"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k=3, mv={1, 1, 7})
              static final class f
                implements View.OnClickListener
              {
                f(PlayerActivity paramPlayerActivity)
                {
                }

                public final void onClick(View paramView)
                {
                  PlayerActivity.b(this.a);
                }
              }

              @Metadata(bv={1, 0, 2}, d1={""}, d2={"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k=3, mv={1, 1, 7})
              static final class g
                implements View.OnClickListener
              {
                g(PlayerActivity paramPlayerActivity)
                {
                }

                public final void onClick(View paramView)
                {
                  PlayerActivity.c(this.a);
                }
              }

              @Metadata(bv={1, 0, 2}, d1={""}, d2={"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k=3, mv={1, 1, 7})
              static final class h
                implements View.OnClickListener
              {
                h(PlayerActivity paramPlayerActivity)
                {
                }

                public final void onClick(View paramView)
                {
                  PlayerActivity.d(this.a);
                }
              }

              @Metadata(bv={1, 0, 2}, d1={""}, d2={"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k=3, mv={1, 1, 7})
              static final class i
                implements View.OnClickListener
              {
                i(PlayerActivity paramPlayerActivity)
                {
                }

                public final void onClick(View paramView)
                {
                  this.a.c(-1);
                }
              }

              @Metadata(bv={1, 0, 2}, d1={""}, d2={"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k=3, mv={1, 1, 7})
              static final class j
                implements View.OnClickListener
              {
                j(PlayerActivity paramPlayerActivity)
                {
                }

                public final void onClick(View paramView)
                {
                  ((LinearLayout)this.a.a(R.id.cacheInfoLayout)).setVisibility(8);
                  PlayerActivity.g(this.a);
                }
              }

              @Metadata(bv={1, 0, 2}, d1={""}, d2={"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k=3, mv={1, 1, 7})
              static final class k
                implements View.OnClickListener
              {
                k(Ref.ObjectRef paramObjectRef)
                {
                }

                public final void onClick(View paramView)
                {
                  ((cn.ikicker.moviefans.ui.widget.e)this.a.element).i();
                }
              }

              @Metadata(bv={1, 0, 2}, d1={""}, d2={"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k=3, mv={1, 1, 7})
              static final class l
                implements View.OnClickListener
              {
                public static final l a = new l();

                public final void onClick(View paramView)
                {
                }
              }

              @Metadata(bv={1, 0, 2}, d1={""}, d2={"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k=3, mv={1, 1, 7})
              static final class m
                implements View.OnClickListener
              {
                m(PlayerActivity paramPlayerActivity)
                {
                }

                public final void onClick(View paramView)
                {
                  org.jetbrains.anko.a.a.b(this.a, CacheActivity.class, new Pair[0]);
                }
              }

              @Metadata(bv={1, 0, 2}, d1={""}, d2={"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k=3, mv={1, 1, 7})
              static final class n
                implements View.OnClickListener
              {
                n(PlayerActivity paramPlayerActivity)
                {
                }

                public final void onClick(View paramView)
                {
                  PlayerActivity.g(this.a);
                }
              }

              @Metadata(bv={1, 0, 2}, d1={""}, d2={"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k=3, mv={1, 1, 7})
              static final class o
                implements View.OnClickListener
              {
                o(PlayerActivity paramPlayerActivity)
                {
                }

                public final void onClick(View paramView)
                {
                  PlayerActivity.g(this.a);
                }
              }

              @Metadata(bv={1, 0, 2}, d1={""}, d2={"<anonymous>", "", "show", "", "showBack"}, k=3, mv={1, 1, 7})
              static final class p
                implements IkickerJZVideoPlayerStandard.e
              {
                p(PlayerActivity paramPlayerActivity)
                {
                }

                public final void a(int paramInt)
                {
                  ((RelativeLayout)this.a.a(R.id.backLay)).setVisibility(paramInt);
                }
              }

              @Metadata(bv={1, 0, 2}, d1={""}, d2={"<anonymous>", "", "type", "", "shareType"}, k=3, mv={1, 1, 7})
              static final class q
                implements IkickerJZVideoPlayerStandard.d
              {
                q(PlayerActivity paramPlayerActivity)
                {
                }

                public final void a(int paramInt)
                {
                  this.a.c(paramInt);
                }
              }

              @Metadata(bv={1, 0, 2}, d1={""}, d2={"<anonymous>", "", "type", "", "position", "handle"}, k=3, mv={1, 1, 7})
              static final class r
                implements IkickerJZVideoPlayerStandard.a
              {
                r(PlayerActivity paramPlayerActivity, List paramList, Ref.ObjectRef paramObjectRef)
                {
                }

                public final void a(int paramInt1, int paramInt2)
                {
                  switch (paramInt1)
                  {
                  default:
                  case 11:
                    PlayerActivity.a(this.a, (VideoPlay)this.c.element, ((VideoPlay)this.c.element).id, 0);
                    return;
                  case 4:
                  case 3:
                    PlayerActivity.f(this.a);
                    Object localObject = this.a.p();
                    if (localObject == null)
                      Intrinsics.throwNpe();
                    ((PlayerEpisodeAdapter)localObject).a(paramInt2);
                    localObject = this.a.p();
                    if (localObject == null)
                      Intrinsics.throwNpe();
                    ((PlayerEpisodeAdapter)localObject).notifyDataSetChanged();
                    localObject = (VideoPlay)paramList.get(paramInt2);
                    PlayerActivity.a(this.a, (VideoPlay)localObject, ((VideoPlay)localObject).id, 1);
                    return;
                  case 0:
                    PlayerActivity.d(this.a);
                  case 1:
                  case 2:
                  }
                }
              }

              @Metadata(bv={1, 0, 2}, d1={""}, d2={"cn/ikicker/moviefans/ui/activity/PlayerActivity$initcast$1", "Lcn/jzvd/upnp/ClingClient$DiscoverListener;", "()V", "addDevice", "", "device", "Lcn/jzvd/upnp/entity/ClingDevice;", "removeDevice", "app_ikickerRelease"}, k=1, mv={1, 1, 7})
              public static final class s
                implements a.c
              {
                public void a(cn.jzvd.upnp.b.c paramc)
                {
                  Intrinsics.checkParameterIsNotNull(paramc, "device");
                  IkickerJZVideoPlayerStandard.o.add(paramc);
                }

                public void b(cn.jzvd.upnp.b.c paramc)
                {
                  Intrinsics.checkParameterIsNotNull(paramc, "device");
                  IkickerJZVideoPlayerStandard.o.remove(paramc);
                }
              }

              @Metadata(bv={1, 0, 2}, d1={""}, d2={"cn/ikicker/moviefans/ui/activity/PlayerActivity$parseRealPlayUrl$1", "Lcn/ikicker/moviefans/util/ParseWebUrlHelper$OnParseWebUrlListener;", "(Lcn/ikicker/moviefans/ui/activity/PlayerActivity;II)V", "onError", "", "errorMsg", "", "onFindUrl", "url", "app_ikickerRelease"}, k=1, mv={1, 1, 7})
              public static final class t
                implements d.c
              {
                t(int paramInt1, int paramInt2)
                {
                }

                public void a(String paramString)
                {
                  Intrinsics.checkParameterIsNotNull(paramString, "errorMsg");
                  cn.ikicker.junecore.d.b.b("parseErrorMsg", paramString);
                }

                public void b(String paramString)
                {
                  Intrinsics.checkParameterIsNotNull(paramString, "url");
                  cn.ikicker.junecore.d.b.b("webUrl", paramString);
                  if (PlayerActivity.j(this.a) == 0)
                    this.a.a(paramString, paramInt1, paramInt2);
                }
              }

              @Metadata(bv={1, 0, 2}, d1={""}, d2={"<anonymous>", "", "onStart"}, k=3, mv={1, 1, 7})
              static final class u
                implements cn.ikicker.moviefans.util.a.b.a
              {
                public static final u a = new u();

                public final void a()
                {
                }
              }

              @Metadata(bv={1, 0, 2}, d1={""}, d2={"<anonymous>", "", "onStop"}, k=3, mv={1, 1, 7})
              static final class v
                implements b.b
              {
                v(PlayerActivity paramPlayerActivity)
                {
                }

                public final void a()
                {
                  ((RelativeLayout)this.a.a(R.id.playerContent)).setVisibility(8);
                  PlayerActivity.l(this.a);
                }
              }

              @Metadata(bv={1, 0, 2}, d1={""}, d2={"<anonymous>", "", "onStart"}, k=3, mv={1, 1, 7})
              static final class w
                implements cn.ikicker.moviefans.util.a.b.a
              {
                public static final w a = new w();

                public final void a()
                {
                }
              }

              @Metadata(bv={1, 0, 2}, d1={""}, d2={"<anonymous>", "", "onStop"}, k=3, mv={1, 1, 7})
              static final class x
                implements b.b
              {
                x(PlayerActivity paramPlayerActivity)
                {
                }

                public final void a()
                {
                  ((RelativeLayout)this.a.a(R.id.movieHanle)).setVisibility(8);
                  PlayerActivity.l(this.a);
                }
              }

              @Metadata(bv={1, 0, 2}, d1={""}, d2={"<anonymous>", "", "run"}, k=3, mv={1, 1, 7})
              static final class y
                implements Runnable
              {
                y(PlayerActivity paramPlayerActivity)
                {
                }

                public final void run()
                {
                  ((NestedScrollView)this.a.a(R.id.bottomScrollLay)).setVisibility(0);
                  ((NestedScrollView)this.a.a(R.id.bottomScrollLay)).fling(0);
                  ((NestedScrollView)this.a.a(R.id.bottomScrollLay)).smoothScrollTo(0, 0);
                }
              }

              @Metadata(bv={1, 0, 2}, d1={""}, d2={"<anonymous>", "", "run"}, k=3, mv={1, 1, 7})
              static final class z
                implements Runnable
              {
                z(PlayerActivity paramPlayerActivity)
                {
                }

                public final void run()
                {
                  ((NestedScrollView)this.a.a(R.id.bottomScrollLay)).fling(0);
                  ((NestedScrollView)this.a.a(R.id.bottomScrollLay)).smoothScrollTo(0, 0);
                }
              }
            }

            ============================================================================================================
            package cn.ikicker.junecore.d;

            import java.security.MessageDigest;
            import java.security.NoSuchAlgorithmException;

            public class g
            {
              // ERROR //
              public static String a(java.io.File paramFile)
              {
                // Byte code:
                //   0: new 12	java/io/FileInputStream
                //   3: astore_2
                //   4: aload_2
                //   5: aload_0
                //   6: invokespecial 16	java/io/FileInputStream:<init>	(Ljava/io/File;)V
                //   9: aload_2
                //   10: astore_1
                //   11: aload_2
                //   12: invokevirtual 20	java/io/FileInputStream:getChannel	()Ljava/nio/channels/FileChannel;
                //   15: getstatic 26	java/nio/channels/FileChannel$MapMode:READ_ONLY	Ljava/nio/channels/FileChannel$MapMode;
                //   18: lconst_0
                //   19: aload_0
                //   20: invokevirtual 32	java/io/File:length	()J
                //   23: invokevirtual 38	java/nio/channels/FileChannel:map	(Ljava/nio/channels/FileChannel$MapMode;JJ)Ljava/nio/MappedByteBuffer;
                //   26: astore_3
                //   27: aload_2
                //   28: astore_1
                //   29: ldc 40
                //   31: invokestatic 46	java/security/MessageDigest:getInstance	(Ljava/lang/String;)Ljava/security/MessageDigest;
                //   34: astore_0
                //   35: aload_2
                //   36: astore_1
                //   37: aload_0
                //   38: aload_3
                //   39: invokevirtual 50	java/security/MessageDigest:update	(Ljava/nio/ByteBuffer;)V
                //   42: aload_2
                //   43: astore_1
                //   44: new 52	java/math/BigInteger
                //   47: astore_3
                //   48: aload_2
                //   49: astore_1
                //   50: aload_3
                //   51: iconst_1
                //   52: aload_0
                //   53: invokevirtual 56	java/security/MessageDigest:digest	()[B
                //   56: invokespecial 59	java/math/BigInteger:<init>	(I[B)V
                //   59: aload_2
                //   60: astore_1
                //   61: aload_3
                //   62: bipush 16
                //   64: invokevirtual 63	java/math/BigInteger:toString	(I)Ljava/lang/String;
                //   67: astore_3
                //   68: aload_3
                //   69: astore_0
                //   70: aload_2
                //   71: ifnull +57 -> 128
                //   74: aload_2
                //   75: invokevirtual 67	java/io/FileInputStream:close	()V
                //   78: aload_3
                //   79: areturn
                //   80: astore_0
                //   81: aload_0
                //   82: invokevirtual 70	java/io/IOException:printStackTrace	()V
                //   85: aload_3
                //   86: areturn
                //   87: astore_1
                //   88: aload_2
                //   89: astore_0
                //   90: aload_1
                //   91: astore_2
                //   92: goto +12 -> 104
                //   95: astore_0
                //   96: aconst_null
                //   97: astore_1
                //   98: goto +33 -> 131
                //   101: astore_2
                //   102: aconst_null
                //   103: astore_0
                //   104: aload_0
                //   105: astore_1
                //   106: aload_2
                //   107: invokevirtual 71	java/lang/Exception:printStackTrace	()V
                //   110: aload_0
                //   111: ifnull +15 -> 126
                //   114: aload_0
                //   115: invokevirtual 67	java/io/FileInputStream:close	()V
                //   118: goto +8 -> 126
                //   121: astore_0
                //   122: aload_0
                //   123: invokevirtual 70	java/io/IOException:printStackTrace	()V
                //   126: aconst_null
                //   127: astore_0
                //   128: aload_0
                //   129: areturn
                //   130: astore_0
                //   131: aload_1
                //   132: ifnull +15 -> 147
                //   135: aload_1
                //   136: invokevirtual 67	java/io/FileInputStream:close	()V
                //   139: goto +8 -> 147
                //   142: astore_1
                //   143: aload_1
                //   144: invokevirtual 70	java/io/IOException:printStackTrace	()V
                //   147: aload_0
                //   148: athrow
                //
                // Exception table:
                //   from	to	target	type
                //   74	78	80	java/io/IOException
                //   11	27	87	java/lang/Exception
                //   29	35	87	java/lang/Exception
                //   37	42	87	java/lang/Exception
                //   44	48	87	java/lang/Exception
                //   50	59	87	java/lang/Exception
                //   61	68	87	java/lang/Exception
                //   0	9	95	finally
                //   0	9	101	java/lang/Exception
                //   114	118	121	java/io/IOException
                //   11	27	130	finally
                //   29	35	130	finally
                //   37	42	130	finally
                //   44	48	130	finally
                //   50	59	130	finally
                //   61	68	130	finally
                //   106	110	130	finally
                //   135	139	142	java/io/IOException
              }

              public static String a(String paramString)
              {
                if ((paramString != null) && (paramString.length() != 0))
                {
                  StringBuffer localStringBuffer = new StringBuffer();
                  try
                  {
                    Object localObject = MessageDigest.getInstance("MD5");
                    ((MessageDigest)localObject).update(paramString.getBytes());
                    localObject = ((MessageDigest)localObject).digest();
                    for (int i = 0; i < localObject.length; i++)
                    {
                      if ((localObject[i] & 0xFF) < 16)
                      {
                        paramString = new java/lang/StringBuilder;
                        paramString.<init>();
                        paramString.append("0");
                        paramString.append(Integer.toHexString(0xFF & localObject[i]));
                      }
                      for (paramString = paramString.toString(); ; paramString = Integer.toHexString(localObject[i] & 0xFF))
                      {
                        localStringBuffer.append(paramString);
                        break;
                      }
                    }
                  }
                  catch (NoSuchAlgorithmException paramString)
                  {
                    paramString.printStackTrace();
                  }
                  return localStringBuffer.toString();
                }
                throw new IllegalArgumentException("String to encript cannot be null or zero length");
              }
            }