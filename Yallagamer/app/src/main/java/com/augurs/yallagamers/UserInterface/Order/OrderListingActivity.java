package com.augurs.yallagamers.UserInterface.Order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.augurs.yallagamers.UserInterface.MainActivity;
import com.augurs.yallagamers.R;
import com.augurs.yallagamers.adapters.OrderListStatusAdapter;
import com.augurs.yallagamers.adapters.OrderListingAdapter;
import com.augurs.yallagamers.adapters.OrderPeriodAdapter;
import com.augurs.yallagamers.api_module.ApiClient;
import com.augurs.yallagamers.api_module.ApiInterface;
import com.augurs.yallagamers.api_module.DialogUtil;
import com.augurs.yallagamers.data_models.OrderFilterInterface;
import com.augurs.yallagamers.data_models.OrderListingFilterModel;
import com.augurs.yallagamers.data_models.OrderListingModel;
import com.augurs.yallagamers.data_models.OrderStatusResponse;
import com.augurs.yallagamers.data_models.PeriodModel;
import com.augurs.yallagamers.utills.LoginPreferences;
import com.augurs.yallagamers.utills.UtilityMethods;

import java.util.ArrayList;
import java.util.Calendar;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import it.sephiroth.android.library.rangeseekbar.RangeSeekBar;
import okhttp3.Credentials;

public class OrderListingActivity extends AppCompatActivity implements OrderFilterInterface {
    private ProgressDialog mProgressDialog;
    private RecyclerView recycleview, recycleviewStatus, recycleviewPeriod;
    private RelativeLayout rlLeft, Rl_Order, Rl_Amount, RlDate, RlOrderStatus, RLPeriond;
    private DrawerLayout drawer_layout;
    private ArrayList<OrderStatusResponse.Datum> orderStatusListing = new ArrayList<>();
    private TextView TvOrderId, TvTotal, TvPeriod, TvDate, TvOrderStatus, TvTotalAmountInterwal;
    private RangeSeekBar rangeSeekBar;
    private EditText EtEndDate, EtStartDate;
    private Calendar myCalendar = Calendar.getInstance();
    private ArrayList<PeriodModel> orderPeriodArray = new ArrayList<>();
    private ArrayList<String> OrderStatusArrayList = new ArrayList<>();
    private OrderListStatusAdapter orderListStatusAdapter;
    private OrderPeriodAdapter orderPeriodAdapter;
    private EditText etEditText;
    private Integer Status = 0;
    private String PeriodId = "";
    private CardView CardClose, CardApply;
    private OrderListingFilterModel orderListingFilterModel;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_listing);
        SwipeRefreshLayout swipe=findViewById ( R.id.swipe );
        swipe.setOnRefreshListener ( ( ) -> {
            swipe.setRefreshing(false);
            mProgressDialog = DialogUtil.getProgressDialog ( OrderListingActivity.this, getResources ( ).getString ( R.string.app_DialogInfo ) , getResources ( ).getString ( R.string.app_DialogLoading ) );
            new CountDownTimer (3000, 1000) {
                public void onTick(long millisUntilFinished) {
                }
                public void onFinish() {
                    mProgressDialog.dismiss ( );
                }
            }.start();
        } );
        ImageView img_back = findViewById (R.id.img_back);
        img_back.setOnClickListener (v -> onBackPressed ());
        orderPeriodArray.add(new PeriodModel("This day", "D"));
        orderPeriodArray.add(new PeriodModel("This week", "W"));
        orderPeriodArray.add(new PeriodModel("This Month", "M"));
        orderPeriodArray.add(new PeriodModel("This Year", "Y"));
        orderPeriodArray.add(new PeriodModel("Yesterday", "LD"));
        orderPeriodArray.add(new PeriodModel("Previous week", "LW"));
        orderPeriodArray.add(new PeriodModel("Previous month", "LM"));
        orderPeriodArray.add(new PeriodModel("Previous year", "LY"));
        orderPeriodArray.add(new PeriodModel("Last 24 hours", "HH"));
        orderPeriodArray.add(new PeriodModel("Last 7 days", "HW"));
        orderPeriodArray.add(new PeriodModel("Last 30 days", "HM"));
        recycleviewPeriod = findViewById(R.id.recycleviewPeriod);
        recycleviewPeriod.setLayoutManager(new LinearLayoutManager(OrderListingActivity.this));
        orderPeriodAdapter = new OrderPeriodAdapter(OrderListingActivity.this, orderPeriodArray, - 1, OrderListingActivity.this);
        recycleviewPeriod.setAdapter(orderPeriodAdapter);
        etEditText = findViewById(R.id.etEditText);
        EtEndDate = findViewById(R.id.EtEndDate);
        EtStartDate = findViewById(R.id.EtStartDate);
        rangeSeekBar = findViewById(R.id.rangeSeekBar);
        TvOrderId = findViewById(R.id.TvOrderId);
        TvTotal = findViewById(R.id.TvTotal);
        TvPeriod = findViewById(R.id.TvPeriod);
        TvDate = findViewById(R.id.TvDate);
        TvOrderStatus = findViewById(R.id.TvOrderStatus);
        Rl_Amount = findViewById(R.id.Rl_Amount);
        RLPeriond = findViewById(R.id.Rl_Period);
        RlDate = findViewById(R.id.RlDate);
        Rl_Order = findViewById(R.id.Rl_Order);
        RlOrderStatus = findViewById(R.id.RlOrderStatus);
        CardClose = findViewById(R.id.CardClose);
        CardApply = findViewById(R.id.CardApply);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Order History");
        CardView Card = findViewById(R.id.Card);
        drawer_layout = findViewById(R.id.drawer_layout);
        rlLeft = findViewById(R.id.rlLeft);
        Card.setVisibility(View.VISIBLE);
        Card.setOnClickListener(viewClickListener);
        TvOrderId.setOnClickListener(viewClickListener);
        TvTotal.setOnClickListener(viewClickListener);
        TvPeriod.setOnClickListener(viewClickListener);
        TvDate.setOnClickListener(viewClickListener);
        TvOrderStatus.setOnClickListener(viewClickListener);
        EtEndDate.setOnClickListener(viewClickListener);
        CardApply.setOnClickListener(viewClickListener);
        CardClose.setOnClickListener(viewClickListener);
        EtStartDate.setOnClickListener(viewClickListener);
        rangeSeekBar = findViewById(R.id.rangeSeekBar);

        TvTotalAmountInterwal = findViewById(R.id.TvTotalAmountInterwal);
        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onProgressChanged (
                    final RangeSeekBar seekBar, final int progressStart, final int progressEnd, final boolean fromUser ) {
                if (seekBar.getProgressEnd() == 25000)
                    TvTotalAmountInterwal.setText("₹" + seekBar.getProgressStart() + " to " + "₹" + seekBar.getProgressEnd() + "+");
                else
                    TvTotalAmountInterwal.setText("₹" + seekBar.getProgressStart() + " to " + "₹" + seekBar.getProgressEnd());
            }

            @Override
            public void onStartTrackingTouch ( final RangeSeekBar seekBar ) {
            }

            @Override
            public void onStopTrackingTouch ( final RangeSeekBar seekBar ) {
            }
        });
        orderListingFilterModel = new OrderListingFilterModel();
        GetOrderList();
        GetStatusList();
    }

    private void GetStatusList () {
        if (UtilityMethods.isNetworkConnected(OrderListingActivity.this)) {
            ApiInterface apiService = ApiClient.getClient(this).create(ApiInterface.class);
            String header= Credentials.basic(getResources().getString(R.string.user_id), getResources().getString(R.string.user_password));
            Observable<OrderStatusResponse> catObservable = apiService.GetOrderStatus(header, "Cart", "getOrderStatus", new LoginPreferences(OrderListingActivity.this).getString("token"));
            catObservable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this :: handleCategoryResultsCE, this :: handleError);
        } else
            GetDialogue(2);
    }

    private void handleCategoryResultsCE ( OrderStatusResponse orderStatusResponse ) {
        mProgressDialog.dismiss();
        if (orderStatusResponse.getStatus() == 200) {
            orderStatusListing.addAll(orderStatusResponse.getData());
            recycleviewStatus = findViewById(R.id.recycleviewStatus);
            recycleviewStatus.setLayoutManager(new LinearLayoutManager(OrderListingActivity.this));
            orderListStatusAdapter = new OrderListStatusAdapter(orderStatusListing, OrderListingActivity.this);
            recycleviewStatus.setAdapter(orderListStatusAdapter);
        } else
            UtilityMethods.PrintToast(OrderListingActivity.this, orderStatusResponse.getMessage(), 1);
    }

    @SuppressLint ("NonConstantResourceId")
    private final View.OnClickListener viewClickListener = v -> {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.Card:
                drawer_layout.openDrawer(rlLeft);
                CallLayout(0);
                break;
            case R.id.TvOrderId:
                CallLayout(0);
                break;
            case R.id.TvTotal:
                CallLayout(1);
                break;
            case R.id.TvPeriod:
                CallLayout(2);
                break;
            case R.id.TvDate:
                CallLayout(3);
                break;
            case R.id.TvOrderStatus:
                CallLayout(4);
                break;
            case R.id.EtEndDate:
                EndDate();
                break;
            case R.id.EtStartDate:
                StartDate();
                break;
            case R.id.CardApply:
                ApplyCard();
                break;
            case R.id.CardClose:
                orderListingFilterModel = new OrderListingFilterModel();
                GetOrderList();
                drawer_layout.closeDrawer(rlLeft);
                break;
            default:
                break;
        }
    };

    private void ApplyCard () {
        if (Status == 0) {
            if (etEditText.getText().toString().length() == 0) {
                UtilityMethods.PrintToast(OrderListingActivity.this, "Please Enter Order Id", 1);
            } else {
                orderListingFilterModel = new OrderListingFilterModel();
                orderListingFilterModel.setOrderId(etEditText.getText().toString());
                drawer_layout.closeDrawer(rlLeft);
                GetOrderList();
            }
        } else if (Status == 1) {
            orderListingFilterModel = new OrderListingFilterModel();
            orderListingFilterModel.setTotalSecTo(rangeSeekBar.getMax());
            orderListingFilterModel.setTotalSecFrom(rangeSeekBar.getProgressEnd());
            drawer_layout.closeDrawer(rlLeft);
            GetOrderList();
        } else if (Status == 2) {
            if (PeriodId.length() == 0) {
                UtilityMethods.PrintToast(OrderListingActivity.this, "Please Select Period", 1);
            } else {
                orderListingFilterModel = new OrderListingFilterModel();
                orderListingFilterModel.setPeriod(PeriodId);
                drawer_layout.closeDrawer(rlLeft);
                GetOrderList();
            }
        } else if (Status == 3) {
            orderListingFilterModel = new OrderListingFilterModel();
            if (EtEndDate.getText().toString().length() == 0) {
                UtilityMethods.PrintToast(OrderListingActivity.this, "Please Select End Date", 1);
            } else if (EtStartDate.getText().toString().length() == 0) {
                UtilityMethods.PrintToast(OrderListingActivity.this, "Please Select Start Date", 1);
            } else {
                orderListingFilterModel = new OrderListingFilterModel();
                orderListingFilterModel.setTimeFrom(EtStartDate.getText().toString());
                orderListingFilterModel.setTimeTo(EtEndDate.getText().toString());
                drawer_layout.closeDrawer(rlLeft);
                GetOrderList();
            }
        } else if (Status == 4) {
            if (OrderStatusArrayList.size() == 0) {
                UtilityMethods.PrintToast(OrderListingActivity.this, "Please Select Order Status", 1);
            }
            orderListingFilterModel = new OrderListingFilterModel();
            orderListingFilterModel.setStatus(OrderStatusArrayList);
            drawer_layout.closeDrawer(rlLeft);
            GetOrderList();
        }
    }

    @SuppressLint ("DefaultLocale")
    private void EndDate () {
        if (EtStartDate.getText().toString().length() == 0)
            UtilityMethods.PrintToast(OrderListingActivity.this, "Please Select Start Date  ", 1);
        else {
        /*    DatePickerDialog mDatePicker = new DatePickerDialog(OrderListingActivity.this, ( datepicker, selectedyear, selectedmonth, selectedday ) -> {
                Calendar.getInstance().set(Calendar.YEAR, selectedyear);
                Calendar.getInstance().set(Calendar.MONTH, selectedmonth);
                Calendar.getInstance().set(Calendar.DAY_OF_MONTH, selectedday);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                EtEndDate.setText(sdf.format(Calendar.getInstance().getTime()));
            }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            mDatePicker.setTitle("Select Start Date");
            mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
            Calendar c = Calendar.getInstance();
            String[] currencies = EtStartDate.getText().toString().split("/");
            c.set(Integer.parseInt(currencies[2]),Integer.parseInt(currencies[1]),Integer.parseInt(currencies[0]));
             mDatePicker.getDatePicker().setMinDate(c.getTimeInMillis());
            mDatePicker.show();*/


            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    EtEndDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                }
            }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            datePickerDialog.setTitle("Select Start Date");
            Calendar c1 = Calendar.getInstance();
            String[] currencies = EtStartDate.getText().toString().split("/");
            c1.set(Integer.parseInt(currencies[2]),Integer.parseInt( currencies[1]),Integer.parseInt(currencies[0]));
            Log.v("asdasd",c1.getTimeInMillis()+"\n"+System.currentTimeMillis());
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.getDatePicker().setMinDate(c1.getTimeInMillis());
            datePickerDialog.show();
        }
    }

    private void StartDate () {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint ("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        EtStartDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setTitle("Select Start Date");
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    @SuppressLint ("SetTextI18n")
    private void CallLayout ( int i ) {
        TvOrderId.setTypeface(null, Typeface.NORMAL);
        TvTotal.setTypeface(null, Typeface.NORMAL);
        TvPeriod.setTypeface(null, Typeface.NORMAL);
        TvDate.setTypeface(null, Typeface.NORMAL);
        TvOrderStatus.setTypeface(null, Typeface.NORMAL);
        TvOrderId.setBackgroundColor(Color.parseColor("#d3d3d3"));
        TvTotal.setBackgroundColor(Color.parseColor("#d3d3d3"));
        TvPeriod.setBackgroundColor(Color.parseColor("#d3d3d3"));
        TvDate.setBackgroundColor(Color.parseColor("#d3d3d3"));
        Rl_Order.setVisibility(View.GONE);
        Rl_Amount.setVisibility(View.GONE);
        RlDate.setVisibility(View.GONE);
        RlOrderStatus.setVisibility(View.GONE);
        RLPeriond.setVisibility(View.GONE);
        TvOrderStatus.setBackgroundColor(Color.parseColor("#d3d3d3"));
        if (i == 0) {
            TvOrderId.setBackgroundColor(Color.parseColor("#ffffff"));
            TvOrderId.setTypeface(null, Typeface.BOLD);
            Rl_Order.setVisibility(View.VISIBLE);
            etEditText.setText("");
        } else if (i == 1) {
            TvTotal.setBackgroundColor(Color.parseColor("#ffffff"));
            TvTotal.setTypeface(null, Typeface.BOLD);
            Rl_Amount.setVisibility(View.VISIBLE);
            rangeSeekBar.setMax(25000);
            TvTotalAmountInterwal.setText("₹0 to " + "₹" + rangeSeekBar.getProgressEnd() + "+");
        } else if (i == 2) {
            TvPeriod.setBackgroundColor(Color.parseColor("#ffffff"));
            TvPeriod.setTypeface(null, Typeface.BOLD);
            RLPeriond.setVisibility(View.VISIBLE);
            orderPeriodAdapter.addListArray(orderPeriodArray);
            PeriodId = "";
        } else if (i == 3) {
            TvDate.setBackgroundColor(Color.parseColor("#ffffff"));
            TvDate.setTypeface(null, Typeface.BOLD);
            RlDate.setVisibility(View.VISIBLE);
            EtStartDate.setText("");
            EtEndDate.setText("");
        } else if (i == 4) {
            for (OrderStatusResponse.Datum orderStatus : orderStatusListing) {
                orderStatus.setChecked(false);
            }
            orderListStatusAdapter.addListArray(orderStatusListing);
            TvOrderStatus.setBackgroundColor(Color.parseColor("#ffffff"));
            TvOrderStatus.setTypeface(null, Typeface.BOLD);
            RlOrderStatus.setVisibility(View.VISIBLE);
            OrderStatusArrayList.clear();
        }
        Status = i;
    }

    private void GetOrderList () {
        if (UtilityMethods.isNetworkConnected(OrderListingActivity.this)) {
            mProgressDialog = DialogUtil.getProgressDialog(OrderListingActivity.this, getResources().getString(R.string.app_DialogInfo), getResources().getString(R.string.app_DialogLoading));
            ApiInterface apiService = ApiClient.getClient(this).create(ApiInterface.class);
            String header= Credentials.basic(getResources().getString(R.string.user_id), getResources().getString(R.string.user_password));
            Observable<OrderListingModel> catObservable = apiService.GetOrderListing(header, "Cart", new LoginPreferences(OrderListingActivity.this).getString("token"), orderListingFilterModel);
            catObservable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this :: handleResultsCE, this :: handleErrorCE);
        } else
            GetDialogue(1);
    }

    private void handleErrorCE ( Throwable throwable ) {
        mProgressDialog.dismiss();
        UtilityMethods.PrintToast(OrderListingActivity.this, throwable.getMessage(), 1);
    }

    private void handleError ( Throwable throwable ) {
        UtilityMethods.PrintToast(OrderListingActivity.this, throwable.getMessage(), 1);
    }

    private void handleResultsCE ( OrderListingModel bagItemData ) {
        mProgressDialog.dismiss();
        if (bagItemData.getStatus() == 200) {
            recycleview = findViewById(R.id.recycleview);
            recycleview.setLayoutManager(new LinearLayoutManager(OrderListingActivity.this));
            if (bagItemData.getData().getOrder() != null&&bagItemData.getData().getOrder().size ()!=0 ) {
                recycleview.setVisibility (View.VISIBLE);
                recycleview.setAdapter (new OrderListingAdapter (OrderListingActivity.this, bagItemData.getData ().getOrder ()));
            }
            else {
                RelativeLayout RlEmptyLayout= findViewById (R.id.RlEmptyLayout);
                RlEmptyLayout.setVisibility (View.VISIBLE);
            }
        } else
            UtilityMethods.PrintToast(OrderListingActivity.this, bagItemData.getMessage(), 1);
    }

    private void GetDialogue ( int i ) {
        Dialog dialog = new Dialog(OrderListingActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setAttributes(wlp);
        TextView TvRetry = dialog.findViewById(R.id.TvRetry);
        TvRetry.setOnClickListener(v -> {
            if (i == 1 && UtilityMethods.isNetworkConnected(OrderListingActivity.this))
                GetOrderList();
            if (i == 2 && UtilityMethods.isNetworkConnected(OrderListingActivity.this))
                GetStatusList();
            else
                GetDialogue(i);
            dialog.dismiss();
        });
        TextView TvRetryNot = dialog.findViewById(R.id.TvRetryNot);
        TvRetryNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick ( View v ) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void PeriodId ( String PeriodValue ) {
        PeriodId = PeriodValue;
    }

    @Override
    public void OrderStatusAdd ( String orderStatusList ) {
        OrderStatusArrayList.add(orderStatusList);
    }

    @Override
    public void OrderStatusRemove ( String orderStatusList ) {
        OrderStatusArrayList.remove(orderStatusList);
    }

    @Override
    public void onBackPressed () {
        Intent i = new Intent (OrderListingActivity.this, MainActivity.class);
        i.putExtra ("ScreenStatus",4);
        startActivity (i);
    }
}