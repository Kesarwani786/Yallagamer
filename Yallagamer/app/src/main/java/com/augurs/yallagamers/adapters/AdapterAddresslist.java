package com.augurs.yallagamers.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.augurs.yallagamers.R;
import com.augurs.yallagamers.data_models.AddressInterface;
import com.augurs.yallagamers.data_models.AddressLIstingModel;
import com.augurs.yallagamers.UserInterface.address.AddNewAddressActivity;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Collections;

public class AdapterAddresslist extends RecyclerView.Adapter<AdapterAddresslist.ListViewHolder> {
    private Context context;
    private ArrayList < AddressLIstingModel.Datum > addressLIstingModel;
    int selectedPosition=-1;
    int Status=0;
    AddressInterface addressInterface;
    int AddressStatus=0;
    public AdapterAddresslist(Context context, ArrayList< AddressLIstingModel.Datum  > addressLIstingModel,AddressInterface addressInterface,int AddressStatus ) {
        this.context = context;
        this.addressLIstingModel = addressLIstingModel;
        this.addressInterface=addressInterface;
        this.AddressStatus=AddressStatus;
    }
    public static class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name,tv_address,tv_mobile_no,tv_address_type,tv_name1,TvRemove,TvEditAddress;
        private RelativeLayout RlMain;
        private CheckBox radio;
        public ListViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_mobile_no = itemView.findViewById(R.id.tv_mobile_no);
            tv_address_type = itemView.findViewById(R.id.tv_address_type);
            tv_name1 = itemView.findViewById(R.id.tv_name1);
            TvRemove=itemView.findViewById ( R.id.TvRemove );
            RlMain = itemView.findViewById( R.id.RlMain );
            radio= itemView.findViewById( R.id.radio );
            TvEditAddress= itemView.findViewById ( R.id.TvEditAddress );
        }
    }
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_address_list_item, parent, false);
        ListViewHolder evh = new ListViewHolder(v);
        return evh;
    }
    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder,  int position) {
        if(AddressStatus==0){
            holder.radio.setVisibility(View.VISIBLE);
        }
        else
            holder.radio.setVisibility(View.GONE);
        if(addressLIstingModel.get( position ).getIsDefault()==1){
            holder.tv_name1.setVisibility( View.VISIBLE );
            holder.tv_name1.setText( "  Default Address" );
            selectedPosition=position;
            addressInterface.Id(addressLIstingModel.get( position ).getProfileId ()  );
            holder.TvRemove.setVisibility(View.GONE);
        }
        else if(Status==0){
            Status=1;
            holder.tv_name1.setVisibility( View.VISIBLE );
            holder.tv_name1.setText( "  Other Address" );
            holder.TvRemove.setVisibility(View.VISIBLE);
        }
        else {
            holder.tv_name1.setVisibility (View.GONE);
            holder.TvRemove.setVisibility(View.VISIBLE);
        }
          holder.TvRemove.setOnClickListener ( new View.OnClickListener ( ) {
              @Override
              public void onClick ( View v ) {
                  addressInterface.removeAddressId(addressLIstingModel.get( position ).getProfileId ()  );
              }
          } );
          holder.radio.setChecked(selectedPosition == position);
          holder.TvEditAddress.setOnClickListener ( new View.OnClickListener ( ) {
              @Override
              public void onClick ( View v ) {
                  Intent i = new Intent ( context, AddNewAddressActivity.class );
                  i.putExtra ( "AddAddressModel",new Gson().toJson(addressLIstingModel.get(position)));
                  i.putExtra ( "Status",1 );
                  context.startActivity ( i );
              }
          } );
          holder.radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = position;
                Status=0;
                addressInterface.Id(addressLIstingModel.get( position ).getProfileId()  );
                for(int i=0;i<addressLIstingModel.size();i++){
                    if(i==position){
                        addressLIstingModel.get( i ).setIsDefault( 1 );
                        Collections.swap(addressLIstingModel, 0, position);
                    }
                    else
                        addressLIstingModel.get( i ).setIsDefault( 0 );
                }
                notifyDataSetChanged( );
            }
        });
          holder.tv_name.setText( addressLIstingModel.get(position).getsFirstname()+" "+ addressLIstingModel.get(position).getsLastname());
          holder.tv_address.setText( addressLIstingModel.get(position).getsAddress()+" "+ addressLIstingModel.get(position).getsAddress2()+" "+ addressLIstingModel.get(position).getsCity ()+" "+ addressLIstingModel.get(position).getsState ()+" "+ addressLIstingModel.get(position).getsCountry ()+" -"+addressLIstingModel.get ( position ).getsZipcode ());
          holder.tv_address_type.setText( addressLIstingModel.get(position).getProfileName ());
          holder.tv_mobile_no.setText(addressLIstingModel.get(position).getsPhone());
    }
    @Override
    public int getItemCount() {
        return addressLIstingModel.size();
    }

}


