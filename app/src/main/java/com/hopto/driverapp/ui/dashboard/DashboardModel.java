package com.hopto.driverapp.ui.dashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;

import com.hopto.driverapp.R;
import com.hopto.driverapp.pojo.order.OrderPojo;
import com.hopto.driverapp.utils.ApiClient;
import com.hopto.driverapp.utils.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardModel {

    private static final String TAG = "DashboardModel";
    DashboardInterface dashboardInterface;
    ApiInterface apiInterface;


    public DashboardModel(DashboardInterface dashboardInterface) {
        this.dashboardInterface = dashboardInterface;

        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
    }

    public interface DashboardInterface {
        void orderResult(ArrayList<OrderPojo> orderPojo);

        void orderRequestHistoryResult(ArrayList<OrderPojo> orderPojo);

        void orderRequestFailed(String message);

        void orderHistoryRequestFailed(String message);


    }

    void checkOrder(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.driversSharedPrefs), Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString(context.getString(R.string.api_key_token), "null_token");

        apiInterface.getOrders(authToken).enqueue(new Callback<ArrayList<OrderPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<OrderPojo>> call, Response<ArrayList<OrderPojo>> response) {

                if (response != null && response.isSuccessful()) {
                    ArrayList<OrderPojo> responseArray = response.body();
                    dashboardInterface.orderResult(responseArray);

                } else {
                    dashboardInterface.orderRequestFailed("Request failed!");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<OrderPojo>> call, Throwable t) {
                dashboardInterface.orderRequestFailed("Request failed!");
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });


    }

    void getOrdersHistory(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.driversSharedPrefs), Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString(context.getString(R.string.api_key_token), "null_token");

        apiInterface.getOrderHistory(authToken).enqueue(new Callback<ArrayList<OrderPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<OrderPojo>> call, Response<ArrayList<OrderPojo>> response) {

                if (response != null && response.isSuccessful()) {
                    ArrayList<OrderPojo> responseArray = response.body();
                    dashboardInterface.orderRequestHistoryResult(responseArray);

                } else {
                    dashboardInterface.orderHistoryRequestFailed("Request failed!");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<OrderPojo>> call, Throwable t) {
                dashboardInterface.orderHistoryRequestFailed("Request failed!");
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });


    }

}
