package com.yogo.pjh.weather_projcect_v10;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by cjw72 on 2018-11-04.
 */

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        try{
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
