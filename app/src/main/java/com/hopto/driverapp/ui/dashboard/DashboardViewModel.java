package com.hopto.driverapp.ui.dashboard;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import com.hopto.driverapp.pojo.order.OrderPojo;

public class DashboardViewModel extends ViewModel implements DashboardModel.DashboardInterface {

    DashboardModel dashboardModel = new DashboardModel(DashboardViewModel.this);

    public MutableLiveData<ArrayList<OrderPojo>> orderResult = new MutableLiveData<>();
    public MutableLiveData<ArrayList<OrderPojo>> orderHistoryResult = new MutableLiveData<>();
    public MutableLiveData<String> requestFailed = new MutableLiveData<>();
    public MutableLiveData<String> historyRequestFailed = new MutableLiveData<>();

    public void getOrders(Context context) {
        GetOrders getOrders = new GetOrders(context);
        getOrders.execute();
    }

    public void getOrdersHistory(Context context) {
        GetOrdersHistory getOrders = new GetOrdersHistory(context);
        getOrders.execute();
    }

    class GetOrdersHistory extends AsyncTask<Void, Void, Void> {
        Context context;

        public GetOrdersHistory(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dashboardModel.getOrdersHistory(context);
            return null;
        }
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
    public void orderRequestHistoryResult(ArrayList<OrderPojo> orderPojo) {
        orderHistoryResult.postValue(orderPojo);
    }

    @Override
    public void orderRequestFailed(String message) {
        requestFailed.postValue(message);
    }

    @Override
    public void orderHistoryRequestFailed(String message) {
        historyRequestFailed.postValue(message);
    }
}
