package com.cm.media.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.cm.media.R;
import com.cm.media.ui.fragment.PlayerFragment;

public class VodPlayerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
