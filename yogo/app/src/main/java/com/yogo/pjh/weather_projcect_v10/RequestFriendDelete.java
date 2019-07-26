package com.yogo.pjh.weather_projcect_v10;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RequestFriendDelete extends StringRequest {
    final static private String URL = "http://g22206.cafe24.com/pjdeletefriend.php";
    private Map<String,String> parameters;

    public RequestFriendDelete(String userEmail, String requestEmail, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        parameters = new HashMap<>();
        parameters.put("userEmail",userEmail);
        parameters.put("friendEmail", requestEmail);

    }
    @Override
    public Map<String,String> getParams()
    {
        return parameters;
    }
}
