package com.yogo.pjh.weather_projcect_v10;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class BoardActivity extends AppCompatActivity {

    private static final String TAG_JSON="recommand";
    private static final String TAG_USER_ID = "userid";
    private static final String TAG_USER_EMAIL = "userEmail";
    private static final String TAG_ADDRESS_OUT ="outimg";
    private static final String TAG_ADDRESS_UP ="upimg";
    private static final String TAG_ADDRESS_DOWN ="downimg";

    RecyclerView recyclerView;
    SharedPreferences settings;
    private String userID="";
    private String userEmail="";
    private String whoSendEmail="";
    private String whoSendID="";
    private Boolean loginChecked;

    private ClothesItem GetClothes;
    final ArrayList<ClothesItem> clothesItem=new ArrayList<>();

    final ArrayList<BoardStringItem> items_string=new ArrayList<BoardStringItem>();


    String clothesType;
    String clothesImageAddressOut;
    String clothesImageAddressUp;
    String clothesImageAddressDown;
    int clothesId;
    BoardAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        recyclerView = (RecyclerView) findViewById(R.id.BoardRecycle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new BoardAdapter(getApplicationContext());

        //데이터 가져오기
        settings = getSharedPreferences("settings", Activity.MODE_PRIVATE);
        loginChecked = settings.getBoolean("loginChecked", false);
        if (loginChecked) {
            userID= settings.getString("userID", "");
            userEmail = settings.getString("userEmail", "");
            Log.d("GetIdinBoardAc","OK");
        }

        new GetData().execute();
    }



    private class GetData extends AsyncTask<String, Void, String> {

        String errorString = null;
        String target;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                target = "http://g22206.cafe24.com/pjtakerecommand.php";
            }
            catch (Exception e){
                e.printStackTrace();
            }
            Log.d("PreExecute","OK");
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            adapter = new BoardAdapter(getApplicationContext());
            adapter.clear();
            clothesItem.clear();
            items_string.clear();

            Log.d("onPostExecute","OK");
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
                Log.d("jsonArray",Integer.toString(jsonArray.length()));
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject item = jsonArray.getJSONObject(i);

                    //whoSendID = item.getString(TAG_USER_ID);
                    whoSendEmail=item.getString(TAG_USER_EMAIL);
                    Log.d("BAEmail",whoSendEmail);
                    clothesImageAddressOut = item.getString(TAG_ADDRESS_OUT);
                    clothesImageAddressUp = item.getString(TAG_ADDRESS_UP);
                    clothesImageAddressDown = item.getString(TAG_ADDRESS_DOWN);

                    Uri imageUriOut = Uri.parse("http://g22206.cafe24.com/" + clothesImageAddressOut);
                    Uri imageUriUp = Uri.parse("http://g22206.cafe24.com/" + clothesImageAddressUp);
                    Uri imageUriDown = Uri.parse("http://g22206.cafe24.com/" + clothesImageAddressDown);




                    GetClothes = new ClothesItem(imageUriOut);


                    if (GetClothes == null) {
                        GetClothes = new ClothesItem(imageUriUp);
                    } else {
                        GetClothes.inputnext(new ClothesItem(imageUriUp));
                    }

                    if (GetClothes == null) {
                        GetClothes = new ClothesItem(imageUriDown);
                    } else {
                        GetClothes.inputnext(new ClothesItem(imageUriDown));
                    }
                    clothesItem.add(GetClothes);
                    items_string.add(new BoardStringItem(whoSendEmail));
                    Log.d("Register","OK");

                    adapter.addItems(clothesItem, items_string);
                }


                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new BoardAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BoardAdapter.ViewHolder holder, View view, int position) {
                        ClothesItem item=adapter.getItem(position);
                        Log.d("clickedinBorAc","OK");

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

                Log.d("doinbackground","OK");
                Log.d("doinbackground",userEmail);
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
