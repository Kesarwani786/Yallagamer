package com.augurs.yallagamers.api_module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentGatewayBodyRequest {

    @SerializedName("action")
    @Expose
    private String action;
    @SerializedName("amount")
    @Expose
    private Amount amount;
    @SerializedName("merchantAttributes")
    @Expose
    private MerchantAttributes merchantAttributes;
    @SerializedName("emailAddress")
    @Expose
    private String emailAddress;
    @SerializedName("billingAddress")
    @Expose
    private BillingAddress billingAddress;

    public void setAction ( String action ) {
        this.action = action;
    }

    public void setAmount ( Amount amount ) {
        this.amount = amount;
    }

    public void setMerchantAttributes ( MerchantAttributes merchantAttributes ) {
        this.merchantAttributes = merchantAttributes;
    }

    public void setEmailAddress ( String emailAddress ) {
        this.emailAddress = emailAddress;
    }

    public void setBillingAddress ( BillingAddress billingAddress ) {
        this.billingAddress = billingAddress;
    }
    public static class MerchantAttributes {

        @SerializedName ("redirectUrl")
        @Expose
        private String redirectUrl;
        @SerializedName ("skipConfirmationPage")
        @Expose
        private Boolean skipConfirmationPage;

        public void setRedirectUrl ( String redirectUrl ) {
            this.redirectUrl = redirectUrl;
        }

        public void setSkipConfirmationPage ( Boolean skipConfirmationPage ) {
            this.skipConfirmationPage = skipConfirmationPage;
        }
    }
    public static class Amount {

        @SerializedName ("currencyCode")
        @Expose
        private String currencyCode;
        @SerializedName ("value")
        @Expose
        private String value;

        public void setCurrencyCode ( String currencyCode ) {
            this.currencyCode = currencyCode;
        }

        public void setValue ( String value ) {
            this.value = value;
        }
    }
    public static class BillingAddress {

        @SerializedName ("firstName")
        @Expose
        private String firstName;
        @SerializedName ("lastName")
        @Expose
        private String lastName;
        @SerializedName ("address1")
        @Expose
        private String address1;
        @SerializedName ("city")
        @Expose
        private String city;
        @SerializedName ("countryCode")
        @Expose
        private String countryCode;

        public void setFirstName ( String firstName ) {
            this.firstName = firstName;
        }

        public void setLastName ( String lastName ) {
            this.lastName = lastName;
        }

        public void setAddress1 ( String address1 ) {
            this.address1 = address1;
        }

        public void setCity ( String city ) {
            this.city = city;
        }

        public void setCountryCode ( String countryCode ) {
            this.countryCode = countryCode;
        }
    }


}
