package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Category implements Serializable {


    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("lang_code")
    @Expose
    private String langCode;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("meta_keywords")
    @Expose
    private String metaKeywords;
    @SerializedName("meta_description")
    @Expose
    private String metaDescription;
    @SerializedName("page_title")
    @Expose
    private String pageTitle;
    @SerializedName("age_warning_message")
    @Expose
    private String ageWarningMessage;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName ( "product_count" )
    @Expose
    private  Integer Product_count;

    public Integer getProduct_count ( ) {
        return Product_count;
    }

    public void setProduct_count ( Integer product_count ) {
        Product_count = product_count;
    }

    public String getDescription ( ) {
        return description;
    }

    public void setDescription ( String description ) {
        this.description = description;
    }

    public String getAgeWarningMessage ( ) {
        return ageWarningMessage;
    }

    public void setAgeWarningMessage ( String ageWarningMessage ) {
        this.ageWarningMessage = ageWarningMessage;
    }

    public String getCategoryId ( ) {
        return categoryId;
    }

    public void setCategoryId ( String categoryId ) {
        this.categoryId = categoryId;
    }

    public String getLangCode ( ) {
        return langCode;
    }

    public void setLangCode ( String langCode ) {
        this.langCode = langCode;
    }

    public String getCategory ( ) {
        return category;
    }

    public void setCategory ( String category ) {
        this.category = category;
    }



    public String getMetaKeywords ( ) {
        return metaKeywords;
    }

    public void setMetaKeywords ( String metaKeywords ) {
        this.metaKeywords = metaKeywords;
    }

    public String getMetaDescription ( ) {
        return metaDescription;
    }

    public void setMetaDescription ( String metaDescription ) {
        this.metaDescription = metaDescription;
    }

    public String getPageTitle ( ) {
        return pageTitle;
    }

    public void setPageTitle ( String pageTitle ) {
        this.pageTitle = pageTitle;
    }

    public String getImage ( ) {
        return image;
    }

    public void setImage ( String image ) {
        this.image = image;
    }
}
