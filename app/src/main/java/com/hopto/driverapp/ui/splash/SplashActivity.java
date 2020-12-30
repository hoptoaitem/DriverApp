package com.hopto.driverapp.ui.splash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;

import com.hopto.driverapp.R;
import com.hopto.driverapp.pojo.order.OrderPojo;
import com.hopto.driverapp.ui.availableOrders.AvailableOrderActivity;
import com.hopto.driverapp.ui.dashboard.DashActivity;
import com.hopto.driverapp.ui.dashboard.DashboardViewModel;
import com.hopto.driverapp.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.installations.InstallationTokenResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class SplashActivity extends AppCompatActivity {

    /**
     * 1. device id is changing every time app opened
     *
     *
     */
    DashboardViewModel dashboardViewModel;
    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        dashboardViewModel = ViewModelProviders
                .of(SplashActivity.this)
                .get(DashboardViewModel.class);

        // Create channel for firebase notifications
        createNotificationChannel();

        subscribeToTopic();

        String drivPrefs = getResources().getString(R.string.driversSharedPrefs);
        SharedPreferences sharedPreferences = getSharedPreferences(drivPrefs, MODE_PRIVATE);
        boolean isUserLoggedIn = sharedPreferences.getBoolean(getString(R.string.isuserloggedin), false);
        String authKey = sharedPreferences.getString(getString(R.string.api_key_token), "null");
        String deviceTokene = sharedPreferences.getString(getString(R.string.deviceToken), "empty");

        Log.d(TAG, "onCreate: authKey " + authKey);
        Log.d(TAG, "onCreate: deviceTokene is " + deviceTokene);

        if (isUserLoggedIn) {
            // User is logged in
            dashboardViewModel.requestFailed.observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    toDashboard();
//                    Log.d(TAG, "onChanged: request failled for checking available order");
//                    Toast.makeText(DashActivity.this, "" + s, Toast.LENGTH_SHORT).show();
                }
            });

            dashboardViewModel.orderResult.observe(this, new Observer<ArrayList<OrderPojo>>() {
                @Override
                public void onChanged(ArrayList<OrderPojo> orderPojos) {

                    // Check if there is any available order to accept
                    if (orderPojos.size() > 0) {
                        OrderPojo orderPojo = orderPojos.get(0);
                        String orderStatus = orderPojo.getOrderStatus();

                        if (orderStatus.equalsIgnoreCase(getString(R.string.status_lookingForDriver)) ||
                                orderStatus.equalsIgnoreCase(getString(R.string.orderAcceptedByDriver))) {
                            // To available order
                            try {
                                Intent intent = new Intent(SplashActivity.this,
                                        AvailableOrderActivity.class);
                                intent.putExtra("order_pojo", orderPojo);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            // To dashboard
                            toDashboard();
                        }

                    } else {
                        // To dashboard
                        toDashboard();
                    }
                }
            });

        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isUserLoggedIn) {
//                    Toast.makeText(SplashActivity.this, "checking for available orders", Toast.LENGTH_SHORT).show();
                    // Get the orders // Check if there is any WAITING_ACCEPTANCE_FROM_DRIVER order OR just completed orders
                    dashboardViewModel.getOrders(SplashActivity.this);
                } else {
                    // To login page
                    toLoginPage();
                }
            }
        }, 2 * 1000);


        // get the device token
        FirebaseApp.initializeApp(this);
//        FirebaseInstanceId.getInstance().getInstanceId()
//                .addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
//                    @Override
//                    public void onSuccess(InstanceIdResult instanceIdResult) {
//                        // This is original token to send to server For FIREBASE...
//                        String token = instanceIdResult.getToken();
//                        Log.d(TAG, "onSuccess: real token is: " + token);
//
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putString(getString(R.string.deviceToken), token);
//                        editor.apply();
//
//                        //Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                        //shareIntent.setType("text/plain");
//                        //shareIntent.putExtra(Intent.EXTRA_TEXT,"Your score and Some extra text");
//                        //shareIntent.putExtra(Intent.EXTRA_SUBJECT, "The title");
//                        //startActivity(Intent.createChooser(shareIntent, "Share..."));
//
//                /*
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent.setType("text/plain");
//                intent.putExtra(Intent.EXTRA_TEXT, token);
//                startActivity(Intent.createChooser(intent, "shareeee"));*/
//
//                    }
//                });
//
//        FirebaseInstallations.getInstance().getToken(false)
//                .addOnSuccessListener(new OnSuccessListener<InstallationTokenResult>() {
//                    @Override
//                    public void onSuccess(InstallationTokenResult installationTokenResult) {
//                        String token = installationTokenResult.getToken();
//                        if (token != null || !token.isEmpty()) {
////                    Toast.makeText(LoginActivity.this, "token ere " + token, Toast.LENGTH_SHORT).show();
//                            Log.d(TAG, "onSuccess: real token 2: " + token);
//
//
//                        }
//                    }
//                });

    }

    void subscribeToTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic("test")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
//                            Toast.makeText(SplashActivity.this, "sussceess register", Toast.LENGTH_SHORT).show();
                        } else {
//                            Toast.makeText(SplashActivity.this, "failed register", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel =
                    new NotificationChannel("MyNotification", "MyNotification",
                            NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    void toLoginPage() {
        Intent loginIntent = new Intent(SplashActivity.this, LoginActivity.class);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(loginIntent);
        finish();
    }

    void toDashboard() {
        Intent dash = new Intent(SplashActivity.this, DashActivity.class);
        dash.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(dash);
        finish();
    }
}
