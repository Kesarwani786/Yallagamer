package com.augurs.yallagamers.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.augurs.yallagamers.R;
import com.augurs.yallagamers.UserInterface.Order.OrderDetailsActivity;
import com.augurs.yallagamers.data_models.Product;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ProductListOrderDetailsById extends RecyclerView.Adapter<ProductListOrderDetailsById.ListViewHolder> {
    private ArrayList<Product> addressLIstingModel;
    private  Context context;
    public ProductListOrderDetailsById( Context context, ArrayList<Product > addressLIstingModel ) {
        this.addressLIstingModel = addressLIstingModel;
        this.context=context;
    }
    public static class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView TvProductName,TvProductCategoryName,TvProductQuantity,TvProductAmount;
        private ImageView ImgProdunt;
        public ListViewHolder( View itemView) {
            super(itemView);
            TvProductName = itemView.findViewById(R.id.TvProductName);
            TvProductCategoryName = itemView.findViewById(R.id.TvProductCategoryName);
            TvProductQuantity = itemView.findViewById(R.id.TvProductQuantity);
            TvProductAmount = itemView.findViewById(R.id.TvProductAmount);
            ImgProdunt = itemView.findViewById(R.id.ImgProdunt);
        }
    }
    @Override
    public ListViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_order_details_product, parent, false);
        return  new ListViewHolder(v);
    }
    @SuppressLint ("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder,  int position) {
        holder.TvProductName.setText( addressLIstingModel.get(position).getProduct ());
        holder.TvProductCategoryName.setText("Platform : "+"Not Added from APi");
        holder.TvProductQuantity.setText("Quantity : "+ addressLIstingModel.get(position).getAmount ()+"");
        holder.TvProductAmount.setText("Amount : "+addressLIstingModel.get(position).getDisplay_subtotal ()+"");
        holder.TvProductAmount.setTypeface (null, Typeface.BOLD);
        Glide.with(context).load(addressLIstingModel.get(position).getMainImage ()).placeholder(R.drawable.placeholder).into(holder.ImgProdunt);
    }
    @Override
    public int getItemCount() {
        return addressLIstingModel.size();
    }

}



