package com.yogo.pjh.weather_projcect_v10;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FriendListAdapter extends ArrayAdapter<FriendListItem>{

    private  ArrayList<FriendListItem> arrayItem;
    private Context context;
    // 버튼 클릭 이벤트를 위한 Listener 인터페이스 정의.

    public interface  onButtonClickListener{
        void onButton1Click(FriendListItem item);
        void onButton2Click(FriendListItem item);
    }

    private  onButtonClickListener adptCallback=null;

    public void setOnButtonClickListener(onButtonClickListener callback){
        adptCallback=callback;
    }

    // 생성자로부터 전달된 resource id 값을 저장.

    // 생성자로부터 전달된 ListBtnClickListener  저장.


    FriendListAdapter(Context context, int resource, ArrayList<FriendListItem> list) {
        super(context, resource, list) ;

        this.arrayItem=list;
        this.context=context;

        Log.d("baka","yogogo");

    }


    // 새롭게 만든 Layout을 위한 View를 생성하는 코드
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("baka","yo0");
        View v = convertView;

       Log.d("baka","yo");
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.friend_list_item,null);       //item 설정하는 곳.
            Log.d("baka","yo2");
        }
        final FriendListItem item=arrayItem.get(position);

        // 화면에 표시될 View(Layout이 inflate된)로부터 위젯에 대한 참조 획득
        final TextView textTextView = (TextView) v.findViewById(R.id.friendEmailText);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final FriendListItem listViewItem = (FriendListItem) getItem(position);


        // 아이템 내 각 위젯에 데이터 반영

        textTextView.setText(listViewItem.getText());

        if(item!=null){
            Button btn1 = (Button) v.findViewById(R.id.friendAddBtton);
            Button btn2 = (Button) v.findViewById(R.id.friendRejectButton);
            btn1.setText(item.getBtn1());
            btn2.setText(item.getBtn2());

            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(adptCallback!=null)
                        adptCallback.onButton1Click(item);
                }
            });

            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(adptCallback!=null)
                        adptCallback.onButton2Click(item);
                }
            });
        }




        return v;
    }




}
