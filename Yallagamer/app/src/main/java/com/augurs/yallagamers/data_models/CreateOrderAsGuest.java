package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateOrderAsGuest {

    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("payment_id")
    @Expose
    private Integer paymentId;
    @SerializedName("ship_to_another")
    @Expose
    private String shipToAnother;
    @SerializedName("customer_notes")
    @Expose
    private String customerNotes;

    @SerializedName ("user_data")
    @Expose
    private UserData userData;
    @SerializedName ("cart_id")
    @Expose
    private  String cart_id;

    public void setCart_id ( String cart_id ) {
        this.cart_id = cart_id;
    }

    public void setMethod ( String method ) {
        this.method = method;
    }

    public void setPaymentId ( Integer paymentId ) {
        this.paymentId = paymentId;
    }

    public void setShipToAnother ( String shipToAnother ) {
        this.shipToAnother = shipToAnother;
    }

    public void setCustomerNotes ( String customerNotes ) {
        this.customerNotes = customerNotes;
    }

    public void setUserData ( UserData userData ) {
        this.userData = userData;
    }
    public static class UserData {

        @SerializedName ("s_country")
        @Expose
        private String sCountry;
        @SerializedName ("s_city")
        @Expose
        private String sCity;
        @SerializedName ("s_state")
        @Expose
        private String sState;
        @SerializedName ("firstname")
        @Expose
        private String firstname;
        @SerializedName ("lastname")
        @Expose
        private String lastname;
        @SerializedName ("email")
        @Expose
        private String email;
        @SerializedName ("s_firstname")
        @Expose
        private String sFirstname;
        @SerializedName ("s_lastname")
        @Expose
        private String sLastname;
        @SerializedName ("s_address")
        @Expose
        private String sAddress;
        @SerializedName ("s_address_2")
        @Expose
        private String sAddress2;
        @SerializedName ("s_phone")
        @Expose
        private String sPhone;
        @SerializedName ("s_zipcode")
        @Expose
        private String sZipcode;
        @SerializedName ("ship_to_another")
        @Expose
        private String shipToAnother;
        @SerializedName ("phone")
        @Expose
        private String phone;
        @SerializedName ("b_firstname")
        @Expose
        private String bFirstname;
        @SerializedName ("b_lastname")
        @Expose
        private String bLastname;
        @SerializedName ("b_address")
        @Expose
        private String bAddress;
        @SerializedName ("b_phone")
        @Expose
        private String bPhone;
        @SerializedName ("b_zipcode")
        @Expose
        private String bZipcode;
        @SerializedName ("b_city")
        @Expose
        private String bCity;
        @SerializedName ("b_state")
        @Expose
        private String bState;

        public void setsCountry ( String sCountry ) {
            this.sCountry = sCountry;
        }

        public void setsCity ( String sCity ) {
            this.sCity = sCity;
        }

        public void setsState ( String sState ) {
            this.sState = sState;
        }

        public void setFirstname ( String firstname ) {
            this.firstname = firstname;
        }

        public void setLastname ( String lastname ) {
            this.lastname = lastname;
        }

        public void setEmail ( String email ) {
            this.email = email;
        }

        public void setsFirstname ( String sFirstname ) {
            this.sFirstname = sFirstname;
        }

        public void setsLastname ( String sLastname ) {
            this.sLastname = sLastname;
        }

        public void setsAddress ( String sAddress ) {
            this.sAddress = sAddress;
        }

        public void setsAddress2 ( String sAddress2 ) {
            this.sAddress2 = sAddress2;
        }

        public void setsPhone ( String sPhone ) {
            this.sPhone = sPhone;
        }

        public void setsZipcode ( String sZipcode ) {
            this.sZipcode = sZipcode;
        }

        public void setShipToAnother ( String shipToAnother ) {
            this.shipToAnother = shipToAnother;
        }

        public void setPhone ( String phone ) {
            this.phone = phone;
        }

        public void setbFirstname ( String bFirstname ) {
            this.bFirstname = bFirstname;
        }

        public void setbLastname ( String bLastname ) {
            this.bLastname = bLastname;
        }

        public void setbAddress ( String bAddress ) {
            this.bAddress = bAddress;
        }

        public void setbPhone ( String bPhone ) {
            this.bPhone = bPhone;
        }

        public void setbZipcode ( String bZipcode ) {
            this.bZipcode = bZipcode;
        }

        public void setbCity ( String bCity ) {
            this.bCity = bCity;
        }

        public void setbState ( String bState ) {
            this.bState = bState;
        }
    }
}
