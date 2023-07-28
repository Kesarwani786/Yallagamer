package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderStatusResponse {
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
    private List<Datum> data = null;
    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public List<Datum> getData() {
        return data;
    }
    public class Datum {

        @SerializedName("Status")
        @Expose
        private String status;
        @SerializedName("Color")
        @Expose
        private String color;
        @SerializedName("Name")
        @Expose
        private String name;
        private boolean isChecked=false;

        public String getStatus() {
            return status;
        }

        public String getColor() {
            return color;
        }

        public String getName() {
            return name;
        }

        public boolean getChecked () {
            return isChecked;
        }

        public void setChecked ( boolean checked ) {
            isChecked = checked;
        }
    }
}
