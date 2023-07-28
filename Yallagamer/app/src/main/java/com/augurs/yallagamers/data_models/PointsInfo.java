package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PointsInfo {
    @SerializedName("raw_total_price")
    @Expose
    private Integer rawTotalPrice;
    @SerializedName ("total_price")
    @Expose
    private Integer totalPrice;

    public Integer getRawTotalPrice ( ) {
        return rawTotalPrice;
    }

    public Integer getTotalPrice ( ) {
        return totalPrice;
    }
}
