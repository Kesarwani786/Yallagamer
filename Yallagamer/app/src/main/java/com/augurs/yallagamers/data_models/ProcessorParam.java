package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProcessorParam {

    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("order_prefix")
    @Expose
    private String orderPrefix;
    @SerializedName ("test")
    @Expose
    private String test;

    public String getClientId () {
        return clientId;
    }

    public String getOrderPrefix () {
        return orderPrefix;
    }

    public String getTest () {
        return test;
    }
}
