package com.cm.media.viewmodel;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.util.Pair;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.cm.media.entity.Entity;
import com.cm.media.entity.ViewStatus;
import com.cm.media.entity.vod.RealPlayUrl;
import com.cm.media.entity.vod.VodDetail;
import com.cm.media.entity.vod.VodPlayUrl;
import com.cm.media.entity.vod.parse.ResolvedPlayUrl;
import com.cm.media.entity.vod.parse.ResolvedStream;
import com.cm.media.entity.vod.parse.ResolvedVod;
import com.cm.media.repository.AppExecutor;
import com.cm.media.repository.RemoteRepo;
import com.cm.media.repository.db.AppDatabase;
import com.cm.media.repository.db.entity.VodHistory;
import com.cm.media.util.CollectionUtils;
import com.cm.media.util.UrlParser;
import com.cm.media.util.WebViewParser;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import kotlin.text.StringsKt;
import org.json.JSONException;
import org.reactivestreams.Subscriber;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Objects;

public class PlayerViewModel extends ViewModel {

    private final MutableLiveData<Pair<VodHistory, VodDetail>> vodDetailLiveData = new MutableLiveData<>();
    private final MutableLiveData<ViewStatus> viewStatus = new MutableLiveData<>();
    private final MutableLiveData<Integer> parseState = new MutableLiveData<>();
    private final MutableLiveData<VodHistory> vodHistory = new MutableLiveData<>();
    private final MutableLiveData<RealPlayUrl> realPlayUrl = new MutableLiveData<>();
    private boolean loaded = false;

    private WeakReference<Activity> mActRef;
    private int videoId;

    public MutableLiveData<Pair<VodHistory, VodDetail>> getVodDetailLiveData() {
        return vodDetailLiveData;
    }

    public MutableLiveData<ViewStatus> getViewStatus() {
        return viewStatus;
    }

    public MutableLiveData<Integer> getParseState() {
        return parseState;
    }

    public MutableLiveData<VodHistory> getVodHistory() {
        return vodHistory;
    }

    public MutableLiveData<RealPlayUrl> getRealPlayUrl() {
        return realPlayUrl;
    }

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void start(Activity act, int videoId) {
        mActRef = new WeakReference<>(Objects.requireNonNull(act));
        viewStatus.setValue(ViewStatus.Companion.generateLoadingStatus("加载中..."));
        this.videoId = videoId;
        Flowable<Entity<VodDetail>> remoteFlowable = RemoteRepo.getInstance().getRxVodDetail(videoId);
        Flowable<List<VodHistory>> localFlowable = AppDatabase.Companion.getInstance(mActRef.get()).vodHistoryDao().findByVId(String.valueOf(videoId));
        Disposable disposable = Flowable.combineLatest(localFlowable, remoteFlowable, Pair::new)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pair -> {
                    if (pair == null) {
                        return;
                    }
                    if (loaded) {
                        return;
                    }
                    loaded = true;
                    VodHistory history = pair.first == null ? null : pair.first.isEmpty() ? null : pair.first.get(0);
                    if (history == null) {
                        history = VodHistory.Companion.createVodHistory(String.valueOf(videoId));
                    }
                    VodDetail detail;
                    if (pair.second == null) {
                        viewStatus.setValue(ViewStatus.Companion.generateErrorStatus("加载失败，点击重试"));
                    } else {
                        detail = pair.second.getData();
                        if (detail != null && !pair.second.getData().getPlays().isEmpty()) {
                            Log.i("url", "source:" + detail.getSource());
                            Log.i("url", "plays:" + CollectionUtils.stringValue(detail.getPlays()));
                            viewStatus.setValue(ViewStatus.Companion.generateSuccessStatus("加载成功"));
                            vodDetailLiveData.setValue(new Pair<>(history, detail));
                            history.setEpiCount(detail.getPlays().size());
                            history.setName(detail.getName());
                            if (detail.getImg() != null) {
                                history.setPost(detail.getImg());
                            }
                            if (detail.getSeason() != null) {
                                history.setSeason(detail.getSeason());
                            }
                            history.setVodType(detail.getType());
                        } else {
                            viewStatus.setValue(ViewStatus.Companion.generateEmptyStatus("视频地址无效"));
                        }
                    }
                    vodHistory.postValue(history);
                }, throwable -> {
                    Log.e("error", "error!", throwable);
                    viewStatus.setValue(ViewStatus.Companion.generateErrorStatus("加载失败，点击重试"));
                });
        compositeDisposable.add(disposable);
    }

    public void retry() {
        start(mActRef.get(), videoId);
    }

    public void updateHistory(VodHistory vodHistory) {
        if (vodHistory != null) {
            Disposable disposable = Flowable.fromPublisher(new Flowable<Void>() {
                @Override
                protected void subscribeActual(Subscriber<? super Void> subscriber) {
                    if (vodHistory.getId() == 0) {
                        vodHistory.setModifiedTime(System.currentTimeMillis());
                        AppDatabase.Companion.getInstance(mActRef.get()).vodHistoryDao().insert(vodHistory);
                    } else {
                        vodHistory.setModifiedTime(System.currentTimeMillis());
                        AppDatabase.Companion.getInstance(mActRef.get()).vodHistoryDao().update(vodHistory);
                    }
                    subscriber.onNext(null);
                }
            }).subscribeOn(Schedulers.io())
                    .subscribe();
            compositeDisposable.add(disposable);
        }
    }

    public void processPlayUrl(VodPlayUrl detail) {
        if (vodDetailLiveData.getValue() == null || vodDetailLiveData.getValue().second == null) {
            return;
        }
        try {
            onProcessPlayUrl(detail.getTitle(), vodDetailLiveData.getValue().second.getSource(), detail.getPlayUrl(), detail.getEpisode());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void onProcessPlayUrl(String name, int source, final String url, int episode) throws JSONException {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (source == 0) {
            long time = System.currentTimeMillis() / 1000;
            String finalUrl = url + "?" + "stTime=" + time + "&token=" + UrlParser.getToken(time, url);
            onParseUrl(episode, new Pair<>(name, finalUrl));
        } else if (source == 7) {
            parseState.postValue(0);
            Disposable disposable = RemoteRepo.getInstance().resolveRxVCinemaUrl(url)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(resolvedVodEntity -> {
                        Log.i("url", "resolve:" + resolvedVodEntity);
                        if (resolvedVodEntity == null) {
                            parseState.postValue(-1);
                            return;
                        }
                        ResolvedVod vod = resolvedVodEntity.getData();
                        List<ResolvedStream> streamList = vod.getStreams();
                        if (CollectionUtils.isEmptyList(streamList) || CollectionUtils.isEmptyList(streamList.get(0).getPlay_list())) {
                            realPlayUrl.setValue(null);
                            parseState.postValue(-1);
                            return;
                        }
                        Pair[] array = new Pair[Objects.requireNonNull(streamList.get(0).getPlay_list()).size()];
                        int i = 0;
                        for (ResolvedPlayUrl resolvedPlayUrl : Objects.requireNonNull(streamList.get(0).getPlay_list())) {
                            array[i++] = new Pair<>(resolvedPlayUrl.getResolution(), resolvedPlayUrl.getUrl());
                        }
                        parseState.postValue(1);
                        onParseUrl(episode, array);
                    }, throwable -> {
                        Log.e("error", "error!", throwable);
                        parseState.postValue(-1);
                    });
            compositeDisposable.add(disposable);

        } else if (source != 8 && !url.contains("m3u")) {
            String parseUrl = "http://app.baiyug.cn:2019/vip/index.php?url=" + StringsKt.split(url, new String[]{"?"}, false, 6).get(0);
            parseState.postValue(0);
            WebViewParser parser = new WebViewParser();
            parser.start(mActRef.get(), parseUrl);

            parser.setCallback(new WebViewParser.Callback() {
                @Override
                public void onSuccess(String url) {
                    parseState.postValue(1);
                    onParseUrl(episode, new Pair<>(name, url));
                }

                @Override
                public void onFailed() {
                    parseState.postValue(-1);
                    Log.i("#$#:", "失败");
                }
            });
        } else {
            onParseUrl(episode, new Pair<>(name, url));
        }
    }

    private void onParseUrl(int episode, Pair... urls) {
        int episodeCount = vodHistory.getValue() == null ? 1 : vodHistory.getValue().getEpiCount();
        RealPlayUrl playUrl = new RealPlayUrl(urls, episodeCount, episode);
        realPlayUrl.postValue(playUrl);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        loaded = false;
        compositeDisposable.clear();
        if (mActRef != null && mActRef.get() != null) {
            mActRef.clear();
        }
    }
}
