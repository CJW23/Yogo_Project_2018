package com.yogo.pjh.weather_projcect_v10;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SecessionRequest extends StringRequest{

    final static private String URL = "http://g22206.cafe24.com/pjsecession.php";
    private Map<String, String> parameters;

    public SecessionRequest(String email,Response.Listener<String> listener){

        super(Method.POST, URL, listener, null);
        parameters=new HashMap<>();
        parameters.put("email", email);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
