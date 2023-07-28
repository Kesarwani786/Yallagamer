package com.augurs.yallagamers.data_models;

import com.google.gson.JsonArray;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class StaticCategoryBaseResponce {
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
    private Data data;

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

    public Data getData() {
        return data;
    }
    public void setData(Data data) {
        this.data = data;
    }
    public class Data {
        @SerializedName ("recommended_for_you")
        @Expose
        private ArrayList < RecommendedForYou > recommendedForYou = null;
        @SerializedName ("banner_images")
        @Expose
        private JsonArray bannerImages = null;
        @SerializedName ("box")
        @Expose
        private ArrayList < Box > box = null;
        public ArrayList < RecommendedForYou > getRecommendedForYou ( ) {
            return recommendedForYou;
        }
        public JsonArray getBannerImages ( ) {
            return bannerImages;
        }


        public ArrayList < Box > getBox ( ) {
            return box;
        }
    }

}
