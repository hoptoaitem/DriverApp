package in.aitemconnect.driverapp.ui.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import in.aitemconnect.driverapp.R;
import in.aitemconnect.driverapp.pojo.LogInPojo;
import in.aitemconnect.driverapp.pojo.LoginResultPojo;
import in.aitemconnect.driverapp.utils.ApiClient;
import in.aitemconnect.driverapp.utils.ApiInterface;
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
        LogInPojo logInPojo = new LogInPojo();
        logInPojo.setUsername(userName);
        logInPojo.setPassword(password);

        apiInterface.loginRequest(logInPojo).enqueue(new Callback<LoginResultPojo>() {
            @Override
            public void onResponse(Call<LoginResultPojo> call, Response<LoginResultPojo> response) {
                if (response.isSuccessful()) {
                    LoginResultPojo loginResultPojo = response.body();

                    // Save auth token to local db
                    SharedPreferences sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.driversPrefs), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(context.getResources().getString(R.string.driverAuthToken), loginResultPojo.getAuthToken());
                    editor.apply();

                    loginInterface.loginSuccessful();
                } else {
//                    Toast.makeText(context, "" + response.message(), Toast.LENGTH_SHORT).show();
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
