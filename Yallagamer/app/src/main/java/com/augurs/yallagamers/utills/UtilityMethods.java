package com.augurs.yallagamers.utills;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.augurs.yallagamers.R;

public class UtilityMethods {
    public static String numberToWord ( int number ) {
        // variable to hold string representation of number
        String words = "";
        String unitsArray[] = { "Zero", "One", "Two", "Three", "Four", "Five", "Six",
                "Seven", "Eight", "Nine", "Ten", "eleven", "twelve",
                "thirteen", "fourteen", "fifteen", "sixteen", "seventeen",
                "eighteen", "nineteen" };
        String tensArray[] = { "Zero", "ten", "twenty", "thirty", "forty", "fifty",
                "sixty", "seventy", "eighty", "ninety" };

        if (number == 0) {
            return "zero";
        }
        // add minus before conversion if the number is less than 0
        if (number < 0) {
            // convert the number to a string
            String numberStr = "" + number;
            // remove minus before the number
            numberStr = numberStr.substring(1);
            // add minus before the number and convert the rest of number
            return "minus " + numberToWord(Integer.parseInt(numberStr));
        }
        // check if number is divisible by 1 million
        if ((number / 1000000) > 0) {
            words += numberToWord(number / 1000000) + " million ";
            number %= 1000000;
        }
        // check if number is divisible by 1 thousand
        if ((number / 1000) > 0) {
            words += numberToWord(number / 1000) + " thousand ";
            number %= 1000;
        }
        // check if number is divisible by 1 hundred
        if ((number / 100) > 0) {
            words += numberToWord(number / 100) + " hundred ";
            number %= 100;
        }

        if (number > 0) {
            // check if number is within teens
            if (number < 20) {
                // fetch the appropriate value from unit array
                words += unitsArray[number];
            } else {
                // fetch the appropriate value from tens array
                words += tensArray[number / 10];
                if ((number % 10) > 0) {
                    words += "-" + unitsArray[number % 10];
                }
            }
        }
        return words;
    }
    public static boolean isNetworkConnected( Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return (networkInfo != null && networkInfo.isConnectedOrConnecting());
        }
        return false;
    }
    public static void PrintToast( Context context,String Message,int ToastType) {
            LayoutInflater inflater1 = (LayoutInflater) context.getSystemService ( Context.LAYOUT_INFLATER_SERVICE );
            View layout = inflater1.inflate ( R.layout.custom_toast_layout , null );
            TextView toastTextView = (TextView) layout.findViewById ( R.id.tv_texsa );
            toastTextView.setText (Message);
            if(ToastType==0)
            layout.setBackgroundResource ( R.drawable.toastgreen );
            if(ToastType==1)
            layout.setBackgroundResource ( R.drawable.toastred );
            if(ToastType==2)
            layout.setBackgroundResource ( R.drawable.toastgrey );
            Toast toast = new Toast ( context);
            toast.setDuration ( Toast.LENGTH_SHORT );
            toast.setView ( layout );
            toast.show ( );
    }
}
