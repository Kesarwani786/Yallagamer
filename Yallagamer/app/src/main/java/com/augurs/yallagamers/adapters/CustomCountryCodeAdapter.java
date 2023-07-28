package com.augurs.yallagamers.adapters;

import android.database.DataSetObserver;
import android.widget.ListAdapter;

import com.augurs.yallagamers.R;
import com.augurs.yallagamers.UserInterface.address.AddNewAddressActivity;
import com.augurs.yallagamers.data_models.CountryListResponse;

import java.util.ArrayList;


import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomCountryCodeAdapter implements ListAdapter {
    ArrayList<CountryListResponse.Datum> arrayList;
    Context context;

    public CustomCountryCodeAdapter ( Context context, ArrayList<CountryListResponse.Datum> arrayList ) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public boolean areAllItemsEnabled ( ) {
        return false;
    }

    @Override
    public boolean isEnabled ( int position ) {
        return true;
    }

    @Override
    public void registerDataSetObserver ( DataSetObserver observer ) {

    }

    @Override
    public void unregisterDataSetObserver ( DataSetObserver observer ) {

    }

    @Override
    public int getCount ( ) {
        return arrayList.size ();
    }

    @Override
    public Object getItem ( int position ) {
        return position;
    }

    @Override
    public long getItemId ( int position ) {
        return position;
    }

    @Override
    public boolean hasStableIds ( ) {
        return false;
    }

    @Override
    public View getView ( int position, View convertView, ViewGroup parent ) {
        CountryListResponse.Datum subjectData = arrayList.get (position);
        convertView = LayoutInflater.from (context).inflate (R.layout.list_row, null);
        TextView TvText = convertView.findViewById (R.id.TvText);
        TvText.setText (subjectData.getCountry ());
        return convertView;
    }

    @Override
    public int getItemViewType ( int position ) {
        return position;
    }

    @Override
    public int getViewTypeCount ( ) {
        return arrayList.size ();
    }

    @Override
    public boolean isEmpty ( ) {
        return false;
    }
}