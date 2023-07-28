package com.augurs.yallagamers.UserInterface.CategoryListItem;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.augurs.yallagamers.UserInterface.MainActivity;
import com.augurs.yallagamers.R;
import com.augurs.yallagamers.UserInterface.ProductDetails.ProductDetailsActivity;
import com.augurs.yallagamers.UserInterface.user_login.LoginActivity;
import com.augurs.yallagamers.adapters.AdapterCategoryProducts;
import com.augurs.yallagamers.data_models.CategoryProductBaseModel;
import com.augurs.yallagamers.data_models.NewProductItem;
import com.augurs.yallagamers.api_module.ApiClient;
import com.augurs.yallagamers.api_module.ApiInterface;
import com.augurs.yallagamers.api_module.DialogUtil;
import com.augurs.yallagamers.UserInterface.wishlist.WishlistFragment;
import com.augurs.yallagamers.utills.ConstantVariable;
import com.augurs.yallagamers.utills.LoginPreferences;
import com.augurs.yallagamers.utills.UtilityMethods;

import java.util.ArrayList;
import java.util.Collections;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;

public class CategoryListItemActivity extends AppCompatActivity {
    TextView navBar_Title, navBar_Items;
    ImageView navBar_Back;
    RecyclerView rvProductList;
    AdapterCategoryProducts adapterCategoryProducts;
    boolean check_ScrollingUp = false;
    EditText searchProduct;
    View searchFilter;
    LinearLayout searchFilterLay;
    Context context;
    int page = 1;
    ArrayList<NewProductItem> productList;
    ImageView img_filter_icon;
    Boolean filter_calling = false;
    ProgressDialog mProgressDialog;
    ImageView ImgWishList;

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
        if (new LoginPreferences (CategoryListItemActivity.this).getString ("token") != null) {
            Intent i = new Intent (CategoryListItemActivity.this, MainActivity.class);
            i.putExtra ("ScreenStatus",p);
            startActivity (i);
        }
        else {
            UtilityMethods.PrintToast (CategoryListItemActivity.this,"Please Login before check your  Wishlist ",1);
        }
    }
    else  if(p==4){
        if (new LoginPreferences (CategoryListItemActivity.this).getString ("token") != null) {
            Intent i = new Intent (CategoryListItemActivity.this, MainActivity.class);
            i.putExtra ("ScreenStatus",p);
            startActivity (i);
        } else {
            Intent intent = new Intent (CategoryListItemActivity.this, LoginActivity.class);
            intent.putExtra ("LoginStatus", 0);
            startActivity (intent);
        }
    }
    else {
        Intent i = new Intent (CategoryListItemActivity.this, MainActivity.class);
        i.putExtra ("ScreenStatus",p);
        startActivity (i);
    }
    }
    @SuppressLint ("SetTextI18n")
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_products);
        SwipeRefreshLayout swipe=findViewById ( R.id.swipe );
        swipe.setOnRefreshListener ( ( ) -> {
              swipe.setRefreshing(false);
            mProgressDialog = DialogUtil.getProgressDialog ( CategoryListItemActivity.this, getResources ( ).getString ( R.string.app_DialogInfo ) , getResources ( ).getString ( R.string.app_DialogLoading ) );
            new CountDownTimer (3000, 1000) {
                public void onTick(long millisUntilFinished) {
                }
                public void onFinish() {
                    mProgressDialog.dismiss ( );
                }
            }.start();
        } );

        UpdateBottomItem(new LoginPreferences(CategoryListItemActivity.this).getInt (ConstantVariable.CartItem, 0),new LoginPreferences(CategoryListItemActivity.this).getInt (ConstantVariable.WishListItem, 0));
        TextView TvProfileLoginText = findViewById (R.id.TvProfileLoginText);
        if (new LoginPreferences (CategoryListItemActivity.this).getString ("token") != null)
            TvProfileLoginText.setText ("Profile");
        else
            TvProfileLoginText.setText ("Login");
        Log.v("ScreenData",new LoginPreferences (CategoryListItemActivity.this).getInt (ConstantVariable.ScreenStatus,0)+"");
        if(new LoginPreferences (CategoryListItemActivity.this).getInt (ConstantVariable.ScreenStatus,0)==2)
        {
            ImageView imgCategory = findViewById (R.id.imgCategory);
            imgCategory.setImageResource (R.drawable.categories_light);
            TextView TvCategory= findViewById (R.id.TvCategory);
            TvCategory.setTextColor (Color.parseColor ("#777A6D"));
        }
        else if(new LoginPreferences (CategoryListItemActivity.this).getInt (ConstantVariable.ScreenStatus,0)==3)
        {
            ImageView ImgWishList = findViewById (R.id.ImgWishList);
            ImgWishList.setImageResource (R.drawable.line_heart_light);
            TextView TvWishlist= findViewById (R.id.TvWishlist);
            TvWishlist.setTextColor (Color.parseColor ("#777A6D"));
        }
        else {
            ImageView imgHome = findViewById (R.id.imgHome);
            imgHome.setImageResource (R.drawable.home_light);
            TextView TvHome= findViewById (R.id.TvHome);
            TvHome.setTextColor (Color.parseColor ("#777A6D"));
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
        context = CategoryListItemActivity.this;
        productList = new ArrayList<>();
        navBar_Title = findViewById(R.id.tv_cat_title);
        if(getIntent().getStringExtra("category")!=null)
            navBar_Title.setText(getIntent().getStringExtra("category"));
        else
            navBar_Title.setText(getIntent().getStringExtra(ConstantVariable.SearchText));
        getProductList();
        ImgWishList = findViewById(R.id.ImgWishList);
        ImgWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick ( View v ) {
                if (new LoginPreferences(CategoryListItemActivity.this).getString("token") != null)
                    startActivity(new Intent(CategoryListItemActivity.this, WishlistFragment.class));
                 else
                     UtilityMethods.PrintToast (CategoryListItemActivity.this, "Please Login before add the Product in wishlist", 1);
            }
        });

        navBar_Items = findViewById(R.id.tv_cat_items);
        navBar_Back = findViewById(R.id.img_back);
        navBar_Back.setOnClickListener(viewClickListener);
        searchProduct = findViewById(R.id.et_search_box);
        img_filter_icon = findViewById(R.id.img_filter_icon);
        img_filter_icon.setOnClickListener(viewClickListener);
        ImageView imageView8 = findViewById(R.id.imageView8);
        imageView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick ( View v ) {
                searchProduct.setText("");
                adapterCategoryProducts.filter("");
            }
        });
        searchProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged ( CharSequence charSequence, int i, int i1, int i2 ) {
            }

            @Override
            public void onTextChanged ( CharSequence charSequence, int i, int i1, int i2 ) {
                adapterCategoryProducts.filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged ( Editable editable ) {
            }
        });
        searchFilter = findViewById(R.id.lay_filter);
        searchFilterLay = findViewById(R.id.layout_filter_search);
        rvProductList = findViewById(R.id.rv_product_list);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(CategoryListItemActivity.this, 2);
        rvProductList.setLayoutManager(mLayoutManager);
        rvProductList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled ( RecyclerView recyclerView, int dx, int dy ) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                     if (check_ScrollingUp && dy > 150) {
                        searchFilter.startAnimation(AnimationUtils.loadAnimation(context, R.anim.trans_upwards));
                        check_ScrollingUp = false;
                        //searchFilterLay.setVisibility(View.GONE);
                        searchFilterLay.animate()
                                .alpha(0f)
                                .setDuration(200)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd ( Animator animation ) {
                                        searchFilterLay.setVisibility(View.GONE);
                                    }
                                });
                    }
                } else {
                     if (! check_ScrollingUp) {
                        searchFilter.startAnimation(AnimationUtils.loadAnimation(context, R.anim.trans_downwards));
                        check_ScrollingUp = true;
                        searchFilterLay.animate().alpha(1f).setDuration(200)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd ( Animator animation ) {
                                        searchFilterLay.setVisibility(View.VISIBLE);
                                    }
                                });
                    }
                }
            }

            @Override
            public void onScrollStateChanged ( RecyclerView recyclerView, int newState ) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        rvProductList.setHasFixedSize(true);
        rvProductList.setItemAnimator(new DefaultItemAnimator());
        adapterCategoryProducts = new AdapterCategoryProducts(context, productList);
        rvProductList.setAdapter(adapterCategoryProducts);
    }

    public void UpdateBottomItem ( int anInt, int anInt1 ) {
        TextView TvCartCount = findViewById (R.id.TvCartCount);
        if(anInt!=0){
            TvCartCount.setText (anInt+"");
            TvCartCount.setVisibility (View.VISIBLE);
        }
        else
            TvCartCount.setVisibility (View.GONE);
        TextView TvWishlistCount = findViewById (R.id.TvWishlistCount);
        if(anInt1!=0){
            TvWishlistCount.setText (anInt1+"");
            TvWishlistCount.setVisibility (View.VISIBLE);
        }
        else
            TvWishlistCount.setVisibility (View.GONE);
    }

    private final View.OnClickListener viewClickListener = new View.OnClickListener() {
        @SuppressLint ("NonConstantResourceId")
        @RequiresApi (api = Build.VERSION_CODES.N)
        @Override
        public void onClick ( View v ) {
            switch (v.getId()) {
                case R.id.img_back:
                    onBackPressed ();
                    break;
                case R.id.img_filter_icon: {
                    final String[] listDesc = {"Price High to Low", "Price Low to High", "Name A to Z", "Name Z to A"};
                    final String[] listId = {"1", "2", "3", "4"};
                    DialogUtil.showDialogList (context, "Sort By:-", listDesc, listId, ( retID, retValue ) -> {
                        switch (retID) {
                            case "1":
                                Collections.sort(productList, ( m1, m2 ) -> (int) (m2.getListPrice() - m1.getListPrice()));
                                adapterCategoryProducts.updateList(productList);
                                break;
                            case "2":
                                Collections.sort(productList, ( m1, m2 ) -> (int) (m1.getListPrice() - m2.getListPrice()));
                                adapterCategoryProducts.updateList(productList);
                                break;
                            case "3":
                                Collections.sort(productList, ( m1, m2 ) -> m1.getProduct().compareTo(m2.getProduct()));
                                adapterCategoryProducts.updateList(productList);
                                break;
                            case "4":
                                Collections.sort(productList, ( m2, m1 ) -> m1.getProduct().compareTo(m2.getProduct()));
                                adapterCategoryProducts.updateList(productList);
                                break;
                        }
                    });
                    break;
                }
                default:
                    break;
            }
        }
    };

    @SuppressLint ("CheckResult")
    private void getProductList () {
        if (UtilityMethods.isNetworkConnected(CategoryListItemActivity.this)) {
            mProgressDialog = DialogUtil.getProgressDialog(this, getResources().getString(R.string.app_DialogInfo), getResources().getString(R.string.app_DialogLoading));
            ApiInterface apiService = ApiClient.getClient(this).create(ApiInterface.class);
            String header= Credentials.basic(getResources().getString(R.string.user_id), getResources().getString(R.string.user_password));
            Observable<CategoryProductBaseModel> catObservable;
            if(getIntent().getStringExtra("category")!=null) {
                if (new LoginPreferences (CategoryListItemActivity.this).getString ("token") != null)
                    catObservable = apiService.getCategoryProducts(header,new LoginPreferences (CategoryListItemActivity.this).getString ("token"),null ,"Homepage", "getProductByCategoryid", getIntent().getStringExtra("category_id"), 1000);
                else if (new LoginPreferences (CategoryListItemActivity.this).getString ("cart_id") != null)
                    catObservable = apiService.getCategoryProducts(header,null,new LoginPreferences (CategoryListItemActivity.this).getString ("cart_id") ,"Homepage", "getProductByCategoryid", getIntent().getStringExtra("category_id"), 1000);
                else
                    catObservable = apiService.getCategoryProducts(header,null ,null,"Homepage", "getProductByCategoryid", getIntent().getStringExtra("category_id"), 1000);
            }
            else
                catObservable = apiService.GetSearchProduct(header, "Homepage", "getSearchProducts", getIntent().getStringExtra(ConstantVariable.SearchText));
            catObservable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this :: handleResultsCE, this :: handleErrorCE);
        } else {
            GetDialogue(1);
        }
    }

    private void GetDialogue ( int i ) {
        Dialog dialog = new Dialog(CategoryListItemActivity.this);
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
            public void onClick ( View v ) {
                if (i == 1 && UtilityMethods.isNetworkConnected(CategoryListItemActivity.this))
                    getProductList();
                else
                    GetDialogue(i);
                dialog.dismiss();
            }
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

    private void handleErrorCE ( Throwable throwable ) {
        mProgressDialog.dismiss ();
        if (adapterCategoryProducts.getItemCount() <= 0) {
            findViewById(R.id.tv_empty).setVisibility(View.VISIBLE);
            rvProductList.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed ( ) {
        Intent i = new Intent ( CategoryListItemActivity.this , MainActivity.class );
        i.putExtra("ScreenStatus",new LoginPreferences (CategoryListItemActivity.this).getInt (ConstantVariable.ScreenStatus,0));
        startActivity ( i );
        finish ();
    }

    private void handleResultsCE ( CategoryProductBaseModel productsList ) {
        mProgressDialog.dismiss ();

        if (productsList.getData().getProducts() != null && productsList.getData().getProducts().size() > 0) {
            adapterCategoryProducts.addListArray(productsList.getData().getProducts(), filter_calling);
            findViewById(R.id.tv_empty).setVisibility(View.GONE);
            rvProductList.setVisibility(View.VISIBLE);
            navBar_Items.setText(productsList.getData().getProducts().size() + " Items");
        } else {
            if (adapterCategoryProducts.getItemCount() <= 0) {
                findViewById(R.id.tv_empty).setVisibility(View.VISIBLE);
                rvProductList.setVisibility(View.GONE);
            }
            navBar_Items.setText(0 + " Items");
        }
        filter_calling = false;
        page++;
    }
    private void handleError ( Throwable throwable ) {
        mProgressDialog.dismiss();
        Log.e("User Data", "negative" + throwable.getMessage());
    }
}