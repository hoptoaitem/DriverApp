package in.aitemconnect.driverapp.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.aitemconnect.driverapp.R;
import in.aitemconnect.driverapp.pojo.LogInPojo;
import in.aitemconnect.driverapp.pojo.LoginResultPojo;
import in.aitemconnect.driverapp.ui.availableOrders.AvailableOrderActivity;
import in.aitemconnect.driverapp.utils.ApiClient;
import in.aitemconnect.driverapp.utils.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.etUsername)
    EditText etUsername;

    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.buttonLogin)
    Button buttonLogin;


//    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(LoginActivity.this);


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

            if (userName == "") {
                Toast.makeText(LoginActivity.this, "Please enter username", Toast.LENGTH_SHORT).show();
            } else if (password == "") {
                Toast.makeText(LoginActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
            } else {
                // Login

            }


//            startActivity(new Intent(this, DashActivity.class));
            startActivity(new Intent(this, AvailableOrderActivity.class));
        }
    }
}