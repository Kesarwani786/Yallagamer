package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewShareModel {
    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName ("name")
    @Expose
    private String name;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("rating_value")
    @Expose
    private String ratingValue;
    @SerializedName("_d")
    @Expose
    private String d;

    public void setMethod ( String method ) {
        this.method = method;
    }

    public void setProductId ( String productId ) {
        this.productId = productId;
    }

    public void setEmail ( String email ) {
        this.email = email;
    }

    public void setName ( String name ) {
        this.name = name;
    }

    public void setTitle ( String title ) {
        this.title = title;
    }

    public void setMessage ( String message ) {
        this.message = message;
    }

    public void setRatingValue ( String ratingValue ) {
        this.ratingValue = ratingValue;
    }

    public void setD ( String d ) {
        this.d = d;
    }
}
