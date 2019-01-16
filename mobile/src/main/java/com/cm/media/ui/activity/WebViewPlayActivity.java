package com.cm.media.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.cm.media.databinding.ActivityWebviewPlayBinding;
import com.cm.media.ui.fragment.CMDialog;
import com.cm.media.ui.widget.player.SuperPlayerConst;
import com.cm.media.ui.widget.player.SuperPlayerModel;
import com.cm.media.ui.widget.player.SuperPlayerView;
import com.cm.media.ui.widget.player.util.TCTimeUtils;
import com.cm.media.util.WebViewParser;
import com.google.android.material.snackbar.Snackbar;

import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

public class WebViewPlayActivity extends AppCompatActivity implements SuperPlayerView.PlayerViewCallback {
    private ActivityWebviewPlayBinding binding;

    private CMDialog mParseDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {
            this.getWindow().addFlags(FLAG_TRANSLUCENT_STATUS);
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        binding = ActivityWebviewPlayBinding.inflate(getLayoutInflater());
        setContentView(binding.root);

        mParseDialog = new CMDialog();

        String mWebUrl = getIntent().getStringExtra("url");
        if (TextUtils.isEmpty(mWebUrl)) {
            Toast.makeText(this, "视频地址无效！", Toast.LENGTH_SHORT).show();
            finish();
        }
        WebViewParser mParser = new WebViewParser();
        String parseUrl = "http://app.baiyug.cn:2019/vip/index.php?url=" + mWebUrl;
        mParser.setCallback(new WebViewParser.Callback() {
            @Override
            public void onSuccess(String url) {
                mParseDialog.onSuccess();
                startPlay(url);
                dismissNaviBar();
            }

            @Override
            public void onFailed() {
                mParseDialog.showError(getSupportFragmentManager());
            }
        });
        mParseDialog.showLoading(getSupportFragmentManager());
        mParser.start(this, parseUrl);
        binding.playerView.setPlayerViewCallback(this);
        binding.playerView.setPlayCallback(new SuperPlayerView.PlayCallback() {
            @Override
            public void onStartPlay() {
            }

            @Override
            public void onPause() {
            }

            @Override
            public void onFinished() {
                super.onFinished();
                exitPlay();
            }
        });
    }

    private void startPlay(String url) {
        SuperPlayerModel superPlayerModel = new SuperPlayerModel();
        superPlayerModel.title = "网络视频";
        superPlayerModel.videoURL = url;
        binding.playerView.playWithMode(superPlayerModel, null);
    }

    public void onClick(View v) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (binding.playerView.getPlayState() == SuperPlayerConst.PLAYSTATE_PLAY) {
            binding.playerView.onResume();
            if (binding.playerView.getPlayMode() == SuperPlayerConst.PLAYMODE_FLOAT) {
                binding.playerView.requestPlayMode(SuperPlayerConst.PLAYMODE_WINDOW);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (binding.playerView.getPlayMode() != SuperPlayerConst.PLAYMODE_FLOAT) {
            binding.playerView.onPause();
        }
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    public static void startWebViewPlay(Context context, String url) {
        Intent intent = new Intent(context, WebViewPlayActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.playerView.release();
        if (binding.playerView.getPlayMode() != SuperPlayerConst.PLAYMODE_FLOAT) {
            binding.playerView.resetPlayer();
        }
    }

    @Override
    public void hideViews() {

    }

    @Override
    public void showViews() {

    }

    @Override
    public void onQuit(int playMode) {
        if (playMode == SuperPlayerConst.PLAYMODE_WINDOW) {
            exitPlay();
        }
    }

    private void exitPlay() {
        if (!isFinishing()) {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (!binding.playerView.onBackKeyPressed()) {
            super.onBackPressed();
        }
    }

    private void dismissNaviBar() {
        if (binding.playerView.getPlayMode() != SuperPlayerConst.PLAYMODE_FULLSCREEN) {
            return;
        }
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION //隐藏导航栏
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN; // hide status bar
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            dismissNaviBar();
        }
    }
}
