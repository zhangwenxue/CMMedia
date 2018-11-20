package com.cm.media.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.text.TextUtils;
import com.cm.media.entity.vod.VodDetail;
import com.cm.media.repository.RemoteRepo;
import com.cm.media.util.UrlParser;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
                            String playUrl = detail.getPlays().get(0).getPlayUrl();
                            onProcessPlayUrl(playUrl);
                        }
                    }
                }, throwable -> {

                });
    }

    private void onProcessPlayUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        long time = System.currentTimeMillis() / 1000;
        String finalUrl = url + "?" + "stTime=" + time + "&token=" + UrlParser.getToken(time, url);
        playingUrlLiveData.setValue(finalUrl);
    }

}
