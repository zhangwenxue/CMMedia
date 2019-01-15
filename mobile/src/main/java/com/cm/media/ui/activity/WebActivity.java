package com.cm.media.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.*;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import com.cm.media.R;
import com.cm.media.databinding.ActivityWebBinding;

import static android.view.KeyEvent.KEYCODE_BACK;

public class WebActivity extends BaseThemeActivity {
    private static final String BAIDU_URL = "https://m.baidu.com";
    private static final String BD_SEARCH = "<form action=\"https://m.baidu.com/s\"  method=\"get\" target=\"_blank\">\n" +
            "<input type=\"text\" id=\"search-input\" name=\"keywords\" placeholder=\"Search\" />\n" +
            "<input type=\"submit\" value=\"Search\" />\n" +
            "</form>\n";
    private ActivityWebBinding binding;
    private String currentUrl;
    private String[] mSuggestionUrls;
    private SearchView mSearchView;

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
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_close_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initWebView();
        binding.webview.loadUrl(BAIDU_URL);
        binding.floatingBtn.setOnClickListener((v -> WebViewPlayActivity.startWebViewPlay(WebActivity.this, binding.webview.getUrl())));
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
                updateSearchText(currentUrl);
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
                binding.progressHorizontal.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                binding.progressHorizontal.setVisibility(View.INVISIBLE);
            }
        });
        binding.webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                binding.progressHorizontal.setProgress(newProgress);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.web_site, menu);
        MenuItem searchItem = menu.findItem(R.id.item_search);
        mSearchView = (SearchView) searchItem.getActionView();
        //searchView.setIconified(false);
        mSearchView.setQueryHint("输入搜索关键字或者网址");

        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String url;
                if (s.startsWith("http")) {
                    url = s;
                } else {
                    url = " https://m.baidu.com/s?wd=" + s;
                }
                binding.webview.loadUrl(url);
                startLoading(url);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mSuggestionUrls == null) {
            mSuggestionUrls = getResources().getStringArray(R.array.vod_web_urls);
        }
        if (item.getItemId() == R.id.item_iqiyi) {
            startLoading(mSuggestionUrls[0]);
        }
        if (item.getItemId() == R.id.item_youku) {
            startLoading(mSuggestionUrls[1]);
        }
        if (item.getItemId() == R.id.item_tencent) {
            startLoading(mSuggestionUrls[2]);
        }
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void startLoading(String url) {
        binding.webview.stopLoading();
        binding.webview.loadUrl(url);
        binding.webview.requestFocus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.webview.destroy();
    }

    private void updateSearchText(String text) {
        mSearchView.setQuery(text, false);
    }
}
