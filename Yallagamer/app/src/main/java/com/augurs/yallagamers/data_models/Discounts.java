package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Discounts {
    @SerializedName ("A")
    @Expose
    private Integer a;
    @SerializedName("P")
    @Expose
    private Integer p;

    public Integer getA() {
        return a;
    }

    public void setA(Integer a) {
        this.a = a;
    }

    public Integer getP() {
        return p;
    }

    public void setP(Integer p) {
        this.p = p;
    }
}
