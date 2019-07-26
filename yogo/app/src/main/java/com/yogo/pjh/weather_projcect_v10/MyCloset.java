package com.yogo.pjh.weather_projcect_v10;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;


public class MyCloset extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_my_closet, container, false);

        return rootView;
    }

    private String userGender="";
    private String userID="";
    RecyclerView recyclerOuterView;
    RecyclerView recyclerTopView;
    RecyclerView recyclerBottomView;

    public void onActivityCreated(Bundle b){
        super.onActivityCreated(b);

        Bundle bundle=getArguments();
        userGender=bundle.getString("userGender");
        userID=bundle.getString("userID");

        ArrayList<ClothesItem> clo=new ArrayList<>();
        final Dialog imageDialog=new Dialog(getActivity());
        imageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 없애기

        imageDialog.setContentView(R.layout.my_closet_photo_dialog); //xml 연결


        final Button clothesRegisterButton=(Button)getView().findViewById(R.id.clothesRegisterButton);

        //리사이클러 뷰 사용위한 선언
        //아우터 리사이클러
        recyclerOuterView=(RecyclerView)getView().findViewById(R.id.recyclerOuterView);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerOuterView.setLayoutManager(layoutManager);
        final ClothesAdapter outerAdapter=new ClothesAdapter(getActivity().getApplicationContext());
        //상의 리사이클러
        recyclerTopView=(RecyclerView)getView().findViewById(R.id.recyclerTopView);
        layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerTopView.setLayoutManager(layoutManager);
        ClothesTopAdapter topAdapter=new ClothesTopAdapter(getActivity().getApplicationContext());
        //하의 리사이클러
        recyclerBottomView=(RecyclerView)getView().findViewById(R.id.recyclerBottomView);
        layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerBottomView.setLayoutManager(layoutManager);
        ClothesBottomAdapter bottomAdapter=new ClothesBottomAdapter(getActivity().getApplicationContext());


        //임시파일
        Bitmap orgImage = BitmapFactory.decodeResource(getResources(),R.mipmap.school_icon);
        clo.add(new ClothesItem(orgImage));

        /*
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {

                    } else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        ValidateRequest validateRequest = new ValidateRequest(userID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MyCloset.this.getContext());
        queue.add(validateRequest);
        */

        outerAdapter.addItems(clo);
        topAdapter.addItems(clo);
        bottomAdapter.addItems(clo);
        recyclerOuterView.setAdapter(outerAdapter);
        recyclerTopView.setAdapter(topAdapter);
        recyclerBottomView.setAdapter(bottomAdapter);

        //사진 클릭시 확대하게하는 다이얼로그 창 생성
        outerAdapter.setOnItemClickListener(new ClothesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ClothesAdapter.ViewHolder holder, View view, int position) {
                ClothesItem item=outerAdapter.getItem(position);

                ImageView iv=(ImageView)imageDialog.findViewById(R.id.bigPhoto);
                iv.setImageBitmap(item.getCenterImage());

                Button okb=(Button)imageDialog.findViewById(R.id.OkButton);

                Button delb=(Button)imageDialog.findViewById(R.id.DeletePhotoButton);

                imageDialog.show();
            }
        });

        //옷장등록 버튼 클릭
        clothesRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), RegisterClothesActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userGender",userGender);
                startActivity(intent);
            }
        });


    }


}