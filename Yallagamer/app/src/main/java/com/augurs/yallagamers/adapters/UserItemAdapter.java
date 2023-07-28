package com.augurs.yallagamers.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.recyclerview.widget.RecyclerView;

import com.augurs.yallagamers.UserInterface.MainActivity;
import com.augurs.yallagamers.R;
import com.augurs.yallagamers.UserInterface.ProductDetails.ProductDetailsActivity;
import com.augurs.yallagamers.api_module.ApiClient;
import com.augurs.yallagamers.api_module.ApiInterface;
import com.augurs.yallagamers.api_module.Const;
import com.augurs.yallagamers.api_module.DialogUtil;
import com.augurs.yallagamers.data_models.AddRemoveWishlistModel;
import com.augurs.yallagamers.data_models.Product;
import com.augurs.yallagamers.data_models.ProductDetailBaseModel;
import com.augurs.yallagamers.data_models.PushCartToLoggedInUserModel;
import com.augurs.yallagamers.UserInterface.bag.ItemRemoveorAddMultiple;
import com.augurs.yallagamers.utills.ConstantVariable;
import com.augurs.yallagamers.utills.LoginPreferences;
import com.augurs.yallagamers.utills.UtilityMethods;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import java.util.ArrayList;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;

public class UserItemAdapter extends RecyclerView.Adapter<UserItemAdapter.ListViewHolder> {
    private Context context;
    private ArrayList<Product> categoryList;
    private ProgressDialog mProgressDialog;
    private final ItemRemoveorAddMultiple itemRemoveorAddMultiple;
    public UserItemAdapter ( Context context, ArrayList<Product> categoryList, ItemRemoveorAddMultiple itemRemoveorAddMultiple ) {
        this.context = context;
        this.categoryList = categoryList;
        this.itemRemoveorAddMultiple = itemRemoveorAddMultiple;
    }

    public void addListArray ( ArrayList<Product> categoryList ) {
        this.categoryList = categoryList;
        notifyDataSetChanged ();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView TvName, TvName1, TvDielievery, TvAmount, TvSOldBy, TvRemove, TvWishList, TvQty,TvSize;
        CheckBox checkbox;

        public ListViewHolder ( View itemView ) {
            super (itemView);
            checkbox = itemView.findViewById (R.id.checkbox);
            img = itemView.findViewById (R.id.img);
            TvName = itemView.findViewById (R.id.TvName);
            TvName1 = itemView.findViewById (R.id.TvName1);
            TvDielievery = itemView.findViewById (R.id.TvDielievery);
            TvAmount = itemView.findViewById (R.id.TvAmount);
            TvSOldBy = itemView.findViewById (R.id.TvSOldBy);
            TvRemove = itemView.findViewById (R.id.TvRemove);
            TvWishList = itemView.findViewById (R.id.TvWishList);
            TvQty = itemView.findViewById (R.id.TvQty);
            TvSize= itemView.findViewById (R.id.TvSize);
        }
    }


    @Override
    public ListViewHolder onCreateViewHolder ( @NonNull ViewGroup parent, int i ) {
        View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.row_shoppig_bag_item, parent, false);
        ListViewHolder evh = new ListViewHolder (v);
        return evh;
    }

    @Override
    public void onBindViewHolder ( @NonNull final ListViewHolder holder, int position ) {
        Product data = categoryList.get (position);
        holder.TvSize.setText ("Size : --");
        holder.checkbox.setChecked (data.isCheckboxStatus ());
        Glide.with (context).load (data.getMainImage ()).placeholder (R.drawable.placeholder).into (holder.img);
        holder.TvName.setText (data.getProduct ());
        holder.TvName1.setText (data.getMainCategory ());
        if (data.getEstimated_delivery_date () != null) {
            String text = "<font color=#d3d3d3>Delivery By</font><B> <font color=#000000>" + data.getEstimated_delivery_date () + "</font></B>";
            holder.TvDielievery.setText (Html.fromHtml (text));
        } else
            holder.TvDielievery.setVisibility (View.GONE);
        holder.img.setOnClickListener (v -> {
            Intent i = new Intent (context, ProductDetailsActivity.class);
            i.putExtra ("ProductId",data.getProduct_id ());
            context.startActivity (i);
        });
        holder.TvAmount.setText (Html.fromHtml ("<font color=#000000>Amount   "+Const.CurrencyValue+  data.getPrice () + " </font></B>") + "");
        holder.TvSOldBy.setText ("Sold By " + data.getMainCategory ());
        holder.TvQty.setText ("Quantity. " + data.getAmount () + "");
        holder.TvWishList.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick ( View v ) {
                if(new LoginPreferences ( context).getString ( "token" )!= null)
                        addToWishlist (data.getProduct_id () + "", "add");
                else
                    UtilityMethods.PrintToast (context,context.getResources ().getString (R.string.WishListErrorMessage),1);
            }
        });
        holder.TvRemove.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick ( View v ) {
                PushCartToLoggedInUserModel pushCartToLoggedInUserModel = new PushCartToLoggedInUserModel ();
                pushCartToLoggedInUserModel.setMethod ("deleteCartProduct");
                if (new LoginPreferences (context).getString ("token") == null)
                    pushCartToLoggedInUserModel.setCartId (new LoginPreferences (context).getString ("cart_id"));
                pushCartToLoggedInUserModel.setProduct_cart_id (data.getProduct_cart_id ());
                mProgressDialog = DialogUtil.getProgressDialog (context, context.getResources ().getString (R.string.app_DialogInfo), context.getResources ().getString (R.string.app_DialogLoading));
                ApiInterface apiService = ApiClient.getClient (context).create (ApiInterface.class);
                String header= Credentials.basic( context.getResources().getString(R.string.user_id), context.getResources().getString(R.string.user_password));
                Single<ProductDetailBaseModel> catObservable;
                if (new LoginPreferences (context).getString ("token") != null)
                    catObservable = apiService.RemoveoneByOneProduct (header, "Cart", new LoginPreferences (context).getString ("token"), pushCartToLoggedInUserModel);
                else
                    catObservable = apiService.RemoveoneByOneProduct (header, "Cart", pushCartToLoggedInUserModel,new LoginPreferences (context).getString ("cart_id"));
                catObservable.observeOn (AndroidSchedulers.mainThread ()).subscribeOn (Schedulers.io ()).observeOn (AndroidSchedulers.mainThread ()).subscribe (this :: handleResultsCE, this :: handleErrorCE);
            }

            private void handleErrorCE ( Throwable throwable ) {
                mProgressDialog.dismiss ();
                Log.e ("CE", "it does not work." + new Gson ().toJson (throwable));
                Toast.makeText (context, throwable.getMessage (), Toast.LENGTH_LONG).show ();
            }

            private void handleResultsCE ( ProductDetailBaseModel baseResponse ) {
                mProgressDialog.dismiss ();
                if (baseResponse.getStatus () == 200) {
                    new LoginPreferences (context).put (ConstantVariable.CartItem, baseResponse.getData().getCartCounts ());
                    new LoginPreferences (context).put (ConstantVariable.WishListItem,baseResponse.getData().getWishlistCounts());
                    ((MainActivity)context).UpdateStatusCartItem(baseResponse.getData().getCartCounts(),baseResponse.getData().getWishlistCounts());
                    Intent i = new Intent (context, MainActivity.class);
                    i.putExtra ("ScreenStatus",5);
                    context.startActivity (i);
                    ((MainActivity) context).finish ();
                }
                Toast.makeText (context, baseResponse.getData ().getMessage (), Toast.LENGTH_LONG).show ();
            }
        });
        holder.checkbox.setOnCheckedChangeListener (( buttonView, isChecked ) -> {
                    itemRemoveorAddMultiple.AddRemoveItem (data.getProduct_cart_id ());
                    data.setCheckboxStatus (isChecked);
                }
        );
        holder.TvQty.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick ( View v ) {
                if (data.getMax_qty () != 0) {
                    OpenDialogueQUantity (data.getMax_qty (), holder.TvQty, data.getProduct_cart_id (),data.getAmount ());
                }
            }
        });
    }
    private void OpenDialogueQUantity ( int PreviousQuantity, TextView textView, String ProductCartId,int amount ) {
        ArrayList<Integer> integerArrayList = new ArrayList<> ();
        for (int i = 1; i <= PreviousQuantity; i++)
            integerArrayList.add (i);
        ListPopupWindow listPopupWindow = new ListPopupWindow (context);
        listPopupWindow.setAdapter (new ArrayAdapter (context, android.R.layout.simple_spinner_dropdown_item, integerArrayList));
        listPopupWindow.setAnchorView (textView);
        listPopupWindow.setWidth (ListPopupWindow.WRAP_CONTENT);
        listPopupWindow.setHeight (ListPopupWindow.WRAP_CONTENT);
        listPopupWindow.setModal (true);
        listPopupWindow.setOnItemClickListener (( adapterView, view, i, l ) -> {
            if(integerArrayList.get (i)!=amount)
                itemRemoveorAddMultiple.QuantityUpdate (ProductCartId, integerArrayList.get (i));
                listPopupWindow.dismiss ();
        });
        listPopupWindow.show ();
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
            UtilityMethods.PrintToast(context, baseResponse.getData().getMessage(), 0);
            new LoginPreferences (context).put (ConstantVariable.CartItem, baseResponse.getData().getCartCounts ());
            new LoginPreferences (context).put (ConstantVariable.WishListItem,baseResponse.getData().getWishlistCounts());
            ((MainActivity)context).UpdateStatusCartItem(baseResponse.getData().getCartCounts(),baseResponse.getData().getWishlistCounts());
        }else
            UtilityMethods.PrintToast(context, baseResponse.getData().getMessage(), 1);
    }

    private void handleError ( Throwable throwable ) {
        mProgressDialog.dismiss ();
        Log.e ("User Data", "negative" + throwable.getMessage ());
    }
    @Override
    public int getItemCount () {
        return categoryList.size ();
    }
}
