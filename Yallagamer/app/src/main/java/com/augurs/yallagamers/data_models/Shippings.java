package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shippings {


    @SerializedName ("shipping_id")
    @Expose
    private Integer shippingId;
    @SerializedName("shipping")
    @Expose
    private String shipping;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("rate")
    @Expose
    private Double rate;
    @SerializedName("service_delivery_time")
    @Expose
    private String serviceDeliveryTime;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName ( "selected" )
    @Expose
    private Boolean selected;
    @SerializedName("tracking_number")
    @Expose
    private String trackingNumber;
    @SerializedName("carrier_info_name")
    @Expose
    private String carrierInfoName;
    @SerializedName("tracking_url")
    @Expose
    private String trackingUrl;



    public String getTrackingNumber () {
        return trackingNumber;
    }

    public String getCarrierInfoName () {
        return carrierInfoName;
    }

    public String getTrackingUrl () {
        return trackingUrl;
    }

    public Boolean getSelected ( ) {
        return selected;
    }


    public String getDescription ( ) {
        return description;
    }

    public String getImage ( ) {
        return image;
    }

    public Integer getShippingId ( ) {
        return shippingId;
    }


    public String getShipping ( ) {
        return shipping;
    }

    public void setShipping ( String shipping ) {
        this.shipping = shipping;
    }

    public Location getLocation ( ) {
        return location;
    }

    public void setLocation ( Location location ) {
        this.location = location;
    }

    public Double getRate ( ) {
        return rate;
    }

    public void setRate ( Double rate ) {
        this.rate = rate;
    }

    public String getServiceDeliveryTime ( ) {
        return serviceDeliveryTime;
    }

    public void setServiceDeliveryTime ( String serviceDeliveryTime ) {
        this.serviceDeliveryTime = serviceDeliveryTime;
    }
}
