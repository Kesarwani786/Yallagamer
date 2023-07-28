package com.augurs.yallagamers.UserInterface.user_login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.augurs.yallagamers.UserInterface.MainActivity;
import com.augurs.yallagamers.R;
import com.augurs.yallagamers.api_module.ApiClient;
import com.augurs.yallagamers.api_module.ApiInterface;
import com.augurs.yallagamers.api_module.DialogUtil;
import com.augurs.yallagamers.data_models.PushCartToLoggedInUserModel;
import com.augurs.yallagamers.data_models.UserData;
import com.augurs.yallagamers.UserInterface.address.AddressFinalizeActivity;
import com.augurs.yallagamers.UserInterface.user_registration.RegisterActivity;
import com.augurs.yallagamers.utills.LoginPreferences;
import com.augurs.yallagamers.utills.UtilityMethods;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;

public class LoginActivity extends AppCompatActivity {
    TextView registration_click;
    EditText et_email,et_password;
    Button btn_login;
    ProgressDialog mProgressDialog;
    ImageView imgBack;
    LoginPreferences loginPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginPreferences = new LoginPreferences(LoginActivity.this);
        initViews();
    }
    private void initViews() {
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        registration_click = findViewById(R.id.registration_click);
        registration_click.setOnClickListener(onClickListener);
        btn_login = findViewById(R.id.btn_login);
        imgBack= findViewById (R.id.img_back);
        TextView toolbar_title = findViewById (R.id.toolbar_title);
        toolbar_title.setText ("User Login");
        btn_login.setOnClickListener(onClickListener);
        imgBack.setOnClickListener(onClickListener);
    }
    private final View.OnClickListener onClickListener= view -> {
        switch (view.getId()){
            case R.id.registration_click:
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra ( "LoginStatus",getIntent ().getIntExtra ( "LoginStatus",0 ) );
                startActivity(i);
                break;
            case R.id.btn_login:
                UserLogin();
                break;
            case R.id.img_back:{
                Intent intent = new Intent ( LoginActivity.this , MainActivity.class );
                intent.putExtra("ScreenStatus",1);
                startActivity ( intent );
                finish ( );
            }
        }
    };
    @Override
    public void onBackPressed () {
        Intent intent = new Intent ( LoginActivity.this , MainActivity.class );
        intent.putExtra("ScreenStatus",1);
        startActivity ( intent );
        finish ( );
    }
    private void UserLogin() {
        ApiInterface apiService = ApiClient.getClient(this).create(ApiInterface.class);
         String email = et_email.getText().toString().trim();
         String password = et_password.getText().toString().trim();
         if(email.isEmpty() && password.isEmpty()){
             Toast.makeText(this, "Please insert email and password ", Toast.LENGTH_SHORT).show();
             et_email.requestFocus();
             et_password.requestFocus();
         }
         else if(email.isEmpty()){
             Toast.makeText(this, "Please insert email ", Toast.LENGTH_SHORT).show();
             et_email.requestFocus();
         }
         else if(password.isEmpty()){
             Toast.makeText(this, "Please insert password ", Toast.LENGTH_SHORT).show();
             et_password.requestFocus();
         }
         else {
             if ( UtilityMethods.isNetworkConnected ( LoginActivity.this ) ) {
                 mProgressDialog = DialogUtil.getProgressDialog ( this , getResources ( ).getString ( R.string.app_DialogInfo ) , getResources ( ).getString ( R.string.app_DialogLoading ) );
                 String header = Credentials.basic (  getResources ( ).getString ( R.string.user_id ) , getResources ( ).getString ( R.string.user_password ) );
                 Single < UserData > userObservable = apiService.login ( header , "User" , "login" , email , password );
                 userObservable.observeOn ( AndroidSchedulers.mainThread ( ) ).subscribeOn ( Schedulers.io ( ) ).subscribe ( this :: handleResults , this :: handleError );
             }
             else {
                 GetDialogue ( 1 );
             }
         }
    }
    private void GetDialogue ( int i ) {
        Dialog dialog = new Dialog (LoginActivity.this);
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
                if(i==1&&UtilityMethods.isNetworkConnected(LoginActivity.this))
                    UserLogin();
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

    private void SwitchToHome ( ) {
        if(getIntent ().getIntExtra ( "LoginStatus",0 ) ==1){
            Intent i = new Intent ( LoginActivity.this , AddressFinalizeActivity.class );
            i.putExtra("AddressStatus",1);
            startActivity ( i );
            finish ( );
        }
        else {
            Intent i = new Intent ( LoginActivity.this , MainActivity.class );
            i.putExtra("ScreenStatus",1);
            startActivity ( i );
            finish ( );
        }
    }
    private void handleResults(UserData userData) {
        mProgressDialog.dismiss();
        if(userData.getStatus() == 200){

            if(new LoginPreferences ( LoginActivity.this ).getString ( "cart_id" )!=null){
                MoveAllCartData(userData.getToken ( ));
            }
            else {
                loginPreferences.clear () ;
                new LoginPreferences ( LoginActivity.this ).put ( "token" , userData.getToken ( ) );
                SwitchToHome();
            }
        }
        Toast.makeText(this, userData.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @SuppressLint ("CheckResult")
    private void MoveAllCartData ( String UserToken ) {
        ApiInterface apiService = ApiClient.getClient(this).create(ApiInterface.class);
        mProgressDialog = DialogUtil.getProgressDialog(this, getResources().getString(R.string.app_DialogInfo), getResources().getString(R.string.app_DialogLoading));
        String header= Credentials.basic(getResources().getString(R.string.user_id), getResources().getString(R.string.user_password));
        PushCartToLoggedInUserModel pushCartToLoggedInUserModel= new PushCartToLoggedInUserModel();
        pushCartToLoggedInUserModel.setCartId(new LoginPreferences ( LoginActivity.this ).getString ( "cart_id"));
        pushCartToLoggedInUserModel.setMethod ( "CartMoveToLogin" );
        Single< UserData > userObservable = apiService.PushCartData(header,UserToken,"Cart",pushCartToLoggedInUserModel);
        userObservable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(this::handleLoginResults,this::handleError);
    }

    private  void handleLoginResults( UserData userData){
        mProgressDialog.dismiss ();
        loginPreferences.clear () ;
        new LoginPreferences ( LoginActivity.this ).put ( "cart_id" ,null );
        new LoginPreferences ( LoginActivity.this ).put ( "token" , userData.getToken () );
        SwitchToHome ( );
        //Toast.makeText ( LoginActivity.this,userData.getMessage (),Toast.LENGTH_LONG ).show ();
    }

    private void handleError(Throwable throwable) {
        mProgressDialog.dismiss();
        Log.e("User Data","negative"+throwable.getMessage());
        Toast.makeText(this, "Email or Password doesn't matched. Please try another one.", Toast.LENGTH_LONG).show();
    }

    private void reSendOtp(String token) {
        ApiInterface apiService = ApiClient.getClient(this).create(ApiInterface.class);
        mProgressDialog = DialogUtil.getProgressDialog(this, getResources().getString(R.string.app_DialogInfo), getResources().getString(R.string.app_DialogLoading));
        String header= Credentials.basic("augurstest@gmail.com", getResources().getString(R.string.user_password));
        Single<UserData> userObservable = apiService.resendOtp(header,"User","resend_otp",token);
        userObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResults,this::handleError);
    }

}