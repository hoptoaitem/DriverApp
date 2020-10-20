package in.aitemconnect.driverapp.utils;

import java.util.List;

import in.aitemconnect.driverapp.pojo.LogInPojo;
import in.aitemconnect.driverapp.pojo.LoginResultPojo;
import in.aitemconnect.driverapp.pojo.TestPojo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    //    @Headers({"Content-Type: application/json"})
    @POST("/login")
    Call<LoginResultPojo> loginRequest(@Body LogInPojo logInPojo);

    @Headers({"Content-Type: application/json"})
    @POST("/orders")
    Call<LoginResultPojo> getOrders(@Header("api-key-token") String apiKeyToken);
}
