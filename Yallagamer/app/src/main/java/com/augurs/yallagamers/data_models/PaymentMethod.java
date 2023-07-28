package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentMethod {
    @SerializedName("payment_id")
    @Expose
    private String paymentId;
    @SerializedName("payment")
    @Expose
    private String payment;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("instructions")
    @Expose
    private String instructions;
    @SerializedName("a_surcharge")
    @Expose
    private String aSurcharge;
    @SerializedName ("image")
    @Expose
    private String image;

    public String getPaymentId () {
        return paymentId;
    }

    public String getPayment () {
        return payment;
    }

    public String getDescription () {
        return description;
    }

    public String getInstructions () {
        return instructions;
    }

    public String getaSurcharge () {
        return aSurcharge;
    }

    public String getImage () {
        return image;
    }
}
