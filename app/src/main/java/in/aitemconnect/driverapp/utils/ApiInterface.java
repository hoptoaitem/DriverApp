package in.aitemconnect.driverapp.utils;

import java.util.ArrayList;
import java.util.List;

import in.aitemconnect.driverapp.pojo.LogInPojo;
import in.aitemconnect.driverapp.pojo.LoginResultPojo;
import in.aitemconnect.driverapp.pojo.TestPojo;
import in.aitemconnect.driverapp.pojo.UpdateOrderStatusPojo;
import in.aitemconnect.driverapp.pojo.order.OrderParentPojo;
import in.aitemconnect.driverapp.pojo.order.OrderPojo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {

    //    @Headers({"Content-Type: application/json"})
    @POST("/login")
    Call<LoginResultPojo> loginRequest(@Body LogInPojo logInPojo);

    // It's a GET request
    @Headers({"Content-Type: application/json"})
    @GET("/orders")
    Call<ArrayList<OrderPojo>> getOrders(@Header("api-key-token") String apiKeyToken);

    // GET oder history
    @Headers({"Content-Type: application/json"})
    @GET("/orders/history")
    Call<ArrayList<OrderPojo>> getOrderHistory(@Header("api-key-token") String apiKeyToken);


    // PUT request for updating the status of order
    @Headers({"Content-Type: application/json"})
    @PUT("/orders/{order_id}")
    Call<String> updateOrderStatus(@Header("api-key-token") String apiKeyToken,
                                   @Path("order_id") String orderId,
                                   @Body UpdateOrderStatusPojo updateOrderStat);


}
