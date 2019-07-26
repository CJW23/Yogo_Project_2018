package com.yogo.pjh.weather_projcect_v10;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class ClothesAdapter extends RecyclerView.Adapter<ClothesAdapter.ViewHolder> {

    Context context;

    ArrayList<ClothesItem> items=new ArrayList<ClothesItem>();

    OnItemClickListener listener;

    public static interface  OnItemClickListener{
        public void onItemClick(ViewHolder holder, View view, int position);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.clothes_item,parent,false);


        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ClothesItem item = items.get(position);

       //글리드로 이미지셋
       //holder.setItem(item);
        Glide.with(context)
                .load(item.getUri_image())
                .apply(new RequestOptions().override(300,300))
                .into(holder.centerImage);


        holder.setOnItemClickListener(listener);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        Log.d("linemaker","pleaseCallMe2");
        this.listener=listener;
    }

    @Override
    public int getItemCount() {
        Log.d("ClAgetItemCnt",Integer.toString(items.size()));

        return (null != items ? items.size() : 0 );
    }

    public ClothesAdapter(Context context)
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

    public void clear()
    {
        items.clear();
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

                    Log.d("linemaker",Integer.toString(position));
                    if(listener!=null)
                    {
                        listener.onItemClick(ViewHolder.this, v, position);
                        Log.d("linemaker","pree");
                    }
                }
            });
        }

        public void setOnItemClickListener(OnItemClickListener listener)
        {
            Log.d("linemaker","pleaseCallMe");

            this.listener=listener;
        }

        public void setItem(ClothesItem item)
        {
            Log.d("dddd",item.getUri_image().toString());
            //글리드 사용, 또한 사용시 오류발생
          //centerImage.setImageURI(item.getUri_image());

        }
    }
}
