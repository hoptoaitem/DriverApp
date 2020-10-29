package in.aitemconnect.driverapp.ui.splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import in.aitemconnect.driverapp.R;
import in.aitemconnect.driverapp.adapter.CompletedOrdersAdapter;
import in.aitemconnect.driverapp.pojo.order.OrderPojo;
import in.aitemconnect.driverapp.ui.availableOrders.AvailableOrderActivity;
import in.aitemconnect.driverapp.ui.dashboard.DashActivity;
import in.aitemconnect.driverapp.ui.dashboard.DashboardViewModel;
import in.aitemconnect.driverapp.ui.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    DashboardViewModel dashboardViewModel;
    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        dashboardViewModel = ViewModelProviders
                .of(SplashActivity.this)
                .get(DashboardViewModel.class);

        String drivPrefs = getResources().getString(R.string.driversSharedPrefs);
        SharedPreferences sharedPreferences = getSharedPreferences(drivPrefs, MODE_PRIVATE);
        boolean isUserLoggedIn = sharedPreferences.getBoolean(getString(R.string.isuserloggedin), false);

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
