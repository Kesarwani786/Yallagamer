package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddRemoveWishlistModel {
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

        public String getMessage() {
            return message;
        }

        public String getToken() {
            return token;
        }

        public Data getData() {
            return data;
        }

    public class Data {

        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("wishlist_id")
        @Expose
        private Integer wishlistId;

        //************************************************
        @SerializedName ("cartCounts")
        @Expose
        private Integer cartCounts;

        @SerializedName ("wishlistCounts")
        @Expose
        private Integer wishlistCounts;
        //************************************************

        public Data() {
        }

        public String getMessage() {
            return message;
        }

        public Integer getWishlistId() {
            return wishlistId;
        }

        //***********************************************************************
        public Integer getWishlistCounts ( ) {
            return wishlistCounts;
        }

        public Integer getCartCounts ( ) {
            return cartCounts;
        }
        //***********************************************************************
    }
}
