package com.augurs.yallagamers.UserInterface.profile;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import com.augurs.yallagamers.UserInterface.MainActivity;
import com.augurs.yallagamers.R;
import com.augurs.yallagamers.UserInterface.saved_cards.SavedCardsActivity;
import com.augurs.yallagamers.api_module.ApiClient;
import com.augurs.yallagamers.api_module.ApiInterface;
import com.augurs.yallagamers.api_module.DialogUtil;
import com.augurs.yallagamers.data_models.ProfilePageResponce;
import com.augurs.yallagamers.UserInterface.Order.OrderListingActivity;
import com.augurs.yallagamers.UserInterface.address.AddressListActivity;
import com.augurs.yallagamers.UserInterface.faq.FAQActivity;
import com.augurs.yallagamers.UserInterface.user_login.LoginActivity;
import com.augurs.yallagamers.utills.LoginPreferences;
import com.augurs.yallagamers.utills.UtilityMethods;
import com.google.gson.Gson;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;

public class MyProfileFragment extends Fragment {
    RelativeLayout parent_address,parent_wishlist,RlProfile,RlOrder,rlSavedCards;
    TextView tv_logout,tv_faq,tv_about_us,tv_term_of_use,tv_privacy_policy,TvUserName,TvPoint;
    LoginPreferences loginPreferences;
    ProgressDialog mProgressDialog;
    ProfilePageResponce.Data userProfileResponce;
    public static MyProfileFragment newInstance ( ) {
        return new MyProfileFragment ( );
    }
    @SuppressLint ("SetTextI18n")
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_profile, container, false);
        loginPreferences = new LoginPreferences(getActivity());
        findViewbyid(root);
        if(loginPreferences.getString ( "token" )!= null)
            getUserProfile();
        else{
            TvUserName.setText ( "Guest User" );
            RelativeLayout parent_address = root.findViewById ( R.id.parent_address );
            parent_address.setVisibility ( View.GONE );
            RelativeLayout RlProfile = root.findViewById ( R.id.RlProfile );
            RlProfile.setVisibility ( View.GONE );
        }
        clickListners();
        return root;
    }

    private void getUserProfile ( ) {
        if ( UtilityMethods.isNetworkConnected ( getActivity ( ) ) ) {
            mProgressDialog = DialogUtil.getProgressDialog ( getActivity ( ) , getResources ( ).getString ( R.string.app_DialogInfo ) , getResources ( ).getString ( R.string.app_DialogLoading ) );
            String header = Credentials.basic ( getResources ( ).getString ( R.string.user_id ) , getResources ( ).getString ( R.string.user_password ) );
            ApiInterface apiService = ApiClient.getClient ( getContext ( ) ).create ( ApiInterface.class );
            Observable < ProfilePageResponce > hpObservable = apiService.GetUserProfile ( header , "User" , "getuserinfo" , loginPreferences.getString ( "token" ) );
            hpObservable.observeOn ( AndroidSchedulers.mainThread ( ) )
                    .subscribeOn ( Schedulers.io ( ) )
                    .subscribe ( this :: handleResults , this :: handleError );
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
        TvRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i==1&&UtilityMethods.isNetworkConnected(getActivity()))
                    getUserProfile();
                else
                    GetDialogue(i);
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
    private void handleResults ( ProfilePageResponce profilePageResponce ) {
        Log.v ( "sadasd", new Gson ().toJson ( profilePageResponce ) );
        mProgressDialog.dismiss ( );
        if(profilePageResponce.getStatus ()==200){
            userProfileResponce=profilePageResponce.getData ();
            TvUserName.setText ( profilePageResponce.getData ().getFirstname ()+" "+profilePageResponce.getData ().getLastname () );
            TvPoint.setText ("Points "+ profilePageResponce.getData ().getPoint ()+"");
        }
        else{
            TvUserName.setText ( "User Name  " );
            Toast.makeText ( getActivity (),profilePageResponce.getMessage (),Toast.LENGTH_LONG ).show ();
        }

    }
    public void handleError ( Throwable throwable ) {
        mProgressDialog.dismiss ( );
        Log.e ( "User Data", "negative" + throwable.getMessage ( ) );
    }

    private void findViewbyid(View root) {
        RlOrder= root.findViewById(R.id.RlOrder);
        TvPoint= root.findViewById (R.id.TvPoint);
        parent_address = root.findViewById(R.id.parent_address);
        parent_wishlist = root.findViewById(R.id.parent_wishlist);
        tv_logout = root.findViewById(R.id.tv_logout);
        tv_faq = root.findViewById(R.id.tv_faq);
        tv_about_us = root.findViewById(R.id.tv_about_us);
        tv_term_of_use = root.findViewById(R.id.tv_term_of_use);
        tv_privacy_policy = root.findViewById(R.id.tv_privacy_policy);
        TvUserName= root.findViewById ( R.id.TvUserName );
        RlProfile= root.findViewById ( R.id.RlProfile );
        if(loginPreferences.getString ( "token" )!= null)
            tv_logout.setText ( "Logout" );
        else
            tv_logout.setText ( "Login" );
        RelativeLayout RlYallaCasah= root.findViewById (R.id.RlYallaCasah);
        RlYallaCasah.setVisibility (View.GONE);
        rlSavedCards=root.findViewById(R.id.RlYallaSavedCards);
    }

    private void clickListners() {
        rlSavedCards.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), SavedCardsActivity.class);
            startActivity(i);
        });

        RlOrder.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), OrderListingActivity.class);
            startActivity(i);
        });
        RlProfile.setOnClickListener (v -> {
            Intent i = new Intent(getActivity(), EditProfileActivity.class);
            i.putExtra ( "UserProfile",new Gson().toJson (userProfileResponce));
            startActivity(i);
        });
        parent_address.setOnClickListener(view -> {
            Intent i = new Intent(getActivity(), AddressListActivity.class);
            i.putExtra("AddressStatus",3);
            startActivity(i);
        });

        parent_wishlist.setOnClickListener(view -> {
            if( loginPreferences.getString ( "token" )!=null) {
                Intent i = new Intent (getActivity (), MainActivity.class);
                i.putExtra ("ScreenStatus", 3);
                startActivity (i);
                getActivity ().finish ();
            }
            else {
                UtilityMethods.PrintToast (getActivity (),"Please Login Before check in wishlist",1);
            }
        });

        tv_logout.setOnClickListener(view -> {
            if(loginPreferences.getString ( "token" )!= null) {
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
                TextView TvTeas = dialog.findViewById ( R.id.TvTeas );
                TvTeas.setText("Are you sure ?");
                TextView TvTesasd = dialog.findViewById ( R.id.TvTesasd );
                TvTesasd.setText("Logout");
                RelativeLayout TvText= dialog.findViewById (R.id.TvText);
                TvText.setVisibility (View.GONE);
                TextView TvRetry =  dialog.findViewById(R.id.TvRetry);
                TvRetry.setText ( "Yes" );
                TvRetry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent ( getActivity ( ) , LoginActivity.class );
                        i.putExtra ( "LoginStatus",0 );
                        loginPreferences.clear () ;
                        startActivity ( i );
                        getActivity ( ).finish ( );
                        dialog.dismiss();
                    }
                });
                TextView TvRetryNot =  dialog.findViewById(R.id.TvRetryNot);
                TvRetryNot.setText ( "No" );
                TvRetryNot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
            else {
                Intent i = new Intent ( getActivity ( ) , LoginActivity.class );
                i.putExtra ( "LoginStatus",0 );
                startActivity ( i );
            }
        });

        tv_faq.setOnClickListener(view -> {
            Intent i = new Intent(getActivity(), FAQActivity.class);
            i.putExtra ("WebviewStatus",1);
            startActivity(i);
        });

        tv_about_us.setOnClickListener(view -> {
            Intent i = new Intent(getActivity(), FAQActivity.class);
            i.putExtra ("WebviewStatus",4);
            startActivity(i);
        });
        tv_term_of_use.setOnClickListener(view -> {
            Intent i = new Intent(getActivity(), FAQActivity.class);
            i.putExtra ("WebviewStatus",2);
            startActivity(i);
        });
        tv_privacy_policy.setOnClickListener(view -> {
            Intent i = new Intent(getActivity(), FAQActivity.class);
            i.putExtra ("WebviewStatus",3);
            startActivity(i);
        });
    }
}