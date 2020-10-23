package in.aitemconnect.driverapp.utils;

import java.util.ArrayList;
import java.util.List;

import in.aitemconnect.driverapp.pojo.LogInPojo;
import in.aitemconnect.driverapp.pojo.LoginResultPojo;
import in.aitemconnect.driverapp.pojo.TestPojo;
import in.aitemconnect.driverapp.pojo.order.OrderParentPojo;
import in.aitemconnect.driverapp.pojo.order.OrderPojo;
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

    // It's a GET request
    @Headers({"Content-Type: application/json"})
    @GET("/orders")
    Call<ArrayList<OrderPojo>> getOrders(@Header("api-key-token") String apiKeyToken);
}
