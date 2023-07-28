package com.augurs.yallagamers.data_models;

import com.augurs.yallagamers.api_module.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PrivacyPolicy extends BaseResponse {

    @SerializedName("data")
    List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data{
        @SerializedName("description")
        String description;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }




}
