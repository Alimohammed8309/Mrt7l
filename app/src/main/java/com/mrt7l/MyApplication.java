package com.mrt7l;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.onesignal.OneSignal;


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
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(mInstance);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
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

