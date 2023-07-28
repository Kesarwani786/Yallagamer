package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class RecommendedForYou {

    @SerializedName ("product_id")
    @Expose
    private Integer productId;
    @SerializedName ("list_price")
    @Expose
    private double listPrice;
    @SerializedName ("base_price")
    @Expose
    private double basePrice;
    @SerializedName ("amount")
    @Expose
    private String amount;
    @SerializedName ("main_category")
    @Expose
    private Integer mainCategory;
    @SerializedName ("stock")
    @Expose
    private String stock;
    @SerializedName ("shortname")
    @Expose
    private String shortname;
    @SerializedName ("page_title")
    @Expose
    private String pageTitle;
    @SerializedName ("enable_preorder")
    @Expose
    private String enablePreorder;
    @SerializedName ("image_path")
    @Expose
    private String imagePath;
    @SerializedName ("product_options")
    @Expose
    private Object productOptions;
    @SerializedName ("selected_options")
    @Expose
    private Object selectedOptions;
    @SerializedName ("variation_features")
    @Expose
    private Object variationFeatures;
    @SerializedName ("in_wishlist")
    @Expose
    private String inWishlist;
    @SerializedName ("category_id")
    @Expose
    private String categoryId;
    @SerializedName ("category")
    @Expose
    private String category;
    @SerializedName ("category_image")
    @Expose
    private String categoryImage;

    public Integer getProductId ( ) {
        return productId;
    }

    public double getListPrice ( ) {
        return listPrice;
    }

    public double getBasePrice ( ) {
        return basePrice;
    }

    public String getAmount ( ) {
        return amount;
    }

    public Integer getMainCategory ( ) {
        return mainCategory;
    }

    public String getStock ( ) {
        return stock;
    }

    public String getShortname ( ) {
        return shortname;
    }

    public String getPageTitle ( ) {
        return pageTitle;
    }

    public String getEnablePreorder ( ) {
        return enablePreorder;
    }

    public String getImagePath ( ) {
        return imagePath;
    }

    public Object getProductOptions ( ) {
        return productOptions;
    }

    public Object getSelectedOptions ( ) {
        return selectedOptions;
    }

    public Object getVariationFeatures ( ) {
        return variationFeatures;
    }

    public String getInWishlist ( ) {
        return inWishlist;
    }

    public String getCategoryId ( ) {
        return categoryId;
    }

    public String getCategory ( ) {
        return category;
    }

    public String getCategoryImage ( ) {
        return categoryImage;
    }
}