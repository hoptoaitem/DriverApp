package com.hopto.driverapp.ui.availableOrders;

import android.content.Context;
import android.content.SharedPreferences;

import com.hopto.driverapp.R;
import com.hopto.driverapp.pojo.UpdateOrderStatusPojo;
import com.hopto.driverapp.utils.ApiClient;
import com.hopto.driverapp.utils.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AvailableOrdersModel {

    ApiInterface apiInterface;
    AvailableOrdersInterface availableOrdersInterface;

    public AvailableOrdersModel(AvailableOrdersInterface availableOrdersInterface) {
        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        this.availableOrdersInterface = availableOrdersInterface;
    }

    interface AvailableOrdersInterface {
        void orderAcceptedSuccessfully();

        void orderDeliveredSuccessfully();

        void orderUpdatedFailed();

        void orderDeclinedSuccess();

    }

    public void finishOrder(Context context) {

    }


    // Update the order status to DRIVER_ACCEPTED
    public void updateOrderStatus(Context context,
                                  UpdateOrderStatusPojo updateOrderStatusPojo,
                                  String acceptOrDelivered) {

        String driversSharedPrefs = context.getString(R.string.driversSharedPrefs);
        String api_key_token = context.getString(R.string.api_key_token);

        SharedPreferences sharedPreferences = context.getSharedPreferences(
                driversSharedPrefs, Context.MODE_PRIVATE);
        String keyToken = sharedPreferences.getString(api_key_token, "null");

        String orderId = updateOrderStatusPojo.getOrderId();

        apiInterface.updateOrderStatus(keyToken, orderId, updateOrderStatusPojo)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful() && response != null) {
                            String result = response.body();
                            // TODO: 27-10-2020 check what is in the string

                            if (acceptOrDelivered.equalsIgnoreCase(
                                    context.getString(R.string.orderAcceptedByDriver))) {
                                // Accepted
                                availableOrdersInterface.orderAcceptedSuccessfully();

                            } else if (acceptOrDelivered.equalsIgnoreCase(
                                    context.getString(R.string.orderDeclinedByDriver))) {
                                // Declined
                                availableOrdersInterface.orderDeclinedSuccess();

                            } else {
                                // Delivered
                                availableOrdersInterface.orderDeliveredSuccessfully();
                            }
                        } else {
                            // Failed
                            availableOrdersInterface.orderUpdatedFailed();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        availableOrdersInterface.orderUpdatedFailed();
                    }
                });

    }

}
