package com.mrt7l;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.onesignal.Continue;
import com.onesignal.OneSignal;
import com.onesignal.debug.LogLevel;


/**
 * Created by AG on 12/24/2016.
 */
public class MyApplication extends Application {



    private static MyApplication mInstance;
    private static final String ONESIGNAL_APP_ID = "12c5cc33-d476-499a-abb6-7358b829fa8d";

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        MultiDex.install(mInstance);
        // Enable verbose OneSignal logging to debug issues if needed.
        // Enable verbose logging for debugging (remove in production)
        OneSignal.getDebug().setLogLevel(LogLevel.VERBOSE);
        // Initialize with your OneSignal App ID
        OneSignal.initWithContext(this, ONESIGNAL_APP_ID);
        // Use this method to prompt for push notifications.
        // We recommend removing this method after testing and instead use In-App Messages to prompt for notification permission.
//        if (BuildConfig.DEBUG) {
//            StrictMode.enableDefaults();
//
//            }

    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
       MultiDex.install(mInstance);
    }
}

