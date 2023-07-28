package com.augurs.yallagamers.UserInterface.bag;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.augurs.yallagamers.R;
import com.augurs.yallagamers.UserInterface.MainActivity;
import com.augurs.yallagamers.UserInterface.ProductDetails.ProductDetailsActivity;
import com.augurs.yallagamers.UserInterface.address.AddAddressModel;
import com.augurs.yallagamers.UserInterface.address.AddNewAddressActivity;
import com.augurs.yallagamers.UserInterface.address.AddressListActivity;
import com.augurs.yallagamers.UserInterface.wishlist.WishlistFragment;
import com.augurs.yallagamers.adapters.CustomCountryCodeAdapter;
import com.augurs.yallagamers.adapters.RecommendedItemAdapter;
import com.augurs.yallagamers.adapters.TaxationAdapter;
import com.augurs.yallagamers.adapters.UserItemAdapter;
import com.augurs.yallagamers.api_module.ApiClient;
import com.augurs.yallagamers.api_module.ApiInterface;
import com.augurs.yallagamers.api_module.Const;
import com.augurs.yallagamers.api_module.DialogUtil;
import com.augurs.yallagamers.data_models.BagItemBaseModel;
import com.augurs.yallagamers.data_models.CheckUserExistEmail;
import com.augurs.yallagamers.data_models.CountryListResponse;
import com.augurs.yallagamers.data_models.Product;
import com.augurs.yallagamers.data_models.ProductDetailBaseModel;
import com.augurs.yallagamers.data_models.PushCartToLoggedInUserModel;
import com.augurs.yallagamers.UserInterface.address.AddressFinalizeActivity;
import com.augurs.yallagamers.UserInterface.user_login.LoginActivity;
import com.augurs.yallagamers.utills.ConstantVariable;
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

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;

public class BagFragment extends Fragment implements ItemRemoveorAddMultiple {
    LoginPreferences loginPreferences;
    String user_token = "";
    private UserItemAdapter userItemAdapter;
    ProgressDialog mProgressDialog;
    ArrayList<Product> productArrayList = new ArrayList<> ();
    RecyclerView rv_item_list;
    ArrayList<String> productIdarray = new ArrayList<> ();
    CheckBox checkbox;
    TextView TvName;
    TextView tv_selected_cart_items, TvAddress, textView54, textView56, textView60, textView26, textView59, TvTextAmountBottom, TvAddressChange;
    RecyclerView rv_similar_item_list, recycleview;
    RelativeLayout RlEmptyLayout;
    LinearLayout LlBag;
    EditText et_s_shipping_f_name, et_s_shipping_l_name, et_s_shipping_Mobile_no, et_s_shipping_Email_id, et_s_shipping_address1, et_s_shipping_address2, et_s_shipping_zipcode, et_s_city, et_s_state, et_s_country;
    EditText et_billing_f_name, et_billing_l_name, et_billing_mobile, et_billing_email, et_billing_address1, et_billing_address2, et_billing_zipcode, et_billing_city, et_billing_country, et_billling_state, et_customer_note;
    TextView click_home, click_work;
    ImageView img_back;
    AddAddressModel addAddressModel;
    CheckBox checkboxdislogue, checkbox_default;
    Button btn_submit;
    int Status = 0;
    private final ArrayList<CountryListResponse.Datum> datumArrayList = new ArrayList<> ();
    PlacesClient placesClient;
    private String CountryCodeShipping,CountryCodeBIlling;
    private Boolean  UserStatus=false;
    AutocompleteSupportFragment autocompleteFragment,autocompleteFragment1;
    private Dialog dialog;
    private CardView Card1;

    public static BagFragment newInstance ( ) {
        return new BagFragment ();
    }


    public View onCreateView ( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View root = inflater.inflate(R.layout.activity_bag, container, false);




        //View root = inflater.inflate (R.layout.activity_bag, container, false);
        TvAddressChange = root.findViewById (R.id.TvAddressChange);
        if (new LoginPreferences (getActivity ()).getString ("cart_id") != null) {
            TvAddressChange.setText ("Add");
        }
        else
            TvAddressChange.setText ("Change");



        TvAddressChange.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick ( View v ) {
                if (new LoginPreferences (getActivity ()).getString ("token") != null) {
                    Intent i = new Intent(new Intent(getActivity (), AddressListActivity.class));
                    i.putExtra("AddressStatus", 0);
                    startActivity(i);
                }
                else {
                    //AddAddressOpenDialogue ();
                    ShowDialogue();
                }
            }
        });
        RlEmptyLayout = root.findViewById (R.id.RlEmptyLayout);
        LlBag = root.findViewById (R.id.LlBag);
        TvTextAmountBottom = root.findViewById (R.id.TvTextAmountBottom);
        TvAddress = root.findViewById (R.id.TvAddress);
        LinearLayout RLWishlist = root.findViewById (R.id.RLWishlist);
        RLWishlist.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick ( View v ) {
                if (loginPreferences.getString ("token") != null) {
                    Intent i = new Intent (getActivity (), MainActivity.class);
                    i.putExtra ("ScreenStatus", 3);
                    startActivity (i);
                    getActivity ().finish ();
                } else {
                    UtilityMethods.PrintToast (getActivity (), "Please Login Before check in wishlist", 1);
                }
            }
        });
        textView54 = root.findViewById (R.id.textView54);
        textView56 = root.findViewById (R.id.textView56);
        textView60 = root.findViewById (R.id.textView60);
        textView26 = root.findViewById (R.id.textView26);
        textView59 = root.findViewById (R.id.textView59);
        rv_similar_item_list = root.findViewById (R.id.rv_similar_item_list);
        recycleview = root.findViewById (R.id.recycleview);
        TvName = root.findViewById (R.id.textView17);
        Card1 = root.findViewById (R.id.Card1);
        Card1.setOnClickListener (v -> {
            if (new LoginPreferences (getActivity ()).getString ("token") != null || addAddressModel != null&&UserStatus) {
                Intent i = new Intent (getActivity (), AddressFinalizeActivity.class);
                i.putExtra (ConstantVariable.AddressParameter, new Gson ().toJson (addAddressModel));
                i.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity (i);
            } else
                ShowDialogue();
                //AddAddressOpenDialogue ();
        });
        ImageView img_empty_cart = root.findViewById (R.id.img_empty_cart);
        tv_selected_cart_items = root.findViewById (R.id.tv_selected_cart_items);
        checkbox = root.findViewById (R.id.checkbox_select_all__cart_items);
        loginPreferences = new LoginPreferences (getActivity ());
        getBagList ();
        img_empty_cart.setOnClickListener (v -> {
                    String asdas = "";
                    if (productIdarray.size () != 0) {
                        for (String asd : productIdarray)
                            asdas = asdas + asd + ",";
                        RemoveApi (asdas.substring (0, asdas.length () - 1).trim ());
                    } else
                        UtilityMethods.PrintToast (getActivity (), "Please Select At Least 1 Product To Remove", 2);
                }
        );
        userItemAdapter = new UserItemAdapter (getActivity (), productArrayList, BagFragment.this);
        rv_item_list = root.findViewById (R.id.rv_item_list);
        rv_item_list.setLayoutManager (new LinearLayoutManager (getActivity ()));
        rv_item_list.setAdapter (userItemAdapter);
        rv_item_list.post (new Runnable () {
            @Override
            public void run ( ) {
                userItemAdapter.notifyDataSetChanged ();
            }
        });
        checkbox.setOnCheckedChangeListener (( buttonView, isChecked ) -> {
                    productIdarray.clear ();
                    if (! isChecked) {
                        for (int i = 0; i < productArrayList.size (); i++) {
                            if (AddRemoveItemIndex (productArrayList.get (i).getProduct_cart_id ()) == - 1) {
                                productIdarray.add (productArrayList.get (i).getProduct_cart_id ().trim ());
                                productArrayList.get (i).setCheckboxStatus (false);
                            }
                        }
                    } else {
                        for (int i = 0; i < productArrayList.size (); i++)
                            productArrayList.get (i).setCheckboxStatus (true);
                    }
                    userItemAdapter.addListArray (productArrayList);
                    tv_selected_cart_items.setText (productIdarray.size () + "/" + productArrayList.size () + " item selected");
                }
        );
        return root;
    }

    private  void ShowDialogue(){
        dialog.show ();
    }
    private void AddAddressOpenDialogue ( ) {
        GetCountryData();
        dialog = new Dialog (getActivity ());
        dialog.requestWindowFeature (Window.FEATURE_NO_TITLE);
        dialog.setCancelable (false);
        dialog.setContentView (R.layout.activity_add_new_address);
        autocompleteFragment1 = (AutocompleteSupportFragment) getFragmentManager ().findFragmentById (R.id.place_autocomplete_fragment1);
        autocompleteFragment = (AutocompleteSupportFragment) getFragmentManager ().findFragmentById (R.id.place_autocomplete_fragment);
        Window window = dialog.getWindow ();
        WindowManager.LayoutParams wlp = window.getAttributes ();
        window.setLayout (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow ().addFlags (WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow ().setBackgroundDrawable (new ColorDrawable (Color.TRANSPARENT));
        window.setAttributes (wlp);
        TextView TvLogin = dialog.findViewById (R.id.TvLogin);
        TvLogin.setVisibility (View.VISIBLE);
        TvLogin.setOnClickListener (v -> {
            Intent intent = new Intent (getActivity (), LoginActivity.class);
            startActivity (intent);
            dialog.dismiss ();
        });
        LinearLayout LinearBillingDetails = dialog.findViewById (R.id.LinearBillingDetails);
        LinearLayout LinearBillingAddress = dialog.findViewById (R.id.LinearBillingAddress);
        et_billing_f_name = dialog.findViewById (R.id.et_billing_f_name);
        et_billing_l_name = dialog.findViewById (R.id.et_billing_l_name);
        et_billing_mobile = dialog.findViewById (R.id.et_billing_mobile);
        et_billing_email = dialog.findViewById (R.id.et_billing_email);
        et_billing_address1 = dialog.findViewById (R.id.et_billing_address1);
        et_billing_address2 = dialog.findViewById (R.id.et_billing_address2);
        et_billing_zipcode = dialog.findViewById (R.id.et_billing_zipcode);
        et_billing_city = dialog.findViewById (R.id.et_billing_city);
        et_billling_state = dialog.findViewById (R.id.et_billling_state);
        et_billing_country = dialog.findViewById (R.id.et_billing_country);
        et_s_shipping_f_name = dialog.findViewById (R.id.et_s_shipping_f_name);
        et_s_shipping_l_name = dialog.findViewById (R.id.et_s_shipping_l_name);
        et_s_shipping_Mobile_no = dialog.findViewById (R.id.et_s_shipping_Mobile_no);
        et_s_shipping_Email_id = dialog.findViewById (R.id.et_s_shipping_Email_id);
        et_s_shipping_address1 = dialog.findViewById (R.id.et_s_shipping_address1);
        et_s_shipping_address2 = dialog.findViewById (R.id.et_s_shipping_address2);
        et_s_shipping_zipcode = dialog.findViewById (R.id.et_s_shipping_zipcode);
        et_s_city = dialog.findViewById (R.id.et_s_city);
        et_s_state = dialog.findViewById (R.id.et_s_state);
        et_s_country = dialog.findViewById (R.id.et_s_country);
        et_customer_note = dialog.findViewById (R.id.et_customer_note);
        click_home = dialog.findViewById (R.id.click_home);
        click_work = dialog.findViewById (R.id.click_work);
        img_back = dialog.findViewById (R.id.img_back);
        checkboxdislogue = dialog.findViewById (R.id.checkbox);
        checkbox_default = dialog.findViewById (R.id.checkbox_default);
        btn_submit = dialog.findViewById (R.id.btn_submit);
        et_s_country.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick ( View v ) {
                if(datumArrayList.size ()==0)
                    UtilityMethods.PrintToast (getActivity (),"Some issue occured ",1);
                else
                    ShowCountryDailogue();
            }
        });
        et_billing_country.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick ( View v ) {
                if(datumArrayList.size ()==0)
                    UtilityMethods.PrintToast (getActivity (),"Some issue occured ",1);
                else
                    ShowCountryBillingDailogue();
            }
        });

            String apiKey = getString (R.string.api_key);
            if (! Places.isInitialized ()) {
                Places.initialize (getActivity (), apiKey);
            }
            placesClient = Places.createClient (getActivity ());
            autocompleteFragment.setTypeFilter (TypeFilter.ADDRESS);
            autocompleteFragment.setPlaceFields (Arrays.asList (Place.Field.LAT_LNG, Place.Field.NAME, Place.Field.ADDRESS));

            autocompleteFragment1.setTypeFilter (TypeFilter.ADDRESS);
            autocompleteFragment1.setPlaceFields (Arrays.asList (Place.Field.LAT_LNG, Place.Field.NAME, Place.Field.ADDRESS));
            autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener () {
                @Override
                public void onError ( @NonNull com.google.android.gms.common.api.Status status ) {
                    //Toast.makeText(getApplicationContext(), sta
                    // tus.toString(), Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onPlaceSelected( @NonNull Place place) {
                    try {
                        List<Address> addresses = new Geocoder (getActivity (), Locale.getDefault()).getFromLocation(Objects.requireNonNull (place.getLatLng ()).latitude, place.getLatLng().longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
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

            autocompleteFragment1.setOnPlaceSelectedListener(new PlaceSelectionListener () {
                @Override
                public void onError ( @NonNull Status status ) {
                    //Toast.makeText(getApplicationContext(), status.toString(), Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onPlaceSelected( @NonNull Place place) {
                    try {
                        List<Address>  addresses = new Geocoder (getActivity (), Locale.getDefault()).getFromLocation(Objects.requireNonNull (place.getLatLng ()).latitude, place.getLatLng().longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
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



        img_back.setOnClickListener (view -> {
            dialog.dismiss ();
            addAddressModel = null;
        });

        checkbox_default.setOnCheckedChangeListener (( buttonView, isChecked ) -> {
            if (isChecked)
                addAddressModel.setIsDefault (1);
            else
                addAddressModel.setIsDefault (0);
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

        checkboxdislogue.setOnCheckedChangeListener (( buttonView, isChecked ) -> {
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
        btn_submit.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick ( View v ) {
                if (et_s_shipping_f_name.getText ().toString ().length () == 0) {
                    UtilityMethods.PrintToast (getActivity (), "Please add Shipping User's First Name   ", 1);
                } else if (et_s_shipping_l_name.getText ().toString ().length () == 0) {
                    UtilityMethods.PrintToast (getActivity (), "Please add Shipping User's Last Name  ", 1);
                } else if (et_s_shipping_Mobile_no.getText ().toString ().length () == 0) {
                    UtilityMethods.PrintToast (getActivity (), "Please add Shipping User's Mobile No ", 1);
                } else if (et_s_shipping_Mobile_no.getText ().toString ().length () != 10) {
                    UtilityMethods.PrintToast (getActivity (), "Please add Shipping User's Valid Mobile No ", 1);
                }
                else if(et_s_shipping_Email_id.getText ().toString ().length ()==0&& !et_s_shipping_Email_id.getText ().toString ().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
                    UtilityMethods.PrintToast (getActivity (), "Please Add  User's Valid Email id ", 1);
                }
                else if (et_s_shipping_address1.getText ().toString ().length () == 0) {
                    UtilityMethods.PrintToast (getActivity (), "Please add Shipping User's Address   ", 1);
                }  else if (et_s_shipping_zipcode.getText ().toString ().length () == 0) {
                    UtilityMethods.PrintToast (getActivity (), "Please add Shipping User's Address Zip code  ", 1);
                }  else if (et_s_city.getText ().toString ().length () == 0) {
                    UtilityMethods.PrintToast (getActivity (), "Please Add Shipping User's City  ", 1);
                } else if (et_s_state.getText ().toString ().length () == 0) {
                    UtilityMethods.PrintToast (getActivity (), "Please Add Shipping User's State   ", 1);
                } else if (et_s_country.getText ().toString ().length () == 0) {
                    UtilityMethods.PrintToast (getActivity (), "Please Add Shipping User's Country  ", 1);
                } else {
                    if (addAddressModel.getIsSame () == 0) {
                        if (et_billing_f_name.getText ().toString ().length () == 0) {
                            UtilityMethods.PrintToast (getActivity (), "Please Add Shipping User's First Name   ", 1);
                        } else if (et_billing_l_name.getText ().toString ().length () == 0) {
                            UtilityMethods.PrintToast (getActivity (), "Please Add Billing User's Last Name ", 1);
                        } else if (et_billing_mobile.getText ().toString ().length () == 0) {
                            UtilityMethods.PrintToast (getActivity (), "Please Add Billing User's Mobile No  ", 1);
                        } else if (et_billing_mobile.getText ().toString ().length () != 10) {
                            UtilityMethods.PrintToast (getActivity (), "Please Add Billing User's Valid Mobile No ", 1);
                        }
                        else if(et_billing_email.getText ().toString ().length ()!=0&& !et_billing_email.getText ().toString ().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
                            UtilityMethods.PrintToast (getActivity (), "Please Add  User's Valid Email id ", 1);
                        }
                        //Email is not Validate not Manditory
                        else if (et_billing_address1.getText ().toString ().length () == 0) {
                            UtilityMethods.PrintToast (getActivity (), "Please Add Billing User's Address   ", 1);
                        } else if (et_billing_zipcode.getText ().toString ().length () == 0) {
                            UtilityMethods.PrintToast (getActivity (), "Please Add Billing User's Address Zip code  ", 1);
                        }  else if (et_billing_city.getText ().toString ().length () == 0) {
                            UtilityMethods.PrintToast (getActivity (), "Please Add Billing User's City ", 1);
                        } else if (et_billling_state.getText ().toString ().length () == 0) {
                            UtilityMethods.PrintToast (getActivity (), "Please Add Billing User's State  ", 1);
                        } else if (et_billing_country.getText ().toString ().length () == 0) {
                            UtilityMethods.PrintToast (getActivity (), "Please Add Billing User's Country    ", 1);
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
                    if (et_s_shipping_Email_id.getText ().toString () != null)
                        addAddressModel.setsEmail (et_s_shipping_Email_id.getText ().toString ());
                    if (et_customer_note.getText ().toString ().length () != 0)
                        addAddressModel.setCustomerNotes (et_customer_note.getText ().toString ());
                    Log.v ("addAddressModel", new Gson ().toJson (addAddressModel));
                    String text = "<font color=#d3d3d3>Deliver to : </font> <font color=#000000>" + addAddressModel.getsFirstname () + " " + addAddressModel.getsLastname () + "," + addAddressModel.getsZipcode () + "</font >";
                    TvName.setText (Html.fromHtml (text));
                    TvAddress.setText (addAddressModel.getsAddress () + " " + addAddressModel.getsAddress2 () + " " + addAddressModel.getsCity () + " " + addAddressModel.getsState () + " " + addAddressModel.getsCountry ());
                    if (addAddressModel.getsAddress ()!=null&& addAddressModel.getsCity ()!=null&& addAddressModel.getsState ()!=null&& addAddressModel.getsCountry ()!=null) {
                        TvAddressChange.setText ("Update");
                    }
                    else
                        TvAddressChange.setText ("Add");
                    ApiToGetCLientUpdate(et_s_shipping_Email_id.getText ().toString ());
                    dialog.dismiss ();
                }
            }
        });
        if (Status != 0) {
            TextView toolbar_title = dialog.findViewById (R.id.toolbar_title);
            toolbar_title.setText ("Update Address");
            btn_submit.setText ("Update Address");
            if (addAddressModel.getIsDefault () == 1)
                checkbox_default.setChecked (true);
            else
                checkbox_default.setChecked (false);
            if (addAddressModel.getIsSame () == 1)
                checkbox.setChecked (true);
            else
                checkbox.setChecked (false);
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
                et_billing_country.setText (CountryCodeBIlling);
                et_billing_city.setText (addAddressModel.getbCity ());
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
            et_s_country.setText (CountryCodeShipping);
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
            Status++;
        }
    }

    private void ApiToGetCLientUpdate ( String Email  ) {
        ApiInterface apiService = ApiClient.getClient (getActivity ()).create (ApiInterface.class);
        String header = Credentials.basic (getResources ().getString (R.string.user_id), getResources ().getString (R.string.user_password));
        mProgressDialog = DialogUtil.getProgressDialog (getActivity (), getResources ().getString (R.string.app_DialogInfo), getResources ().getString (R.string.app_DialogLoading));
        Single<CheckUserExistEmail> catObservable=apiService.CheckUserExistEmailRequest (header, "User", "application/json","check_user_exists",Email);
        catObservable.observeOn (AndroidSchedulers.mainThread ()).subscribeOn (Schedulers.io ()).subscribe (this :: CheckUserExistResponce, this :: handleErrorCE);
    }

    private void CheckUserExistResponce ( CheckUserExistEmail checkUserExistEmail ) {
        mProgressDialog.dismiss ();
        if(checkUserExistEmail.getStatus ()==200){
            if(checkUserExistEmail.getData ().getUser ())
                UtilityMethods.PrintToast (getActivity (),"Please continue with other Shipping Email Id ",1);
            else
                UserStatus= true;
        }
        else {
            UtilityMethods.PrintToast (getActivity (),checkUserExistEmail.getData ().getMessage (),1);
        }
    }

    private void getBagList ( ) {
        if (UtilityMethods.isNetworkConnected (getActivity ())) {
            ApiInterface apiService = ApiClient.getClient (getActivity ()).create (ApiInterface.class);
            String header = Credentials.basic (getResources ().getString (R.string.user_id), getResources ().getString (R.string.user_password));
            Single<BagItemBaseModel> catObservable;
            if (new LoginPreferences (getActivity ()).getString ("token") != null) {
                mProgressDialog = DialogUtil.getProgressDialog (getActivity (), getResources ().getString (R.string.app_DialogInfo), getResources ().getString (R.string.app_DialogLoading));
                catObservable = apiService.getBagItemListUserAuthorised (header, "Cart", "getCart", new LoginPreferences (getActivity ()).getString ("token"));
                catObservable.observeOn (AndroidSchedulers.mainThread ()).subscribeOn (Schedulers.io ()).subscribe (this :: handleResultsCE, this :: handleErrorCE);
            } else if (new LoginPreferences (getActivity ()).getString ("cart_id") != null) {
                mProgressDialog = DialogUtil.getProgressDialog (getActivity (), getResources ().getString (R.string.app_DialogInfo), getResources ().getString (R.string.app_DialogLoading));
                catObservable = apiService.getBagItemListNonUser (header, "Cart", "getCart", new LoginPreferences (getActivity ()).getString ("cart_id"));
                catObservable.observeOn (AndroidSchedulers.mainThread ()).subscribeOn (Schedulers.io ()).subscribe (this :: handleResultsCE, this :: handleErrorCE);
            } else
                RlEmptyLayout.setVisibility (View.VISIBLE);
            //UtilityMethods.PrintToast (getActivity (), "Your Cart Bag is Empty ", 2);

        } else {
            GetDialogue (1);
        }
    }
    private void ShowCountryBillingDailogue ( ) {
        ListPopupWindow listPopupWindow = new ListPopupWindow (getActivity ());
        listPopupWindow.setAdapter (new CustomCountryCodeAdapter (getActivity (), datumArrayList));
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

    private void GetCountryData ( ) {
        mProgressDialog = DialogUtil.getProgressDialog (getActivity (), getResources ().getString (R.string.app_DialogInfo), getResources ().getString (R.string.app_DialogLoading));
        ApiInterface apiService = ApiClient.getClient (getActivity ()).create (ApiInterface.class);
        String _d = "User"; //
        String header = Credentials.basic (getResources ().getString (R.string.user_id), getResources ().getString (R.string.user_password));
        Single<CountryListResponse> catObservable = apiService.GetCountryList (header, _d, "application/json","get_countries");
        catObservable.observeOn (AndroidSchedulers.mainThread ()).subscribeOn (Schedulers.io ()).subscribe (this :: handleCountryResults, this :: handleError);
    }

    private void handleError ( Throwable throwable ) {
        mProgressDialog.dismiss ();
        Toast.makeText (getActivity (), throwable.getMessage (), Toast.LENGTH_LONG).show ();
        Log.e ("User Data", "negative" + throwable.getMessage ());
    }

    private void handleCountryResults ( CountryListResponse countryListResponse ) {
        mProgressDialog.dismiss ();
        if(countryListResponse.getStatus ()==200)
            datumArrayList.addAll (countryListResponse.getData ());
        else
            UtilityMethods.PrintToast (getActivity (),"Unable to fetch the country code ",1);
    }


    private void GetDialogue ( int i ) {
        Dialog dialog = new Dialog (getActivity ());
        dialog.requestWindowFeature (Window.FEATURE_NO_TITLE);
        dialog.setCancelable (false);
        dialog.setContentView (R.layout.dialog);
        Window window = dialog.getWindow ();
        WindowManager.LayoutParams wlp = window.getAttributes ();
        window.setLayout (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow ().addFlags (WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow ().setBackgroundDrawable (new ColorDrawable (android.graphics.Color.TRANSPARENT));
        window.setAttributes (wlp);
        TextView TvRetry = dialog.findViewById (R.id.TvRetry);
        TvRetry.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick ( View v ) {
                if (i == 1 && UtilityMethods.isNetworkConnected (getActivity ()))
                    getBagList ();
                else
                    GetDialogue (i);
                dialog.dismiss ();
            }
        });
        TextView TvRetryNot = dialog.findViewById (R.id.TvRetryNot);
        TvRetryNot.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick ( View v ) {
                dialog.dismiss ();
            }
        });
        //dialog.show ();
    }

    private void handleErrorCE ( Throwable throwable ) {
        mProgressDialog.dismiss ();
        Log.e ("Error",new Gson ().toJson (throwable));
        Toast.makeText (getActivity (), throwable.getMessage (), Toast.LENGTH_LONG).show ();
    }

    private void handleResultsCE ( BagItemBaseModel bagItemData ) {
        Log.v ("bagItemData", new Gson ().toJson (bagItemData));
        mProgressDialog.dismiss ();
        if (bagItemData.getStatus () == 200) {
            //new LoginPreferences (getActivity ()).put (ConstantVariable.CartItem, bagItemData.getData ().getCartProducts ().size ());
            //new LoginPreferences (getActivity ()).put (ConstantVariable.WishListItem, bagItemData.getData ()..size ());
            if (bagItemData.getData ().getCartProducts ().size () == 0) {
                RlEmptyLayout.setVisibility (View.VISIBLE);
                LlBag.setVisibility (View.GONE);
                new LoginPreferences (getActivity ()).put (ConstantVariable.CartItem, 0);
                new LoginPreferences (getActivity ()).put (ConstantVariable.WishListItem, 0);
                ((MainActivity) getActivity ()).UpdateStatusCartItem (0, 0);
            } else {
                if (bagItemData.getData ().getNotice ().length () != 0) {
                    DialogueForNotice (bagItemData.getData ().getNotice ());
                }
                new LoginPreferences (getActivity ()).put (ConstantVariable.CartItem, bagItemData.getData ().getCartCounts ());
                new LoginPreferences (getActivity ()).put (ConstantVariable.WishListItem, bagItemData.getData ().getWishlistCounts ());
                ((MainActivity) getActivity ()).UpdateStatusCartItem (0, 0);
                LlBag.setVisibility (View.VISIBLE);
                RlEmptyLayout.setVisibility (View.GONE);
                if (new LoginPreferences (getActivity ()).getString ("token") != null) {
                    String text = "<font color=#d3d3d3>Deliver to : </font> <font color=#000000>" + bagItemData.getData ().getUserData ().getFirstname () + " " + bagItemData.getData ().getUserData ().getLastname () + "," + bagItemData.getData ().getUserData ().getS_zipcode () + "</font >";
                    TvName.setText (Html.fromHtml (text));
                    TvAddress.setText (bagItemData.getData ().getUserData ().getS_address () + " " + bagItemData.getData ().getUserData ().getS_address_2 () + " " + bagItemData.getData ().getUserData ().getS_city () + " " + bagItemData.getData ().getUserData ().getS_state () + " " + bagItemData.getData ().getUserData ().getS_country ());
                } else {
                    String text = "<font color=#d3d3d3>Deliver to : </font> <font color=#000000>";
                    TvName.setText (Html.fromHtml (text));
                    AddAddressOpenDialogue();
                }
                textView54.setText (Const.CurrencyValue + bagItemData.getData ().getSubtotal () + " ");
                textView56.setText (Const.CurrencyValue + bagItemData.getData ().getDiscount () + " ");
                Card1.setVisibility (View.VISIBLE);
                textView60.setText (Const.CurrencyValue + bagItemData.getData ().getDiscountedSubtotal () + " ");
                //  TvTextAmountBottom.setText ("Buy "+bagItemData.getData ().getCartProducts ().size ()+" item for "+Const.CurrencyValue+bagItemData.getData ().getDiscountedSubtotal () + " ");
                userItemAdapter.addListArray (bagItemData.getData ().getCartProducts ());
                productArrayList = bagItemData.getData ().getCartProducts ();
                textView26.setText (Html.fromHtml ("<font color=#000000>You will earn </font> <font color=#d3d3d3>" + bagItemData.getData ().getCartId () + "</font><font color=#000000> insider points on this purchase </font>"));
                if (bagItemData.getData ().getTaxes ().size () == 0) {
                    recycleview.setVisibility (View.GONE);
                    textView59.setVisibility (View.GONE);
                } else {
                    recycleview.setLayoutManager (new LinearLayoutManager (getActivity (), LinearLayoutManager.VERTICAL, false));
                    recycleview.setAdapter (new TaxationAdapter (getActivity (), bagItemData.getData ().getTaxes ()));
                    recycleview.setVisibility (View.VISIBLE);
                    textView59.setVisibility (View.VISIBLE);
                }
                rv_similar_item_list.setLayoutManager (new LinearLayoutManager (getActivity (), LinearLayoutManager.HORIZONTAL, false));
                rv_similar_item_list.setAdapter (new RecommendedItemAdapter (getActivity (), bagItemData.getData ().getSimilarProducts ()));



            }
        } else if (bagItemData.getStatus () == 204) {
            RlEmptyLayout.setVisibility (View.VISIBLE);
            new LoginPreferences (getActivity ()).put (ConstantVariable.CartItem, 0);
            new LoginPreferences (getActivity ()).put (ConstantVariable.WishListItem, 0);
            ((MainActivity) getActivity ()).UpdateStatusCartItem (0, 0);
        } else {
            Toast.makeText (getActivity (), bagItemData.getData ().getMessage (), Toast.LENGTH_LONG).show ();
        }
    }

    private void DialogueForNotice ( String notice ) {
        Dialog dialog1 = new Dialog (getActivity ());
        dialog1.requestWindowFeature (Window.FEATURE_NO_TITLE);
        dialog1.setCancelable (false);
        dialog1.setContentView (R.layout.dialog);
        Window window = dialog1.getWindow ();
        WindowManager.LayoutParams wlp = window.getAttributes ();
        window.setLayout (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog1.getWindow ().addFlags (WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog1.getWindow ().setBackgroundDrawable (new ColorDrawable (android.graphics.Color.TRANSPARENT));
        window.setAttributes (wlp);
        TextView TvTeas = dialog1.findViewById (R.id.TvTeas);
        TvTeas.setText (notice);
        TextView TvRetry = dialog1.findViewById (R.id.TvRetry);
        TvRetry.setText ("Continue");
        TvRetry.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick ( View v ) {
                Intent i = new Intent (getActivity (), MainActivity.class);
                i.putExtra ("ScreenStatus", 5);
                getActivity ().startActivity (i);
                ((MainActivity) getActivity ()).finish ();
                dialog1.dismiss ();
            }
        });
        TextView TvRetryNot = dialog1.findViewById (R.id.TvRetryNot);
        TvRetryNot.setText ("No");
        TvRetryNot.setVisibility (View.GONE);
        TvRetryNot.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick ( View v ) {
                dialog.dismiss ();
            }
        });
        dialog1.show ();
    }

    private void ShowCountryDailogue ( ) {
        ListPopupWindow listPopupWindow = new ListPopupWindow (getActivity ());
        listPopupWindow.setAdapter (new CustomCountryCodeAdapter (getActivity (), datumArrayList));
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


    @Override
    public void AddRemoveItem ( String id ) {
        if (AddRemoveItemIndex (id) == - 1)
            productIdarray.add (id);
        else
            productIdarray.remove (id);
        tv_selected_cart_items.setText (productIdarray.size () + "/" + productArrayList.size () + " item selected");
    }

    @Override
    public void QuantityUpdate ( String productCartId, Integer Quantity ) {
        mProgressDialog = DialogUtil.getProgressDialog (getActivity (), getResources ().getString (R.string.app_DialogInfo), getResources ().getString (R.string.app_DialogLoading));
        PushCartToLoggedInUserModel pushCartToLoggedInUserModel = new PushCartToLoggedInUserModel ();
        pushCartToLoggedInUserModel.setMethod ("updateCartProductQuantity");
        pushCartToLoggedInUserModel.setProduct_cart_id (productCartId);
        pushCartToLoggedInUserModel.setQuantity (Quantity + "");
        if( new LoginPreferences (getActivity ()).getString ("token")==null)
            pushCartToLoggedInUserModel.setCartId (new LoginPreferences (getActivity ()).getString ("cart_id"));
        ApiInterface apiService = ApiClient.getClient (getActivity ()).create (ApiInterface.class);
        String header = Credentials.basic (getResources ().getString (R.string.user_id), getResources ().getString (R.string.user_password));
        Observable<BagItemBaseModel> catObservable;
        if( new LoginPreferences (getActivity ()).getString ("token")!=null){
            catObservable = apiService.UpdateCart (header, "Cart", new LoginPreferences (getActivity ()).getString ("token"), pushCartToLoggedInUserModel);
        }
        else {
            catObservable = apiService.UpdateCartForGuest (header, "Cart", pushCartToLoggedInUserModel, null );
        }

        catObservable.observeOn (AndroidSchedulers.mainThread ()).subscribeOn (Schedulers.io ()).observeOn (AndroidSchedulers.mainThread ()).subscribe (this :: handleResultsCE, this :: handleErrorCE);
    }

    private void RemoveApi ( String id ) {
        PushCartToLoggedInUserModel pushCartToLoggedInUserModel = new PushCartToLoggedInUserModel ();
        pushCartToLoggedInUserModel.setMethod ("deleteCartProduct");
        if (new LoginPreferences (getActivity ()).getString ("token") != null)
            pushCartToLoggedInUserModel.setCartId (new LoginPreferences (getActivity ()).getString ("cart_id"));
        pushCartToLoggedInUserModel.setProduct_cart_id (id);
        mProgressDialog = DialogUtil.getProgressDialog (getActivity (), getResources ().getString (R.string.app_DialogInfo), getResources ().getString (R.string.app_DialogLoading));
        ApiInterface apiService = ApiClient.getClient (getActivity ()).create (ApiInterface.class);
        String header = Credentials.basic (getResources ().getString (R.string.user_id), getResources ().getString (R.string.user_password));
        Single<ProductDetailBaseModel> catObservable;
        if (new LoginPreferences (getActivity ()).getString ("token") != null) {
            catObservable = apiService.RemoveoneByOneProduct (header, "Cart", new LoginPreferences (getActivity ()).getString ("token"), pushCartToLoggedInUserModel);
        } else
            catObservable = apiService.RemoveoneByOneProduct (header, "Cart", pushCartToLoggedInUserModel,new LoginPreferences (getActivity ()).getString ("cart_id"));
        catObservable.subscribeOn (Schedulers.io ()).observeOn (AndroidSchedulers.mainThread ()).subscribe (this :: HandleResponceForRemoveBag, this :: handleErrorCE);
    }

    private void HandleResponceForRemoveBag ( ProductDetailBaseModel baseResponse ) {
        mProgressDialog.dismiss ();
        if (baseResponse.getStatus () == 200) {
            new LoginPreferences (getActivity ()).put (ConstantVariable.CartItem, baseResponse.getData ().getCartCounts ());
            new LoginPreferences (getActivity ()).put (ConstantVariable.WishListItem, baseResponse.getData ().getWishlistCounts ());
            ((MainActivity)getActivity ()).UpdateStatusCartItem(baseResponse.getData().getCartCounts(),baseResponse.getData().getWishlistCounts());
            Intent i = new Intent (getActivity (), MainActivity.class);
            i.putExtra ("ScreenStatus",5);
            startActivity (i);
            ((MainActivity) getActivity ()).finish ();
        }
    }

    private int AddRemoveItemIndex ( String id ) {
        int Status = - 1;
        for (int i = 0; i < productIdarray.size (); i++) {
            if (id.equals (productIdarray.get (i))) {
                Status = productIdarray.indexOf (id);
                break;
            }
        }
        return Status;
    }

}