package com.yogo.pjh.weather_projcect_v10;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder>{
    Context context;

    ArrayList<ClothesItem> items=new ArrayList<ClothesItem>();
    ArrayList<BoardStringItem> items_string=new ArrayList<BoardStringItem>();

    OnItemClickListener listener;

    public static interface  OnItemClickListener{
        public void onItemClick(ViewHolder holder, View view, int position);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.board_item,parent,false);


        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ClothesItem item = items.get(position);
        BoardStringItem stringIt=items_string.get(position);
        int remdcnt=0;

        //글리드로 이미지셋
        //holder.setItem(item);
        Glide.with(context)
                .load(item.getUri_image())
                .apply(new RequestOptions().override(300,300))
                .into(holder.outerImage);

        if(item.getNext()[remdcnt].getUri_image()!=null)
        Glide.with(context)
                .load(item.getNext()[remdcnt++].getUri_image())
                .apply(new RequestOptions().override(300,300))
                .into(holder.topImage);

        if(item.getNext()[remdcnt].getUri_image()!=null)
        Glide.with(context)
                .load(item.getNext()[remdcnt++].getUri_image())
                .apply(new RequestOptions().override(300,300))
                .into(holder.bottomImage);

        holder.sendUserText.setText(stringIt.getEmail());

        holder.setOnItemClickListener(listener);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.listener=listener;
    }

    @Override
    public int getItemCount() {
        Log.d("getItemCnt",Integer.toString(items.size()));
        return (null != items ? items.size() : 0 );
    }

    public BoardAdapter(Context context)
    {
        this.context=context;
    }

    public void addItems(ArrayList<ClothesItem> items, ArrayList<BoardStringItem> string_items)
    {
        this.items=items;
        this.items_string=string_items;
    }

    public ClothesItem getItem(int position)
    {
        return items.get(position);
    }

    public void clear()
    {
        items.clear(); items_string.clear();
    }
    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView outerImage;
        ImageView topImage;
        ImageView bottomImage;
        //TextView  commentText;
        TextView dateText;
        TextView sendUserText;


        OnItemClickListener listener;

        public ViewHolder(View itemView)
        {
            super(itemView);
            outerImage=itemView.findViewById(R.id.outerImage);
            topImage=itemView.findViewById(R.id.topImage);
            bottomImage=itemView.findViewById(R.id.bottomImage);
            //commentText=(TextView)itemView.findViewById(R.id.commentText);
            dateText=(TextView)itemView.findViewById(R.id.dateText);
            sendUserText=(TextView)itemView.findViewById(R.id.sendUserText);

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
            //centerImage.setImageURI(item.getUri_image());

        }
    }
}
