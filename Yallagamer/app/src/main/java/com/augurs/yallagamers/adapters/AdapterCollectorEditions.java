package com.augurs.yallagamers.adapters;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.augurs.yallagamers.UserInterface.MainActivity;
import com.augurs.yallagamers.api_module.ApiClient;
import com.augurs.yallagamers.api_module.ApiInterface;
import com.augurs.yallagamers.api_module.Const;
import com.augurs.yallagamers.api_module.DialogUtil;
import com.augurs.yallagamers.data_models.AddRemoveWishlistModel;
import com.augurs.yallagamers.data_models.Collectors_Editions;
import com.augurs.yallagamers.UserInterface.ProductDetails.ProductDetailsActivity;
import com.augurs.yallagamers.R;
import com.augurs.yallagamers.data_models.PushCartToLoggedInUserModel;
import com.augurs.yallagamers.utills.ConstantVariable;
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

public class AdapterCollectorEditions extends RecyclerView.Adapter<AdapterCollectorEditions.ListViewHolder> {

    private Context context;
    private  int Position=0;
    private ArrayList<Collectors_Editions> collectorsEditionsList;
    private ProgressDialog mProgressDialog;

    private int Type=1;

    public void addListArray(ArrayList<Collectors_Editions> itemList)
    {
        this.collectorsEditionsList = itemList;
        notifyDataSetChanged();
    }
    public AdapterCollectorEditions(Context context, ArrayList<Collectors_Editions> collectorsEditionsList,int Type) {
        this.context = context;
        this.Type =Type;
        this.collectorsEditionsList = collectorsEditionsList;
    }
    public static class ListViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout layoutProduct;
        private TextView txtProduct,txtBrand,txtProductOldPrice,txtProductCurrentPrice;
        private ImageView imgProduct,star_button;
        public ListViewHolder(View itemView) {
            super(itemView);
            layoutProduct = itemView.findViewById(R.id.layout_product);
            txtProduct = itemView.findViewById(R.id.tv_item_name);
            txtBrand = itemView.findViewById(R.id.tv_item_brand);
            imgProduct = itemView.findViewById(R.id.iv_item_image);
            star_button= itemView.findViewById (R.id.star_button);
            txtProductOldPrice = itemView.findViewById(R.id.textView10);
            txtProductCurrentPrice = itemView.findViewById(R.id.tv_item_current_price);
        }
    }
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        ListViewHolder evh = new ListViewHolder(v);
        return evh;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder,  int position) {
        final Collectors_Editions data= collectorsEditionsList.get(position);
        if(Type==1){
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int)(Resources.getSystem().getDisplayMetrics().widthPixels/3),
                    LinearLayout.LayoutParams.WRAP_CONTENT); // or set height to any fixed value you want
            holder.layoutProduct.setLayoutParams(lp);
            lp.setMargins (10,5,10,5);
        }
        holder.txtProduct.setText(data.getPage_title());
        if(data.getIn_wishlist ().equals ( "Y" ))
            holder.star_button.setImageResource (R.drawable.favorite_like);
        else
            holder.star_button.setImageResource (R.drawable.favorite_unlike);
        int listPrice = (int) data.getList_price ();
        int BasePrice = (int) data.getBase_price ();
        if( listPrice<=BasePrice)
        {
            holder.txtProductCurrentPrice.setText(Const.CurrencyValue+BasePrice);
            holder.txtProductOldPrice.setVisibility ( View.GONE );
        }
        else {
            holder.txtProductOldPrice.setText(Const.CurrencyValue +listPrice);
            holder.txtProductCurrentPrice.setText(Const.CurrencyValue+BasePrice);
        }
        int height=Resources.getSystem().getDisplayMetrics().widthPixels/3,width = Resources.getSystem().getDisplayMetrics().widthPixels/3;
        String adas=Const.BASE_URL+"imageresize.php?&width="+width+"&height="+height+"&imagepath="+data.getImage_path();
        Log.v("ImagePath",adas);

        Glide.with(this.context).load(adas).into(holder.imgProduct);
        holder.txtBrand.setText ( data.getCategory () );
        holder.layoutProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("ProductId",data.getProduct_id());
                context.startActivity(intent);
            }
        });
        holder.star_button.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick ( View v ) {
                if(new LoginPreferences ( context).getString ( "token" )!= null) {
                    Position = position;
                    if (data.getIn_wishlist ().equals ("Y")) {
                        addToWishlist (data.getProduct_id () + "", "remove");
                    } else {
                        addToWishlist (data.getProduct_id () + "", "add");
                    }
                }
                else
                    UtilityMethods.PrintToast (context,context.getResources ().getString (R.string.WishListErrorMessage),1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return collectorsEditionsList.size();
    }

    public void addToWishlist ( String product_id, String Status ) {
        mProgressDialog = DialogUtil.getProgressDialog ( context , context.getResources ( ).getString ( R.string.app_DialogInfo ) , context.getResources ( ).getString ( R.string.app_DialogLoading ) );
        PushCartToLoggedInUserModel pushCartToLoggedInUserModel = new PushCartToLoggedInUserModel ();
        pushCartToLoggedInUserModel.setMethod ("addToWishlist");
        pushCartToLoggedInUserModel.setAction (Status);
        pushCartToLoggedInUserModel.setProductId (product_id);
        ApiInterface apiService = ApiClient.getClient (context).create (ApiInterface.class);
        LoginPreferences loginPreferences = new LoginPreferences (context);
        String header = Credentials.basic ("augurstest@gmail.com", context.getResources ().getString (R.string.user_password));
        Single<AddRemoveWishlistModel> userObservable = apiService.addProductItemToWishList (header, "Wishlist2", loginPreferences.getString ("token"), pushCartToLoggedInUserModel);
        userObservable.observeOn (AndroidSchedulers.mainThread ())
                .subscribeOn (Schedulers.io ())
                .subscribe (this :: handleResults, this :: handleError);

    }
    private void handleResults ( AddRemoveWishlistModel baseResponse ) {
        mProgressDialog.dismiss ();
        if (baseResponse.getStatus () == 200) {
            new LoginPreferences (context).put (ConstantVariable.CartItem, baseResponse.getData().getCartCounts ());
            new LoginPreferences (context).put (ConstantVariable.WishListItem,baseResponse.getData().getWishlistCounts());
            ((MainActivity)context).UpdateStatusCartItem(baseResponse.getData().getCartCounts(),baseResponse.getData().getWishlistCounts());
            if(!collectorsEditionsList.get (Position).getIn_wishlist ().equals ( "Y" ))
                collectorsEditionsList.get (Position).setIn_wishlist ("Y");
            else
                collectorsEditionsList.get (Position).setIn_wishlist ("N");
            notifyDataSetChanged ();
        }else
            UtilityMethods.PrintToast(context, baseResponse.getData().getMessage(), 1);
    }

    private void handleError ( Throwable throwable ) {
        mProgressDialog.dismiss ();
        Log.e ("User Data", "negative" + throwable.getMessage ());
    }
}
