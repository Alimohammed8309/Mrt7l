package com.mrt7l.helpers;


import android.content.Context;
import android.content.Intent;


public class BroadcastHelper {
    public static final String BROADCAST_EXTRA_METHOD_NAME = "method";
    public static final String NEW_SOS = "newSos";
    public static final String LanguageChanged = "languageChanged";
    public static final String ACTION_NAME = "searchFilter";
    public static final String UPDATE_LOCATION_METHOD = "updateLocation";
    public static final String NEW_NOTIFICATION = "newNotification";
    public static final String NEW_TASK = "newTask";


    public static void sendInform(Context context, String method) {
        Intent intent = new Intent();
        intent.setAction(ACTION_NAME);
        intent.putExtra(BROADCAST_EXTRA_METHOD_NAME, method);
        try {
            context.sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendInform(Context context, String method, Intent intent) {
        intent.setAction(ACTION_NAME);
        intent.putExtra(BROADCAST_EXTRA_METHOD_NAME, method);
        try {
            context.sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    public static void sendUpdateLocation(Context context, UpdateLocationViewModel updateLocationViewModel){
//        Intent intent = new Intent();
//        intent.setAction(BROADCAST_EXTRA_METHOD_NAME);
//        intent.putExtra(BROADCAST_EXTRA_METHOD_NAME, UPDATE_LOCATION_METHOD);
//        intent.putExtra(UPDATE_LOCATION_METHOD,updateLocationViewModel);
//        try {
//            context.sendBroadcast(intent);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
