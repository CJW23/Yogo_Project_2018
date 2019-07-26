package com.yogo.pjh.weather_projcect_v10;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.kakao.auth.ErrorCode;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;

import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;

import com.kakao.usermgmt.response.model.UserProfile;

import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

import org.json.JSONObject;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;


public class LoginActivity extends AppCompatActivity {


    private String getDeviceID; //휴대폰 장치 고유값

    private AlertDialog dialog;
    private String userID;
    private String userPassword;
    private String userName;
    private String userEmail;
    private int userAge;
    private String userGender;
    private CheckBox autoLoginCheckbox;
    public SharedPreferences settings;
    private Boolean loginChecked;
    private Boolean userShowCloset = false; //add7.22

    // EditText idText;
    //EditText passwordText;
    Context mConetext;

    //kakao
    SessionCallback callback;
/*
    @Override
    protected void onResume() {
        super.onResume();

        //자동 로그인 정보가져오기
        settings = getSharedPreferences("settings", Activity.MODE_PRIVATE);
        loginChecked = settings.getBoolean("loginChecked", false);
        if (loginChecked) {
            userID = settings.getString("userID", "Error_NoneID");
            //idText.setText(userID);
            userPassword = settings.getString("userPassword", "Error_NonePW");
            Log.d("onResLoginActivityID",userID);
            Log.d("onResLoginActivityPW",userPassword);
            //passwordText.setText(userPassword);
            //autoLoginCheckbox.setChecked(true);
            SocialLogin();
            AutoLogin();
            finish();
        } else {
            //idText.setText("");
            //passwordText.setText("");
        }

    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN); //키보드 화면가리기방지

        //idText=(EditText)findViewById(R.id.idText);
        //passwordText=(EditText)findViewById(R.id.passwordText);
        //final Button loginButton=(Button)findViewById(R.id.loginButton);
        //final TextView registerButton=(TextView) findViewById(R.id.registerButton);

        //final TextView findButton=(TextView) findViewById(R.id.findButton);
        //autoLoginCheckbox=(CheckBox) findViewById(R.id.autoLoginCheckbox);

        //네트워크 연결상태 체크
        if (NetworkConnection() == false) {
            NotConnected_showAlert();
        }

        //kakao
        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
        Session.getCurrentSession().checkAndImplicitOpen();


        /*
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //회원가입 버튼 눌렀을 때
                Intent registerIntent=new Intent(LoginActivity.this,RegisterActivity.class); //RegisterActivity로 넘어갑니다.
                LoginActivity.this.startActivity(registerIntent);
            }
        });
        */

        /*
        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // 아이디 찾기 버튼
                Intent findButton=new Intent(LoginActivity.this,FindIdActivity.class); //FindIdActivity로 넘어갑니다.
                LoginActivity.this.startActivity(findButton);
            }
        });
        */


        /*
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //아이디 로그인 버튼

                userID = idText.getText().toString().trim();
                userPassword = passwordText.getText().toString().trim();

                if(userID!=null&&!userID.isEmpty() && userPassword!=null && !userPassword.isEmpty()){
                    login();
                    if(loginChecked)
                        finish();
                }
            }
        });
        */
    }


    private void login() {

        /*
         //디비연동자동로그인을 위한 사용자디바이스 고유번호 가져오기 //필요없을것같아 미구현
            TelephonyManager mTelephony=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
             if (ActivityCompat.checkSelfPermission(mConetext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

             }
            if(mTelephony.getDeviceId()!=null){
                getDeviceID=mTelephony.getDeviceId();
            }else{
                getDeviceID= Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            }
         */


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success"); //로그인 성공시
                    if (success) {
                        userID = jsonResponse.getString("userID"); //정보를 DB에서 가져옵니다.
                        userPassword = jsonResponse.getString("userPassword");
                        userName = jsonResponse.getString("userName");
                        userAge = jsonResponse.getInt("userAge");
                        userGender = jsonResponse.getString("userGender");
                        userEmail = jsonResponse.getString("userEmail");
                        String userSCS = jsonResponse.getString("userShow");
                        if (userSCS.equals("true")) {
                            userShowCloset = true;
                        } else {
                            userShowCloset = false;
                        }

                        //자동로그인을 로그인정보저장
                        //AutoLoginCheck();
                        AutoLogin();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class); //mainactivity로 넘어가기 전에 Intent에 넣음
                        intent.putExtra("userID", userID);
                        intent.putExtra("userPassword", userPassword);
                        intent.putExtra("userName", userName);
                        intent.putExtra("userAge", userAge);
                        intent.putExtra("userGender", userGender);
                        LoginActivity.this.startActivity(intent);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        dialog = builder.setMessage("계정을 확인해 주세요.")
                                .setNegativeButton("다시 시도", null)
                                .create();
                        dialog.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener); //LoginRequest에 객체를 만들고
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(loginRequest);


    }

    boolean vaildid;

    private void IsValidid() {
        Response.Listener<String> responseIdListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        vaildid = false; //their no ID in DB
                    } else {
                        vaildid = true;
                    }
                    Log.d("isvalidid_fun",String.valueOf(vaildid));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        ValidateRequest validateRequest = new ValidateRequest(userID, responseIdListener);
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(validateRequest);
    }

/*
    private void RegisterUser() {
        if (userID.equals("") || userPassword.equals("") || userName.equals("") || userGender.equals("") || userEmail.equals("")) {
            Log.d("RegisterIdError", "lessInformation");
            return;
        }

        Response.Listener<String> responseRegisterListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {

                    } else {
                        Log.d("RegisterIdError", "PHPorDBError");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userName, userAge, userGender, userEmail, responseRegisterListener);
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(registerRequest);
    }
*/
    private void SocialLogin() {

        Response.Listener<String> responseLoginListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success"); //로그인 성공시
                    if (success) {
                        userID = jsonResponse.getString("userID"); //정보를 DB에서 가져옵니다.
                        userPassword = jsonResponse.getString("userPassword");
                        userName = jsonResponse.getString("userName");
                        userAge = jsonResponse.getInt("userAge");
                        Log.d("LA:userAge",userAge+"");
                        userGender = jsonResponse.getString("userGender");
                        userEmail = jsonResponse.getString("userEmail");
                        String userSCS = jsonResponse.getString("userShow");
                        if (userSCS.equals("true")) {
                            userShowCloset = true;
                        } else {
                            userShowCloset = false;
                        }
                        Log.d("SuccessLogin","InSocialLogin");
                        Log.d("SuccessLogin",userEmail);
                        //자동로그인을 로그인정보저장
                        AutoLogin();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class); //mainactivity로 넘어가기 전에 Intent에 넣음
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                        LoginActivity.this.startActivity(intent);
                    } else {
                        Log.d("NonRegister","InSocialLogin");
                        //if user wasn't registered
                        Intent intent = new Intent(LoginActivity.this, RegisterSocialLoginActivity.class); //넘어가기 전에 Intent에 넣음
                        intent.putExtra("userID", userID);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                        LoginActivity.this.startActivity(intent);


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Log.d("PreSuccessLogin",userID);
        //Log.d("PreSuccessLogin",userPassword);
        LoginRequest loginRequest = new LoginRequest(userID, "0000", responseLoginListener); //LoginRequest에 객체를 만들고
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(loginRequest);
    }

    private void SocialLoginAndRegister() {
       // IsValidid();
        //Log.d("isvalidid",String.valueOf(vaildid));
        Log.d("SocialLoginAndRegister","Enter");
        SocialLogin();

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }

    private void NotConnected_showAlert() {
        //네트워크 연결 오류시 알림
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("네트워크 연결 오류");
        builder.setMessage("사용 가능한 무선네트워크가 없습니다.")
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        android.os.Process.killProcess(android.os.Process.myPid());//앱 강제 종료
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private boolean NetworkConnection() {
        //네트워크 연결오류인지 확인
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isMobileAvailable = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isAvailable();
        boolean isMobileConnect = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        boolean isWifiAvailable = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isAvailable();
        boolean isWifiConnect = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();

        if ((isWifiAvailable && isWifiConnect) || (isMobileAvailable && isMobileConnect)) {
            return true;
        } else {
            return false;
        }
    }

    public void onStop() {
        //어플리케이션 화면이 닫힐때
        super.onStop();
    }

    /* 자동로그인체크박스를 없애고 바로 자동로그인으로 구현하기위해 사용하지않기로함
    private void AutoLoginCheck(){
        //자동로그인이 체크되어 있고, 로그인에 성공했으면 폰에 자동로그인 정보 저장
        if(autoLoginCheckbox.isChecked()){
            settings=getSharedPreferences("settings", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor=settings.edit();

            editor.putString("userID",userID);
            editor.putString("userPassword",userPassword);
            editor.putBoolean("loginChecked",true);

            editor.commit();
        }else{
            //자동로그인체크 해제시 폰에 저장된 정보 모두 삭제
            settings=getSharedPreferences("settings",Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor=settings.edit();
            editor.clear();
            editor.commit();
        }
    }
    */
    private void AutoLogin() {
        settings = getSharedPreferences("settings", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        editor.putString("userID", userID);
        editor.putString("userPassword", userPassword);
        editor.putString("userName", userName);
        editor.putInt("userAge", userAge);
        editor.putString("userGender", userGender);
        editor.putString("userEmail", userEmail);
        editor.putBoolean("loginChecked", true);
        editor.putBoolean("userShowCloset", userShowCloset);

        editor.commit();
    }

    /*
    public void showAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("로그인 에러");
        builder.setMessage("로그인 정보가 일치하지 않습니다.")
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert=builder.create();
        alert.show();
    }
    */


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            requestMe();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception != null) {
                Logger.e(exception);
            }
        }


    }

    public void requestMe() {
        //유저의 정보를 받아오는 함수

        UserManagement.getInstance().requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
//                super.onFailure(errorResult);
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {

            }

            @Override
            public void onNotSignedUp() {
                //카카오톡 회원이 아닐시

            }

            @Override
            public void onSuccess(UserProfile result) {
                userID = String.valueOf(result.getId());
                //userEmail=result.getEmail();
                //userAge=result.getKakaoAccount().getBirthday();
                //userGender=result.getKakaoAccount().getGender().getValue();
                Log.d("userID", userID);
                //Log.d("userName", userName);
                //Log.d("userEmail",userEmail);
                //Log.d("userGender",userGender);
                SocialLoginAndRegister();
            }
        });
    }
}

