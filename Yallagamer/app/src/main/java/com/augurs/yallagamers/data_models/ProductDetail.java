package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
public class ProductDetail {
    @SerializedName ( "Product_rating")
    @Expose
    private  String Product_rating;
    @SerializedName ("product_url")
    @Expose
    private  String product_url;

    public String getProduct_url ( ) {
        return product_url;
    }

    public String getProduct_rating ( ) {
        return Product_rating;
    }

    @SerializedName ("wishlistCounts")
    @Expose
    private  Integer wishlistCounts;
    @SerializedName ("cartCounts")
    @Expose
    private  Integer cartCounts;

    public Integer getWishlistCounts ( ) {
        return wishlistCounts;
    }

    public Integer getCartCounts ( ) {
        return cartCounts;
    }

    @SerializedName ("maxQuantity")
    @Expose
    private  Integer maxQuantity;
    public Integer getMaxQuantity ( ) {
        return maxQuantity;
    }
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("product")
    @Expose
    private String product;
    @SerializedName("product_code")
    @Expose
    private String productCode;
    @SerializedName("status")
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
    @SerializedName("is_edp")
    @Expose
    private String isEdp;
    @SerializedName("short_description")
    @Expose
    private String shortDescription;
    @SerializedName("full_description")
    @Expose
    private String fullDescription;
    @SerializedName("page_title")
    @Expose
    private String pageTitle;
    @SerializedName("popularity")
    @Expose
    private String popularity;
    @SerializedName("enable_preorder")
    @Expose
    private String enablePreorder;
    @SerializedName("enable_preorder_type")
    @Expose
    private String enablePreorderType;
    @SerializedName("preorder_price")
    @Expose
    private String preorderPrice;
    @SerializedName("available_date")
    @Expose
    private String availableDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("point_price")
    @Expose
    private Object pointPrice;
    @SerializedName("points")
    @Expose
    private Integer points;
    @SerializedName("main_image")
    @Expose
    private String mainImage;
    @SerializedName("allimages")
    @Expose
    private ArrayList<String> allimages = null;
    @SerializedName("product_features")
    @Expose
    private ArrayList<ProductFeatures> productFeatures = null;
    @SerializedName("product_options")
    @Expose
    private Object productOptions;
    @SerializedName("selected_options")
    @Expose
    private Object selectedOptions;
    @SerializedName("variation_features")
    @Expose
    private Object variationFeatures;
    @SerializedName("in_wishlist")
    @Expose
    private String inWishlist;
    @SerializedName("discounts")
    @Expose
    private Object discounts;
    @SerializedName("shippings")
    @Expose
    private Shippings shippings;
    @SerializedName("variation")
    @Expose
    private ArrayList<Variation> variation = null;
    @SerializedName ("reviews")
    @Expose
    private ArrayList <Review> reviews = null;
    public ArrayList < ProductFeatures > getProductFeatures ( ) {
        return productFeatures;
    }
    public void setProductFeatures ( ArrayList < ProductFeatures > productFeatures ) {
        this.productFeatures = productFeatures;
    }

    public Integer getProductId ( ) {
        return productId;
    }

    public void setProductId ( Integer productId ) {
        this.productId = productId;
    }

    public String getProduct ( ) {
        return product;
    }

    public void setProduct ( String product ) {
        this.product = product;
    }

    public String getProductCode ( ) {
        return productCode;
    }

    public void setProductCode ( String productCode ) {
        this.productCode = productCode;
    }

    public String getStatus ( ) {
        return status;
    }

    public void setStatus ( String status ) {
        this.status = status;
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

    public void setAmount ( String amount ) {
        this.amount = amount;
    }

    public String getPrice ( ) {
        return price;
    }

    public void setPrice ( String price ) {
        this.price = price;
    }

    public String getStock ( ) {
        return stock;
    }

    public void setStock ( String stock ) {
        this.stock = stock;
    }

    public Integer getMainCategoryId ( ) {
        return mainCategoryId;
    }

    public void setMainCategoryId ( Integer mainCategoryId ) {
        this.mainCategoryId = mainCategoryId;
    }

    public String getMainCategory ( ) {
        return mainCategory;
    }

    public void setMainCategory ( String mainCategory ) {
        this.mainCategory = mainCategory;
    }

    public String getIsEdp ( ) {
        return isEdp;
    }

    public void setIsEdp ( String isEdp ) {
        this.isEdp = isEdp;
    }

    public String getShortDescription ( ) {
        return shortDescription;
    }

    public void setShortDescription ( String shortDescription ) {
        this.shortDescription = shortDescription;
    }

    public String getFullDescription ( ) {
        return fullDescription;
    }

    public void setFullDescription ( String fullDescription ) {
        this.fullDescription = fullDescription;
    }

    public String getPageTitle ( ) {
        return pageTitle;
    }

    public void setPageTitle ( String pageTitle ) {
        this.pageTitle = pageTitle;
    }

    public String getPopularity ( ) {
        return popularity;
    }

    public void setPopularity ( String popularity ) {
        this.popularity = popularity;
    }

    public String getEnablePreorder ( ) {
        return enablePreorder;
    }

    public void setEnablePreorder ( String enablePreorder ) {
        this.enablePreorder = enablePreorder;
    }

    public String getEnablePreorderType ( ) {
        return enablePreorderType;
    }

    public void setEnablePreorderType ( String enablePreorderType ) {
        this.enablePreorderType = enablePreorderType;
    }

    public String getPreorderPrice ( ) {
        return preorderPrice;
    }

    public void setPreorderPrice ( String preorderPrice ) {
        this.preorderPrice = preorderPrice;
    }

    public String getAvailableDate ( ) {
        return availableDate;
    }

    public void setAvailableDate ( String availableDate ) {
        this.availableDate = availableDate;
    }

    public String getEndDate ( ) {
        return endDate;
    }

    public void setEndDate ( String endDate ) {
        this.endDate = endDate;
    }

    public Object getPointPrice ( ) {
        return pointPrice;
    }

    public void setPointPrice ( Object pointPrice ) {
        this.pointPrice = pointPrice;
    }

    public Integer getPoints ( ) {
        return points;
    }

    public void setPoints ( Integer points ) {
        this.points = points;
    }

    public String getMainImage ( ) {
        return mainImage;
    }

    public void setMainImage ( String mainImage ) {
        this.mainImage = mainImage;
    }

    public ArrayList < String > getAllimages ( ) {
        return allimages;
    }



    public Object getProductOptions ( ) {
        return productOptions;
    }

    public void setProductOptions ( Object productOptions ) {
        this.productOptions = productOptions;
    }

    public Object getSelectedOptions ( ) {
        return selectedOptions;
    }

    public void setSelectedOptions ( Object selectedOptions ) {
        this.selectedOptions = selectedOptions;
    }

    public Object getVariationFeatures ( ) {
        return variationFeatures;
    }

    public void setVariationFeatures ( Object variationFeatures ) {
        this.variationFeatures = variationFeatures;
    }

    public String getInWishlist ( ) {
        return inWishlist;
    }

    public void setInWishlist ( String inWishlist ) {
        this.inWishlist = inWishlist;
    }

    public Object getDiscounts ( ) {
        return discounts;
    }

    public void setDiscounts ( Object discounts ) {
        this.discounts = discounts;
    }

    public Shippings getShippings ( ) {
        return shippings;
    }

    public void setShippings ( Shippings shippings ) {
        this.shippings = shippings;
    }

    public ArrayList < Variation > getVariation ( ) {
        return variation;
    }

    public void setVariation ( ArrayList < Variation > variation ) {
        this.variation = variation;
    }

    public ArrayList < Review > getReviews ( ) {
        return reviews;
    }

    public void setReviews ( ArrayList < Review > reviews ) {
        this.reviews = reviews;
    }
}
