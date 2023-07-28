package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review {
    @SerializedName("thread_id")
    @Expose
    private String threadId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("rating_value")
    @Expose
    private String ratingValue;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName ("title")
    @Expose
    private String title;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("message")
    @Expose
    private String message;

    public String getThreadId ( ) {
        return threadId;
    }

    public void setThreadId ( String threadId ) {
        this.threadId = threadId;
    }

    public String getType ( ) {
        return type;
    }

    public void setType ( String type ) {
        this.type = type;
    }

    public String getRatingValue ( ) {
        return ratingValue;
    }

    public void setRatingValue ( String ratingValue ) {
        this.ratingValue = ratingValue;
    }

    public String getName ( ) {
        return name;
    }

    public void setName ( String name ) {
        this.name = name;
    }

    public String getUserId ( ) {
        return userId;
    }

    public void setUserId ( String userId ) {
        this.userId = userId;
    }

    public String getTitle ( ) {
        return title;
    }

    public void setTitle ( String title ) {
        this.title = title;
    }

    public String getEmail ( ) {
        return email;
    }

    public void setEmail ( String email ) {
        this.email = email;
    }

    public String getTimestamp ( ) {
        return timestamp;
    }

    public void setTimestamp ( String timestamp ) {
        this.timestamp = timestamp;
    }

    public String getMessage ( ) {
        return message;
    }

    public void setMessage ( String message ) {
        this.message = message;
    }
}
