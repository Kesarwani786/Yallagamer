package com.augurs.yallagamers.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.augurs.yallagamers.R;
import com.augurs.yallagamers.data_models.SliderItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class AdapterBannerSlider extends SliderViewAdapter<AdapterBannerSlider.SliderAdapterVH>
{
    private final Context context;
    private ArrayList<String> mSliderItems = new ArrayList<>();

    public AdapterBannerSlider(Context context)
    {
        this.context = context;
    }

    public void renewItems(ArrayList<String> sliderItems)
    {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }
    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent)
    {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH (inflate);
    }
    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position)
    {
        Glide.with ( viewHolder.itemView ).load ( mSliderItems.get ( position ) ).apply ( RequestOptions.bitmapTransform ( new RoundedCorners ( 5 ) ) ).placeholder ( R.drawable.placeholder ).into ( viewHolder.imageViewBackground );
    }

    @Override
    public int getCount()
    {
        return mSliderItems.size();
    }

   public static class SliderAdapterVH extends SliderViewAdapter.ViewHolder
    {
        View itemView;
        ImageView imageViewBackground;
        public SliderAdapterVH(View itemView)
        {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.imgSlider);
            this.itemView = itemView;
        }
    }
}