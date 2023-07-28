package com.augurs.yallagamers.UserInterface.update_pass;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.augurs.yallagamers.UserInterface.MainActivity;
import com.augurs.yallagamers.R;
import com.augurs.yallagamers.api_module.ApiClient;
import com.augurs.yallagamers.api_module.ApiInterface;
import com.augurs.yallagamers.api_module.BaseResponse;
import com.augurs.yallagamers.api_module.DialogUtil;
import com.augurs.yallagamers.utills.LoginPreferences;
import com.augurs.yallagamers.utills.UtilityMethods;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;
import okhttp3.RequestBody;

public class UpdatePassActivity extends AppCompatActivity {
    private EditText etPass,etCPass;

    private ImageView img_back;
    private TextView toolbar_title;
    private ProgressDialog mProgressDialog;
    private Context context;
    private Button btnSaveDetails;
    private String first_name,last_name,email,phone,add_one,add_two,city,state,country,zip_code,profile_name,profile_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pass);
        context= UpdatePassActivity.this;
        toolbar_title= findViewById ( R.id.toolbar_title );
        toolbar_title.setText ( "Update Password" );

        GetId();
        ClickEvent();
    }



    private void ClickEvent ( ) {

        img_back.setOnClickListener ( v -> {
            onBackPressed ();
        } );

        btnSaveDetails.setOnClickListener(view -> {
            SaveDetails();
        });


    }

    private void GetId ( ) {
        toolbar_title= findViewById ( R.id.toolbar_title );
        img_back= findViewById ( R.id.img_back );
        etPass = findViewById ( R.id.etPass );
        etCPass = findViewById ( R.id.etCPass );
        btnSaveDetails = findViewById(R.id.btn_save);
    }

    private void SaveDetails() {

        String pass=etPass.getText().toString();
        String cpass=etCPass.getText().toString();



        if(pass.isEmpty()){
            UtilityMethods.PrintToast (this, "Password field can not be empty.", 1);
            etPass.requestFocus();
        }else if(cpass.isEmpty()){
            UtilityMethods.PrintToast (this, "Confirm Password field can not be empty.", 1);
            etCPass.requestFocus();
        }    else  if(!cpass.equals(pass)){
            UtilityMethods.PrintToast (this, "Passwords Did Not Match", 1);
            etPass.requestFocus();
        }

        else {
            JSONObject user_data= new JSONObject();
            JSONObject key_val= new JSONObject();
            try {
                key_val.put("password1",pass);
                key_val.put("password2",cpass);

                user_data.put("user_data",key_val);
                user_data.put("method","updateProfilePassword");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("user_data","=>"+user_data.toString());


            if ( UtilityMethods.isNetworkConnected ( context ) ) {
                mProgressDialog = DialogUtil.getProgressDialog ( context , getResources ( ).getString ( R.string.app_DialogInfo ) , getResources ( ).getString ( R.string.app_DialogLoading ) );
                String _d = "User";
                String token = new LoginPreferences( context ).getString ( "token" );
                String header = Credentials.basic ( getResources ( ).getString ( R.string.user_id ) , getResources ( ).getString ( R.string.user_password ) );
                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),user_data.toString());
                ApiInterface apiService = ApiClient.getClient ( context ).create ( ApiInterface.class );
                Single<BaseResponse> hpObservable = apiService.UpdatePassword ( header , _d , token,body );
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
        UtilityMethods.PrintToast (this, "There is a problem in updating password. Please contact to Admin!!", 1);
        Log.e("SD","it does not work."+throwable.getMessage ());
    }

    private void handleResultsSD(BaseResponse baseResponse) {
        mProgressDialog.dismiss ( );
        if(baseResponse.getStatus()==200){
            UtilityMethods.PrintToast (this, "Password has been updated successfully.", 2);
            Intent i = new Intent (UpdatePassActivity.this, MainActivity.class);
            i.putExtra ("ScreenStatus", 3);
            startActivity (i);
        }
        etPass.setText("");
        etCPass.setText("");
        Log.e("SD","it's work.-"+baseResponse.getMessage());
    }
}