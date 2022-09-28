//package com.mrt7l.huaweihms;
//
//import android.util.Log;
//
//import com.huawei.hms.push.HmsMessageService;
//import com.mrt7l.helpers.PreferenceHelper;
//import com.onesignal.OneSignalHmsEventBridge;
//
//public class HmsService extends HmsMessageService {
//    public void onNewToken(String token) {
//        // ...
//        // Forward event on to OneSignal SDK
//        OneSignalHmsEventBridge.onNewToken(this, token);
////        new PreferenceHelper(getApplicationContext()).setDeviceToken(token);
////        new PreferenceHelper(getApplicationContext()).setISDeviceTokenUpdated(true);
////        Log.v("hmsToken:",token);
//    }
//
//    @Override
//    public void onMessageReceived(com.huawei.hms.push.RemoteMessage remoteMessage) {
//        OneSignalHmsEventBridge.onMessageReceived(this, remoteMessage);
//    }
//
//
//}