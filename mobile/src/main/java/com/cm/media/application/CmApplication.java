package com.cm.media.application;

import android.app.Application;
import com.cm.dlna.DLNAManager;

public class CmApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DLNAManager.getInstance().init(this, null);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        DLNAManager.getInstance().release();
    }
}
