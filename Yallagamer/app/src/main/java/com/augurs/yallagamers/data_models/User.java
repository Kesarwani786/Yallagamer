package com.augurs.yallagamers.data_models;

import com.augurs.yallagamers.api_module.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {

    // User Details
    @SerializedName("profile_id")
    String profile_id;

    @SerializedName("profile_type")
    String profile_type;


    @SerializedName("user_id")
    String user_id;

    @SerializedName("user_type")
    String user_type;

    @SerializedName("user_login")
    String user_login;

    @SerializedName("status")
    String status;

    @SerializedName("company_id")
    String company_id;

    @SerializedName("is_root")
    String is_root;

    @SerializedName("firstname")
    String firstname;

    @SerializedName("lastname")
    String lastname;

    @SerializedName("phone")
    String phone;

    @SerializedName("email")
    String email;

    @SerializedName("lang_code")
    String lang_code;


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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getIs_root() {
        return is_root;
    }

    public void setIs_root(String is_root) {
        this.is_root = is_root;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLang_code() {
        return lang_code;
    }

    public void setLang_code(String lang_code) {
        this.lang_code = lang_code;
    }

    // Billing Details
    @SerializedName("b_firstname")
    String b_firstname;

    @SerializedName("b_lastname")
    String b_lastname;

    @SerializedName("b_address")
    String b_address;

    @SerializedName("b_address_2")
    String b_address_2;

    @SerializedName("b_city")
    String b_city;

    @SerializedName("b_county")
    String b_county;
    @SerializedName("b_state")
    String b_state;
    @SerializedName("b_country")
    String b_country;
    @SerializedName("b_zipcode")
    String b_zipcode;
    @SerializedName("b_phone")
    String b_phone;

    public String getB_firstname() {
        return b_firstname;
    }

    public void setB_firstname(String b_firstname) {
        this.b_firstname = b_firstname;
    }

    public String getB_lastname() {
        return b_lastname;
    }

    public void setB_lastname(String b_lastname) {
        this.b_lastname = b_lastname;
    }

    public String getB_address() {
        return b_address;
    }

    public void setB_address(String b_address) {
        this.b_address = b_address;
    }

    public String getB_address_2() {
        return b_address_2;
    }

    public void setB_address_2(String b_address_2) {
        this.b_address_2 = b_address_2;
    }

    public String getB_city() {
        return b_city;
    }

    public void setB_city(String b_city) {
        this.b_city = b_city;
    }

    public String getB_county() {
        return b_county;
    }

    public void setB_county(String b_county) {
        this.b_county = b_county;
    }

    public String getB_state() {
        return b_state;
    }

    public void setB_state(String b_state) {
        this.b_state = b_state;
    }

    public String getB_country() {
        return b_country;
    }

    public void setB_country(String b_country) {
        this.b_country = b_country;
    }

    public String getB_zipcode() {
        return b_zipcode;
    }

    public void setB_zipcode(String b_zipcode) {
        this.b_zipcode = b_zipcode;
    }

    public String getB_phone() {
        return b_phone;
    }

    public void setB_phone(String b_phone) {
        this.b_phone = b_phone;
    }

    // Shipping Details
    @SerializedName("s_firstname")
    String s_firstname;

    @SerializedName("s_lastname")
    String s_lastname;

    @SerializedName("s_address")
    String s_address="";

    @SerializedName("s_address_2")
    String s_address_2="";

    @SerializedName("s_city")
    String s_city="";

    @SerializedName("s_county")
    String s_county="";

    @SerializedName("s_state")
    String s_state="";

    @SerializedName("s_country")
    String s_country="";

    @SerializedName("s_zipcode")
    String s_zipcode="";

    @SerializedName("s_phone")
    String s_phone="";

    @SerializedName("s_address_type")
    String s_address_type="";

    @SerializedName("profile_name")
    String profile_name;

    public String getS_firstname() {
        return s_firstname;
    }

    public void setS_firstname(String s_firstname) {
        this.s_firstname = s_firstname;
    }

    public String getS_lastname() {
        return s_lastname;
    }

    public void setS_lastname(String s_lastname) {
        this.s_lastname = s_lastname;
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

    public String getS_county() {
        return s_county;
    }

    public void setS_county(String s_county) {
        this.s_county = s_county;
    }

    public String getS_state() {
        return s_state;
    }

    public void setS_state(String s_state) {
        this.s_state = s_state;
    }

    public String getS_country() {
        return s_country;
    }

    public void setS_country(String s_country) {
        this.s_country = s_country;
    }

    public String getS_zipcode() {
        return s_zipcode;
    }

    public void setS_zipcode(String s_zipcode) {
        this.s_zipcode = s_zipcode;
    }

    public String getS_phone() {
        return s_phone;
    }

    public void setS_phone(String s_phone) {
        this.s_phone = s_phone;
    }

    public String getS_address_type() {
        return s_address_type;
    }

    public void setS_address_type(String s_address_type) {
        this.s_address_type = s_address_type;
    }

    public String getProfile_name() {
        return profile_name;
    }

    public void setProfile_name(String profile_name) {
        this.profile_name = profile_name;
    }
}


