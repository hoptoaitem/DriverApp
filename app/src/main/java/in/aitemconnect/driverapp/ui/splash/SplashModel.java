package in.aitemconnect.driverapp.ui.splash;

import java.util.ArrayList;

import in.aitemconnect.driverapp.pojo.order.OrderPojo;

public class SplashModel {

    interface SplashInterface{

        void orderResult(ArrayList<OrderPojo> orderPojo);

        void orderRequestFailed(String message);
    }

}
