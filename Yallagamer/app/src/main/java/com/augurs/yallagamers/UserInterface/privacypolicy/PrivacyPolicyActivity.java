package com.augurs.yallagamers.UserInterface.privacypolicy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.augurs.yallagamers.R;
import com.augurs.yallagamers.api_module.ApiClient;
import com.augurs.yallagamers.api_module.ApiInterface;
import com.augurs.yallagamers.data_models.PrivacyPolicy;
import com.augurs.yallagamers.utills.LoginPreferences;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;

public class PrivacyPolicyActivity extends AppCompatActivity {
    WebView webview_faq;
    ProgressDialog mProgressDialog;
    TextView toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        webview_faq =findViewById(R.id.webview_faq);
        toolbar_title =findViewById(R.id.toolbar_title);
        toolbar_title.setText(getResources().getString(R.string.layout_profile_privacy_policy));
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        loadPrivacyPolicyData();
    }

    private void loadPrivacyPolicyData() {
     /*   mProgressDialog = DialogUtil.getProgressDialog(this, getResources().getString(R.string.app_DialogInfo), getResources().getString(R.string.app_DialogLoading));*/
        LoginPreferences loginPreferences = new LoginPreferences(PrivacyPolicyActivity.this);
        String token = loginPreferences.getString("token");
        ApiInterface apiService = ApiClient.getClient(this).create(ApiInterface.class);
        String header= Credentials.basic("augurstest@gmail.com", getResources().getString(R.string.user_password));
        Single<PrivacyPolicy> userObservable = apiService.getPrivacyPolicy(header,"Page","aboutus");
        userObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResults,this::handleError);
    }

    private void handleResults(PrivacyPolicy privacyPolicy) {
        Log.e("User Data","positive");
        Log.e("User Data",""+ privacyPolicy.getStatus());
    /*    mProgressDialog.dismiss();*/
        if(privacyPolicy.getStatus() == 200){
            webview_faq.loadData(privacyPolicy.getData().get(0).getDescription(), "text/html", "UTF-8");
        }

    }

    private void handleError(Throwable throwable) {
        mProgressDialog.dismiss();
        Log.e("User Data","negative"+throwable.getMessage());
    }
}