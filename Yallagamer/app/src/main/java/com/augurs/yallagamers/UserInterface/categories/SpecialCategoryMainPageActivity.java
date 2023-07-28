package com.augurs.yallagamers.UserInterface.categories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.augurs.yallagamers.UserInterface.CategoryListItem.CategoryListItemActivity;
import com.augurs.yallagamers.UserInterface.MainActivity;
import com.augurs.yallagamers.R;
import com.augurs.yallagamers.UserInterface.user_login.LoginActivity;
import com.augurs.yallagamers.adapters.RecommendedProducts;
import com.augurs.yallagamers.adapters.StaticCategoryItemAdapter;
import com.augurs.yallagamers.api_module.ApiClient;
import com.augurs.yallagamers.api_module.ApiInterface;
import com.augurs.yallagamers.api_module.Const;
import com.augurs.yallagamers.api_module.DialogUtil;
import com.augurs.yallagamers.data_models.StaticCategoryBaseResponce;
import com.augurs.yallagamers.utills.ConstantVariable;
import com.augurs.yallagamers.utills.LoginPreferences;
import com.augurs.yallagamers.utills.UtilityMethods;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;

public class SpecialCategoryMainPageActivity extends AppCompatActivity {
    RecyclerView recyclarview,recyclarview2;
    ImageView img_back,imgBottomLeftImage,imgBottomRightImage,imgBottomImage,imgTopImage,imgSlider;
    ProgressDialog mProgressDialog;
    View.OnClickListener onClickListener = new View.OnClickListener () {
        @SuppressLint ("NonConstantResourceId")
        @Override
        public void onClick ( View view ) {
            switch (view.getId ()) {
                case R.id.LLHome: {
                    ScreenChange(1);
                    break;
                }
                case R.id.LLExplore: {
                    ScreenChange(2);
                    break;
                }
                case R.id.LLWishList: {
                    ScreenChange(3);
                    break;
                }
                case R.id.LLProfile: {
                    ScreenChange(4);
                    break;
                }
                case R.id.LLCart: {
                    ScreenChange(5);
                    break;
                }
            }
        }
    };
    void ScreenChange(int p){
        if(p==3){
            if (new LoginPreferences (SpecialCategoryMainPageActivity.this).getString ("token") != null) {
                Intent i = new Intent (SpecialCategoryMainPageActivity.this, MainActivity.class);
                i.putExtra ("ScreenStatus",p);
                startActivity (i);
            }
            else {
                UtilityMethods.PrintToast (SpecialCategoryMainPageActivity.this,"Please Login before check your  Wishlist ",1);
            }
        }
        else  if(p==4){
            if (new LoginPreferences (SpecialCategoryMainPageActivity.this).getString ("token") != null) {
                Intent i = new Intent (SpecialCategoryMainPageActivity.this, MainActivity.class);
                i.putExtra ("ScreenStatus",p);
                startActivity (i);
            } else {
                Intent intent = new Intent (SpecialCategoryMainPageActivity.this, LoginActivity.class);
                intent.putExtra ("LoginStatus", 0);
                startActivity (intent);
            }
        }
        else {
            Intent i = new Intent (SpecialCategoryMainPageActivity.this, MainActivity.class);
            i.putExtra ("ScreenStatus",p);
            startActivity (i);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_category_main);
        int count_cart_list=new LoginPreferences(SpecialCategoryMainPageActivity.this).getInt (ConstantVariable.CartItem, 0);
        int count_wish_list=new LoginPreferences(SpecialCategoryMainPageActivity.this).getInt (ConstantVariable.WishListItem, 0);
        TextView TvCartCount = findViewById (R.id.TvCartCount);
        if(count_cart_list!=0){
            TvCartCount.setText (new LoginPreferences (SpecialCategoryMainPageActivity.this).getInt (ConstantVariable.CartItem,0)+"");
            TvCartCount.setVisibility (View.VISIBLE);
        }
        else
            TvCartCount.setVisibility (View.GONE);
        TextView TvWishlistCount = findViewById (R.id.TvWishlistCount);
        if(count_wish_list!=0){
            TvWishlistCount.setText (new LoginPreferences (SpecialCategoryMainPageActivity.this).getInt (ConstantVariable.WishListItem,0)+"");
            TvWishlistCount.setVisibility (View.VISIBLE);
        }
        else
            TvWishlistCount.setVisibility (View.GONE);

        if(new LoginPreferences (SpecialCategoryMainPageActivity.this).getInt (ConstantVariable.ScreenStatus,0)==2)
        {
            ImageView imgCategory = findViewById (R.id.imgCategory);
            imgCategory.setImageResource (R.drawable.categories_light);
            TextView TvCategory= findViewById (R.id.TvCategory);
            TvCategory.setTextColor (Color.parseColor ("#459699"));
        }
        else if(new LoginPreferences (SpecialCategoryMainPageActivity.this).getInt (ConstantVariable.ScreenStatus,0)==3)
        {
            ImageView ImgWishList = findViewById (R.id.ImgWishList);
            ImgWishList.setImageResource (R.drawable.line_heart_light);
            TextView TvWishlist= findViewById (R.id.TvWishlist);
            TvWishlist.setTextColor (Color.parseColor ("#459699"));
        }
        else {
            ImageView imgHome = findViewById (R.id.imgHome);
            imgHome.setImageResource (R.drawable.home_light);
            TextView TvHome= findViewById (R.id.TvHome);
            TvHome.setTextColor (Color.parseColor ("#459699"));
        }

        LinearLayout LLHome= findViewById (R.id.LLHome);
        LinearLayout LLExplore= findViewById (R.id.LLExplore);
        RelativeLayout LLWishList= findViewById (R.id.LLWishList);
        LinearLayout LLProfile= findViewById (R.id.LLProfile);
        RelativeLayout LLCart= findViewById (R.id.LLCart);
        LLHome.setOnClickListener (onClickListener);
        LLExplore.setOnClickListener (onClickListener);
        LLWishList.setOnClickListener (onClickListener);
        LLProfile.setOnClickListener (onClickListener);
        LLCart.setOnClickListener (onClickListener);
        TextView TvProfileLoginText = findViewById (R.id.TvProfileLoginText);
        if (new LoginPreferences (SpecialCategoryMainPageActivity.this).getString ("token") != null)
            TvProfileLoginText.setText ("Profile");
        else
            TvProfileLoginText.setText ("Login");
        recyclarview = findViewById(R.id.recyclarview);
        imgTopImage= findViewById ( R.id.imgTopImage );
        imgBottomLeftImage= findViewById ( R.id.imgBottomLeftImage );
         imgBottomRightImage= findViewById ( R.id.imgBottomRightImage );
        imgSlider= findViewById ( R.id.imgSlider );
        imgBottomImage= findViewById ( R.id.imgBottomImage );
        recyclarview.setLayoutManager(new GridLayoutManager (SpecialCategoryMainPageActivity.this, 2));
        recyclarview2 = findViewById(R.id.recyclarview2);
        recyclarview2.setLayoutManager(new LinearLayoutManager(SpecialCategoryMainPageActivity.this,LinearLayoutManager.HORIZONTAL,false));
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        TextView TvText= findViewById ( R.id.TvText );
        TvText.setText (getIntent ().getStringExtra ( "Name" )  );
        CategoryPageData();
    }

    private void CategoryPageData ( ) {
        mProgressDialog = DialogUtil.getProgressDialog (SpecialCategoryMainPageActivity.this, getResources ( ).getString ( R.string.app_DialogInfo ), getResources ( ).getString ( R.string.app_DialogLoading ) );
        String header= Credentials.basic(getResources().getString(R.string.user_id), getResources().getString(R.string.user_password));
        ApiInterface apiService = ApiClient.getClient(SpecialCategoryMainPageActivity.this).create(ApiInterface.class);
        Single < StaticCategoryBaseResponce > hpObservable = apiService.GetStaticCategory(header,"Homepage","getDetailsBypage",getIntent ().getStringExtra ( "Name" ));
        hpObservable.observeOn( AndroidSchedulers.mainThread()).subscribeOn( Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this::handleResultsHP,this::handleErrorHP);
    }

    private void handleResultsHP ( StaticCategoryBaseResponce staticCategoryBaseResponce ) {
            mProgressDialog.dismiss ( );
            if(staticCategoryBaseResponce.getStatus ()==200) {
                recyclarview2.setAdapter ( new RecommendedProducts ( SpecialCategoryMainPageActivity.this , staticCategoryBaseResponce.getData ( ).getRecommendedForYou ( ) ) );
                recyclarview.setAdapter ( new StaticCategoryItemAdapter ( SpecialCategoryMainPageActivity.this , staticCategoryBaseResponce.getData ( ).getBox ( ) ) );
                if(staticCategoryBaseResponce.getData ( ).getBannerImages ( ).size ()>0&&staticCategoryBaseResponce.getData ( ).getBannerImages ( ).get ( 0 ) != null ){
                    Glide.with ( SpecialCategoryMainPageActivity.this).load (
                            Const.BASE_URL+"/imageresize.php?width=" + Resources.getSystem ( ).getDisplayMetrics ( ).widthPixels + "&height=" + 200 * getResources().getDisplayMetrics().density + "&imagepath=" + staticCategoryBaseResponce.getData ( ).getBannerImages ( ).get ( 0 ).getAsString ()
                    ) .diskCacheStrategy(DiskCacheStrategy.ALL).apply ( RequestOptions.bitmapTransform ( new RoundedCorners ( 5 ) ) ).placeholder ( R.drawable.placeholder ).into ( imgSlider );
                }
                if(staticCategoryBaseResponce.getData ( ).getBannerImages ( ).size ()>1&& staticCategoryBaseResponce.getData ( ).getBannerImages ( ).get ( 1) != null ) {
                    Glide.with ( SpecialCategoryMainPageActivity.this ).load ( Const.BASE_URL+"/imageresize.php?width=" + (Resources.getSystem ( ).getDisplayMetrics ( ).widthPixels) + "&height=" + 80 * getResources().getDisplayMetrics().density + "&imagepath=" + staticCategoryBaseResponce.getData ( ).getBannerImages ( ).get (1).getAsString ()
                    ).diskCacheStrategy(DiskCacheStrategy.ALL).apply ( RequestOptions.bitmapTransform ( new RoundedCorners ( 5 ) ) ).placeholder ( R.drawable.placeholder ).into ( imgTopImage );
                    imgTopImage.setVisibility ( View.VISIBLE );
                }
                if(staticCategoryBaseResponce.getData ( ).getBannerImages ( ).size ()>2&& staticCategoryBaseResponce.getData ( ).getBannerImages ( ).get ( 2 ) != null ) {
                    Glide.with ( SpecialCategoryMainPageActivity.this ).load (
                            Const.BASE_URL+"/imageresize.php?width=" + (Resources.getSystem ( ).getDisplayMetrics ( ).widthPixels/2) + "&height=" + 200 * getResources().getDisplayMetrics().density + "&imagepath=" + staticCategoryBaseResponce.getData ( ).getBannerImages ( ).get (2).getAsString ()
                    ).diskCacheStrategy(DiskCacheStrategy.ALL).apply ( RequestOptions.bitmapTransform ( new RoundedCorners ( 5 ) ) ).placeholder ( R.drawable.placeholder ).into ( imgBottomLeftImage );
                    imgBottomLeftImage.setVisibility ( View.VISIBLE );
                }
                if(staticCategoryBaseResponce.getData ( ).getBannerImages ( ).size ()>3&&staticCategoryBaseResponce.getData ( ).getBannerImages ( ).get ( 3 ) != null ) {
                    Glide.with ( SpecialCategoryMainPageActivity.this ).load (
                            Const.BASE_URL+"/imageresize.php?width=" + (Resources.getSystem ( ).getDisplayMetrics ( ).widthPixels/2) + "&height=" + 200 * getResources().getDisplayMetrics().density + "&imagepath=" + staticCategoryBaseResponce.getData ( ).getBannerImages ( ).get (3).getAsString ()
                    ).diskCacheStrategy(DiskCacheStrategy.ALL).apply ( RequestOptions.bitmapTransform ( new RoundedCorners ( 5 ) ) ).placeholder ( R.drawable.placeholder ).into ( imgBottomRightImage );
                    imgBottomRightImage.setVisibility ( View.VISIBLE );
                }
                if(staticCategoryBaseResponce.getData ( ).getBannerImages ( ).size ()>4&& staticCategoryBaseResponce.getData ( ).getBannerImages ( ).get ( 4 ) != null ) {
                    Glide.with ( SpecialCategoryMainPageActivity.this ).load (
                            Const.BASE_URL+"/imageresize.php?width=" + (Resources.getSystem ( ).getDisplayMetrics ( ).widthPixels) + "&height=" + 80 * getResources().getDisplayMetrics().density + "&imagepath=" + staticCategoryBaseResponce.getData ( ).getBannerImages ( ).get (4).getAsString ()
                    ).diskCacheStrategy(DiskCacheStrategy.ALL).apply ( RequestOptions.bitmapTransform ( new RoundedCorners ( 5 ) ) ).placeholder ( R.drawable.placeholder ).into ( imgBottomImage );
                    imgBottomImage.setVisibility ( View.VISIBLE );
                }
            }
            else {
                Toast.makeText ( SpecialCategoryMainPageActivity.this,staticCategoryBaseResponce.getMessage (),Toast.LENGTH_LONG ).show ();
            }
    }
    private void handleErrorHP(Throwable throwable) {
        mProgressDialog.dismiss ( );
        Log.e("HP","it does not work."+throwable.getMessage ());
    }
}