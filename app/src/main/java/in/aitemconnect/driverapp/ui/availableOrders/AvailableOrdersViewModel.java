package in.aitemconnect.driverapp.ui.availableOrders;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import in.aitemconnect.driverapp.pojo.UpdateOrderStatusPojo;

public class AvailableOrdersViewModel extends ViewModel implements
        AvailableOrdersModel.AvailableOrdersInterface {

    AvailableOrdersModel availableOrdersModel = new AvailableOrdersModel(this);

    MutableLiveData<String> orderAccepted = new MutableLiveData<>();
    MutableLiveData<String> orderDelivered = new MutableLiveData<>();


    void updateOrder(Context context, UpdateOrderStatusPojo updateOrderStatusPojo, String acceptOrDelivered) {
        AcceptOrders acceptOrders = new AcceptOrders(context, updateOrderStatusPojo, acceptOrDelivered);
        acceptOrders.execute();
    }


    class AcceptOrders extends AsyncTask<Void, Void, Void> {
        Context context;
        UpdateOrderStatusPojo updateOrderStatusPojo;
        String acceptOrDelivered;

        public AcceptOrders(Context context, UpdateOrderStatusPojo updateOrderStatusPojo,
                            String acceptOrDelivered) {
            this.context = context;
            this.updateOrderStatusPojo = updateOrderStatusPojo;
            this.acceptOrDelivered = acceptOrDelivered;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            availableOrdersModel.updateOrderStatus(context, updateOrderStatusPojo, acceptOrDelivered);
            return null;
        }
    }


    @Override
    public void orderAcceptedSuccessfully() {
        orderAccepted.postValue("success");
    }

    @Override
    public void orderDeliveredSuccessfully() {
        orderDelivered.postValue("success");
    }

    @Override
    public void orderUpdatedFailed() {
        orderAccepted.postValue("failed");
    }

}
