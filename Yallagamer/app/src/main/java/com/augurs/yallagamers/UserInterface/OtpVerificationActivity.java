package com.augurs.yallagamers.UserInterface;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.augurs.yallagamers.R;
import com.augurs.yallagamers.api_module.ApiClient;
import com.augurs.yallagamers.api_module.ApiInterface;
import com.augurs.yallagamers.api_module.DialogUtil;
import com.augurs.yallagamers.data_models.UserData;
import com.augurs.yallagamers.UserInterface.user_login.LoginActivity;
import com.augurs.yallagamers.utills.LoginPreferences;

import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;

public class OtpVerificationActivity extends AppCompatActivity {
    TextView tv_otp_counter;
    private OtpTextView otpTextView;
    Button btn_verify;
    String otp = "";
    ProgressDialog mProgressDialog;
    String token;
    TextView tv_resend;
    String action_type = "";
    LoginPreferences loginPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
         tv_otp_counter = findViewById(R.id.tv_otp_counter);
         otpTextView = findViewById(R.id.otp_view);
         btn_verify = findViewById(R.id.btn_verify);
         tv_resend = findViewById(R.id.tv_resend);
         token = getIntent().getStringExtra("token");
         loginPreferences = new LoginPreferences(OtpVerificationActivity.this);
          otpTextView.setOtpListener(new OTPListener() {
             @Override
             public void onInteractionListener() {
                 // fired when user types something in the Otpbox
                 otp = otpTextView.getOTP().trim();
             }
             @Override
             public void onOTPComplete(String OTP) {
             }
         });


         btn_verify.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if(otp.length() == 4){
                     action_type = "verify";
                     verifyOtp(token,otp);
                 }
                 else {
                     Toast.makeText(OtpVerificationActivity.this, "Please insert 4 Digit Code.", Toast.LENGTH_SHORT).show();
                 }
             }
         });

        tv_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action_type = "resend";
                reSendOtp(token);
            }
        });


         setOtpCounter();
    }

    private void verifyOtp(String token,String otp) {
        ApiInterface apiService = ApiClient.getClient(this).create(ApiInterface.class);
        mProgressDialog = DialogUtil.getProgressDialog(this, getResources().getString(R.string.app_DialogInfo), getResources().getString(R.string.app_DialogLoading));
        String header= Credentials.basic("augurstest@gmail.com", getResources().getString(R.string.user_password));
        Single<UserData> userObservable = apiService.otpVerificaiton(header,"User","verify_otp",token,otp);
        userObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResults,this::handleError);
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

    private void setOtpCounter() {
        CountDownTimer countDownTimer = new CountDownTimer(60000,1) {
            @Override
            public void onTick(long time_in_milisec) {
                int seconds;
                int minutes;
                String Sec = "";
                String Min = "";
                minutes = (int)(time_in_milisec / 1000)  / 60;
                seconds = (int)((time_in_milisec / 1000) % 60);
                if(minutes < 10)
                    Min = "0"+minutes;
                else
                    Min = ""+minutes;

                if(seconds < 10)
                    Sec = "0"+seconds;
                else
                    Sec = ""+seconds;

                tv_resend.setClickable(false);
                tv_otp_counter.setText(Min+":"+Sec);
            }

            @Override
            public void onFinish() {
                tv_resend.setClickable(true);
                tv_otp_counter.setVisibility(View.GONE);
            }
        };
        countDownTimer.start();

    }

    private void handleResults(UserData users) {
        mProgressDialog.dismiss();
        Log.e("User Data","positive");

      /*  Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        finish();*/
        if(users.getStatus() == 200 && action_type.equals("verify")){
           if(getIntent().getBooleanExtra("callfromlogin",false)){
               loginPreferences.put("isLogged",true);
               Intent i = new Intent(OtpVerificationActivity.this, MainActivity.class);
               i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
               startActivity(i);
               finish();
           }
           else{
               Intent i = new Intent(OtpVerificationActivity.this, LoginActivity.class);
               i.putExtra ( "LoginStatus",0 );
               i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
               startActivity(i);
               finish();
           }
        }
        else if(users.getStatus() == 200 && action_type.equals("resend")){
            tv_resend.setClickable(false);
            tv_otp_counter.setVisibility(View.VISIBLE);
            setOtpCounter();
        }


        Toast.makeText(this, ""+users.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void handleError(Throwable throwable) {
        mProgressDialog.dismiss();
        Log.e("User Data","negative"+throwable.getMessage());
        Toast.makeText(this, "Email or Password doesn't matched. Please try another one.", Toast.LENGTH_LONG).show();
    }
}