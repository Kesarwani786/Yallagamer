package com.augurs.yallagamers.UserInterface.saved_cards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.augurs.yallagamers.R;
import com.augurs.yallagamers.adapters.AdapterSavedCards;
import com.augurs.yallagamers.api_module.ApiClient;
import com.augurs.yallagamers.api_module.ApiInterface;
import com.augurs.yallagamers.api_module.BaseResponse;
import com.augurs.yallagamers.api_module.DialogUtil;
import com.augurs.yallagamers.data_models.CardData;
import com.augurs.yallagamers.data_models.ProfileDataResponce;
import com.augurs.yallagamers.data_models.SavedCardsDataResponce;
import com.augurs.yallagamers.utills.LoginPreferences;
import com.augurs.yallagamers.utills.RecyclerItemTouchHelper;
import com.augurs.yallagamers.utills.UtilityMethods;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;
import okhttp3.RequestBody;

public class SavedCardsActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    private Context context;
    private ProgressDialog mProgressDialog;
    private RecyclerView rvSavedCardsListView;
    private AdapterSavedCards adapterSavedCards;
    ImageView img_add_card;
    private ArrayList<CardData> cardDataArrayList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_cards);
        context=SavedCardsActivity.this;

        findViewById(R.id.img_back).setOnClickListener ( v -> {
            onBackPressed ();
        } );
        img_add_card= findViewById(R.id.img_add);
        img_add_card.setOnClickListener ( v -> {
            OpenAddCardDialog();
        } );

       rvSavedCardsListView=findViewById(R.id.rv_saved_cards);
       rvSavedCardsListView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));


        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rvSavedCardsListView);

        GetSavedCards();
    }


    private void GetSavedCards ( ) {
        if ( UtilityMethods.isNetworkConnected ( context ) ) {
            mProgressDialog = DialogUtil.getProgressDialog ( context , getResources ( ).getString ( R.string.app_DialogInfo ) , getResources ( ).getString ( R.string.app_DialogLoading ) );
            String _d1 = "User";
            String method = "getSaveCards";
            String header = Credentials.basic ( getResources ( ).getString ( R.string.user_id ) , getResources ( ).getString ( R.string.user_password ) );
            ApiInterface apiService = ApiClient.getClient ( context ).create ( ApiInterface.class );
            Observable<SavedCardsDataResponce> hpObservable = apiService.GetSavedCardsData ( header , _d1 , method , new LoginPreferences( context ).getString ( "token" ));

            // Subscribe Observer
            hpObservable.observeOn ( AndroidSchedulers.mainThread ( ) )
                    .subscribeOn ( Schedulers.io ( ) )
                    .observeOn ( AndroidSchedulers.mainThread ( ) )
                    .subscribe ( this :: handleResultsSC , this :: handleErrorSC );
        }
        else {
            GetDialogue(1);
        }
    }


    private void handleErrorSC(Throwable throwable) {
        mProgressDialog.dismiss ( );
        Log.e("HP","it does not work."+throwable.getMessage ());
    }

    private void handleResultsSC(SavedCardsDataResponce savedCardsDataResponce) {
        mProgressDialog.dismiss ( );
        Log.e("HP","it's work.-"+savedCardsDataResponce.getData().getCardDataList().get(0).getCard_name());

        cardDataArrayList.clear();
        cardDataArrayList=(ArrayList<CardData>) savedCardsDataResponce.getData().getCardDataList();
        adapterSavedCards=new AdapterSavedCards(context,cardDataArrayList);
        rvSavedCardsListView.setAdapter(adapterSavedCards);


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
                    GetSavedCards();
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

    int remPos=-1;
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        JSONObject user_data= new JSONObject();
        JSONObject key_val= new JSONObject();
        try {
            key_val.put("cart_id",cardDataArrayList.get(position).getId());
            user_data.put("card_data",key_val);
            user_data.put("method","deleteCards");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("card_data","=>"+user_data.toString());

        /*adapterSavedCards.removeData(position);
        //cardDataArrayList.remove(position);
        if(adapterSavedCards.getItemCount()==0){
            rvSavedCardsListView.setVisibility(View.GONE);
            findViewById(R.id.tv_empty_list).setVisibility(View.VISIBLE);
        }else {
            rvSavedCardsListView.setVisibility(View.VISIBLE);
            findViewById(R.id.tv_empty_list).setVisibility(View.GONE);
        }*/


        if ( UtilityMethods.isNetworkConnected ( context ) ) {
            mProgressDialog = DialogUtil.getProgressDialog ( context , getResources ( ).getString ( R.string.app_DialogInfo ) , getResources ( ).getString ( R.string.app_DialogLoading ) );
            String _d = "User";
            String token = new LoginPreferences ( context ).getString ( "token" );
            String header = Credentials.basic ( getResources ( ).getString ( R.string.user_id ) , getResources ( ).getString ( R.string.user_password ) );
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),user_data.toString());
            ApiInterface apiService = ApiClient.getClient ( context ).create ( ApiInterface.class );
            Single<BaseResponse> hpObservable = apiService.RemoveCardData ( header , _d , token,body );
            remPos=position;
            // Subscribe Observer
            hpObservable.observeOn ( AndroidSchedulers.mainThread ( ) )
                    .subscribeOn ( Schedulers.io ( ) )
                    .observeOn ( AndroidSchedulers.mainThread ( ) )
                    .subscribe ( this :: handleResultsDC , this :: handleErrorDC );





        }



    }


    public void handleResultsDC(BaseResponse baseResponse) {
        mProgressDialog.dismiss ( );
        if(baseResponse.getStatus()==200){
            Toast.makeText(context, "Cards removed successfully.", Toast.LENGTH_SHORT).show();
        }

        //Log.e("SD","it's work.-"+baseResponse.getMessage());
        if(remPos!=-1) {

            adapterSavedCards.removeData(remPos);
            if(adapterSavedCards.getItemCount()==0){
                rvSavedCardsListView.setVisibility(View.GONE);
                findViewById(R.id.tv_empty_list).setVisibility(View.VISIBLE);
            }else {
                rvSavedCardsListView.setVisibility(View.VISIBLE);
                findViewById(R.id.tv_empty_list).setVisibility(View.GONE);
            }
        }

      remPos=-1;
        GetSavedCards();
    }


    public void handleErrorDC(Throwable throwable) {
        //mProgressDialog.dismiss ( );
        //Log.e("HP","it does not work."+throwable.getMessage ());
        Toast.makeText(context, "Cards removed unsuccessfully.", Toast.LENGTH_SHORT).show();
    }


    private void OpenAddCardDialog (  ) {
        Dialog dialog = new Dialog (context);
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_add_cards);
        Window window = dialog.getWindow ( );
        WindowManager.LayoutParams wlp = window.getAttributes ( );
        window.setLayout ( ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT );
        dialog.getWindow ( ).addFlags ( WindowManager.LayoutParams.FLAG_DIM_BEHIND );
        //dialog.getWindow ( ).setBackgroundDrawable ( new ColorDrawable( android.graphics.Color.TRANSPARENT ) );
        window.setAttributes ( wlp );

        EditText card_name=dialog.findViewById(R.id.etCardName);
        EditText card_number=dialog.findViewById(R.id.etCardNumber);
        EditText card_exp_month=dialog.findViewById(R.id.etExpMonts);
        EditText card_exp_year=dialog.findViewById(R.id.etExpYear);


        TextView TvCancel =  dialog.findViewById(R.id.tv_cancel);
        TvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView TvAddCard =  dialog.findViewById(R.id.tv_add);
        TvAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cardname=card_name.getText().toString();
                String cardnum=card_number.getText().toString();
                String expmonth=card_exp_month.getText().toString();
                String expyear=card_exp_year.getText().toString();

                if(cardname.isEmpty()){
                    Toast.makeText(context, "Card name can not be empty", Toast.LENGTH_SHORT).show();
                    card_name.requestFocus();

                } else if(cardnum.isEmpty()){
                    Toast.makeText(context, "Card number can not be empty.", Toast.LENGTH_SHORT).show();
                    card_number.requestFocus();

                }else if(expmonth.isEmpty()){
                    Toast.makeText(context, "Expiry Month can not be empty.", Toast.LENGTH_SHORT).show();
                    card_exp_month.requestFocus();

                }else if(expyear.isEmpty()){
                    Toast.makeText(context, "Expiry Year can not be empty.", Toast.LENGTH_SHORT).show();
                    card_exp_year.requestFocus();

                }else {

                    JSONObject user_data= new JSONObject();
                    JSONObject key_val= new JSONObject();
                    try {
                        key_val.put("card_number",cardnum);
                        key_val.put("card_name",cardname);
                        key_val.put("exp_mm",expmonth);
                        key_val.put("exp_yy",expyear);
                        user_data.put("card_data",key_val);
                        user_data.put("method","addCards");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("card_data","=>"+user_data.toString());


                    if ( UtilityMethods.isNetworkConnected ( context ) ) {
                        mProgressDialog = DialogUtil.getProgressDialog ( context , getResources ( ).getString ( R.string.app_DialogInfo ) , getResources ( ).getString ( R.string.app_DialogLoading ) );
                        String _d = "User";
                        String token = new LoginPreferences ( context ).getString ( "token" );
                        String header = Credentials.basic ( getResources ( ).getString ( R.string.user_id ) , getResources ( ).getString ( R.string.user_password ) );
                        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),user_data.toString());
                        ApiInterface apiService = ApiClient.getClient ( context ).create ( ApiInterface.class );
                        Single<BaseResponse> hpObservable = apiService.SaveCardsData ( header , _d , token,body );

                        // Subscribe Observer
                        hpObservable.observeOn ( AndroidSchedulers.mainThread ( ) )
                                .subscribeOn ( Schedulers.io ( ) )
                                .observeOn ( AndroidSchedulers.mainThread ( ) )
                                .subscribe ( this :: handleResultsSC , this :: handleErrorSC );
                    }


                }

                }

            private void handleErrorSC(Throwable throwable) {
                mProgressDialog.dismiss ( );
                Toast.makeText(context, "There is a problem in adding cards. Please contact to Admin!!", Toast.LENGTH_SHORT).show();
                Log.e("SD","it does not work."+throwable.getMessage ());
                dialog.dismiss();
            }

            private void handleResultsSC(BaseResponse baseResponse) {

                mProgressDialog.dismiss ( );
                if(baseResponse.getStatus()==200){
                    Toast.makeText(context, "Cards added successfully.", Toast.LENGTH_SHORT).show();
                }
                Log.e("SD","it's work.-"+baseResponse.getMessage());
                dialog.dismiss();
                GetSavedCards();

            }



        });
        dialog.show();
    }



}