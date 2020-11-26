package com.hopto.driverapp.pojo.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Contact implements Serializable {

    @SerializedName("retailor")
    @Expose
    private Retailor retailor;

    @SerializedName("shopper")
    @Expose
    private Shopper shopper;

    public Retailor getRetailor() {
        return retailor;
    }

    public void setRetailor(Retailor retailor) {
        this.retailor = retailor;
    }

    public Shopper getShopper() {
        return shopper;
    }

    public void setShopper(Shopper shopper) {
        this.shopper = shopper;
    }
}
