package com.augurs.yallagamers.UserInterface;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.augurs.yallagamers.R;
import com.augurs.yallagamers.UserInterface.bag.BagFragment;
import com.augurs.yallagamers.UserInterface.categories.CategoriesFragment;
import com.augurs.yallagamers.UserInterface.home.HomeFragment;
import com.augurs.yallagamers.UserInterface.profile.MyProfileFragment;
import com.augurs.yallagamers.UserInterface.user_login.LoginActivity;
import com.augurs.yallagamers.UserInterface.wishlist.WishlistFragment;
import com.augurs.yallagamers.utills.ConstantVariable;
import com.augurs.yallagamers.utills.LoginPreferences;
import com.augurs.yallagamers.utills.UtilityMethods;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Menu menu;
    int Status = 0;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        bottomNavigationView = (BottomNavigationView) findViewById (R.id.nav_view);
        menu = bottomNavigationView.getMenu ();
        if (new LoginPreferences (MainActivity.this).getString ("token") != null)
            menu.findItem (R.id.navigation_my_account).setTitle ("Profile");
        else
            menu.findItem (R.id.navigation_my_account).setTitle ("Login");
        Log.v ("StatusMain", getIntent ().getIntExtra ("ScreenStatus", 0) + "");
        if (getIntent ().getIntExtra ("ScreenStatus", 0) == 1)
            bottomNavigationView.setSelectedItemId (R.id.navigation_home);
        else if (getIntent ().getIntExtra ("ScreenStatus", 0) == 2)
            bottomNavigationView.setSelectedItemId (R.id.navigation_categories);
        else if (getIntent ().getIntExtra ("ScreenStatus", 0) == 3)
            bottomNavigationView.setSelectedItemId (R.id.navigation_dashboard);
        else if (getIntent ().getIntExtra ("ScreenStatus", 0) == 4)
            bottomNavigationView.setSelectedItemId (R.id.navigation_my_account);
        else if (getIntent ().getIntExtra ("ScreenStatus", 0) == 5)
            bottomNavigationView.setSelectedItemId (R.id.navigation_cart);
        updateFragment (getIntent ().getIntExtra ("ScreenStatus", 0));
     //   UpdateStatusCartItem (new LoginPreferences (MainActivity.this).getInt (ConstantVariable.CartItem, 0), new LoginPreferences (MainActivity.this).getInt (ConstantVariable.WishListItem, 0));
        bottomNavigationView.setOnNavigationItemSelectedListener (item -> {
            switch (item.getItemId ()) {
                case R.id.navigation_categories: {
                    updateFragment (2);
                    break;
                }
                case R.id.navigation_dashboard: {
                    updateFragment (3);
                    break;
                }
                case R.id.navigation_my_account: {
                    updateFragment (4);
                    break;
                }
                case R.id.navigation_cart: {
                    updateFragment (5);
                    break;
                }
                default: {
                    updateFragment (1);
                    break;
                }
            }
            return true;
        });
    }

    @SuppressLint ("SetTextI18n")
    private void updateFragment ( int i ) {
        new LoginPreferences (MainActivity.this).put (ConstantVariable.ScreenStatus, i);
        if (Status != i) {
            FragmentTransaction transaction = getSupportFragmentManager ().beginTransaction ();
            if (i == 1) {
                Status = i;
                transaction.replace (R.id.nav_host_fragment, HomeFragment.newInstance ());
                transaction.commit ();
            } else if (i == 2) {
                Status = i;
                transaction.replace (R.id.nav_host_fragment, CategoriesFragment.newInstance ());
                transaction.commit ();
            } else if (i == 3) {
                if (new LoginPreferences (MainActivity.this).getString ("token") != null) {
                    Status = i;
                    transaction.replace (R.id.nav_host_fragment, WishlistFragment.newInstance ());
                    transaction.commit ();
                } else
                    UtilityMethods.PrintToast (MainActivity.this, "Please Login before check your  Wishlist ", 1);
            } else if (i == 4) {
                if (new LoginPreferences (MainActivity.this).getString ("token") != null) {
                    Status = i;
                    transaction.replace (R.id.nav_host_fragment, MyProfileFragment.newInstance ());
                    transaction.commit ();
                } else {
                    Intent intent = new Intent (MainActivity.this, LoginActivity.class);
                    startActivity (intent);
                }
            } else if (i == 5) {
                Status = i;
                transaction.replace (R.id.nav_host_fragment, BagFragment.newInstance ());
                transaction.commit ();
            }
        }
    }

    @Override
    public void onBackPressed ( ) {
        if (Status == 1) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed ();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            UtilityMethods.PrintToast (MainActivity.this, "Please click BACK again to exit", 2);
            new Handler ().postDelayed (new Runnable () {
                @Override
                public void run ( ) {
                    doubleBackToExitPressedOnce = false;
                }
            }, 3000);
        } else {
            bottomNavigationView.setSelectedItemId (R.id.navigation_home);
            updateFragment (1);
        }
    }

    @Override
    protected void onResume ( ) {
        super.onResume ();

    }

    View.OnClickListener onClickListener = new View.OnClickListener () {
        @Override
        public void onClick ( View view ) {
            switch (view.getId ()) {
                case R.id.img_title_bag: {
                    if (new LoginPreferences (MainActivity.this).getInt (ConstantVariable.CartItem, 0) != 0) {
                        Intent i = new Intent (MainActivity.this, BagFragment.class);
                        i.putExtra ("ScreenStatus", Status);
                        i.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity (i);
                    } else
                        UtilityMethods.PrintToast (MainActivity.this, "Please Add some items in your Bag", 1);
                }
                break;
            }
        }
    };

    public void UpdateStatusCartItem ( Integer CartItem, Integer WishListItem ) {
        Log.v ("CartCount", CartItem + "\n\n" + WishListItem + "");
        BottomNavigationMenuView mbottomNavigationMenuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt (0);
        if (WishListItem != 0) {
            View view = mbottomNavigationMenuView.getChildAt (2);
            BottomNavigationItemView itemView = (BottomNavigationItemView) view;
            View cart_badge = LayoutInflater.from (this).inflate (R.layout.count_layout, mbottomNavigationMenuView, false);
            ((TextView) cart_badge.findViewById (R.id.notificationsBadge)).setText (WishListItem + "");
            itemView.addView (cart_badge);
        }
        else {
            View view = mbottomNavigationMenuView.getChildAt (2);
            BottomNavigationItemView itemView1 = (BottomNavigationItemView) view;
            View cart_badge1 = LayoutInflater.from (this).inflate (R.layout.count_layout, mbottomNavigationMenuView, false);
            ((TextView) cart_badge1.findViewById (R.id.notificationsBadge)).setVisibility (View.GONE);
            itemView1.addView (cart_badge1);
        }
        if (CartItem != 0) {
            View view1 = mbottomNavigationMenuView.getChildAt (4);
            BottomNavigationItemView itemView1 = (BottomNavigationItemView) view1;
            View cart_badge1 = LayoutInflater.from (this).inflate (R.layout.count_layout, mbottomNavigationMenuView, false);
            ((TextView) cart_badge1.findViewById (R.id.notificationsBadge)).setText (CartItem + "");
            itemView1.addView (cart_badge1);
        }
        else {
            View view1 = mbottomNavigationMenuView.getChildAt (4);
            BottomNavigationItemView itemView1 = (BottomNavigationItemView) view1;
            View cart_badge1 = LayoutInflater.from (this).inflate (R.layout.count_layout, mbottomNavigationMenuView, false);
            ((TextView) cart_badge1.findViewById (R.id.notificationsBadge)).setVisibility (View.GONE);
            itemView1.addView (cart_badge1);
        }
    }


}