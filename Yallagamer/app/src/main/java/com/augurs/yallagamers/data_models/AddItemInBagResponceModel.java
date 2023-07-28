package com.augurs.yallagamers.data_models;

import com.augurs.yallagamers.api_module.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddItemInBagResponceModel extends BaseResponse {


    @SerializedName ("data")
    @Expose
    private Data data;
    public Data getData ( ) {
        return data;
    }
    public static class Data {
        @SerializedName ("notice")
        @Expose
        private  String notice="";
        @SerializedName ("details")
        @Expose
        private  Detail details;

        public Detail getDetails ( ) {
            return details;
        }

        public String getNotice ( ) {
            return notice;
        }

        @SerializedName ( "message" )
        @Expose
        private  String message;

        @SerializedName ("cart_id")
        @Expose
        private String cartId;
        //************************************************
        @SerializedName ("cartCounts")
        @Expose
        private String cartCounts;

        @SerializedName ("wishlistCounts")
        @Expose
        private String wishlistCounts;
        //************************************************

        public String getMessage ( ) {
            return message;
        }

        public String getCartId ( ) {
            return cartId;
        }

        //***********************************************************************
        public String getWishlistCounts ( ) {
            return wishlistCounts;
        }

        public String getCartCounts ( ) {
            return cartCounts;
        }
        //***********************************************************************
    }

    public static class Detail {
        @SerializedName ("total")
        @Expose
        private  String  total;

        public String getTotal ( ) {
            return total;
        }
    }
}



