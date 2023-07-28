package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PeriodModel {
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName ("Value")
    @Expose
    private String value;

    public PeriodModel ( String name, String value ) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
