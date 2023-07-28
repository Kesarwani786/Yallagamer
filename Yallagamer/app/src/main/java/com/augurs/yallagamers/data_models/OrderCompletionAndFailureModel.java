package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderCompletionAndFailureModel {

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

    public Integer getStatus () {
        return status;
    }

    public String getMessage () {
        return message;
    }

    public String getToken () {
        return token;
    }

    public Data getData () {
        return data;
    }
    public class Data {

        @SerializedName ("message")
        @Expose
        private String message;
        @SerializedName ("order_id")
        @Expose
        private Integer orderId;
        @SerializedName ("tax_subtotal")
        @Expose
        private Integer taxSubtotal;
        @SerializedName ("shipping_cost")
        @Expose
        private String shippingCost;
        @SerializedName ("discount")
        @Expose
        private Integer discount;
        @SerializedName ("total")
        @Expose
        private String total;
        @SerializedName ("tip")
        @Expose
        private String tip;
        @SerializedName ("status")
        @Expose
        private String status;
        @SerializedName ("user_data")
        @Expose
        private User userData;
        @SerializedName ("earn_reward_points")
        @Expose
        private Integer earnRewardPoints;
        @SerializedName ("need_shipping")
        @Expose
        private Boolean needShipping;
        @SerializedName ("chosen_shippings")
        @Expose
        private Shippings chosenShippings;

        public String getMessage () {
            return message;
        }

        public Integer getOrderId () {
            return orderId;
        }

        public Integer getTaxSubtotal () {
            return taxSubtotal;
        }

        public String getShippingCost () {
            return shippingCost;
        }

        public Integer getDiscount () {
            return discount;
        }

        public String getTotal () {
            return total;
        }

        public String getTip () {
            return tip;
        }

        public String getStatus () {
            return status;
        }

        public User getUserData () {
            return userData;
        }

        public Integer getEarnRewardPoints () {
            return earnRewardPoints;
        }

        public Boolean getNeedShipping () {
            return needShipping;
        }

        public Shippings getChosenShippings () {
            return chosenShippings;
        }
    }
}
