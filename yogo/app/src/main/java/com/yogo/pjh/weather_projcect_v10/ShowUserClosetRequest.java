package com.yogo.pjh.weather_projcect_v10;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ShowUserClosetRequest extends StringRequest {

    final static private String URL="http://g22206.cafe24.com/pjupdateshow.php";
    private Map<String,String> parameters;

    public ShowUserClosetRequest(String userID, String showUserCloset, Response.Listener<String> listener)
    {
        super(Request.Method.POST, URL,listener,null);
        parameters=new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("userShowCloset",showUserCloset);
        Log.d("아니왜5",showUserCloset);
    }

    @Override
    public Map<String, String> getParams()
    {
        return parameters;
    }

}
