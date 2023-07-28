package com.augurs.yallagamers.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.augurs.yallagamers.R;
import com.augurs.yallagamers.data_models.VariantChangeInterface;
import com.augurs.yallagamers.data_models.Variation;
import java.util.ArrayList;

public class VariantsAdapter extends RecyclerView.Adapter<VariantsAdapter.ListViewHolder> {

    private Context context;
    private Integer id;
    private ArrayList < Variation > categoryList;
    private  VariantChangeInterface variantChangeInterface;

    public VariantsAdapter( Context context, ArrayList< Variation > categoryList, VariantChangeInterface variantChangeInterface, Integer id) {
        this.context = context;
        this.categoryList = categoryList;
        this.variantChangeInterface=variantChangeInterface;
        this.id=id;
    }
    public static class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView textView19;
        private LinearLayout LInear;
        public ListViewHolder( View itemView) {
            super(itemView);
            textView19= itemView.findViewById ( R.id.textView19 );
            LInear= itemView.findViewById ( R.id.LInear );
        }
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_variants , parent, false);
        ListViewHolder evh = new ListViewHolder(v);
        return evh;
    }
    @Override
    public void onBindViewHolder( final ListViewHolder holder,  int position) {
        final Variation data=categoryList.get(position);
        if( id.equals ( data.getProductId ( ) ) ){
            holder.textView19.setTextColor ( Color.WHITE );
            holder.LInear.setBackgroundResource (   R.drawable.dark_button_background ) ;
        }
        holder.textView19.setText(data.getVariant ());
        holder.LInear.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                //if( id.equals ( data.getProductId ( ) ) ){
                    variantChangeInterface.ProductId ( Integer.valueOf (  data.getProductId () ));
                //}
            }
        } );
    }
    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}



