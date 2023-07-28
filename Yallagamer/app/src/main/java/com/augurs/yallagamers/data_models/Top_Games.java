package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Top_Games implements Serializable {

    @SerializedName("product_id")
    Integer product_id;
    @SerializedName("list_price")
    String list_price;
    @SerializedName("amount")
    String amount;
    @SerializedName("main_category")
    String main_category;
    @SerializedName("shortname")
    String shortname;
    @SerializedName("page_title")
    String page_title;
    @SerializedName("enable_preorder")
    String enable_preorder;
    @SerializedName("image_path")
    String image_path;
    @SerializedName ( "category" )
    String category;

    public String getCategory ( ) {
        return category;
    }

    public void setCategory ( String category ) {
        this.category = category;
    }

    public Integer getProduct_id() {
        return product_id;
    }


    public String getList_price() {
        return list_price;
    }

    public void setList_price(String list_price) {
        this.list_price = list_price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMain_category() {
        return main_category;
    }

    public void setMain_category(String main_category) {
        this.main_category = main_category;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getPage_title() {
        return page_title;
    }

    public void setPage_title(String page_title) {
        this.page_title = page_title;
    }

    public String getEnable_preorder() {
        return enable_preorder;
    }

    public void setEnable_preorder(String enable_preorder) {
        this.enable_preorder = enable_preorder;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
}
