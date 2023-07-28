package com.augurs.yallagamers.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.augurs.yallagamers.R;
import com.augurs.yallagamers.api_module.Const;
import com.augurs.yallagamers.data_models.SimilarProducts;
import com.augurs.yallagamers.UserInterface.ProductDetails.ProductDetailsActivity;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecommendedItemAdapter extends RecyclerView.Adapter<RecommendedItemAdapter.ListViewHolder> {

    private Context context;
    private ArrayList < SimilarProducts > categoryList;

    public RecommendedItemAdapter(Context context, ArrayList< SimilarProducts > categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView txtProduct,txtBrand,txtProductOldPrice,txtProductCurrentPrice;
        private CheckBox checkbox;
        private RelativeLayout ParentLayout;
        private RelativeLayout RlWishlist;
        public ListViewHolder( View itemView) {
            super(itemView);
            RlWishlist= itemView.findViewById (R.id.RlWishlist);
            img = itemView.findViewById( R.id.iv_item_image);
            txtProduct = itemView.findViewById(R.id.tv_item_name);
            ParentLayout= itemView.findViewById ( R.id.layout_product );
            checkbox= itemView.findViewById ( R.id.checkbox );
            txtBrand = itemView.findViewById(R.id.tv_item_brand);
            txtProductOldPrice = itemView.findViewById(R.id.textView10);
            txtProductCurrentPrice = itemView.findViewById(R.id.tv_item_current_price);
        }
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new ListViewHolder(v);
    }
    @Override
    public void onBindViewHolder( final ListViewHolder holder,  int position) {
        final SimilarProducts data=categoryList.get(position);
        Log.v("data",data.getMainImage ());
        holder.RlWishlist.setVisibility (View.GONE);
        int height= Resources.getSystem().getDisplayMetrics().widthPixels/3,width = Resources.getSystem().getDisplayMetrics().widthPixels/3;
        String adas=Const.BASE_URL+"/imageresize.php?width="+width+"&height="+height+"&imagepath="+data.getMainImage ();
        Log.v("ImagePath",adas);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int)(Resources.getSystem().getDisplayMetrics().widthPixels/3),
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins (10,5,10,5);
        holder.ParentLayout.setLayoutParams(lp);
        Picasso.with( context )
                .load( adas )
                .error( R.drawable.placeholder )
                .placeholder( R.drawable.image_loader )
                .into( holder.img );
        int listPrice = (int) data.getListPrice ();
        int BasePrice = (int) data.getBasePrice ();
        if( listPrice<=BasePrice)
        {
            holder.txtProductCurrentPrice.setText(Const.CurrencyValue+BasePrice);
            holder.txtProductOldPrice.setVisibility ( View.GONE );
        }
        else {
            holder.txtProductOldPrice.setText(Const.CurrencyValue +listPrice);
            holder.txtProductCurrentPrice.setText(Const.CurrencyValue+BasePrice);
        }
        holder.txtProduct.setText(data.getProduct ());
        holder.txtBrand.setText ( data.getMainCategory ().toString () );
        holder.ParentLayout.setOnClickListener (v -> {
            Intent intent=new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("ProductId",data.getProductId ());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
            context.startActivity(intent);
        });
    }
    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
