package com.augurs.yallagamers.UserInterface.address;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.augurs.yallagamers.UserInterface.MainActivity;
import com.augurs.yallagamers.R;
import com.augurs.yallagamers.adapters.AdapterAddresslist;
import com.augurs.yallagamers.api_module.ApiClient;
import com.augurs.yallagamers.api_module.ApiInterface;
import com.augurs.yallagamers.api_module.DialogUtil;
import com.augurs.yallagamers.data_models.AddressInterface;
import com.augurs.yallagamers.data_models.AddressLIstingModel;
import com.augurs.yallagamers.UserInterface.payment.PaymentActivity;
import com.augurs.yallagamers.utills.LoginPreferences;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;
public class AddressListActivity extends AppCompatActivity implements AddressInterface {
    RecyclerView recyclerview;
    ArrayList < AddressLIstingModel > addressLIstingModel;
    TextView toolbar_title, tv_add_new_address, TvSubmit;
    ImageView img_back;
    ProgressDialog mProgressDialog;
    int ShippingAddressId = 0;
    RelativeLayout RlBottom;
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_address_list );
        callAddressListing ( );
        recyclerview = findViewById ( R.id.recyclarview );
        RlBottom = findViewById ( R.id.RlBottom );
        toolbar_title = findViewById ( R.id.toolbar_title );
        tv_add_new_address = findViewById ( R.id.tv_add_new_address );
        img_back = findViewById ( R.id.img_back );
        toolbar_title.setText ( "Address List" );

        RlBottom.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                SaveDefaultAddress ( );
            }
        } );
        if(getIntent().getIntExtra("AddressStatus",0)==0)
            RlBottom.setVisibility(View.VISIBLE);
        else
            RlBottom.setVisibility(View.GONE);
        img_back.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View view ) {
                onBackPressed ( );
            }
        } );
        addressLIstingModel = new ArrayList <> ( );
        LinearLayoutManager layoutManager = new LinearLayoutManager ( AddressListActivity.this );
        recyclerview.setLayoutManager ( layoutManager );
        tv_add_new_address.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View view ) {
                Intent i = new Intent ( AddressListActivity.this, AddNewAddressActivity.class );
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity ( i );
            }
        } );
    }

    private void SaveDefaultAddress ( ) {
        ApiInterface apiService = ApiClient.getClient ( AddressListActivity.this ).create ( ApiInterface.class );
        if ( ShippingAddressId == 0 ) {
            Toast.makeText ( AddressListActivity.this, "Please Select Shipping Address", Toast.LENGTH_SHORT ).show ( );
        } else {
            mProgressDialog = DialogUtil.getProgressDialog ( this, getResources ( ).getString ( R.string.app_DialogInfo ), getResources ( ).getString ( R.string.app_DialogLoading ) );
            LoginPreferences loginPreferences = new LoginPreferences ( AddressListActivity.this );
            String token = loginPreferences.getString ( "token" );
            String header= Credentials.basic(getResources().getString(R.string.user_id), getResources().getString(R.string.user_password));
            Single < JsonObject > userObservable = apiService.UpdateDefaultAddress ( header, "User", "change_default_address", token, ShippingAddressId );
            userObservable.observeOn ( AndroidSchedulers.mainThread ( ) ).subscribeOn ( Schedulers.io ( ) ).observeOn ( AndroidSchedulers.mainThread ( ) ).subscribe ( this :: handleDefaultAddressResult, this :: handleDefaultAddressResultError );
        }
    }

    private void handleDefaultAddressResult ( JsonObject jsonObject ) {
        mProgressDialog.dismiss ( );
        Intent i = new Intent ( AddressListActivity.this, AddressFinalizeActivity.class );
        startActivity ( i );
    }
    public void handleDefaultAddressResultError ( Throwable throwable ) {
        mProgressDialog.dismiss ( );
        Log.e ( "User Data", "negative" + throwable.getMessage ( ) );
    }

    private void callAddressListing ( ) {
        LoginPreferences loginPreferences = new LoginPreferences ( AddressListActivity.this );
        String token = loginPreferences.getString ( "token" );
        mProgressDialog = DialogUtil.getProgressDialog ( this, getResources ( ).getString ( R.string.app_DialogInfo ), getResources ( ).getString ( R.string.app_DialogLoading ) );
        ApiInterface apiService = ApiClient.getClient ( AddressListActivity.this ).create ( ApiInterface.class );
        String _d = "User"; //
        String header = Credentials.basic ( getResources ( ).getString ( R.string.user_id ), getResources ( ).getString ( R.string.user_password ) );
         Single < AddressLIstingModel > catObservable = apiService.getUserAddressListing ( header, _d, "get_shipping_address", token );
        catObservable.observeOn ( AndroidSchedulers.mainThread ( ) ).subscribeOn ( Schedulers.io ( ) ).observeOn ( AndroidSchedulers.mainThread ( ) ).subscribe ( this :: handleResults, this :: handleError );
    }

    private void handleResults ( AddressLIstingModel addressLIstingModel ) {
        mProgressDialog.dismiss ( );
            if ( addressLIstingModel.getData ( ) != null && addressLIstingModel.getData ( ).size ( ) != 0 ) {
                AdapterAddresslist adapterAddresslist = new AdapterAddresslist ( this , addressLIstingModel.getData ( ) , AddressListActivity.this ,getIntent().getIntExtra("AddressStatus",0));
                recyclerview.setAdapter ( adapterAddresslist );
            } else {
                RlBottom.setVisibility ( View.GONE );
            }
    }

    private void handleError ( Throwable throwable ) {
        mProgressDialog.dismiss ( );
        Log.e ( "User Data", "negative" + throwable.getMessage ( ) );
    }

    @Override
    public void Id ( int id ) {
        Log.v("asdasd",id+"");
        ShippingAddressId = id;
    }

    @Override
    public void removeAddressId ( int id ) {
        Log.v("id",id+"");
        RemoveAddress ( id );
    }


    private void RemoveAddress (int id ) {
        ApiInterface apiService = ApiClient.getClient ( AddressListActivity.this ).create ( ApiInterface.class );
           mProgressDialog = DialogUtil.getProgressDialog ( this, getResources ( ).getString ( R.string.app_DialogInfo ), getResources ( ).getString ( R.string.app_DialogLoading ) );
            LoginPreferences loginPreferences = new LoginPreferences ( AddressListActivity.this );
            String token = loginPreferences.getString ( "token" );
            String header = Credentials.basic ( getResources ( ).getString ( R.string.user_id ) , getResources ( ).getString ( R.string.user_password ) );
            //Single< JsonObject > userObservable = apiService.UpdateDefaultAddress(header,"User","change_default_address","77a5a4dfd22b5fd233182d2de9fadc4d",ShippingAddressId);
            Single < JsonObject > userObservable = apiService.RemoveAddress ( header, "User", "delete_shipping_address", token, id );
            userObservable.observeOn ( AndroidSchedulers.mainThread ( ) ).subscribeOn ( Schedulers.io ( ) ).observeOn ( AndroidSchedulers.mainThread ( ) ).subscribe ( this :: handleRemoveAddressResult, this :: handleRemoveAddressResultError );
    }
    private void handleRemoveAddressResult ( JsonObject jsonObject ) {
        mProgressDialog.dismiss ( );
        Log.v ( "sadasd", jsonObject.toString ( ) );
       // Toast.makeText ( AddressListActivity.this,jsonObject+"",Toast.LENGTH_LONG ).show ();
        startActivity ( new Intent ( AddressListActivity.this,AddressListActivity.class ) );
    }
    public void handleRemoveAddressResultError ( Throwable throwable ) {
        mProgressDialog.dismiss ( );
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(AddressListActivity.this, MainActivity.class);
        if(getIntent().getIntExtra("AddressStatus",0)==0)
            i.putExtra("ScreenStatus",5);
        else{
            i.putExtra("ScreenStatus",4);
        }
            startActivity(i);
    }
}