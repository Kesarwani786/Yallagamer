package com.augurs.yallagamers.UserInterface.user_registration;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
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
import com.augurs.yallagamers.UserInterface.user_login.LoginActivity;
import com.augurs.yallagamers.utills.LoginPreferences;
import com.augurs.yallagamers.utills.UtilityMethods;
import com.google.gson.Gson;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;

public class RegisterActivity extends AppCompatActivity {
    EditText et_email,et_password,et_first_name,et_last_name,et_mobile,et_confirm_password;
    Button btn_signup;
    ProgressDialog mProgressDialog;
    TextView click_login;
    String UserToken="";
    boolean StatusPassword=true;
    boolean StatusCPassword=true;
    CountryCodePicker ccp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.v("asdasd",getIntent ().getIntExtra ( "LoginStatus",0 )+"" );
        initViews();
    }
    private void initViews(){
        //input views
        ccp=(CountryCodePicker)findViewById(R.id.ccp);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_first_name = findViewById(R.id.et_first_name);
        et_last_name = findViewById(R.id.et_last_name);
        et_mobile = findViewById(R.id.et_mobile);
        et_confirm_password = findViewById(R.id.et_confirm_password);
        ImageView imgEye= findViewById ( R.id.imgEye );
        TextView toolbar_title = findViewById (R.id.toolbar_title);
        toolbar_title.setText ("User Registration");
        ImageView imgBack= findViewById (R.id.img_back);
        imgBack.setOnClickListener (v -> {
            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra ( "LoginStatus",getIntent ().getIntExtra ( "LoginStatus",0 ) );
            startActivity(i);
        });
        imgEye.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                if(StatusPassword){
                    et_password.setTransformationMethod( HideReturnsTransformationMethod.getInstance());
                    imgEye.setImageResource ( R.drawable.eye );
                }
                else {
                    et_password.setTransformationMethod( PasswordTransformationMethod.getInstance());
                    imgEye.setImageResource ( R.drawable.hidden );
                }
                StatusPassword=!StatusPassword;
            }
        } );
        ImageView imgEye1= findViewById ( R.id.imgEye1 );
        imgEye1.setOnClickListener (v -> {
            if(StatusCPassword){
                et_confirm_password.setTransformationMethod( HideReturnsTransformationMethod.getInstance());
                imgEye1.setImageResource ( R.drawable.eye );
            }
            else {
                et_confirm_password.setTransformationMethod( PasswordTransformationMethod.getInstance());
                imgEye1.setImageResource ( R.drawable.hidden );
            }
            StatusCPassword=!StatusCPassword;
        });
        //Click views
        click_login = findViewById(R.id.click_login);
        click_login.setOnClickListener(onClickListener);
        btn_signup = findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(onClickListener);

    }

    private final View.OnClickListener onClickListener= view -> {
        switch (view.getId()){
            case R.id.click_login:
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                i.putExtra ( "LoginStatus",getIntent ().getIntExtra ( "LoginStatus",0 ) );
                startActivity(i);
                break;
            case R.id.btn_signup:
                SaveUser();
                break;
            default:
                break;
        }
    };


    private void SaveUser() {

        ApiInterface apiService = ApiClient.getClient(this).create(ApiInterface.class);
        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String confirm_password = et_confirm_password.getText().toString().trim();
        String first_name = et_first_name.getText().toString().trim();
        String last_name = et_last_name.getText().toString().trim();
        String mobile = et_mobile.getText().toString().trim();
        if(first_name.length()==0&&last_name.length()==0&&email.length()==0&&mobile.length()==0&&password.length()==0&&confirm_password.length()==0){
            UtilityMethods.PrintToast (this, "Please insert all the Required field.", 1);
        }
        else if(first_name.isEmpty()){
            UtilityMethods.PrintToast (this, "Please insert first name", 1);
            et_first_name.requestFocus();
        }
        else if(last_name.isEmpty()){
            UtilityMethods.PrintToast (this, "Please insert last name", 1);
            et_last_name.requestFocus();
        }
        else if(email.isEmpty()){
            UtilityMethods.PrintToast (this, "Please insert email", 1);
            et_email.requestFocus();
        }
        else if(!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
            UtilityMethods.PrintToast (this, "Please insert a an valid email", 1);
            et_email.requestFocus();
        }
        else if(mobile.isEmpty()){
            UtilityMethods.PrintToast (this, "Please insert mobile no.", 1);
            et_mobile.requestFocus();
        }
        else  if(password.isEmpty()){
            UtilityMethods.PrintToast (this, "Password field can not be empty.", 1);
            et_password.requestFocus();
        }
        else  if(confirm_password.isEmpty()){
            UtilityMethods.PrintToast (this, "Confirm Password field can not be empty.", 1);
            et_confirm_password.requestFocus();
        }
        else  if(!confirm_password.equals(password)){
            UtilityMethods.PrintToast (this, "Passwords Did Not Match", 1);
            et_confirm_password.requestFocus();
        }
        else if(password.length ()<=5){
            UtilityMethods.PrintToast (this, "Your password must be at least 6 characters long.", 1);
            et_password.requestFocus();
        }
         else{
             String mobile_with_code = "+"+ccp.getSelectedCountryCode()+""+mobile;
             mProgressDialog = DialogUtil.getProgressDialog(this, getResources().getString(R.string.app_DialogInfo), getResources().getString(R.string.app_DialogLoading));
             String header= Credentials.basic(getResources().getString(R.string.user_id), getResources().getString(R.string.user_password));
             Single<UserData> userObservable = apiService.register(header,"User",first_name,last_name,password,confirm_password,email,mobile_with_code);
             userObservable.observeOn(AndroidSchedulers.mainThread())
                     .subscribeOn(Schedulers.io())
                     .observeOn(AndroidSchedulers.mainThread())
                     .subscribe(this::handleResults,this::handleError);
         }
    }

    private  void handleLoginResults( UserData userData){
        mProgressDialog.dismiss ();
       if(userData.getStatus ()==200) {
           new LoginPreferences ( RegisterActivity.this ).clear ();
            new LoginPreferences ( RegisterActivity.this ).put ( "cart_id" ,null );
            new LoginPreferences ( RegisterActivity.this ).put ( "token" , userData.getToken () );
           Toast.makeText(this, userData.getMessage (), Toast.LENGTH_SHORT).show();
            SwitchToHome ( );
        }
        else{
           Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
           i.putExtra ( "LoginStatus",getIntent ().getIntExtra ( "LoginStatus",0 ) );
           startActivity(i);
       }

    }
    private void handleResults(UserData users) {
        mProgressDialog.dismiss();
        if(users.getStatus() == 200){
                 if(new LoginPreferences ( RegisterActivity.this ).getString ( "cart_id" )!=null){
                    UserToken=users.getToken ( );
                    MoveAllCartData();
                }
                else {
                    new LoginPreferences ( RegisterActivity.this ).clear ();
                    new LoginPreferences ( RegisterActivity.this ).put ( "token" , users.getToken ( ) );
                    SwitchToHome();
                }
        }

        Toast.makeText(this, users.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void MoveAllCartData (  ) {
        ApiInterface apiService = ApiClient.getClient(this).create(ApiInterface.class);
        mProgressDialog = DialogUtil.getProgressDialog(this, getResources().getString(R.string.app_DialogInfo), getResources().getString(R.string.app_DialogLoading));
        String header= Credentials.basic(getResources().getString(R.string.user_id), getResources().getString(R.string.user_password));
        PushCartToLoggedInUserModel pushCartToLoggedInUserModel= new PushCartToLoggedInUserModel();
        pushCartToLoggedInUserModel.setCartId(new LoginPreferences ( RegisterActivity.this ).getString ( "cart_id"));
        pushCartToLoggedInUserModel.setMethod ( "CartMoveToLogin" );
        Log.v("pushCartToLo",new Gson().toJson ( pushCartToLoggedInUserModel ) );
        Single< UserData > userObservable = apiService.PushCartData(header,UserToken,"Cart",pushCartToLoggedInUserModel);
        userObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleLoginResults,this::handleError);
    }

    private void SwitchToHome ( ) {
        if(getIntent ().getIntExtra ( "LoginStatus",0 ) ==1){
            Intent i = new Intent ( RegisterActivity.this , AddressFinalizeActivity.class );
            i.putExtra("AddressStatus",1);
            startActivity ( i );
            finish ( );
        }
        else {
            Intent i = new Intent ( RegisterActivity.this , MainActivity.class );
            i.putExtra("ScreenStatus",1);
            startActivity ( i );
            finish ( );
        }
    }

    @Override
    public void onBackPressed () {
        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra ( "LoginStatus",getIntent ().getIntExtra ( "LoginStatus",0 ) );
        startActivity(i);
    }

    private void handleError( Throwable throwable) {
        mProgressDialog.dismiss();
        Log.e("User Data","negative"+throwable.getMessage());
        Toast.makeText(this, "Error -"+throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
