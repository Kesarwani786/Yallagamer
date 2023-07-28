package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckUserExistEmail {

    @SerializedName ("status")
    @Expose
    private Integer status;
    @SerializedName ("message")
    @Expose
    private String message;
    @SerializedName ("token")
    @Expose
    private String token;
    @SerializedName ("data")
    @Expose
    private Data data;

    public Integer getStatus ( ) {
        return status;
    }

    public String getMessage ( ) {
        return message;
    }

    public String getToken ( ) {
        return token;
    }

    public Data getData ( ) {
        return data;
    }

    public class Data {
        @SerializedName ("userid")
        @Expose
        private String userid;
        @SerializedName ("message")
        @Expose
        private String Message;

        @SerializedName ("IsUser")
        @Expose
        private Boolean isUser;

        public String getMessage ( ) {
            return Message;
        }

        public String getUserid ( ) {
            return userid;
        }

        public Boolean getUser ( ) {
            return isUser;
        }
    }
}
