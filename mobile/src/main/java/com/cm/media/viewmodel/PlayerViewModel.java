package com.cm.media.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.text.TextUtils;
import android.util.Log;
import com.cm.media.entity.vod.VodDetail;
import com.cm.media.entity.vod.parse.ResolvedStream;
import com.cm.media.entity.vod.parse.ResolvedVod;
import com.cm.media.repository.RemoteRepo;
import com.cm.media.util.CollectionUtils;
import com.cm.media.util.UrlParser;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import org.json.JSONException;

import java.util.List;

public class PlayerViewModel extends ViewModel {
    private final MutableLiveData<VodDetail> vodDetailLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> playingUrlLiveData = new MutableLiveData<>();

    public MutableLiveData<VodDetail> getVodDetailLiveData() {
        return vodDetailLiveData;
    }

    public MutableLiveData<String> getPlayingUrlLiveData() {
        return playingUrlLiveData;
    }

    public void start(int videoId) {
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
                            String playUrl = detail.getPlays().get(0).getPlayUrl();
                            onProcessPlayUrl(detail.getSource(), playUrl);
                        }
                    }
                }, throwable -> {

                });
    }

    private void onProcessPlayUrl(int source, String url) throws JSONException {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        switch (source) {
            case 0:
                long time = System.currentTimeMillis() / 1000;
                String finalUrl = url + "?" + "stTime=" + time + "&token=" + UrlParser.getToken(time, url);
                playingUrlLiveData.setValue(finalUrl);
                break;
//            case 7:
//                Disposable disposable = RemoteRepo.getInstance().resolveRxVCinemaUrl(url)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(resolvedVodEntity -> {
//                            Log.i("url", "resolve:" + resolvedVodEntity);
//                            if (resolvedVodEntity == null) {
//                                return;
//                            }
//                            ResolvedVod vod = resolvedVodEntity.getData();
//                            List<ResolvedStream> streamList = vod.getStreams();
//                            if (CollectionUtils.isEmptyList(streamList)) {
//                                return;
//                            }
//                            playingUrlLiveData.setValue(streamList.get(0).getPlayList().get(0).getUrl());
//                        }, throwable -> Log.e("error", "error!", throwable));
//                break;
            case 7:
                Disposable disposable = RemoteRepo.getInstance().resolveRxVCinemaUrl(url)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(resolvedVodEntity -> {
                            Log.i("url", "resolve:" + resolvedVodEntity);
                            if (resolvedVodEntity == null) {
                                return;
                            }
//                            ResolvedVod vod = resolvedVodEntity.getData();
//                            List<ResolvedStream> streamList = vod.getStreams();
//                            if (CollectionUtils.isEmptyList(streamList)) {
//                                return;
//                            }
//                            playingUrlLiveData.setValue(streamList.get(0).getPlayList().get(0).getUrl());
                        }, throwable -> Log.e("error", "error!", throwable));
                break;
            default:
                break;
        }
    }
}
