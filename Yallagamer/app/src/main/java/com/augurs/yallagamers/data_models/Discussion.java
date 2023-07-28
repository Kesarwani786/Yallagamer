package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Discussion implements Serializable {

    @SerializedName("post_id")
    String post_id;
    @SerializedName("thread_id")
    String thread_id;
    @SerializedName("name")
    String name;
    @SerializedName("timestamp")
    String timestamp;
    @SerializedName("user_id")
    String user_id;
    @SerializedName("ip_address")
    String ip_address;
    @SerializedName("status")
    String status;
    @SerializedName("title")
    String title;
    @SerializedName("email")
    String email;
    @SerializedName("message")
    String message;
    @SerializedName("rating_value")
    String rating_value;
    @SerializedName("object_id")
    String object_id;
    @SerializedName("object_type")
    String object_type;
    @SerializedName("type")
    String type;
    @SerializedName("company_id")
    String company_id;


    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getThread_id() {
        return thread_id;
    }

    public void setThread_id(String thread_id) {
        this.thread_id = thread_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRating_value() {
        return rating_value;
    }

    public void setRating_value(String rating_value) {
        this.rating_value = rating_value;
    }

    public String getObject_id() {
        return object_id;
    }

    public void setObject_id(String object_id) {
        this.object_id = object_id;
    }

    public String getObject_type() {
        return object_type;
    }

    public void setObject_type(String object_type) {
        this.object_type = object_type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }
}
