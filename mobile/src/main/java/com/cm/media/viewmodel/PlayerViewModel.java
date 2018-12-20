package com.cm.media.viewmodel;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.util.Pair;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.cm.media.R;
import com.cm.media.entity.ViewStatus;
import com.cm.media.entity.vod.VodDetail;
import com.cm.media.entity.vod.VodPlayUrl;
import com.cm.media.entity.vod.parse.ResolvedPlayUrl;
import com.cm.media.entity.vod.parse.ResolvedStream;
import com.cm.media.entity.vod.parse.ResolvedVod;
import com.cm.media.repository.RemoteRepo;
import com.cm.media.util.CollectionUtils;
import com.cm.media.util.UrlParser;
import com.cm.media.util.WebViewVodParser;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerViewModel extends ViewModel {
    private final MutableLiveData<VodDetail> vodDetailLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Pair<String, String>>> playingUrlLiveData = new MutableLiveData<>();
    private final MutableLiveData<ViewStatus> viewStatus = new MutableLiveData<>();
    private final MutableLiveData<Integer> parseState = new MutableLiveData<>();

    private WeakReference<Context> mAppContextRef;
    private int videoId;
    private WebViewVodParser webViewVodParser;

    public MutableLiveData<VodDetail> getVodDetailLiveData() {
        return vodDetailLiveData;
    }

    public MutableLiveData<List<Pair<String, String>>> getPlayingUrlLiveData() {
        return playingUrlLiveData;
    }

    public MutableLiveData<ViewStatus> getViewStatus() {
        return viewStatus;
    }

    public MutableLiveData<Integer> getParseState() {
        return parseState;
    }

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void start(Context context, int videoId) {
        mAppContextRef = new WeakReference<>(Objects.requireNonNull(context).getApplicationContext());
        viewStatus.setValue(ViewStatus.Companion.generateLoadingStatus("加载中..."));
        this.videoId = videoId;
        Disposable disposable = RemoteRepo.getInstance().getRxVodDetail(videoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(vodDetailEntity -> {
                    if (vodDetailEntity != null && vodDetailEntity.getData() != null) {
                        VodDetail detail = vodDetailEntity.getData();
                        vodDetailLiveData.setValue(detail);
                        if (detail != null && !detail.getPlays().isEmpty()) {
                            Log.i("url", "source:" + detail.getSource());
                            Log.i("url", "plays:" + CollectionUtils.stringValue(detail.getPlays()));
                            viewStatus.setValue(ViewStatus.Companion.generateSuccessStatus("加载成功"));
                        } else {
                            viewStatus.setValue(ViewStatus.Companion.generateEmptyStatus("视频地址无效"));
                        }
                    } else {
                        viewStatus.setValue(ViewStatus.Companion.generateEmptyStatus("加载失败，点击重试"));
                    }
                }, throwable -> {
                    Log.e("error", "error!", throwable);
                    viewStatus.setValue(ViewStatus.Companion.generateErrorStatus("加载失败，点击重试"));
                });
        compositeDisposable.add(disposable);
    }

    public void retry() {
        start(mAppContextRef.get(), videoId);
    }

    public void processPlayUrl(VodPlayUrl detail) {
        if (vodDetailLiveData.getValue() == null) {
            return;
        }
        try {
            onProcessPlayUrl(detail.getTitle(), vodDetailLiveData.getValue().getSource(), detail.getPlayUrl());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void onProcessPlayUrl(String name, int source, String url) throws JSONException {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (source == 0) {
            long time = System.currentTimeMillis() / 1000;
            String finalUrl = url + "?" + "stTime=" + time + "&token=" + UrlParser.getToken(time, url);
            playingUrlLiveData.setValue(new ArrayList<Pair<String, String>>() {{
                add(new Pair<>(name, finalUrl));
            }});
        } else if (source == 7) {
            parseState.postValue(0);
            Disposable disposable = RemoteRepo.getInstance().resolveRxVCinemaUrl(url)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(resolvedVodEntity -> {
                        Log.i("url", "resolve:" + resolvedVodEntity);
                        parseState.postValue(-1);
                        if (resolvedVodEntity == null) {
                            return;
                        }
                        ResolvedVod vod = resolvedVodEntity.getData();
                        List<ResolvedStream> streamList = vod.getStreams();
                        if (CollectionUtils.isEmptyList(streamList) || CollectionUtils.isEmptyList(streamList.get(0).getPlay_list())) {
                            playingUrlLiveData.setValue(null);
                            parseState.postValue(-1);
                            return;
                        }
                        List<Pair<String, String>> list = null;
                        for (ResolvedPlayUrl resolvedPlayUrl : Objects.requireNonNull(streamList.get(0).getPlay_list())) {
                            if (list == null) {
                                list = new ArrayList<>(Objects.requireNonNull(streamList.get(0).getPlay_list()).size());
                            }
                            list.add(new Pair<>(resolvedPlayUrl.getResolution(), resolvedPlayUrl.getUrl()));
                        }
                        parseState.postValue(1);
                        playingUrlLiveData.setValue(list);
                    }, throwable -> {
                        Log.e("error", "error!", throwable);
                        parseState.postValue(-1);
                    });
            compositeDisposable.add(disposable);

        } else if (source != 8 && !url.contains("m3u")) {
            if (webViewVodParser == null) {
                webViewVodParser = new WebViewVodParser(mAppContextRef.get());
            }
            parseState.postValue(0);
            webViewVodParser.start(url, new WebViewVodParser.ParseResultCallback() {
                @Override
                public void onSuccess(String ret) {
                    Log.e("playUrl:", ret);
                    playingUrlLiveData.setValue(new ArrayList<Pair<String, String>>() {{
                        add(new Pair<>(name, ret));
                    }});
                    webViewVodParser.stop();
                    parseState.postValue(1);
                }

                @Override
                public void onFailed(String msg) {
                    Log.e("error", "parseError," + msg);
                    webViewVodParser.stop();
                    parseState.postValue(-1);
                }
            });
        } else {
            playingUrlLiveData.setValue(new ArrayList<Pair<String, String>>() {{
                add(new Pair<>(name, url));
            }});
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
        if (mAppContextRef != null && mAppContextRef.get() != null) {
            mAppContextRef.clear();
        }
        if (webViewVodParser != null) {
            webViewVodParser.release();
            webViewVodParser = null;
        }
    }
}
