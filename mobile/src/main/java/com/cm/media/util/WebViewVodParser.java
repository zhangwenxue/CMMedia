package com.cm.media.util;

import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.*;
import com.cm.media.R;
import com.cm.media.repository.AppExecutor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class WebViewVodParser {
    public interface ParseResultCallback {
        void onSuccess(String ret);

        void onFailed(String msg);
    }

    private static final int TIMEOUT_MILLS = 6000;
    private String mUrl;
    private WebView mWebView;
    private ViewGroup mParent;
    private String[] mParseUrls;
    private volatile CountDownLatch latch;
    private volatile boolean parseSuccess = false;
    private volatile String mPlayUrl;

    public WebViewVodParser(ViewGroup parent, String url) {
        this.mUrl = url;
        mParseUrls = parent.getResources().getStringArray(R.array.cloud_parse_urls);
        mParent = parent;
    }

    private void prepare() {
        if (mWebView != null) {
            return;
        }
        mWebView = new WebView(mParent.getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(1, 1);
        mParent.addView(mWebView, params);
        mWebView.clearFocus();
        WebSettings settings = mWebView.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mWebView.getSettings().setSafeBrowsingEnabled(false);
        }

        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setDisplayZoomControls(false);
        settings.setUseWideViewPort(true);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);
        settings.setSupportZoom(true);
        settings.setAllowContentAccess(true);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSavePassword(true);
        settings.setSaveFormData(true);
        settings.setJavaScriptEnabled(true);
        settings.setTextZoom(100);
        settings.setDomStorageEnabled(true);
        settings.setSupportMultipleWindows(true);
        if (Build.VERSION.SDK_INT >= 21) {
            settings.setMixedContentMode(0);
        }

        if (Build.VERSION.SDK_INT >= 17) {
            settings.setMediaPlaybackRequiresUserGesture(true);
        }

        if (Build.VERSION.SDK_INT >= 16) {
            settings.setAllowFileAccessFromFileURLs(true);
            settings.setAllowUniversalAccessFromFileURLs(true);
        }

        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(mParent.getContext().getCacheDir().getAbsolutePath());
        settings.setDatabaseEnabled(true);
        settings.setGeolocationDatabasePath(mParent.getContext().getDir("database", 0).getPath());
        settings.setGeolocationEnabled(true);
        CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT < 21) {
            CookieSyncManager.createInstance(mParent.getContext().getApplicationContext());
        }

        cookieManager.setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= 21) {
            cookieManager.setAcceptThirdPartyCookies(mWebView, true);
        }
        mWebView.setWebViewClient(new Client());

        CookieManager var2 = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT < 21) {
            CookieSyncManager.createInstance(mParent.getContext());
        }

        var2.setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= 21) {
            var2.setAcceptThirdPartyCookies(mWebView, true);
        }
    }

    public void start(ParseResultCallback callback) {
        AppExecutor.getInstance().execute(() -> {
            for (int i = 0; i < mParseUrls.length; i++) {
                parseSuccess = false;
                final int index = i;
                AppExecutor.getInstance().uiPost(() -> {
                    prepare();
                    mWebView.stopLoading();
                    mWebView.loadUrl(mParseUrls[index] + mUrl);
                });
                latch = new CountDownLatch(1);
                try {
                    latch.await(TIMEOUT_MILLS, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (parseSuccess) {
                    if (callback != null) {
                        AppExecutor.getInstance().uiPost(() -> callback.onSuccess(mPlayUrl));
                    }
                    break;
                } else if (i == mParseUrls.length - 1) {
                    if (callback != null) {
                        AppExecutor.getInstance().uiPost(() -> callback.onFailed("解析超时."));
                    }
                }

            }
            AppExecutor.getInstance().uiPost(() -> {
                mWebView.stopLoading();
                mWebView.clearCache(true);
                mWebView = null;
            });
        });


    }

    private class Client extends WebViewClient {
        private Client() {
        }

        public void onPageStarted(WebView var1, String var2, Bitmap var3) {
            super.onPageStarted(var1, var2, var3);
            log("onPageStarted," + var2);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            log("onPageFinished," + url);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
            log("onLoadResource," + url);
        }

        public void onReceivedSslError(WebView webView, SslErrorHandler errorHandler, SslError error) {
            errorHandler.proceed();
        }

        public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest request) {
            log("shouldInterceptRequest," + request.getUrl());
            if (Build.VERSION.SDK_INT >= 21) {
                if (isVodUrl(request.getUrl().toString())) {
                    mPlayUrl = request.getUrl().toString();
                    parseSuccess = true;
                    if (latch != null) {
                        latch.countDown();
                    }
                }
            }

            return super.shouldInterceptRequest(webView, request);
        }

        public WebResourceResponse shouldInterceptRequest(WebView var1, String url) {
            log("shouldInterceptRequest.old," + url);
            if (isVodUrl(url)) {
                mPlayUrl = url;
                parseSuccess = true;
                if (latch != null) {
                    latch.countDown();
                }
            }
            return super.shouldInterceptRequest(var1, url);
        }

        @RequiresApi(api = 21)
        public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest request) {
            log("shouldOverrideUrlLoading," + request.getUrl().toString());
            boolean intent = request.getUrl().toString().startsWith("intent");
            boolean var3 = true;
            if (!intent) {
                if (request.getUrl().toString().startsWith("youku")) {
                    return true;
                }

                var3 = super.shouldOverrideUrlLoading(webView, request);
            }
            return var3;
        }

        public boolean shouldOverrideUrlLoading(WebView var1, String var2) {
            log("shouldOverrideUrlLoading.old," + var2);
            boolean var4 = var2.startsWith("intent");
            boolean var3 = true;
            if (!var4) {
                if (var2.startsWith("youku")) {
                    return true;
                }

                var3 = super.shouldOverrideUrlLoading(var1, var2);
            }
            return var3;
        }

    }

    private void log(String msg) {
        Log.i("webview", msg);
    }

    private boolean isVodUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        Uri uri = Uri.parse(url);
        String path = uri.getPath();
        return path != null && path.contains("m3u");
    }
}
