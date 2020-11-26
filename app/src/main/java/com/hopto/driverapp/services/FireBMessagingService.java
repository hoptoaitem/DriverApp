package com.hopto.driverapp.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.hopto.driverapp.R;
import com.hopto.driverapp.ui.dashboard.DashActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class FireBMessagingService extends FirebaseMessagingService {

    // DEVICE ID
    // eFeO-D9wQGuL5e4txTNRJP:APA91bFXpfWRhofALhpejnKX7unFtmQfjDrUbijp-fMsT5uegj-5mcr45IPgQBOB0SCDL7bKM08TZPf_Acf5puVsGjeljamaHm_PQZ-cCrZ_H8D2PRqa9DUlPpoJEE_CP-80P1vol6qA
    // kanQ+WD7Q7ADapG03Bdm4lTXzkrgcy3WFsjh/jYa6UFTcTspXuSI97Tua+iGSSGAadc2gcii0Xz7jtzE+fgnZg==
    private static final String TAG = "FireBMessagingService";

    @Override
    public void onNewToken(@NonNull String newToken) {
        super.onNewToken(newToken);

//        getSharedPreferences(getString(R.string.driversSharedPrefs),
//                MODE_PRIVATE).edit().putString(getString(R.string.deviceToken), newToken).apply();

        Log.d(TAG, "check this onNewToken: " + newToken);
    }


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

//        RemoteMessage.Notification notification = remoteMessage.getNotification();
//        Map<String, String> data = remoteMessage.getData();

//        Log.e("mnotificatin", "" + notification.getBody());
//        Log.e("mnotificatin", "" + notification.getImageUrl());
//        Log.e("mnotificatin", "" + notification.getBody());

//        showNotification(notification, data);

        showNotification(remoteMessage.getNotification().getTitle(),
                remoteMessage.getNotification().getBody());
    }

    private void showNotification(RemoteMessage.Notification notification,
                                  Map<String, String> data) {
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.hopto_foreground);

        Intent intent = new Intent(this, DashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "channel_id")
                .setContentTitle(notification.getTitle() + " by service")
                .setContentText(notification.getBody() + " by service")
                .setAutoCancel(true)
//                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
//                .setContentIntent(pendingIntent)
                .setContentInfo(notification.getTitle())
//                .setColor(Color.RED)
//                .setLights(Color.BLACK, 1000, 300)
//                .setDefaults(Notification.DEFAULT_VIBRATE)
//                .setLargeIcon(icon)
                .setSmallIcon(R.mipmap.hopto_foreground);

      /*  try {
            if (notification.getImageUrl() != null) {
                URL url = new URL(notification.getImageUrl().toString());
                Bitmap bigPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                notificationBuilder.setStyle(
                        new NotificationCompat.BigPictureStyle().bigLargeIcon(bigPicture).bigPicture(bigPicture).setSummaryText(notification.getBody() + " by service expanding notification")
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);

        // Notification Channel is required for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "channel_id", "channel_name",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("channel description");
            channel.setShowBadge(true);
            channel.canShowBadge();
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(878, notificationBuilder.build());
    }


    public void showNotification(String title, String message) {

        String CHANNEL_ID = "878";

        Uri soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + getApplicationContext().getPackageName() + "/" + R.raw.notification_bell);

        NotificationManager notificationManager = (NotificationManager) getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);

        // For api 26+
        NotificationChannel notificationChannel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(CHANNEL_ID, "channelName", NotificationManager.IMPORTANCE_MAX);
            notificationChannel.setLightColor(Color.GREEN);
            notificationChannel.enableLights(true);
            notificationChannel.setDescription("Notification for driver");

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();

            notificationChannel.setSound(soundUri, audioAttributes);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,
                CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setVibrate(new long[]{0, 500, 1000})
                .setDefaults(Notification.DEFAULT_LIGHTS)
                .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getApplicationContext().getPackageName() + "/" + R.raw.notification_bell))
                .setSmallIcon(R.mipmap.hopto_foreground);

        notificationManager.notify(878, builder.build());
//        NotificationManagerCompat notificationManagerCompat =
//                NotificationManagerCompat.from(this);
//
//        notificationManagerCompat.notify(878, builder.build());
    }
}
