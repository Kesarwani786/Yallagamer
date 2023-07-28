package com.augurs.yallagamers.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.augurs.yallagamers.R;
import com.augurs.yallagamers.UserInterface.CategoryListItem.CategoryListItemActivity;
import com.augurs.yallagamers.data_models.Box;
import com.augurs.yallagamers.UserInterface.ProductDetails.ProductDetailsActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;


import java.util.ArrayList;
public class StaticCategoryItemAdapter extends RecyclerView.Adapter<StaticCategoryItemAdapter.ListViewHolder> {
    private Context context;
    private ArrayList < Box > productList;
    public StaticCategoryItemAdapter(Context context, ArrayList< Box > productList) {
        this.context = context;
        this.productList = productList;
    }
    public static class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView TvText;
        private ImageView imgProduct;
        private ImageView star_button;
        public ListViewHolder( View itemView) {
            super(itemView);
            TvText = itemView.findViewById(R.id.TvText);
            imgProduct = itemView.findViewById(R.id.img);
        }
    }
    @Override
    public ListViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.static_item, parent, false);
        ListViewHolder evh = new ListViewHolder(v);
        return evh;
    }
    @Override
    public void onBindViewHolder(final ListViewHolder holder,  int position) {
        Box box= productList.get(position);
        Log.v("box",new Gson ().toJson (box));
        holder.TvText.setText(box.getCategory ());

        Glide.with(this.context)
                .load(box.getImage ())
                .diskCacheStrategy( DiskCacheStrategy.ALL)
                .placeholder(R.drawable.placeholder)
                .into(holder.imgProduct);

        holder.imgProduct.setOnClickListener( view -> {
            Intent intent=new Intent(context, CategoryListItemActivity.class);
            intent.putExtra("category_id",box.getCategory_id ()+"");
            intent.putExtra ("category",box.getCategory ());
            context.startActivity(intent);
        } );
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


}


