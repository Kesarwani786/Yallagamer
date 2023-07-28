package com.augurs.yallagamers.UserInterface.address;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.augurs.yallagamers.R;
import com.augurs.yallagamers.data_models.ShippingScreenModel;
import com.bumptech.glide.Glide;
import java.util.List;

public class DilieveryEstimatesAdapter extends RecyclerView.Adapter< DilieveryEstimatesAdapter.ListViewHolder> {
    private Context context;
    private List < ShippingScreenModel.Data1.CartProduct > addressLIstingModel;
    public DilieveryEstimatesAdapter(Context context, List < ShippingScreenModel.Data1.CartProduct > addressLIstingModel ) {
        this.context = context;
        this.addressLIstingModel = addressLIstingModel;
    }
    public static class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name,TvCardNo;
        private ImageView ImgBank;
        public ListViewHolder( View itemView) {
            super(itemView);
            tv_name = itemView.findViewById( R.id.TvCardNo1);
            ImgBank=itemView.findViewById ( R.id.ImgBank );
        }
    }
    @Override
    public ListViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_delivery_options, parent, false);
        ListViewHolder evh = new ListViewHolder (v);
        return evh;
    }
    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        if( addressLIstingModel.get(position).getDelieverydate ()!=null)
        holder.tv_name.setText("Estimated delivery by "+ addressLIstingModel.get(position).getDelieverydate ()+"");
        Glide.with(context ).load ( addressLIstingModel.get(position).getMainImage ()) .placeholder ( R.drawable.placeholder ).into ( holder.ImgBank );

    }
    @Override
    public int getItemCount() {
        return addressLIstingModel.size();
    }
}




