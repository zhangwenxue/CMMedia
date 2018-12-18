package com.cm.media.repository;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AppExecutor {
    private static AppExecutor instance;
    private final ExecutorService executor;
    private static final Handler UI_HANDLER = new Handler(Looper.getMainLooper());

    private AppExecutor() {
        executor = Executors.newCachedThreadPool();
    }

    public static AppExecutor getInstance() {
        if (instance == null) {
            synchronized (AppExecutor.class) {
                if (instance == null) {
                    instance = new AppExecutor();
                }
            }
        }
        return instance;
    }

    public Future<?> submit(Runnable runnable) {
        return executor.submit(runnable);
    }

    public void execute(Runnable runnable) {
        executor.execute(runnable);
    }

    public void uiPost(Runnable runnable) {
        UI_HANDLER.post(runnable);
    }

    public void uiPostDelayed(Runnable runnable, long timeMillSeconds) {
        UI_HANDLER.postDelayed(runnable, timeMillSeconds);
    }
}
