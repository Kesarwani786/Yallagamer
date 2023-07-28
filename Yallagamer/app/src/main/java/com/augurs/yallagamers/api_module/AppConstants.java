package com.augurs.yallagamers.api_module;

public class AppConstants {
    public static final int DEFAULT_CONNECT_TIMEOUT_IN_MIN = 1;
    public static final int DEFAULT_CONNECT_TIMEOUT_IN_SEC = 30;
    public static final int DEFAULT_WRITE_TIMEOUT_IN_MIN = 1;
    public static final int DEFAULT_WRITE_TIMEOUT_IN_SEC = 30;
    public static final int DEFAULT_READ_TIMEOUT_IN_MIN = 1;
    public static final int DEFAULT_READ_TIMEOUT_IN_SEC = 30;
    public static final String success = "Success";
    public static final String try_again = "Something went wrong.. Try again after sometime";
    public static final String server_error = "Unable to reach server, please check internet connectivity";
    public static final String payment_gateway_config_error = "Payment Gateway Not Configured..";
    //    public static final String image_base_url=  ApiClient.DOMAIN_URL+"content/fsc/image/";
    public static final String SYSTEM_LOOKUP_url = "trusted/lookup/SYSTEMTYPELOOKUP";
    public static int DEFAULT_LIST_COUNT = 20;
    public static String CODE_PARAM = "codeParam";
    public static String SEARCH_PARAM = "searchParam";
    public static int dashboard_state = 0;
    public static int forgot_password_state = 0;
    public static String content_type = "application/json";
    public static String no_cache = "no-cache";


    public static String map_ApiKey = "AIzaSyAHTwxjf7HHtgKifjq7-ZOVL5KqF0qmNc8";
    public static String contact_Address_UUID = "fc367643-f836-404b-89cc-fc9da133d865";



    //SMTP Config
    static String Smtp_Username = "AKIAIQZYOVMKKQ4RGGEQ";
    static String Smtp_Password = "At3NGjcFoJ2WO42yoC+nyLjsy8gQNGmlCZPGHKNXdyIH";
    static String Smtp_Host = "email-smtp.eu-west-1.amazonaws.com";
    static int Smtp_Port = 465;


}
