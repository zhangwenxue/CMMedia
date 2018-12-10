package com.cm.media.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.cm.media.entity.category.Category;
import com.cm.media.repository.RemoteRepo;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<Category>> categoryList;

    public MutableLiveData<List<Category>> getCategoryList() {
        if (categoryList == null) {
            categoryList = new MutableLiveData<>();
        }
        return categoryList;
    }

    public void start() {
        List<Category> list = getCategoryList().getValue();
        if (list != null && !list.isEmpty()) {
            return;
        }
        Disposable disposable = RemoteRepo.getInstance().getRxCategory()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(listEntity -> {
                            List<Category> categories = listEntity.getData();
                            categories.add(0, Category.Companion.HOME_CATEGORY());
                            getCategoryList().setValue(categories);
                        }, throwable -> getCategoryList().setValue(null)

                );
    }
}
