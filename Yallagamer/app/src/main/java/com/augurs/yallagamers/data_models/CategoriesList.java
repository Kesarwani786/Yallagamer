package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class CategoriesList implements Serializable {


    /*@SerializedName("categories")
    JsonArray categories;*/

    @SerializedName("categories")
    ArrayList<Category> categories;

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    /* public JsonArray getCategories() {
        return categories;
    }

    public void setCategories(JsonArray categories) {
        this.categories = categories;
    }*/
}
