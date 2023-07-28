package com.augurs.yallagamers.UserInterface.payment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.augurs.yallagamers.R;
import com.augurs.yallagamers.data_models.AddressInterface;
import com.augurs.yallagamers.data_models.PaymentScreenModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class PaymentBankotherAdapter extends RecyclerView.Adapter<PaymentBankotherAdapter.ListViewHolder> {
    private Context context;
    private  List < PaymentScreenModel.PaymentMethod > addressLIstingModel;
    private  AddressInterface addressInterface;
    private int   Postition=-1;
    public PaymentBankotherAdapter(Context context, List<PaymentScreenModel.PaymentMethod> addressLIstingModel, AddressInterface addressInterface) {
        this.context = context;
        this.addressLIstingModel = addressLIstingModel;
        this.addressInterface=addressInterface;
    }
    public static class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView TvBankName;
        private ImageView ImgBank1,ImgBank;
        private RelativeLayout RlMain;
        public ListViewHolder( View itemView) {
            super(itemView);
            TvBankName = itemView.findViewById( R.id.TvBankName);
            ImgBank1= itemView.findViewById( R.id.ImgBank1);
            RlMain= itemView.findViewById(R.id.RlMain);
            ImgBank= itemView.findViewById(R.id.ImgBank);

        }
    }

    public static void main ( String[] args ) {
        System.out.println ("hello andeep" );
    }
    @Override
    public ListViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_payment_other_option, parent, false);
        ListViewHolder evh = new ListViewHolder (v);
        return evh;
    }
    @SuppressLint ("RecyclerView")
    @Override
    public void onBindViewHolder( ListViewHolder holder, int position) {
        if(position==Postition)
            holder.ImgBank.setImageResource(R.drawable.ic_checked);
        else
            holder.ImgBank.setImageResource(R.drawable.ic_checked_light);
        holder.TvBankName.setText( addressLIstingModel.get(position).getPayment ());
        Glide.with(context  ).load (addressLIstingModel.get(position).getMainImage () ) .placeholder ( R.drawable.placeholder ).into ( holder.ImgBank1 );
        holder.RlMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Postition=position;
                notifyDataSetChanged();
                addressInterface.Id(addressLIstingModel.get(position).getPaymentId());
            }
        });
        //holder.ImgBank1.setImageResource ( addressLIstingModel.get(position).getCardNo ());
    }
    @Override
    public int getItemCount() {
        return addressLIstingModel.size();
    }
}



