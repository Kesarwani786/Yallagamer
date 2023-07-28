package com.augurs.yallagamers.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.augurs.yallagamers.R;
import com.augurs.yallagamers.data_models.AddressInterface;
import com.augurs.yallagamers.data_models.OrderFilterInterface;
import com.augurs.yallagamers.data_models.PeriodModel;
import java.util.ArrayList;

public class OrderPeriodAdapter extends RecyclerView.Adapter<OrderPeriodAdapter.ListViewHolder> {
    private Context context;
    private Integer Index=-1;
    private ArrayList<PeriodModel> categoryList;
    private  OrderFilterInterface addressInterface;
    public OrderPeriodAdapter( Context context, ArrayList<PeriodModel>  categoryList, Integer Index, OrderFilterInterface addressInterface ) {
        this.context = context;
        this.categoryList = categoryList;
        this.Index=Index;
        this.addressInterface=addressInterface;
    }
    public void addListArray(ArrayList<PeriodModel> catList){
        this.Index=-1;
        this.categoryList = catList;
        notifyDataSetChanged();
    }
    public static class ListViewHolder extends RecyclerView.ViewHolder {
        private final TextView TvText;
        private RelativeLayout RlMain;
        private ImageView imgChecnked;
        private  View Liner1;
        public ListViewHolder( View itemView) {
            super(itemView);
            imgChecnked= itemView.findViewById(R.id.imgChecnked);
            RlMain = itemView.findViewById(R.id.RlMain);
            TvText = itemView.findViewById(R.id.TvText);
            Liner1= itemView.findViewById(R.id.Liner1);
        }
    }
    @Override
    public ListViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_period_filter_item, parent, false);
        return new ListViewHolder(v);
    }
    @Override
    public void onBindViewHolder( final ListViewHolder holder,  int position) {
        final PeriodModel data=categoryList.get(position);
        holder.TvText.setText ( data.getName()+"" );
        if(position==Index){
            holder.imgChecnked.setVisibility(View.VISIBLE);
            holder.TvText.setTypeface(null, Typeface.BOLD);
            addressInterface.PeriodId(data.getValue());
        }
        else{
            holder.imgChecnked.setVisibility(View.GONE);
            holder.TvText.setTypeface(null, Typeface.NORMAL);
        }
        if(position==5||position==9)
            holder.Liner1.setVisibility(View.VISIBLE);
        else
            holder.Liner1.setVisibility(View.GONE);
        holder.RlMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick ( View v ) {
                Index=position;
                notifyDataSetChanged();
            }
        });
    }
    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}

