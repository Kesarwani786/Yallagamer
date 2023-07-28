
package com.augurs.yallagamers.data_models;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class HomePageData implements Serializable {

    @SerializedName("status")
    Integer status;
    @SerializedName("message")
    String message;
    @SerializedName("token")
    String token;
    @SerializedName("data")
    HomePage homePages;
    //JsonObject homePages;
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public HomePage getHomePages() {
        return homePages;
    }

    public void setHomePages(HomePage homePages) {
        this.homePages = homePages;
    }


}

