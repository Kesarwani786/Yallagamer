package com.augurs.yallagamers.UserInterface.payment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.augurs.yallagamers.R;
import com.augurs.yallagamers.UserInterface.MainActivity;
import com.augurs.yallagamers.UserInterface.address.AddAddressModel;
import com.augurs.yallagamers.UserInterface.address.AddressFinalizeActivity;
import com.augurs.yallagamers.adapters.TaxationAdapter;
import com.augurs.yallagamers.api_module.ApiClient;
import com.augurs.yallagamers.api_module.ApiInterface;
import com.augurs.yallagamers.api_module.Const;
import com.augurs.yallagamers.api_module.DialogUtil;
import com.augurs.yallagamers.data_models.AddressInterface;
import com.augurs.yallagamers.data_models.PaymentScreenModel;
import com.augurs.yallagamers.data_models.ProcessorParam;
import com.augurs.yallagamers.utills.ConstantVariable;
import com.augurs.yallagamers.utills.LoginPreferences;
import com.augurs.yallagamers.utills.UtilityMethods;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;
public class PaymentActivity extends AppCompatActivity implements AddressInterface {
    TextView TvHeader,Tvapply;
    RecyclerView recyclerview,recyclarview_other;
    ProgressDialog mProgressDialog;
    ImageView img_back;
    Integer PaymentId=0;
    EditText etPromocode;
    int CouponValue =0;
    ProcessorParam processorParam;
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_payment );
        etPromocode= findViewById (R.id.etPromocode);
        TextView  TvRemoveCoupon= findViewById (R.id.TvRemoveCoupon);
        TvRemoveCoupon.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick ( View v ) {
                if(etPromocode.getText ().toString ().length ()==0)
                    UtilityMethods.PrintToast (PaymentActivity.this,"Please enter Promocode ", 1);
                else
                    RemoveCoupon();
            }
        });
        Tvapply = findViewById (R.id.Tvapply);
        Tvapply.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick ( View v ) {
                if(etPromocode.getText ().toString ().length ()==0)
                    UtilityMethods.PrintToast (PaymentActivity.this,"Please enter Promocode ", 1);
                else
                    ApplyPromocode();
            }
        });
        CallAPi();
        TvHeader= findViewById ( R.id.toolbar_title );
        recyclarview_other =findViewById(R.id.recyclarview_other);
        recyclerview =findViewById(R.id.recyclarview);
        img_back =findViewById(R.id.img_back);
        TvHeader.setText ( "PAYMENT" );
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        TextView TvSubmit= findViewById(R.id.TvSubmit);
        TvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if(PaymentId!=0)
                 {
                     Intent intent = new Intent(PaymentActivity.this,PaymentFinalizeActivity.class);
                     intent.putExtra("PaymentId",PaymentId);
                     intent.putExtra(ConstantVariable.PaymentScreenPaymentGatewayParamter,new Gson ().toJson (processorParam));
                     intent.putExtra (ConstantVariable.AddressParameter,new Gson().toJson (new Gson().fromJson ( new JsonParser ().parse(getIntent ().getStringExtra (ConstantVariable.AddressParameter)) , AddAddressModel.class )));
                     startActivity(intent);
                 }
                 else
                     UtilityMethods.PrintToast(PaymentActivity.this,"Please Select Payment Method",1);
            }
        });
    }

    private void RemoveCoupon ( ) {
        if ( UtilityMethods.isNetworkConnected (PaymentActivity.this ) ) {
            CouponValue=0;
            mProgressDialog = DialogUtil.getProgressDialog ( PaymentActivity.this, getResources ( ).getString ( R.string.app_DialogInfo ) , getResources ( ).getString ( R.string.app_DialogLoading ) );
            String header = Credentials.basic ( getResources ( ).getString ( R.string.user_id ) , getResources ( ).getString ( R.string.user_password ) );
            ApiInterface apiService = ApiClient.getClient ( PaymentActivity.this ).create ( ApiInterface.class );
            Observable < PaymentScreenModel > hpObservable=apiService.RemoveCoupon ( header , "Cart" ,"removedCopunCode",new LoginPreferences ( PaymentActivity.this ).getString ( "token" ) ,etPromocode.getText ().toString (), new LoginPreferences(PaymentActivity.this).getString("cart_id"));
            hpObservable.observeOn ( AndroidSchedulers.mainThread ( ) ).subscribeOn ( Schedulers.io ( ) ).subscribe ( this :: handleResultsHP , this :: handleErrorHP );
        }
        else {
            GetDialogue(2);
        }
    }

    private void ApplyPromocode ( ) {
        if ( UtilityMethods.isNetworkConnected (PaymentActivity.this ) ) {
            CouponValue=1;
            mProgressDialog = DialogUtil.getProgressDialog ( PaymentActivity.this, getResources ( ).getString ( R.string.app_DialogInfo ) , getResources ( ).getString ( R.string.app_DialogLoading ) );
            String header = Credentials.basic ( getResources ( ).getString ( R.string.user_id ) , getResources ( ).getString ( R.string.user_password ) );
            ApiInterface apiService = ApiClient.getClient ( PaymentActivity.this ).create ( ApiInterface.class );
            Observable < PaymentScreenModel > hpObservable=apiService.ApplyPromocode ( header , "Cart" ,"applyCopunCode",new LoginPreferences ( PaymentActivity.this ).getString ( "token" ) ,etPromocode.getText ().toString (), new LoginPreferences(PaymentActivity.this).getString("cart_id"));
            hpObservable.observeOn ( AndroidSchedulers.mainThread ( ) ).subscribeOn ( Schedulers.io ( ) ).subscribe ( this :: handleResultsHP , this :: handleErrorHP );
        }
        else {
            GetDialogue(2);
        }
    }

    private void CallAPi ( ) {
        if ( UtilityMethods.isNetworkConnected (PaymentActivity.this ) ) {
            mProgressDialog = DialogUtil.getProgressDialog ( PaymentActivity.this, getResources ( ).getString ( R.string.app_DialogInfo ) , getResources ( ).getString ( R.string.app_DialogLoading ) );
            String header = Credentials.basic ( getResources ( ).getString ( R.string.user_id ) , getResources ( ).getString ( R.string.user_password ) );
            ApiInterface apiService = ApiClient.getClient ( PaymentActivity.this ).create ( ApiInterface.class );
            Observable < PaymentScreenModel > hpObservable=apiService.GetPaymentScreen ( header , "Cart" ,  new LoginPreferences ( PaymentActivity.this ).getString ( "token" ) , "paymentScreen", new LoginPreferences(PaymentActivity.this).getString("cart_id"));
            hpObservable.observeOn ( AndroidSchedulers.mainThread ( ) ).subscribeOn ( Schedulers.io ( ) ).subscribe ( this :: handleResultsHP , this :: handleErrorHP );
        }
        else {
            GetDialogue(1);
        }
    }
    private void handleResultsHP (PaymentScreenModel homePageData ) {
        mProgressDialog.dismiss ( );
        if(homePageData.getStatus() == 200){
            processorParam=homePageData.getData ().getPaymentMethods ().get (0).getProcessorParams ().get (0);
            recyclarview_other.setAdapter(new PaymentBankotherAdapter (this,homePageData.getData().getPaymentMethods(),PaymentActivity.this));
            recyclarview_other.setLayoutManager( new LinearLayoutManager(PaymentActivity.this));
            TextView textView54 = findViewById ( R.id.TvMrp );
            textView54.setText ( homePageData.getData ( ).getSubtotal ( ) + " " );
            TextView textView56 = findViewById ( R.id.TvDiscount );
            TextView TvTotalAmount = findViewById ( R.id.TvTotalAmount );
            TvTotalAmount.setText ( homePageData.getData ( ).getSubtotalDiscount ( ) + " " );
            TextView TvAMountDetails = findViewById ( R.id.TvAMountDetails );
            TvAMountDetails.setText (Const.CurrencyValue+ homePageData.getData ( ).getDiscountedSubtotal ());
            if(homePageData.getData ( ).getSubtotalDiscount ()==0){
                RelativeLayout RlDiscount= findViewById (R.id.RlDiscount);
                RlDiscount.setVisibility (View.GONE);
            }
            else {
                textView56.setText ( homePageData.getData ( ).getSubtotalDiscount ( ) + " " );

            }
            TextView TvTextAMount = findViewById ( R.id.TvTextAMount );
            TvTextAMount.setText ( Html.fromHtml ( "<font color=#000000>You will earn </font> <font color=#d3d3d3>"+homePageData.getData ().getEarnRewardPoints ()+"</font><font color=#000000> insider points on this purchase </font>" ) );
            RecyclerView recycleview1= findViewById ( R.id.recycleview1 );
            recycleview1.setLayoutManager ( new LinearLayoutManager (PaymentActivity.this, LinearLayoutManager.VERTICAL,false ) );
            if(homePageData.getData ( ).getTaxes ( ).size ()!=0){
                recycleview1.setAdapter ( new TaxationAdapter(PaymentActivity.this,homePageData.getData ( ).getTaxes ( ) ) );
            }
            else {
                TextView TvTaxation= findViewById (R.id.TvTaxation);
                TvTaxation.setVisibility (View.GONE);
            }
            TextView TvRemoveCoupon = findViewById (R.id.TvRemoveCoupon);
            if(CouponValue==1)
                TvRemoveCoupon.setVisibility (View.VISIBLE);
            else {
                TvRemoveCoupon.setVisibility (View.GONE);
                etPromocode.setText ("");
            }
            if(homePageData.getData ().getNotice ().length ()!=0){
                DialogueForNotice(homePageData.getData ().getNotice ());
            }
        }
        else
            UtilityMethods.PrintToast (PaymentActivity.this, homePageData.getData ().getMessage (),1);
    }
    private void DialogueForNotice ( String notice ) {
        Dialog dialog = new Dialog (PaymentActivity.this);
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
        TvTeas.setText(notice);
        TextView TvRetry =  dialog.findViewById(R.id.TvRetry);
        TvRetry.setText ( "Continue" );
        TvRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (PaymentActivity.this, MainActivity.class);
                i.putExtra ("ScreenStatus",5);
                startActivity (i);
                finish ();
                dialog.dismiss();
            }
        });
        TextView TvRetryNot = dialog.findViewById(R.id.TvRetryNot);
        TvRetryNot.setText ( "No" );
        TvRetryNot.setVisibility (View.GONE);
        TvRetryNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void handleErrorHP(Throwable throwable) {
        mProgressDialog.dismiss ( );
        Log.e("HP","it does not work."+throwable.getMessage ());
    }
    private void GetDialogue ( int i ) {
        Dialog dialog = new Dialog (PaymentActivity.this);
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
            if(i==1&&UtilityMethods.isNetworkConnected(PaymentActivity.this))
                CallAPi();
            else if(i==2&&UtilityMethods.isNetworkConnected(PaymentActivity.this))
                ApplyPromocode();
            else if(i==3&&UtilityMethods.isNetworkConnected(PaymentActivity.this))
            RemoveCoupon();
            else
                GetDialogue(i);
            dialog.dismiss();
        });
        TextView TvRetryNot =  dialog.findViewById(R.id.TvRetryNot);
        TvRetryNot.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
    @Override
    public void Id(int id) {
        PaymentId=id;
    }
    @Override
    public void removeAddressId(int id) {

    }
}