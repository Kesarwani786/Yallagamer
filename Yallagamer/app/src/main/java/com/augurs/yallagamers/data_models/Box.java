package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Box {
    @SerializedName ("block_id")
    @Expose
    private Integer blockId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName ("category_id")
    @Expose
    private String category_id;



    @SerializedName("category")
    @Expose
    private String Category;

    @SerializedName("image")
    @Expose
    private String image;

    public String getCategory_id ( ) {
        return category_id;
    }

    public Integer getBlockId() {
        return blockId;
    }


    public String getCategory ( ) {
        return Category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
