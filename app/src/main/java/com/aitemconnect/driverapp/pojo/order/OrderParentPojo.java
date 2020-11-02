package com.aitemconnect.driverapp.pojo.order;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class OrderParentPojo {

    @Expose
    ArrayList<OrderPojo> order;

    public ArrayList<OrderPojo> getOrder() {
        return order;
    }

    public void setOrder(ArrayList<OrderPojo> order) {
        this.order = order;
    }
}
