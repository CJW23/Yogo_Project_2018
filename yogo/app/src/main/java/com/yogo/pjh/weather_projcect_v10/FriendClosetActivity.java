package com.yogo.pjh.weather_projcect_v10;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
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
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

public class FriendClosetActivity extends AppCompatActivity {

    RecyclerView recyclerOuterView;
    RecyclerView recyclerTopView;
    RecyclerView recyclerBottomView;

    SharedPreferences settings;
    private AlertDialog dialog;
    private String userID="";
    private String userEmail="";
    private String sendUserID=""; //보내는사람(자신)
    private String sendUserEamil="";
    private Boolean loginChecked;




    String clothesType;

    String clothesDetailType;
    String clothesImageAddress;
    int clothesId;

    private static final String TAG_JSON="webnautes";
    private static final String TAG_ID = "clotheinfo";
    private static final String TAG_NAME = "def";
    private static final String TAG_ADDRESS ="img";
    private static final String TAG_CLOTHES_ID="id";


    final ArrayList<ClothesItem> cloOuter=new ArrayList<>();
    final ArrayList<ClothesItem> cloTop=new ArrayList<>();
    final ArrayList<ClothesItem> cloBottom=new ArrayList<>();

    private ClothesAdapter outerAdapter;
    private ClothesTopAdapter topAdapter;
    private ClothesBottomAdapter bottomAdapter;

    Context ClosetAc=this;

    private  boolean outerCheck = false;
    private  boolean topCheck = false;
    private  boolean bottomCheck = false;

    private Button sentToFriendBtn;

    private  int topId,outerId,bottomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_closet);


        final Dialog imageDialog=new Dialog(this);
        imageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 없애기
        imageDialog.setContentView(R.layout.my_closet_photo_dialog); //xml 연결

        sentToFriendBtn=(Button)findViewById(R.id.sendToFriendBtn);


        //데이터 가져오기
        settings = getSharedPreferences("settings", Activity.MODE_PRIVATE);
        loginChecked = settings.getBoolean("loginChecked", false);
        if (loginChecked) {
            //sendUserID = settings.getString("userID", "");
            sendUserEamil = settings.getString("userEmail", "");
            Log.d("GetIdinFriCloActivity","OK");
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

        new GetData().execute();

        //outerAdapter.addItem(new ClothesItem(Uri.parse("http://g22206.cafe24.com/image/20180713_193242.jpg"),"ss","dd"));
        //recyclerOuterView.setAdapter(outerAdapter);

        sentToFriendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((outerCheck || topCheck )&& bottomCheck){
                    Log.d("Call","ysssssso");
                    if(sendUserEamil!="" && userEmail!="" ){
                        Log.d("Call","ysso");
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    Log.d("Call","yo");
                                    if (success) {

                                        AlertDialog.Builder builder = new AlertDialog.Builder(FriendClosetActivity.this);
                                        dialog = builder.setMessage("추천을 보냈습니다.")
                                                .setNegativeButton("확인", null)
                                                .create();
                                        dialog.show();

                                    }
                                    else {
                                        Log.d("failrequest", "");
                                    }
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        FriendClosetRecommandRequest friendClosetRecommandRequest = new FriendClosetRecommandRequest(sendUserEamil,userEmail ,outerId,topId,bottomId, responseListener); //php 에서는 userEmail 이 보내는사람
                        RequestQueue queue = Volley.newRequestQueue(FriendClosetActivity.this);
                        queue.add(friendClosetRecommandRequest);
                    }else{
                        Log.d("DontHaveIdinFCA","error");
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "추천해줄 옷을 골라주세요 ", Toast.LENGTH_SHORT).show();
                }
            }
        });


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
                    Uri imageUri=Uri.parse("http://g22206.cafe24.com/"+clothesImageAddress);

                    if(clothesType.equals("아우터")){
                        cloOuter.add(new ClothesItem(imageUri,clothesType,clothesDetailType,clothesId));
                        outerAdapter.addItems(cloOuter);
                    }
                    else if(clothesType.equals("상의")){
                        cloTop.add(new ClothesItem(imageUri,clothesType,clothesDetailType,clothesId));
                        topAdapter.addItems(cloTop);

                    }else if(clothesType.equals("하의")){
                        cloBottom.add(new ClothesItem(imageUri,clothesType,clothesDetailType,clothesId));
                        bottomAdapter.addItems(cloBottom);

                    }

                }
                recyclerOuterView.setAdapter(outerAdapter);
                recyclerTopView.setAdapter(topAdapter);
                recyclerBottomView.setAdapter(bottomAdapter);
                outerAdapter.setOnItemClickListener(new ClothesAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(ClothesAdapter.ViewHolder holder, View view, int position) {
                        ClothesItem item=outerAdapter.getItem(position);
                        Log.d("linemaker","prell");
                        if(outerCheck){
                            view.setBackgroundResource(R.drawable.boarder);
                            outerCheck=false;
                        }else {
                            view.setBackgroundResource(R.drawable.shape_tag);
                            outerCheck=true;
                            outerId=item.getClothes_id();
                        }
                    }
                });


                bottomAdapter.setOnItemClickListener(new ClothesBottomAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(ClothesBottomAdapter.ViewHolder holder, View view, int position) {
                        ClothesItem item=bottomAdapter.getItem(position);
                        if(bottomCheck){
                            view.setBackgroundResource(R.drawable.boarder);
                            bottomCheck=false;
                        }else {
                            view.setBackgroundResource(R.drawable.shape_tag);
                            bottomCheck=true;
                            bottomId=item.getClothes_id();
                        }
                    }
                });


                topAdapter.setOnItemClickListener(new ClothesTopAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(ClothesTopAdapter.ViewHolder holder, View view, int position) {
                        ClothesItem item = topAdapter.getItem(position);
                        if (topCheck) {
                            view.setBackgroundResource(R.drawable.boarder);
                            topCheck = false;
                        } else {
                            view.setBackgroundResource(R.drawable.shape_tag);
                            topCheck = true;
                            topId=item.getClothes_id();
                        }
                    }
                });


            }
            catch(Exception e){
                e.printStackTrace();
            }
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


                Intent intent = getIntent();
                userEmail=intent.getStringExtra("userEmail");

                Log.d("aaadfiffidafdf",userID);
                Log.d("aaadfiffidafdf",userEmail);

                //서버로 전송
                StringBuffer buffer = new StringBuffer();
                buffer.append("userEmail").append("=").append(userEmail);                 // php 변수에 값 대입

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
