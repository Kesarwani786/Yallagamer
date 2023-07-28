package com.augurs.yallagamers.adapters;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import com.squareup.picasso.Picasso;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.augurs.yallagamers.UserInterface.CategoryListItem.CategoryListItemActivity;
import com.augurs.yallagamers.api_module.ApiClient;
import com.augurs.yallagamers.api_module.ApiInterface;
import com.augurs.yallagamers.api_module.Const;
import com.augurs.yallagamers.api_module.DialogUtil;
import com.augurs.yallagamers.data_models.AddRemoveWishlistModel;
import com.augurs.yallagamers.data_models.NewProductItem;
import com.augurs.yallagamers.UserInterface.ProductDetails.ProductDetailsActivity;
import com.augurs.yallagamers.R;
import com.augurs.yallagamers.data_models.PushCartToLoggedInUserModel;
import com.augurs.yallagamers.utills.ConstantVariable;
import com.augurs.yallagamers.utills.ImageLoader;
import com.augurs.yallagamers.utills.LoginPreferences;
import com.augurs.yallagamers.utills.UtilityMethods;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import java.util.ArrayList;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;

public class AdapterCategoryProducts extends RecyclerView.Adapter<AdapterCategoryProducts.ListViewHolder> {
    private Context context;
    private ArrayList<NewProductItem> productList;
    private ArrayList<NewProductItem> productListFilter;
    ProgressDialog mProgressDialog;
    private int Position = 0;
    public ImageLoader imageLoader;
    public AdapterCategoryProducts ( Context context, ArrayList<NewProductItem> productList ) {
        this.context = context;
        this.productList = productList;
        productListFilter = productList;
        imageLoader=new ImageLoader (context.getApplicationContext());
    }

    public void addListArray ( ArrayList<NewProductItem> itemList, Boolean filter_calling ) {
        if (filter_calling) {
            this.productList.clear ();
        }
        this.productList.addAll (itemList);
        notifyDataSetChanged ();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutProduct;
        private TextView txtProduct, txtProductDesc, txtProductOldPrice, txtProductStockStatus, txtProductCurrentPrice;
        private ImageView imgProduct;
        private ImageView star_button;

        public ListViewHolder ( View itemView ) {
            super (itemView);
            layoutProduct = itemView.findViewById (R.id.layout_product);
            imgProduct = itemView.findViewById (R.id.iv_item_image);
            txtProduct = itemView.findViewById (R.id.tv_product);
            txtProductDesc = itemView.findViewById (R.id.tv_product_desc);
            txtProductOldPrice = itemView.findViewById (R.id.textView10);
            txtProductStockStatus = itemView.findViewById (R.id.tv_product_stock_status);
            txtProductCurrentPrice = itemView.findViewById (R.id.tv_item_current_price);
            star_button = itemView.findViewById (R.id.star_button);
        }
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder ( @NonNull ViewGroup parent, int i ) {
        View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.row_product_a, parent, false);
        ListViewHolder evh = new ListViewHolder (v);
        return evh;
    }

    @Override
    public void onBindViewHolder ( @NonNull final ListViewHolder holder, @SuppressLint ("RecyclerView") int position ) {
        NewProductItem data = productList.get (position);
        holder.txtProduct.setText (data.getProduct ());
        holder.txtProductDesc.setText (data.getProduct ());
        holder.txtProductStockStatus.setText (data.getStock ());
        if(data.getStock ().equals ("In Stock"))
            holder.txtProductStockStatus.setTextColor (Color.parseColor ("#28AF2E"));
        else
            holder.txtProductStockStatus.setTextColor (Color.parseColor ("#FF0A2D"));
        int height= Resources.getSystem().getDisplayMetrics().widthPixels/2,width = Resources.getSystem().getDisplayMetrics().widthPixels/2;
        String adas=Const.BASE_URL+"/imageresize.php?crop=true&width="+width+"&height="+height+"&imagepath="+data.getMainImage ();
        Log.v("ImagePath",adas);

        Picasso.with( context )
                .load( adas )
                .error( R.drawable.placeholder )
                .placeholder( R.drawable.image_loader )
                .into( holder.imgProduct );

        if (data.getInWishlist ().equals ("Y"))
            holder.star_button.setImageResource (R.drawable.favorite_like);
        else
            holder.star_button.setImageResource (R.drawable.favorite_unlike);
        int listPrice = (int) data.getListPrice ();
        int BasePrice = (int) data.getBasePrice ();
        if( listPrice<=BasePrice)
        {
            holder.txtProductCurrentPrice.setText(Const.CurrencyValue+BasePrice);
            holder.txtProductOldPrice.setVisibility ( View.GONE );
        }
        else {
            holder.txtProductOldPrice.setText(Const.CurrencyValue +listPrice);
            holder.txtProductCurrentPrice.setText(Const.CurrencyValue+BasePrice);
        }
        holder.star_button.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick ( View v ) {
                if (new LoginPreferences (context).getString ("token") != null) {
                    Position = position;
                    if (data.getInWishlist ().equals ("Y")) {
                        addToWishlist (data.getProductId () + "", "remove");
                    } else {
                        addToWishlist (data.getProductId () + "", "add");
                    }
                } else
                    UtilityMethods.PrintToast (context, context.getResources ().getString (R.string.WishListErrorMessage), 1);
            }
        });
        holder.layoutProduct.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick ( View view ) {
                Log.v ("asdasd", data.getProductId () + "");
                Intent intent = new Intent (context, ProductDetailsActivity.class);
                intent.putExtra ("ProductId", data.getProductId ());
                context.startActivity (intent);
            }
        });
    }
    @Override
    public int getItemCount ( ) {
        return productList.size ();
    }
    private void handleError ( Throwable throwable ) {
        Log.e ("User Data", "negative" + throwable.getMessage ());
    }

    public void addToWishlist ( String product_id, String Status ) {
        mProgressDialog = DialogUtil.getProgressDialog (context, context.getResources ().getString (R.string.app_DialogInfo), context.getResources ().getString (R.string.app_DialogLoading));
        PushCartToLoggedInUserModel pushCartToLoggedInUserModel = new PushCartToLoggedInUserModel ();
        pushCartToLoggedInUserModel.setMethod ("addToWishlist");
        pushCartToLoggedInUserModel.setAction (Status);
        pushCartToLoggedInUserModel.setProductId (product_id);
        Log.v("pushCartToLoggedInUserModel",new Gson ().toJson (pushCartToLoggedInUserModel));
        ApiInterface apiService = ApiClient.getClient (context).create (ApiInterface.class);
        LoginPreferences loginPreferences = new LoginPreferences (context);
        String header = Credentials.basic ("augurstest@gmail.com", context.getResources ().getString (R.string.user_password));
        Single<AddRemoveWishlistModel> userObservable = apiService.addProductItemToWishList (header, "Wishlist2", loginPreferences.getString ("token"), pushCartToLoggedInUserModel);
        userObservable.observeOn (AndroidSchedulers.mainThread ())
                .subscribeOn (Schedulers.io ())
                .subscribe (this :: handleResults, this :: handleError);
    }

    @SuppressLint ("NotifyDataSetChanged")
    private void handleResults ( AddRemoveWishlistModel baseResponse ) {
        mProgressDialog.dismiss ();
        if (baseResponse.getStatus () == 200) {
            new LoginPreferences (context).put (ConstantVariable.CartItem,baseResponse.getData ().getCartCounts ());
            new LoginPreferences (context).put (ConstantVariable.WishListItem,baseResponse.getData ().getWishlistCounts ());
            if (! productList.get (Position).getInWishlist ().equals ("Y")) {
                productList.get (Position).setInWishlist ("Y");
            } else {
                productList.get (Position).setInWishlist ("N");
            }
            ((CategoryListItemActivity)context).UpdateBottomItem( baseResponse.getData ().getCartCounts (),baseResponse.getData ().getWishlistCounts ());
            notifyDataSetChanged ();
        } else
            UtilityMethods.PrintToast (context, baseResponse.getData ().getMessage (), 1);
    }

    @SuppressLint ("NotifyDataSetChanged")
    public void updateList ( ArrayList<NewProductItem> filteredList ) {
        productList = filteredList;
        notifyDataSetChanged ();
    }

    public void filter ( String text ) {
        ArrayList<NewProductItem> filteredList = new ArrayList<> ();
        if (text.isEmpty () || text.length () == 0) {
            filteredList = productListFilter;
        } else {
            for (NewProductItem movie : productListFilter) {
                if (movie.getProduct ().toLowerCase ().contains (text.toLowerCase ())) {
                    filteredList.add (movie);
                }
            }
        }
        updateList (filteredList);
    }


}


