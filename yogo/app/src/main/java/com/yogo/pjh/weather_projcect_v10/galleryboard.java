package com.yogo.pjh.weather_projcect_v10;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class galleryboard extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG_JSON="webnautes";
    private static final String TAG_ID = "uid";
    private static final String TAG_TITLE = "title";
    private static final String TAG_IMG ="img";
    private static final String TAG_DATE ="date";
    private static final String TAG_DES = "des";
    private static final String TAG_POSTID = "postid";
    RecyclerView recyclerView;
    private String title;
    private String date;
    private String user;
    private String userID;
    private String img;
    private  String des;
    private int postid;
    String imgsrc;
    Button regist;
    GalleryAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galleryboard);
        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        Log.d("언제되니", userID);
        regist = (Button)findViewById(R.id.button3);
        recyclerView = (RecyclerView) findViewById(R.id.galleryRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(layoutManager);

        adapter = new GalleryAdapter(getApplicationContext());

        regist.setOnClickListener(this);
        new GetData().execute();

        Log.d("userefef : ", Integer.toString(adapter.getItemCount()));
        adapter.setOnItemClickListener(new GalleryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(GalleryAdapter.ViewHolder holder, View view, int position) {
                Galleryitem item=adapter.getItem(position);
                Log.d("음", des);
                Intent intent = new Intent(galleryboard.this, BoardActivity.class);
                intent.putExtra("user", user);
                intent.putExtra("title", title);
                intent.putExtra("date", date);
                intent.putExtra("img", imgsrc);
                intent.putExtra("des", des);
                galleryboard.this.startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, PostActivity.class);
        intent.putExtra("userID", userID);
        startActivity(intent);
    }

    private class GetData extends AsyncTask<String, Void, String> {

        String errorString = null;

        String target;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                target = "http://g22206.cafe24.com/pjtakegallery.php";
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject item = jsonArray.getJSONObject(i);

                    user = item.getString(TAG_ID);
                    title = item.getString(TAG_TITLE);
                    date = item.getString(TAG_DATE);
                    img = item.getString(TAG_IMG);
                    des = item.getString(TAG_DES);
                    postid = item.getInt(TAG_POSTID);
                    imgsrc = "http://g22206.cafe24.com/" + img;
                    Log.d("아니왜", imgsrc);

                    Uri imageUri=Uri.parse(imgsrc);

                    adapter.addItem(new Galleryitem(title,imageUri,date,user,des,postid));
                    recyclerView.setAdapter(adapter);
                }

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

                //서버로 전송
                StringBuffer buffer = new StringBuffer();
                buffer.append("userID").append("=").append("ss");                 // php 변수에 값 대입

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