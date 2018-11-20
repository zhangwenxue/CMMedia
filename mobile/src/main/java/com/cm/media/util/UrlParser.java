package com.cm.media.util;

public class UrlParser {
    public static String getToken(long time, String url) {
        StringBuilder builder = new StringBuilder();
        String[] splits = url.split("/");
        for (int i = 3; i < splits.length; i++) {
            if (builder.length() > 0) {
                builder.append("/");
            }
            builder.append(splits[i]);
        }
        String value = builder.toString();
        String sb = "zToHXm3h7zzmnL" +
                '|' +
                time +
                '|' +
                value;
        return EncUtil.enc(sb);
    }

}

