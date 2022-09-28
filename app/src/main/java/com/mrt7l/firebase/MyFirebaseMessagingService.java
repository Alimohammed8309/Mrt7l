package com.mrt7l.firebase;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mrt7l.R;
import com.mrt7l.helpers.PreferenceHelper;
import com.mrt7l.ui.activity.RegisterActivity;
import com.onesignal.OneSignal;

import java.util.Objects;


/**
 * Created by mustafa on 7/26/17.
 * Release the GEEK
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        String TAG = "FCM";
 //        String userId = Objects.requireNonNull(OneSignal.getDeviceState()).getUserId();
//        sendRegistrationToServer(userId);
    }

    public Intent resultIntent;

    private void sendRegistrationToServer(String refreshedToken) {
        new PreferenceHelper(getApplicationContext()).setDeviceToken(refreshedToken);
        new PreferenceHelper(getApplicationContext()).setISDeviceTokenUpdated(true);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        try {
            resultIntent = new Intent(this, RegisterActivity.class);
            RemoteMessage.Notification notification = remoteMessage.getNotification();

            if (notification != null) {
                sendNotification(this, 1, notification.getTitle(), notification.getBody());

            }
        } catch (Exception e) {
            // FirebaseErrorEventLog.SaveEventLog(e);
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    public void sendNotification(Context context, int id, String title, String body) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "1:283530940482:android:4352bd2cf227f8a0425ddb";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            String channelName = "Mrt7l";
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(mChannel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        PendingIntent resultPendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE );
        } else
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
             resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        } else {
            resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        }
        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setAutoCancel(true);
        assert notificationManager != null;
        notificationManager.notify(0, mBuilder.build());
    }
}
