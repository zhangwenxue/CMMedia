package com.cm.media.viewmodel;

import android.text.TextUtils;
import androidx.core.util.Pair;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.cm.media.entity.ViewStatus;
import com.cm.media.entity.category.Category;
import com.cm.media.entity.vod.Vod;
import com.cm.media.repository.RemoteRepo;
import com.cm.media.util.CollectionUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

public class VodListViewModel extends ViewModel {

    private static final int DEF_pageSize = 15;

    public static final int STATE_REFRESH_IDLE = 0x00;
    public static final int STATE_REFRESH_START = 0x01;
    public static final int STATE_REFRESH_SUCCESS = 0x02;
    public static final int STATE_REFRESH_EMPTY = 0x03;
    public static final int STATE_REFRESH_FAILED = -0x04;

    public static final int STATE_LOAD_MORE_IDLE = 0x00;
    public static final int STATE_LOAD_MORE_START = 0x01;
    public static final int STATE_LOAD_MORE_FINISH = 0x02;
    public static final int STATE_LOAD_MORE_END = 0x03;
    public static final int STATE_LOAD_MORE_FAILED = -0x01;

    private int pageSize = DEF_pageSize;

    public class Request {
        String filters = "";
        boolean isRefresh;
        Disposable disposable;
        int pageNo = 1;

        void dispose() {
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
            disposable = null;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Request request = (Request) o;

            if (isRefresh != request.isRefresh) return false;
            return filters != null ? filters.equals(request.filters) : request.filters == null;
        }

        @Override
        public int hashCode() {
            int result = filters != null ? filters.hashCode() : 0;
            result = 31 * result + (isRefresh ? 1 : 0);
            return result;
        }
    }

    public class VodWrapper {
        public boolean isRefresh;
        public String filters;
        public List<Vod> vodList;

        VodWrapper(boolean isRefresh, String filters, List<Vod> vodList) {
            this.isRefresh = isRefresh;
            this.filters = filters;
            this.vodList = vodList;
        }
    }

    private final Request mRequest = new Request();
    private Category category;
    private final MutableLiveData<Boolean> loadMoreEnable = new MutableLiveData<>();
    private final MutableLiveData<Pair<String, Integer>> loadMoreState = new MutableLiveData<>();
    private final MutableLiveData<Pair<String, Integer>> refreshState = new MutableLiveData<>();
    private final MutableLiveData<VodWrapper> vodLiveData = new MutableLiveData<>();
    private final MutableLiveData<ViewStatus> viewStatus = new MutableLiveData<>();

    public MutableLiveData<Pair<String, Integer>> getLoadMoreState() {
        return loadMoreState;
    }

    public MutableLiveData<Pair<String, Integer>> getRefreshState() {
        return refreshState;
    }

    public MutableLiveData<Boolean> getLoadMoreEnable() {
        return loadMoreEnable;
    }

    public String getFilters() {
        return mRequest.filters;
    }

    public MutableLiveData<VodWrapper> getVodLiveData() {
        return vodLiveData;
    }

    public MutableLiveData<ViewStatus> getViewStatus() {
        return viewStatus;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void refresh() {
        loadData(mRequest.filters, true);
    }


    public void loadData(String filters, boolean refresh) {
        mRequest.dispose();
        mRequest.isRefresh = refresh || !mRequest.filters.equals(filters);
        mRequest.filters = filters;
        if (mRequest.isRefresh) {
            mRequest.pageNo = 1;
            loadMoreEnable.postValue(false);
            refreshState.postValue(new Pair<>(mRequest.filters, STATE_REFRESH_START));
            loadMoreState.setValue(new Pair<>(mRequest.filters, STATE_LOAD_MORE_IDLE));
            viewStatus.setValue(ViewStatus.Companion.generateLoadingStatus(""));
            vodLiveData.setValue(new VodWrapper(true, filters, new ArrayList<>()));
        } else {
            if (viewStatus.getValue() != null && viewStatus.getValue().getState() != ViewStatus.STATE_SUCCESS) {
                viewStatus.setValue(ViewStatus.Companion.generateSuccessStatus(""));
            }
            refreshState.postValue(new Pair<>(mRequest.filters, STATE_REFRESH_IDLE));
            loadMoreState.setValue(new Pair<>(mRequest.filters, STATE_LOAD_MORE_START));
        }
        mRequest.disposable = RemoteRepo.getInstance().getRxVodList(category.getId(), filters, mRequest.pageNo, pageSize)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(vodEntity -> {
                    List<Vod> vodList = vodEntity.getData();
                    boolean empty = CollectionUtils.isEmptyList(vodList);
                    boolean noMoreData = vodList.size() == 0 || vodList.size() < pageSize;
                    pageSize = Math.max(vodList.size(), pageSize);
                    if (mRequest.isRefresh) {
                        //refresh data
                        refreshState.setValue(empty ? new Pair<>(mRequest.filters, STATE_REFRESH_EMPTY) :
                                new Pair<>(mRequest.filters, STATE_REFRESH_SUCCESS));
                        String message = "没有数据，点击重试";
                        if (!TextUtils.isEmpty(mRequest.filters)) {
                            message = "没有找到相关视频，换个条件试试吧";
                        }
                        viewStatus.setValue(empty ? ViewStatus.Companion.generateEmptyStatus(message) :
                                ViewStatus.Companion.generateSuccessStatus(""));
                        if (noMoreData) {
                            loadMoreState.setValue(new Pair<>(mRequest.filters
                                    , STATE_LOAD_MORE_END));
                        }
                    } else {
                        //load more data
                        loadMoreState.setValue(noMoreData ? new Pair<>(mRequest.filters
                                , STATE_LOAD_MORE_END) : new Pair<>(mRequest.filters, STATE_LOAD_MORE_FINISH));
                    }
                    if (!noMoreData) {
                        mRequest.pageNo++;
                    }
                    vodLiveData.setValue(new VodWrapper(mRequest.isRefresh, mRequest.filters, vodList));
                    loadMoreEnable.setValue(true);
                }, throwable -> {
                    loadMoreEnable.setValue(true);
                    if (mRequest.isRefresh) {
                        //refresh data
                        vodLiveData.setValue(new VodWrapper(mRequest.isRefresh, mRequest.filters, new ArrayList<>()));
                        refreshState.setValue(new Pair<>(mRequest.filters, STATE_REFRESH_FAILED));
                        viewStatus.setValue(ViewStatus.Companion.generateErrorStatus("没有找到相关视频，换个条件试试吧"));
                    } else {
                        //load more data
                        loadMoreState.setValue(new Pair<>(mRequest.filters
                                , STATE_LOAD_MORE_FAILED));
                    }
                });
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mRequest.dispose();
    }
}
