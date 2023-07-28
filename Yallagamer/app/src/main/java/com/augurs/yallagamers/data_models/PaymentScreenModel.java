package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaymentScreenModel {
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

    public void setData ( Data data ) {
        this.data = data;
    }


    public class Data {
        @SerializedName ("notice")
        @Expose
        private  String notice="";

        public String getNotice ( ) {
            return notice;
        }

        @SerializedName ("message")
        @Expose
        private String message;
        @SerializedName ("order_id")
        @Expose
        private String order_id;
        @SerializedName ("tax_subtotal")
        @Expose
        private Integer taxSubtotal;
        @SerializedName ("shipping_cost")
        @Expose
        private Double shippingCost;
        @SerializedName ("discount")
        @Expose
        private Integer discount;
        @SerializedName ("total")
        @Expose
        private Double total;
        @SerializedName ("amount")
        @Expose
        private Integer amount;
        @SerializedName ("subtotal")
        @Expose
        private Integer subtotal;
        @SerializedName ("subtotal_discount")
        @Expose
        private Integer subtotalDiscount;
        @SerializedName ("use_discount")
        @Expose
        private Boolean useDiscount;
        @SerializedName ("discounted_subtotal")
        @Expose
        private Integer discountedSubtotal;
        @SerializedName ("coupons")
        @Expose
        private List < Object > coupons = null;
        @SerializedName ("free_shipping")
        @Expose
        private List < Object > freeShipping = null;
        @SerializedName ("shipping_required")
        @Expose
        private Boolean shippingRequired;
        @SerializedName ("promotions")
        @Expose
        private List < Object > promotions = null;
        @SerializedName ("points_info")
        @Expose
        private PointsInfo pointsInfo;
        @SerializedName ("taxes")
        @Expose
        private List < Tax > taxes = null;
        @SerializedName ("chosen_shippings")
        @Expose
        private List < ChosenShipping > chosenShippings = null;
        @SerializedName ("shipping")
        @Expose
        private List < Object > shipping = null;
        @SerializedName ("user_data")
        @Expose
        private UserData userData;
        @SerializedName ("cart_products")
        @Expose
        private List < CartProduct > cartProducts = null;
        @SerializedName ("earn_reward_points")
        @Expose
        private Integer earnRewardPoints;
        @SerializedName ("payment_methods")
        @Expose
        private List < PaymentMethod > paymentMethods = null;

        public String getOrder_id () {
            return order_id;
        }


        public String getMessage ( ) {
            return message;
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

        public List < ChosenShipping > getChosenShippings ( ) {
            return chosenShippings;
        }

        public List < Object > getShipping ( ) {
            return shipping;
        }

        public UserData getUserData ( ) {
            return userData;
        }

        public List < CartProduct > getCartProducts ( ) {
            return cartProducts;
        }

        public Integer getEarnRewardPoints ( ) {
            return earnRewardPoints;
        }



        public List < PaymentMethod > getPaymentMethods ( ) {
            return paymentMethods;
        }
    }

    public class ChosenShipping {

        @SerializedName ("shipping_id")
        @Expose
        private String shippingId;
        @SerializedName ("shipping")
        @Expose
        private String shipping;
        @SerializedName ("service_delivery_time")
        @Expose
        private String serviceDeliveryTime;
        @SerializedName ("description")
        @Expose
        private String description;
        @SerializedName ("image")
        @Expose
        private String image;
        @SerializedName ("rate")
        @Expose
        private Double rate;

        public String getShippingId ( ) {
            return shippingId;
        }

        public String getShipping ( ) {
            return shipping;
        }

        public String getServiceDeliveryTime ( ) {
            return serviceDeliveryTime;
        }

        public String getDescription ( ) {
            return description;
        }

        public String getImage ( ) {
            return image;
        }

        public Double getRate ( ) {
            return rate;
        }
    }

    public class CartProduct {

        @SerializedName ("product_cart_id")
        @Expose
        private Long productCartId;
        @SerializedName ("product_id")
        @Expose
        private Integer productId;
        @SerializedName ("product")
        @Expose
        private String product;
        @SerializedName ("delieverydate")
        @Expose
        private String delieverydate;
        @SerializedName ("product_code")
        @Expose
        private String productCode;
        @SerializedName ("display_price")
        @Expose
        private Integer displayPrice;
        @SerializedName ("base_price")
        @Expose
        private Integer basePrice;
        @SerializedName ("amount_total")
        @Expose
        private Integer amountTotal;
        @SerializedName ("amount")
        @Expose
        private Integer amount;
        @SerializedName ("price")
        @Expose
        private Integer price;
        @SerializedName ("main_category")
        @Expose
        private String mainCategory;
        @SerializedName ("main_image")
        @Expose
        private String mainImage;
        @SerializedName ("in_wishlist")
        @Expose
        private String inWishlist;
        @SerializedName ("price_in_points")
        @Expose
        private Integer priceInPoints;
        @SerializedName ("reward_points")
        @Expose
        private Integer rewardPoints;
        @SerializedName ("discounts")
        @Expose
        private Object discounts;

        public Long getProductCartId ( ) {
            return productCartId;
        }

        public Integer getProductId ( ) {
            return productId;
        }

        public String getProduct ( ) {
            return product;
        }

        public String getDelieverydate ( ) {
            return delieverydate;
        }

        public String getProductCode ( ) {
            return productCode;
        }

        public Integer getDisplayPrice ( ) {
            return displayPrice;
        }

        public Integer getBasePrice ( ) {
            return basePrice;
        }

        public Integer getAmountTotal ( ) {
            return amountTotal;
        }

        public Integer getAmount ( ) {
            return amount;
        }

        public Integer getPrice ( ) {
            return price;
        }

        public String getMainCategory ( ) {
            return mainCategory;
        }

        public String getMainImage ( ) {
            return mainImage;
        }

        public String getInWishlist ( ) {
            return inWishlist;
        }

        public Integer getPriceInPoints ( ) {
            return priceInPoints;
        }

        public Integer getRewardPoints ( ) {
            return rewardPoints;
        }

        public Object getDiscounts ( ) {
            return discounts;
        }
    }

    public class UserData {

        @SerializedName ("user_id")
        @Expose
        private Integer userId;
        @SerializedName ("email")
        @Expose
        private String email;
        @SerializedName ("firstname")
        @Expose
        private String firstname;
        @SerializedName ("lastname")
        @Expose
        private String lastname;
        @SerializedName ("phone")
        @Expose
        private String phone;
        @SerializedName ("s_country")
        @Expose
        private String sCountry;
        @SerializedName ("s_state")
        @Expose
        private String sState;
        @SerializedName ("s_city")
        @Expose
        private String sCity;
        @SerializedName ("s_address")
        @Expose
        private String sAddress;
        @SerializedName ("s_zipcode")
        @Expose
        private String sZipcode;

        public Integer getUserId ( ) {
            return userId;
        }

        public String getEmail ( ) {
            return email;
        }

        public String getFirstname ( ) {
            return firstname;
        }

        public String getLastname ( ) {
            return lastname;
        }

        public String getPhone ( ) {
            return phone;
        }

        public String getsCountry ( ) {
            return sCountry;
        }

        public String getsState ( ) {
            return sState;
        }

        public String getsCity ( ) {
            return sCity;
        }

        public String getsAddress ( ) {
            return sAddress;
        }

        public String getsZipcode ( ) {
            return sZipcode;
        }
    }

    public class TaxSummary {

        @SerializedName ("included")
        @Expose
        private Double included;
        @SerializedName ("added")
        @Expose
        private Integer added;
        @SerializedName ("total")
        @Expose
        private Double total;

        public Double getIncluded ( ) {
            return included;
        }

        public Integer getAdded ( ) {
            return added;
        }

        public Double getTotal ( ) {
            return total;
        }
    }

        public class SaveCard {

        @SerializedName ("id")
        @Expose
        private String id;
        @SerializedName ("user_id")
        @Expose
        private String userId;
        @SerializedName ("card_number")
        @Expose
        private String cardNumber;
        @SerializedName ("exp_mm")
        @Expose
        private String expMm;
        @SerializedName ("exp_yy")
        @Expose
        private String expYy;
        @SerializedName ("card_name")
        @Expose
        private String cardName;
        @SerializedName ("created_at")
        @Expose
        private String createdAt;

        public String getId ( ) {
            return id;
        }

        public String getUserId ( ) {
            return userId;
        }

        public String getCardNumber ( ) {
            return cardNumber;
        }

        public String getExpMm ( ) {
            return expMm;
        }

        public String getExpYy ( ) {
            return expYy;
        }

        public String getCardName ( ) {
            return cardName;
        }

        public String getCreatedAt ( ) {
            return createdAt;
        }
    }

    public class PointsInfo {

        @SerializedName ("raw_total_price")
        @Expose
        private String rawTotalPrice;
        @SerializedName ("total_price")
        @Expose
        private Integer totalPrice;

        public String getRawTotalPrice ( ) {
            return rawTotalPrice;
        }

        public Integer getTotalPrice ( ) {
            return totalPrice;
        }
    }

    public class PaymentMethod {

        @SerializedName ("payment_id")
        @Expose
        private Integer paymentId;
        @SerializedName ("processor_id")
        @Expose
        private String processorId;
        @SerializedName("processor_params")
        @Expose
        private List<ProcessorParam> processorParams = null;
        @SerializedName ("payment")
        @Expose
        private String payment;
        @SerializedName ("description")
        @Expose
        private String description;
        @SerializedName ("main_image")
        @Expose
        private String mainImage;
        @SerializedName ("surcharge_value")
        @Expose
        private Integer surchargeValue;

        public Integer getPaymentId ( ) {
            return paymentId;
        }

        public String getProcessorId ( ) {
            return processorId;
        }

        public List<ProcessorParam> getProcessorParams () {
            return processorParams;
        }

        public String getPayment ( ) {
            return payment;
        }

        public String getDescription ( ) {
            return description;
        }

        public String getMainImage ( ) {
            return mainImage;
        }

        public Integer getSurchargeValue ( ) {
            return surchargeValue;
        }
    }
}



