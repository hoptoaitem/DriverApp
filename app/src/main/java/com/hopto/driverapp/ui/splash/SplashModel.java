package com.hopto.driverapp.ui.splash;

import java.util.ArrayList;

import com.hopto.driverapp.pojo.order.OrderPojo;

public class SplashModel {

    interface SplashInterface{

        void orderResult(ArrayList<OrderPojo> orderPojo);

        void orderRequestFailed(String message);
    }

}
