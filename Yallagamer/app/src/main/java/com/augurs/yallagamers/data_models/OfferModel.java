package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferModel {

    @SerializedName("block_id")
    @Expose
    private String blockId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName ("image")
    @Expose
    private String image;
    @SerializedName ("category_id")
    @Expose
    private String category_id;

    @SerializedName ("category")
    @Expose
    private String category;

    private String ColorCode;

    public String getColorCode ( ) {
        return ColorCode;
    }

    public void setColorCode ( String colorCode ) {
        ColorCode = colorCode;
    }

    public String getCategory_id ( ) {
        return category_id;
    }

    public String getCategory ( ) {
        return category;
    }

    public void setCategory ( String category ) {
        this.category = category;
    }

    public void setCategory_id ( String category_id ) {
        this.category_id = category_id;
    }

    public String getBlockId ( ) {
        return blockId;
    }

    public void setBlockId ( String blockId ) {
        this.blockId = blockId;
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
