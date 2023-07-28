package com.augurs.yallagamers.UserInterface.address;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.augurs.yallagamers.UserInterface.MainActivity;
import com.augurs.yallagamers.R;
import com.augurs.yallagamers.UserInterface.ProductDetails.ProductDetailsActivity;
import com.augurs.yallagamers.UserInterface.profile.EditProfileActivity;
import com.augurs.yallagamers.adapters.ShippingMethodsAdapter;
import com.augurs.yallagamers.api_module.ApiClient;
import com.augurs.yallagamers.api_module.ApiInterface;
import com.augurs.yallagamers.api_module.DialogUtil;
import com.augurs.yallagamers.data_models.AddressInterface;
import com.augurs.yallagamers.data_models.ShippingScreenModel;
import com.augurs.yallagamers.UserInterface.payment.PaymentActivity;
import com.augurs.yallagamers.utills.ConstantVariable;
import com.augurs.yallagamers.utills.LoginPreferences;
import com.augurs.yallagamers.utills.UtilityMethods;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;

public class AddressFinalizeActivity extends AppCompatActivity implements AddressInterface {
    RecyclerView recycleview, recycelview12;
    ProgressDialog mProgressDialog;
    Integer ShippingId = 0;
    TextView TvName, TvAddress123, TvMobile, TvAddressListing;
    Integer Size=0;
    private AddAddressModel addAddressModel;
    private  Integer ShippingStatus=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_finalize);
        TvName = findViewById(R.id.TvName);
        TextView tv_place_order = findViewById(R.id.tv_place_order);
        tv_place_order.setOnClickListener(v -> {
            if(Size==0){
                Intent i = new Intent(AddressFinalizeActivity.this, PaymentActivity.class);
                startActivity(i);
            }
            else {
                if(ShippingStatus!=0){
                    Intent i = new Intent (AddressFinalizeActivity.this, PaymentActivity.class);
                    i.putExtra (ConstantVariable.AddressParameter,new Gson().toJson (new Gson().fromJson ( new JsonParser ().parse(getIntent ().getStringExtra (ConstantVariable.AddressParameter)) , AddAddressModel.class )));
                    startActivity (i);
                }
                else {
                    if (ShippingId == 0)
                        UtilityMethods.PrintToast (AddressFinalizeActivity.this, "Please Select Your Shipping method ", 1);
                    else {
                        Intent i = new Intent (AddressFinalizeActivity.this, PaymentActivity.class);
                        i.putExtra (ConstantVariable.AddressParameter, new Gson ().toJson (new Gson ().fromJson (new JsonParser ().parse (getIntent ().getStringExtra (ConstantVariable.AddressParameter)), AddAddressModel.class)));
                        startActivity (i);
                    }
                }
            }
        });
        ImageView img_back = findViewById (R.id.img_back);
        img_back.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick ( View v ) {
                onBackPressed ();
            }
        });
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Address");
        TvName = findViewById(R.id.TvName);
        TvAddress123 = findViewById(R.id.TvAddress123);
        TvMobile = findViewById(R.id.TvMobile);
        TvAddressListing = findViewById(R.id.TvAddressListing);
        TvAddressListing.setOnClickListener(v -> {
            if (new LoginPreferences (AddressFinalizeActivity.this).getString ("token") != null) {
                Intent i = new Intent (new Intent (AddressFinalizeActivity.this, AddressListActivity.class));
                i.putExtra ("AddressStatus", 0);
                startActivity (i);
            }
            else {
                super.onBackPressed ();
            }
        });
        GetShippingResponce();
        recycelview12 = findViewById(R.id.recycelview12);
    }

    private void GetShippingResponce() {
        if (UtilityMethods.isNetworkConnected(AddressFinalizeActivity.this)) {
            mProgressDialog = DialogUtil.getProgressDialog(AddressFinalizeActivity.this, getResources().getString(R.string.app_DialogInfo), getResources().getString(R.string.app_DialogLoading));
            String header = Credentials.basic(getResources().getString(R.string.user_id), getResources().getString(R.string.user_password));
            ApiInterface apiService = ApiClient.getClient(AddressFinalizeActivity.this).create(ApiInterface.class);
            Observable<ShippingScreenModel> hpObservable;
            if (new LoginPreferences (AddressFinalizeActivity.this).getString ("token") != null)
                hpObservable   = apiService.GetShippingResponse(header, new LoginPreferences(AddressFinalizeActivity.this).getString("token"), "Cart", "getShippingsMethod",null);
                else
                hpObservable   = apiService.GetShippingResponse(header,null, "Cart", "getShippingsMethod", new LoginPreferences(AddressFinalizeActivity.this).getString("cart_id"));
            hpObservable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this::handleResultsHP, this::handleErrorHP);
        } else
            GetDialogue(1);
    }

    private void DialogueForNotice ( String notice ) {
        Dialog dialog = new Dialog (AddressFinalizeActivity.this);
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
        TextView TvTesasd = dialog.findViewById ( R.id.TvTesasd );
        RelativeLayout TvText= dialog.findViewById (R.id.TvText);
        TextView TvRetry =  dialog.findViewById(R.id.TvRetry);
        TvRetry.setText ( "Continue" );
        TvRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (AddressFinalizeActivity.this, MainActivity.class);
                i.putExtra ("ScreenStatus",5);
                startActivity (i);
                finish ();
                dialog.dismiss();
            }
        });
        TextView TvRetryNot =  dialog.findViewById(R.id.TvRetryNot);
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


    public void handleResultsHP ( ShippingScreenModel baseResponse ) {
        mProgressDialog.dismiss();
        if (baseResponse.getStatus() == 200) {
            if(new LoginPreferences(AddressFinalizeActivity.this).getString("token")==null){
                addAddressModel = new Gson().fromJson ( new JsonParser ().parse(getIntent ().getStringExtra (ConstantVariable.AddressParameter)) , AddAddressModel.class );
                String text = "<font color=#d3d3d3>Deliver to : </font> <font color=#000000>" + addAddressModel.getsFirstname () + " " + addAddressModel.getsLastname ()  + "," + addAddressModel.getsZipcode ()  + "</font >";
                TvName.setText (Html.fromHtml (text));
                TvAddress123.setText (addAddressModel.getsAddress ()  + " " + addAddressModel.getsAddress2 ()  + " " + addAddressModel.getsCity ()  + " " + addAddressModel.getsState () + " " + addAddressModel.getsCountry ());
                TvMobile.setText (addAddressModel.getsPhone ());
                TextView TvHomeType= findViewById (R.id.TvHomeType);
                TvHomeType.setText (addAddressModel.getsAddressType ());
            }
            else {
                TvName.setText (baseResponse.getData ().getUserData ().getFirstname () + " " + baseResponse.getData ().getUserData ().getLastname ());
                TextView TvHomeType = findViewById (R.id.TvHomeType);
                TvHomeType.setText (baseResponse.getData ().getUserData ().getProfileName ());
                String text = "<b>Address. <b>" + baseResponse.getData ().getUserData ().getAddress () + "\n" + baseResponse.getData ().getUserData ().getAddress2 () + " " + baseResponse.getData ().getUserData ().getCity () + " " + baseResponse.getData ().getUserData ().getState () + " \n" + baseResponse.getData ().getUserData ().getCountry () + " -" + baseResponse.getData ().getUserData ().getZipcode ();
                TvAddress123.setText (Html.fromHtml (text));
                TvMobile.setText (Html.fromHtml ("<b>Mo. <b>" + baseResponse.getData ().getUserData ().getPhone ()));
            }
            if(baseResponse.getData ().getNotice ().length ()!=0){
                DialogueForNotice(baseResponse.getData ().getNotice ());
            }
            recycleview = findViewById(R.id.recycleview);
            recycleview.setAdapter(new ShippingMethodsAdapter(AddressFinalizeActivity.this, baseResponse.getData().getShipping(), AddressFinalizeActivity.this));
            Size=baseResponse.getData().getShipping().size ();
            recycleview.setLayoutManager(new LinearLayoutManager(AddressFinalizeActivity.this));
            recycelview12.setAdapter(new DilieveryEstimatesAdapter(this, baseResponse.getData().getCartProducts()));
            recycelview12.setLayoutManager(new LinearLayoutManager(AddressFinalizeActivity.this));
            TextView textView26 = findViewById(R.id.textView26);
            textView26.setText(Html.fromHtml("<font color=#000000>You will earn </font> <font color=#d3d3d3>" + baseResponse.getData().getEarnRewardPoints() + "</font><font color=#000000> insider points on this purchase </font>"));
            ShippingStatus=baseResponse.getData ().getShipping_required ().length ();
        } else {
            Toast.makeText(AddressFinalizeActivity.this, baseResponse.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void GetDialogue(int i) {
        Dialog dialog = new Dialog(AddressFinalizeActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setAttributes(wlp);
        TextView TvRetry = dialog.findViewById(R.id.TvRetry);
        TvRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 1 && UtilityMethods.isNetworkConnected(AddressFinalizeActivity.this))
                    GetShippingResponce();
                else
                    GetDialogue(i);
                dialog.dismiss();
            }
        });
        TextView TvRetryNot = dialog.findViewById(R.id.TvRetryNot);
        TvRetryNot.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
    private void handleErrorHP(Throwable throwable) {
        mProgressDialog.dismiss();
        Log.e("HP", "it does not work." + throwable.getMessage());
    }
    @Override
    public void Id(int id) {
        ShippingId = id;
    }
    @Override
    public void removeAddressId(int id) {
    }

    @Override
    public void onBackPressed ( ) {
        super.onBackPressed ();
    }

    public void UpdateShippingScreenData ( ShippingScreenModel baseResponse ) {
    }
}