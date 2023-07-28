package com.augurs.yallagamers.UserInterface.payment.PaymentGatewayRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentGatewayAccessTokenResponse {
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("refresh_token")
    @Expose
    private String refreshToken;
    @SerializedName("expires_in")
    @Expose
    private Integer expiresIn;
    @SerializedName("refresh_expires_in")
    @Expose
    private Integer refreshExpiresIn;
    @SerializedName ("token_type")
    @Expose
    private String tokenType;

    public String getAccessToken ( ) {
        return accessToken;
    }

    public String getRefreshToken ( ) {
        return refreshToken;
    }

    public Integer getExpiresIn ( ) {
        return expiresIn;
    }

    public Integer getRefreshExpiresIn ( ) {
        return refreshExpiresIn;
    }

    public String getTokenType ( ) {
        return tokenType;
    }
}
