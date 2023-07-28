package com.augurs.yallagamers.UserInterface.categories;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.augurs.yallagamers.R;
import com.augurs.yallagamers.adapters.CategoriesAdapter;
import com.augurs.yallagamers.api_module.ApiClient;
import com.augurs.yallagamers.api_module.ApiInterface;
import com.augurs.yallagamers.api_module.DialogUtil;
import com.augurs.yallagamers.data_models.Category;
import com.augurs.yallagamers.data_models.CategoryData;
import com.augurs.yallagamers.utills.UtilityMethods;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;

public class CategoriesFragment extends Fragment {
    CategoriesAdapter categoriesAdapter;
    ArrayList < Category > categoryArrayList =new ArrayList<>();
    RecyclerView recycleview;
    ProgressDialog mProgressDialog;
    private SkeletonScreen skeletonScreenCategory;
    public static CategoriesFragment newInstance ( ) {
        return new CategoriesFragment ( );
    }
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_categories, container, false);
        SwipeRefreshLayout swipe=root.findViewById ( R.id.swipe );
        swipe.setOnRefreshListener ( ( ) -> {
            swipe.setRefreshing(false);
            mProgressDialog = DialogUtil.getProgressDialog ( getActivity ( ) , getResources ( ).getString ( R.string.app_DialogInfo ) , getResources ( ).getString ( R.string.app_DialogLoading ) );
            new CountDownTimer (3000, 1000) {
                public void onTick(long millisUntilFinished) {
                }
                public void onFinish() {
                    mProgressDialog.dismiss ( );
                }
            }.start();
        } );
        recycleview = root.findViewById ( R.id.recycleview );
        recycleview.setLayoutManager(new LinearLayoutManager (getContext(),LinearLayoutManager.VERTICAL,false));
        categoriesAdapter=new CategoriesAdapter (getContext(),categoryArrayList);
        recycleview.setAdapter(categoriesAdapter);
        skeletonScreenCategory = Skeleton.bind(recycleview).adapter(categoriesAdapter).duration(1000).count(15).load(R.layout.row_categorie).show(); //default count is 10
        callCategoryPage();
        return root;
    }

    private void callCategoryPage ( ) {
        if ( UtilityMethods.isNetworkConnected ( getActivity ( ) ) ) {
            mProgressDialog = DialogUtil.getProgressDialog ( getActivity ( ) , getResources ( ).getString ( R.string.app_DialogInfo ) , getResources ( ).getString ( R.string.app_DialogLoading ) );
            String _d1 = "Homepage"; //
            String header = Credentials.basic ( getResources ( ).getString ( R.string.user_id ) , getResources ( ).getString ( R.string.user_password ) );
            ApiInterface apiService = ApiClient.getClient ( getContext ( ) ).create ( ApiInterface.class );
            Observable < CategoryData > hpObservable = apiService.getCategoryPage ( header , _d1 , "categories" );
            hpObservable.subscribeOn ( Schedulers.io ( ) ).observeOn ( AndroidSchedulers.mainThread ( ) ).subscribe ( this :: handleResultsHP , this :: handleErrorHP );
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
                    callCategoryPage();
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

    private void handleResultsHP( CategoryData categoryPageData) {
        mProgressDialog.dismiss ( );
        if(categoryPageData.getStatus() == 200){
            categoriesAdapter.addListArray(categoryPageData.getData ());
            skeletonScreenCategory.hide ();
        }
    }

    private void handleErrorHP(Throwable throwable) {
        mProgressDialog.dismiss ( );
        // Toast.makeText ( getActivity (),throwable.getMessage (),Toast.LENGTH_LONG ).show ();
        Log.e("HP","it does not work."+throwable.getMessage ());
    }

}