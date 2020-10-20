package in.aitemconnect.driverapp.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.aitemconnect.driverapp.R;
import in.aitemconnect.driverapp.ui.availableOrders.AvailableOrderActivity;

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

//    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(LoginActivity.this);

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        loginViewModel.loginSuccess.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                String drivPrefs = getResources().getString(R.string.driversPrefs);
//                String authToken = getResources().getString(R.string.driverAuthToken);
//                String authtoken = sharedPreferences.getString(authToken, "null");

                SharedPreferences sharedPreferences = getSharedPreferences(drivPrefs, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("USER_LOGGED_IN", true);
                editor.apply();


                Intent intent = new Intent(LoginActivity.this, AvailableOrderActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

//                startActivity(new Intent(LoginActivity.this, AvailableOrderActivity.class));
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
//        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);

    }

    /*
        void logIn(String username, String password) {
            LogInPojo logInPojo = new LogInPojo();
            logInPojo.setUsername(username);
            logInPojo.setPassword(password);

            apiInterface.login(logInPojo).enqueue(new Callback<LoginResultPojo>() {
                @Override
                public void onResponse(Call<LoginResultPojo> call, Response<LoginResultPojo> response) {

                    if (response.isSuccessful()) {
                        if (response != null) {
                            LoginResultPojo loginResultPojo = response.body();
                            String profileType = loginResultPojo.getProfileType();
                            String username1 = loginResultPojo.getUsername();
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResultPojo> call, Throwable t) {

                }
            });
        }
    */
    @OnClick(R.id.buttonLogin)
    public void Onclick(View view) {
        int viewId = view.getId();

        if (viewId == R.id.buttonLogin) {

            String userName = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (userName == "" || userName.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter username", Toast.LENGTH_SHORT).show();
            } else if (password == "" || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
            } else {
                // Login
                loginViewModel.loginVM(userName, password, LoginActivity.this);
                buttonLogin.setVisibility(View.GONE);
                progressBarLogin.setVisibility(View.VISIBLE);
            }

//            startActivity(new Intent(this, DashActivity.class));
//
        }
    }
}
