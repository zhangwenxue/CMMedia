package com.cm.media.ui.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.*;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.cm.media.R;
import com.cm.media.databinding.ActivityWebBinding;
import com.cm.media.ui.widget.WebViewToolbar;

import static android.view.KeyEvent.KEYCODE_BACK;

public class WebActivity extends BaseThemeActivity {
    private static final String BAIDU_URL = "https://m.baidu.com";
    private ActivityWebBinding binding;
    private String currentUrl;
    private Dialog mSuggestionDlg;
    private String[] mSuggestionUrls;

    public static void startWebBrowser(Context context) {
        Intent intent = new Intent(context, WebActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebBinding.inflate(getLayoutInflater());
        setContentView(binding.root);
        setSupportActionBar(binding.toolbar);
        initWebView();
        binding.toolbar.setOnToolbarCloseListener(new WebViewToolbar.OnToolbarActionListener() {
            @Override
            public void onSearchAction(String text) {
                binding.webview.stopLoading();
                String url = "https://m.baidu.com?word=" + text;
                binding.webview.loadUrl(url);
                binding.webview.requestFocus();
            }

            @Override
            public void onStopAction() {
                binding.webview.stopLoading();
                binding.refreshLayout.setRefreshing(false);
            }

            @Override
            public void onRefreshAction() {
                binding.webview.loadUrl(currentUrl);
            }

            @Override
            public void onCloseAction() {
                finish();
            }

            @Override
            public void onMoreAction() {
                showSuggestionDlg();
            }
        });
        binding.refreshLayout.setOnRefreshListener(() -> {
            if (TextUtils.isEmpty(currentUrl)) {
                binding.refreshLayout.setRefreshing(false);
                return;
            }
            binding.webview.stopLoading();
            binding.webview.loadUrl(currentUrl);
        });
        binding.webview.loadUrl(BAIDU_URL);
        binding.toolbar.setNewUrl(BAIDU_URL);
        binding.floatingBtn.setOnClickListener((v -> {
            WebViewPlayActivity.startWebViewPlay(WebActivity.this, binding.webview.getUrl());
        }));
    }

    private void showSuggestionDlg() {
        if (mSuggestionDlg != null) {
            mSuggestionDlg.show();
            return;
        }
        if (mSuggestionUrls == null) {
            mSuggestionUrls = getResources().getStringArray(R.array.vod_web_urls);
        }
        mSuggestionDlg = new AlertDialog.Builder(this).setItems(R.array.websites, (dialog, which) -> {
            dialog.dismiss();
            binding.webview.stopLoading();
            binding.webview.loadUrl(mSuggestionUrls[which]);
        }).create();
        mSuggestionDlg.show();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {

        //获取WebView的配置
        WebSettings settings = binding.webview.getSettings();
        //配置支持DomStorage
        settings.setDomStorageEnabled(true);//启用或禁用DOM缓存
        settings.setAppCacheEnabled(false);//关闭/启用应用缓存
        settings.setSupportZoom(true);//是否可以缩放，默认true
        //settings.setBuiltInZoomControls(false);//是否显示缩放按钮，默认false
        settings.setJavaScriptEnabled(true);
        settings.setAllowContentAccess(true);
        settings.setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        settings.setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        binding.webview.canGoBack();
        //同时加载Https和Http混合模式
        if (Build.VERSION.SDK_INT >= 21) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }


        binding.webview.setWebViewClient(new WebViewClient() {
            //@Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                currentUrl = url;
                binding.toolbar.setNewUrl(url);
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    view.loadUrl(url);
                    return true;

                } else {
                    return true;
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                binding.toolbar.onWebviewStop();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                binding.toolbar.onWebviewRefresh();
                binding.toolbar.updateProgress(0);
                binding.refreshLayout.setRefreshing(false);
            }
        });
        binding.webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                binding.toolbar.updateProgress(newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
            }

            @Override
            public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
                super.onReceivedTouchIconUrl(view, url, precomposed);
            }

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                super.onShowCustomView(view, callback);
            }

            @Override
            public void onHideCustomView() {
                super.onHideCustomView();
            }

            @Nullable
            @Override
            public Bitmap getDefaultVideoPoster() {
                return super.getDefaultVideoPoster();
            }

            @Nullable
            @Override
            public View getVideoLoadingProgressView() {
                return super.getVideoLoadingProgressView();
            }

            @Override
            public void getVisitedHistory(ValueCallback<String[]> callback) {
                super.getVisitedHistory(callback);
            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
            }
        });
    }

    //按返回键回退网页
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && binding.webview.canGoBack()) {
            binding.webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.webview.destroy();
    }
}
