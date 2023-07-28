package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TaxSummary {
    @SerializedName("included")
    @Expose
    private Double included;
    @SerializedName("added")
    @Expose
    private Integer added;
    @SerializedName ("total")
    @Expose
    private Double total;

    public Double getIncluded ( ) {
        return included;
    }

    public Integer getAdded ( ) {
        return added;
    }

    public Double getTotal ( ) {
        return total;
    }
}
