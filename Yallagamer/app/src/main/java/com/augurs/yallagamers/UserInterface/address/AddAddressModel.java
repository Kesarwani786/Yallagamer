package com.augurs.yallagamers.UserInterface.address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddAddressModel {


    @SerializedName (  "method" )
    @Expose
    private  String method;

    public void setMethod ( String method ) {
        this.method = method;
    }
    @SerializedName (  "profile_id" )
    @Expose
    private  Integer profile_id;
    public Integer getProfile_id ( ) {
        return profile_id;
    }
    public void setProfile_id ( Integer profile_id ) {
        this.profile_id = profile_id;
    }
    @SerializedName("b_firstname")
    @Expose
    private String bFirstname;
    @SerializedName("b_lastname")
    @Expose
    private String bLastname;
    @SerializedName("b_address")
    @Expose
    private String bAddress;
    @SerializedName("b_email")
    @Expose
    private String bEmail;
    @SerializedName("b_address_2")
    @Expose
    private String bAddress2;
    @SerializedName("b_city")
    @Expose
    private String bCity;
    @SerializedName("b_county")
    @Expose
    private String bCounty;
    @SerializedName("b_state")
    @Expose
    private String bState;
    @SerializedName("b_country")
    @Expose
    private String bCountry;
    @SerializedName("b_zipcode")
    @Expose
    private String bZipcode;
    @SerializedName("b_phone")
    @Expose
    private String bPhone;
    @SerializedName("s_firstname")
    @Expose
    private String sFirstname;
    @SerializedName("s_lastname")
    @Expose
    private String sLastname;
    @SerializedName("s_email")
    @Expose
    private String sEmail;
    @SerializedName("s_address")
    @Expose
    private String sAddress;
    @SerializedName("s_address_2")
    @Expose
    private String sAddress2;
    @SerializedName("s_city")
    @Expose
    private String sCity;
    @SerializedName("s_state")
    @Expose
    private String sState;
    @SerializedName("s_country")
    @Expose
    private String sCountry;
    @SerializedName("s_zipcode")
    @Expose
    private String sZipcode;
    @SerializedName("s_phone")
    @Expose
    private String sPhone;
    @SerializedName("profile_name")
    @Expose
    private String sAddressType;
    @SerializedName("is_same")
    @Expose
    private Integer isSame;
    @SerializedName("customer_notes")
    @Expose
    private String customerNotes;
    @SerializedName("is_default")
    @Expose
    private int isDefault;

    public String getbFirstname() {
        return bFirstname;
    }

    public void setbFirstname(String bFirstname) {
        this.bFirstname = bFirstname;
    }

    public String getbLastname() {
        return bLastname;
    }

    public void setbLastname(String bLastname) {
        this.bLastname = bLastname;
    }

    public String getbAddress() {
        return bAddress;
    }

    public void setbAddress(String bAddress) {
        this.bAddress = bAddress;
    }

    public String getbEmail() {
        return bEmail;
    }

    public void setbEmail(String bEmail) {
        this.bEmail = bEmail;
    }

    public String getbAddress2() {
        return bAddress2;
    }

    public void setbAddress2(String bAddress2) {
        this.bAddress2 = bAddress2;
    }

    public String getbCity() {
        return bCity;
    }

    public void setbCity(String bCity) {
        this.bCity = bCity;
    }

    public String getbCounty() {
        return bCounty;
    }

    public void setbCounty(String bCounty) {
        this.bCounty = bCounty;
    }

    public String getbState() {
        return bState;
    }

    public void setbState(String bState) {
        this.bState = bState;
    }

    public String getbCountry() {
        return bCountry;
    }

    public void setbCountry(String bCountry) {
        this.bCountry = bCountry;
    }

    public String getbZipcode() {
        return bZipcode;
    }

    public void setbZipcode(String bZipcode) {
        this.bZipcode = bZipcode;
    }

    public String getbPhone() {
        return bPhone;
    }

    public void setbPhone(String bPhone) {
        this.bPhone = bPhone;
    }

    public String getsFirstname() {
        return sFirstname;
    }

    public void setsFirstname(String sFirstname) {
        this.sFirstname = sFirstname;
    }

    public String getsLastname() {
        return sLastname;
    }

    public void setsLastname(String sLastname) {
        this.sLastname = sLastname;
    }

    public String getsEmail() {
        return sEmail;
    }

    public void setsEmail(String sEmail) {
        this.sEmail = sEmail;
    }

    public String getsAddress() {
        return sAddress;
    }

    public void setsAddress(String sAddress) {
        this.sAddress = sAddress;
    }

    public String getsAddress2() {
        return sAddress2;
    }

    public void setsAddress2(String sAddress2) {
        this.sAddress2 = sAddress2;
    }

    public String getsCity() {
        return sCity;
    }

    public void setsCity(String sCity) {
        this.sCity = sCity;
    }

    public String getsState() {
        return sState;
    }

    public void setsState(String sState) {
        this.sState = sState;
    }

    public String getsCountry() {
        return sCountry;
    }

    public void setsCountry(String sCountry) {
        this.sCountry = sCountry;
    }

    public String getsZipcode() {
        return sZipcode;
    }

    public void setsZipcode(String sZipcode) {
        this.sZipcode = sZipcode;
    }

    public String getsPhone() {
        return sPhone;
    }

    public void setsPhone(String sPhone) {
        this.sPhone = sPhone;
    }

    public String getsAddressType() {
        return sAddressType;
    }

    public void setsAddressType(String sAddressType) {
        this.sAddressType = sAddressType;
    }

    public Integer getIsSame() {
        return isSame;
    }

    public void setIsSame(Integer isSame) {
        this.isSame = isSame;
    }

    public String getCustomerNotes() {
        return customerNotes;
    }

    public void setCustomerNotes(String customerNotes) {
        this.customerNotes = customerNotes;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault ( int isDefault ) {
        this.isDefault = isDefault;
    }
}
