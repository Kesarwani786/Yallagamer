package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Collectors_Editions implements Serializable {
    @SerializedName("list_price")
    double list_price;
    @SerializedName ( "base_price" )
    double base_price;
    @SerializedName("product_id")
    Integer product_id;
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
    @SerializedName (  "in_wishlist" )
    String in_wishlist;
    @SerializedName ( "category_id" )
    Integer category_id;
    @SerializedName ( "category" )
    String category;
    @SerializedName ( "category_image" )
    String category_image;

    public double getList_price ( ) {
        return list_price;
    }

    public double getBase_price ( ) {
        return base_price;
    }

    public String getIn_wishlist ( ) {
        return in_wishlist;
    }

    public void setIn_wishlist ( String in_wishlist ) {
        this.in_wishlist = in_wishlist;
    }

    public Integer getCategory_id ( ) {
        return category_id;
    }

    public void setCategory_id ( Integer category_id ) {
        this.category_id = category_id;
    }

    public String getCategory ( ) {
        return category;
    }

    public void setCategory ( String category ) {
        this.category = category;
    }

    public String getCategory_image ( ) {
        return category_image;
    }

    public void setCategory_image ( String category_image ) {
        this.category_image = category_image;
    }

    public Integer getProduct_id() {
        return product_id;
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
