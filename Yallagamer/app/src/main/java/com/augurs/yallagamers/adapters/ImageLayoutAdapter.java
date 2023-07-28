package com.augurs.yallagamers.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.augurs.yallagamers.R;
import com.bumptech.glide.Glide;
import java.util.List;

public class ImageLayoutAdapter extends RecyclerView.Adapter<ImageLayoutAdapter.ListViewHolder> {
    private final Context context;
    private List<String> topGamesList;
    private int Selected_Index=0;

    public ImageLayoutAdapter( Context context, List<String> topGamesList, int Selected_Index) {
        this.context = context;
        this.topGamesList = topGamesList;
        this.Selected_Index=Selected_Index;

    }


    public static class ListViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private CardView Cardv;
        public ListViewHolder( View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            Cardv= itemView.findViewById ( R.id.Cardv );
        }
    }
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_image, parent, false);
        ListViewHolder evh = new ListViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder,  int position) {
        Glide.with(this.context).load(topGamesList.get(position))
                .placeholder(R.drawable.placeholder)
                .into(holder.img);
        if(position==Selected_Index){
            holder.Cardv.setBackgroundResource(R.drawable.selected_item);
        }
        else {
            holder.Cardv.setBackgroundResource(R.drawable.selected_non_item);
        }
        holder.Cardv.setOnClickListener(view -> {
            ProductImageSliderAdapter.GetImageAdapter(position);
            // addressInterface.Id(position);
        });
    }

    @Override
    public int getItemCount() {
        return topGamesList.size();
    }


}

