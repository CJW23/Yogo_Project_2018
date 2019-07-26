package com.yogo.pjh.weather_projcect_v10;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RequestFriendReject extends StringRequest {
    final static private String URL = "http://g22206.cafe24.com/pjrejectfriendreq.php";
    private Map<String,String> parameters;

    public RequestFriendReject(String userID, String requestID, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("friendID", requestID);

    }
    @Override
    public Map<String,String> getParams()
    {
        return parameters;
    }
}
