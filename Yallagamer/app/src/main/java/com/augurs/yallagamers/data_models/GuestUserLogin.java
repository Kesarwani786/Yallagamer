package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GuestUserLogin {
    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName ("user_data")
    @Expose
    private UserData userData;

    public void setMethod ( String method ) {
        this.method = method;
    }

    public void setOrderId ( String orderId ) {
        this.orderId = orderId;
    }

    public void setUserData ( UserData userData ) {
        this.userData = userData;
    }

    public static class UserData {
        @SerializedName ("password1")
        @Expose
        private String password1;
        @SerializedName ("password2")
        @Expose
        private String password2;

        public UserData ( String password1, String password2 ) {
            this.password1 = password1;
            this.password2 = password2;
        }
    }
}
