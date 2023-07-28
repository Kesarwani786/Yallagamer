package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShippingScreenModel {

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
    private Data1 data;

    public Integer getStatus ( ) {
        return status;
    }

    public String getMessage ( ) {
        return message;
    }

    public String getToken ( ) {
        return token;
    }

    public Data1 getData ( ) {
        return data;
    }

    public class Data1 {
        @SerializedName ("notice")
        @Expose
        private String notice="";
        @SerializedName ("shipping_required")
        @Expose
        private  String shipping_required="";
        @SerializedName ("message")
        @Expose
        private String message;
        @SerializedName ("shipping")
        @Expose
        private List < Shippings > shipping = null;
        @SerializedName ("user_data")
        @Expose
        private UserData1 userData;
        @SerializedName ("cart_products")
        @Expose
        private List < CartProduct > cartProducts = null;
        @SerializedName ("earn_reward_points")
        @Expose
        private Integer earnRewardPoints;

        public String getShipping_required ( ) {
            return shipping_required;
        }

        public String getNotice ( ) {
            return notice;
        }

        public String getMessage ( ) {
            return message;
        }

        public List < Shippings > getShipping ( ) {
            return shipping;
        }

        public UserData1 getUserData ( ) {
            return userData;
        }

        public List < CartProduct > getCartProducts ( ) {
            return cartProducts;
        }

        public Integer getEarnRewardPoints ( ) {
            return earnRewardPoints;
        }

        public class CartProduct {
            @SerializedName ("product_id")
            @Expose
            private Integer productId;
            @SerializedName ("product")
            @Expose
            private String product;
            @SerializedName ("delieverydate")
            @Expose
            private String delieverydate;
            @SerializedName ("main_category")
            @Expose
            private String mainCategory;
            @SerializedName ("main_image")
            @Expose
            private String mainImage;
            @SerializedName ("reward_points")
            @Expose
            private Integer rewardPoints;

            public Integer getProductId ( ) {
                return productId;
            }

            public String getProduct ( ) {
                return product;
            }

            public String  getDelieverydate ( ) {
                return delieverydate;
            }

            public String getMainCategory ( ) {
                return mainCategory;
            }

            public String getMainImage ( ) {
                return mainImage;
            }

            public Integer getRewardPoints ( ) {
                return rewardPoints;
            }
        }
        public class UserData1 {
            @SerializedName ("profile_id")
            @Expose
            private Integer ProfileId;
            @SerializedName ("user_id")
            @Expose
            private Integer UserId;
            @SerializedName ("profile_name")
            @Expose
            private String ProfileName;
            @SerializedName ("phone")
            @Expose
            private String Phone ;
            @SerializedName ("s_country")
            @Expose
            private String Country ;
            @SerializedName ("s_state")
            @Expose
            private String State ;
            @SerializedName ("s_city")
            @Expose
            private String City ;
            @SerializedName ("s_address")
            @Expose
            private String Address;
            @SerializedName ("s_address_2")
            @Expose
            private String Address2 ;
            @SerializedName ("s_zipcode")
            @Expose
            private String Zipcode ;
            @SerializedName ( "firstname" )
            @Expose
            private  String firstname;
            @SerializedName ( "lastname" )
            @Expose
            private  String lastname;

            public String getLastname ( ) {
                return lastname;
            }

            public String getFirstname ( ) {
                return firstname;
            }

            public Integer getProfileId ( ) {
                return ProfileId;
            }

            public Integer getUserId ( ) {
                return UserId;
            }

            public String getProfileName ( ) {
                return ProfileName;
            }

            public String getPhone ( ) {
                return Phone;
            }

            public String getCountry ( ) {
                return Country;
            }

            public String getState ( ) {
                return State;
            }

            public String getCity ( ) {
                return City;
            }

            public String getAddress ( ) {
                return Address;
            }

            public String getAddress2 ( ) {
                return Address2;
            }

            public String getZipcode ( ) {
                return Zipcode;
            }
        }
    }
}
