package com.cm.media.util;

import android.content.Context;
import android.content.res.Resources;
import com.cm.media.R;


public class AdFilterTool {
    public static boolean isAd(Context context, String url) {
        Resources res = context.getResources();
        String[] filterUrls = res.getStringArray(R.array.adUrls);
        for (String adUrl : filterUrls ) {
            if (url.contains(adUrl)) {
                return true;
            }
        }
        return false;
    }
}

