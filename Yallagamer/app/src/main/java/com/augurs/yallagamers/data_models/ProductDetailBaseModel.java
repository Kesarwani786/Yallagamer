package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailBaseModel {
    @SerializedName ("status")
    @Expose
    private Integer status;
    @SerializedName ("message")
    @Expose
    private String message;
    @SerializedName ("token")
    @Expose
    private String token;
    @SerializedName ("data")
    @Expose
    private Data data;

    public Integer getStatus ( ) {
        return status;
    }

    public void setStatus ( Integer status ) {
        this.status = status;
    }

    public String getMessage ( ) {
        return message;
    }

    public void setMessage ( String message ) {
        this.message = message;
    }

    public String getToken ( ) {
        return token;
    }

    public void setToken ( String token ) {
        this.token = token;
    }

    public Data getData ( ) {
        return data;
    }

    public class Data {
        @SerializedName ("message")
        @Expose
        private String Message;
        @SerializedName ("productsDetails")
        @Expose
        private ProductDetail productsDetails;
        @SerializedName ("similarProducts")
        @Expose
        private ArrayList < SimilarProducts > similarProducts = null;
        @SerializedName ("cartCounts")
        @Expose
        private Integer cartCounts=0;
        @SerializedName ("wishlistCounts")
        @Expose
        private Integer wishlistCounts=0;

        public Integer getCartCounts ( ) {
            return cartCounts;
        }

        public Integer getWishlistCounts ( ) {
            return wishlistCounts;
        }

        public String getMessage ( ) {
            return Message;
        }

        public ProductDetail getProductsDetails ( ) {
            return productsDetails;
        }

        public void setProductsDetails ( ProductDetail productsDetails ) {
            this.productsDetails = productsDetails;
        }

        public ArrayList < SimilarProducts > getSimilarProducts ( ) {
            return similarProducts;
        }

        public void setSimilarProducts ( ArrayList < SimilarProducts > similarProducts ) {
            this.similarProducts = similarProducts;
        }

    }
}
