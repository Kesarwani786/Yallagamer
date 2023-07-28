package com.augurs.yallagamers.UserInterface.payment.PaymentGatewayRequest;

import com.google.gson.annotations.SerializedName;

public class PaymentLinks {
        @SerializedName("payment")
        private Href payment;
        @SerializedName("payment-authorization")
        private Href paymentAuthorization;

        public Href getPayment() {
            return payment;
        }

        public Href getPaymentAuthorization() {
            return paymentAuthorization;
        }
    }