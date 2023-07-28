package com.augurs.yallagamers.data_models;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable {
    @SerializedName ( "product_cart_id" )
    @Expose
    String product_cart_id;
    @SerializedName("parent_product_id")
    String parent_product_id;
    @SerializedName("product_code")
    String product_code;
    @SerializedName("product_id")
    Integer product_id;
    @SerializedName("product")
    String product;
    @SerializedName("product_type")
    String product_type;
    @SerializedName("status")
    String status;
    @SerializedName("company_id")
    String company_id;
    @SerializedName("list_price")
    String list_price;
    @SerializedName("amount")
    Integer amount;
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
    @SerializedName("main_image")
    @Expose
    private String mainImage;

    @SerializedName("weight")
    String weight;
    @SerializedName("length")
    String length;
    @SerializedName("width")
    String width;
    @SerializedName("height")
    String height;
    @SerializedName("shipping_freight")
    String shipping_freight;
    @SerializedName("low_avail_limit")
    String low_avail_limit;
    @SerializedName("usergroup_ids")
    String usergroup_ids;
    @SerializedName("is_edp")
    String is_edp;
    @SerializedName("edp_shipping")
    String edp_shipping;
    @SerializedName("unlimited_download")
    String unlimited_download;
    @SerializedName("tracking")
    String tracking;
    @SerializedName("free_shipping")
    String free_shipping;
    @SerializedName("zero_price_action")
    String zero_price_action;
    @SerializedName("is_pbp")
    String is_pbp;
    @SerializedName("is_op")
    String is_op;
    @SerializedName("is_oper")
    String is_oper;
    @SerializedName("is_returnable")
    String is_returnable;
    @SerializedName("return_period")
    String return_period;
    @SerializedName("avail_since")
    String avail_since;
    @SerializedName("out_of_stock_action")
    String out_of_stock_action;
    @SerializedName("localization")
    String localization;
    @SerializedName("min_qty")
    String min_qty;
    @SerializedName("maxQuantity")
    Integer max_qty;
    @SerializedName("qty_step")
    String qty_step;
    @SerializedName("list_qty_count")
    String list_qty_count;
   /* @SerializedName("tax_ids")
    String tax_ids;*/
   /* @SerializedName("tax_ids")
    JsonArray tax_ids;*/
    @SerializedName("age_verification")
    String age_verification;
    @SerializedName("age_limit")
    String age_limit;
    @SerializedName("option_type")
    String option_type;
    @SerializedName("exception_type")
    String exception_type;
    @SerializedName("detail_layout")
    String detail_layout;
    @SerializedName("shipping_params")
    String shipping_params;
    @SerializedName("facebook_obj_type")
    String facebook_obj_type;
    @SerializedName("buy_now_url")
    String buy_now_url;
    @SerializedName("category_ids")
    JsonArray category_ids;
    @SerializedName("seo_name")
    String seo_name;
    @SerializedName("seo_path")
    String seo_path;
    @SerializedName("average_rating")
    String average_rating;
    @SerializedName("discussion_type")
    String discussion_type;
    @SerializedName("discussion_thread_id")
    String discussion_thread_id;
    @SerializedName("options_type_raw")
    String options_type_raw;
    @SerializedName("exception_type_raw")
    String exception_type_raw;
    @SerializedName("tracking_raw")
    String tracking_raw;
    @SerializedName("zero_price_action_raw")
    String zero_price_action_raw;
    @SerializedName("min_qty_raw")
    String min_qty_raw;
    @SerializedName("max_qty_raw")
    String max_qty_raw;
    @SerializedName("qty_step_raw")
    String qty_step_raw;
    @SerializedName("list_qty_count_raw")
    String list_qty_count_raw;
    @SerializedName("detail_layout_raw")
    String detail_layout_raw;
    //@SerializedName("validation_features")
    //JsonArray validation_features;
    @SerializedName("main_pair")
    JsonObject main_pair;
    @SerializedName("base_price")
    String base_price;
    //@SerializedName("selected_options")
    //JsonArray selected_options;
    @SerializedName("has_options")
    boolean has_options;
    //@SerializedName("product_options")
    //JsonArray product_options;
    //@SerializedName("discounts")
    //JsonObject discounts;
    //@SerializedName("product_features")
    //JsonArray product_features;
    //@SerializedName("qty_content")
    //JsonArray qty_content;
    @SerializedName("full_description")
     String full_description;
    @SerializedName ( "shortname" )
    private  String shortname;
    @SerializedName ( "estimated_delivery_date" )
    private  String estimated_delivery_date;

    @SerializedName("product_features")
    @Expose
    private List<Object> productFeatures = null;
    @SerializedName("product_options")
    @Expose
    private List<Object> productOptions = null;
    @SerializedName("selected_options")
    @Expose
    private List<Object> selectedOptions = null;
    @SerializedName("variation_features")
    @Expose
    private List <Object> variationFeatures = null;
    @SerializedName("in_wishlist")
    @Expose
    private String inWishlist;
    @SerializedName("discounts")
    @Expose
    private Discounts discounts;
    @SerializedName ( "quantity" )
    @Expose
    private  String quantity;
    @SerializedName ( "display_subtotal" )
    @Expose
    private  Double display_subtotal;

    public Double getDisplay_subtotal () {
        return display_subtotal;
    }

    boolean checkboxStatus;

    public String getProduct_cart_id ( ) {
        return product_cart_id;
    }

    public boolean isCheckboxStatus ( ) {
        return checkboxStatus;
    }

    public void setCheckboxStatus ( boolean checkboxStatus ) {
        this.checkboxStatus = checkboxStatus;
    }

    public String getQuantity ( ) {
        return quantity;
    }

    public void setQuantity ( String quantity ) {
        this.quantity = quantity;
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

    public String getMainImage ( ) {
        return mainImage;
    }

    public void setMainImage ( String mainImage ) {
        this.mainImage = mainImage;
    }

    public List < Object > getProductFeatures ( ) {
        return productFeatures;
    }

    public void setProductFeatures ( List < Object > productFeatures ) {
        this.productFeatures = productFeatures;
    }

    public List < Object > getProductOptions ( ) {
        return productOptions;
    }

    public void setProductOptions ( List < Object > productOptions ) {
        this.productOptions = productOptions;
    }

    public List < Object > getSelectedOptions ( ) {
        return selectedOptions;
    }

    public void setSelectedOptions ( List < Object > selectedOptions ) {
        this.selectedOptions = selectedOptions;
    }

    public List < Object > getVariationFeatures ( ) {
        return variationFeatures;
    }

    public void setVariationFeatures ( List < Object > variationFeatures ) {
        this.variationFeatures = variationFeatures;
    }

    public String getInWishlist ( ) {
        return inWishlist;
    }

    public void setInWishlist ( String inWishlist ) {
        this.inWishlist = inWishlist;
    }

    public Discounts getDiscounts ( ) {
        return discounts;
    }

    public void setDiscounts ( Discounts discounts ) {
        this.discounts = discounts;
    }

    public String getEstimated_delivery_date ( ) {
        return estimated_delivery_date;
    }

    public void setEstimated_delivery_date ( String estimated_delivery_date ) {
        this.estimated_delivery_date = estimated_delivery_date;
    }

    public String getShortname ( ) {
        return shortname;
    }

    public void setShortname ( String shortname ) {
        this.shortname = shortname;
    }

    public String getFull_description() {
        return full_description;
    }

    public void setFull_description(String full_description) {
        this.full_description = full_description;
    }

    public String getParent_product_id() {
        return parent_product_id;
    }

    public void setParent_product_id(String parent_product_id) {
        this.parent_product_id = parent_product_id;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getList_price() {
        return list_price;
    }

    public void setList_price(String list_price) {
        this.list_price = list_price;
    }

    public Integer getAmount() {
        return amount;
    }


    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getShipping_freight() {
        return shipping_freight;
    }

    public void setShipping_freight(String shipping_freight) {
        this.shipping_freight = shipping_freight;
    }

    public String getLow_avail_limit() {
        return low_avail_limit;
    }

    public void setLow_avail_limit(String low_avail_limit) {
        this.low_avail_limit = low_avail_limit;
    }

    public String getUsergroup_ids() {
        return usergroup_ids;
    }

    public void setUsergroup_ids(String usergroup_ids) {
        this.usergroup_ids = usergroup_ids;
    }

    public String getIs_edp() {
        return is_edp;
    }

    public void setIs_edp(String is_edp) {
        this.is_edp = is_edp;
    }

    public String getEdp_shipping() {
        return edp_shipping;
    }

    public void setEdp_shipping(String edp_shipping) {
        this.edp_shipping = edp_shipping;
    }

    public String getUnlimited_download() {
        return unlimited_download;
    }

    public void setUnlimited_download(String unlimited_download) {
        this.unlimited_download = unlimited_download;
    }

    public String getTracking() {
        return tracking;
    }

    public void setTracking(String tracking) {
        this.tracking = tracking;
    }

    public String getFree_shipping() {
        return free_shipping;
    }

    public void setFree_shipping(String free_shipping) {
        this.free_shipping = free_shipping;
    }

    public String getZero_price_action() {
        return zero_price_action;
    }

    public void setZero_price_action(String zero_price_action) {
        this.zero_price_action = zero_price_action;
    }

    public String getIs_pbp() {
        return is_pbp;
    }

    public void setIs_pbp(String is_pbp) {
        this.is_pbp = is_pbp;
    }

    public String getIs_op() {
        return is_op;
    }

    public void setIs_op(String is_op) {
        this.is_op = is_op;
    }

    public String getIs_oper() {
        return is_oper;
    }

    public void setIs_oper(String is_oper) {
        this.is_oper = is_oper;
    }

    public String getIs_returnable() {
        return is_returnable;
    }

    public void setIs_returnable(String is_returnable) {
        this.is_returnable = is_returnable;
    }

    public String getReturn_period() {
        return return_period;
    }

    public void setReturn_period(String return_period) {
        this.return_period = return_period;
    }

    public String getAvail_since() {
        return avail_since;
    }

    public void setAvail_since(String avail_since) {
        this.avail_since = avail_since;
    }

    public String getOut_of_stock_action() {
        return out_of_stock_action;
    }

    public void setOut_of_stock_action(String out_of_stock_action) {
        this.out_of_stock_action = out_of_stock_action;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public String getMin_qty() {
        return min_qty;
    }

    public void setMin_qty(String min_qty) {
        this.min_qty = min_qty;
    }

    public Integer getMax_qty() {
        return max_qty;
    }

    public String getQty_step() {
        return qty_step;
    }

    public void setQty_step(String qty_step) {
        this.qty_step = qty_step;
    }

    public String getList_qty_count() {
        return list_qty_count;
    }

    public void setList_qty_count(String list_qty_count) {
        this.list_qty_count = list_qty_count;
    }

   /* public String getTax_ids() {
        return tax_ids;
    }

    public void setTax_ids(String tax_ids) {
        this.tax_ids = tax_ids;
    }*/

    public String getAge_verification() {
        return age_verification;
    }

    public void setAge_verification(String age_verification) {
        this.age_verification = age_verification;
    }

    public String getAge_limit() {
        return age_limit;
    }

    public void setAge_limit(String age_limit) {
        this.age_limit = age_limit;
    }

    public String getOption_type() {
        return option_type;
    }

    public void setOption_type(String option_type) {
        this.option_type = option_type;
    }

    public String getException_type() {
        return exception_type;
    }

    public void setException_type(String exception_type) {
        this.exception_type = exception_type;
    }

    public String getDetail_layout() {
        return detail_layout;
    }

    public void setDetail_layout(String detail_layout) {
        this.detail_layout = detail_layout;
    }

    public String getShipping_params() {
        return shipping_params;
    }

    public void setShipping_params(String shipping_params) {
        this.shipping_params = shipping_params;
    }

    public String getFacebook_obj_type() {
        return facebook_obj_type;
    }

    public void setFacebook_obj_type(String facebook_obj_type) {
        this.facebook_obj_type = facebook_obj_type;
    }

    public String getBuy_now_url() {
        return buy_now_url;
    }

    public void setBuy_now_url(String buy_now_url) {
        this.buy_now_url = buy_now_url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public JsonArray getCategory_ids() {
        return category_ids;
    }

    public void setCategory_ids(JsonArray category_ids) {
        this.category_ids = category_ids;
    }

    public String getSeo_name() {
        return seo_name;
    }

    public void setSeo_name(String seo_name) {
        this.seo_name = seo_name;
    }

    public String getSeo_path() {
        return seo_path;
    }

    public void setSeo_path(String seo_path) {
        this.seo_path = seo_path;
    }

    public String getAverage_rating() {
        return average_rating;
    }

    public void setAverage_rating(String average_rating) {
        this.average_rating = average_rating;
    }

    public String getDiscussion_type() {
        return discussion_type;
    }

    public void setDiscussion_type(String discussion_type) {
        this.discussion_type = discussion_type;
    }

    public String getDiscussion_thread_id() {
        return discussion_thread_id;
    }

    public void setDiscussion_thread_id(String discussion_thread_id) {
        this.discussion_thread_id = discussion_thread_id;
    }

    public String getOptions_type_raw() {
        return options_type_raw;
    }

    public void setOptions_type_raw(String options_type_raw) {
        this.options_type_raw = options_type_raw;
    }

    public String getException_type_raw() {
        return exception_type_raw;
    }

    public void setException_type_raw(String exception_type_raw) {
        this.exception_type_raw = exception_type_raw;
    }

    public String getTracking_raw() {
        return tracking_raw;
    }

    public void setTracking_raw(String tracking_raw) {
        this.tracking_raw = tracking_raw;
    }

    public String getZero_price_action_raw() {
        return zero_price_action_raw;
    }

    public void setZero_price_action_raw(String zero_price_action_raw) {
        this.zero_price_action_raw = zero_price_action_raw;
    }

    public String getMin_qty_raw() {
        return min_qty_raw;
    }

    public void setMin_qty_raw(String min_qty_raw) {
        this.min_qty_raw = min_qty_raw;
    }

    public String getMax_qty_raw() {
        return max_qty_raw;
    }

    public void setMax_qty_raw(String max_qty_raw) {
        this.max_qty_raw = max_qty_raw;
    }

    public String getQty_step_raw() {
        return qty_step_raw;
    }

    public void setQty_step_raw(String qty_step_raw) {
        this.qty_step_raw = qty_step_raw;
    }

    public String getList_qty_count_raw() {
        return list_qty_count_raw;
    }

    public void setList_qty_count_raw(String list_qty_count_raw) {
        this.list_qty_count_raw = list_qty_count_raw;
    }

    public String getDetail_layout_raw() {
        return detail_layout_raw;
    }

    public void setDetail_layout_raw(String detail_layout_raw) {
        this.detail_layout_raw = detail_layout_raw;
    }

    /*public JsonArray getValidation_features() {
        return validation_features;
    }

    public void setValidation_features(JsonArray validation_features) {
        this.validation_features = validation_features;
    }*/

    public JsonObject getMain_pair() {
        return main_pair;
    }

    public void setMain_pair(JsonObject main_pair) {
        this.main_pair = main_pair;
    }

    public String getBase_price() {
        return base_price;
    }

    public void setBase_price(String base_price) {
        this.base_price = base_price;
    }

   /* public JsonArray getSelected_options() {
        return selected_options;
    }

    public void setSelected_options(JsonArray selected_options) {
        this.selected_options = selected_options;
    }*/

    public boolean isHas_options() {
        return has_options;
    }

    public void setHas_options(boolean has_options) {
        this.has_options = has_options;
    }

    /*public JsonArray getProduct_options() {
        return product_options;
    }

    public void setProduct_options(JsonArray product_options) {
        this.product_options = product_options;
    }*/

    /*public JsonObject getDiscounts() {
        return discounts;
    }

    public void setDiscounts(JsonObject discounts) {
        this.discounts = discounts;
    }*/

    /*public JsonArray getProduct_features() {
        return product_features;
    }

    public void setProduct_features(JsonArray product_features) {
        this.product_features = product_features;
    }*/

    /*public JsonArray getQty_content() {
        return qty_content;
    }

    public void setQty_content(JsonArray qty_content) {
        this.qty_content = qty_content;
    }*/


}
