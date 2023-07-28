package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductFeatures {

    @SerializedName("feature_id")
    @Expose
    private String featureId;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("value_int")
    @Expose
    private Object valueInt;
    @SerializedName("variant_id")
    @Expose
    private String variantId;
    @SerializedName("feature_type")
    @Expose
    private String featureType;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("prefix")
    @Expose
    private String prefix;
    @SerializedName("suffix")
    @Expose
    private String suffix;
    @SerializedName("variant")
    @Expose
    private String variant;
    @SerializedName("parent_id")
    @Expose
    private String parentId;
    @SerializedName("display_on_header")
    @Expose
    private String displayOnHeader;
    @SerializedName("display_on_catalog")
    @Expose
    private String displayOnCatalog;
    @SerializedName("display_on_product")
    @Expose
    private String displayOnProduct;
    @SerializedName("feature_code")
    @Expose
    private String featureCode;
    @SerializedName ("variants")
    @Expose
    private List <Variant> variants = null;

    public String getFeatureId ( ) {
        return featureId;
    }

    public void setFeatureId ( String featureId ) {
        this.featureId = featureId;
    }

    public String getValue ( ) {
        return value;
    }

    public void setValue ( String value ) {
        this.value = value;
    }

    public Object getValueInt ( ) {
        return valueInt;
    }

    public void setValueInt ( Object valueInt ) {
        this.valueInt = valueInt;
    }

    public String getVariantId ( ) {
        return variantId;
    }

    public void setVariantId ( String variantId ) {
        this.variantId = variantId;
    }

    public String getFeatureType ( ) {
        return featureType;
    }

    public void setFeatureType ( String featureType ) {
        this.featureType = featureType;
    }

    public String getDescription ( ) {
        return description;
    }

    public void setDescription ( String description ) {
        this.description = description;
    }

    public String getPrefix ( ) {
        return prefix;
    }

    public void setPrefix ( String prefix ) {
        this.prefix = prefix;
    }

    public String getSuffix ( ) {
        return suffix;
    }

    public void setSuffix ( String suffix ) {
        this.suffix = suffix;
    }

    public String getVariant ( ) {
        return variant;
    }

    public void setVariant ( String variant ) {
        this.variant = variant;
    }

    public String getParentId ( ) {
        return parentId;
    }

    public void setParentId ( String parentId ) {
        this.parentId = parentId;
    }

    public String getDisplayOnHeader ( ) {
        return displayOnHeader;
    }

    public void setDisplayOnHeader ( String displayOnHeader ) {
        this.displayOnHeader = displayOnHeader;
    }

    public String getDisplayOnCatalog ( ) {
        return displayOnCatalog;
    }

    public void setDisplayOnCatalog ( String displayOnCatalog ) {
        this.displayOnCatalog = displayOnCatalog;
    }

    public String getDisplayOnProduct ( ) {
        return displayOnProduct;
    }

    public void setDisplayOnProduct ( String displayOnProduct ) {
        this.displayOnProduct = displayOnProduct;
    }

    public String getFeatureCode ( ) {
        return featureCode;
    }

    public void setFeatureCode ( String featureCode ) {
        this.featureCode = featureCode;
    }

    public List < Variant > getVariants ( ) {
        return variants;
    }

    public void setVariants ( List < Variant > variants ) {
        this.variants = variants;
    }
}
