package com.cm.media.ui.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;
import com.cm.media.entity.category.Category;
import com.cm.media.ui.fragment.HomeTopicFragment;
import com.cm.media.ui.fragment.VodListFragment;

import java.lang.ref.WeakReference;
import java.util.List;

public class VodFragmentAdapter extends FragmentStatePagerAdapter {
    private List<Category> mList;
    private SparseArray<WeakReference<Fragment>> mFragmentCache = new SparseArray<>();

    public VodFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void replaceData(List<Category> list) {
        this.mList = list;
        mFragmentCache.clear();
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int i) {
        WeakReference<Fragment> fragmentWeakReference = mFragmentCache.get(i, null);
        if (fragmentWeakReference == null || fragmentWeakReference.get() == null) {
            Fragment fragment = i == 0 ? HomeTopicFragment.newInstance() : VodListFragment.newInstance(mList.get(i));
            WeakReference<Fragment> reference = new WeakReference<>(fragment);
            mFragmentCache.put(i, reference);
        }
        return mFragmentCache.get(i).get();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position).getName();
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }
}
