package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryBaseParameter {

    @SerializedName ("area")
    @Expose
    private String area;
    @SerializedName("use_caching")
    @Expose
    private Boolean useCaching;
    @SerializedName("extend")
    @Expose
    private List<String> extend = null;
    @SerializedName("custom_extend")
    @Expose
    private List <Object> customExtend = null;
    @SerializedName("pname")
    @Expose
    private String pname;
    @SerializedName("pshort")
    @Expose
    private String pshort;
    @SerializedName("pfull")
    @Expose
    private String pfull;
    @SerializedName("pkeywords")
    @Expose
    private String pkeywords;
    @SerializedName("feature")
    @Expose
    private List<Object> feature = null;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("action")
    @Expose
    private String action;
    @SerializedName("filter_variants")
    @Expose
    private List<Object> filterVariants = null;
    @SerializedName("features_hash")
    @Expose
    private String featuresHash;
    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("bid")
    @Expose
    private Integer bid;
    @SerializedName("match")
    @Expose
    private String match;
    @SerializedName("tracking")
    @Expose
    private List<Object> tracking = null;
    @SerializedName("get_frontend_urls")
    @Expose
    private Boolean getFrontendUrls;
    @SerializedName("items_per_page")
    @Expose
    private Integer itemsPerPage;
    @SerializedName("apply_disabled_filters")
    @Expose
    private String applyDisabledFilters;
    @SerializedName("load_products_extra_data")
    @Expose
    private Boolean loadProductsExtraData;
    @SerializedName("storefront")
    @Expose
    private Object storefront;
    @SerializedName("company_ids")
    @Expose
    private String companyIds;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("cid")
    @Expose
    private String cid;
    @SerializedName("hide_out_of_stock_products")
    @Expose
    private Boolean hideOutOfStockProducts;
    @SerializedName("sort_by")
    @Expose
    private String sortBy;
    @SerializedName("sort_order")
    @Expose
    private String sortOrder;
    @SerializedName("include_child_variations")
    @Expose
    private Boolean includeChildVariations;
    @SerializedName("group_child_variations")
    @Expose
    private Boolean groupChildVariations;
    @SerializedName("sort_order_rev")
    @Expose
    private String sortOrderRev;
    @SerializedName("total_items")
    @Expose
    private String totalItems;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Boolean getUseCaching() {
        return useCaching;
    }

    public void setUseCaching(Boolean useCaching) {
        this.useCaching = useCaching;
    }

    public List<String> getExtend() {
        return extend;
    }

    public void setExtend(List<String> extend) {
        this.extend = extend;
    }

    public List<Object> getCustomExtend() {
        return customExtend;
    }

    public void setCustomExtend(List<Object> customExtend) {
        this.customExtend = customExtend;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPshort() {
        return pshort;
    }

    public void setPshort(String pshort) {
        this.pshort = pshort;
    }

    public String getPfull() {
        return pfull;
    }

    public void setPfull(String pfull) {
        this.pfull = pfull;
    }

    public String getPkeywords() {
        return pkeywords;
    }

    public void setPkeywords(String pkeywords) {
        this.pkeywords = pkeywords;
    }

    public List<Object> getFeature() {
        return feature;
    }

    public void setFeature(List<Object> feature) {
        this.feature = feature;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<Object> getFilterVariants() {
        return filterVariants;
    }

    public void setFilterVariants(List<Object> filterVariants) {
        this.filterVariants = filterVariants;
    }

    public String getFeaturesHash() {
        return featuresHash;
    }

    public void setFeaturesHash(String featuresHash) {
        this.featuresHash = featuresHash;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public List<Object> getTracking() {
        return tracking;
    }

    public void setTracking(List<Object> tracking) {
        this.tracking = tracking;
    }

    public Boolean getGetFrontendUrls() {
        return getFrontendUrls;
    }

    public void setGetFrontendUrls(Boolean getFrontendUrls) {
        this.getFrontendUrls = getFrontendUrls;
    }

    public Integer getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(Integer itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public String getApplyDisabledFilters() {
        return applyDisabledFilters;
    }

    public void setApplyDisabledFilters(String applyDisabledFilters) {
        this.applyDisabledFilters = applyDisabledFilters;
    }

    public Boolean getLoadProductsExtraData() {
        return loadProductsExtraData;
    }

    public void setLoadProductsExtraData(Boolean loadProductsExtraData) {
        this.loadProductsExtraData = loadProductsExtraData;
    }

    public Object getStorefront() {
        return storefront;
    }

    public void setStorefront(Object storefront) {
        this.storefront = storefront;
    }

    public String getCompanyIds() {
        return companyIds;
    }

    public void setCompanyIds(String companyIds) {
        this.companyIds = companyIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Boolean getHideOutOfStockProducts() {
        return hideOutOfStockProducts;
    }

    public void setHideOutOfStockProducts(Boolean hideOutOfStockProducts) {
        this.hideOutOfStockProducts = hideOutOfStockProducts;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean getIncludeChildVariations() {
        return includeChildVariations;
    }

    public void setIncludeChildVariations(Boolean includeChildVariations) {
        this.includeChildVariations = includeChildVariations;
    }

    public Boolean getGroupChildVariations() {
        return groupChildVariations;
    }

    public void setGroupChildVariations(Boolean groupChildVariations) {
        this.groupChildVariations = groupChildVariations;
    }

    public String getSortOrderRev() {
        return sortOrderRev;
    }

    public void setSortOrderRev(String sortOrderRev) {
        this.sortOrderRev = sortOrderRev;
    }

    public String getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }

}
