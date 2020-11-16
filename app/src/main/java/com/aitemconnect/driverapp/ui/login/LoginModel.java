package com.aitemconnect.driverapp.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.aitemconnect.driverapp.R;
import com.aitemconnect.driverapp.pojo.LogInPojo;
import com.aitemconnect.driverapp.pojo.LoginResultPojo;
import com.aitemconnect.driverapp.utils.ApiClient;
import com.aitemconnect.driverapp.utils.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginModel {

    LoginInterface loginInterface;
    ApiInterface apiInterface;
    private static final String TAG = "LoginModel";

    public LoginModel(LoginInterface loginInterface) {
        this.loginInterface = loginInterface;

        this.apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
    }

    interface LoginInterface {
        void loginResult(LoginResultPojo loginResultPojo);

        void failedToLogin();

        void loginSuccessful();
    }


    public void loginRequest(String userName, String password, Context context) {
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(context.getResources()
                        .getString(R.string.driversSharedPrefs), Context.MODE_PRIVATE);
        String deviceToken = sharedPreferences
                .getString(context.getResources().getString(R.string.deviceToken), "null");


        LogInPojo logInPojo = new LogInPojo();
        logInPojo.setUsername(userName);
        logInPojo.setPassword(password);
        logInPojo.setDeviceId(deviceToken);

        apiInterface.loginRequest(logInPojo).enqueue(new Callback<LoginResultPojo>() {
            @Override
            public void onResponse(Call<LoginResultPojo> call, Response<LoginResultPojo> response) {
                if (response.isSuccessful()) {
                    LoginResultPojo loginResultPojo = response.body();

                    // Save auth token to local db
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(context.getResources().getString(R.string.api_key_token), loginResultPojo.getAuthToken());
                    editor.apply();

//                    Intent intent = new Intent(Intent.ACTION_SEND);
//                    intent.setType("text/plain");
//                    intent.putExtra(Intent.EXTRA_TEXT, loginResultPojo.getAuthToken());
//                    context.startActivity(Intent.createChooser(intent, "shareeee"));

                    loginInterface.loginSuccessful();
                } else {
//                  Toast.makeText(context, "" + response.message(), Toast.LENGTH_SHORT).show();
                    loginInterface.failedToLogin();
                }
            }

            @Override
            public void onFailure(Call<LoginResultPojo> call, Throwable t) {
                Log.d(TAG, "fff onFailure: " + t.getMessage());
//                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

                loginInterface.failedToLogin();
            }
        });
    }
}
