package com.augurs.yallagamers.UserInterface.termofuse;

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
import com.augurs.yallagamers.data_models.Termofuse;
import com.augurs.yallagamers.utills.LoginPreferences;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;

public class TermofUseActivity extends AppCompatActivity {
    WebView webview_faq;
    ProgressDialog mProgressDialog;
    TextView toolbar_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termof_use);
        webview_faq =findViewById(R.id.webview_faq);
        toolbar_title =findViewById(R.id.toolbar_title);
        toolbar_title.setText(getResources().getString(R.string.layout_profile_term_of_use));
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        loadTermofUseData();
    }

    private void loadTermofUseData() {
/*
        mProgressDialog = DialogUtil.getProgressDialog(this, getResources().getString(R.string.app_DialogInfo), getResources().getString(R.string.app_DialogLoading));
*/
        LoginPreferences loginPreferences = new LoginPreferences(TermofUseActivity.this);
        String token = loginPreferences.getString("token");
        ApiInterface apiService = ApiClient.getClient(this).create(ApiInterface.class);
        String header= Credentials.basic(getResources().getString(R.string.user_id), getResources().getString(R.string.user_password));
        Single<Termofuse> userObservable = apiService.getTermOfUse(header,"Page","terms");
        userObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResults,this::handleError);
    }


    private void handleResults(Termofuse termofuse) {
        Log.e("User Data","positive");
        Log.e("User Data",""+ termofuse.getStatus());
     //   mProgressDialog.dismiss();
        if(termofuse.getStatus() == 200){
            webview_faq.loadData(termofuse.getData().get(0).getDescription(), "text/html", "UTF-8");
        }

    }

    private void handleError(Throwable throwable) {
      //  mProgressDialog.dismiss();
        Log.e("User Data","negative"+throwable.getMessage());
    }
}