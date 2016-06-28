package com.mozilla.hackathon.kiboko;

import java.util.TimeZone;

public class Config {

    // Warning messages for dogfood build
    public static final String DOGFOOD_BUILD_WARNING_TITLE = "DOGFOOD BUILD";

    public static final String DOGFOOD_BUILD_WARNING_TEXT = "Shhh! This is a pre-release build "
            + "of the I/O app. Don't show it around.";

    public static final TimeZone TIMEZONE = TimeZone.getDefault();


    // YouTube share URL
    public static final String YOUTUBE_SHARE_URL_PREFIX = "http://youtu.be/";

    // Play store URL prefix
    public static final String PLAY_STORE_URL_PREFIX
            = "https://play.google.com/store/apps/details?id=";

}

