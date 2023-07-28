package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("zipcode")
    @Expose
    private String zipcode;
    @SerializedName ("address")
    @Expose
    private String address;

    public String getCity ( ) {
        return city;
    }

    public void setCity ( String city ) {
        this.city = city;
    }

    public String getState ( ) {
        return state;
    }

    public void setState ( String state ) {
        this.state = state;
    }

    public String getCountry ( ) {
        return country;
    }

    public void setCountry ( String country ) {
        this.country = country;
    }

    public String getZipcode ( ) {
        return zipcode;
    }

    public void setZipcode ( String zipcode ) {
        this.zipcode = zipcode;
    }

    public String getAddress ( ) {
        return address;
    }

    public void setAddress ( String address ) {
        this.address = address;
    }
}
