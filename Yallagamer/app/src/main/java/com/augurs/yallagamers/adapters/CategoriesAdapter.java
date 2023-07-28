package com.augurs.yallagamers.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.augurs.yallagamers.R;
import com.augurs.yallagamers.api_module.Const;
import com.augurs.yallagamers.data_models.Category;
import com.augurs.yallagamers.UserInterface.CategoryListItem.CategoryListItemActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ListViewHolder> {
    private Context context;
    private ArrayList < Category > categoryList;
    public CategoriesAdapter(Context context, ArrayList< Category > categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }
    public void addListArray(ArrayList<Category> catList){
        this.categoryList = catList;
        notifyDataSetChanged();
    }
    public static class ListViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageButton4;
        private TextView tv_heading,TvText,Tvdesc;
        private RelativeLayout RlMain;
        public ListViewHolder( View itemView) {
            super(itemView);
            imageButton4 = itemView.findViewById( R.id.img);
            tv_heading= itemView.findViewById (  R.id.tv_heading );
            RlMain=itemView.findViewById ( R.id.RlMain );
            TvText= itemView.findViewById ( R.id.TvText );
            Tvdesc= itemView.findViewById ( R.id.Tvdesc );

        }
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_categorie, parent, false);
        ListViewHolder evh = new ListViewHolder(v);
        return evh;
    }

    @SuppressLint ("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder,  int position) {

        int height=200,width =200;
        String image= Const.BASE_URL+"/imageresize.php?width="+width+"&height="+height+"&imagepath="+categoryList.get(position).getImage();
        Glide.with(this.context).load(image).placeholder(R.drawable.placeholder).into(holder.imageButton4);
        holder.tv_heading.setText (categoryList.get(position).getCategory ()  );
        holder.TvText.setText ( Html.fromHtml("<b>No of  Product : </b>"+ categoryList.get ( position ).getProduct_count ()+"") );
        if(categoryList.get ( position ).getDescription ()!=null&&categoryList.get ( position ).getDescription ().length ()!=0)
            holder.Tvdesc.setText ( Html.fromHtml("<b>Description : </b>"+ categoryList.get ( position ).getDescription ()+"") );
        else
            holder.Tvdesc.setVisibility (View.GONE);
        holder.RlMain.setOnClickListener ( v -> {
            Intent intent=new Intent(context, CategoryListItemActivity.class);
            intent.putExtra("category_id",categoryList.get(position).getCategoryId ());
            intent.putExtra("category",categoryList.get(position).getCategory());
            context.startActivity(intent);
        } );
    }
    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
