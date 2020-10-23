package in.aitemconnect.driverapp.ui.dashboard;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import in.aitemconnect.driverapp.pojo.order.OrderPojo;

public class DashboardViewModel extends ViewModel implements DashboardModel.DashboardInterface {

    DashboardModel dashboardModel = new DashboardModel(DashboardViewModel.this);

    MutableLiveData<ArrayList<OrderPojo>> orderResult = new MutableLiveData<>();
    MutableLiveData<String> requestFailed = new MutableLiveData<>();

    public void getOrders(Context context) {
        GetOrders getOrders = new GetOrders(context);
        getOrders.execute();
    }

    class GetOrders extends AsyncTask<Void, Void, Void> {
        Context context;

        public GetOrders(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dashboardModel.checkOrder(context);
            return null;
        }
    }

    @Override
    public void orderResult(ArrayList<OrderPojo> orderPojo) {
        orderResult.postValue(orderPojo);
    }

    @Override
    public void orderRequestFailed(String message) {
        requestFailed.postValue(message);
    }
}
