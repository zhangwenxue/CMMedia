package com.cm.media.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.*;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.webkit.SafeBrowsingResponseCompat;
import androidx.webkit.WebResourceErrorCompat;
import androidx.webkit.WebViewClientCompat;
import com.cm.media.R;
import com.cm.media.repository.AppExecutor;
import kotlin.text.StringsKt;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static android.webkit.WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE;

public class WebViewVodParser {
    public interface ParseResultCallback {
        void onSuccess(String ret);

        void onFailed(String msg);
    }

    private static final int TIMEOUT_MILLS = 15 * 1000;
    private String mUrl;
    private WebView mWebView;
    private String[] mParseUrls;
    private volatile CountDownLatch latch;
    private volatile boolean parseSuccess = false;
    private volatile String mPlayUrl;
    private ViewGroup mWebViewContainer;

    public WebViewVodParser(Activity activity) {
        mWebViewContainer = activity.findViewById(android.R.id.content);
        mParseUrls = activity.getResources().getStringArray(R.array.cloud_parse_urls);
        prepare(mWebViewContainer);
    }

    @SuppressLint({"RequiresFeature"})
    private void prepare(ViewGroup container) {
        if (mWebView != null) {
            return;
        }
        mWebView = new WebView(container.getContext());

        mWebView.setLayoutParams(new LinearLayout.LayoutParams(1, 1));
        container.addView(mWebView);

        mWebView.clearFocus();
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
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
        settings.setSaveFormData(true);
        settings.setJavaScriptEnabled(false);
        settings.setTextZoom(100);
        settings.setDomStorageEnabled(true);
        settings.setSupportMultipleWindows(true);
        settings.setBlockNetworkImage(true);
        settings.setBlockNetworkImage(false);
        settings.setLoadsImagesAutomatically(false);
        settings.setMixedContentMode(MIXED_CONTENT_COMPATIBILITY_MODE);

        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);

        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(container.getContext().getCacheDir().getAbsolutePath());
        settings.setDatabaseEnabled(true);
        settings.setGeolocationEnabled(false);
        CookieManager cookieManager = CookieManager.getInstance();

        cookieManager.setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= 21) {
            cookieManager.setAcceptThirdPartyCookies(mWebView, true);
        }
        mWebView.setWebViewClient(new ClientCompat());
    }

    public void start(String url, ParseResultCallback callback) {
        this.mUrl = StringsKt.split(url, new String[]{"?"}, false, 6).get(0);
        AppExecutor.getInstance().execute(() -> {
            for (int i = 0; i < mParseUrls.length; i++) {
                parseSuccess = false;
                final int index = i;
                AppExecutor.getInstance().uiPost(() -> {
                    if (mWebView != null) {
                        mWebView.stopLoading();
                        mWebView.loadUrl(mParseUrls[index] + mUrl);
                    }
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
                if (mWebView != null) {
                    mWebView.stopLoading();
                    mWebView.clearCache(true);
                    mWebView = null;
                }
            });
        });


    }


    private class ClientCompat extends WebViewClientCompat {

        @Override
        public void onReceivedError(@NonNull WebView view, @NonNull WebResourceRequest request, @NonNull WebResourceErrorCompat error) {
            super.onReceivedError(view, request, error);
        }

        @Override
        public void onReceivedHttpError(@NonNull WebView view, @NonNull WebResourceRequest request, @NonNull WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
        }

        @Override
        public void onSafeBrowsingHit(@NonNull WebView view, @NonNull WebResourceRequest request, int threatType, @NonNull SafeBrowsingResponseCompat callback) {
            super.onSafeBrowsingHit(view, request, threatType, callback);
        }

        @Override
        public boolean shouldOverrideUrlLoading(@NonNull WebView view, @NonNull WebResourceRequest request) {
            if (request.getUrl().toString().startsWith("intent") || request.getUrl().toString().startsWith("youku")) {
                return true;
            } else {
                return super.shouldOverrideUrlLoading(view, request);
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("intent") || url.startsWith("youku")) {
                return true;
            } else {
                return super.shouldOverrideUrlLoading(view, url);
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }


        @Nullable
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            onInterceptRequest(url);
            return super.shouldInterceptRequest(view, url);
        }

        @Nullable
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                String url = request.getUrl().toString();
                onInterceptRequest(request.getUrl().toString());
            }
            return super.shouldInterceptRequest(view, request);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }


        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }

        @Override
        public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
            super.onReceivedClientCertRequest(view, request);
        }

    }

    private void onInterceptRequest(String url) {
        if (VodFormatUtils.isVod(url)) {
            if (url.contains("url=")) {
                url = url.split("url=")[1];
            }
            mPlayUrl = url;
            parseSuccess = true;
            if (latch != null) {
                latch.countDown();
            }
        }
    }

    private void log(String msg) {
        Log.i("webview", msg);
    }

    public void stop() {
        if (mWebView != null) {
            mWebView.stopLoading();
        }
    }

    public void release() {
        if (mWebView != null) {
            mWebView.stopLoading();
            mWebView.destroy();
            mWebViewContainer.removeView(mWebView);
        }
        mWebView = null;
    }
}
