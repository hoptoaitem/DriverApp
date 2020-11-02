package com.aitemconnect.driverapp.pojo.order;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Retailor {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}