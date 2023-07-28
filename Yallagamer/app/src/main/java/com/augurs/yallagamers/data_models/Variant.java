package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Variant {
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("value_int")
    @Expose
    private Object valueInt;
    @SerializedName("variant_id")
    @Expose
    private String variantId;
    @SerializedName("variant")
    @Expose
    private String variant;
    @SerializedName ("image_pairs")
    @Expose
    private Boolean imagePairs;

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

    public String getVariant ( ) {
        return variant;
    }

    public void setVariant ( String variant ) {
        this.variant = variant;
    }

    public Boolean getImagePairs ( ) {
        return imagePairs;
    }

    public void setImagePairs ( Boolean imagePairs ) {
        this.imagePairs = imagePairs;
    }
}
