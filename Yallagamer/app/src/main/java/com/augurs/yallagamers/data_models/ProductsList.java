package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductsList implements Serializable {
    @SerializedName("products")
    ArrayList<Product> products;
    public ArrayList<Product> getProducts() {
        return products;
    }
    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
