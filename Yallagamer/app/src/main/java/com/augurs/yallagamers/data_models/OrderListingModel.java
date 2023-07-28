package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OrderListingModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("data")
    @Expose
    private Data data;

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public Data getData() {
        return data;
    }


    public class Data {

        @SerializedName("Order")
        @Expose
        private ArrayList<Order> order = new ArrayList<>();
     /*   @SerializedName("Params")
        @Expose
        private Params params;
*/
        public List<Order> getOrder() {
            return order;
        }

        public void setOrder(ArrayList<Order> order) {
            this.order = order;
        }

  /*      public Params getParams() {
            return params;
        }

        public void setParams(Params params) {
            this.params = params;
        }
*/
    }
    public class Order {

        @SerializedName("order_id")
        @Expose
        private String orderId;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("timestamp")
        @Expose
        private String timestamp;
        @SerializedName("firstname")
        @Expose
        private String firstname;
        @SerializedName("lastname")
        @Expose
        private String lastname;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("color")
        @Expose
        private String color;
        @SerializedName("total")
        @Expose
        private String total;

        public String getOrderId() {
            return orderId;
        }

        public String getUserId() {
            return userId;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public String getFirstname() {
            return firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone() {
            return phone;
        }

        public String getStatus() {
            return status;
        }

        public String getColor() {
            return color;
        }

        public String getTotal() {
            return total;
        }
    }
    public class Params {

        @SerializedName("page")
        @Expose
        private Integer page;
        @SerializedName("items_per_page")
        @Expose
        private Boolean itemsPerPage;
        @SerializedName("extra")
        @Expose
        private List<String> extra = null;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("include_incompleted")
        @Expose
        private Boolean includeIncompleted;
        @SerializedName("sort_order")
        @Expose
        private String sortOrder;
        @SerializedName("sort_by")
        @Expose
        private String sortBy;
        @SerializedName("sort_order_rev")
        @Expose
        private String sortOrderRev;

        public Integer getPage() {
            return page;
        }

        public Boolean getItemsPerPage() {
            return itemsPerPage;
        }

        public List<String> getExtra() {
            return extra;
        }

        public String getUserId() {
            return userId;
        }

        public Boolean getIncludeIncompleted() {
            return includeIncompleted;
        }

        public String getSortOrder() {
            return sortOrder;
        }

        public String getSortBy() {
            return sortBy;
        }

        public String getSortOrderRev() {
            return sortOrderRev;
        }
    }
}
