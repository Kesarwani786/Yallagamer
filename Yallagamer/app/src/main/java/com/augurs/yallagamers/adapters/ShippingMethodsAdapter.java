package com.augurs.yallagamers.adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.augurs.yallagamers.R;
import com.augurs.yallagamers.UserInterface.MainActivity;
import com.augurs.yallagamers.UserInterface.address.AddressFinalizeActivity;
import com.augurs.yallagamers.api_module.ApiClient;
import com.augurs.yallagamers.api_module.ApiInterface;
import com.augurs.yallagamers.api_module.DialogUtil;
import com.augurs.yallagamers.data_models.AddressInterface;
import com.augurs.yallagamers.data_models.PushCartToLoggedInUserModel;
import com.augurs.yallagamers.data_models.ShippingScreenModel;
import com.augurs.yallagamers.data_models.Shippings;
import com.augurs.yallagamers.utills.LoginPreferences;
import com.augurs.yallagamers.utills.UtilityMethods;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;

public class ShippingMethodsAdapter extends RecyclerView.Adapter<ShippingMethodsAdapter.ListViewHolder> {
    ProgressDialog mProgressDialog;
    private Context context;
    private List < Shippings > categoryList;
    AddressInterface addressInterface;
    private  int Id=0;

    public ShippingMethodsAdapter(Context context, List< Shippings > categoryList, AddressInterface addressInterface) {
        this.context = context;
        this.categoryList = categoryList;
        this.addressInterface=addressInterface;
    }
    public static class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView TvTextHeader;
        private ImageView imgChecked;
        private RelativeLayout RlMain;
        public ListViewHolder( View itemView) {
            super(itemView);
            RlMain= itemView.findViewById ( R.id.RlMain );
            imgChecked= itemView.findViewById ( R.id.imgChecked );
            TvTextHeader=itemView.findViewById ( R.id.TvText );
        }
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shipping_list, parent, false);
        ListViewHolder evh = new ListViewHolder(v);
        return evh;
    }
    @SuppressLint ("SetTextI18n")
    @Override
    public void onBindViewHolder( final ListViewHolder holder, final int position) {
        Shippings data=categoryList.get(position);
        Log.v("data",data.getSelected ()+"");
        if(data.getSelected ()) {
            holder.imgChecked.setVisibility (View.VISIBLE);
            Id=data.getShippingId ();
            addressInterface.Id(Id);
        }
        else
            holder.imgChecked.setVisibility ( View.GONE );

        if(data.getServiceDeliveryTime ()!=null&&data.getServiceDeliveryTime ().length ()!=0)
            holder.TvTextHeader.setText ( data.getShipping ()+" -"+data.getRate ()+" ("+data.getServiceDeliveryTime ()+")" );
        else
            holder.TvTextHeader.setText ( data.getShipping ()+" -"+data.getRate () );
        holder.RlMain.setOnClickListener ( v -> {

            UpdateShippingParameter(data.getShippingId ());
        } );
    }

    @SuppressLint ("CheckResult")
    private void UpdateShippingParameter ( Integer shippingId ) {
        if ( UtilityMethods.isNetworkConnected ( context) ) {
            mProgressDialog = DialogUtil.getProgressDialog ( context , context.getResources ( ).getString ( R.string.app_DialogInfo ) , context.getResources ( ).getString ( R.string.app_DialogLoading ) );
            String header = Credentials.basic (context.getString ( R.string.user_id ) ,context.getString ( R.string.user_password ) );
            ApiInterface apiService = ApiClient.getClient (context).create ( ApiInterface.class );
            PushCartToLoggedInUserModel pushCartToLoggedInUserModel = new PushCartToLoggedInUserModel();
            pushCartToLoggedInUserModel.setMethod ( "updateCartShippingmethod" );
            pushCartToLoggedInUserModel.setShipping_id (shippingId);
            pushCartToLoggedInUserModel.setCartId (new LoginPreferences(context).getString("cart_id"));
            Observable <ShippingScreenModel> hpObservable = apiService.UpdateShippingMethod ( header,"Cart",new LoginPreferences (context).getString ( "token" ),pushCartToLoggedInUserModel);
            hpObservable.observeOn ( AndroidSchedulers.mainThread ( ) ).subscribeOn ( Schedulers.io ( ) ).subscribe ( this :: handleResults , this :: handleError );
        }
        else {
            GetDialogue(1,shippingId);
        }
    }

    private void handleResults ( ShippingScreenModel baseResponse ) {
        mProgressDialog.dismiss ();
        if(baseResponse.getStatus ()==200){
            addressInterface.Id(Id);
            ((AddressFinalizeActivity)context).handleResultsHP (baseResponse);
        }
        else
            UtilityMethods.PrintToast ( context,baseResponse.getData ().getMessage (),1 );

    }
    public void handleError ( Throwable throwable ) {
        mProgressDialog.dismiss ();
        Log.e ( "User Data", "negative" + throwable.getMessage ( ) );
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
    private void GetDialogue ( int i,Integer shippingId ) {
        Dialog dialog = new Dialog (context);
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
                if(i==1&&UtilityMethods.isNetworkConnected(context))
                    UpdateShippingParameter(shippingId);
                else
                    GetDialogue(i,shippingId);
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
}



