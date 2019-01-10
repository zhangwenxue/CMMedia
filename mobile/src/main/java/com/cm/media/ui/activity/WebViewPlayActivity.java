package com.cm.media.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.*;
import android.widget.*;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.cm.dlna.DLNAManager;
import com.cm.dlna.DLNAPlayer;
import com.cm.media.R;
import com.cm.media.util.AdFilterTool;
import org.fourthline.cling.controlpoint.ControlPoint;
import org.fourthline.cling.model.meta.Device;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WebViewPlayActivity extends AppCompatActivity {

    private String url;
    private WebView mWebView;
    private Intent intent;
    private Set<String> urlSet = new HashSet<>();
    private BaseAdapter mAdapter;
    private DLNAPlayer mPlayer;
    private Device mDevice;
    private ControlPoint mControlPoint;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_webview_play);
        initWebView();

        mAdapter = new CustomAdapter(this, android.R.layout.simple_list_item_single_choice, DLNAManager.getInstance(this).getDeviceList());
        mPlayer = new DLNAPlayer();
        DLNAManager.getInstance(this).setOnDeviceRefreshListener(() -> {
            if (mControlPoint == null) {
                mControlPoint = DLNAManager.getInstance(WebViewPlayActivity.this).getControlPoint();
            }
            mAdapter.notifyDataSetChanged();
        });
    }

    public void onClick(View v) {
        showDLNADevices();
    }

    @Override
    protected void onResume() {
        //横屏显示
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
    }

    public static void startWebViewPlay(Context context,String url){
        Intent intent = new Intent(context,WebViewPlayActivity.class);
        intent.putExtra("url",url);
        context.startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initWebView() {
        intent = getIntent();
        url = intent.getStringExtra("url");
        url = "http://app.baiyug.cn:2019/vip/index.php?url=" + url;
        mWebView = findViewById(R.id.webView_play);
        //获取webview的配置
        WebSettings settings = mWebView.getSettings();
        //配置支持domstorage
        settings.setDomStorageEnabled(true);//启用或禁用DOM缓存
        settings.setAppCacheEnabled(false);//关闭/启用应用缓存
        settings.setSupportZoom(true);//是否可以缩放，默认true
        //settings.setBuiltInZoomControls(false);//是否显示缩放按钮，默认false
        settings.setJavaScriptEnabled(true);
        settings.setAllowContentAccess(true);
        settings.setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        settings.setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setUseWideViewPort(true); // 关键点
        settings.setDomStorageEnabled(true);

        settings.setAllowFileAccess(true); // 允许访问文件
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView webView, String url) {
                //判断是否是广告相关的资源链接
                if (!TextUtils.isEmpty(url)) {
                    if (url.contains(".html") || url.contains(".html") || url.contains(".jpeg")
                            || url.contains("png") || url.contains(".html") || url.contains(".xml")
                            || url.contains(".jpg") || url.contains(".xml") || url.contains(".js")
                            || url.contains(".css") || url.contains(".json") || url.contains(".gif") || url.contains(".ico")) {

                    } else {
                        urlSet.add(url);
                    }
                }
                if (!AdFilterTool.isAd(webView.getContext(), url)) {
                    //这里是不做处理的数据
                    return super.shouldInterceptRequest(webView, url);
                } else {
                    //有广告的请求数据，我们直接返回空数据，注：不能直接返回null
                    return new WebResourceResponse(null, null, null);
                }
            }
        });

        mWebView.loadUrl(url);
    }

    class CustomAdapter extends ArrayAdapter<Device> {


        public CustomAdapter(Context context, int resource, List<Device> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            WebViewPlayActivity.CustomAdapter.ViewHolder holder;
            if (convertView == null) {
                holder = new WebViewPlayActivity.CustomAdapter.ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_single_choice, parent, false);
                holder.textView = convertView.findViewById(android.R.id.text1);
                convertView.setTag(holder);
            } else {
                holder = (WebViewPlayActivity.CustomAdapter.ViewHolder) convertView.getTag();
            }
            Device device = getItem(position);
            holder.textView.setText(device.getDetails().getFriendlyName());
            return convertView;
        }

        class ViewHolder {
            TextView textView;
        }
    }


    private Dialog mDLNADeviceDialog;

    private void showDLNADevices() {
        if (mDLNADeviceDialog == null) {

            mDLNADeviceDialog = new Dialog(this, android.R.style.Theme_Material_Dialog);
            View view = getLayoutInflater().inflate(R.layout.dlg_dlna_devices, null);
            mDLNADeviceDialog.setContentView(view);

            ListView listView = view.findViewById(R.id.listView);
            mAdapter = new CustomAdapter(this, android.R.layout.simple_list_item_single_choice, DLNAManager.getInstance(this).getDeviceList());
            listView.setAdapter(mAdapter);

            view.findViewById(R.id.refresh).setOnClickListener(v -> DLNAManager.getInstance(WebViewPlayActivity.this).search());
            listView.setOnItemClickListener((parent, view1, position, id) -> {
                mDLNADeviceDialog.dismiss();
                mDevice = (Device) mAdapter.getItem(position);
                mPlayer.setUp(mDevice, mControlPoint);
                final String[] arrays = new String[urlSet.size()];
                urlSet.toArray(arrays);
                AlertDialog.Builder builder = new AlertDialog.Builder(WebViewPlayActivity.this)
                        .setItems(arrays, (dialog, which) -> {
                            mPlayer.play("vod", arrays[which]);
                            dialog.dismiss();
                        });
                builder.create().show();
            });
        }
        mDLNADeviceDialog.show();
        DLNAManager.getInstance(WebViewPlayActivity.this).search();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
        mWebView = null;
    }
}
