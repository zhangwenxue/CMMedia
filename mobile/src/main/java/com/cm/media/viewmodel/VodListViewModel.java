package com.cm.media.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v4.util.Pair;
import android.text.TextUtils;
import com.cm.media.ApiService;
import com.cm.media.entity.category.Category;
import com.cm.media.entity.vod.Vod;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

public class VodListViewModel extends ViewModel {

    public class VodWrapper {
        public boolean isRefresh;
        public String filters;
        public List<Vod> vodList;

        public VodWrapper(boolean isRefresh, String filters, List<Vod> vodList) {
            this.isRefresh = isRefresh;
            this.filters = filters;
            this.vodList = vodList;
        }
    }

    private Category category;
    private String filters = "";
    private int pageNo = 1;
    private Disposable disposable;
    private final MutableLiveData<Boolean> isLoadingFinish = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isRefreshFinish = new MutableLiveData<>();
    private final MutableLiveData<Boolean> hasNoMoreData = new MutableLiveData<>();
    private final MutableLiveData<VodWrapper> vodLiveData = new MutableLiveData<>();


    public MutableLiveData<Boolean> getIsLoadingFinish() {
        return isLoadingFinish;
    }

    public MutableLiveData<Boolean> getIsRefreshFinish() {
        return isRefreshFinish;
    }

    public MutableLiveData<Boolean> getHasNoMoreData() {
        return hasNoMoreData;
    }

    public String getFilters() {
        return filters;
    }

    public MutableLiveData<VodWrapper> getVodLiveData() {
        return vodLiveData;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void start(String filters) {
        this.filters = filters;
        loadData(filters, true);
    }


    public void loadData(String filters, boolean refresh) {
        final boolean reFetch = refresh || (!TextUtils.isEmpty(filters) && !filters.equals(this.filters));
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            disposable = null;
        }
        if (reFetch) {
            pageNo = 1;
            isRefreshFinish.setValue(false);
            hasNoMoreData.setValue(false);
        } else {
            isLoadingFinish.setValue(false);
        }
        this.filters = filters;
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.vfans.fun")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);
        disposable = service.getRxVodList(category.getId(), filters, pageNo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(vodEntity -> {
                    List<Vod> vodList = vodEntity.getData();
                    if (vodEntity.getCode() == 1000) {
                        if (vodList.isEmpty()) {
                            hasNoMoreData.setValue(true);
                        } else {
                            pageNo++;
                        }
                        vodLiveData.setValue(new VodWrapper(refresh, filters, vodList));

                    }
                    if (reFetch) {
                        isRefreshFinish.setValue(true);
                    } else {
                        isLoadingFinish.setValue(true);
                    }
                }, throwable -> {
                    if (reFetch) {
                        isRefreshFinish.setValue(true);
                    } else {
                        isLoadingFinish.setValue(true);
                    }
                });
    }


}
