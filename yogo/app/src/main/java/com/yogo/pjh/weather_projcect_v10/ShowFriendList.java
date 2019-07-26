package com.yogo.pjh.weather_projcect_v10;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

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
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

public class ShowFriendList extends AppCompatActivity {

    private AlertDialog dialog;

    //private List<String> listreqfr;          // 데이터를 넣은 리스트변수 친구요청

    private EditText editSearch;        // 검색어를 입력할 Input 창


    private String userID;
    private String userEmail;
    private Boolean loginChecked;
    //private ListView requestlistView;          // 친구요청
    private ImageButton addFriendButton;

    private  ArrayList<FriendListItem> listreq;
    private  ArrayList<FriendListItem> arrayItem;
    private  FriendListAdapter adapter_item;
    private  ListView requestlistView;

    private ArrayList<FriendListItem> arraylist ;
    private List<FriendListItem> list;          // 데이터를 넣은 리스트변수
    private FriendListAdapter adapter;      // 리스트뷰에 연결할 아답터
    private ListView listView;          // 친구목록

    SharedPreferences settings;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_friend_list);

        editSearch = (EditText) findViewById(R.id.editSearch);

        listView = (ListView) findViewById(R.id.listView);

        requestlistView = (ListView) findViewById(R.id.listView2);

        addFriendButton=(ImageButton) findViewById(R.id.addFrinedButton);



        settings = getSharedPreferences("settings", Activity.MODE_PRIVATE);
        loginChecked = settings.getBoolean("loginChecked", false);
        if (loginChecked) {
            userID = settings.getString("userID", "");
            userEmail = settings.getString("userEmail", "");
        }

        // 리스트를 생성한다.
        list = new ArrayList<FriendListItem>();
        listreq = new ArrayList<FriendListItem>();

        // 검색에 사용할 데이터을 미리 저장한다.
        new GetData().execute();
        new GetDataRequestFrined().execute();


        // input창에 검색어를 입력시 "addTextChangedListener" 이벤트 리스너를 정의한다.
        editSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                // input창에 문자를 입력할때마다 호출된다.
                // search 메소드를 호출한다.
                String text = editSearch.getText().toString();
                search(text);
            }
        });

        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowFriendList.this,FindFriendActivity.class); //mainactivity로 넘어가기 전에 Intent에 넣음
                intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        /*
        //리스트뷰있는거 클릭시
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String touchUserEmail = adapter.getItem(position).toString();


                Intent intent = new Intent(ShowFriendList.this,FriendClosetActivity.class); //mainactivity로 넘어가기 전에 Intent에 넣음
                intent.putExtra("userEmail",touchUserEmail);

                intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                ShowFriendList.this.startActivity(intent);

            }
        });
        */
/*
        requestlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String touchUserID=adapter.getItem(position).toString();
                Log.d("그래나와라",touchUserID);
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ShowFriendList.this);
                                dialog = builder.setMessage("친구 추가가 되었습니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                                list.clear();
                                new GetData().execute();
                                listreq.clear();
                                new GetDataRequestFrined().execute();
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
                RequestFriendRequest registerRequest = new RequestFriendRequest(touchUserID, userEmail, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ShowFriendList.this);
                queue.add(registerRequest);
            }
        });
*/

    }
    private DialogInterface.OnClickListener yesButtonClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }

    };

    private DialogInterface.OnClickListener noButtonClickListener = new DialogInterface.OnClickListener()     {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    };


    // 검색을 수행하는 메소드
    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        list.clear();


        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            list.addAll(arraylist);
            Log.d("cnjmkfjr", "");
            if(arraylist.size()==0)
                Log.d("Nofriend", "hey you need friend");
        }
        // 문자 입력을 할때..
        else {
            // 리스트의 모든 데이터를 검색한다.
            for (int i = 0; i < arraylist.size(); i++) {

                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (arraylist.get(i).getText().toLowerCase().contains(charText)) {
                    // 검색된 데이터를 리스트에 추가한다.
                    list.add(arraylist.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter.notifyDataSetChanged();
        Log.d("bnbnbnbnbn", Integer.toString(arraylist.size()));
    }

    private class GetData extends AsyncTask<String, Void, String> {

        String errorString = null;

        String target;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                target = "http://g22206.cafe24.com/pjtakefriend.php";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {

                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("friendlist");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject item = jsonArray.getJSONObject(i);
                    //String getUserID = item.getString("userID");
                    String getUserEmail=item.getString("userID");
                    FriendListItem frl=new FriendListItem(getUserEmail,"추천","삭제");
                    list.add(frl);

                    Log.d("friendlist", getUserEmail);
                }


                // 리스트의 모든 데이터를 arraylist에 복사한다.// list 복사본을 만든다.
                arraylist = new ArrayList<FriendListItem>();
                arraylist.addAll(list);

                // 리스트에 연동될 아답터를 생성한다.
                adapter = new FriendListAdapter(ShowFriendList.this,R.layout.friend_list_item,arraylist);

                adapter.setOnButtonClickListener(new FriendListAdapter.onButtonClickListener() {
                    @Override
                    public void onButton1Click(final FriendListItem item) {
                        String touchUserEmail=item.getText().trim();

                        Intent intent = new Intent(ShowFriendList.this,FriendClosetActivity.class); //mainactivity로 넘어가기 전에 Intent에 넣음
                        intent.putExtra("userEmail",touchUserEmail);

                        intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                        ShowFriendList.this.startActivity(intent);


                    }

                    String takeEmailB2;
                    @Override
                    public void onButton2Click(final FriendListItem item) {
                        takeEmailB2=item.getText().trim();
                        AlertDialog.Builder builder = new AlertDialog.Builder(ShowFriendList.this);
                        dialog = builder.setMessage("정말로 삭제하시겠습니까?")
                                .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    JSONObject jsonResponse = new JSONObject(response);
                                                    boolean success = jsonResponse.getBoolean("success");
                                                    if (success) {


                                                        arraylist.remove(item);
                                                        adapter.notifyDataSetChanged();


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
                                        RequestFriendDelete registerDelete = new RequestFriendDelete(takeEmailB2, userEmail, responseListener);
                                        RequestQueue queue = Volley.newRequestQueue(ShowFriendList.this);
                                        queue.add(registerDelete);
                                    }
                                })
                                .setPositiveButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        return;
                                    }
                                })
                                .create();
                        dialog.show();



                    }
                });
                // 리스트뷰에 아답터를 연결한다.
                listView.setAdapter(adapter);

                //add
                list.clear();
                //기본으로 다 보여줌
                list.addAll(arraylist);



            } catch (Exception e) {
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
                buffer.append("userID").append("=").append(userEmail);                 // php 변수에 값 대입

                OutputStreamWriter outStream = new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8");
                PrintWriter writer = new PrintWriter(outStream);
                writer.write(buffer.toString());
                writer.flush();

                int responseStatusCode = httpURLConnection.getResponseCode();

                InputStream inputStream;

                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
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
    private Runnable updateUI = new Runnable() {
        public void run() {
            ShowFriendList.this.adapter_item.notifyDataSetChanged();
        }
    };

    private class GetDataRequestFrined extends AsyncTask<String, Void, String> {

        String errorString = null;

        String target;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                target="http://g22206.cafe24.com/pjtakerequestfriend.php";
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
                JSONArray jsonArray = jsonObject.getJSONArray("webnautes");

                if(jsonArray.length()==0)
                    return;

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject item = jsonArray.getJSONObject(i);
                    //String getUserID = item.getString("requestID");
                    String getUserEmail = item.getString("requestID");
                    FriendListItem fr = new FriendListItem(getUserEmail,"수락","거절");
                    listreq.add(fr);

                    Log.d("thistihis", getUserEmail);
                }




                // 리스트의 모든 데이터를 arraylist에 복사한다.// list 복사본을 만든다.
                arrayItem=new ArrayList<FriendListItem>();
                arrayItem.addAll(listreq);

                Log.d("ssbb",Integer.toString(arrayItem.size()));

                // 리스트에 연동될 아답터를 생성한다.
                adapter_item=new FriendListAdapter(ShowFriendList.this,R.layout.friend_list_item,arrayItem);
                Log.d("aa","fadff");
                adapter_item.setOnButtonClickListener(new FriendListAdapter.onButtonClickListener() {
                    @Override
                    public void onButton1Click(final FriendListItem item) {
                        String touchUserID=item.getText().trim();

                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    if (success) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ShowFriendList.this);
                                        dialog = builder.setMessage("친구 추가가 되었습니다.")
                                                .setNegativeButton("확인", null)
                                                .create();
                                        dialog.show();
                                        list.clear();
                                        new GetData().execute();
                                        arrayItem.remove(item);
                                        adapter_item.notifyDataSetChanged();

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
                        RequestFriendRequest registerRequest = new RequestFriendRequest(touchUserID, userEmail, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(ShowFriendList.this);
                        queue.add(registerRequest);
                    }

                    @Override
                    public void onButton2Click(final FriendListItem item) {
                        String touchUserID=item.getText().trim();
                        Log.d("그래..",touchUserID);
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    if (success) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ShowFriendList.this);
                                        dialog = builder.setMessage("친구 요청을 거절 하였습니다.")
                                                .setNegativeButton("확인", null)
                                                .create();
                                        dialog.show();

                                        arrayItem.remove(item);
                                        adapter_item.notifyDataSetChanged();


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
                        RequestFriendReject registerReject = new RequestFriendReject(touchUserID, userEmail, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(ShowFriendList.this);
                        queue.add(registerReject);

                    }
                });
                // 리스트뷰에 아답터를 연결한다.
                requestlistView.setAdapter(adapter_item);
                Log.d("ayayaNotify : ",Integer.toString(arrayItem.size()));
                listreq.clear();

                adapter_item.notifyDataSetChanged();


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
