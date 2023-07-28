package com.augurs.yallagamers.UserInterface.wishlist;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.augurs.yallagamers.UserInterface.MainActivity;
import com.augurs.yallagamers.R;
import com.augurs.yallagamers.adapters.AdapterWishlistProducts;
import com.augurs.yallagamers.api_module.ApiClient;
import com.augurs.yallagamers.api_module.ApiInterface;
import com.augurs.yallagamers.api_module.DialogUtil;
import com.augurs.yallagamers.data_models.WishList;
import com.augurs.yallagamers.utills.ConstantVariable;
import com.augurs.yallagamers.utills.LoginPreferences;

import java.util.ArrayList;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;

public class WishlistFragment extends Fragment {
    ProgressDialog mProgressDialog;
    AdapterWishlistProducts adapterCategoryProducts;
    RecyclerView rvProductList;
    ArrayList<WishList.Datum> productList=new ArrayList<>();
    TextView TvTextCountCart;
    RelativeLayout  RlItemCart;
    TextView tv_cat_items;
    RelativeLayout RlEmptyLayout;
    public static WishlistFragment newInstance ( ) {
        return new WishlistFragment ( );
    }

    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_wishlist, container, false);
        RlEmptyLayout=root.findViewById (R.id.RlEmptyLayout);
        ImageView img_back = root.findViewById (R.id.img_back);
        tv_cat_items = root.findViewById ( R.id.tv_cat_items );
        img_back.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick ( View v ) {
            }
        });
        TextView TvHeader = root.findViewById ( R.id.TvHeader );
        TvHeader.setText ( "WishList" );
        rvProductList=root.findViewById(R.id.rv_product_list);
        adapterCategoryProducts=new AdapterWishlistProducts(getActivity (),productList);
        rvProductList.setHasFixedSize(true);
        rvProductList.setLayoutManager( new GridLayoutManager (getActivity (), 2));
        rvProductList.setAdapter(adapterCategoryProducts);
        getProductList();
        return root;
    }
    private void getProductList(){
        LoginPreferences loginPreferences = new LoginPreferences(getActivity ());
        String token = loginPreferences.getString("token");
        ApiInterface apiService = ApiClient.getClient(getActivity ()).create(ApiInterface.class);
        mProgressDialog = DialogUtil.getProgressDialog(getActivity (), getResources().getString(R.string.app_DialogInfo), getResources().getString(R.string.app_DialogLoading));
        String header= Credentials.basic(getResources().getString(R.string.user_id), getResources().getString(R.string.user_password));
        Single<WishList> userObservable = apiService.getWishlist(header,"Wishlist2","getFromWishlist",token);
        userObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this::handleResults,this::handleError);
    }

    @SuppressLint ("SetTextI18n")
    private void handleResults( WishList wishList) {
        mProgressDialog.dismiss();
        if(wishList.getStatus ()==200) {
            if ( wishList.getData ( ) != null && wishList.getData ( ).size ( ) > 0 ) {
                tv_cat_items.setText (wishList.getData ( ).size ( ) +" item");
                adapterCategoryProducts.addListArray (wishList.getData ());
                rvProductList.setVisibility (View.VISIBLE);
                new LoginPreferences (getContext ()).put (ConstantVariable.CartItem, wishList.getData ().get (0).getCartCounts ());
                new LoginPreferences (getContext ()).put (ConstantVariable.WishListItem, wishList.getData ().get (0).getWishlistCounts ());
                ((MainActivity)getActivity ()).UpdateStatusCartItem (wishList.getData ().get (0).getCartCounts (),wishList.getData ().get (0).getWishlistCounts ());
            }
            else
                RlEmptyLayout.setVisibility (View.VISIBLE);
        }
        else
            Toast.makeText ( getActivity (),wishList.getMessage (),Toast.LENGTH_LONG ).show ();
    }
    private void handleError(Throwable throwable) {
        mProgressDialog.dismiss();
        Toast.makeText ( getActivity (),throwable.getMessage (),Toast.LENGTH_LONG ).show ();
        Log.e("User Data","negative"+throwable.getMessage());
    }
}