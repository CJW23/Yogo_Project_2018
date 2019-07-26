package com.yogo.pjh.weather_projcect_v10;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class FriendClosetRecommandRequest extends StringRequest {
    final static private String URL = "http://g22206.cafe24.com/pjresgistrecommand.php";
    private Map<String,String> parameters;

    public FriendClosetRecommandRequest(String userEmail, String requestEmail,int outID,int topID,int bottomID, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        parameters = new HashMap<>();
        parameters.put("userEmail",userEmail);
        parameters.put("recEmail", requestEmail);
        parameters.put("outid", outID+"");
        parameters.put("topid", topID+"");
        parameters.put("downid", bottomID+"");

    }
    @Override
    public Map<String,String> getParams()
    {
        return parameters;
    }
}
