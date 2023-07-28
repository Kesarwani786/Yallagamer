package com.augurs.yallagamers.data_models;

import com.google.gson.JsonArray;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class HomePage implements Serializable {

    @SerializedName("banner_images")
    JsonArray home_banner;

    @SerializedName("gadgest_accessories")
    ArrayList<Gadgets_Accessories> gadgets_accessories;

    @SerializedName("collectors_editions")
    ArrayList<Collectors_Editions> collectors_editions;

    @SerializedName("top_games")
    ArrayList<Top_Games> top_games;

    @SerializedName("blog")
    ArrayList<Blog> blogs;

    @SerializedName("box")
    ArrayList<Box> box;

    @SerializedName("categories")
    @Expose
    private ArrayList <Category> categories ;
    @SerializedName("AdvertisementHome")
    @Expose
    private ArrayList <AdvertisementHome> advertisementHome ;
    @SerializedName( "wishlistCounts")
    @Expose
    private Integer wishlistCounts=0;
    @SerializedName( "cartCounts")
    @Expose
    private Integer cartCounts=0;

    public Integer getWishlistCounts () {
        return wishlistCounts;
    }

    public Integer getCartCounts () {
        return cartCounts;
    }

    public ArrayList < AdvertisementHome > getAdvertisementHome ( ) {
        return advertisementHome;
    }

    public void setAdvertisementHome ( ArrayList < AdvertisementHome > advertisementHome ) {
        this.advertisementHome = advertisementHome;
    }

    @SerializedName("status")
    Integer status;

    @SerializedName("offer_corner")
    ArrayList<OfferModel> offerModels;

    public ArrayList < OfferModel > getOfferModels ( ) {
        return offerModels;
    }

    public void setOfferModels ( ArrayList < OfferModel > offerModels ) {
        this.offerModels = offerModels;
    }

    public ArrayList < Category > getCategories ( ) {
        return categories;
    }

    public void setCategories ( ArrayList < Category > categories ) {
        this.categories = categories;
    }

    public ArrayList < Box > getBox ( ) {
        return box;
    }

    public void setBox ( ArrayList < Box > box ) {
        this.box = box;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }



    public JsonArray getHome_banner() {
        return home_banner;
    }

    public void setHome_banner(JsonArray home_banner) {
        this.home_banner = home_banner;
    }

    public ArrayList<Gadgets_Accessories> getGadgets_accessories() {
        return gadgets_accessories;
    }

    public void setGadgets_accessories(ArrayList<Gadgets_Accessories> gadgets_accessories) {
        this.gadgets_accessories = gadgets_accessories;
    }

    public ArrayList<Collectors_Editions> getCollectors_editions() {
        return collectors_editions;
    }

    public void setCollectors_editions(ArrayList<Collectors_Editions> collectors_editions) {
        this.collectors_editions = collectors_editions;
    }

    public ArrayList<Top_Games> getTop_games() {
        return top_games;
    }

    public void setTop_games(ArrayList<Top_Games> top_games) {
        this.top_games = top_games;
    }

    public ArrayList<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(ArrayList<Blog> blogs) {
        this.blogs = blogs;
    }
}
