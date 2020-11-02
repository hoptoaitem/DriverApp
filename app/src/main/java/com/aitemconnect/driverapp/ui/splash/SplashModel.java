package com.aitemconnect.driverapp.ui.splash;

import java.util.ArrayList;

import com.aitemconnect.driverapp.pojo.order.OrderPojo;

public class SplashModel {

    interface SplashInterface{

        void orderResult(ArrayList<OrderPojo> orderPojo);

        void orderRequestFailed(String message);
    }

}
