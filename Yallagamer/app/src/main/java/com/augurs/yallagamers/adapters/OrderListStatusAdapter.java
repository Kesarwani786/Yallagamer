package com.augurs.yallagamers.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.augurs.yallagamers.R;
import com.augurs.yallagamers.data_models.OrderFilterInterface;
import com.augurs.yallagamers.data_models.OrderStatusResponse;
import java.util.ArrayList;

public class OrderListStatusAdapter extends RecyclerView.Adapter<OrderListStatusAdapter.ListViewHolder> {
    private ArrayList<OrderStatusResponse.Datum> topGamesList;
    private OrderFilterInterface orderFilterInterface;
    public OrderListStatusAdapter( ArrayList<OrderStatusResponse.Datum> topGamesList, OrderFilterInterface orderFilterInterface ) {
        this.topGamesList = topGamesList;
        this.orderFilterInterface=orderFilterInterface;
    }
    public void addListArray(ArrayList<OrderStatusResponse.Datum> itemList)
    {
        this.topGamesList = itemList;
        notifyDataSetChanged();
    }
    public static class ListViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout RlMain;
        private final TextView TvOrderStatus;
        private final ImageView imgChecked;
        public ListViewHolder( View itemView) {
            super(itemView);
            TvOrderStatus = itemView.findViewById(R.id.TvOrderStatus);
            RlMain= itemView.findViewById(R.id.RlMain);;
            imgChecked= itemView.findViewById(R.id.imgChecked);
        }
    }
    @Override
    public ListViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_order_status, parent, false);
        ListViewHolder evh = new ListViewHolder(v);
        return evh;
    }
    @Override
    public void onBindViewHolder(final ListViewHolder holder,  int position) {
        final OrderStatusResponse.Datum data= topGamesList.get(position);
        holder.TvOrderStatus.setText ( data.getName () );
        if(data.getChecked()){
            holder.imgChecked.setImageResource(R.drawable.ic_checked);
        }
        else {
            holder.imgChecked.setImageResource(0);
        }
        holder.RlMain.setOnClickListener(v -> {
            if(!data.getChecked()) {
                holder.imgChecked.setImageResource(R.drawable.ic_checked);
                orderFilterInterface.OrderStatusAdd(data.getStatus());
            }
            else {
                holder.imgChecked.setImageResource(0);
                orderFilterInterface.OrderStatusRemove(data.getStatus());
            }
            data.setChecked(!data.getChecked());
            notifyDataSetChanged();
        });
    }
    @Override
    public int getItemCount() {
        return topGamesList.size();
    }
}

