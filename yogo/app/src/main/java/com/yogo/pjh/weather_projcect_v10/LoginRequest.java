package com.yogo.pjh.weather_projcect_v10;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
    final static private String URL="http://g22206.cafe24.com/pjlogin.php";
    private Map<String,String> parameters;

    public LoginRequest(String userID,String userPassword,Response.Listener<String> listener)
    {
        super(Method.POST,URL,listener,null);
        parameters=new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("userPassword",userPassword);
    }

    @Override
    public Map<String, String> getParams()
    {
        return parameters;
    }

}

