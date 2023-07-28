package com.augurs.yallagamers.UserInterface.home;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.augurs.yallagamers.UserInterface.MainActivity;
import com.augurs.yallagamers.R;
import com.augurs.yallagamers.UserInterface.CategoryListItem.CategoryListItemActivity;
import com.augurs.yallagamers.adapters.AdapterBox;
import com.augurs.yallagamers.adapters.AdapterCategories;
import com.augurs.yallagamers.adapters.AdapterCollectorEditions;
import com.augurs.yallagamers.adapters.AdapterGadgetsAccessories;
import com.augurs.yallagamers.adapters.AdapterOffer;
import com.augurs.yallagamers.adapters.AdapterOurBlog;
import com.augurs.yallagamers.adapters.AdapterTopGames;
import com.augurs.yallagamers.adapters.AdapterBannerSlider;
import com.augurs.yallagamers.api_module.DialogUtil;
import com.augurs.yallagamers.data_models.Blog;
import com.augurs.yallagamers.data_models.Box;
import com.augurs.yallagamers.data_models.Category;
import com.augurs.yallagamers.data_models.Collectors_Editions;
import com.augurs.yallagamers.data_models.Gadgets_Accessories;
import com.augurs.yallagamers.data_models.HomePageData;
import com.augurs.yallagamers.data_models.OfferModel;
import com.augurs.yallagamers.api_module.ApiClient;
import com.augurs.yallagamers.api_module.ApiInterface;
import com.augurs.yallagamers.data_models.Top_Games;
import com.augurs.yallagamers.utills.ConstantVariable;
import com.augurs.yallagamers.utills.LoginPreferences;
import com.augurs.yallagamers.utills.UtilityMethods;
import com.bumptech.glide.Glide;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import java.util.ArrayList;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;

public class HomeFragment extends Fragment {
    RecyclerView rvCategoryList,rvProductList,rvCollectorEditionList,rvTopGameList,rvOurBlogList,recycleview,recycleview_offer;
    AdapterBox adapterBox;
    AdapterCategories adapterCategories;
    AdapterGadgetsAccessories adapterGadgetsAccessories;
    AdapterCollectorEditions adapterCollectorEditions;
    AdapterTopGames adapterTopGames;
    AdapterOurBlog adapterOurBlog;
    AdapterOffer adapterOffer;
    ProgressDialog mProgressDialog;
    ShapeableImageView imageView6;
    SliderView sliderView;
    AdapterBannerSlider adapterBannerSlider;
    ArrayList< OfferModel > offerList=new ArrayList<>(  );
    ArrayList<Category> categoryList=new ArrayList<>();
    ArrayList<Gadgets_Accessories> gadgetsAccessoriesList =new ArrayList<>();
    ArrayList<Collectors_Editions> collectorsEditionsList =new ArrayList<>();
    ArrayList<Top_Games> topGamesList=new ArrayList<>();
    ArrayList<Blog> blogList =new ArrayList<>();
    ArrayList< Box > boxList =new ArrayList<>();
    private TextView TvCategory,TvGadgets,TvCollector,TvTopGames;
    private EditText et_search_box;
    private  ImageView Imgcorss;
    private SkeletonScreen skeletonScreenOffer,skeletonScreenBox,skeletonScreenCategories,skeletonScreenGadgets,skeletonScreenEditor,skeletonScreenTopGames,skeletonScreenblog;
    public static HomeFragment newInstance ( ) {
        return new HomeFragment ( );
    }
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        InitialiseHomePageViews(root);
        SetupSliderView(root);
        callHomePage();
        return root;
    }
    public void InitialiseHomePageViews(View root){
        Imgcorss= root.findViewById (R.id.Imgcorss);
        imageView6= root.findViewById ( R.id.imageView6 );
        rvCategoryList=root.findViewById(R.id.rv_category_list);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        rvCategoryList.setLayoutManager(linearLayoutManager);
        adapterCategories=new AdapterCategories(getContext(),categoryList);
        rvCategoryList.setAdapter(adapterCategories);
        skeletonScreenCategories = Skeleton.bind(rvCategoryList).adapter(adapterCategories).shimmer(true).load(R.layout.row_category).show(); //default count is 10

        recycleview=root.findViewById(R.id.recycleview);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager (getActivity (), 2);
        recycleview.setLayoutManager(mLayoutManager);
        recycleview.setHasFixedSize(true);
        adapterBox=new AdapterBox (getContext(),boxList);
        recycleview.setAdapter(adapterBox);
        skeletonScreenBox = Skeleton.bind(recycleview).adapter(adapterBox).shimmer(true).load(R.layout.row_category_box).show(); //default count is 10

        recycleview_offer=root.findViewById(R.id.recycleview_offer);
        recycleview_offer.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        adapterOffer=new AdapterOffer (getContext(),offerList);
        recycleview_offer.setAdapter(adapterOffer);
        skeletonScreenOffer = Skeleton.bind(recycleview_offer).adapter(adapterOffer).shimmer(false).load(R.layout.row_offer).show(); //default count is 10

        // Products List
        rvProductList=root.findViewById(R.id.rv_item_list);
        LinearLayoutManager linearLayoutManager2=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        rvProductList.setLayoutManager(linearLayoutManager2);
        adapterGadgetsAccessories =new AdapterGadgetsAccessories(getContext(), gadgetsAccessoriesList,1);
        rvProductList.setAdapter(adapterGadgetsAccessories);
        skeletonScreenGadgets = Skeleton.bind(rvProductList).adapter(adapterGadgetsAccessories).shimmer(false).load(R.layout.row_item).show(); //default count is 10

        // Collectors Editions List
        rvCollectorEditionList=root.findViewById(R.id.rv_collector_list);
        rvCollectorEditionList.setLayoutManager(new GridLayoutManager (getActivity (), 3));
        rvCollectorEditionList.setHasFixedSize(true);
        adapterCollectorEditions=new AdapterCollectorEditions(getContext(), collectorsEditionsList,2);
        rvCollectorEditionList.setAdapter(adapterCollectorEditions);
        skeletonScreenEditor = Skeleton.bind(rvCollectorEditionList).adapter(adapterCollectorEditions).shimmer(false).load(R.layout.row_game).show(); //default count is 10

        // Top Games List
        rvTopGameList=root.findViewById(R.id.rv_top_game_list);
        LinearLayoutManager linearLayoutManager3=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        rvTopGameList.setLayoutManager(linearLayoutManager3);
        adapterTopGames=new AdapterTopGames(getContext(),topGamesList);
        rvTopGameList.setAdapter(adapterTopGames);
        skeletonScreenTopGames = Skeleton.bind(rvTopGameList).adapter(adapterTopGames).shimmer(true).load(R.layout.row_game_h).show(); //default count is 10

        // Our Blog6
        rvOurBlogList=root.findViewById(R.id.rv_our_blog_list);
        LinearLayoutManager linearLayoutManager4=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        rvOurBlogList.setLayoutManager(linearLayoutManager4);
        adapterOurBlog=new AdapterOurBlog(getContext(), blogList);
        rvOurBlogList.setAdapter(adapterOurBlog);
        //skeletonScreenblog = Skeleton.bind(rvOurBlogList).adapter(adapterOurBlog).duration(1000).count(10).load(R.layout.row_our_blog).show(); //default count is 10
        skeletonScreenblog = Skeleton.bind(rvOurBlogList).adapter(adapterOurBlog).shimmer(true).load(R.layout.row_our_blog).show(); //default count is 10
        // Category List Skeleton
       // skeletonScreen = Skeleton.bind(rvCategoryList).adapter(adapterCategories).load(R.layout.item_skeleton).shimmer(false).show();
    }
    // Home Page API Call
    @SuppressLint("CheckResult")
    private void callHomePage() {
        if ( UtilityMethods.isNetworkConnected ( getActivity ( ) ) ) {
            mProgressDialog = DialogUtil.getProgressDialog ( getActivity ( ) , getResources ( ).getString ( R.string.app_DialogInfo ) , getResources ( ).getString ( R.string.app_DialogLoading ) );
            String _d1 = "Homepage";
            String header = Credentials.basic ( getResources ( ).getString ( R.string.user_id ) , getResources ( ).getString ( R.string.user_password ) );
            ApiInterface apiService = ApiClient.getClient ( getContext ( ) ).create ( ApiInterface.class );
            Observable < HomePageData > hpObservable;
            if ( new LoginPreferences ( getActivity ( ) ).getString ( "token" ) != null )
                hpObservable = apiService.getHomePage ( header , _d1 , "homePage" , new LoginPreferences ( getActivity ( ) ).getString ( "token" ),null );
            else if ( new LoginPreferences ( getActivity ( ) ).getString ( "cart_id" ) != null )
                hpObservable = apiService.getHomePage ( header , _d1 , "homePage", null,new LoginPreferences ( getActivity ( ) ).getString ( "cart_id" ) );
            else
                hpObservable = apiService.getHomePage ( header , _d1 , "homePage" );
            // Subscribe Observer
            hpObservable.observeOn ( AndroidSchedulers.mainThread ( ) ).subscribeOn ( Schedulers.io ( ) ).subscribe ( this :: handleResultsHP , this :: handleErrorHP );
        }
        else {
            GetDialogue(1);
        }
    }

    private void GetDialogue ( int i ) {
        Dialog dialog = new Dialog (getActivity ());
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
        TvRetry.setOnClickListener(v -> {
            if(i==1&&UtilityMethods.isNetworkConnected(getActivity()))
                callHomePage();
            else
                GetDialogue(i);
            dialog.dismiss();
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
    private void handleResultsHP(HomePageData homePageData) {
        mProgressDialog.dismiss ( );
        if(homePageData.getStatus() == 200){
            ((MainActivity)getActivity ()).UpdateStatusCartItem (homePageData.getHomePages ().getCartCounts (),homePageData.getHomePages ().getWishlistCounts ());
            adapterCollectorEditions.addListArray(homePageData.getHomePages ().getCollectors_editions());
            new LoginPreferences (getContext ()).put (ConstantVariable.CartItem, homePageData.getHomePages ().getCartCounts ());
            new LoginPreferences (getContext ()).put (ConstantVariable.WishListItem, homePageData.getHomePages ().getWishlistCounts ());
            adapterGadgetsAccessories.addListArray(homePageData.getHomePages ().getGadgets_accessories());
            adapterTopGames.addListArray(homePageData.getHomePages ().getTop_games());
            adapterBox.addListArray(homePageData.getHomePages ().getBox ());
            SetBanner(homePageData.getHomePages ().getHome_banner());
            adapterCategories.addListArray(homePageData.getHomePages ().getCategories ());
            adapterOffer.addListArray ( homePageData.getHomePages ().getOfferModels ());
            skeletonScreenOffer.hide ();
            skeletonScreenBox.hide ();
            skeletonScreenCategories.hide ();
            skeletonScreenGadgets.hide ();
            skeletonScreenEditor.hide ();
            skeletonScreenTopGames.hide ();
            skeletonScreenblog.hide ();
            if(homePageData.getHomePages ().getBox ().size ()!=0)
                TvCategory.setVisibility ( View.VISIBLE );
            if(homePageData.getHomePages ().getGadgets_accessories ().size ()!=0)
                TvGadgets.setVisibility ( View.VISIBLE );
            if(homePageData.getHomePages ().getCollectors_editions ().size ()!=0)
                TvCollector.setVisibility ( View.VISIBLE );
            if(homePageData.getHomePages ().getTop_games ().size ()!=0)
                TvTopGames.setVisibility ( View.VISIBLE );
            Glide.with(getActivity ()  ).load ( homePageData.getHomePages ().getAdvertisementHome ().get ( 0 ).getImage () ) .placeholder ( R.drawable.placeholder ).into ( imageView6 );
        }
    }

    private void handleErrorHP(Throwable throwable) {
        mProgressDialog.dismiss ( );
        Log.e("HP","it does not work."+throwable.getMessage ());
    }


    // Banner Setup
    private void SetupSliderView(View root){
        SwipeRefreshLayout swipe=root.findViewById ( R.id.swipe );
        swipe.setOnRefreshListener ( ( ) -> {
            swipe.setRefreshing(false);

            new CountDownTimer (3000, 1000) {
                public void onTick(long millisUntilFinished) {
                }
                public void onFinish() {
                }
            }.start();
        } );
        ImageView imgSearch = root.findViewById (R.id.ImgSearch);
        et_search_box= root.findViewById(R.id.et_search_box);
        TvCategory= root.findViewById ( R.id.TvCategory );
        TvGadgets= root.findViewById ( R.id.TvGadgets );
        TvCollector= root.findViewById ( R.id.TvCollector );
        TvTopGames= root.findViewById ( R.id.TvTopGames );
        sliderView=root.findViewById(R.id.imageSlider);
        adapterBannerSlider = new AdapterBannerSlider(getContext());
        sliderView.setSliderAdapter(adapterBannerSlider);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.GRAY);
        sliderView.setIndicatorUnselectedColor(Color.parseColor ( "#d3d3d3" ));
        sliderView.setScrollTimeInSec(2); //set scroll delay in seconds :
        sliderView.startAutoCycle();
        sliderView.setOnIndicatorClickListener( position -> Log.i("GGG", "onIndicatorClicked: " + sliderView.getCurrentPagePosition()) );
        Imgcorss.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick ( View v ) {
                et_search_box.setText ("");
                Imgcorss.setVisibility (View.GONE);
            }
        });
        et_search_box.addTextChangedListener(new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged( Editable s)
            {
               if(s.toString().length ()==0){
                   Imgcorss.setVisibility (View.GONE);
               }
               else {
                   Imgcorss.setVisibility (View.VISIBLE);
               }

            }
        });
        et_search_box.setOnKeyListener(new View.OnKeyListener () {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                   // Toast.makeText(getActivity (), et_search_box.getText().toString (), Toast.LENGTH_SHORT).show();
                    if(et_search_box.getText().toString().length()<=3){
                        UtilityMethods.PrintToast(getActivity(),"Please Enter atlest 4 Character for Search",1);
                    }
                    else {
                        Intent intent = new Intent(getActivity(), CategoryListItemActivity.class);
                        intent.putExtra(ConstantVariable.SearchText,et_search_box.getText().toString());
                        startActivity(intent);
                    }
                    return true;
                }
                return false;
            }
        });
        imgSearch.setOnClickListener(v -> {
            if(et_search_box.getText().toString().length()<=3){
                UtilityMethods.PrintToast(getActivity(),"Please Enter atlest 4 Character for Search",1);
            }
            else {
                Intent intent = new Intent(getActivity(), CategoryListItemActivity.class);
                intent.putExtra(ConstantVariable.SearchText,et_search_box.getText().toString());
                startActivity(intent);
            }
        });
    }
    public void SetBanner(JsonArray home_banner)
    {
        ArrayList<String> sliderItemList = new ArrayList<>();
        for (int i = 0; i < home_banner.size(); i++) {
                sliderItemList.add ((home_banner.get (i).getAsString ()));
        }
        adapterBannerSlider.renewItems(sliderItemList);
    }

    @Override
    public void onResume ( ) {
        super.onResume ( );
    }
}