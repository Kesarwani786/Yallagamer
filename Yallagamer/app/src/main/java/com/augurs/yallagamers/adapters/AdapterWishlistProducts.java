package com.augurs.yallagamers.adapters;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.augurs.yallagamers.UserInterface.MainActivity;
import com.augurs.yallagamers.R;
import com.augurs.yallagamers.api_module.ApiClient;
import com.augurs.yallagamers.api_module.ApiInterface;
import com.augurs.yallagamers.api_module.Const;
import com.augurs.yallagamers.api_module.DialogUtil;
import com.augurs.yallagamers.data_models.AddItemInBagResponceModel;
import com.augurs.yallagamers.data_models.AddProductInBagGuestUserModel;
import com.augurs.yallagamers.data_models.AddRemoveWishlistModel;
import com.augurs.yallagamers.data_models.PushCartToLoggedInUserModel;
import com.augurs.yallagamers.data_models.WishList;
import com.augurs.yallagamers.UserInterface.ProductDetails.ProductDetailsActivity;
import com.augurs.yallagamers.utills.LoginPreferences;
import com.augurs.yallagamers.utills.UtilityMethods;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;

public class AdapterWishlistProducts extends RecyclerView.Adapter<AdapterWishlistProducts.ListViewHolder> {
    private Context context;
    private ArrayList<WishList.Datum> productList;
    ProgressDialog mProgressDialog;

    public AdapterWishlistProducts ( Context context, ArrayList<WishList.Datum> productList ) {
        this.context = context;
        this.productList = productList;
    }

    public void addListArray ( ArrayList<WishList.Datum> itemList ) {
        this.productList = itemList;
        notifyDataSetChanged ();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutProduct;
        private TextView txtProduct, tv_product_price, txtProductOldPrice, tv_product_stock_status, tv_product_desc, tvAddInBag;
        private ImageView star_button;
        private ImageView iv_item_image, ImgDelete;

        public ListViewHolder ( View itemView ) {
            super (itemView);
            ImgDelete = itemView.findViewById (R.id.ImgDelete);
            iv_item_image = itemView.findViewById (R.id.iv_item_image);
            layoutProduct = itemView.findViewById (R.id.layout_product);
            txtProduct = itemView.findViewById (R.id.tv_product);
            tv_product_price = itemView.findViewById (R.id.tv_item_current_price);
            txtProductOldPrice = itemView.findViewById (R.id.textView10);
            star_button = itemView.findViewById (R.id.star_button);
            tv_product_stock_status = itemView.findViewById (R.id.tv_product_stock_status);
            tv_product_desc = itemView.findViewById (R.id.tv_product_desc);
            tvAddInBag = itemView.findViewById (R.id.tvAddInBag);
        }
    }

    @Override
    public ListViewHolder onCreateViewHolder ( @NonNull ViewGroup parent, int i ) {
        View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.row_product, parent, false);
        ListViewHolder evh = new ListViewHolder (v);
        return evh;
    }

    @SuppressLint ("RecyclerView")
    @Override
    public void onBindViewHolder ( @NonNull final ListViewHolder holder, int position ) {
        WishList.Datum data = productList.get (position);
        holder.txtProduct.setText (data.getProduct () + "");
        holder.tv_product_desc.setText (data.getMain_category () + "");
        if(data.getStock ().equals ("In Stock"))
            holder.tv_product_stock_status.setTextColor (Color.parseColor ("#28AF2E"));
        else
            holder.tv_product_stock_status.setTextColor (Color.parseColor ("#FF0A2D"));

        holder.tv_product_stock_status.setText (data.getStock () + "  ");
        Glide.with (this.context).load (data.getMain_image ()).apply (RequestOptions.bitmapTransform (new RoundedCorners (5))).placeholder (R.drawable.placeholder).into (holder.iv_item_image);
        int listPrice = (int) data.getList_price ();
        int BasePrice = (int) data.getBase_price ();
        if( listPrice<=BasePrice)
        {
            holder.tv_product_price.setText(Const.CurrencyValue+BasePrice);
            holder.txtProductOldPrice.setVisibility ( View.GONE );
        }
        else {
            holder.txtProductOldPrice.setText(Const.CurrencyValue +listPrice);
            holder.tv_product_price.setText(Const.CurrencyValue+BasePrice);
        }

        holder.tvAddInBag.setOnClickListener (v -> AddinBag (data.getProductId ()));

        holder.layoutProduct.setOnClickListener (view -> {
            Intent intent = new Intent (context, ProductDetailsActivity.class);
            intent.putExtra ("ProductId", data.getProductId ());
            context.startActivity (intent);
        });
        holder.ImgDelete.setOnClickListener (v -> addToWishlist (data.getProductId () + "", "remove"));

    }

    private void AddinBag ( Integer productId ) {
        AddProductInBagGuestUserModel addProductInBagGuestUserModel = new AddProductInBagGuestUserModel ();
        addProductInBagGuestUserModel.setProductId (productId);
        addProductInBagGuestUserModel.setMethod ("addToCart");
        addProductInBagGuestUserModel.setQuantity (1);
        ApiInterface apiService = ApiClient.getClient (context).create (ApiInterface.class);
        mProgressDialog = DialogUtil.getProgressDialog (context, context.getResources ().getString (R.string.app_DialogInfo), context.getResources ().getString (R.string.app_DialogLoading));
        String header = Credentials.basic (context.getResources ().getString (R.string.user_id), context.getResources ().getString (R.string.user_password));
        Single<AddItemInBagResponceModel> userObservable;
        userObservable = apiService.AddInBag (header, "Cart", addProductInBagGuestUserModel, new LoginPreferences (context).getString ("token"));
        userObservable.observeOn (AndroidSchedulers.mainThread ())
                .subscribeOn (Schedulers.io ())
                .subscribe (this :: handleResultsBagGuestUser, this :: handleErrorBagGuestUser);


    }

    private void handleResultsBagGuestUser ( AddItemInBagResponceModel baseResponse ) {
        mProgressDialog.dismiss ();
        if (baseResponse.getStatus () == 200) {
            Intent i = new Intent (context, MainActivity.class);
            i.putExtra ("ScreenStatus", 3);
            context.startActivity (i);
            UtilityMethods.PrintToast (context, "Product Added to Your Cart", 1);
        } else
            UtilityMethods.PrintToast (context, baseResponse.getData ().getMessage (), 1);

    }

    private void handleErrorBagGuestUser ( Throwable throwable ) {
        mProgressDialog.dismiss ();
        UtilityMethods.PrintToast (context, throwable.getMessage (), 1);
    }

    @Override
    public int getItemCount ( ) {
        return productList.size ();
    }

    public void addToWishlist ( String product_id, String Status ) {
        PushCartToLoggedInUserModel pushCartToLoggedInUserModel = new PushCartToLoggedInUserModel ();
        pushCartToLoggedInUserModel.setMethod ("addToWishlist");
        pushCartToLoggedInUserModel.setAction (Status);
        pushCartToLoggedInUserModel.setProductId (product_id);
        ApiInterface apiService = ApiClient.getClient (context).create (ApiInterface.class);
        LoginPreferences loginPreferences = new LoginPreferences (context);
        mProgressDialog = DialogUtil.getProgressDialog (context, context.getResources ().getString (R.string.app_DialogInfo), context.getResources ().getString (R.string.app_DialogLoading));
        String header = Credentials.basic ("augurstest@gmail.com", context.getResources ().getString (R.string.user_password));
        Single<AddRemoveWishlistModel> userObservable = apiService.addProductItemToWishList (header, "Wishlist2", loginPreferences.getString ("token"), pushCartToLoggedInUserModel);
        userObservable.observeOn (AndroidSchedulers.mainThread ())
                .subscribeOn (Schedulers.io ())
                .observeOn (AndroidSchedulers.mainThread ())
                .subscribe (this :: handleResults, this :: handleError);
    }


    private void handleResults ( AddRemoveWishlistModel baseResponse ) {
        mProgressDialog.dismiss ();
        if (baseResponse.getStatus () == 200) {
            if (! baseResponse.getToken ().trim ().isEmpty ()) {
                Intent i = new Intent (context, MainActivity.class);
                i.putExtra ("ScreenStatus", 3);
                context.startActivity (i);
            }
            UtilityMethods.PrintToast (context, baseResponse.getData ().getMessage (), 0);
        } else {
            UtilityMethods.PrintToast (context, baseResponse.getData ().getMessage (), 1);
        }
    }

    private void handleError ( Throwable throwable ) {
        mProgressDialog.dismiss ();
        Log.e ("User Data", "negative" + throwable.getMessage ());
    }
}




