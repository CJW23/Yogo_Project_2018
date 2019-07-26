package com.yogo.pjh.weather_projcect_v10;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder>{

    Context context;
    ArrayList<Galleryitem> items = new ArrayList<Galleryitem>();
    GalleryAdapter.OnItemClickListener listener;
    public static interface  OnItemClickListener{
        public void onItemClick(GalleryAdapter.ViewHolder holder, View view, int position);
    }
    public GalleryAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.gallery_item, parent, false);

        return new ViewHolder(itemView);
    }

    public void setOnItemClickListener(GalleryAdapter.OnItemClickListener listener)
    {
        this.listener=listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Galleryitem item = items.get(position);
        holder.setItem(item);
        Glide.with(context)
                .load(item.getImg())
                .apply(new RequestOptions().override(300,300))
                .into(holder.img);

    }
    public Galleryitem getItem(int position){
        return items.get(position);
    }
    public void addItem(Galleryitem item)
    {
        items.add(item);
    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        GalleryAdapter.OnItemClickListener listener;
        ImageView img;
        TextView title;
        TextView date;
        TextView user;

        public ViewHolder(View itemView){
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.imageView2);
            title = (TextView) itemView.findViewById(R.id.textView);
            date = (TextView) itemView.findViewById(R.id.textView3);
            user = (TextView) itemView.findViewById(R.id.textView2);
        }
        public void setItem(Galleryitem item){
            title.setText(item.getTitle());
            date.setText(item.getDate());
            user.setText(item.getUid());

        }
        public void setOnItemClickListener(GalleryAdapter.OnItemClickListener listener)
        {
            this.listener=listener;
        }


    }
}
