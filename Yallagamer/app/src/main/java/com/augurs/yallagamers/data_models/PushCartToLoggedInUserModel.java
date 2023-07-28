package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PushCartToLoggedInUserModel {

    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("transaction_id")
    @Expose
    private String transaction_id;
    @SerializedName ("cart_id")
    @Expose
    private String cartId;
    @SerializedName ("product_cart_id")
    @Expose
    private String product_cart_id;
    @SerializedName ("shipping_id")
    @Expose
    private Integer shipping_id;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("action")
    @Expose
    private String action;
    @SerializedName("reason_text")
    @Expose
    private String reason_text;
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("order_id")
    @Expose
    private String order_id;



    public void setTransaction_id ( String transaction_id ) {
        this.transaction_id = transaction_id;
    }

    public String getOrder_id () {
        return order_id;
    }

    public void setOrder_id ( String order_id ) {
        this.order_id = order_id;
    }

    public String getStatus () {
        return status;
    }

    public void setStatus ( String status ) {
        this.status = status;
    }

    public void setReason_text ( String reason_text ) {
        this.reason_text = reason_text;
    }

    public void setQuantity ( String quantity ) {
        this.quantity = quantity;
    }

    public void setProductId( String productId) {
        this.productId = productId;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setShipping_id (Integer shipping_id ) {
        this.shipping_id = shipping_id;
    }

    public void setProduct_cart_id ( String product_cart_id ) {
        this.product_cart_id = product_cart_id;
    }

    public void setMethod ( String method ) {
        this.method = method;
    }
    public void setCartId ( String cartId ) {
        this.cartId = cartId;
    }

}
