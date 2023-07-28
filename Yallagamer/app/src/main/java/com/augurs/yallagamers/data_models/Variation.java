package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Variation {
    @SerializedName ("variant")
    @Expose
    private String variant;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("product_id")
    @Expose
    private Integer productId;

    public String getVariant ( ) {
        return variant;
    }

    public void setVariant ( String variant ) {
        this.variant = variant;
    }

    public String getDescription ( ) {
        return description;
    }

    public void setDescription ( String description ) {
        this.description = description;
    }

    public Integer getProductId ( ) {
        return productId;
    }

    public void setProductId ( Integer productId ) {
        this.productId = productId;
    }
}
