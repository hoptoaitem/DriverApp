package in.aitemconnect.driverapp.utils;

import java.util.List;

import in.aitemconnect.driverapp.pojo.LogInPojo;
import in.aitemconnect.driverapp.pojo.LoginResultPojo;
import in.aitemconnect.driverapp.pojo.TestPojo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("/login")
    Call<LoginResultPojo> login(@Body LogInPojo logInPojo);


}
