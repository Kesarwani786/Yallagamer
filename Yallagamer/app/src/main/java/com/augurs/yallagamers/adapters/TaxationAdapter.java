package com.augurs.yallagamers.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.augurs.yallagamers.R;
import com.augurs.yallagamers.api_module.Const;
import com.augurs.yallagamers.data_models.Tax;

import java.util.List;

public class TaxationAdapter extends RecyclerView.Adapter<TaxationAdapter.ListViewHolder> {

    private Context context;
    private List<Tax> categoryList;

    public TaxationAdapter ( Context context, List<Tax> categoryList ) {
        this.context = context;
        this.categoryList = categoryList;
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {

        private TextView TvTextHeader, TvTextHeaderValue;

        public ListViewHolder ( View itemView ) {
            super (itemView);
            TvTextHeader = itemView.findViewById (R.id.TvTextHeader);
            TvTextHeaderValue = itemView.findViewById (R.id.TvTextHeaderValue);

        }
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder ( @NonNull ViewGroup parent, int i ) {
        View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.tax_summary, parent, false);
        ListViewHolder evh = new ListViewHolder (v);
        return evh;
    }

    @Override
    public void onBindViewHolder ( final ListViewHolder holder, int position ) {
        final Tax data = categoryList.get (position);
        holder.TvTextHeaderValue.setText (Const.CurrencyValue+data.getRateValue () + "");
        holder.TvTextHeader.setText (data.getDescription () + "");


    }

    @Override
    public int getItemCount () {
        return categoryList.size ();
    }
}

