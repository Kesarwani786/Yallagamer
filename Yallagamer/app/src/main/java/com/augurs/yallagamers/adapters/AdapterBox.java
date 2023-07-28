package com.augurs.yallagamers.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.augurs.yallagamers.R;
import com.augurs.yallagamers.api_module.Const;
import com.augurs.yallagamers.data_models.Box;
import com.augurs.yallagamers.UserInterface.CategoryListItem.CategoryListItemActivity;
import com.augurs.yallagamers.UserInterface.categories.SpecialCategoryMainPageActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;

import java.util.ArrayList;


public class AdapterBox extends RecyclerView.Adapter<AdapterBox.ListViewHolder> {
    private Context context;
    private ArrayList < Box > categoryList;

    public AdapterBox(Context context, ArrayList< Box > categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    public void addListArray(ArrayList<Box> catList){
        this.categoryList = catList;
        notifyDataSetChanged();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
       private ShapeableImageView imageButton4;

        public ListViewHolder( View itemView) {
            super(itemView);
            imageButton4 = itemView.findViewById( R.id.imageButton4);

        }
    }
    @Override
    public ListViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category_box, parent, false);
        ListViewHolder evh = new ListViewHolder(v);
        return evh;
    }
    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder,  int position) {
        int height=1700,width = Resources.getSystem().getDisplayMetrics().widthPixels;
        String adas= Const.BASE_URL+"/imageresize.php?width="+width+"&height="+height+"&imagepath="+categoryList.get(position).getImage();
        Glide.with ( this.context ).load ( adas ).apply ( RequestOptions.bitmapTransform ( new RoundedCorners ( 5 ) ) ).placeholder ( R.drawable.placeholder ).into ( holder.imageButton4 );
        float radius = context.getResources().getDimension(R.dimen.dp_5);
        holder.imageButton4.setShapeAppearanceModel(holder.imageButton4.getShapeAppearanceModel()
                .toBuilder()
                .setAllCorners(CornerFamily.ROUNDED, radius)
                .build());
        holder.imageButton4.setOnClickListener ( v -> {
            if(position==3) {
                Intent intent=new Intent(context, CategoryListItemActivity.class);
                intent.putExtra("category_id",categoryList.get(position).getBlockId ()+"");
                intent.putExtra("category","Customised Stuff");
                context.startActivity(intent);
            }
            else {
                Intent i = new Intent ( context , SpecialCategoryMainPageActivity.class );
                i.putExtra ( "Name" , categoryList.get ( position ).getName ( ) );
                context.startActivity ( i );
            }

        } );
    }
    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
