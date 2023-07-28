package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SimilarProducts {
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("product")
    @Expose
    private String product;
    @SerializedName("product_code")
    @Expose
    private String productCode;
    @SerializedName ("status")
    @Expose
    private String status;
    @SerializedName("list_price")
    @Expose
    private double listPrice;
    @SerializedName("base_price")
    @Expose
    private double basePrice;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("stock")
    @Expose
    private String stock;
    @SerializedName("main_category_id")
    @Expose
    private Integer mainCategoryId;
    @SerializedName("main_category")
    @Expose
    private String mainCategory;
    @SerializedName("main_image")
    @Expose
    private String mainImage;

    public Integer getProductId ( ) {
        return productId;
    }

    public String getProduct ( ) {
        return product;
    }

    public String getProductCode ( ) {
        return productCode;
    }

    public String getStatus ( ) {
        return status;
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

    public String getPrice ( ) {
        return price;
    }

    public String getStock ( ) {
        return stock;
    }

    public Integer getMainCategoryId ( ) {
        return mainCategoryId;
    }

    public String getMainCategory ( ) {
        return mainCategory;
    }

    public String getMainImage ( ) {
        return mainImage;
    }
}
