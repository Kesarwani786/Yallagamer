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
import com.augurs.yallagamers.data_models.ProductFeatures;
import com.augurs.yallagamers.data_models.Review;
import com.augurs.yallagamers.data_models.SimilarProducts;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class ProductDetailsFeatureAdapter extends RecyclerView.Adapter<ProductDetailsFeatureAdapter.ListViewHolder> {

    private Context context;
    private ArrayList < ProductFeatures > categoryList;

    public ProductDetailsFeatureAdapter(Context context, ArrayList< ProductFeatures > categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }
    public static class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView Tvasd;
        public ListViewHolder( View itemView) {
            super(itemView);
            Tvasd=itemView.findViewById ( R.id.Tvasd );
        }
    }

    @Override
    public ListViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feature_layout , parent, false);
        ListViewHolder evh = new ListViewHolder(v);
        return evh;
    }
    @Override
    public void onBindViewHolder( final ListViewHolder holder,  int position) {
        final ProductFeatures data=categoryList.get(position);
        holder.Tvasd.setText(data.getDescription ()+" :  "+data.getVariant ());

    }
    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}



