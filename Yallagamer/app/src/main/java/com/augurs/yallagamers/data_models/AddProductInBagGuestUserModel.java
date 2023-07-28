package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddProductInBagGuestUserModel {

    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName ("cart_id")
    @Expose
    private String cartId;

    public void setMethod ( String method ) {
        this.method = method;
    }

    public void setProductId ( Integer productId ) {
        this.productId = productId;
    }

    public void setQuantity ( Integer quantity ) {
        this.quantity = quantity;
    }

    public void setLatitude ( Double latitude ) {
        this.latitude = latitude;
    }

    public void setLongitude ( Double longitude ) {
        this.longitude = longitude;
    }

    public void setCartId ( String cartId ) {
        this.cartId = cartId;
    }
}
