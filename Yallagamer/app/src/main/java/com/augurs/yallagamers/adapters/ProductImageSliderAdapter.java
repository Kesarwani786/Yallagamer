package com.augurs.yallagamers.adapters;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.augurs.yallagamers.R;
import com.augurs.yallagamers.api_module.Const;
import com.augurs.yallagamers.utills.TouchImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class ProductImageSliderAdapter extends SliderViewAdapter < ProductImageSliderAdapter.SliderAdapterVH >  {
    private static Context context;
    private static ArrayList < String > mSliderItems = new ArrayList <> ( );
    private boolean doNotifyDataSetChangedOnce = false;

    public ProductImageSliderAdapter ( Context context ) {
        this.context = context;
    }
    public void renewItems ( ArrayList < String > sliderItems ) {
        mSliderItems = sliderItems;
        notifyDataSetChanged ();
    }
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder ( ViewGroup parent ) {
        View inflate = LayoutInflater.from ( parent.getContext ( ) ).inflate ( R.layout.image_slider_layout , null );
        return new SliderAdapterVH ( inflate );
    }

    @Override
    public void onBindViewHolder ( SliderAdapterVH viewHolder , final int position ) {
        int height= Resources.getSystem().getDisplayMetrics().widthPixels/2,width = Resources.getSystem().getDisplayMetrics().widthPixels;

        //String imageData= Const.BASE_URL+"/imageresize.php?width="+width+"&height="+height+"&imagepath="+;
        //Log.v("imageData",imageData);
        Glide.with(viewHolder.itemView ).load ( mSliderItems.get ( position )) .apply ( RequestOptions.bitmapTransform ( new RoundedCorners ( 5 ) ) ).placeholder ( R.drawable.placeholder ).into ( viewHolder.imageViewBackground );
        viewHolder.imageViewBackground.setOnClickListener ( new View.OnClickListener ( ) {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick ( View v ) {
               CalltheDialogue(0);
            }
        } );
    }

    @SuppressLint("WrongConstant")
    private static void CalltheDialogue(int i) {
        Dialog dialog1 = new Dialog ( context );
        dialog1.setContentView ( R.layout.zoom_in_zoom_out );
        Window window = dialog1.getWindow ( );
        WindowManager.LayoutParams wlp = window.getAttributes ( );
        window.setLayout ( ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT );
        dialog1.getWindow ( ).addFlags ( WindowManager.LayoutParams.FLAG_DIM_BEHIND );
        dialog1.getWindow ( ).setBackgroundDrawable ( new ColorDrawable( android.graphics.Color.TRANSPARENT ) );
        window.setAttributes ( wlp );
        dialog1.show ( );
        TouchImageView touchImageView = dialog1.findViewById ( R.id.touchImageView );
        touchImageView.setMaxZoom(4f);
        Glide.with(context ).load ( mSliderItems.get(i))  .skipMemoryCache(true).apply ( RequestOptions.bitmapTransform ( new RoundedCorners ( 5 ) ) ).placeholder ( R.drawable.placeholder ).into ( touchImageView);
        ImageView Cross= dialog1.findViewById(R.id.Cross);
        RecyclerView recycleview= dialog1.findViewById(R.id.recycleview);
        recycleview.setLayoutManager(new LinearLayoutManager(context, LinearLayout.HORIZONTAL,false));
        recycleview.setAdapter(new ImageLayoutAdapter(context,mSliderItems,i));
        Cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
    }

    @Override
    public int getCount ( ) {
        if (doNotifyDataSetChangedOnce) {
            doNotifyDataSetChangedOnce = false;
            notifyDataSetChanged();
        }


        return mSliderItems.size ( );
    }

   public static void GetImageAdapter(int position){
       CalltheDialogue(position);
   }
    public class SliderAdapterVH extends SliderViewAdapter.ViewHolder {
        View itemView;
        ImageView imageViewBackground;
        public SliderAdapterVH ( View itemView ) {
            super ( itemView );
            imageViewBackground = itemView.findViewById ( R.id.iv_auto_image_slider );
            this.itemView = itemView;
        }
    }
}
