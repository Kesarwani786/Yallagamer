package com.augurs.yallagamers.UserInterface.ProductDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.augurs.yallagamers.UserInterface.CategoryListItem.CategoryListItemActivity;
import com.augurs.yallagamers.UserInterface.MainActivity;
import com.augurs.yallagamers.R;
import com.augurs.yallagamers.UserInterface.user_login.LoginActivity;
import com.augurs.yallagamers.adapters.ProductDetailsCustomerReviewAdapter;
import com.augurs.yallagamers.adapters.ProductDetailsFeatureAdapter;
import com.augurs.yallagamers.adapters.ProductImageSliderAdapter;
import com.augurs.yallagamers.adapters.RecommendedItemAdapter;
import com.augurs.yallagamers.adapters.VariantsAdapter;
import com.augurs.yallagamers.api_module.BaseResponse;
import com.augurs.yallagamers.api_module.Const;
import com.augurs.yallagamers.api_module.DialogUtil;
import com.augurs.yallagamers.data_models.AddItemInBagResponceModel;
import com.augurs.yallagamers.data_models.AddProductInBagGuestUserModel;
import com.augurs.yallagamers.data_models.AddRemoveWishlistModel;
import com.augurs.yallagamers.data_models.BagItemBaseModel;
import com.augurs.yallagamers.data_models.ProductDetailBaseModel;
import com.augurs.yallagamers.api_module.ApiClient;
import com.augurs.yallagamers.api_module.ApiInterface;
import com.augurs.yallagamers.data_models.PushCartToLoggedInUserModel;
import com.augurs.yallagamers.data_models.VariantChangeInterface;
import com.augurs.yallagamers.utills.ConstantVariable;
import com.augurs.yallagamers.utills.LoginPreferences;
import com.augurs.yallagamers.utills.UtilityMethods;
import com.google.gson.Gson;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;

public class ProductDetailsActivity extends AppCompatActivity implements VariantChangeInterface {
    private Context context;
    private SliderView sliderView;
    private ProductImageSliderAdapter adapterBannerSlider;
    private ProgressDialog mProgressDialog;
    private TextView TvTextAddinBag;
    private Boolean IsWIshList = false;
    private Integer ProductCartId = 0;
    private Integer ProductSelectedCoount = 1;
    private ImageView imgWislist;
    private TextView TvQuantity;
    private  String ProductName ="";
    View.OnClickListener onClickListener = view -> {
        switch (view.getId ()) {
            case R.id.LLHome: {
                ScreenChange (1);
                break;
            }
            case R.id.LLExplore: {
                ScreenChange (2);
                break;
            }
            case R.id.LLWishList: {
                ScreenChange (3);
                break;
            }
            case R.id.LLProfile: {
                ScreenChange (4);
                break;
            }
            case R.id.LLCart: {
                ScreenChange (5);
                break;
            }
        }
    };


    void ScreenChange ( int p ) {
        if (p == 3) {
            if (new LoginPreferences (ProductDetailsActivity.this).getString ("token") != null) {
                Intent i = new Intent (ProductDetailsActivity.this, MainActivity.class);
                i.putExtra ("ScreenStatus", p);
                startActivity (i);
            } else {
                UtilityMethods.PrintToast (ProductDetailsActivity.this, "Please Login before check your  Wishlist ", 1);
            }
        } else if (p == 4) {
            if (new LoginPreferences (ProductDetailsActivity.this).getString ("token") != null) {
                Intent i = new Intent (ProductDetailsActivity.this, MainActivity.class);
                i.putExtra ("ScreenStatus", p);
                startActivity (i);
            } else {
                Intent intent = new Intent (ProductDetailsActivity.this, LoginActivity.class);
                intent.putExtra ("LoginStatus", 0);
                startActivity (intent);
            }
        } else {
            Intent i = new Intent (ProductDetailsActivity.this, MainActivity.class);
            i.putExtra ("ScreenStatus", p);
            startActivity (i);
        }
    }

    RelativeLayout RLMainBottom;
    Integer MaxQuantity = 0;
    String ShareUrl="";

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_product_details);
        ImageView imgShare = findViewById (R.id.imgShare);
        imgShare.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick ( View v ) {
                /*Create an ACTION_SEND Intent*/
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                /*This will be the actual content you wish you share.*/
                String shareBody = "Here is the share content body";
                /*The type of the content is text, obviously.*/
                intent.setType("text/plain");
                /*Applying information Subject and Body.*/
                //intent.putExtra(android.content.Intent.EXTRA_SUBJECT,ShareUrl);
                intent.putExtra(android.content.Intent.EXTRA_TEXT, ShareUrl);
                /*Fire!*/
                startActivity(Intent.createChooser(intent, "Share via"));


            }
        });
        int count_cart_list = new LoginPreferences (ProductDetailsActivity.this).getInt (ConstantVariable.CartItem, 0);
        int count_wish_list = new LoginPreferences (ProductDetailsActivity.this).getInt (ConstantVariable.WishListItem, 0);
        TextView TvCartCount = findViewById (R.id.TvCartCount);
        if (count_cart_list != 0) {
            TvCartCount.setText (new LoginPreferences (ProductDetailsActivity.this).getInt (ConstantVariable.CartItem, 0) + "");
            TvCartCount.setVisibility (View.VISIBLE);
        } else
            TvCartCount.setVisibility (View.GONE);
        TextView TvWishlistCount = findViewById (R.id.TvWishlistCount);
        if (count_wish_list != 0) {
            TvWishlistCount.setText (new LoginPreferences (ProductDetailsActivity.this).getInt (ConstantVariable.WishListItem, 0) + "");
            TvWishlistCount.setVisibility (View.VISIBLE);
        } else
            TvWishlistCount.setVisibility (View.GONE);

        LinearLayout RlQty = findViewById (R.id.RlQty);
        TvQuantity = findViewById (R.id.TvQuantity);
        TvQuantity.setText (1+"");
        RlQty.setOnClickListener (v -> {
            if (MaxQuantity != 0) {
                OpenDialogueQUantity (MaxQuantity, ProductCartId);
            }
        });
        if (new LoginPreferences (ProductDetailsActivity.this).getInt (ConstantVariable.ScreenStatus, 0) == 2) {
            ImageView imgCategory = findViewById (R.id.imgCategory);
            imgCategory.setImageResource (R.drawable.categories_light);
            TextView TvCategory = findViewById (R.id.TvCategory);
            TvCategory.setTextColor (Color.parseColor ("#777A6D"));
        } else if (new LoginPreferences (ProductDetailsActivity.this).getInt (ConstantVariable.ScreenStatus, 0) == 3) {
            ImageView ImgWishList = findViewById (R.id.ImgWishList);
            ImgWishList.setImageResource (R.drawable.line_heart_light);
            TextView TvWishlist = findViewById (R.id.TvWishlist);
            TvWishlist.setTextColor (Color.parseColor ("#777A6D"));
        } else {
            ImageView imgHome = findViewById (R.id.imgHome);
            imgHome.setImageResource (R.drawable.home_light);
            TextView TvHome = findViewById (R.id.TvHome);
            TvHome.setTextColor (Color.parseColor ("#777A6D"));
        }
        RLMainBottom = findViewById (R.id.RLMainBottom);
        imgWislist = findViewById (R.id.imgWislist);
        imgWislist.setOnClickListener (v -> {
            if (new LoginPreferences (ProductDetailsActivity.this).getString ("token") != null)
                addToWishlist ();
            else
                UtilityMethods.PrintToast (ProductDetailsActivity.this, "Please Login before add the Product in wishlist", 1);
        });
        LinearLayout LLHome = findViewById (R.id.LLHome);
        LinearLayout LLExplore = findViewById (R.id.LLExplore);
        RelativeLayout LLWishList = findViewById (R.id.LLWishList);
        LinearLayout LLProfile = findViewById (R.id.LLProfile);
        RelativeLayout LLCart = findViewById (R.id.LLCart);
        TextView TvProfileLoginText = findViewById (R.id.TvProfileLoginText);
        if (new LoginPreferences (ProductDetailsActivity.this).getString ("token") != null)
            TvProfileLoginText.setText ("Profile");
        else
            TvProfileLoginText.setText ("Login");
        TvTextAddinBag = findViewById (R.id.TvTextAddinBag);
        TvTextAddinBag.setOnClickListener (viewClickListener);
        LLHome.setOnClickListener (onClickListener);
        LLExplore.setOnClickListener (onClickListener);
        LLWishList.setOnClickListener (onClickListener);
        LLProfile.setOnClickListener (onClickListener);
        LLCart.setOnClickListener (onClickListener);
        context = ProductDetailsActivity.this;
        LinearLayout layout_wishlist = findViewById (R.id.layout_wishlist);
        layout_wishlist.setOnClickListener (viewClickListener);
        LinearLayout layout_add_to_bag = findViewById (R.id.layout_add_to_bag);
        layout_add_to_bag.setOnClickListener (viewClickListener);
        findViewById (R.id.img_back).setOnClickListener (viewClickListener);
        sliderView = findViewById (R.id.imageSlider);
        setSlider ();
        GetProductDetailsApi (getIntent ().getIntExtra ("ProductId", 0));
    }

    private void OpenDialogueQUantity ( Integer maxQuantity, Integer productCartId ) {
        ArrayList<Integer> integerArrayList = new ArrayList<> ();
        for (int i = 1; i <= maxQuantity; i++)
            integerArrayList.add (i);
        ListPopupWindow listPopupWindow = new ListPopupWindow (context);
        listPopupWindow.setAdapter (new ArrayAdapter (context, android.R.layout.simple_spinner_dropdown_item, integerArrayList));
        listPopupWindow.setAnchorView (TvQuantity);
        listPopupWindow.setOnItemClickListener (( adapterView, view, i, l ) -> {
            if (integerArrayList.get (i) != 0) {
                ProductSelectedCoount = integerArrayList.get (i);
                TvQuantity.setText (ProductSelectedCoount + " item");
            }
            listPopupWindow.dismiss ();
        });
        listPopupWindow.show ();
    }

    private void QuantityUpdate ( Integer productCartId, Integer Quantity ) {
        mProgressDialog = DialogUtil.getProgressDialog (ProductDetailsActivity.this, getResources ().getString (R.string.app_DialogInfo), getResources ().getString (R.string.app_DialogLoading));
        PushCartToLoggedInUserModel pushCartToLoggedInUserModel = new PushCartToLoggedInUserModel ();
        pushCartToLoggedInUserModel.setMethod ("updateCartProductQuantity");
        pushCartToLoggedInUserModel.setProduct_cart_id (productCartId + "");
        pushCartToLoggedInUserModel.setQuantity (Quantity + "");
        ApiInterface apiService = ApiClient.getClient (ProductDetailsActivity.this).create (ApiInterface.class);
        String header = Credentials.basic (getResources ().getString (R.string.user_id), getResources ().getString (R.string.user_password));
        Observable<BagItemBaseModel> catObservable = apiService.UpdateCart (header, "Cart", new LoginPreferences (ProductDetailsActivity.this).getString ("token"), pushCartToLoggedInUserModel);
        catObservable.observeOn (AndroidSchedulers.mainThread ()).subscribeOn (Schedulers.io ()).observeOn (AndroidSchedulers.mainThread ()).subscribe (this :: handleResultsCE, this :: handleError);
    }

    private void handleResultsCE ( BagItemBaseModel bagItemBaseModel ) {
    }


    private void GetProductDetailsApi ( int id ) {
        if (UtilityMethods.isNetworkConnected (ProductDetailsActivity.this)) {
            ApiInterface apiService = ApiClient.getClient (this).create (ApiInterface.class);
            mProgressDialog = DialogUtil.getProgressDialog (this, getResources ().getString (R.string.app_DialogInfo), getResources ().getString (R.string.app_DialogLoading));
            String header = Credentials.basic (getResources ().getString (R.string.user_id), getResources ().getString (R.string.user_password));
            Single<ProductDetailBaseModel> userObservable;
            if (new LoginPreferences (ProductDetailsActivity.this).getString ("token") != null)
                userObservable = apiService.GetProductDetailsById (header, "Homepage", "getProductByid", id, new LoginPreferences (ProductDetailsActivity.this).getString ("token"));
            else
                userObservable = apiService.GetProductDetailsById (header, "Homepage", "getProductByid", id, null, null, new LoginPreferences (ProductDetailsActivity.this).getString ("cart_id"));
            userObservable.subscribeOn (Schedulers.io ()).observeOn (AndroidSchedulers.mainThread ()).subscribe (this :: GetProductDetailsResult, this :: GetProductDetailsError);
        } else {
            GetDialogue (1);
        }
    }

    private void GetDialogue ( int i ) {
        Dialog dialog = new Dialog (ProductDetailsActivity.this);
        dialog.requestWindowFeature (Window.FEATURE_NO_TITLE);
        dialog.setCancelable (false);
        dialog.setContentView (R.layout.dialog);
        Window window = dialog.getWindow ();
        WindowManager.LayoutParams wlp = window.getAttributes ();
        window.setLayout (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow ().addFlags (WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow ().setBackgroundDrawable (new ColorDrawable (android.graphics.Color.TRANSPARENT));
        window.setAttributes (wlp);
        TextView TvRetry = dialog.findViewById (R.id.TvRetry);
        TvRetry.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick ( View v ) {
                if (i == 1 && UtilityMethods.isNetworkConnected (ProductDetailsActivity.this))
                    GetProductDetailsApi (getIntent ().getIntExtra ("ProductId", 0));
                else if (i == 2 && UtilityMethods.isNetworkConnected (ProductDetailsActivity.this))
                    addToBag ();
                else if (i == 3 && UtilityMethods.isNetworkConnected (ProductDetailsActivity.this))
                    addToWishlist ();
                else
                    GetDialogue (i);
                dialog.dismiss ();
            }
        });
        TextView TvRetryNot = dialog.findViewById (R.id.TvRetryNot);
        TvRetryNot.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick ( View v ) {
                dialog.dismiss ();
            }
        });
        dialog.show ();
    }

    @SuppressLint ({"SetTextI18n", "DefaultLocale", "SetJavaScriptEnabled"})
    private void GetProductDetailsResult ( ProductDetailBaseModel productDetailBaseModel ) {
        Log.v ("productDetailBaseModel", new Gson ().toJson (productDetailBaseModel));
        mProgressDialog.dismiss ();
        if (productDetailBaseModel.getStatus () == 200) {
            ShareUrl = productDetailBaseModel.getData ().getProductsDetails ().getProduct_url ();
            if (productDetailBaseModel.getData ().getProductsDetails ().getCartCounts () != 0) {
                TextView TvCartCount = findViewById (R.id.TvCartCount);
                TvCartCount.setText (productDetailBaseModel.getData ().getProductsDetails ().getCartCounts () + "");
                TvCartCount.setVisibility (View.VISIBLE);
            }
            if (productDetailBaseModel.getData ().getProductsDetails ().getWishlistCounts () != 0) {
                TextView TvWishlistCount = findViewById (R.id.TvWishlistCount);
                TvWishlistCount.setText (productDetailBaseModel.getData ().getProductsDetails ().getWishlistCounts () + "");
                TvWishlistCount.setVisibility (View.VISIBLE);
            }
            ProductCartId = productDetailBaseModel.getData ().getProductsDetails ().getProductId ();
            MaxQuantity = productDetailBaseModel.getData ().getProductsDetails ().getMaxQuantity ();
            if(MaxQuantity==0) {
                TvTextAddinBag.setText ("Out of Stock");
                TvQuantity.setText (0+" items");
            }
            RLMainBottom.setVisibility (View.VISIBLE);
            if (productDetailBaseModel.getData ().getProductsDetails ().getInWishlist ().equals ("Y")) {
                imgWislist.setImageResource (R.drawable.ic_heart);
                IsWIshList = true;
            } else {
                IsWIshList = false;
                imgWislist.setImageResource (R.drawable.ic_wishlist);
            }
            new LoginPreferences (ProductDetailsActivity.this).put (ConstantVariable.CartItem, Integer.parseInt (new LoginPreferences (ProductDetailsActivity.this).getInt (ConstantVariable.CartItem, 0) + ""));
            if (productDetailBaseModel.getData ().getProductsDetails ().getAllimages ().size () != 0&&productDetailBaseModel.getData ().getProductsDetails ().getAllimages ()!=null) {
                adapterBannerSlider.renewItems (productDetailBaseModel.getData ().getProductsDetails ().getAllimages ());

            }
            TextView textView18 = findViewById (R.id.textView18);
            if (productDetailBaseModel.getData ().getProductsDetails ().getVariation ().size () == 0)
                textView18.setVisibility (View.GONE);
            textView18.setText ("Edition : " + UtilityMethods.numberToWord (productDetailBaseModel.getData ().getProductsDetails ().getVariation ().size ()) + " Variants");
            TextView textView20 = findViewById (R.id.textView20);textView20.setText (productDetailBaseModel.getData ().getProductsDetails ().getProduct_rating ());
            RecyclerView recyclewview = findViewById (R.id.recyclewview);
            recyclewview.setLayoutManager (new LinearLayoutManager (ProductDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
            recyclewview.setAdapter (new VariantsAdapter (ProductDetailsActivity.this, productDetailBaseModel.getData ().getProductsDetails ().getVariation (), ProductDetailsActivity.this, productDetailBaseModel.getData ().getProductsDetails ().getProductId ()));
            TextView textView21 = findViewById (R.id.textView21);
            textView21.setText (productDetailBaseModel.getData ().getProductsDetails ().getPopularity () + "");
            TextView pd_tv_product_name = findViewById (R.id.pd_tv_product_name);
            pd_tv_product_name.setText (productDetailBaseModel.getData ().getProductsDetails ().getProduct () + "");
            TextView TvTitle = findViewById (R.id.TvTitle);TvTitle.setText (productDetailBaseModel.getData ().getProductsDetails ().getProduct () + "");
            ProductName=productDetailBaseModel.getData ().getProductsDetails ().getProduct () + "";
            TextView textView24 = findViewById (R.id.textView24);
            textView24.setText (productDetailBaseModel.getData ().getProductsDetails ().getProduct () + "");
            TextView pd_tv_product_description = findViewById (R.id.pd_tv_product_description);
            pd_tv_product_description.setText (productDetailBaseModel.getData ().getProductsDetails ().getShortDescription () + "");
            TextView pd_tv_product_brand = findViewById (R.id.pd_tv_product_brand);
            pd_tv_product_brand.setText (productDetailBaseModel.getData ().getProductsDetails ().getMainCategory () + "");
            TextView pd_base_price = findViewById (R.id.pd_base_price);
            TextView TvAmount = findViewById (R.id.TvAmount);
            int listPrice = (int) productDetailBaseModel.getData ().getProductsDetails ().getListPrice ();
            int BasePrice = (int) productDetailBaseModel.getData ().getProductsDetails ().getBasePrice ();
            if( listPrice<=BasePrice)
            {
                TvAmount.setText(Const.CurrencyValue+BasePrice);
                pd_base_price.setVisibility ( View.GONE );
            }
            else {
                TvAmount.setText(Const.CurrencyValue +BasePrice);
                pd_base_price.setText(Const.CurrencyValue+listPrice);
            }
            TextView pd_tv_product_price_2 = findViewById (R.id.pd_tv_product_price_2);
            pd_tv_product_price_2.setText (Const.CurrencyValue +BasePrice);
            TextView textView23 = findViewById (R.id.textView23);
            textView23.setText (productDetailBaseModel.getData ().getProductsDetails ().getShippings ().getServiceDeliveryTime ());
            TextView textView26 = findViewById (R.id.textView26);
            String text = "<font size=15 color=#000000>You will earn </font>" +
                    "<B> <font size=25 color=#d3d3d3>" + productDetailBaseModel.getData ().getProductsDetails ().getPoints () + "</font></B>" +
                    "<font size=15 color=#000000> insider points on this purchase </font>";
            textView26.setText (Html.fromHtml (text));
            RecyclerView recycleview_review = findViewById (R.id.recycleview_review);
            recycleview_review.setLayoutManager (new LinearLayoutManager (ProductDetailsActivity.this, LinearLayoutManager.VERTICAL, false));
            recycleview_review.setAdapter (new ProductDetailsCustomerReviewAdapter (ProductDetailsActivity.this, productDetailBaseModel.getData ().getProductsDetails ().getReviews ()));

            RecyclerView recycleviewfeature = findViewById (R.id.recycleviewfeature);
            recycleviewfeature.setLayoutManager (new LinearLayoutManager (ProductDetailsActivity.this, LinearLayoutManager.VERTICAL, false));
            recycleviewfeature.setAdapter (new ProductDetailsFeatureAdapter (ProductDetailsActivity.this, productDetailBaseModel.getData ().getProductsDetails ().getProductFeatures ()));
            TextView pd_tv_review_all = findViewById (R.id.pd_tv_review_all);
            if (productDetailBaseModel.getData ().getProductsDetails ().getReviews ().size () == 0)
                pd_tv_review_all.setVisibility (View.GONE);

            TextView pd_tv_product_details = findViewById (R.id.pd_tv_product_details);
            pd_tv_product_details.setText (Html.fromHtml (productDetailBaseModel.getData ().getProductsDetails ().getFullDescription ().trim ()));
            WebView webview = findViewById (R.id.webview);
            webview.getSettings ().setJavaScriptEnabled (true);
            webview.loadDataWithBaseURL ("", productDetailBaseModel.getData ().getProductsDetails ().getFullDescription (), "text/html", "UTF-8", "");
            RecyclerView rv_similar_item_list = findViewById (R.id.rv_similar_item_list);
            rv_similar_item_list.setLayoutManager (new LinearLayoutManager (ProductDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
            rv_similar_item_list.setAdapter (new RecommendedItemAdapter (ProductDetailsActivity.this, productDetailBaseModel.getData ().getSimilarProducts ()));
            TextView TvReview = findViewById (R.id.TvReview);
            TvReview.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick ( View v ) {
                    Dialog dialog1 = new Dialog (ProductDetailsActivity.this);
                    dialog1.setContentView (R.layout.dialogue_write_review);
                    Window window = dialog1.getWindow ();
                    WindowManager.LayoutParams wlp = window.getAttributes ();
                    window.setLayout (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog1.getWindow ().addFlags (WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                    dialog1.getWindow ().setBackgroundDrawable (new ColorDrawable (android.graphics.Color.TRANSPARENT));
                    window.setAttributes (wlp);
                    dialog1.show ();
                    Button BtnSubmit = dialog1.findViewById (R.id.BtnSubmit);
                    ImageView img_close = dialog1.findViewById (R.id.img_close);
                    img_close.setOnClickListener (new View.OnClickListener () {
                        @Override
                        public void onClick ( View v ) {
                            dialog1.dismiss ();
                        }
                    });
                    EditText etTitle = dialog1.findViewById (R.id.etTitle);
                    EditText etName = dialog1.findViewById (R.id.etName);
                    EditText etEmail = dialog1.findViewById (R.id.etEmail);
                    EditText etMessage = dialog1.findViewById (R.id.etMessage);
                    RatingBar ratingBar = dialog1.findViewById (R.id.ratingBar);
                    BtnSubmit.setOnClickListener (new View.OnClickListener () {
                        @Override
                        public void onClick ( View v ) {
                            if (etTitle.getText ().toString ().length () == 0)
                                Toast.makeText (ProductDetailsActivity.this, "Please enter title ", Toast.LENGTH_LONG).show ();
                            else if (etName.getText ().toString ().length () == 0)
                                Toast.makeText (ProductDetailsActivity.this, "Please enter Name ", Toast.LENGTH_LONG).show ();
                            else if (etEmail.getText ().toString ().length () == 0)
                                Toast.makeText (ProductDetailsActivity.this, "Please enter Email-Id ", Toast.LENGTH_LONG).show ();
                            else if (! android.util.Patterns.EMAIL_ADDRESS.matcher (etEmail.getText ().toString ()).matches ())
                                Toast.makeText (ProductDetailsActivity.this, "Please enter valid Email-Id", Toast.LENGTH_LONG).show ();
                            else if (etMessage.getText ().toString ().length () == 0)
                                Toast.makeText (ProductDetailsActivity.this, "Please enter Message ", Toast.LENGTH_LONG).show ();
                            else if (ratingBar.getRating () == 0)
                                Toast.makeText (ProductDetailsActivity.this, "Please enter Rating  ", Toast.LENGTH_LONG).show ();
                            else {
                                ApiInterface apiService = ApiClient.getClient (ProductDetailsActivity.this).create (ApiInterface.class);
                                mProgressDialog = DialogUtil.getProgressDialog (ProductDetailsActivity.this, getResources ().getString (R.string.app_DialogInfo), getResources ().getString (R.string.app_DialogLoading));
                                String header = Credentials.basic (getResources ().getString (R.string.user_id), getResources ().getString (R.string.user_password));
                                Single<BaseResponse> userObservable = apiService.AddReview (header, "addRatings", "Homepage", productDetailBaseModel.getData ().getProductsDetails ().getProductId () + "", etEmail.getText ().toString (), etName.getText ().toString (), etTitle.getText ().toString (), etMessage.getText ().toString (), ratingBar.getRating () + "");
                                userObservable.observeOn (AndroidSchedulers.mainThread ()).subscribeOn (Schedulers.io ()).observeOn (AndroidSchedulers.mainThread ()).subscribe (this :: GetProductDetailsResult, this :: GetProductDetailsError);
                            }
                        }

                        private void GetProductDetailsResult ( BaseResponse userData ) {
                            mProgressDialog.dismiss ();
                            dialog1.dismiss ();
                            if (userData.getStatus () == 200)
                                UtilityMethods.PrintToast (ProductDetailsActivity.this, "Review added successfully" + "", 0);
                            else
                                UtilityMethods.PrintToast (ProductDetailsActivity.this, userData.getMessage (), 1);
                        }

                        private void GetProductDetailsError ( Throwable throwable ) {
                            mProgressDialog.dismiss ();
                            dialog1.dismiss ();
                            UtilityMethods.PrintToast (ProductDetailsActivity.this, throwable.getMessage (), 1);
                        }
                    });
                }
            });
        } else
            UtilityMethods.PrintToast (ProductDetailsActivity.this, productDetailBaseModel.getMessage (), 1);
    }



    private void GetProductDetailsError ( Throwable throwable ) {
        mProgressDialog.dismiss ();
        UtilityMethods.PrintToast (ProductDetailsActivity.this, "Something went wrong", 1);
    }
    private void setSlider ( ) {
        adapterBannerSlider = new ProductImageSliderAdapter (context);
        sliderView.setSliderAdapter (adapterBannerSlider);
        sliderView.setIndicatorAnimation (IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation (SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection (SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor (Color.GRAY);
        sliderView.setIndicatorUnselectedColor (Color.parseColor ("#d3d3d3"));
        sliderView.setScrollTimeInSec (2); //set scroll delay in seconds :
        sliderView.startAutoCycle ();
    }

    private final View.OnClickListener viewClickListener = v -> {
        switch (v.getId ()) {
            case R.id.img_back:
                finish ();
                break;
            case R.id.layout_wishlist:
                if (new LoginPreferences (ProductDetailsActivity.this).getString ("token") != null)
                    addToWishlist ();
                else
                    UtilityMethods.PrintToast (ProductDetailsActivity.this, "Please Login before add the Product in wishlist", 1);
                break;
            case R.id.TvTextAddinBag: {
                if(MaxQuantity==0)
                    UtilityMethods.PrintToast (ProductDetailsActivity.this, "Product is out of stock", 2);
                else
                    addToBag ();
                //else
                  //  UtilityMethods.PrintToast (ProductDetailsActivity.this, "Please select Quantity", 2);
            }

            break;
            default:
                break;
        }
    };

    private void addToWishlist ( ) {
        PushCartToLoggedInUserModel pushCartToLoggedInUserModel = new PushCartToLoggedInUserModel ();
        pushCartToLoggedInUserModel.setMethod ("addToWishlist");
        if (! IsWIshList)
            pushCartToLoggedInUserModel.setAction ("add");
        else
            pushCartToLoggedInUserModel.setAction ("remove");
        pushCartToLoggedInUserModel.setProductId (getIntent ().getIntExtra ("ProductId", 0) + "");

        if (UtilityMethods.isNetworkConnected (ProductDetailsActivity.this)) {
            ApiInterface apiService = ApiClient.getClient (this).create (ApiInterface.class);
            mProgressDialog = DialogUtil.getProgressDialog (this, getResources ().getString (R.string.app_DialogInfo), getResources ().getString (R.string.app_DialogLoading));
            String header = Credentials.basic (getResources ().getString (R.string.user_id), getResources ().getString (R.string.user_password));
            Single<AddRemoveWishlistModel> userObservable = apiService.addProductItemToWishList (header, "Wishlist2", new LoginPreferences (context).getString ("token"), pushCartToLoggedInUserModel);
            userObservable.observeOn (AndroidSchedulers.mainThread ()).subscribeOn (Schedulers.io ()).observeOn (AndroidSchedulers.mainThread ()).subscribe (this :: handleResults, this :: handleError);
        } else
            GetDialogue (3);
    }

    private void handleResults ( AddRemoveWishlistModel baseResponse ) {
        mProgressDialog.dismiss ();
        if (baseResponse.getStatus () == 200) {
            if (! IsWIshList) {
                imgWislist.setImageResource (R.drawable.ic_heart);
                IsWIshList = true;
            } else {
                IsWIshList = false;
                imgWislist.setImageResource (R.drawable.ic_wishlist);
            }
            new LoginPreferences (context).put (ConstantVariable.CartItem, baseResponse.getData().getCartCounts ());
            new LoginPreferences (context).put (ConstantVariable.WishListItem,baseResponse.getData().getWishlistCounts());
            TextView TvCartCount = findViewById (R.id.TvCartCount);
            if (baseResponse.getData().getCartCounts () != 0) {
                TvCartCount.setText (new LoginPreferences (ProductDetailsActivity.this).getInt (ConstantVariable.CartItem, 0) + "");
                TvCartCount.setVisibility (View.VISIBLE);
            } else
                TvCartCount.setVisibility (View.GONE);
            TextView TvWishlistCount = findViewById (R.id.TvWishlistCount);
            if ( new LoginPreferences (ProductDetailsActivity.this).getInt (ConstantVariable.WishListItem, 0) != 0) {
                TvWishlistCount.setText (new LoginPreferences (ProductDetailsActivity.this).getInt (ConstantVariable.WishListItem, 0) + "");
                TvWishlistCount.setVisibility (View.VISIBLE);
            } else
                TvWishlistCount.setVisibility (View.GONE);

        } else
            Toast.makeText (ProductDetailsActivity.this, "" + baseResponse.getMessage (), Toast.LENGTH_SHORT).show ();
    }

    private void handleError ( Throwable throwable ) {
        mProgressDialog.dismiss ();
        Toast.makeText (this, throwable.getMessage (), Toast.LENGTH_LONG).show ();
    }

    @SuppressLint ("CheckResult")
    private void addToBag ( ) {
        AddProductInBagGuestUserModel addProductInBagGuestUserModel = new AddProductInBagGuestUserModel ();
        addProductInBagGuestUserModel.setProductId (getIntent ().getIntExtra ("ProductId", 0));
        addProductInBagGuestUserModel.setMethod ("addToCart");
        addProductInBagGuestUserModel.setQuantity (ProductSelectedCoount);
        if (new LoginPreferences (ProductDetailsActivity.this).getString ("token") == null)
            addProductInBagGuestUserModel.setCartId (new LoginPreferences (ProductDetailsActivity.this).getString ("cart_id"));
        if (UtilityMethods.isNetworkConnected (ProductDetailsActivity.this)) {
            ApiInterface apiService = ApiClient.getClient (this).create (ApiInterface.class);
            mProgressDialog = DialogUtil.getProgressDialog (this, getResources ().getString (R.string.app_DialogInfo), getResources ().getString (R.string.app_DialogLoading));
            String header = Credentials.basic (getResources ().getString (R.string.user_id), getResources ().getString (R.string.user_password));
            Single<AddItemInBagResponceModel> userObservable;
            if (new LoginPreferences (ProductDetailsActivity.this).getString ("token") != null)
                userObservable = apiService.AddInBag (header, "Cart", addProductInBagGuestUserModel, new LoginPreferences (ProductDetailsActivity.this).getString ("token"));
            else
                userObservable = apiService.AddInBag (header, "Cart", addProductInBagGuestUserModel);
            userObservable.observeOn (AndroidSchedulers.mainThread ())
                    .subscribeOn (Schedulers.io ())
                    .observeOn (AndroidSchedulers.mainThread ())
                    .subscribe (this :: handleResultsBagGuestUser, this :: handleErrorBagGuestUser);
        } else
            GetDialogue (2);
    }

    private void handleResultsBagGuestUser ( AddItemInBagResponceModel baseResponse ) {
        mProgressDialog.dismiss ();
        if (baseResponse.getStatus () == 200) {
            OpenDialogue(baseResponse);

            LinearLayout RlQty = findViewById (R.id.RlQty);
            RlQty.setVisibility (View.VISIBLE);
             {
                int count_wish_list = Integer.parseInt (baseResponse.getData ().getWishlistCounts ());
                int count_cart_list = Integer.parseInt (baseResponse.getData ().getCartCounts ());
                new LoginPreferences (ProductDetailsActivity.this).put (ConstantVariable.CartItem, count_cart_list);
                new LoginPreferences (ProductDetailsActivity.this).put (ConstantVariable.WishListItem, count_wish_list);
                if (count_cart_list != 0) {
                    TextView TvCartCount = findViewById (R.id.TvCartCount);
                    TvCartCount.setText (new LoginPreferences (ProductDetailsActivity.this).getInt (ConstantVariable.CartItem, 0) + "");
                    TvCartCount.setVisibility (View.VISIBLE);
                }
                if (count_wish_list != 0) {
                    TextView TvWishlistCount = findViewById (R.id.TvWishlistCount);
                    TvWishlistCount.setText (new LoginPreferences (ProductDetailsActivity.this).getInt (ConstantVariable.WishListItem, 0) + "");
                    TvWishlistCount.setVisibility (View.VISIBLE);
                }
                if(baseResponse.getData ().getNotice ().length ()!=0){
                    DialogueForNotice(baseResponse.getData ().getNotice ());
                }
            }
            new LoginPreferences (ProductDetailsActivity.this).put ("cart_id", baseResponse.getData ().getCartId () + "");
         //   UtilityMethods.PrintToast (ProductDetailsActivity.this, "Product Added to Your Cart", 0);
        } else
            UtilityMethods.PrintToast (ProductDetailsActivity.this, baseResponse.getData ().getMessage (), 1);
    }

    private void OpenDialogue (AddItemInBagResponceModel baseResponse ) {
        Dialog dialog = new Dialog (ProductDetailsActivity.this);
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogue_add_more_products);
        Window window = dialog.getWindow ( );
        WindowManager.LayoutParams wlp = window.getAttributes ( );
        window.setLayout ( ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT );
        window.setGravity(Gravity.TOP);
        dialog.getWindow ( ).addFlags ( WindowManager.LayoutParams.FLAG_DIM_BEHIND );
        dialog.getWindow ( ).setBackgroundDrawable ( new ColorDrawable ( android.graphics.Color.TRANSPARENT ) );
        window.setAttributes ( wlp );
        TextView TVProductName= dialog.findViewById (R.id.TVProductName);
        TVProductName.setText (ProductName );
        TextView TvAmount= dialog.findViewById (R.id.TvAmount);
        TvAmount.setText (Const.CurrencyValue + baseResponse.getData ().getDetails ().getTotal ());
        TextView TvContinueShopping = dialog.findViewById (R.id.TvContinueShopping);
        TvContinueShopping.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick ( View v ) {
                dialog.dismiss ();
            }
        });
        TextView TvCheckout = dialog.findViewById (R.id.TvCheckout);
        TvCheckout.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick ( View v ) {
                dialog.dismiss ();
                Intent i = new Intent (ProductDetailsActivity.this, MainActivity.class);
                i.putExtra ("ScreenStatus", 5);
                startActivity (i);
                finish ();
            }
        });



        dialog.show();
    }

    private void handleErrorBagGuestUser ( Throwable throwable ) {
        mProgressDialog.dismiss ();
        UtilityMethods.PrintToast (ProductDetailsActivity.this, throwable.getMessage (), 1);
    }

    @Override
    public void ProductId ( Integer ProductId ) {
        GetProductDetailsApi (ProductId);
    }
    private void DialogueForNotice ( String notice ) {
        Dialog dialog = new Dialog (ProductDetailsActivity.this);
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        Window window = dialog.getWindow ( );
        WindowManager.LayoutParams wlp = window.getAttributes ( );
        window.setLayout ( ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT );
        dialog.getWindow ( ).addFlags ( WindowManager.LayoutParams.FLAG_DIM_BEHIND );
        dialog.getWindow ( ).setBackgroundDrawable ( new ColorDrawable ( android.graphics.Color.TRANSPARENT ) );
        window.setAttributes ( wlp );
        TextView TvTeas = dialog.findViewById ( R.id.TvTeas );
        TvTeas.setText(notice);
        TextView TvTesasd = dialog.findViewById ( R.id.TvTesasd );
        RelativeLayout TvText= dialog.findViewById (R.id.TvText);
        TextView TvRetry =  dialog.findViewById(R.id.TvRetry);
        TvRetry.setText ( "Continue" );
        TvRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (ProductDetailsActivity.this, MainActivity.class);
                i.putExtra ("ScreenStatus",5);
                startActivity (i);
                finish ();
                dialog.dismiss();
            }
        });
        TextView TvRetryNot =  dialog.findViewById(R.id.TvRetryNot);
        TvRetryNot.setText ( "No" );
        TvRetryNot.setVisibility (View.GONE);
        TvRetryNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onResume ( ) {
        super.onResume ();
        adapterBannerSlider.notifyDataSetChanged ();
    }
}