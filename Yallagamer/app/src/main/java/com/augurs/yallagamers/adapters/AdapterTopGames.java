package com.augurs.yallagamers.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.augurs.yallagamers.data_models.Top_Games;
import com.augurs.yallagamers.UserInterface.ProductDetails.ProductDetailsActivity;
import com.augurs.yallagamers.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AdapterTopGames extends RecyclerView.Adapter<AdapterTopGames.ListViewHolder> {
    private Context context;
    private ArrayList<Top_Games> topGamesList;

    public AdapterTopGames(Context context, ArrayList<Top_Games> topGamesList) {
        this.context = context;
        this.topGamesList = topGamesList;
    }

    public void addListArray(ArrayList<Top_Games> itemList) {
        this.topGamesList = itemList;
        notifyDataSetChanged();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout layoutGame;
        private final TextView txtGameTitle;
        private final TextView txtBrandName;
        private final ImageView imgGamePick;
        public ListViewHolder(View itemView) {
            super(itemView);
            layoutGame = itemView.findViewById(R.id.layout_product);
            txtGameTitle = itemView.findViewById(R.id.tv_title);
            txtBrandName = itemView.findViewById(R.id.tv_brand);
            imgGamePick = itemView.findViewById(R.id.iv_item_image);
        }
    }
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_game_h, parent, false);
        return new ListViewHolder(v);
    }
    @Override
    public void onBindViewHolder(final ListViewHolder holder,  int position) {
        final Top_Games data= topGamesList.get(position);
        holder.txtBrandName.setText ( data.getCategory () );
        holder.txtGameTitle.setText(data.getPage_title());
        int height=450,width =390;
       // String adas="http://122.163.176.14/yallagamers_cscart/imageresize.php?width="+width+"&height="+height+"&imagepath="+data.getImage_path();
        Glide.with(holder.imgGamePick ).load ( data.getImage_path()).placeholder ( R.drawable.placeholder ).into ( holder.imgGamePick );
        holder.layoutGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("ProductId",data.getProduct_id());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return topGamesList.size();
    }
}
