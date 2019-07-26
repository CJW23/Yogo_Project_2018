package com.yogo.pjh.weather_projcect_v10;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class FindIdActivity extends AppCompatActivity {
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id);

        final EditText findText=(EditText)findViewById(R.id.findText);
        final Button findButton=(Button)findViewById(R.id.findButton);


        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName=findText.getText().toString(); //사용자가 입력한 이름을 씁니다.

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(FindIdActivity.this);
                                dialog = builder.setMessage("존재")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                finish();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(FindIdActivity.this);
                                dialog = builder.setMessage("해당 이름의 계정이 존재하지않습니다")
                                        .setNegativeButton("다시 시도", null)
                                        .create();
                                dialog.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                FindIdRequest findIdRequest = new FindIdRequest(userName, responseListener);
                RequestQueue queue = Volley.newRequestQueue(FindIdActivity.this);
                queue.add(findIdRequest);

            }
        });
    }
    protected void onStop(){
        super.onStop();
        if(dialog!=null)
        {
            dialog.dismiss();
            dialog = null;
        }
    }
}
