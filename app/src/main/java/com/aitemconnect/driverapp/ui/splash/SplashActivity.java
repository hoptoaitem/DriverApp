package com.aitemconnect.driverapp.ui.splash;

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
import android.widget.Toast;

import java.util.ArrayList;

import com.aitemconnect.driverapp.R;
import com.aitemconnect.driverapp.pojo.order.OrderPojo;
import com.aitemconnect.driverapp.services.FireBMessagingService;
import com.aitemconnect.driverapp.ui.availableOrders.AvailableOrderActivity;
import com.aitemconnect.driverapp.ui.dashboard.DashActivity;
import com.aitemconnect.driverapp.ui.dashboard.DashboardViewModel;
import com.aitemconnect.driverapp.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class SplashActivity extends AppCompatActivity {

    DashboardViewModel dashboardViewModel;
    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Log.d(TAG, "onCreate: calleddd");
        dashboardViewModel = ViewModelProviders
                .of(SplashActivity.this)
                .get(DashboardViewModel.class);



        // Create channel for firebase notifications
        createNotificationChannel();

        subscribeToTopic();

        String drivPrefs = getResources().getString(R.string.driversSharedPrefs);
        SharedPreferences sharedPreferences = getSharedPreferences(drivPrefs, MODE_PRIVATE);
        boolean isUserLoggedIn = sharedPreferences.getBoolean(getString(R.string.isuserloggedin), false);
        String tokennn = sharedPreferences.getString(getString(R.string.api_key_token), "null");
        String deviceId = sharedPreferences.getString("DeviceToken", "empty");

        Log.d(TAG, "onCreate: tokennn " + tokennn);
        Log.d(TAG, "onCreate: deviceId " + deviceId);

        if (isUserLoggedIn) {
            // User is logged in
            dashboardViewModel.requestFailed.observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    toDashboard();
                    Log.d(TAG, "onChanged: request failled for checking available order");
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

                        if (orderStatus.equalsIgnoreCase("LOOKING_FOR_DRIVER")) {
                            // To available order
                            Intent intent = new Intent(SplashActivity.this,
                                    AvailableOrderActivity.class);
                            intent.putExtra("order_pojo", orderPojo);
                            startActivity(intent);

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
                    // Get the orders // Check if there is any LOOKING_FOR_DRIVER order OR just completed orders
                    dashboardViewModel.getOrders(SplashActivity.this);
                } else {
                    // To login page
                    toLoginPage();
                }
            }
        }, 2 * 1000);

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