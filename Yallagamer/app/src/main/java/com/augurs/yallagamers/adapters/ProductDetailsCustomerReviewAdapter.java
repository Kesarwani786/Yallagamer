package com.augurs.yallagamers.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.augurs.yallagamers.R;
import com.augurs.yallagamers.data_models.Review;
import com.augurs.yallagamers.data_models.SimilarProducts;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class ProductDetailsCustomerReviewAdapter extends RecyclerView.Adapter<ProductDetailsCustomerReviewAdapter.ListViewHolder> {

    private Context context;
    private ArrayList < Review > categoryList;

    public ProductDetailsCustomerReviewAdapter(Context context, ArrayList< Review > categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }
    public static class ListViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView pd_tv_comment_by,TvReview,pd_tv_comment_date,textView34,pd_tv_comment_title;
        private RatingBar ratingBar_comment;
        public ListViewHolder( View itemView) {
            super(itemView);
            img = itemView.findViewById( R.id.img);
            textView34=itemView.findViewById ( R.id.textView34 );
            TvReview=itemView.findViewById ( R.id.TvReview );
            pd_tv_comment_date= itemView.findViewById ( R.id.pd_tv_comment_date );
            pd_tv_comment_by= itemView.findViewById ( R.id.pd_tv_comment_by );
            ratingBar_comment= itemView.findViewById ( R.id.ratingBar_comment );
            pd_tv_comment_title= itemView.findViewById ( R.id.pd_tv_comment_title );
        }
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_product_detail_view_a5_1 , parent, false);
        ListViewHolder evh = new ListViewHolder(v);
        return evh;
    }
    @Override
    public void onBindViewHolder( final ListViewHolder holder,  int position) {
        final Review data=categoryList.get(position);
        /*Glide.with(context).load(data.getMainImage ()).placeholder(R.drawable.placeholder).into(holder.img);
        holder.TvCategoryName.setText ( data.getMainCategory () );
        holder.TvShortName.setText ( data.getProduct ()+"" );
        holder.TvAmount.setText ( data.getListPrice ()+"" );*/
        holder.ratingBar_comment.setRating ( Float.parseFloat ( data.getRatingValue () ) );
        holder.pd_tv_comment_by.setText(data.getName ());
        holder.pd_tv_comment_title.setText ( data.getTitle () );
        holder.TvReview.setText ( data.getMessage () );
        //holder.pd_tv_comment_date.setText ( data.get );
    }
    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}

