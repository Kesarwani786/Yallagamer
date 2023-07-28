package com.augurs.yallagamers.UserInterface.aboutus;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.augurs.yallagamers.R;
import com.augurs.yallagamers.api_module.ApiClient;
import com.augurs.yallagamers.api_module.ApiInterface;
import com.augurs.yallagamers.data_models.Aboutus;
import com.augurs.yallagamers.utills.LoginPreferences;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;

public class AboutusActivity extends AppCompatActivity {
    WebView webview_faq;
    ProgressDialog mProgressDialog;
    TextView toolbar_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        webview_faq =findViewById(R.id.webview_faq);
        toolbar_title =findViewById(R.id.toolbar_title);
        toolbar_title.setText(getResources().getString(R.string.layout_profile_about_us));
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        loadAboutusData();
    }

    @SuppressLint("CheckResult")
    private void loadAboutusData() {
        LoginPreferences loginPreferences = new LoginPreferences(AboutusActivity.this);
        ApiInterface apiService = ApiClient.getClient(this).create(ApiInterface.class);
        String header= Credentials.basic(getResources().getString(R.string.user_id), getResources().getString(R.string.user_password));
        Single<Aboutus> userObservable = apiService.getAboutus(header,"Page","aboutus");
        userObservable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this::handleResults,this::handleError);
    }

    private void handleResults(Aboutus aboutus) {
       // mProgressDialog.dismiss();
        if(aboutus.getStatus() == 200){
            webview_faq.loadData(aboutus.getData().get(0).getDescription(), "text/html", "UTF-8");
        }

    }

    private void handleError(Throwable throwable) {
      //  mProgressDialog.dismiss();
        Log.e("User Data","negative"+throwable.getMessage());
    }
}