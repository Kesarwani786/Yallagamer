package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdvertisementHome {

    @SerializedName ("block_id")
    @Expose
    private String blockId;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;

    public String getBlockId ( ) {
        return blockId;
    }

    public void setBlockId ( String blockId ) {
        this.blockId = blockId;
    }

    public String getCategoryId ( ) {
        return categoryId;
    }

    public void setCategoryId ( String categoryId ) {
        this.categoryId = categoryId;
    }

    public String getCategory ( ) {
        return category;
    }

    public void setCategory ( String category ) {
        this.category = category;
    }

    public String getName ( ) {
        return name;
    }

    public void setName ( String name ) {
        this.name = name;
    }

    public String getImage ( ) {
        return image;
    }

    public void setImage ( String image ) {
        this.image = image;
    }
}

