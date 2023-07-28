package com.augurs.yallagamers.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.augurs.yallagamers.data_models.Blog;
import com.augurs.yallagamers.data_models.Gadgets_Accessories;
import com.augurs.yallagamers.data_models.Product;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class AdapterOurBlog extends RecyclerView.Adapter<AdapterOurBlog.ListViewHolder> {
    private Context context;
    private ArrayList<Blog> blogList;

    public void addListArray(ArrayList<Blog> itemList)
    {
        this.blogList = itemList;
        notifyDataSetChanged();
    }
    public AdapterOurBlog(Context context, ArrayList<Blog> blogList) {
        this.context = context;
        this.blogList = blogList;
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout layoutOurBlog;
        private TextView txtHeading,txtDesc;
        private ImageView imgOurBlog;

        public ListViewHolder(View itemView) {
            super(itemView);
            layoutOurBlog = itemView.findViewById(R.id.layout_our_blog);
            txtHeading = itemView.findViewById(R.id.tv_heading);
            txtDesc = itemView.findViewById(R.id.tv_desc);
            imgOurBlog = itemView.findViewById(R.id.iv_our_blog_banner);
        }
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_our_blog, parent, false);
        ListViewHolder evh = new ListViewHolder(v);
        return evh;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder,  int position) {
        final int itmPos=position;
        final Blog data= blogList.get(itmPos);
        holder.txtHeading.setText(data.getPage());
        holder.txtDesc.setText(Html.fromHtml(data.getDescription()));

     /*   Glide.with(this.context)
                .load(data.getImage_path())
                .placeholder(R.drawable.bg_blog)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                .into(holder.imgOurBlog);
*/

        int height=600,width =750;
        String blogImage= Const.BASE_URL+"/imageresize.php?width="+width+"&height="+height+"&imagepath="+data.getImage_path();
        Log.v("blogImage",blogImage);
        Glide.with(this.context).load(blogImage)
                .diskCacheStrategy( DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(5)))
                .placeholder(R.drawable.placeholder).into(holder.imgOurBlog);

        holder.layoutOurBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }


}
