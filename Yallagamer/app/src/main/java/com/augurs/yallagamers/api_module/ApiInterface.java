package com.augurs.yallagamers.api_module;

import com.augurs.yallagamers.data_models.Aboutus;
import com.augurs.yallagamers.data_models.AddItemInBagResponceModel;
import com.augurs.yallagamers.data_models.AddProductInBagGuestUserModel;
import com.augurs.yallagamers.data_models.AddRemoveWishlistModel;
import com.augurs.yallagamers.data_models.AddressLIstingModel;
import com.augurs.yallagamers.data_models.BagItemBaseModel;
import com.augurs.yallagamers.data_models.CategoriesList;
import com.augurs.yallagamers.data_models.CategoryData;
import com.augurs.yallagamers.data_models.CategoryProductBaseModel;
import com.augurs.yallagamers.data_models.CheckUserExistEmail;
import com.augurs.yallagamers.data_models.CountryListResponse;
import com.augurs.yallagamers.data_models.CreateOrderAsGuest;
import com.augurs.yallagamers.data_models.DiscussionList;
import com.augurs.yallagamers.data_models.Faq;
import com.augurs.yallagamers.data_models.GuestUserLogin;
import com.augurs.yallagamers.data_models.HomePageData;
import com.augurs.yallagamers.data_models.OrderCompletionAndFailureModel;
import com.augurs.yallagamers.data_models.OrderDetailsModel;
import com.augurs.yallagamers.data_models.OrderListingFilterModel;
import com.augurs.yallagamers.data_models.OrderListingModel;
import com.augurs.yallagamers.data_models.OrderStatusResponse;
import com.augurs.yallagamers.data_models.PaymentScreenModel;
import com.augurs.yallagamers.data_models.PrivacyPolicy;
import com.augurs.yallagamers.data_models.ProductDetailBaseModel;
import com.augurs.yallagamers.data_models.ProductsList;
import com.augurs.yallagamers.data_models.ProfileDataResponce;
import com.augurs.yallagamers.data_models.ProfilePageResponce;
import com.augurs.yallagamers.data_models.PushCartToLoggedInUserModel;
import com.augurs.yallagamers.data_models.SavedCardsDataResponce;
import com.augurs.yallagamers.data_models.ShippingScreenModel;
import com.augurs.yallagamers.data_models.StaticCategoryBaseResponce;
import com.augurs.yallagamers.data_models.Termofuse;
import com.augurs.yallagamers.data_models.UserData;
import com.augurs.yallagamers.data_models.WishList;
import com.augurs.yallagamers.UserInterface.address.AddAddressModel;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("api.php")
    Single<UserData> register(@Header ("Authorization") String header, @Field("_d") String _d, @Field("firstname") String first_name, @Field("lastname") String last_name, @Field("password") String password,@Field("confirm_password") String confirm_password,@Field("email") String email,@Field("phone") String phone);

    @GET("api.php")
    Single<UserData> login(@Header ("Authorization") String header, @Query("_d") String _d, @Query("method") String method, @Query("email") String email, @Query("password") String password);

    @GET("api.php")
    Single<UserData> otpVerificaiton(@Header ("Authorization") String header, @Query("_d") String _d, @Query("method") String method, @Query("token") String token, @Query("otp") String otp);

    @GET("api.php")
    Single<UserData> resendOtp(@Header ("Authorization") String header, @Query("_d") String _d, @Query("method") String method, @Query("token") String token);

    @GET("api.php")
    Observable<ProductsList> getProducts(@Header ("Authorization") String header, @Query("_d") String _d1, @Query("category_id") String category_id, @Query("_d") String _d2, @Query("page") String page, @Query("items_per_page") String items_per_page);

    @GET("api.php")
    Observable<CategoriesList> getCategories(@Header ("Authorization") String header, @Query("_d") String _d, @Query("page") String page, @Query("items_per_page") String items_per_page);

    @GET("api.php")
    Observable< CategoryProductBaseModel > getCategoryProducts( @Header ("Authorization") String header,@Header ("token") String token, @Query("cart_id") String cart_id, @Query("_d") String _d1, @Query("method") String method, @Query("id") String items_per_page, @Query("items_per_page") Integer r);

    @GET("api.php")
    Observable< CategoryProductBaseModel > GetSearchProduct( @Header ("Authorization") String header, @Query("_d") String _d1, @Query("method") String method, @Query("q") String Text);

    @GET("api.php")
    Observable<JsonObject> getProductDetails(@Header ("Authorization") String header, @Query("_d") String _d1);

    @GET("api.php")
    Observable<DiscussionList> getProductDiscussion(@Header ("Authorization") String header, @Query("_d") String _d1);

    @GET("api.php")
    Observable<ProductsList> getCollectorEditions(@Header ("Authorization") String header, @Query("_d") String _d1, @Query("category_id") String category_id, @Query("_d") String _d2, @Query("page") String page, @Query("items_per_page") String items_per_page);

    //Home Page Api
    //...........Starts...............
    @GET("api.php")
    Observable<HomePageData> getHomePage(@Header ("Authorization") String header, @Query("_d") String _d1, @Query("method") String method);

    @GET("api.php")
    Observable<HomePageData> getHomePage(@Header ("Authorization") String header, @Query("_d") String _d1, @Query("method") String method, @Header ("token") String token, @Query ("cart_id") String cart_id);

    //...........Ends...............

    @GET("api.php")
    Observable< CategoryData >getCategoryPage( @Header ("Authorization") String header, @Query("_d") String _d1, @Query("method") String method);

    @POST("api.php")
    Single<AddRemoveWishlistModel> addProductItemToWishList(@Header ("Authorization") String header, @Query("_d") String _d, @Header("token") String token, @Body PushCartToLoggedInUserModel pushCartToLoggedInUserModel);

    @FormUrlEncoded
    @POST("api.php")
    Single<BaseResponse> addProductItemToBag(@Header ("Authorization") String header, @Field("_d") String _d, @Field("products_id") Integer products_id, @Field("token") String token,@Field("quantity") Integer quantity);

    @POST("api.php")
    Single< AddItemInBagResponceModel > AddInBag( @Header ("Authorization") String header, @Query("_d") String _d, @Body AddProductInBagGuestUserModel addProductInBagGuestUserModel, @Header ("token") String token);

    @POST("api.php")
    Single< AddItemInBagResponceModel > AddInBag( @Header ("Authorization") String header, @Query("_d") String _d, @Body AddProductInBagGuestUserModel addProductInBagGuestUserModel);

    @GET("api.php")
    Single<Faq> getFAQdata(@Header ("Authorization") String header, @Query("_d") String _d,@Query("method") String method);
    
    @GET("api.php")
    Single< BagItemBaseModel > getBagItemListUserAuthorised( @Header ("Authorization") String header, @Query("_d") String _d, @Query("method") String method, @Header ("token") String token);

    @GET("api.php")
    Single< BagItemBaseModel > getBagItemListNonUser( @Header ("Authorization") String header, @Query("_d") String _d, @Query("method") String method, @Query("cart_id") String cart_id);

    @GET("api.php")
    Single<Aboutus> getAboutus(@Header ("Authorization") String header, @Query("_d") String _d, @Query("method") String method);

    @GET("api.php")
    Single<Termofuse> getTermOfUse(@Header ("Authorization") String header, @Query("_d") String _d, @Query("method") String method);

    @GET("api.php")
    Single<PrivacyPolicy> getPrivacyPolicy(@Header ("Authorization") String header, @Query("_d") String _d,@Query("method") String method);

    @GET("api.php")
    Single<WishList> getWishlist(@Header ("Authorization") String header, @Query("_d") String _d1, @Query("method") String method, @Header("token") String token);

    @GET("api.php")
    Single< AddressLIstingModel > getUserAddressListing( @Header ("Authorization") String header, @Query("_d") String _d1, @Query("method") String method, @Query("token") String token);

    @GET("api.php")
    Single< JsonObject> UpdateDefaultAddress( @Header ("Authorization")  String header,@Query("_d") String _d1 ,@Query("method") String method, @Query("token") String token,@Query("profile_id") int address_id);

    @GET("api.php")
    Single< JsonObject> RemoveAddress( @Header ("Authorization")  String header,@Query("_d") String _d1 ,@Query("method") String method, @Query("token") String token,@Query("profile_id") int address_id);

    @POST("api.php")
    Single< BaseResponse> SaveUserAddress ( @Header ("Authorization")  String header, @Query("_d") String _d1 , @Header ("Accept") String Accept,  @Header("token") String token, @Body AddAddressModel addNewAddress  );

    @GET("api.php")
    Observable< ProfilePageResponce > GetUserProfile ( @Header ("Authorization")  String header , @Query("_d") String d1 , @Query("method") String method , @Query ("token") String token );

    @GET("api.php")
    Observable<ProfileDataResponce> GetProfileData (@Header ("Authorization")  String header , @Query("_d") String d1 , @Query("method") String method , @Header ("token") String token,@Query ("profile_id") String profile_id );

    @GET("api.php")
    Observable<SavedCardsDataResponce> GetSavedCardsData (@Header ("Authorization")  String header , @Query("_d") String d1 , @Query("method") String method , @Header ("token") String token );


    @POST("api.php")
    Single< BaseResponse> SaveProfileData( @Header ("Authorization")  String header, @Query("_d") String _d1 ,  @Header("token") String token, @Body RequestBody body  );

    @POST("api.php")
    Single< BaseResponse> RemoveCardData( @Header ("Authorization")  String header, @Query("_d") String _d1 ,  @Header("token") String token, @Body RequestBody body  );


    @POST("api.php")
    Single< BaseResponse> SaveCardsData( @Header ("Authorization")  String header, @Query("_d") String _d1 ,  @Header("token") String token, @Body RequestBody body  );

    @POST("api.php")
    Single< BaseResponse> UpdatePassword( @Header ("Authorization")  String header, @Query("_d") String _d1 ,  @Header("token") String token, @Body RequestBody body  );


    @POST("api.php")
    Single< ProductDetailBaseModel> RemoveoneByOneProduct ( @Header ("Authorization")String header, @Query("_d") String d1 ,@Body PushCartToLoggedInUserModel product_id ,@Query("cart_id") String cart_id );

    @POST("api.php")
    Single< ProductDetailBaseModel> RemoveoneByOneProduct ( @Header ("Authorization")String header, @Query("_d") String d1 ,@Header ("token") String token ,@Body PushCartToLoggedInUserModel product_id );

    @GET("api.php")
    Single< ProductDetailBaseModel > GetProductDetailsById ( @Header ("Authorization") String header , @Query("_d") String _d, @Query ( "method" ) String method , @Query ( "id" ) Integer id , @Query ( "latitude" ) String latitude, @Query ( "longitude" ) String longitude ,@Query ("cart_id") String  cart_id);

    @GET("api.php")
    Single< ProductDetailBaseModel > GetProductDetailsById ( @Header ("Authorization") String header , @Query("_d") String _d, @Query ( "method" ) String method , @Query ( "id" ) Integer id , @Header ("token") String token);

    @FormUrlEncoded
    @POST("api.php")
    Single< BaseResponse> AddReview ( @Header ("Authorization")  String header ,
                                    @Field ( "method" ) String method ,
                                    @Field ( "_d" ) String _d,
                                    @Field ( "product_id" ) String product_id,
                                    @Field ( "email" ) String email,
                                    @Field ( "name" ) String name,
                                    @Field ( "title" ) String title,
                                    @Field ( "message" ) String message,
                                    @Field ( "rating_value" ) String rating_value);

    @GET("api.php")
    Single< StaticCategoryBaseResponce > GetStaticCategory ( @Header ("Authorization") String header , @Query("_d") String _d , @Query ( "method" ) String getDetailsBypage , @Query ( "name" ) String nintendo );

    @POST("api.php")
    Single< UserData> PushCartData (@Header("Authorization") String header ,@Header("token") String token ,@Query("_d") String _d, @Body PushCartToLoggedInUserModel pushCartToLoggedInUserModel );

    @GET("api.php")
    Observable< ShippingScreenModel >GetShippingResponse( @Header("Authorization") String header , @Header("token") String token , @Query("_d") String _d, @Query ("method") String method,@Query ("cart_id") String cart_id );

    @POST("api.php")
    Observable< ShippingScreenModel> UpdateShippingMethod (@Header("Authorization") String header ,@Query("_d") String _d , @Header("token") String token ,@Body PushCartToLoggedInUserModel product_id  );

    @GET("api.php")
    Observable< PaymentScreenModel >     GetPaymentScreen ( @Header("Authorization") String header , @Query("_d") String _d , @Header("token") String token , @Query ("method") String method ,@Query ("cart_id") String cart_id );

    @POST("api.php")
    Observable<OrderListingModel> GetOrderListing( @Header("Authorization") String header, @Query("_d") String _d, @Header("token") String token, @Body OrderListingFilterModel orderListingFilterModel );

    @GET("api.php")
    Observable<OrderCompletionAndFailureModel>  CreateCart( @Header("Authorization")String header, @Query("_d")String _d, @Query("method") String method,  @Header("token") String token,@Query("payment_id")  Integer paymentScreen);

    @GET("api.php")
    Observable<OrderStatusResponse> GetOrderStatus(@Header("Authorization")String header, @Query("_d")String _d, @Query("method") String method, @Header("token")  String token);

    @GET("api.php")
    Observable<OrderDetailsModel> GetOrderDetails ( @Header("Authorization")String header, @Query("_d")String _d, @Query("method") String method, @Header("token")  String token, @Query("order_id")  String orderId);

    @POST("api.php")
    Observable<BagItemBaseModel> UpdateCart ( @Header("Authorization") String header, @Query("_d")String _d,  @Header("token") String token,@Body PushCartToLoggedInUserModel product_id );

    @POST("api.php")
    Observable<BagItemBaseModel> UpdateCartForGuest ( @Header("Authorization") String header, @Query("_d")String _d,@Body PushCartToLoggedInUserModel product_id,  @Query ("cart_id") String cart_id );

    @POST("api.php")
    Observable<OrderCompletionAndFailureModel> UpdatePaymentScreenStatus ( @Header("Authorization") String header, @Query("_d")String _d,  @Header("token") String token,@Body PushCartToLoggedInUserModel pushCartToLoggedInUserModel , @Query ("cart_id") String cart_id );

    @POST("api.php")
    Observable<OrderCompletionAndFailureModel> UpdatePaymentScreenStatus ( @Header("Authorization") String header, @Query("_d")String _d, @Body PushCartToLoggedInUserModel pushCartToLoggedInUserModel );

    @GET("api.php")
    Observable<PaymentScreenModel> ApplyPromocode (@Header("Authorization") String header,  @Query("_d")String _d, @Query("method") String method, @Header("token") String token,@Query("coupon_code")  String coupon_code, @Query ("cart_id") String cart_id );

    @GET("api.php")
    Observable<PaymentScreenModel> RemoveCoupon (@Header("Authorization") String header,  @Query("_d")String _d, @Query("method") String method, @Header("token") String token,@Query("coupon_code")  String coupon_code ,@Query ("cart_id") String cart_id );

    @POST("api.php")
    Observable<OrderCompletionAndFailureModel> CreateOrderForGuestUser (@Header("Authorization") String header,@Query("_d") String cart,@Body  CreateOrderAsGuest createOrderAsGuest);

    @POST("api.php")
    Observable<BaseResponse> PutLoginUserDetails (@Header("Authorization")  String header,@Query("_d") String cart,@Body GuestUserLogin guestUserLogin );

    @GET("api.php")
    Single<CountryListResponse> GetCountryList (@Header("Authorization") String header,@Query("_d")  String d,@Header ("Accept") String s,@Query("method")  String method );

    @GET("api.php")
    Single<CheckUserExistEmail> CheckUserExistEmailRequest ( @Header("Authorization") String header,@Query("_d")  String d,@Header ("Content-Type") String s,@Query("method")  String method ,@Query("email") String email);
}
