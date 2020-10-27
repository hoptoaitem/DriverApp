package in.aitemconnect.driverapp.ui.availableOrders;

import android.content.Context;
import android.content.SharedPreferences;

import in.aitemconnect.driverapp.R;
import in.aitemconnect.driverapp.pojo.UpdateOrderStatusPojo;
import in.aitemconnect.driverapp.utils.ApiClient;
import in.aitemconnect.driverapp.utils.ApiInterface;
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
