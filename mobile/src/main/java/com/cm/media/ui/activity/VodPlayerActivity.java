package com.cm.media.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import com.cm.media.R;
import com.cm.media.ui.fragment.PlayerFragment;

public class VodPlayerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_vod_player);
        int vid = getIntent().getIntExtra("vid", 0);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, PlayerFragment.newInstance(vid))
                    .commitNow();
        }
    }

    public static void startVodPlay(Context context, int vid) {
        Intent intent = new Intent(context, VodPlayerActivity.class);
        intent.putExtra("vid", vid);
        context.startActivity(intent);
    }
}
