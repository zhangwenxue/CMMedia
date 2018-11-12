package com.cm.media.ui.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class AutoPlayLayout extends FrameLayout {
    private ViewPager mViewPager;
    private int curIndex = 0;
    private int totalCount = 0;
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    public AutoPlayLayout(Context context) {
        this(context, null);
    }

    public AutoPlayLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoPlayLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setupWithViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        HANDLER.post(mCallback);
    }

    private void init() {
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        HANDLER.removeCallbacks(mCallback);
        HANDLER.postDelayed(mCallback, 1800);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        HANDLER.removeCallbacks(mCallback);
    }

    private final Runnable mCallback = new Runnable() {
        @Override
        public void run() {
            PagerAdapter adapter = mViewPager.getAdapter();
            if (adapter == null) {
                return;
            }
            int count = adapter.getCount();
            curIndex = totalCount == 0 ? 0 : curIndex;
            totalCount = count;
            mViewPager.setCurrentItem(curIndex++);
            if (curIndex == totalCount) {
                curIndex = 0;
            }
            HANDLER.postDelayed(this, 1800);
        }
    };
}
