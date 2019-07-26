package com.yogo.pjh.weather_projcect_v10;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

public class MyClosetActivity extends AppCompatActivity {

    RecyclerView recyclerOuterView;
    RecyclerView recyclerTopView;
    RecyclerView recyclerBottomView;
    private String userGender="";
    private String userID="";
    private String userEmail="";
    private Boolean loginChecked;
    public static Context mMyClosetActivity;
    View closetView;
    ConstraintLayout constraintLayout;


    String clothesType;
    String clothesDetailType;
    String clothesImageAddress;
    int clothesId;


    private static final String TAG_JSON="webnautes";
    private static final String TAG_ID = "clotheinfo";
    private static final String TAG_NAME = "def";
    private static final String TAG_ADDRESS ="img";
    private static final String TAG_CLOTHES_ID="id";
    private static final String  mJsonString="";


    final ArrayList<ClothesItem> cloOuter=new ArrayList<>();
    final ArrayList<ClothesItem> cloTop=new ArrayList<>();
    final ArrayList<ClothesItem> cloBottom=new ArrayList<>();

    private ClothesAdapter outerAdapter;
    private ClothesTopAdapter topAdapter;
    private ClothesBottomAdapter bottomAdapter;

    Context ClosetAc=this;

    @Override
    protected void onResume() {
        super.onResume();
        //리사이클러 뷰 사용위한 선언
        //아우터 리사이클러
        recyclerOuterView=(RecyclerView)findViewById(R.id.recyclerOuterView);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerOuterView.setLayoutManager(layoutManager);
        outerAdapter=new ClothesAdapter(getApplicationContext());

        //상의 리사이클러
        recyclerTopView=(RecyclerView)findViewById(R.id.recyclerTopView);
        layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerTopView.setLayoutManager(layoutManager);
        topAdapter=new ClothesTopAdapter(getApplicationContext());

        //하의 리사이클러
        recyclerBottomView=(RecyclerView)findViewById(R.id.recyclerBottomView);
        layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerBottomView.setLayoutManager(layoutManager);
        bottomAdapter=new ClothesBottomAdapter(getApplicationContext());

        new GetData().execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_closet);
        closetView=findViewById(R.id.closetRootView);

        mMyClosetActivity=this;

        final Dialog imageDialog=new Dialog(this);
        imageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 없애기

        imageDialog.setContentView(R.layout.my_closet_photo_dialog); //xml 연결

        final Button clothesRegisterButton=(Button)findViewById(R.id.clothesRegisterButton);

        Intent intent = getIntent();
        userGender = intent.getStringExtra("userGender");
        userID=intent.getStringExtra("userID");

        //데이터 가져오기
        SharedPreferences settings = getSharedPreferences("settings", Activity.MODE_PRIVATE);
        loginChecked = settings.getBoolean("loginChecked", false);
        if (loginChecked) {
            userID = settings.getString("userID", "");
            userEmail = settings.getString("userEmail", "");
            userGender = settings.getString("userGender", "");
        }

        //리사이클러 뷰 사용위한 선언
        //아우터 리사이클러
        recyclerOuterView=(RecyclerView)findViewById(R.id.recyclerOuterView);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerOuterView.setLayoutManager(layoutManager);
        outerAdapter=new ClothesAdapter(getApplicationContext());

        //상의 리사이클러
        recyclerTopView=(RecyclerView)findViewById(R.id.recyclerTopView);
        layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerTopView.setLayoutManager(layoutManager);
        topAdapter=new ClothesTopAdapter(getApplicationContext());

        //하의 리사이클러
        recyclerBottomView=(RecyclerView)findViewById(R.id.recyclerBottomView);
        layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerBottomView.setLayoutManager(layoutManager);
        bottomAdapter=new ClothesBottomAdapter(getApplicationContext());

        //new GetData().execute();

        //사진 클릭시 확대하게하는 다이얼로그 창 생성
        /*outerAdapter.setOnItemClickListener(new ClothesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ClothesAdapter.ViewHolder holder, View view, int position) {
                ClothesItem item=outerAdapter.getItem(position);

                ImageView iv=(ImageView)imageDialog.findViewById(R.id.bigPhoto);
                Glide.with(ClosetAc )
                        .load(item.getUri_image())
                        .apply(new RequestOptions().override(300,300))
                        .into(iv);

                Button okb=(Button)imageDialog.findViewById(R.id.OkButton);

                Button delb=(Button)imageDialog.findViewById(R.id.DeletePhotoButton);

                imageDialog.show();
            }
        });*/

        outerAdapter.setOnItemClickListener(new ClothesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ClothesAdapter.ViewHolder holder, View view, int position) {
                Log.d("test1","test1");
                ClothesItem item=outerAdapter.getItem(position);

                ImageView iv=(ImageView)imageDialog.findViewById(R.id.bigPhoto);
                Glide.with(ClosetAc )
                        .load(item.getUri_image())
                        .apply(new RequestOptions().override(300,300))
                        .into(iv);

                Button okb=(Button)imageDialog.findViewById(R.id.OkButton);

                Button delb=(Button)imageDialog.findViewById(R.id.DeletePhotoButton);

                imageDialog.show();
            }
        });

        //옷장등록 버튼 클릭
        clothesRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userID != null) {
                    Log.d("awdawd","awdawd");
                    Intent intent = new Intent(MyClosetActivity.this, RegisterClothesActivity.class);
                    intent.putExtra("userID", userID);
                    intent.putExtra("userGender", userGender);
                    intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                }

            }
        });
    }


    public void UpdatePic(){

        closetView.invalidate();
        outerAdapter.notifyDataSetChanged();
        topAdapter.notifyDataSetChanged();
        bottomAdapter.notifyDataSetChanged();
    }

    private class GetData extends AsyncTask<String, Void, String> {

        String errorString = null;

        String target;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                target = "http://g22206.cafe24.com/pjtakeclothe.php";
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            outerAdapter = new ClothesAdapter(getApplicationContext());
            topAdapter = new ClothesTopAdapter(getApplicationContext());
            bottomAdapter = new ClothesBottomAdapter(getApplicationContext());
            outerAdapter.clear();
            topAdapter.clear();
            bottomAdapter.clear();
            cloOuter.clear();
            cloTop.clear();
            cloBottom.clear();
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject item = jsonArray.getJSONObject(i);

                    clothesType = item.getString(TAG_ID);
                    clothesDetailType= item.getString(TAG_NAME);
                    clothesImageAddress = item.getString(TAG_ADDRESS);
                    clothesId=item.getInt(TAG_CLOTHES_ID);
                    Log.d("Clothes_id_test",Integer.toString(clothesId));
                    Uri imageUri=Uri.parse("http://g22206.cafe24.com/"+clothesImageAddress);

                    if(clothesType.equals("아우터")){
                        cloOuter.add(new ClothesItem(imageUri));
                        outerAdapter.addItems(cloOuter);
                    }
                    else if(clothesType.equals("상의")){
                        cloTop.add(new ClothesItem(imageUri));
                        topAdapter.addItems(cloTop);

                    }else if(clothesType.equals("하의")){
                        cloBottom.add(new ClothesItem(imageUri));
                        bottomAdapter.addItems(cloBottom);

                    }

                }
                recyclerOuterView.setAdapter(outerAdapter);
                recyclerTopView.setAdapter(topAdapter);
                recyclerBottomView.setAdapter(bottomAdapter);
            }
            catch(Exception e){
                e.printStackTrace();
            }
            long now = System.currentTimeMillis();
            java.sql.Date date = new java.sql.Date(now);
            SimpleDateFormat sdfNow = new SimpleDateFormat("HH");
            String formatDate = sdfNow.format(date);
            Log.d("시간 : ", formatDate);


        }


        @Override
        protected String doInBackground(String... params) {
            try {
                //URL 설정및 접속
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                //전송 모드 설정
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();

                //서버로 전송
                StringBuffer buffer = new StringBuffer();
                buffer.append("userEmail").append("=").append(userEmail);                 // php 변수에 값 대입
                Log.d("dfdfdfargevfcvedrf",userEmail);

                OutputStreamWriter outStream = new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8");
                PrintWriter writer = new PrintWriter(outStream);
                writer.write(buffer.toString());
                writer.flush();




                int responseStatusCode = httpURLConnection.getResponseCode();

                InputStream inputStream;

                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString().trim();

            } catch (Exception e) {

                errorString = e.toString();

                return null;
            }

        }
    }
}
