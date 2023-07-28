package com.augurs.yallagamers.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.augurs.yallagamers.UserInterface.CategoryListItem.CategoryListItemActivity;
import com.augurs.yallagamers.R;
import com.augurs.yallagamers.api_module.Const;
import com.augurs.yallagamers.data_models.Category;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;

import java.util.ArrayList;

public class AdapterCategories extends RecyclerView.Adapter<AdapterCategories.ListViewHolder> {

    private Context context;
    private ArrayList<Category> categoryList;

    public AdapterCategories(Context context, ArrayList<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    public void addListArray(ArrayList<Category> catList){
        this.categoryList = catList;
        notifyDataSetChanged();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutCategory;
        private TextView txtCategory;
        ShapeableImageView imgCategory;

        public ListViewHolder(View itemView) {
            super(itemView);
            layoutCategory = itemView.findViewById(R.id.layout_category);
            txtCategory = itemView.findViewById(R.id.tv_cate_name);
            imgCategory = itemView.findViewById(R.id.iv_cate_image);

        }
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category, parent, false);
        ListViewHolder evh = new ListViewHolder(v);
        return evh;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder,  int position) {
        final Category data=categoryList.get(position);
        Log.v("data",new Gson().toJson ( data ) );
        holder.txtCategory.setText(data.getCategory());
        int height=150,width =150;
        String adas= Const.BASE_URL+"/imageresize.php?width="+width+"&height="+height+"&imagepath="+data.getImage();
        Log.v("adas",adas);
        Glide.with(this.context).load(adas).placeholder(R.drawable.placeholder)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(15))).into(holder.imgCategory);
        holder.layoutCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, CategoryListItemActivity.class);
                intent.putExtra("category_id",data.getCategoryId ());
                intent.putExtra("category",data.getCategory());
                intent.putExtra("ProductId",data.getProduct_count ()+"");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }


}
