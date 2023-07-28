package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CardData implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("user_id")
    @Expose
    private String user_id;

    @SerializedName("card_number")
    @Expose
    private String card_number;


    @SerializedName("exp_mm")
    @Expose
    private String exp_mm;

    @SerializedName("exp_yy")
    @Expose
    private String exp_yy;

    @SerializedName("card_name")
    @Expose
    private String card_name;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getExp_mm() {
        return exp_mm;
    }

    public void setExp_mm(String exp_mm) {
        this.exp_mm = exp_mm;
    }

    public String getExp_yy() {
        return exp_yy;
    }

    public void setExp_yy(String exp_yy) {
        this.exp_yy = exp_yy;
    }

    public String getCard_name() {
        return card_name;
    }

    public void setCard_name(String card_name) {
        this.card_name = card_name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
