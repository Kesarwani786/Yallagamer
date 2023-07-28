package com.augurs.yallagamers.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.augurs.yallagamers.R;
import com.augurs.yallagamers.data_models.CategorySingle;

import java.util.ArrayList;


public class AdapterCategorySingle extends RecyclerView.Adapter<AdapterCategorySingle.ListViewHolder> {

    private Context context;

    private ArrayList<CategorySingle> productList;


    public AdapterCategorySingle(Context context, ArrayList<CategorySingle> productList) {
        this.context = context;
        this.productList = productList;


    }


    public static class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name;
        private ImageView img;

        public ListViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            img = itemView.findViewById(R.id.img);
        }
    }


    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category_items, parent, false);
        ListViewHolder evh = new ListViewHolder(v);
        return evh;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder,  int position) {
          holder.img.setImageResource(productList.get(position).getImage());
          holder.tv_name.setText(productList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

}


