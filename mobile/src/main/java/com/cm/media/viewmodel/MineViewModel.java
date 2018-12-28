package com.cm.media.viewmodel;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.cm.media.repository.AppExecutor;
import com.cm.media.repository.db.AppDatabase;
import com.cm.media.repository.db.entity.VodHistory;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscriber;

import java.util.List;

public class MineViewModel extends ViewModel {
    private final MutableLiveData<List<VodHistory>> vodHistoryList = new MutableLiveData<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MutableLiveData<List<VodHistory>> getVodHistoryList() {
        return vodHistoryList;
    }

    public void start(Context context) {
        Disposable disposable = AppDatabase.Companion.getInstance(context)
                .vodHistoryDao()
                .listHistories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(vodHistories -> {
                    if (vodHistories != null) {
                        vodHistoryList.setValue(vodHistories);
                    }
                }, Throwable::printStackTrace);
        compositeDisposable.add(disposable);
    }

    public void removeHistory(Context context, VodHistory vodHistory) {
        if (vodHistory == null) {
            return;
        }
        AppExecutor.getInstance().execute(() -> AppDatabase.Companion.getInstance(context)
                .vodHistoryDao()
                .delete(vodHistory));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
