package com.augurs.yallagamers.UserInterface.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.augurs.yallagamers.UserInterface.MainActivity;
import com.augurs.yallagamers.R;
import com.augurs.yallagamers.UserInterface.update_pass.UpdatePassActivity;
import com.augurs.yallagamers.api_module.ApiClient;
import com.augurs.yallagamers.api_module.ApiInterface;
import com.augurs.yallagamers.api_module.BaseResponse;
import com.augurs.yallagamers.api_module.DialogUtil;
import com.augurs.yallagamers.data_models.ProfileDataResponce;
import com.augurs.yallagamers.utills.LoginPreferences;
import com.augurs.yallagamers.utills.UtilityMethods;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;
import okhttp3.RequestBody;

public class EditProfileActivity extends AppCompatActivity {

    private EditText etMobile,etFirstName,etLastName,etEmail,etAddOne,etAddTwo,etCity,etZip,etState,etCountry;
    private RelativeLayout RlMale,RlFeMale;
    private ImageView imgMale,imgFemale,img_back;
    private TextView toolbar_title,tv_profile_name,view_change_pass;
    private ProgressDialog mProgressDialog;
    private Context context;
    private Button btnSaveDetails;
    private String first_name,last_name,email,phone,add_one,add_two,city,state,country,zip_code,profile_name,profile_id;
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_edit_profile );
        context=EditProfileActivity.this;
        toolbar_title= findViewById ( R.id.toolbar_title );
        toolbar_title.setText ( "Edit Profile" );
        GetId();
        GetProfileData();
        ClickEvent();
    }

    private void ClickEvent ( ) {
        RlMale.setOnClickListener ( v -> {
            imgMale.setVisibility ( View.VISIBLE );
            imgFemale.setVisibility ( View.GONE );
        } );
        RlFeMale.setOnClickListener ( v -> {
            imgFemale.setVisibility ( View.VISIBLE );
            imgMale.setVisibility ( View.GONE );
        } );
        img_back.setOnClickListener ( v -> {
            onBackPressed ();
        } );
        btnSaveDetails.setOnClickListener(view -> {
            SaveDetails();
        });

        view_change_pass.setOnClickListener(view -> {
            startActivity(new Intent(context, UpdatePassActivity.class));
        });
    }

    private void SaveDetails() {

        String fname=etFirstName.getText().toString();
        String lname=etLastName.getText().toString();
        String uphone=etMobile.getText().toString();
        String badd1=etAddOne.getText().toString();
        String badd2=etAddTwo.getText().toString();
        String bcity=etCity.getText().toString();
        String bstate=etState.getText().toString();
        String bcountry=etCountry.getText().toString();
        String bzipcode=etZip.getText().toString();


        if(fname.isEmpty()){
            Toast.makeText(context, "First name can not be empty.", Toast.LENGTH_SHORT).show();
            etFirstName.requestFocus();
        }else if(lname.isEmpty()){
            Toast.makeText(context, "Last name can not be empty.", Toast.LENGTH_SHORT).show();
            etLastName.requestFocus();
        }else if(uphone.isEmpty()){
            Toast.makeText(context, "Phone number can not be empty.", Toast.LENGTH_SHORT).show();
            etMobile.requestFocus();
        }else if(badd1.isEmpty()){
            Toast.makeText(context, "Address line 1 can not be empty.", Toast.LENGTH_SHORT).show();
            etAddOne.requestFocus();
        }else if(bcity.isEmpty()){
            Toast.makeText(context, "City can not be empty.", Toast.LENGTH_SHORT).show();
            etCity.requestFocus();
        }else if(bcountry.isEmpty()){
            Toast.makeText(context, "Please select country.", Toast.LENGTH_SHORT).show();

        }else if(bstate.isEmpty()){
            Toast.makeText(context, "Please select state/provenance.", Toast.LENGTH_SHORT).show();
        }else if(bzipcode.isEmpty()){
            Toast.makeText(context, "Zip code can not be empty.", Toast.LENGTH_SHORT).show();
            etZip.requestFocus();
        }
        else if(bzipcode.length ()!=6){
            Toast.makeText(context, "Please enter the valid Zip code.", Toast.LENGTH_SHORT).show();
            etZip.requestFocus();
        }else {
            JSONObject user_data= new JSONObject();
            JSONObject key_val= new JSONObject();
            try {
                key_val.put("email",email);
                key_val.put("phone",uphone);
                key_val.put("firstname",fname);
                key_val.put("lastname",lname);
                key_val.put("profile_id",profile_id);
                key_val.put("profile_name",profile_name);
                key_val.put("s_address",badd1);
                key_val.put("s_address_2",badd2);
                key_val.put("s_city",bcity);
                key_val.put("s_country",bcountry);
                key_val.put("s_state",bstate);
                key_val.put("s_zipcode",bzipcode);
                user_data.put("user_data",key_val);
                user_data.put("method","updateProfiles");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("user_data","=>"+user_data.toString());


            if ( UtilityMethods.isNetworkConnected ( context ) ) {
                mProgressDialog = DialogUtil.getProgressDialog ( context , getResources ( ).getString ( R.string.app_DialogInfo ) , getResources ( ).getString ( R.string.app_DialogLoading ) );
                String _d = "User";
                String token = new LoginPreferences ( context ).getString ( "token" );
                String header = Credentials.basic ( getResources ( ).getString ( R.string.user_id ) , getResources ( ).getString ( R.string.user_password ) );
                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),user_data.toString());
                ApiInterface apiService = ApiClient.getClient ( context ).create ( ApiInterface.class );
                Single<BaseResponse> hpObservable = apiService.SaveProfileData ( header , _d , token,body );

                // Subscribe Observer
                hpObservable.observeOn ( AndroidSchedulers.mainThread ( ) )
                        .subscribeOn ( Schedulers.io ( ) )
                        .observeOn ( AndroidSchedulers.mainThread ( ) )
                        .subscribe ( this :: handleResultsSD , this :: handleErrorSD );
            }

        }


    }

    private void handleErrorSD(Throwable throwable) {
        mProgressDialog.dismiss ( );

        Toast.makeText(context, "There is a problem in updating profile. Please contact to Admin!!", Toast.LENGTH_SHORT).show();

        Log.e("SD","it does not work."+throwable.getMessage ());
    }

    private void handleResultsSD(BaseResponse baseResponse) {
        mProgressDialog.dismiss ( );
        if(baseResponse.getStatus()==200){
            Toast.makeText(context, "Profile update successfully.", Toast.LENGTH_SHORT).show();
        }
        Log.e("SD","it's work.-"+baseResponse.getMessage());
        tv_profile_name.setText ( etFirstName.getText().toString() + " " + etLastName.getText().toString() );

    }

    /*@SuppressLint ("SetTextI18n")
    private void GetDataAndSetData ( ) {
        toolbar_title= findViewById ( R.id.toolbar_title );
        toolbar_title.setText ( "Edit Profile" );
        if(new LoginPreferences (EditProfileActivity.this).getString ( "token" )!= null) {
            ProfilePageResponce.Data profileResponce = new Gson ( ).fromJson ( new JsonParser ( ).parse ( getIntent ( ).getStringExtra ( "UserProfile" ) ) , ProfilePageResponce.Data.class );
            tv_profile_name.setText ( profileResponce.getFirstname ( ) + " " + profileResponce.getLastname ( ) );
            etMobile.setText ( profileResponce.getPhone ( ) + " " );
            etEmail.setText ( profileResponce.getEmail ( ) + "" );

        }
    }*/

    private void GetProfileData ( ) {
        if ( UtilityMethods.isNetworkConnected ( context ) ) {
            mProgressDialog = DialogUtil.getProgressDialog ( context , getResources ( ).getString ( R.string.app_DialogInfo ) , getResources ( ).getString ( R.string.app_DialogLoading ) );
            String _d1 = "User";
            String method = "Getprofiles";
            String header = Credentials.basic ( getResources ( ).getString ( R.string.user_id ) , getResources ( ).getString ( R.string.user_password ) );
            ApiInterface apiService = ApiClient.getClient ( context ).create ( ApiInterface.class );
            Observable<ProfileDataResponce> hpObservable = apiService.GetProfileData ( header , _d1 , method , new LoginPreferences ( context ).getString ( "token" ),new LoginPreferences ( context ).getString ( "profile_id" ) );

            // Subscribe Observer
            hpObservable.observeOn ( AndroidSchedulers.mainThread ( ) )
                    .subscribeOn ( Schedulers.io ( ) )
                    .observeOn ( AndroidSchedulers.mainThread ( ) )
                    .subscribe ( this :: handleResultsPD , this :: handleErrorPD );
        }
        else {
            GetDialogue(1);
        }
    }

    private void handleErrorPD(Throwable throwable) {
        mProgressDialog.dismiss ( );
        Log.e("HP","it does not work."+throwable.getMessage ());
    }

    private void handleResultsPD(ProfileDataResponce profileDataResponce) {
        mProgressDialog.dismiss ( );
        Log.e("HP","it's work.-"+profileDataResponce.getData().getRawdata().getFirstname());

        tv_profile_name.setText ( profileDataResponce.getData().getRawdata().getFirstname ( ) + " " + profileDataResponce.getData().getRawdata().getLastname ( ) );

        etMobile.setText(profileDataResponce.getData().getRawdata().getPhone());
        etFirstName.setText(profileDataResponce.getData().getRawdata().getFirstname());
        etLastName.setText(profileDataResponce.getData().getRawdata().getLastname());
        etEmail.setText(profileDataResponce.getData().getRawdata().getEmail());
        etAddOne.setText(profileDataResponce.getData().getRawdata().getS_address());
        etAddTwo.setText(profileDataResponce.getData().getRawdata().getS_address_2());
        etCity.setText(profileDataResponce.getData().getRawdata().getS_city());
        etZip.setText(profileDataResponce.getData().getRawdata().getS_zipcode());
        etState.setText(profileDataResponce.getData().getRawdata().getS_state());
        etCountry.setText(profileDataResponce.getData().getRawdata().getS_country());

        first_name=profileDataResponce.getData().getRawdata().getFirstname();
        last_name=profileDataResponce.getData().getRawdata().getLastname();
        email=profileDataResponce.getData().getRawdata().getEmail();
        phone=profileDataResponce.getData().getRawdata().getPhone();
        add_one=profileDataResponce.getData().getRawdata().getS_address();
        add_two=profileDataResponce.getData().getRawdata().getS_address_2();
        city=profileDataResponce.getData().getRawdata().getS_city();
        state=profileDataResponce.getData().getRawdata().getS_state();
        country=profileDataResponce.getData().getRawdata().getS_country();
        zip_code=profileDataResponce.getData().getRawdata().getS_zipcode();

        profile_id=profileDataResponce.getData().getRawdata().getProfile_id();
        profile_name=profileDataResponce.getData().getRawdata().getProfile_name();


    }


    private void GetDialogue ( int i ) {
        Dialog dialog = new Dialog (context);
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        Window window = dialog.getWindow ( );
        WindowManager.LayoutParams wlp = window.getAttributes ( );
        window.setLayout ( ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT );
        dialog.getWindow ( ).addFlags ( WindowManager.LayoutParams.FLAG_DIM_BEHIND );
        dialog.getWindow ( ).setBackgroundDrawable ( new ColorDrawable( android.graphics.Color.TRANSPARENT ) );
        window.setAttributes ( wlp );
        TextView TvRetry =  dialog.findViewById(R.id.TvRetry);
        TvRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i==1&& UtilityMethods.isNetworkConnected(context))
                    GetProfileData();
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

    private void GetId ( ) {

        toolbar_title= findViewById ( R.id.toolbar_title );

        tv_profile_name = findViewById ( R.id.tv_profile_name );
        etMobile = findViewById ( R.id.etMobile );
        etFirstName = findViewById ( R.id.etFirstName );
        etLastName = findViewById ( R.id.etLastName );
        etEmail = findViewById ( R.id.etEmail );
        imgMale= findViewById ( R.id.imgMale );
        imgFemale= findViewById ( R.id.imgFemale );
        RlMale= findViewById ( R.id.RlMale );
        RlFeMale= findViewById ( R.id.RlFeMale );
        img_back= findViewById ( R.id.img_back );

        etAddOne = findViewById ( R.id.etAddress_one );
        etAddTwo = findViewById ( R.id.etAddress_two );
        etCity = findViewById ( R.id.etCity );
        etZip = findViewById ( R.id.etZipCode );
        etState = findViewById ( R.id.etState );
        etCountry = findViewById ( R.id.etCountry );
        btnSaveDetails = findViewById(R.id.btn_save);

        view_change_pass= findViewById(R.id.view_change_pass);
    }

    @Override
    public void onBackPressed () {
        Intent i = new Intent (EditProfileActivity.this, MainActivity.class);
        i.putExtra ("ScreenStatus",4);
        startActivity (i);
    }
}