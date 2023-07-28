package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CategoryProductBaseModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("data")
    @Expose
    private Data data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public class Data {
        @SerializedName ("products")
        @Expose
        private ArrayList < NewProductItem > products = null;
        @SerializedName ("params")
        @Expose
        private CategoryBaseParameter params;
        @SerializedName ("cartCounts")
        @Expose
        private String cartCounts;

        @SerializedName ("wishlistCounts")
        @Expose
        private String wishlistCounts;

        public String getCartCounts ( ) {
            return cartCounts;
        }

        public String getWishlistCounts ( ) {
            return wishlistCounts;
        }

        public ArrayList < NewProductItem > getProducts ( ) {
            return products;
        }

        public void setProducts ( ArrayList < NewProductItem > products ) {
            this.products = products;
        }

        public CategoryBaseParameter getParams ( ) {
            return params;
        }

        public void setParams ( CategoryBaseParameter params ) {
            this.params = params;
        }
    }
}
