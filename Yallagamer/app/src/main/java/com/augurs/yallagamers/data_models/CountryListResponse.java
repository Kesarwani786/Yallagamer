package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountryListResponse {

    @SerializedName ("status")
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
    private List<Datum> data = null;

    public Integer getStatus ( ) {
        return status;
    }

    public String getMessage ( ) {
        return message;
    }

    public String getToken ( ) {
        return token;
    }

    public List<Datum> getData ( ) {
        return data;
    }
    public class Datum {

        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("country")
        @Expose
        private String country;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

    }

}
