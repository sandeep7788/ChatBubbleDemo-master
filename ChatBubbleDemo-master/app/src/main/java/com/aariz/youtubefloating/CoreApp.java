package com.aariz.youtubefloating;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;

public class CoreApp extends Application {
    private static CoreApp mInstance;

    public static synchronized CoreApp getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        MobileAds.initialize(this, "ca-app-pub-3885072453336309~6509713876");
    }
}
