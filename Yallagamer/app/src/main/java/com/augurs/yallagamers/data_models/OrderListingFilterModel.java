package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OrderListingFilterModel {
    @SerializedName("method")
    @Expose
    private String method="getOrderlist";
    @SerializedName("status")
    @Expose
    private ArrayList<String> status = null;
    @SerializedName("time_from")
    @Expose
    private String timeFrom;
    @SerializedName("time_to")
    @Expose
    private String timeTo;
    @SerializedName("items_per_page")
    @Expose
    private Integer itemsPerPage;
    @SerializedName("page")
    @Expose
    private Integer page=1;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("total_sec_from")
    @Expose
    private Integer totalSecFrom;
    @SerializedName("total_sec_to")
    @Expose
    private Integer totalSecTo;
    @SerializedName ("period")
    @Expose
    private String period;



    public void setStatus ( ArrayList<String> status ) {
        this.status = status;
    }

    public void setTimeFrom ( String timeFrom ) {
        this.timeFrom = timeFrom;
    }

    public void setTimeTo ( String timeTo ) {
        this.timeTo = timeTo;
    }
    public void setItemsPerPage ( Integer itemsPerPage ) {
        this.itemsPerPage = itemsPerPage;
    }

    public void setOrderId ( String orderId ) {
        this.orderId = orderId;
    }

    public void setTotalSecFrom ( Integer totalSecFrom ) {
        this.totalSecFrom = totalSecFrom;
    }

    public void setTotalSecTo ( Integer totalSecTo ) {
        this.totalSecTo = totalSecTo;
    }

    public void setPeriod ( String period ) {
        this.period = period;
    }
}
