package com.augurs.yallagamers.UserInterface.payment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.augurs.yallagamers.R;
import com.augurs.yallagamers.data_models.PaymentScreenModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PaymentBankAdapter extends RecyclerView.Adapter<PaymentBankAdapter.ListViewHolder> {
    private Context context;
    private List <PaymentScreenModel.SaveCard> addressLIstingModel;
    public PaymentBankAdapter(Context context, List< PaymentScreenModel.SaveCard  > addressLIstingModel ) {
        this.context = context;
        this.addressLIstingModel = addressLIstingModel;
    }
    public static class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name,TvCardNo;
        private ImageView ImgBank;
        public ListViewHolder( View itemView) {
            super(itemView);
            tv_name = itemView.findViewById( R.id.TvBankName);
            TvCardNo= itemView.findViewById( R.id.TvCardNo);
            ImgBank=itemView.findViewById ( R.id.ImgBank );
        }
    }
    @NotNull
    @Override
    public ListViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_payment_option, parent, false);
        ListViewHolder evh = new ListViewHolder (v);
        return evh;
    }
    @SuppressLint ("RecyclerView")
    @Override
    public void onBindViewHolder( ListViewHolder holder, int position) {
        holder.tv_name.setText( addressLIstingModel.get(position).getCardName ());
        holder.TvCardNo.setText( addressLIstingModel.get(position).getCardNumber ());
        holder.ImgBank.setTag ("bank Name");
        //holder.ImgBank.setImageResource (addressLIstingModel.get(position).getSymbol ());
    }
    @Override
    public int getItemCount() {
        return addressLIstingModel.size();
    }
}



