package com.cm.media.ui.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.cm.media.R;
import com.cm.media.ui.fragment.DiscoverFragment;
import com.cm.media.ui.fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import io.alterac.blurkit.BlurLayout;

import java.lang.ref.WeakReference;

import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

public class MainActivity extends AppCompatActivity {
    private final SparseArray<Fragment> mFragmentArray = new SparseArray<>(3);
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                showFragment(getFragment(0));
                return true;
            case R.id.navigation_dashboard:
                showFragment(getFragment(1));
                return true;
            case R.id.navigation_notifications:
                return true;
            default:
                return false;
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.getWindow().addFlags(FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_main);
        ((BottomNavigationView) findViewById(R.id.navigation)).setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        showFragment(getFragment(0));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private Fragment getFragment(int index) {
        Fragment fragment = mFragmentArray.get(index);
        if (fragment == null) {
            switch (index) {
                case 0:
                    fragment = HomeFragment.newInstance();
                    break;
                case 1:
                    fragment = DiscoverFragment.newInstance();
                    break;
                default:
                    break;
            }
            if (fragment != null) {
                mFragmentArray.put(index, fragment);
            }
        }
        return fragment;
    }

    private void showFragment(Fragment fragment) {
        for (int i = 0; i < 3; i++) {
            if (mFragmentArray.get(i) == null) {
                continue;
            }
            if (mFragmentArray.get(i) == fragment) {
                if (fragment.isAdded()) {
                    getSupportFragmentManager().beginTransaction().show(fragment).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, fragment).commit();
                }
            } else {
                if (mFragmentArray.get(i).isAdded()) {
                    getSupportFragmentManager().beginTransaction().hide(mFragmentArray.get(i)).commit();
                }
            }
        }
    }
}
