package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tax {

    @SerializedName ("rate_type")
    @Expose
    private String rateType;
    @SerializedName("rate_value")
    @Expose
    private String rateValue;
    @SerializedName("tax_subtotal")
    @Expose
    private Double taxSubtotal;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("regnumber")
    @Expose
    private String regnumber;

    public String getRegnumber () {
        return regnumber;
    }

    public String getRateType ( ) {
        return rateType;
    }

    public String getRateValue ( ) {
        return rateValue;
    }

    public Double getTaxSubtotal ( ) {
        return taxSubtotal;
    }

    public Object getDescription ( ) {
        return description;
    }
}
