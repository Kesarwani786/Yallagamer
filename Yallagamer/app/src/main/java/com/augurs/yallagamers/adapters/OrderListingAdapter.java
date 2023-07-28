package com.augurs.yallagamers.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.augurs.yallagamers.R;
import com.augurs.yallagamers.UserInterface.Order.OrderDetailsActivity;
import com.augurs.yallagamers.data_models.OrderListingModel;
import com.google.gson.Gson;

import java.util.List;

public class OrderListingAdapter extends RecyclerView.Adapter<OrderListingAdapter.ListViewHolder> {
    private Context context;
    private List <OrderListingModel.Order> categoryList;
    public OrderListingAdapter(Context context, List< OrderListingModel.Order > categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }
    public void OrderListingAdapterRefresh(List< OrderListingModel.Order > categoryList) {
        this.categoryList.clear();
        notifyDataSetChanged();
    }
    public static class ListViewHolder extends RecyclerView.ViewHolder {
        private final TextView TvDate, TvStatus, TvUserName, TvAmount,TvId,Tvemail;
        private CardView Card,Card1;
        public ListViewHolder(View itemView) {
            super(itemView);
            Card = itemView.findViewById(R.id.TvId);
            TvDate = itemView.findViewById(R.id.TvDate);
            TvStatus = itemView.findViewById(R.id.TvStatus);
            TvUserName = itemView.findViewById(R.id.TvUserName);
            TvAmount = itemView.findViewById(R.id.TvAmount);
            TvId=itemView.findViewById(R.id.TvId1);
            Tvemail=itemView.findViewById(R.id.Tvemail);
            Card1= itemView.findViewById(R.id.Card1);
        }
    }
    @Override
    public ListViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_listing_item, parent, false);
        return new ListViewHolder(v);
    }
    @Override
    public void onBindViewHolder( final ListViewHolder holder,  int position) {
        final OrderListingModel.Order data=categoryList.get(position);

        holder.TvDate.setText ( data.getTimestamp()+"" );
        holder.Card1.setCardBackgroundColor(Color.parseColor(data.getColor()));
        holder.TvStatus.setText ( "Status : "+data.getStatus ());
        holder.TvUserName.setText(data.getFirstname()+" "+data.getLastname());
        holder.TvAmount.setText(data.getTotal() );
        holder.TvUserName.setTypeface(null, Typeface.NORMAL);
        holder.TvAmount.setTypeface(null, Typeface.BOLD);
        holder.TvId.setText("#"+data.getOrderId());
        holder.Tvemail.setText(data.getEmail());
        holder.Card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick ( View v ) {
                Log.v("OrderListing",new Gson ().toJson (data));
                Intent i = new Intent(context, OrderDetailsActivity.class);
                i.putExtra("OrderId",data.getOrderId());
                context.startActivity(i);
            }
        });
    }
    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}

