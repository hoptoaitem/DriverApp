package com.aitemconnect.driverapp.ui.login;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.aitemconnect.driverapp.pojo.LoginResultPojo;

public class LoginViewModel extends AndroidViewModel implements LoginModel.LoginInterface {

    LoginModel loginModel = new LoginModel(this);

    MutableLiveData<String> failedToLogin = new MutableLiveData<>();
    MutableLiveData<String> loginSuccess = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void loginVM(String userName, String password, Context context) {
        // Create bg thread
        LoginRequestAT loginRequestAT = new LoginRequestAT(userName, password, context);
        loginRequestAT.execute();
    }

    @Override
    public void loginResult(LoginResultPojo loginResultPojo) {

    }

    @Override
    public void failedToLogin() {
        failedToLogin.postValue("Incorrect username or password");
    }

    @Override
    public void loginSuccessful() {
        loginSuccess.postValue("Logged in successfully");
    }

    /****Async Tasks****/
    class LoginRequestAT extends AsyncTask<Void, Void, Void> {
        String usernameaa;
        String password;
        Context context;

        public LoginRequestAT(String username, String password, Context context) {
            this.usernameaa = username;
            this.password = password;
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            loginModel.loginRequest(usernameaa, password, context);
            return null;
        }
    }

}
