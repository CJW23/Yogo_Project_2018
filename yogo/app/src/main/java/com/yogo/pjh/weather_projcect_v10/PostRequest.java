package com.yogo.pjh.weather_projcect_v10;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PostRequest extends StringRequest{

    final static private String URL ="http://g22206.cafe24.com/pjpost.php";
    private Map<String, String> parameters;
    public PostRequest(String encoded_string, String image_name, String userID, String title, String des, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("encoded_string", encoded_string);
        parameters.put("image_name",image_name);
        parameters.put("userID",userID);
        parameters.put("title",title);
        parameters.put("des",des);
    }
    @Override
    public Map<String, String> getParams()
    {
        return parameters;
    }
}
