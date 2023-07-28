package com.augurs.yallagamers.UserInterface.Order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.augurs.yallagamers.R;
import com.augurs.yallagamers.adapters.ProductListOrderDetailsById;
import com.augurs.yallagamers.api_module.ApiClient;
import com.augurs.yallagamers.api_module.ApiInterface;
import com.augurs.yallagamers.api_module.DialogUtil;
import com.augurs.yallagamers.data_models.OrderDetailsModel;
import com.augurs.yallagamers.utills.LoginPreferences;
import com.augurs.yallagamers.utills.UtilityMethods;
import com.bumptech.glide.Glide;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;

public class OrderDetailsActivity extends AppCompatActivity {
    private RecyclerView recycleViewProduct;
    private ProgressDialog mProgressDialog;
    private  TextView TvBillingUserName,TvBillingAddress,TvShippingUserName,TvBillingMobileNO,  TvShippingMobile,TvShippingAddress;
    private  TextView TvUserName,TvEmail,TvMobile,TvPaymentMethod,TvShippingMethod,TvAMount,TvSubTotal,TvShipping;
    private ImageView ImgBarCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        TextView toolbar_title= findViewById(R.id.toolbar_title);
        toolbar_title.setText("Order Details ");
        ImageView img_back = findViewById (R.id.img_back);
        img_back.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick ( View v ) {
                onBackPressed ();
            }
        });
        ImgBarCode= findViewById (R.id.ImgBarCode);
        TvBillingUserName= findViewById(R.id.TvBillingUserName);
        TvBillingAddress= findViewById(R.id.TvBillingAddress);
        TvShippingUserName= findViewById(R.id.  TvShippingUserName);
        TvShippingMobile= findViewById(R.id.TvShippingMobile);
        TvBillingMobileNO= findViewById(R.id.TvBillingMobileNO);
        TvShippingAddress= findViewById(R.id.TvShippingAddress);
        TvPaymentMethod= findViewById(R.id.TvPaymentMethod);
        TvUserName= findViewById(R.id.TvUserName);
        TvEmail= findViewById(R.id.TvEmail);
        TvMobile= findViewById(R.id.TvMobile);
        TvShippingMethod= findViewById(R.id.TvShippingMethod);
        TvAMount= findViewById(R.id.TvAMount);
        TvSubTotal= findViewById(R.id.TvSubTotal);
        recycleViewProduct= findViewById(R.id.recycleViewProduct);
        TvShipping= findViewById (R.id.TvShipping);
        GetProductDetails();
    }
    private void GetProductDetails (  ) {
        if (UtilityMethods.isNetworkConnected(OrderDetailsActivity.this)) {
            mProgressDialog = DialogUtil.getProgressDialog(OrderDetailsActivity.this, getResources().getString(R.string.app_DialogInfo), getResources().getString(R.string.app_DialogLoading));
            ApiInterface apiService = ApiClient.getClient(this).create(ApiInterface.class);
            String header = Credentials.basic(getResources().getString(R.string.user_id), getResources().getString(R.string.user_password));
            Observable<OrderDetailsModel> catObservable = apiService.GetOrderDetails(header, "Cart", "getOrderDetails",new LoginPreferences(OrderDetailsActivity.this).getString("token"), getIntent ().getStringExtra ("OrderId"));
            catObservable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this :: handleResultsCE, this :: handleErrorCE);
        } else
            GetDialogue(1);
    }

    @SuppressLint ("SetTextI18n")
    private void handleResultsCE ( OrderDetailsModel orderDetailsModel ) {
        mProgressDialog.dismiss();
        if(orderDetailsModel.getStatus()==200){
            TvBillingUserName.setText(orderDetailsModel.getData().getUserData().getB_firstname()+" "+orderDetailsModel.getData().getUserData().getB_lastname());
            TvShippingUserName.setText(orderDetailsModel.getData().getUserData().getS_firstname()+" "+orderDetailsModel.getData().getUserData().getS_lastname());
            TvBillingMobileNO.setText("Mobile "+orderDetailsModel.getData().getUserData().getB_phone());
            TvShippingMobile.setText("Mobile "+orderDetailsModel.getData().getUserData().getS_phone());
            TvBillingAddress.setText(" Address "+orderDetailsModel.getData().getUserData().getB_address()+" "+orderDetailsModel.getData().getUserData().getB_address_2()+" "+orderDetailsModel.getData().getUserData().getB_city()+" "+orderDetailsModel.getData().getUserData().getB_state()+" "+orderDetailsModel.getData().getUserData().getB_country() );
            TvShippingAddress.setText("Address "+orderDetailsModel.getData().getUserData().getS_address()+" "+orderDetailsModel.getData().getUserData().getS_address_2()+" "+orderDetailsModel.getData().getUserData().getS_city()+" "+orderDetailsModel.getData().getUserData().getS_state()+" "+orderDetailsModel.getData().getUserData().getS_country() );
            TvUserName.setText(orderDetailsModel.getData().getUserData().getFirstname()+" "+orderDetailsModel.getData().getUserData().getLastname());
            TvEmail.setText("Email: "+orderDetailsModel.getData().getUserData().getEmail());
            TvMobile.setText("Mobile: "+orderDetailsModel.getData().getUserData().getPhone());
            TvPaymentMethod.setText(orderDetailsModel.getData().getPaymentMethod().getDescription ());
            TvShippingMethod.setText(orderDetailsModel.getData().getChosenShippings().getShipping ());
            TvAMount.setText(orderDetailsModel.getData().getTotal ()+"");
            if(orderDetailsModel.getData ().getShippingCost ()!=null)
            TvShipping.setText (orderDetailsModel.getData ().getShippingCost ()+"");
            else
            {
                RelativeLayout RlSHipping= findViewById (R.id.RlSHipping);
                RlSHipping.setVisibility (View.GONE);
            }
            TvSubTotal.setText(orderDetailsModel.getData().getSubtotal ()+"");
            recycleViewProduct.setLayoutManager ( new LinearLayoutManager(OrderDetailsActivity.this, LinearLayoutManager.VERTICAL,false ) );
            recycleViewProduct.setAdapter ( new ProductListOrderDetailsById (OrderDetailsActivity.this,orderDetailsModel.getData ( ).getProducts ( ) ) );
            Glide.with(OrderDetailsActivity.this).load(orderDetailsModel.getData ().getBarcodeImg ()).placeholder(R.drawable.placeholder).into(ImgBarCode);
        }
        else
            UtilityMethods.PrintToast(OrderDetailsActivity.this,orderDetailsModel.getData().getMessage(),1);
    }

    private void handleErrorCE ( Throwable throwable ) {
        mProgressDialog.dismiss();
        UtilityMethods.PrintToast(OrderDetailsActivity.this, throwable.getMessage(), 1);
    }

    private void GetDialogue ( int i ) {
        Dialog dialog = new Dialog(OrderDetailsActivity.this);
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
        TvRetry.setOnClickListener(v -> {
            if (i == 1 && UtilityMethods.isNetworkConnected(OrderDetailsActivity.this))
                GetProductDetails();
            else
                GetDialogue(i);
            dialog.dismiss();
        });
        TextView TvRetryNot = dialog.findViewById(R.id.TvRetryNot);
        TvRetryNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick ( View v ) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}