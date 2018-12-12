package com.cm.media.application;

import android.app.Application;
import com.meizu.common.renderer.effect.GLRenderManager;

public class CmApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        GLRenderManager.getInstance().initialize(this);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        GLRenderManager.getInstance().trimMemory(level);
    }
}
