package com.cm.media.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import com.cm.media.R;

public class WebViewToolbar extends Toolbar implements View.OnClickListener, View.OnFocusChangeListener {

    private static final int STATE_EDIT_FOCUSED = 0x01;
    private static final int STATE_EDIT_UNFOCUSED = 0x02;
    private static final int STATE_SHOW_CLEAR_ICON = 0x03;
    private static final int STATE_HIDE_CLEAR_ICON = 0x04;
    private static final int STATE_REFRESHING = 0x05;
    private static final int STATE_SHOW_STOP = 0x06;

    public interface OnToolbarActionListener {
        void onSearchAction(String text);

        void onStopAction();

        void onRefreshAction();

        void onCloseAction();

        void onMoreAction();
    }


    private EditText mEditText;
    private ImageView mSearchView;

    private ImageView mclearView;
    private ImageView mStopView;
    private ImageView mRefreshView;
    private ImageView mMoreView;

    private ImageView mBackView;
    private ProgressBar mProgressBar;

    private OnToolbarActionListener mCloseListener;

    public WebViewToolbar(Context context) {
        this(context, null);
    }

    public WebViewToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WebViewToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void onWebviewRefresh() {
        onStateChanged(STATE_REFRESHING);
    }

    public void onWebviewStop() {
        onStateChanged(STATE_SHOW_STOP);
    }

    public void setNewUrl(String url) {
        mEditText.setText(url);
    }

    public void updateProgress(int progress) {
        mProgressBar.setProgress(progress);
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.web_toolbar, this, true);
        mEditText = findViewById(R.id.edit_text);
        mSearchView = findViewById(R.id.iv_search);
        mclearView = findViewById(R.id.iv_clear);
        mStopView = findViewById(R.id.iv_stop);
        mRefreshView = findViewById(R.id.iv_refresh);
        mBackView = findViewById(R.id.iv_back);
        mProgressBar = findViewById(R.id.progress_horizontal);
        mMoreView = findViewById(R.id.iv_more);

        mSearchView.setOnClickListener(this);
        mclearView.setOnClickListener(this);
        mStopView.setOnClickListener(this);
        mRefreshView.setOnClickListener(this);
        mBackView.setOnClickListener(this);
        mMoreView.setOnClickListener(this);

        mEditText.setOnFocusChangeListener(this);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() > 0) {
                    onStateChanged(STATE_SHOW_CLEAR_ICON);
                } else {
                    onStateChanged(STATE_HIDE_CLEAR_ICON);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void setOnToolbarCloseListener(OnToolbarActionListener mCloseListener) {
        this.mCloseListener = mCloseListener;
    }

    private void onStateChanged(int newState) {
        switch (newState) {
            case STATE_EDIT_FOCUSED:
                mRefreshView.setVisibility(GONE);
                mStopView.setVisibility(GONE);
                boolean hasSearchStr = mEditText.getText() != null && mEditText.getText().toString().trim().length() > 0;
                onStateChanged(hasSearchStr ? STATE_SHOW_CLEAR_ICON : STATE_HIDE_CLEAR_ICON);
                break;
            case STATE_EDIT_UNFOCUSED:
                mclearView.setVisibility(GONE);
                mSearchView.setVisibility(INVISIBLE);
                mMoreView.setVisibility(VISIBLE);
                hideKeyboard();
                break;
            case STATE_SHOW_CLEAR_ICON:
                mSearchView.setVisibility(VISIBLE);
                mMoreView.setVisibility(INVISIBLE);
                mclearView.setVisibility(VISIBLE);
                mRefreshView.setVisibility(GONE);
                mStopView.setVisibility(GONE);
                break;
            case STATE_HIDE_CLEAR_ICON:
                mSearchView.setVisibility(INVISIBLE);
                mMoreView.setVisibility(INVISIBLE);
                mclearView.setVisibility(GONE);
                break;
            case STATE_REFRESHING:
                mSearchView.setVisibility(INVISIBLE);
                mMoreView.setVisibility(VISIBLE);
                mclearView.setVisibility(GONE);
                mRefreshView.setVisibility(VISIBLE);
                mStopView.setVisibility(GONE);
                break;
            case STATE_SHOW_STOP:
                mSearchView.setVisibility(INVISIBLE);
                mMoreView.setVisibility(VISIBLE);
                mclearView.setVisibility(GONE);
                mRefreshView.setVisibility(GONE);
                mStopView.setVisibility(VISIBLE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search:
                hideKeyboard();
                mEditText.clearFocus();
                if (mCloseListener != null) {
                    mCloseListener.onSearchAction(mEditText.getText().toString().trim());
                }

                break;
            case R.id.iv_clear:
                mEditText.setText("");
                break;
            case R.id.iv_stop:
                if (mCloseListener != null) {
                    mCloseListener.onStopAction();
                }
                break;
            case R.id.iv_refresh:
                if (mCloseListener != null) {
                    mCloseListener.onRefreshAction();
                }
                break;
            case R.id.iv_back:
                if (mCloseListener != null) {
                    mCloseListener.onCloseAction();
                }
                break;
            case R.id.iv_more:
                if (mCloseListener != null) {
                    mCloseListener.onMoreAction();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v == mEditText) {
            onStateChanged(hasFocus ? STATE_EDIT_FOCUSED : STATE_EDIT_UNFOCUSED);
        }
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
