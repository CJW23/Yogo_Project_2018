package com.yogo.pjh.weather_projcect_v10;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class ClothesTopAdapter extends RecyclerView.Adapter<ClothesTopAdapter.ViewHolder> {

    Context context;

    ArrayList<ClothesItem> items=new ArrayList<ClothesItem>();

    OnItemClickListener listener;

    public static interface  OnItemClickListener{
        public void onItemClick(ViewHolder holder, View view, int position);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.clothes_item,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClothesItem item = items.get(position);
        Glide.with(context)
                .load(item.getUri_image())
                .apply(new RequestOptions().override(300,300))
                .into(holder.centerImage);

        holder.setOnItemClickListener(listener);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.listener=listener;
    }

    @Override
    public int getItemCount() {
        return (null != items ? items.size() : 0 );
    }

    public ClothesTopAdapter(Context context)
    {
        this.context=context;
    }



    public void addItem(ClothesItem item)
    {
        items.add(item);
    }

    public void addItems(ArrayList<ClothesItem> items)
    {
        this.items=items;
    }

    public ClothesItem getItem(int position)
    {
        return items.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView centerImage;

        OnItemClickListener listener;

        public ViewHolder(View itemView)
        {
            super(itemView);
            centerImage=itemView.findViewById(R.id.centerImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(listener!=null)
                    {
                        listener.onItemClick(ViewHolder.this, v, position);
                    }
                }
            });

        }

        public void setOnItemClickListener(OnItemClickListener listener)
        {
            this.listener=listener;
        }

        public void setItem(ClothesItem item)
        {


        }
    }
    public void clear()
    {
        items.clear();
    }
}
