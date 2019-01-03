package com.cm.media.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.cm.media.R;

public class BaseThemeActivity extends AppCompatActivity {
    private static final String SHARED_FILE_NAME = "config";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(SHARED_FILE_NAME, MODE_PRIVATE);
        getWindow().setNavigationBarColor(getResources().getColor(R.color.backgroundLight));
    }

    @Override
    protected void onStart() {
        super.onStart();
        float brightness = sharedPreferences.getFloat("brightness", -1);
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.screenBrightness = brightness;
        window.setAttributes(layoutParams);
    }
}
