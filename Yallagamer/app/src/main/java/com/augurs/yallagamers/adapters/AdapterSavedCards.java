package com.augurs.yallagamers.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.augurs.yallagamers.R;
import com.augurs.yallagamers.UserInterface.CategoryListItem.CategoryListItemActivity;
import com.augurs.yallagamers.UserInterface.categories.SpecialCategoryMainPageActivity;
import com.augurs.yallagamers.data_models.Box;
import com.augurs.yallagamers.data_models.CardData;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;


public class AdapterSavedCards extends RecyclerView.Adapter<AdapterSavedCards.ListViewHolder> {

    private Context context;
    private ArrayList <CardData> cardDataList;

    public AdapterSavedCards(Context context, ArrayList< CardData > cardDataList) {
        this.context = context;
        this.cardDataList = cardDataList;
    }

    public void addListArray(ArrayList<CardData> cardDataList){
        this.cardDataList = cardDataList;
        notifyDataSetChanged();
    }

    public void removeData(int pos){
        this.cardDataList.remove(pos);
        notifyItemRemoved(pos);
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
       public TextView card_name,card_number,card_expiry;
        public RelativeLayout viewBackground;
        public CardView viewForeground;
        public ListViewHolder( View itemView) {
            super(itemView);
            card_name = itemView.findViewById( R.id.card_name);
            card_number = itemView.findViewById( R.id.card_number);
            card_expiry = itemView.findViewById( R.id.card_expiry);

            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);

        }
    }
    @Override
    public ListViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_saved_card, parent, false);
        ListViewHolder evh = new ListViewHolder(v);
        return evh;
    }
    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder,  int position) {

        CardData cardData=cardDataList.get(position);

        holder.card_name.setText(cardData.getCard_name());
        holder.card_number.setText(cardData.getCard_number());
        holder.card_expiry.setText(cardData.getExp_mm()+"/"+cardData.getExp_yy());

    }
    @Override
    public int getItemCount() {
        return cardDataList.size();
    }
}
