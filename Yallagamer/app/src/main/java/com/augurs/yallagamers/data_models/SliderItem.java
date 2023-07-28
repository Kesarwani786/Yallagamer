package com.augurs.yallagamers.data_models;

import android.graphics.drawable.Drawable;

public class SliderItem
{
    private String description;
    private String imageUrl;
    private Drawable   imgUrl;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Drawable getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(Drawable imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }
}