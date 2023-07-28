package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AddressLIstingModel {
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
    private ArrayList<Datum> data = null;

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

    public ArrayList<Datum> getData() {
        return data;
    }

    public void setData(ArrayList<Datum> data) {
        this.data = data;
    }
    public class Datum {
        @SerializedName ( "is_same" )
        @Expose
        private  Integer is_same;

        public Integer getIs_same ( ) {
            return is_same;
        }

        public void setIs_same ( Integer is_same ) {
            this.is_same = is_same;
        }

        @SerializedName("profile_id")
        @Expose
        private Integer profileId;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("profile_type")
        @Expose
        private String profileType;
        @SerializedName("b_firstname")
        @Expose
        private String bFirstname;
        @SerializedName("b_lastname")
        @Expose
        private String bLastname;
        @SerializedName("b_address")
        @Expose
        private String bAddress;
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
        @SerializedName ( "customer_notes" )
        @Expose
        private String customer_notes;
        @SerializedName("s_address")
        @Expose
        private String sAddress;
        @SerializedName("s_address_2")
        @Expose
        private String sAddress2;
        @SerializedName("s_city")
        @Expose
        private String sCity;
        @SerializedName("s_county")
        @Expose
        private String sCounty;
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
        @SerializedName("s_address_type")
        @Expose
        private String sAddressType;
        @SerializedName("profile_name")
        @Expose
        private String profileName;
        @SerializedName("profile_update_timestamp")
        @Expose
        private String profileUpdateTimestamp;
        @SerializedName("is_default")
        @Expose
        private int isDefault;
        @SerializedName("user_shipping_address_id")
        @Expose
        private int userShippingAddressId;
        @SerializedName ( "s_email" )
        @Expose
        private  String s_email;
        @SerializedName ( "b_email" )
        @Expose
        private  String b_email;
        public String getS_email ( ) {
            return s_email;
        }
        public String getCustomer_notes ( ) {
            return customer_notes;
        }
        public void setCustomer_notes ( String customer_notes ) {
            this.customer_notes = customer_notes;
        }

        public Integer getProfileId( ) {
            return profileId;
        }

        public void setProfileId( Integer profileId ) {
            this.profileId = profileId;
        }

        public String getUserId( ) {
            return userId;
        }

        public void setUserId( String userId ) {
            this.userId = userId;
        }

        public String getProfileType( ) {
            return profileType;
        }

        public void setProfileType( String profileType ) {
            this.profileType = profileType;
        }

        public String getbFirstname( ) {
            return bFirstname;
        }

        public void setbFirstname( String bFirstname ) {
            this.bFirstname = bFirstname;
        }

        public String getbLastname( ) {
            return bLastname;
        }

        public void setbLastname( String bLastname ) {
            this.bLastname = bLastname;
        }

        public String getbAddress( ) {
            return bAddress;
        }

        public void setbAddress( String bAddress ) {
            this.bAddress = bAddress;
        }

        public String getbAddress2( ) {
            return bAddress2;
        }

        public void setbAddress2( String bAddress2 ) {
            this.bAddress2 = bAddress2;
        }

        public String getbCity( ) {
            return bCity;
        }

        public void setbCity( String bCity ) {
            this.bCity = bCity;
        }

        public String getbCounty( ) {
            return bCounty;
        }

        public void setbCounty( String bCounty ) {
            this.bCounty = bCounty;
        }

        public String getbState( ) {
            return bState;
        }

        public void setbState( String bState ) {
            this.bState = bState;
        }

        public String getbCountry( ) {
            return bCountry;
        }

        public void setbCountry( String bCountry ) {
            this.bCountry = bCountry;
        }

        public String getbZipcode( ) {
            return bZipcode;
        }

        public void setbZipcode( String bZipcode ) {
            this.bZipcode = bZipcode;
        }

        public String getbPhone( ) {
            return bPhone;
        }

        public void setbPhone( String bPhone ) {
            this.bPhone = bPhone;
        }

        public String getsFirstname( ) {
            return sFirstname;
        }

        public void setsFirstname( String sFirstname ) {
            this.sFirstname = sFirstname;
        }

        public String getsLastname( ) {
            return sLastname;
        }

        public void setsLastname( String sLastname ) {
            this.sLastname = sLastname;
        }

        public String getsAddress( ) {
            return sAddress;
        }

        public void setsAddress( String sAddress ) {
            this.sAddress = sAddress;
        }

        public String getsAddress2( ) {
            return sAddress2;
        }

        public void setsAddress2( String sAddress2 ) {
            this.sAddress2 = sAddress2;
        }

        public String getsCity( ) {
            return sCity;
        }

        public void setsCity( String sCity ) {
            this.sCity = sCity;
        }

        public String getsCounty( ) {
            return sCounty;
        }

        public void setsCounty( String sCounty ) {
            this.sCounty = sCounty;
        }

        public String getsState( ) {
            return sState;
        }

        public void setsState( String sState ) {
            this.sState = sState;
        }

        public String getsCountry( ) {
            return sCountry;
        }

        public void setsCountry( String sCountry ) {
            this.sCountry = sCountry;
        }

        public String getsZipcode( ) {
            return sZipcode;
        }

        public void setsZipcode( String sZipcode ) {
            this.sZipcode = sZipcode;
        }

        public String getsPhone( ) {
            return sPhone;
        }

        public void setsPhone( String sPhone ) {
            this.sPhone = sPhone;
        }

        public String getsAddressType( ) {
            return sAddressType;
        }

        public void setsAddressType( String sAddressType ) {
            this.sAddressType = sAddressType;
        }

        public String getProfileName( ) {
            return profileName;
        }

        public void setProfileName( String profileName ) {
            this.profileName = profileName;
        }

        public String getProfileUpdateTimestamp( ) {
            return profileUpdateTimestamp;
        }

        public void setProfileUpdateTimestamp( String profileUpdateTimestamp ) {
            this.profileUpdateTimestamp = profileUpdateTimestamp;
        }

        public int getIsDefault( ) {
            return isDefault;
        }

        public void setIsDefault( int isDefault ) {
            this.isDefault = isDefault;
        }

        public int getUserShippingAddressId( ) {
            return userShippingAddressId;
        }

        public void setUserShippingAddressId( int userShippingAddressId ) {
            this.userShippingAddressId = userShippingAddressId;
        }
    }
}