
package com.augurs.yallagamers.data_models;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BagItemBaseModel implements Serializable {
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
        @SerializedName ( "message" )
        @Expose
        private  String message;
        @SerializedName ("notice")
        @Expose
        private  String notice="";
        @SerializedName("cart_id")
        @Expose
        private String cartId;
        @SerializedName("tax_subtotal")
        @Expose
        private Integer taxSubtotal;
        @SerializedName("shipping_cost")
        @Expose
        private Double shippingCost;
        @SerializedName("discount")
        @Expose
        private Integer discount;
        @SerializedName("total")
        @Expose
        private Double total;
        @SerializedName("amount")
        @Expose
        private Integer amount;
        @SerializedName("subtotal")
        @Expose
        private Integer subtotal;
        @SerializedName("subtotal_discount")
        @Expose
        private Integer subtotalDiscount;
        @SerializedName("use_discount")
        @Expose
        private Boolean useDiscount;
        @SerializedName("discounted_subtotal")
        @Expose
        private Integer discountedSubtotal;
        @SerializedName("coupons")
        @Expose
        private List<Object> coupons = null;
        @SerializedName("free_shipping")
        @Expose
        private List<Object> freeShipping = null;
        @SerializedName("shipping_required")
        @Expose
        private Boolean shippingRequired;
        @SerializedName("promotions")
        @Expose
        private List<Object> promotions = null;
        @SerializedName("points_info")
        @Expose
        private PointsInfo pointsInfo;
        @SerializedName("taxes")
        @Expose
        private List<Tax> taxes = null;

        @SerializedName("chosen_shippings")
        @Expose
        private List<Object> chosenShippings = null;
        @SerializedName("shipping")
        @Expose
        private List<Shippings> shipping = null;
        @SerializedName("user_data")
        @Expose
        private User userData;
        @SerializedName("cart_products")
        @Expose
        private ArrayList < Product > cartProducts = null;
        @SerializedName("similarProducts")
        @Expose
        private ArrayList<SimilarProducts> similarProducts = null;
        @SerializedName ("cartCounts")
        @Expose
        private Integer cartCounts;

        @SerializedName ("wishlistCounts")
        @Expose
        private Integer wishlistCounts;

        public String getNotice ( ) {
            return notice;
        }

        public Integer getCartCounts ( ) {
            return cartCounts;
        }

        public Integer getWishlistCounts ( ) {
            return wishlistCounts;
        }

        public String getCartId ( ) {
            return cartId;
        }

        public Integer getTaxSubtotal ( ) {
            return taxSubtotal;
        }

        public Double getShippingCost ( ) {
            return shippingCost;
        }
        public Integer getDiscount ( ) {
            return discount;
        }

        public Double getTotal ( ) {
            return total;
        }

        public Integer getAmount ( ) {
            return amount;
        }

        public Integer getSubtotal ( ) {
            return subtotal;
        }

        public Integer getSubtotalDiscount ( ) {
            return subtotalDiscount;
        }

        public Boolean getUseDiscount ( ) {
            return useDiscount;
        }

        public Integer getDiscountedSubtotal ( ) {
            return discountedSubtotal;
        }

        public List < Object > getCoupons ( ) {
            return coupons;
        }

        public List < Object > getFreeShipping ( ) {
            return freeShipping;
        }

        public Boolean getShippingRequired ( ) {
            return shippingRequired;
        }

        public List < Object > getPromotions ( ) {
            return promotions;
        }

        public PointsInfo getPointsInfo ( ) {
            return pointsInfo;
        }

        public List < Tax > getTaxes ( ) {
            return taxes;
        }
        public List < Object > getChosenShippings ( ) {
            return chosenShippings;
        }

        public List<Shippings>  getShipping ( ) {
            return shipping;
        }

        public User getUserData ( ) {
            return userData;
        }

        public ArrayList < Product > getCartProducts ( ) {
            return cartProducts;
        }

        public ArrayList < SimilarProducts > getSimilarProducts ( ) {
            return similarProducts;
        }

        public String getMessage ( ) {
            return message;
        }
    }
}

