package com.cm.media.util;

public class Main {
    public static void main(String args[]) {
        long time = System.currentTimeMillis() / 1000;
        String token = UrlParser.getToken(time, "http://test.vod.bokecs.net/dianying/49681/tiexuezhanshi.m3u8");
        System.out.println("time:" + time + "\ntoken:" + token);
        String value = "s1;s2;s3;s4";
        System.out.println(value.substring(0,value.lastIndexOf(";")));

    }
}
