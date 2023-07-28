package com.augurs.yallagamers.UserInterface.payment.PaymentGatewayRequest;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaymentMethods {
        @SerializedName("card")
        private List<String> card;
    }