package com.augurs.yallagamers.UserInterface.faq;

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
import com.augurs.yallagamers.api_module.DialogUtil;
import com.augurs.yallagamers.data_models.Faq;
import com.augurs.yallagamers.utills.UtilityMethods;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;

public class FAQActivity extends AppCompatActivity {
    WebView webview_faq;
    ProgressDialog mProgressDialog;
    TextView toolbar_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        webview_faq =findViewById(R.id.webview_faq);
        toolbar_title =findViewById(R.id.toolbar_title);

        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        if(getIntent ().getIntExtra ("WebviewStatus",0)==1) {
            toolbar_title.setText("FAQ's");
            loadFaqData ("faq");
        }
        else if(getIntent ().getIntExtra ("WebviewStatus",0)==2) {
            toolbar_title.setText("Terms and Conditions");
            loadFaqData ("terms");
        }
        else if(getIntent ().getIntExtra ("WebviewStatus",0)==3) {
            toolbar_title.setText("Privacy Policy");
            loadFaqData ("privacypolicy");
        }
        else if(getIntent ().getIntExtra ("WebviewStatus",0)==4) {
            toolbar_title.setText("About Us");
            loadFaqData ("aboutus");
        }


    }

    private void loadFaqData(String method) {
        mProgressDialog = DialogUtil.getProgressDialog(this, getResources().getString(R.string.app_DialogInfo), getResources().getString(R.string.app_DialogLoading));
        ApiInterface apiService = ApiClient.getClient(this).create(ApiInterface.class);
        String header= Credentials.basic(getResources().getString(R.string.user_id), getResources().getString(R.string.user_password));
        Single<Faq> userObservable = apiService.getFAQdata(header,"Page",method);
        userObservable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(this::handleResults,this::handleError);
    }

    private void handleResults(Faq faqdata) {
        mProgressDialog.dismiss();
        if(faqdata.getStatus() == 200) {
            webview_faq.loadDataWithBaseURL ("",faqdata.getData (), "text/html", "UTF-8","");
            webview_faq.requestFocus();
            webview_faq.getSettings().setLightTouchEnabled(true);
            webview_faq.getSettings().setJavaScriptEnabled(true);
            webview_faq.getSettings().setGeolocationEnabled(true);
            webview_faq.setSoundEffectsEnabled(true);
        //    webview.loadDataWithBaseURL("",str_data,"text/html", "UTF-8","");
        }
        else
            UtilityMethods.PrintToast (FAQActivity.this,faqdata.getMessage (),2);
    }

    private void handleError(Throwable throwable) {
        mProgressDialog.dismiss();
        Log.e("User Data","negative"+throwable.getMessage());
    }
}