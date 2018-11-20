package com.cm.media.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.cm.media.R;
import com.cm.media.ui.fragment.PlayerFragment;

public class VodPlayerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vod_player);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, PlayerFragment.newInstance())
                    .commitNow();
        }
    }
}
