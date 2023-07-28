package com.augurs.yallagamers.adapters;

//public class RecommendedProducts { }

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.augurs.yallagamers.api_module.Const;
import com.augurs.yallagamers.data_models.RecommendedForYou;
import com.augurs.yallagamers.UserInterface.ProductDetails.ProductDetailsActivity;
import com.augurs.yallagamers.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class RecommendedProducts extends RecyclerView.Adapter<RecommendedProducts.ListViewHolder> {

    private Context context;

    private ArrayList<RecommendedForYou> productList;





    public RecommendedProducts(Context context, ArrayList< RecommendedForYou > productList) {
        this.context = context;
        this.productList = productList;

    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout layout_product,RlWishlist;
        private TextView textView10,tv_item_brand,tv_item_name,tv_item_current_price,tv_item_brand1;
        private ImageView imgProduct;
        public ListViewHolder(View itemView) {
            super(itemView);
            RlWishlist= itemView.findViewById (R.id.RlWishlist);
            textView10= itemView.findViewById ( R.id.textView10 );
            layout_product = itemView.findViewById(R.id.layout_product);
            tv_item_brand = itemView.findViewById(R.id.tv_item_brand);
            tv_item_name = itemView.findViewById(R.id.tv_item_name);
            tv_item_current_price= itemView.findViewById ( R.id.tv_item_current_price );
            imgProduct = itemView.findViewById(R.id.iv_item_image);
            tv_item_brand1= itemView.findViewById ( R.id.tv_item_brand1 );
        }
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        ListViewHolder evh = new ListViewHolder(v);
        return evh;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder,  int position) {
        final int itmPos=position;
        final RecommendedForYou data= productList.get(itmPos);
        holder.tv_item_brand.setText ( data.getCategory () );
        holder.tv_item_name.setText ( data.getShortname () );
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int)(Resources.getSystem().getDisplayMetrics().widthPixels/3),
                LinearLayout.LayoutParams.WRAP_CONTENT); // or set height to any fixed value you want
        lp.setMargins (10,5,10,5);
        holder.layout_product.setLayoutParams(lp);
        int height= Resources.getSystem().getDisplayMetrics().widthPixels/3,width = Resources.getSystem().getDisplayMetrics().widthPixels/3;
        String adas=Const.BASE_URL+"/imageresize.php?crop=true&width="+width+"&height="+height+"&imagepath="+data.getImagePath ();
        Log.v("ImagePath",adas);
        Glide.with(this.context).load(adas).into(holder.imgProduct);
        holder.RlWishlist.setVisibility (View.GONE);
        holder.tv_item_brand1.setVisibility ( View.GONE );
        holder.layout_product.setOnClickListener( view -> {
            Intent intent=new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("ProductId",data.getProductId ());
            context.startActivity(intent);
        } );

        int listPrice = (int) data.getListPrice ();
        int BasePrice = (int) data.getBasePrice ();
        if( listPrice<=BasePrice)
        {
            holder.tv_item_current_price.setText(Const.CurrencyValue+BasePrice);
            holder.textView10.setVisibility ( View.GONE );
        }
        else {
            holder.textView10.setText(Const.CurrencyValue +listPrice);
            holder.tv_item_current_price.setText(Const.CurrencyValue+BasePrice);
        }

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


}

