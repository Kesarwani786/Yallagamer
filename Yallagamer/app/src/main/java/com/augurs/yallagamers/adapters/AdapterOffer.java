package com.augurs.yallagamers.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.augurs.yallagamers.R;
import com.augurs.yallagamers.api_module.Const;
import com.augurs.yallagamers.data_models.OfferModel;
import com.augurs.yallagamers.UserInterface.CategoryListItem.CategoryListItemActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class AdapterOffer extends RecyclerView.Adapter<AdapterOffer.ListViewHolder> {
    private Context context;
    private ArrayList < OfferModel > topGamesList;
    private int i=0;
    public AdapterOffer(Context context, ArrayList<OfferModel> topGamesList) {
        this.context = context;
        this.topGamesList = topGamesList;
    }
    public void addListArray(ArrayList<OfferModel> itemList)
    {
        int i=0;
        for (OfferModel offerModel:itemList){
            if(i==0){
                offerModel.setColorCode ( "#FFF4E6" );
                i++;
            }
            else if(i==1){
                offerModel.setColorCode ( "#F2F5EA" );
                i++;
            }
            else if(i==2){
                offerModel.setColorCode ( "#F6EBF3" );
                i=0;
            }
        }
        this.topGamesList = itemList;
        notifyDataSetChanged();
    }
    public static class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView txtGameTitle;
        private ImageView imgGamePick;
        private RelativeLayout RlMain;

        public ListViewHolder( View itemView) {
            super(itemView);
            txtGameTitle = itemView.findViewById(R.id.textView);
            imgGamePick = itemView.findViewById(R.id.imageView);
            RlMain= itemView.findViewById ( R.id.RlMain );
        }
    }
    @Override
    public ListViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_offer, parent, false);
        ListViewHolder evh = new ListViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder,  int position) {
        final OfferModel data= topGamesList.get(position);
        holder.txtGameTitle.setText(data.getName ());
        holder.RlMain.setBackgroundColor ( Color.parseColor ( data.getColorCode ()) );
        int height=600,width =600;
        String adas= Const.BASE_URL+"imageresize.php?width="+width+"&height="+height+"&imagepath="+data.getImage ();
        Log.v("adas123",adas);
        Glide.with(this.context).load(adas)
                .diskCacheStrategy( DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .placeholder(R.drawable.placeholder)
                .into(holder.imgGamePick);
        holder.RlMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, CategoryListItemActivity.class);
                intent.putExtra("category_id",data.getCategory_id ());
                intent.putExtra("category",data.getName ());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return topGamesList.size();
    }
}
