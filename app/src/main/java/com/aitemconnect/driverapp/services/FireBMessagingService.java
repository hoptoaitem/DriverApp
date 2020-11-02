package com.aitemconnect.driverapp.services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.aitemconnect.driverapp.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FireBMessagingService extends FirebaseMessagingService {

    // DEVICE ID
    // eFeO-D9wQGuL5e4txTNRJP:APA91bFXpfWRhofALhpejnKX7unFtmQfjDrUbijp-fMsT5uegj-5mcr45IPgQBOB0SCDL7bKM08TZPf_Acf5puVsGjeljamaHm_PQZ-cCrZ_H8D2PRqa9DUlPpoJEE_CP-80P1vol6qA
    // kanQ+WD7Q7ADapG03Bdm4lTXzkrgcy3WFsjh/jYa6UFTcTspXuSI97Tua+iGSSGAadc2gcii0Xz7jtzE+fgnZg==
    private static final String TAG = "FireBMessagingService";

    @Override
    public void onNewToken(@NonNull String newToken) {
        super.onNewToken(newToken);

        getSharedPreferences(getString(R.string.driversSharedPrefs),
                MODE_PRIVATE).edit().putString("DeviceToken", newToken).apply();

        Log.d(TAG, "onNewToken: " + newToken);
    }


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        showNotification(remoteMessage.getNotification().getTitle(),
                remoteMessage.getNotification().getBody());
    }

    public void showNotification(String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,
                "MyNotification")
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_aitem_foreground);

        NotificationManagerCompat notificationManagerCompat =
                NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(878, builder.build());
    }
}
