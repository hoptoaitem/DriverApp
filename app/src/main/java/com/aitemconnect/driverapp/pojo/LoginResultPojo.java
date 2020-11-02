package com.aitemconnect.driverapp.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResultPojo {

    @Expose
    @SerializedName("authToken")
    private String authToken;

    @Expose
    @SerializedName("profileType")
    private String profileType;


    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getProfileType() {
        return profileType;
    }

    public void setProfileType(String profileType) {
        this.profileType = profileType;
    }

}
