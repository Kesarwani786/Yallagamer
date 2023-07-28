package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Blog implements Serializable {

     @SerializedName("page_id")
     String page_id;
     @SerializedName("parent_id")
     String parent_id;
     @SerializedName("timestamp")
     String timestamp;
     @SerializedName("page")
     String page;
     @SerializedName("description")
     String description;
     @SerializedName("seo_name")
     String seo_name;
     @SerializedName("spoiler")
     String spoiler;
     @SerializedName("author")
     String author;
     @SerializedName("image_path")
     String image_path;

     public String getPage_id() {
          return page_id;
     }

     public void setPage_id(String page_id) {
          this.page_id = page_id;
     }

     public String getParent_id() {
          return parent_id;
     }

     public void setParent_id(String parent_id) {
          this.parent_id = parent_id;
     }

     public String getTimestamp() {
          return timestamp;
     }

     public void setTimestamp(String timestamp) {
          this.timestamp = timestamp;
     }

     public String getPage() {
          return page;
     }

     public void setPage(String page) {
          this.page = page;
     }

     public String getDescription() {
          return description;
     }

     public void setDescription(String description) {
          this.description = description;
     }

     public String getSeo_name() {
          return seo_name;
     }

     public void setSeo_name(String seo_name) {
          this.seo_name = seo_name;
     }

     public String getSpoiler() {
          return spoiler;
     }

     public void setSpoiler(String spoiler) {
          this.spoiler = spoiler;
     }

     public String getAuthor() {
          return author;
     }

     public void setAuthor(String author) {
          this.author = author;
     }

     public String getImage_path() {
          return image_path;
     }

     public void setImage_path(String image_path) {
          this.image_path = image_path;
     }
}
