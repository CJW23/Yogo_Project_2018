package com.yogo.pjh.weather_projcect_v10;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterClothesRequest extends StringRequest {

    final static private String URL="http://g22206.cafe24.com/pjclotheregister.php";
    private Map<String,String> parameters;

    public RegisterClothesRequest(String encoded_string, String image_name, String userID, String clothesColor, String clothesType, String clothesDetailType, Response.Listener<String> listener)
    {
        super(Method.POST,URL,listener,null);
        parameters=new HashMap<>();
        parameters.put("encoded_string",encoded_string);
        parameters.put("image_name",image_name);
        parameters.put("userID", userID);
        parameters.put("color",clothesColor);
        parameters.put("clothesType",clothesType);
        parameters.put("clothesDetailType",clothesDetailType);
        // encoded_string 인코딩된 이미지 파일
        // image_name 이미지 이름
        // userID 유저 아이디
        // clothesWeather 옷 계절 (여름, 봄/가을, 겨울)
        // clothesType 아우터,상의,하의
        // clothesDetailType 가디건 가죽자켓 데님자켓 등등 디테일한 옷 종류
    }

    @Override
    public Map<String, String> getParams()
    {
        return parameters;
    }
}
