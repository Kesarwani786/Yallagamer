package com.augurs.yallagamers.data_models;

import com.augurs.yallagamers.api_module.BaseResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WishList {


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
    private ArrayList < Datum > data = null;

    public Integer getStatus ( ) {
        return status;
    }

    public void setStatus ( Integer status ) {
        this.status = status;
    }

    public String getMessage ( ) {
        return message;
    }

    public String getToken ( ) {
        return token;
    }

    public ArrayList < Datum > getData ( ) {
        return data;
    }

    public class Datum {
        @SerializedName ( "main_category" )
        @Expose
        private  String main_category;
        @SerializedName ( "discounts" )
        @Expose
        private  String discounts;
        @SerializedName ( "main_image" )
        @Expose
        private  String main_image;
        @SerializedName ( "stock" )
        @Expose
        private  String stock;
        @SerializedName ( "product_id" )
        @Expose
        private Integer ProductId;
        @SerializedName ("list_price")
        @Expose
        private double  list_price;
        @SerializedName ("cartCounts")
        @Expose
        private  Integer cartCounts;
        @SerializedName ("wishlistCounts")
        @Expose
        private  Integer wishlistCounts;
        @SerializedName ( "base_price" )
        @Expose
        private  double base_price;

        @SerializedName ("product")
        @Expose
        private String product;

        public String getMain_category ( ) {
            return main_category;
        }

        public String getDiscounts ( ) {
            return discounts;
        }

        public String getMain_image ( ) {
            return main_image;
        }

        public String getStock ( ) {
            return stock;
        }

        public Integer getProductId ( ) {
            return ProductId;
        }

        public double getBase_price ( ) {
            return base_price;
        }

        public double getList_price ( ) {
            return list_price;
        }

        public String getProduct ( ) {
            return product;
        }

        public Integer getCartCounts ( ) {
            return cartCounts;
        }

        public Integer getWishlistCounts ( ) {
            return wishlistCounts;
        }
    }
}