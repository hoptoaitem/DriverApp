package com.aitemconnect.driverapp.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.aitemconnect.driverapp.R;
import com.aitemconnect.driverapp.pojo.order.OrderPojo;
import com.aitemconnect.driverapp.ui.availableOrders.AvailableOrderActivity;
import com.aitemconnect.driverapp.ui.dashboard.DashActivity;
import com.aitemconnect.driverapp.ui.dashboard.DashboardViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.installations.InstallationTokenResult;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.etUsername)
    EditText etUsername;

    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.buttonLogin)
    Button buttonLogin;

    @BindView(R.id.progressBarLogin)
    ProgressBar progressBarLogin;

    LoginViewModel loginViewModel;
    DashboardViewModel dashboardViewModel;
    private static final String TAG = "LoginActivity";

//    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(LoginActivity.this);

        String drivPrefs = getResources().getString(R.string.driversSharedPrefs);
        SharedPreferences sharedPreferences = getSharedPreferences(drivPrefs, MODE_PRIVATE);


        loginViewModel = ViewModelProviders
                .of(this)
                .get(LoginViewModel.class);

        dashboardViewModel = ViewModelProviders
                .of(this)
                .get(DashboardViewModel.class);


        loginViewModel.loginSuccess.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(getString(R.string.isuserloggedin), true);
                editor.apply();


                //Check if has any available order
                // Get the orders // Check if there is any LOOKING_FOR_DRIVER order OR just completed orders
                dashboardViewModel.getOrders(LoginActivity.this);

            }
        });


        loginViewModel.failedToLogin.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String stringe) {
                Toast.makeText(LoginActivity.this, "" + stringe, Toast.LENGTH_SHORT).show();

                buttonLogin.setVisibility(View.VISIBLE);
                progressBarLogin.setVisibility(View.GONE);
            }
        });


        // No order available to accept
        dashboardViewModel.requestFailed.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                toDashboard();
                Log.d(TAG, "onChanged: request failled for checking available order");
            }
        });

        // There is order available to accept
        dashboardViewModel.orderResult.observe(this, new Observer<ArrayList<OrderPojo>>() {
            @Override
            public void onChanged(ArrayList<OrderPojo> orderPojos) {

                // Check if there is any available order to accept
                if (orderPojos.size() > 0) {
                    OrderPojo orderPojo = orderPojos.get(0);
                    String orderStatus = orderPojo.getOrderStatus();

                    if (orderStatus.equalsIgnoreCase("LOOKING_FOR_DRIVER")) {
                        // To available order
                        Intent intent = new Intent(LoginActivity.this,
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


    @OnClick(R.id.buttonLogin)
    public void Onclick(View view) {
        int viewId = view.getId();

        if (viewId == R.id.buttonLogin) {

            String userName = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            String drivPrefs = getResources().getString(R.string.driversSharedPrefs);
            SharedPreferences sharedPreferences = getSharedPreferences(drivPrefs, MODE_PRIVATE);

            String deviceToken = sharedPreferences
                    .getString(getResources().getString(R.string.deviceToken), "null");

            if (userName == "" || userName.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter username", Toast.LENGTH_SHORT).show();
            } else if (password == "" || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
            } else if (deviceToken == null || deviceToken == "" || deviceToken.isEmpty()) {
                Toast.makeText(LoginActivity.this, "No token found, Please restart the app", Toast.LENGTH_SHORT).show();
            } else {
                // Login
                loginViewModel.loginVM(userName, password, LoginActivity.this);
                buttonLogin.setVisibility(View.GONE);
                progressBarLogin.setVisibility(View.VISIBLE);
            }
        }
    }


    void toLoginPage() {
        Intent loginIntent = new Intent(LoginActivity.this, LoginActivity.class);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(loginIntent);
        finish();
    }

    void toDashboard() {
        Intent dash = new Intent(LoginActivity.this, DashActivity.class);
        dash.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(dash);
        finish();
    }

}
