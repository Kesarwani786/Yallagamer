package com.augurs.yallagamers.UserInterface.address;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.augurs.yallagamers.R;
import com.augurs.yallagamers.adapters.CustomCountryCodeAdapter;
import com.augurs.yallagamers.api_module.ApiClient;
import com.augurs.yallagamers.api_module.ApiInterface;
import com.augurs.yallagamers.api_module.BaseResponse;
import com.augurs.yallagamers.api_module.DialogUtil;
import com.augurs.yallagamers.data_models.CountryListResponse;
import com.augurs.yallagamers.utills.LoginPreferences;
import com.augurs.yallagamers.utills.UtilityMethods;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;

public class AddNewAddressActivity extends AppCompatActivity {
    TextView click_home, click_work;
    ImageView img_back;
    ProgressDialog mProgressDialog;
    AddAddressModel addAddressModel;
    EditText et_customer_note;
    CheckBox checkbox, checkbox_default;
    Button btn_submit;
    EditText et_s_shipping_f_name, et_s_shipping_l_name, et_s_shipping_Mobile_no, et_s_shipping_Email_id, et_s_shipping_address1, et_s_shipping_address2, et_s_shipping_zipcode, et_s_city, et_s_state, et_s_country;
    EditText et_billing_f_name, et_billing_l_name, et_billing_mobile, et_billing_email, et_billing_address1, et_billing_address2, et_billing_zipcode, et_billing_city, et_billing_country, et_billling_state;
    PlacesClient placesClient;
    private String CountryCodeShipping,CountryCodeBIlling;
    private final ArrayList<CountryListResponse.Datum> datumArrayList = new ArrayList<> ();

    @SuppressLint ("SetTextI18n")
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_add_new_address);
        //https://github.com/velmurugan-murugesan/Android-Example/blob/master/GoogleAutoCompletePlacesAndroid/app/src/main/res/layout/activity_main.xml

        LinearLayout LinearBillingDetails = findViewById (R.id.LinearBillingDetails);
        LinearLayout LinearBillingAddress = findViewById (R.id.LinearBillingAddress);
        et_billing_f_name = findViewById (R.id.et_billing_f_name);
        et_billing_l_name = findViewById (R.id.et_billing_l_name);
        et_billing_mobile = findViewById  (R.id.et_billing_mobile);
        et_billing_email = findViewById (R.id.et_billing_email);
        et_billing_address1 = findViewById (R.id.et_billing_address1);
        et_billing_address2 = findViewById (R.id.et_billing_address2);
        et_billing_zipcode = findViewById (R.id.et_billing_zipcode);
        et_billing_city = findViewById (R.id.et_billing_city);
        et_billling_state = findViewById (R.id.et_billling_state);
        et_billing_country = findViewById (R.id.et_billing_country);
        et_s_shipping_f_name = findViewById (R.id.et_s_shipping_f_name);
        et_s_shipping_l_name = findViewById (R.id.et_s_shipping_l_name);
        et_s_shipping_Mobile_no = findViewById (R.id.et_s_shipping_Mobile_no);
        et_s_shipping_Email_id = findViewById (R.id.et_s_shipping_Email_id);
        et_s_shipping_address1 = findViewById (R.id.et_s_shipping_address1);
        et_s_shipping_address2 = findViewById (R.id.et_s_shipping_address2);
        et_s_shipping_zipcode = findViewById (R.id.et_s_shipping_zipcode);
        et_s_city = findViewById (R.id.et_s_city);
        et_s_state = findViewById (R.id.et_s_state);
        et_s_country = findViewById (R.id.et_s_country);
        et_customer_note = findViewById (R.id.et_customer_note);

        click_home = findViewById (R.id.click_home);
        click_work = findViewById (R.id.click_work);
        img_back = findViewById (R.id.img_back);
        checkbox = findViewById (R.id.checkbox);
        checkbox_default = findViewById (R.id.checkbox_default);
        btn_submit = findViewById (R.id.btn_submit);
        ShippingAutoGetAddress();
        if (getIntent ().getIntExtra ("Status", 0) == 1) {
            TextView toolbar_title = findViewById (R.id.toolbar_title);
            toolbar_title.setText ("Update Address");
            btn_submit.setText ("Update Address");
            JsonElement mJson = new JsonParser ().parse (getIntent ().getStringExtra ("AddAddressModel"));
            addAddressModel = new Gson ().fromJson (mJson, AddAddressModel.class);
            if (addAddressModel.getIsDefault () != 1)
                checkbox_default.setChecked (false);
             else
                checkbox_default.setChecked (true);

            if (addAddressModel.getIsSame () != 1)
                checkbox.setChecked (false);
             else
                checkbox.setChecked (true);

            addAddressModel.setMethod ("update_shipping_address");
            if (addAddressModel.getIsSame () == 0) {
                LinearBillingDetails.setVisibility (View.VISIBLE);
                LinearBillingAddress.setVisibility (View.VISIBLE);
                et_billing_f_name.setText (addAddressModel.getbFirstname ());
                et_billing_l_name.setText (addAddressModel.getbLastname ());
                et_billing_mobile.setText (addAddressModel.getbPhone ());
                if (addAddressModel.getbEmail () != null)
                    et_billing_email.setText (addAddressModel.getbEmail ());
                et_billing_address1.setText (addAddressModel.getbAddress ());
                et_billing_address2.setText (addAddressModel.getbAddress2 ());
                et_billing_zipcode.setText (addAddressModel.getbZipcode ());
                et_billing_city.setText (addAddressModel.getbCity ());
                et_billling_state.setText (addAddressModel.getbState ());
                et_billing_country.setText (addAddressModel.getbCountry ());
            }
            et_s_shipping_f_name.setText (addAddressModel.getsFirstname ());
            et_s_shipping_l_name.setText (addAddressModel.getsLastname ());
            et_s_shipping_Mobile_no.setText (addAddressModel.getsPhone ());
            if (addAddressModel.getsEmail () != null)
                et_s_shipping_Email_id.setText (addAddressModel.getsEmail ());
            et_s_shipping_address1.setText (addAddressModel.getsAddress ());
            et_s_shipping_address2.setText (addAddressModel.getsAddress2 ());
            et_s_shipping_zipcode.setText (addAddressModel.getsZipcode ());
            et_s_city.setText (addAddressModel.getsCity ());
            et_s_state.setText (addAddressModel.getsState ());
            et_s_country.setText (addAddressModel.getsCountry ());
            if (addAddressModel.getCustomerNotes () != null)
                et_customer_note.setText (addAddressModel.getCustomerNotes ());
        } else {
            addAddressModel = new AddAddressModel ();
            LinearBillingDetails.setVisibility (View.GONE);
            LinearBillingAddress.setVisibility (View.GONE);
            addAddressModel.setIsSame (1);
            addAddressModel.setsAddressType ("Home");
            addAddressModel.setIsDefault (0);
            addAddressModel.setMethod ("add_shipping_address");
        }
        GetCountryData();
        checkbox.setOnCheckedChangeListener (( buttonView, isChecked ) -> {
            if (isChecked) {
                LinearBillingDetails.setVisibility (View.GONE);
                LinearBillingAddress.setVisibility (View.GONE);
                addAddressModel.setIsSame (1);
                addAddressModel.setbFirstname (null);
                et_billing_f_name.setText ("");
                addAddressModel.setbLastname (null);
                et_billing_l_name.setText ("");
                addAddressModel.setbPhone (null);
                et_billing_mobile.setText ("");
                addAddressModel.setbAddress (null);
                et_billing_address1.setText ("");
                addAddressModel.setbAddress2 (null);
                et_billing_address2.setText ("");
                addAddressModel.setbZipcode (null);
                et_billing_zipcode.setText ("");
                addAddressModel.setbCity (null);
                et_billing_city.setText ("");
                addAddressModel.setbState (null);
                et_billling_state.setText ("");
                addAddressModel.setbCountry (null);
                et_billing_country.setText ("");
                addAddressModel.setbEmail (null);
                et_billing_email.setText ("");
            } else {
                LinearBillingDetails.setVisibility (View.VISIBLE);
                LinearBillingAddress.setVisibility (View.VISIBLE);
                addAddressModel.setIsSame (0);
            }
        });
        click_home.setOnClickListener (view -> {
            click_home.setTextColor (Color.parseColor ("#ffffff"));
            click_work.setTextColor (Color.parseColor ("#4c989d"));
            click_home.setBackgroundResource (R.drawable.gray_border_bg);
            click_work.setBackgroundResource (R.drawable.green_border_bg);
            addAddressModel.setsAddressType ("Home");
        });
        click_work.setOnClickListener (view -> {
            click_home.setTextColor (Color.parseColor ("#4c989d"));
            click_work.setTextColor (Color.parseColor ("#ffffff"));
            click_home.setBackgroundResource (R.drawable.green_border_bg);
            click_work.setBackgroundResource (R.drawable.gray_border_bg);
            addAddressModel.setsAddressType ("Work");
        });
        img_back.setOnClickListener (view -> onBackPressed ());

        checkbox_default.setOnCheckedChangeListener (( buttonView, isChecked ) -> {
            if (isChecked)
                addAddressModel.setIsDefault (1);
            else
                addAddressModel.setIsDefault (0);
        });


        btn_submit.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick ( View v ) {
                if (et_s_shipping_f_name.getText ().toString ().length () == 0) {
                    Toast.makeText (AddNewAddressActivity.this, "Please Add Shipping User's First Name   ", Toast.LENGTH_LONG).show ();
                    return;
                } else if (et_s_shipping_l_name.getText ().toString ().length () == 0) {
                    Toast.makeText (AddNewAddressActivity.this, "Please Add Shipping User's Last Name   ", Toast.LENGTH_LONG).show ();
                    return;
                } else if (et_s_shipping_Mobile_no.getText ().toString ().length () == 0) {
                    Toast.makeText (AddNewAddressActivity.this, "Please Add Shipping User's Mobile No ", Toast.LENGTH_LONG).show ();
                    return;
                } else if (et_s_shipping_Mobile_no.getText ().toString ().length () != 10) {
                    Toast.makeText (AddNewAddressActivity.this, "Please Add Shipping User's Valid Mobile No ", Toast.LENGTH_LONG).show ();
                    return;
                }
                else if(et_s_shipping_Email_id.getText ().toString ().length ()!=0&& !et_s_shipping_Email_id.getText ().toString ().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
                    UtilityMethods.PrintToast (AddNewAddressActivity.this, "Please Add  User's Valid Email id ", 1);
                    return;
                }else if (et_s_shipping_address1.getText ().toString ().length () == 0) {
                    Toast.makeText (AddNewAddressActivity.this, "Please Add Shipping User's Address ", Toast.LENGTH_LONG).show ();
                    return;
                }  else if (et_s_shipping_zipcode.getText ().toString ().length () == 0) {
                    Toast.makeText (AddNewAddressActivity.this, "Please Add Shipping User's Address Zip code  ", Toast.LENGTH_LONG).show ();
                    return;
                }  else if (et_s_city.getText ().toString ().length () == 0) {
                    Toast.makeText (AddNewAddressActivity.this, "Please Add Shipping User's City  ", Toast.LENGTH_LONG).show ();
                    return;
                } else if (et_s_state.getText ().toString ().length () == 0) {
                    Toast.makeText (AddNewAddressActivity.this, "Please Add Shipping User's State  ", Toast.LENGTH_LONG).show ();
                    return;
                } else if (et_s_country.getText ().toString ().length () == 0) {
                    Toast.makeText (AddNewAddressActivity.this, "Please Add Shipping User's Country  ", Toast.LENGTH_LONG).show ();
                    return;
                } else {
                    if (addAddressModel.getIsSame () == 0) {
                        if (et_billing_f_name.getText ().toString ().length () == 0) {
                            Toast.makeText (AddNewAddressActivity.this, "Please Add Billing User's First Name   ", Toast.LENGTH_LONG).show ();
                            return;
                        } else if (et_billing_l_name.getText ().toString ().length () == 0) {
                            Toast.makeText (AddNewAddressActivity.this, "Please Add Billing User's Last Name   ", Toast.LENGTH_LONG).show ();
                            return;
                        } else if (et_billing_mobile.getText ().toString ().length () == 0) {
                            Toast.makeText (AddNewAddressActivity.this, "Please Add Billing User's Mobile No ", Toast.LENGTH_LONG).show ();
                            return;
                        } else if (et_billing_mobile.getText ().toString ().length () != 10) {
                            Toast.makeText (AddNewAddressActivity.this, "Please Add Billing User's Valid Mobile No ", Toast.LENGTH_LONG).show ();
                            return;
                        }
                        else if(et_billing_email.getText ().toString ().length ()!=0&& !et_billing_email.getText ().toString ().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
                            UtilityMethods.PrintToast (AddNewAddressActivity.this, "Please Add  User's Valid Email id ", 1);
                        }
                        //Email is not Validate not Manditory
                        else if (et_billing_address1.getText ().toString ().length () == 0) {
                            Toast.makeText (AddNewAddressActivity.this, "Please Add Billing User's Address ", Toast.LENGTH_LONG).show ();
                            return;
                        } else if (et_billing_zipcode.getText ().toString ().length () == 0) {
                            Toast.makeText (AddNewAddressActivity.this, "Please Add Billing User's Address Zip code  ", Toast.LENGTH_LONG).show ();
                            return;
                        } else if (et_billing_city.getText ().toString ().length () == 0) {
                            Toast.makeText (AddNewAddressActivity.this, "Please Add Billing User's City  ", Toast.LENGTH_LONG).show ();
                            return;
                        } else if (et_billling_state.getText ().toString ().length () == 0) {
                            Toast.makeText (AddNewAddressActivity.this, "Please Add Billing User's State  ", Toast.LENGTH_LONG).show ();
                            return;
                        } else if (et_billing_country.getText ().toString ().length () == 0) {
                            Toast.makeText (AddNewAddressActivity.this, "Please Add Billing User's Country  ", Toast.LENGTH_LONG).show ();
                            return;
                        }
                        addAddressModel.setbFirstname (et_billing_f_name.getText ().toString ());
                        addAddressModel.setbLastname (et_billing_l_name.getText ().toString ());
                        addAddressModel.setbPhone (et_billing_mobile.getText ().toString ());
                        addAddressModel.setbAddress (et_billing_address1.getText ().toString ());
                        addAddressModel.setbAddress2 (et_billing_address2.getText ().toString ());
                        addAddressModel.setbZipcode (et_billing_zipcode.getText ().toString ());
                        addAddressModel.setbCity (et_billing_city.getText ().toString ());
                        addAddressModel.setbState (et_billling_state.getText ().toString ());
                        addAddressModel.setbCountry (CountryCodeBIlling);
                        if (et_billing_email.getText ().toString ().length () != 0)
                            addAddressModel.setbEmail (et_billing_email.getText ().toString ());
                    }
                    addAddressModel.setsFirstname (et_s_shipping_f_name.getText ().toString ());
                    addAddressModel.setsLastname (et_s_shipping_l_name.getText ().toString ());
                    addAddressModel.setsPhone (et_s_shipping_Mobile_no.getText ().toString ());
                    addAddressModel.setsAddress (et_s_shipping_address1.getText ().toString ());
                    addAddressModel.setsAddress2 (et_s_shipping_address2.getText ().toString ());
                    addAddressModel.setsZipcode (et_s_shipping_zipcode.getText ().toString ());
                    addAddressModel.setsCity (et_s_city.getText ().toString ());
                    addAddressModel.setsState (et_s_state.getText ().toString ());
                    addAddressModel.setsCountry (CountryCodeShipping);
                    et_s_shipping_Email_id.getText ().toString ();
                    addAddressModel.setsEmail (et_s_shipping_Email_id.getText ().toString ());
                    et_customer_note= findViewById ( R.id.et_customer_note );
                    if (et_customer_note.getText ( ).toString ( ).length ( ) != 0 )
                        addAddressModel.setCustomerNotes ( et_customer_note.getText ( ).toString ( ) );
                }
                Log.v ("addAddressModel", new Gson ().toJson (addAddressModel));
                SaveAddress (addAddressModel);
            }
        });
    }

    private void GetCountryData ( ) {
        LoginPreferences loginPreferences = new LoginPreferences (AddNewAddressActivity.this);
        mProgressDialog = DialogUtil.getProgressDialog (this, getResources ().getString (R.string.app_DialogInfo), getResources ().getString (R.string.app_DialogLoading));
        ApiInterface apiService = ApiClient.getClient (AddNewAddressActivity.this).create (ApiInterface.class);
        String _d = "User"; //
        String header = Credentials.basic (getResources ().getString (R.string.user_id), getResources ().getString (R.string.user_password));
        Single<CountryListResponse> catObservable = apiService.GetCountryList (header, _d, "application/json","get_countries");
        catObservable.observeOn (AndroidSchedulers.mainThread ()).subscribeOn (Schedulers.io ()).subscribe (this :: handleCountryResults, this :: handleError);
    }

    private void handleCountryResults ( CountryListResponse countryListResponse ) {
        mProgressDialog.dismiss ();
        if(countryListResponse.getStatus ()==200){
            datumArrayList.addAll (countryListResponse.getData ());
            if (getIntent ().getIntExtra ("Status", 0) == 1) {
                for (CountryListResponse.Datum country :datumArrayList){
                    if(addAddressModel.getsCountry ().equals (country.getCode ())) {
                        et_s_country.setText (country.getCountry ());
                        CountryCodeShipping = country.getCode ();
                        break;
                    }
                }
                for (CountryListResponse.Datum country1 :datumArrayList){
                    if(addAddressModel.getbCountry ().equals (country1.getCode ())) {
                        et_billing_country.setText (country1.getCountry ());
                        CountryCodeBIlling = country1.getCode ();
                        break;
                    }
                }
            }
        }
        else {
            UtilityMethods.PrintToast (AddNewAddressActivity.this,"Unable to fetch the country code ",1);
        }
    }

    private void ShippingAutoGetAddress ( ) {
        et_s_country.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick ( View v ) {
                if(datumArrayList.size ()==0)
                    UtilityMethods.PrintToast (AddNewAddressActivity.this,"Some issue occured ",1);
                else
                    ShowCountryDailogue();
            }
        });
        et_billing_country.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick ( View v ) {
                if(datumArrayList.size ()==0)
                    UtilityMethods.PrintToast (AddNewAddressActivity.this,"Some issue occured ",1);
                else
                    ShowCountryBillingDailogue();
            }
        });
        String apiKey = getString(R.string.api_key);
        if (! Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }
        placesClient = Places.createClient(this);
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setTypeFilter(TypeFilter.ADDRESS);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.LAT_LNG, Place.Field.NAME, Place.Field.ADDRESS));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener () {
            @Override
            public void onError ( @NonNull Status status ) {
                //Toast.makeText(getApplicationContext(), status.toString(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onPlaceSelected( @NonNull Place place) {
                try {
                        List<Address>  addresses = new Geocoder (AddNewAddressActivity.this, Locale.getDefault()).getFromLocation(Objects.requireNonNull (place.getLatLng ()).latitude, place.getLatLng().longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                        et_s_shipping_address1.setText (addresses.get(0).getAddressLine(0).split(addresses.get(0).getLocality())[0]);
                        et_s_shipping_address2.setText (addresses.get(0).getAddressLine(1));
                        et_s_city.setText (addresses.get(0).getLocality());
                        CountryCodeShipping=addresses.get(0).getCountryCode ();
                        et_s_state.setText (addresses.get(0).getAdminArea());
                        et_s_country.setText (addresses.get(0).getCountryName());
                        et_s_shipping_zipcode.setText (addresses.get(0).getPostalCode());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        });

        AutocompleteSupportFragment autocompleteFragment1 = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment1);
        autocompleteFragment1.setTypeFilter(TypeFilter.ADDRESS);
        autocompleteFragment1.setPlaceFields(Arrays.asList(Place.Field.LAT_LNG, Place.Field.NAME, Place.Field.ADDRESS));
        autocompleteFragment1.setOnPlaceSelectedListener(new PlaceSelectionListener () {
            @Override
            public void onError ( @NonNull Status status ) {
                //Toast.makeText(getApplicationContext(), status.toString(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onPlaceSelected( @NonNull Place place) {
                try {
                    List<Address>  addresses = new Geocoder (AddNewAddressActivity.this, Locale.getDefault()).getFromLocation(Objects.requireNonNull (place.getLatLng ()).latitude, place.getLatLng().longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    et_billing_address1.setText (addresses.get(0).getAddressLine(0).split(addresses.get(0).getLocality())[0]);
                    et_billing_address2.setText (addresses.get(0).getAddressLine(1));
                    et_billing_city.setText (addresses.get(0).getLocality());
                    et_billling_state.setText (addresses.get(0).getAdminArea());
                    CountryCodeBIlling=addresses.get(0).getCountryName();
                    et_billing_country.setText (CountryCodeBIlling);
                    et_billing_zipcode.setText (addresses.get(0).getPostalCode());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void ShowCountryBillingDailogue ( ) {
        ListPopupWindow listPopupWindow = new ListPopupWindow (AddNewAddressActivity.this);
        listPopupWindow.setAdapter (new CustomCountryCodeAdapter (AddNewAddressActivity.this, datumArrayList));
        listPopupWindow.setAnchorView (et_billing_country);
        listPopupWindow.setWidth (ListPopupWindow.WRAP_CONTENT);
        listPopupWindow.setHeight (ListPopupWindow.WRAP_CONTENT);
        listPopupWindow.setModal (true);
        listPopupWindow.setOnItemClickListener (new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick ( AdapterView<?> adapterView, View view, int i, long l ) {
                et_billing_country.setText (datumArrayList.get (i).getCountry ());
                CountryCodeBIlling = datumArrayList.get (i).getCode ();
                listPopupWindow.dismiss ();
            }
        });
        listPopupWindow.show ();
    }

    private void ShowCountryDailogue ( ) {
        ListPopupWindow listPopupWindow = new ListPopupWindow (AddNewAddressActivity.this);
        listPopupWindow.setAdapter (new CustomCountryCodeAdapter (AddNewAddressActivity.this, datumArrayList));
        listPopupWindow.setAnchorView (et_s_country);
        listPopupWindow.setWidth (ListPopupWindow.WRAP_CONTENT);
        listPopupWindow.setHeight (ListPopupWindow.WRAP_CONTENT);
        listPopupWindow.setModal (true);
        listPopupWindow.setOnItemClickListener (new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick ( AdapterView<?> adapterView, View view, int i, long l ) {
                et_s_country.setText (datumArrayList.get (i).getCountry ());
                CountryCodeShipping = datumArrayList.get (i).getCode ();
                listPopupWindow.dismiss ();
            }

        });
        listPopupWindow.show ();
    }

    @SuppressLint ("CheckResult")
    private void SaveAddress ( AddAddressModel addNewAddress ) {
        LoginPreferences loginPreferences = new LoginPreferences (AddNewAddressActivity.this);
        mProgressDialog = DialogUtil.getProgressDialog (this, getResources ().getString (R.string.app_DialogInfo), getResources ().getString (R.string.app_DialogLoading));
        ApiInterface apiService = ApiClient.getClient (AddNewAddressActivity.this).create (ApiInterface.class);
        String _d = "User"; //
        String header = Credentials.basic (getResources ().getString (R.string.user_id), getResources ().getString (R.string.user_password));
        Single<BaseResponse> catObservable = apiService.SaveUserAddress (header, _d, "application/json", loginPreferences.getString ("token"), addNewAddress);
        catObservable.observeOn (AndroidSchedulers.mainThread ()).subscribeOn (Schedulers.io ()).subscribe (this :: handleResults, this :: handleError);
    }

    private void handleResults ( BaseResponse baseResponse ) {
        mProgressDialog.dismiss ();
        if (baseResponse.getStatus () == 200) {
            Intent i = new Intent (AddNewAddressActivity.this, AddressListActivity.class);
            startActivity (i);
        }
        Toast.makeText (AddNewAddressActivity.this, baseResponse.getMessage (), Toast.LENGTH_LONG).show ();
    }

    private void handleError ( Throwable throwable ) {
        mProgressDialog.dismiss ();
        Toast.makeText (AddNewAddressActivity.this, throwable.getMessage (), Toast.LENGTH_LONG).show ();
        Log.e ("User Data", "negative" + throwable.getMessage ());
    }
}

