
package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class UserData implements Serializable {

    @SerializedName("status")
    int status;
    @SerializedName("message")
    String message;
    @SerializedName("token")
    String token;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }



    public class Data{
        @SerializedName("user_id")
        @Expose
        private String user_id;

        @SerializedName("status")
        @Expose
        private String status;

        @SerializedName("user_type")
        @Expose
        private String user_type;

        @SerializedName("user_login")
        @Expose
        private String user_login;

        @SerializedName("company_id")
        @Expose
        private String company_id;

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

        @SerializedName("lang_code")
        @Expose
        private String lang_code;


        @SerializedName("profile_id")
        @Expose
        private String profile_id;


        @SerializedName("profile_type")
        @Expose
        private String profile_type;

        @SerializedName("profile_name")
        @Expose
        private String profile_name;

        @SerializedName("s_address")
        @Expose
        private String s_address;

        @SerializedName("s_address_2")
        @Expose
        private String s_address_2;

        @SerializedName("s_city")
        @Expose
        private String s_city;

        @SerializedName("s_country")
        @Expose
        private String s_country;

        @SerializedName("s_state")
        @Expose
        private String s_state;

        @SerializedName("s_zipcode")
        @Expose
        private String s_zipcode;


        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public String getUser_login() {
            return user_login;
        }

        public void setUser_login(String user_login) {
            this.user_login = user_login;
        }

        public String getCompany_id() {
            return company_id;
        }

        public void setCompany_id(String company_id) {
            this.company_id = company_id;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getLang_code() {
            return lang_code;
        }

        public void setLang_code(String lang_code) {
            this.lang_code = lang_code;
        }

        public String getProfile_id() {
            return profile_id;
        }

        public void setProfile_id(String profile_id) {
            this.profile_id = profile_id;
        }

        public String getProfile_type() {
            return profile_type;
        }

        public void setProfile_type(String profile_type) {
            this.profile_type = profile_type;
        }

        public String getProfile_name() {
            return profile_name;
        }

        public void setProfile_name(String profile_name) {
            this.profile_name = profile_name;
        }

        public String getS_address() {
            return s_address;
        }

        public void setS_address(String s_address) {
            this.s_address = s_address;
        }

        public String getS_address_2() {
            return s_address_2;
        }

        public void setS_address_2(String s_address_2) {
            this.s_address_2 = s_address_2;
        }

        public String getS_city() {
            return s_city;
        }

        public void setS_city(String s_city) {
            this.s_city = s_city;
        }

        public String getS_country() {
            return s_country;
        }

        public void setS_country(String s_country) {
            this.s_country = s_country;
        }

        public String getS_state() {
            return s_state;
        }

        public void setS_state(String s_state) {
            this.s_state = s_state;
        }

        public String getS_zipcode() {
            return s_zipcode;
        }

        public void setS_zipcode(String s_zipcode) {
            this.s_zipcode = s_zipcode;
        }
    }

}

