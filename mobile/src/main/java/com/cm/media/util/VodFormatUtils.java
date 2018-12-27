package com.cm.media.util;

import android.text.TextUtils;

public class VodFormatUtils {
    private static final String[] FORMAT = {".mp4", ".m3u", ".m3u8", ".3gp", ".wmv", ".asf", ".flv", ".rm", ".rmvb", ".mov", ".m4v", ".asf", ".avi", ".vob", ".mkv"};

    private VodFormatUtils() {
    }

    public static String parse(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        if (url.contains("url=")) {
            url = url.substring(url.lastIndexOf("url="), url.length());
        }
        for (String format : FORMAT) {
            if (url.contains(format)) {
                return url;
            }
        }
        return null;
    }
}
