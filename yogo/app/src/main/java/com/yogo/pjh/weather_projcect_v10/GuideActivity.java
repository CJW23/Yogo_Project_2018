package com.yogo.pjh.weather_projcect_v10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class GuideActivity extends AppCompatActivity {

    ImageButton clear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        clear = (ImageButton)findViewById(R.id.clear);  //닫기 버튼
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public void onBackPressed(){

    }
}
