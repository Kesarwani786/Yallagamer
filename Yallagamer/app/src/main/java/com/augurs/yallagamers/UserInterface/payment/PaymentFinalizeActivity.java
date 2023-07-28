package com.augurs.yallagamers.UserInterface.payment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.augurs.yallagamers.R;
import com.augurs.yallagamers.UserInterface.CategoryListItem.CategoryListItemActivity;
import com.augurs.yallagamers.UserInterface.MainActivity;
import com.augurs.yallagamers.UserInterface.Order.OrderDetailsActivity;
import com.augurs.yallagamers.UserInterface.SplashActivity;
import com.augurs.yallagamers.UserInterface.address.AddAddressModel;
import com.augurs.yallagamers.UserInterface.payment.PaymentGatewayRequest.CreateOrderResponseDto;
import com.augurs.yallagamers.UserInterface.user_login.LoginActivity;
import com.augurs.yallagamers.api_module.ApiClient;
import com.augurs.yallagamers.api_module.ApiInterface;
import com.augurs.yallagamers.api_module.BaseResponse;
import com.augurs.yallagamers.api_module.DialogUtil;
import com.augurs.yallagamers.UserInterface.payment.PaymentGatewayRequest.PaymentGatewayAccessTokenResponse;
import com.augurs.yallagamers.UserInterface.payment.PaymentGatewayRequest.PaymentGatewayClient;
import com.augurs.yallagamers.UserInterface.payment.PaymentGatewayRequest.PaymentGatewayInterface;
import com.augurs.yallagamers.api_module.PaymentGatewayBodyRequest;
import com.augurs.yallagamers.data_models.CreateOrderAsGuest;
import com.augurs.yallagamers.data_models.GuestUserLogin;
import com.augurs.yallagamers.data_models.OrderCompletionAndFailureModel;
import com.augurs.yallagamers.data_models.PaymentScreenModel;
import com.augurs.yallagamers.data_models.ProcessorParam;
import com.augurs.yallagamers.data_models.PushCartToLoggedInUserModel;
import com.augurs.yallagamers.data_models.UserData;
import com.augurs.yallagamers.utills.ConstantVariable;
import com.augurs.yallagamers.utills.LoginPreferences;
import com.augurs.yallagamers.utills.UtilityMethods;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.text.DecimalFormat;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;
import payment.sdk.android.PaymentClient;
import payment.sdk.android.cardpayment.CardPaymentData;
import payment.sdk.android.cardpayment.CardPaymentRequest;
import retrofit2.Call;
import retrofit2.Callback;

public class PaymentFinalizeActivity extends AppCompatActivity {
    ProgressDialog mProgressDialog;
    RelativeLayout RlPaymentSuccessfully,RlFailure;
    TextView TvRetryMain,TvOrderId,TvTextStatus,TvTextDescription,TvTextTip,TvTextAMountText,TvUserName,TvAddress,TvfailureCaseDesc,TvAmountMessage;
    String OrderId="";
    String PaymentStatus="";
    private TextView TvGoHome;
    private String AccessTokenString="";
    private ProcessorParam processorParam;
    private PaymentGatewayBodyRequest paymentGatewayBodyRequest;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_payment_finalize);
        RelativeLayout RlViewOrder= findViewById (R.id.RlViewOrder);
        RlViewOrder.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick ( View v ) {
                Intent i = new Intent(PaymentFinalizeActivity.this, OrderDetailsActivity.class);
                i.putExtra("OrderId",OrderId);
                startActivity(i);

            }
        });
        ImageView img_back = findViewById (R.id.img_back);
        img_back.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick ( View v ) {
                onBackPressed ();
            }
        });
        RelativeLayout RlHome= findViewById (R.id.RlHome);
        RlHome.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick ( View v ) {
                Intent i = new Intent ( PaymentFinalizeActivity.this , MainActivity.class );
                i.putExtra("ScreenStatus",1);
                startActivity ( i );
                overridePendingTransition ( R.anim.slide_out_right,R.anim.slide_in_right );
                finish ( );
            }
        });
        RelativeLayout RlLogin = findViewById (R.id.RlLogin);
        RlLogin.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick ( View v ) {
                PutLoginCredentials();
            }
        });

        RelativeLayout RlHome1= findViewById (R.id.RlHome1);
        RlHome1.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick ( View v ) {
                Intent i = new Intent ( PaymentFinalizeActivity.this , MainActivity.class );
                i.putExtra("ScreenStatus",1);
                startActivity ( i );
                overridePendingTransition ( R.anim.slide_out_right,R.anim.slide_in_right );
                finish ( );
            }
        });

        TextView toolbar_title = findViewById (R.id.toolbar_title);
        TvOrderId= findViewById (R.id.TvOrderId);
        TvTextStatus= findViewById (R.id.TvTextStatus);
        TvTextDescription= findViewById (R.id.TvTextDescription);
        TvfailureCaseDesc= findViewById (R.id.TvfailureCaseDesc);
        TvTextTip= findViewById (R.id.TvTextTip);
        TvTextAMountText= findViewById (R.id.TvTextAMountText);
        TvUserName= findViewById (R.id.TvUserName);
        TvAddress= findViewById (R.id.TvAddress);
        TvRetryMain= findViewById (R.id.TvRetryMain);
        TvAmountMessage= findViewById (R.id.TvAmountMessage);
        toolbar_title.setText ("Payment Status ");
        RlPaymentSuccessfully = findViewById (R.id.RlPaymentSuccessfully);
        RlFailure= findViewById (R.id.RlFailure);
        if(new LoginPreferences (PaymentFinalizeActivity.this).getString ("token")!=null)
            CreateBag();
        else
            CreateOrderForGuestUser();
        TvRetryMain.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick ( View v ) {
                OpenDialogue();
            }
        });
    }

    private void PutLoginCredentials ( ) {
        EditText et_password = findViewById (R.id.et_password);
        EditText et_confirm_password = findViewById (R.id.et_confirm_password);
        if(et_password.getText ().toString ().length ()==0)
            UtilityMethods.PrintToast (PaymentFinalizeActivity.this,"Please enter Password",1);
        if(et_confirm_password.getText ().toString ().length ()==0)
            UtilityMethods.PrintToast (PaymentFinalizeActivity.this,"Please enter Comfirm Password",1);
        else  if(!et_password.getText ().toString ().equals(et_confirm_password.getText ().toString ()))
            UtilityMethods.PrintToast (this, "Passwords Did Not Match", 1);
        else    {
            GuestUserLogin guestUserLogin = new GuestUserLogin();
            guestUserLogin.setOrderId (OrderId);
            guestUserLogin.setMethod ("create_profile_guest");
            guestUserLogin.setUserData (new GuestUserLogin.UserData (et_password.getText ().toString (),et_password.getText ().toString ()));
            mProgressDialog = DialogUtil.getProgressDialog (PaymentFinalizeActivity.this, getResources ().getString (R.string.app_DialogInfo), getResources ().getString (R.string.app_DialogLoading));
            String header = Credentials.basic (getResources ().getString (R.string.user_id), getResources ().getString (R.string.user_password));
            ApiInterface apiService = ApiClient.getClient (PaymentFinalizeActivity.this).create (ApiInterface.class);
            Observable<BaseResponse> hpObservable = apiService.PutLoginUserDetails (header, "Cart",guestUserLogin );
            hpObservable.observeOn (AndroidSchedulers.mainThread ()).subscribeOn (Schedulers.io ()).subscribe (this :: PutLoginDetails, this :: handleErrorHP);
        }
    }

    private void PutLoginDetails ( BaseResponse baseResponse ) {
        new LoginPreferences (PaymentFinalizeActivity.this).put ("cart_id",null);
        mProgressDialog.dismiss ();
        if(baseResponse.getStatus ()==200){
            UtilityMethods.PrintToast (PaymentFinalizeActivity.this,"Please Login for Account Updation",0);
            Intent intent = new Intent (PaymentFinalizeActivity.this, LoginActivity.class);
            startActivity (intent);
            finish ();
        }
        else
            UtilityMethods.PrintToast (PaymentFinalizeActivity.this,baseResponse.getMessage (),1);

    }

    private void CreateOrderForGuestUser ( ) {
        if (UtilityMethods.isNetworkConnected (PaymentFinalizeActivity.this)) {
            CreateOrderAsGuest createOrderAsGuest= new CreateOrderAsGuest ();
            createOrderAsGuest.setMethod ("createOrderGuest");
            createOrderAsGuest.setPaymentId (getIntent ().getIntExtra ("PaymentId",0));
            createOrderAsGuest.setShipToAnother ("1");
            createOrderAsGuest.setCart_id (new LoginPreferences (PaymentFinalizeActivity.this).getString ("cart_id"));
            AddAddressModel addAddressModel= new Gson().fromJson ( new JsonParser ().parse(getIntent ().getStringExtra (ConstantVariable.AddressParameter)) , AddAddressModel.class );
            createOrderAsGuest.setCustomerNotes (addAddressModel.getCustomerNotes ());
            CreateOrderAsGuest.UserData userData = new CreateOrderAsGuest.UserData ();
            userData.setsAddress (addAddressModel.getsAddress ());
            userData.setsAddress2 (addAddressModel.getsAddress2 ());
            userData.setsCity (addAddressModel.getsCity ());
            userData.setsState (addAddressModel.getsState ());
            userData.setsCountry (addAddressModel.getsCountry ());
            userData.setsZipcode (addAddressModel.getsZipcode ());
            userData.setsPhone (addAddressModel.getsPhone ());
            userData.setsFirstname (addAddressModel.getsFirstname ());
            userData.setsLastname (addAddressModel.getsLastname ());

            userData.setbFirstname (addAddressModel.getbFirstname ());
            userData.setbLastname (addAddressModel.getbLastname ());
            userData.setbPhone (addAddressModel.getbPhone ());
            userData.setbAddress (addAddressModel.getbAddress ());
            userData.setbCity (addAddressModel.getbCity ());
            userData.setbState (addAddressModel.getbState ());
            userData.setbZipcode (addAddressModel.getbZipcode ());
            userData.setFirstname (addAddressModel.getsFirstname ());
            userData.setLastname (addAddressModel.getsLastname ());
            userData.setEmail (addAddressModel.getsEmail ());
            userData.setPhone (addAddressModel.getsPhone ());
            userData.setShipToAnother ("1");
            createOrderAsGuest.setUserData (userData);
            mProgressDialog = DialogUtil.getProgressDialog (PaymentFinalizeActivity.this, getResources ().getString (R.string.app_DialogInfo), getResources ().getString (R.string.app_DialogLoading));
            String header = Credentials.basic (getResources ().getString (R.string.user_id), getResources ().getString (R.string.user_password));
            ApiInterface apiService = ApiClient.getClient (PaymentFinalizeActivity.this).create (ApiInterface.class);
            Observable<OrderCompletionAndFailureModel> hpObservable = apiService.CreateOrderForGuestUser (header, "Cart",createOrderAsGuest );
            hpObservable.observeOn (AndroidSchedulers.mainThread ()).subscribeOn (Schedulers.io ()).subscribe (this :: GetCreateCartResponce, this :: handleErrorHP);
        } else {
            GetDialogue (1);
        }
    }

    private void UpdateUI ( OrderCompletionAndFailureModel.Data data ) {
       if(PaymentStatus.equals ("S")) {
           //TvTextDescription.setText ("Your Payment has been done ");
           TvAmountMessage.setText ("Your Payment has been done ");
           TvTextStatus.setText ("Order Placed Status  "+"Success");
       }
       else if(PaymentStatus.equals ("C")) {
           TvfailureCaseDesc.setText ("Payment of AED "+data.getTotal ()+" is failed .Your Order is not successfully Placed.");
       }
        TvOrderId.setText ("Order Id "+data.getOrderId ());
        TvTextTip.setText (data.getTip ());
        TvTextAMountText.setText ("AED "+data.getTotal ()+"");
        TvUserName.setText (data.getUserData ().getFirstname ()+" "+data.getUserData ().getLastname ());
        TvAddress.setText (data.getUserData ().getS_address ()+" "+data.getUserData ().getS_address_2 ()+" "+data.getUserData ().getS_city ()+" "+data.getUserData ().getS_state ()+" "+data.getUserData ().getS_country ());
        LinearLayout Linear1 = findViewById (R.id.Linear1);
        if (new LoginPreferences (PaymentFinalizeActivity.this).getString ("cart_id") != null&&new LoginPreferences (PaymentFinalizeActivity.this).getString ("token")==null)
            Linear1.setVisibility (View.VISIBLE);
    }

    private void OpenDialogue () {
        Dialog dialog = new Dialog (PaymentFinalizeActivity.this);
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        Window window = dialog.getWindow ( );
        WindowManager.LayoutParams wlp = window.getAttributes ( );
        window.setLayout ( ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT );
        dialog.getWindow ( ).addFlags ( WindowManager.LayoutParams.FLAG_DIM_BEHIND );
        dialog.getWindow ( ).setBackgroundDrawable ( new ColorDrawable ( android.graphics.Color.TRANSPARENT ) );
        window.setAttributes ( wlp );
        TextView TvTeas = dialog.findViewById ( R.id.TvTeas );
        TvTeas.setText("Payment Gateway Mode ");
        TextView TvTesasd = dialog.findViewById ( R.id.TvTesasd );
        TvTesasd.setText("Run Payment Gateway again");
        TextView TvRetry =  dialog.findViewById(R.id.TvRetry);
        TvRetry.setText ( "Yes" );
        TvRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  processorParam = new Gson ( ).fromJson ( new JsonParser ( ).parse ( getIntent ().getStringExtra (ConstantVariable.PaymentScreenPaymentGatewayParamter) ) ,ProcessorParam.class );
                GetAccessToken();
                //UpdateCartItem("P");
                dialog.dismiss();
            }
        });
        TextView TvRetryNot =  dialog.findViewById(R.id.TvRetryNot);
        TvRetryNot.setText ( "No" );
        TvRetryNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateCartItem("F");
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data==null){
            UpdateCartItem("C");
            //Toast.makeText(activity, "transaction cancelled", Toast.LENGTH_SHORT).show();
        }
        else{
            if(requestCode==0){

                CardPaymentData cardPaymentData= CardPaymentData.getFromIntent(data);
                if(cardPaymentData.getCode()==2){
                    UpdateCartItem("S");
                }else
                {
                    UpdateCartItem("F");
                    Toast.makeText(PaymentFinalizeActivity.this,"Transaction failed",Toast.LENGTH_SHORT).show();
                }

            }

        }
    }
    private void UpdateCartItem ( String p ) {
        if (UtilityMethods.isNetworkConnected (PaymentFinalizeActivity.this)) {
            PaymentStatus=p;
            mProgressDialog = DialogUtil.getProgressDialog (PaymentFinalizeActivity.this, getResources ().getString (R.string.app_DialogInfo), getResources ().getString (R.string.app_DialogLoading));
            String header = Credentials.basic (getResources ().getString (R.string.user_id), getResources ().getString (R.string.user_password));
            ApiInterface apiService = ApiClient.getClient (PaymentFinalizeActivity.this).create (ApiInterface.class);
            PushCartToLoggedInUserModel pushCartToLoggedInUserModel = new PushCartToLoggedInUserModel ();
            pushCartToLoggedInUserModel.setMethod ("updateOrderStatus");
            pushCartToLoggedInUserModel.setReason_text ("network paymenent");
            pushCartToLoggedInUserModel.setStatus (p);
            pushCartToLoggedInUserModel.setOrder_id (OrderId);
            pushCartToLoggedInUserModel.setTransaction_id ("3432535");
            if(new LoginPreferences (PaymentFinalizeActivity.this).getString ("cart_id")!=null)
                pushCartToLoggedInUserModel.setCartId (new LoginPreferences (PaymentFinalizeActivity.this).getString ("cart_id"));
            Observable<OrderCompletionAndFailureModel> hpObservable;
            if(new LoginPreferences (PaymentFinalizeActivity.this).getString ("token")!=null)
             hpObservable = apiService.UpdatePaymentScreenStatus (header, "Cart", new LoginPreferences (PaymentFinalizeActivity.this).getString ("token"), pushCartToLoggedInUserModel,null);
            else
                hpObservable = apiService.UpdatePaymentScreenStatus (header, "Cart", pushCartToLoggedInUserModel);
            hpObservable.observeOn (AndroidSchedulers.mainThread ()).subscribeOn (Schedulers.io ()).subscribe (this :: GetUpdateCartResponce, this :: handleErrorHP);
        } else {
            GetDialogue (1);
        }
    }

    private void GetUpdateCartResponce ( OrderCompletionAndFailureModel paymentScreenModel ) {
        mProgressDialog.dismiss ();
        if (paymentScreenModel.getStatus () == 200) {
            OrderId= paymentScreenModel.getData ().getOrderId ()+"";
            if(PaymentStatus.equals ("S")) {
                TvGoHome.setVisibility (View.GONE);
                RlFailure.setVisibility (View.GONE);
                RlPaymentSuccessfully.setVisibility (View.VISIBLE);
                UpdateUI (paymentScreenModel.getData ());


            }
            else {
                UpdateUI (paymentScreenModel.getData ());
                TvGoHome.setVisibility (View.VISIBLE);
                RlFailure.setVisibility (View.VISIBLE);
            }
        } else {
            UtilityMethods.PrintToast (PaymentFinalizeActivity.this, paymentScreenModel.getMessage (), 1);
            onBackPressed ();
        }
    }

    private void CreateBag () {
        if (UtilityMethods.isNetworkConnected (PaymentFinalizeActivity.this)) {
            mProgressDialog = DialogUtil.getProgressDialog (PaymentFinalizeActivity.this, getResources ().getString (R.string.app_DialogInfo), getResources ().getString (R.string.app_DialogLoading));
            String header = Credentials.basic (getResources ().getString (R.string.user_id), getResources ().getString (R.string.user_password));
            ApiInterface apiService = ApiClient.getClient (PaymentFinalizeActivity.this).create (ApiInterface.class);
            Observable<OrderCompletionAndFailureModel> hpObservable = apiService.CreateCart (header, "Cart", "createOrder",new LoginPreferences (PaymentFinalizeActivity.this).getString ("token"),getIntent().getIntExtra("PaymentId",0));
            hpObservable.observeOn (AndroidSchedulers.mainThread ()).subscribeOn (Schedulers.io ()).subscribe (this :: GetCreateCartResponce, this :: handleErrorHP);
        } else {
            GetDialogue (1);
        }
    }
    private void handleErrorHP(Throwable throwable) {
        mProgressDialog.dismiss ( );
        Log.e("HP","it does not work."+throwable.getMessage ());
    }
    private void GetDialogue ( int i ) {
        Dialog dialog = new Dialog (PaymentFinalizeActivity.this);
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        Window window = dialog.getWindow ( );
        WindowManager.LayoutParams wlp = window.getAttributes ( );
        window.setLayout ( ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT );
        dialog.getWindow ( ).addFlags ( WindowManager.LayoutParams.FLAG_DIM_BEHIND );
        dialog.getWindow ( ).setBackgroundDrawable ( new ColorDrawable ( android.graphics.Color.TRANSPARENT ) );
        window.setAttributes ( wlp );
        TextView TvRetry =  dialog.findViewById(R.id.TvRetry);
        TvRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i==1&&UtilityMethods.isNetworkConnected(PaymentFinalizeActivity.this))
                    CreateBag();
                else
                    GetDialogue(i);
                dialog.dismiss();
            }
        });
        TextView TvRetryNot =  dialog.findViewById(R.id.TvRetryNot);
        TvRetryNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void GetCreateCartResponce ( OrderCompletionAndFailureModel baseResponse ) {
        mProgressDialog.dismiss ();
        TvGoHome= findViewById (R.id.TvGoHome);
        TvGoHome.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick ( View v ) {
                Intent i = new Intent ( PaymentFinalizeActivity.this , MainActivity.class );
                i.putExtra("ScreenStatus",1);
                startActivity ( i );
                overridePendingTransition ( R.anim.slide_out_right,R.anim.slide_in_right );
                finish ( );
            }
        });
        if (baseResponse.getStatus () == 200) {
            OrderId= baseResponse.getData ().getOrderId ()+"";
            if(getIntent().getIntExtra("PaymentId",0)==6) {
                RlPaymentSuccessfully.setVisibility (View.VISIBLE);
                TvGoHome.setVisibility (View.VISIBLE);
                UpdateUI(baseResponse.getData ());


            }
            else {
                paymentGatewayBodyRequest= new PaymentGatewayBodyRequest ();
                paymentGatewayBodyRequest.setAction ("SALE");
                PaymentGatewayBodyRequest.Amount amount = new PaymentGatewayBodyRequest.Amount();
                amount.setValue (190+"");
                amount.setCurrencyCode ("AED");
                paymentGatewayBodyRequest.setAmount (amount);

                PaymentGatewayBodyRequest.MerchantAttributes merchantAttributes = new PaymentGatewayBodyRequest.MerchantAttributes ();
                merchantAttributes.setRedirectUrl ("https://google.com");
                merchantAttributes.setSkipConfirmationPage (true);
                paymentGatewayBodyRequest.setMerchantAttributes (merchantAttributes);
                paymentGatewayBodyRequest.setEmailAddress (baseResponse.getData ().getUserData ().getEmail ());

                PaymentGatewayBodyRequest.BillingAddress billingAddress = new PaymentGatewayBodyRequest.BillingAddress ();
                billingAddress.setFirstName (baseResponse.getData ().getUserData ().getFirstname ());
                billingAddress.setLastName (baseResponse.getData ().getUserData ().getLastname ());
                billingAddress.setAddress1 (baseResponse.getData ().getUserData ().getS_address ());
                billingAddress.setCity (baseResponse.getData ().getUserData ().getS_city ());
                billingAddress.setCountryCode ("AE");
                paymentGatewayBodyRequest.setBillingAddress (billingAddress);
                //  UtilityMethods.PrintToast (PaymentFinalizeActivity.this,"We have get the Self api Create Order Api and Now proceed to the payment gateway access token ",1);
                processorParam = new Gson ( ).fromJson ( new JsonParser ( ).parse ( getIntent ().getStringExtra (ConstantVariable.PaymentScreenPaymentGatewayParamter) ) ,ProcessorParam.class );
                GetAccessToken();
            }
        } else {
            UtilityMethods.PrintToast (PaymentFinalizeActivity.this, baseResponse.getData ().getMessage (), 1);
            Intent i = new Intent (PaymentFinalizeActivity.this, MainActivity.class);
            i.putExtra ("ScreenStatus",5);
            startActivity (i);
            finish ();
        }
    }

    private void GetAccessToken ( ) {
        if ( UtilityMethods.isNetworkConnected (PaymentFinalizeActivity.this ) ) {
            mProgressDialog = DialogUtil.getProgressDialog ( PaymentFinalizeActivity.this, getResources ( ).getString ( R.string.app_DialogInfo ) , getResources ( ).getString ( R.string.app_DialogLoading ) );
            PaymentGatewayInterface apiService = PaymentGatewayClient.getAccountClient ().create ( PaymentGatewayInterface.class );
            Call<PaymentGatewayAccessTokenResponse> call = apiService.GetAccessToken ("Basic "+processorParam.getClientId (),"application/vnd.ni-identity.v1+json");
            call.enqueue(new Callback<PaymentGatewayAccessTokenResponse> () {
                @Override
                public void onResponse(Call<PaymentGatewayAccessTokenResponse> call, retrofit2.Response<PaymentGatewayAccessTokenResponse> response) {
                    mProgressDialog.dismiss ();
                    if (response.code() == 200) {
                        PaymentGatewayAccessTokenResponse paymentGatewayAccessTokenResponse=response.body();
                        AccessTokenString= paymentGatewayAccessTokenResponse != null ? paymentGatewayAccessTokenResponse.getAccessToken () : null;
                        Log.v("AccessTokenString",AccessTokenString);
                        CreateOrderApi();
                    }
                    else{
                        UtilityMethods.PrintToast (PaymentFinalizeActivity.this,"Some Things happening wrong  Please contact Yallagamer Support Team ",1);

                    }
                }
                @Override
                public void onFailure(Call<PaymentGatewayAccessTokenResponse> call, Throwable t) {
                    Log.v("URL",t.getMessage ());
                    mProgressDialog.dismiss ();
                    UtilityMethods.PrintToast (PaymentFinalizeActivity.this,"Server unreachable Please contact Yallagamer Support Team ",1);
                    Intent i = new Intent (PaymentFinalizeActivity.this, MainActivity.class);
                    i.putExtra ("ScreenStatus",5);
                    startActivity (i);
                    finish ();
                }
            });

        }
        else {
            GetDialogueInternetConnection(1);
        }
    }
    private  void CreateOrderApi ( ) {
        if ( UtilityMethods.isNetworkConnected (PaymentFinalizeActivity.this ) ) {
            mProgressDialog = DialogUtil.getProgressDialog ( PaymentFinalizeActivity.this, getResources ( ).getString ( R.string.app_DialogInfo ) , getResources ( ).getString ( R.string.app_DialogLoading ) );
            PaymentGatewayInterface apiService = PaymentGatewayClient.getAccountClient ().create ( PaymentGatewayInterface.class );
            Log.v("Request","transactions/outlets/"+AccessTokenString+"/orders"+"\n"+"Bearer "+processorParam.getClientId ()+"\n"+"application/vnd.ni-payment.v2+json"+"\n"+processorParam.getOrderPrefix ());
            Log.v("paymentGatewayBodyRequest",new Gson ().toJson (paymentGatewayBodyRequest));
            Call<CreateOrderResponseDto> call = apiService.CreateOrderApi ("Bearer "+AccessTokenString,"application/vnd.ni-payment.v2+json",processorParam.getOrderPrefix (),paymentGatewayBodyRequest);
            call.enqueue(new Callback<CreateOrderResponseDto> () {
                @Override
                public void onResponse( Call<CreateOrderResponseDto> call, retrofit2.Response<CreateOrderResponseDto> response) {
                    mProgressDialog.dismiss ();
                    Log.v("code",response.code ()+"");
                    Log.v("dsfsd",new Gson ().toJson (response.body ()));
                  /*  try {
                        Log.v("Error code 400",response.errorBody().string());
                    } catch (IOException e) {
                        Log.v("Error code 400",e+"");
                    }*/
                    if (response.code() == 201) {
                        CreateOrderResponseDto responseDto=response.body();
                        CardPaymentRequest.Builder b = new CardPaymentRequest.Builder ();
                        b.gatewayUrl (responseDto != null ? responseDto.getPaymentLinks ().getPaymentAuthorization ().getHref () : null);
                        b.code (responseDto.getPaymentLinks().getPayment().getHref().split("=")[1]);
                        PaymentClient paymentClient=new PaymentClient(PaymentFinalizeActivity.this);
                        paymentClient.launchCardPayment(b.build (),0);
                      //  Toast.makeText(PaymentymentFinalizeActivity.this,"Order created",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        UtilityMethods.PrintToast (PaymentFinalizeActivity.this, "Some Things happening wrong  Please contact Yallagamer Support Team ", 1);
                    }
                }
                @Override
                public void onFailure(Call<CreateOrderResponseDto> call, Throwable t) {
                    Log.v("URL",t.getMessage ());
                    mProgressDialog.dismiss ();
                    UtilityMethods.PrintToast (PaymentFinalizeActivity.this,"Server unreachable Please contact Yallagamer Support Team ",1);
                    onBackPressed ();
                }
            });

        }
        else {
            GetDialogueInternetConnection(2);
        }
    }

    private void GetDialogueInternetConnection ( int i ) {
        Dialog dialog = new Dialog (PaymentFinalizeActivity.this);
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        Window window = dialog.getWindow ( );
        WindowManager.LayoutParams wlp = window.getAttributes ( );
        window.setLayout ( ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT );
        dialog.getWindow ( ).addFlags ( WindowManager.LayoutParams.FLAG_DIM_BEHIND );
        dialog.getWindow ( ).setBackgroundDrawable ( new ColorDrawable ( android.graphics.Color.TRANSPARENT ) );
        window.setAttributes ( wlp );
        TextView TvRetry =  dialog.findViewById(R.id.TvRetry);
        TvRetry.setOnClickListener(v -> {
            if(i==1&&UtilityMethods.isNetworkConnected(PaymentFinalizeActivity.this))
                GetAccessToken();
            else if(i==2&&UtilityMethods.isNetworkConnected(PaymentFinalizeActivity.this))
                CreateOrderApi();
//            else if(i==3&&UtilityMethods.isNetworkConnected(PaymentActivity.this))
//                //RemoveCoupon();
            else
                GetDialogue(i);
            dialog.dismiss();
        });
        TextView TvRetryNot =  dialog.findViewById(R.id.TvRetryNot);
        TvRetryNot.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    @Override
    public void onBackPressed ( ) {
        //super.onBackPressed ();
        Dialog dialog = new Dialog (PaymentFinalizeActivity.this);
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        Window window = dialog.getWindow ( );
        WindowManager.LayoutParams wlp = window.getAttributes ( );
        window.setLayout ( ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT );
        dialog.getWindow ( ).addFlags ( WindowManager.LayoutParams.FLAG_DIM_BEHIND );
        dialog.getWindow ( ).setBackgroundDrawable ( new ColorDrawable ( android.graphics.Color.TRANSPARENT ) );
        window.setAttributes ( wlp );///////////////////////////////l

        TextView TvTeas = dialog.findViewById (R.id.TvTeas);
        TvTeas.setText ("Do You want to Home Sceen ");

        TextView TvRetry =  dialog.findViewById(R.id.TvRetry);
        TvRetry.setText ("Agree");
        TvRetry.setOnClickListener(v -> {
            dialog.dismiss();
            Intent i = new Intent ( PaymentFinalizeActivity.this , MainActivity.class );
            i.putExtra("ScreenStatus",5);
            startActivity ( i );
            overridePendingTransition ( R.anim.slide_out_right,R.anim.slide_in_right );
            finish ( );

        });


        TextView TvRetryNot= dialog.findViewById (R.id.TvRetryNot);
        TvRetryNot.setText ("Cancel");
        TvRetryNot.setOnClickListener(v -> dialog.dismiss());
        dialog.show();



    }
}

