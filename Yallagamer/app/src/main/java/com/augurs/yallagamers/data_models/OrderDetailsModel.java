package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsModel {
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
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName ("order_id")
        @Expose
        private Integer orderId;
        @SerializedName ("total")
        @Expose
        private Double total;
        @SerializedName ("subtotal")
        @Expose
        private Double subtotal;
        @SerializedName ("discount")
        @Expose
        private Double discount;
        @SerializedName ("subtotal_discount")
        @Expose
        private Double subtotalDiscount;
        @SerializedName ("payment_surcharge")
        @Expose
        private Double paymentSurcharge;
        @SerializedName ("shipping_cost")
        @Expose
        private String shippingCost;
        @SerializedName ("timestamp")
        @Expose
        private String timestamp;
        @SerializedName ("orderDate")
        @Expose
        private String orderDate;
        @SerializedName ("status")
        @Expose
        private String status;
        @SerializedName ("notes")
        @Expose
        private Object notes;
        @SerializedName ("details")
        @Expose
        private Object details;
        @SerializedName ("promotions")
        @Expose
        private List<Object> promotions = null;
        @SerializedName ("profile_id")
        @Expose
        private String profileId;
        @SerializedName ("tax_subtotal")
        @Expose
        private Integer taxSubtotal;
        @SerializedName ("customer_notes")
        @Expose
        private String customerNotes;
        @SerializedName ("display_subtotal")
        @Expose
        private Integer displaySubtotal;
        @SerializedName ("need_shipping")
        @Expose
        private Boolean needShipping;
        @SerializedName ("barcodeImg")
        @Expose
        private String barcodeImg;
        @SerializedName ("taxes")
        @Expose
        private ArrayList<Tax> taxesList = null;
        @SerializedName ("payment_method")
        @Expose
        private PaymentMethod paymentMethod;
        @SerializedName ("products")
        @Expose
        private ArrayList<Product> products = null;
        @SerializedName ("user_data")
        @Expose
        private User userData;
        @SerializedName ("chosen_shippings")
        @Expose
        private Shippings chosenShippings;
        @SerializedName ("shipping_carrier_info")
        @Expose
        private Shippings shippingCarrierInfo;

        public String getMessage () {
            return message;
        }

        public Integer getOrderId () {
            return orderId;
        }

        public Double getTotal () {
            return total;
        }

        public Double getSubtotal () {
            return subtotal;
        }

        public Double getDiscount () {
            return discount;
        }

        public Double getSubtotalDiscount () {
            return subtotalDiscount;
        }

        public Double getPaymentSurcharge () {
            return paymentSurcharge;
        }

        public String getShippingCost () {
            return shippingCost;
        }

        public String getTimestamp () {
            return timestamp;
        }

        public String getOrderDate () {
            return orderDate;
        }

        public String getStatus () {
            return status;
        }

        public Object getNotes () {
            return notes;
        }

        public Object getDetails () {
            return details;
        }

        public List<Object> getPromotions () {
            return promotions;
        }

        public String getProfileId () {
            return profileId;
        }

        public Integer getTaxSubtotal () {
            return taxSubtotal;
        }

        public String getCustomerNotes () {
            return customerNotes;
        }

        public Integer getDisplaySubtotal () {
            return displaySubtotal;
        }

        public Boolean getNeedShipping () {
            return needShipping;
        }

        public String getBarcodeImg () {
            return barcodeImg;
        }

        public ArrayList<Tax> getTaxesList ( ) {
            return taxesList;
        }

        public PaymentMethod getPaymentMethod () {
            return paymentMethod;
        }

        public ArrayList<Product> getProducts () {
            return products;
        }

        public User getUserData () {
            return userData;
        }

        public Shippings getChosenShippings () {
            return chosenShippings;
        }

        public Shippings getShippingCarrierInfo () {
            return shippingCarrierInfo;
        }
    }
}
