package com.yogo.pjh.weather_projcect_v10;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.util.helper.log.Logger;

import org.json.JSONObject;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;


public class UserInfoActivity extends AppCompatActivity {

    private String userID;
    private String userPassword;
    private String userName;
    private int userAge;
    private String userGender;
    private String userEmail;
    private  Boolean loginChecked;
    private Boolean userShowCloset; //add7.22
    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);


        final EditText showUserName = (EditText) findViewById(R.id.showUserName);
        final EditText showUserAge = (EditText) findViewById(R.id.showUserAge);
        final EditText showUserGender = (EditText) findViewById(R.id.showUserGender);
        final EditText showUserEmail = (EditText) findViewById(R.id.showUserEmail);
        final Button logoutButton=(Button)findViewById(R.id.logoutButton);
        final Button secessionButton=(Button)findViewById(R.id.secessionButton);


/*        Intent intent = getIntent();
        userID=intent.getStringExtra("userID");
        userPassword=intent.getStringExtra("userPassword");
        userName=intent.getStringExtra("userName");
        userAge=intent.getIntExtra("userAge",-1);
        userGender=intent.getStringExtra("userGender");*/

        //데이터 가져오기
        SharedPreferences settings = getSharedPreferences("settings", Activity.MODE_PRIVATE);
        loginChecked = settings.getBoolean("loginChecked", false);
        if (loginChecked) {
            userID = settings.getString("userID", "");
            userPassword = settings.getString("userPassword", "");
            userName = settings.getString("userName", "");
            userAge = settings.getInt("userAge", -1);
            userGender = settings.getString("userGender", "");
            userEmail = settings.getString("userEmail", "");
            userShowCloset=settings.getBoolean("userShowCloset",false); //add7.22
            Log.d("UIA:userAge",userAge+"");

        }





        showUserName.setText(userName);
        showUserName.setEnabled(false);
        showUserName.setBackgroundColor(getResources().getColor(R.color.colorGrayBright));

        showUserAge.setText(userAge+"");
        showUserAge.setEnabled(false);
        showUserAge.setBackgroundColor(getResources().getColor(R.color.colorGrayBright));

        showUserGender.setText(userGender);
        showUserGender.setEnabled(false);
        showUserGender.setBackgroundColor(getResources().getColor(R.color.colorGrayBright));

        showUserEmail.setText(userEmail);
        showUserEmail.setEnabled(false);
        showUserEmail.setBackgroundColor(getResources().getColor(R.color.colorGrayBright));





        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(UserInfoActivity.this, MainActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(loginIntent);

                //스태틱 리스트초기화
                TodayRecommendCodi.recommand_clothes.clear();

                //자동로그인체크 해제시 폰에 저장된 정보 모두 삭제
                SharedPreferences settings=getSharedPreferences("settings",Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor=settings.edit();
                editor.clear();
                editor.commit();

                //카카오톡 로그아웃
                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                    }
                });

            }
        });


       secessionButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Response.Listener<String> responseListener = new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {
                       try {
                           JSONObject jsonResponse = new JSONObject(response);
                           boolean success = jsonResponse.getBoolean("success");
                           if (success) {

                               Log.d("secession","Success");
                               //스태틱 리스트초기화
                               TodayRecommendCodi.recommand_clothes.clear();

                               //자동로그인체크 해제시 폰에 저장된 정보 모두 삭제
                               SharedPreferences settings=getSharedPreferences("settings",Activity.MODE_PRIVATE);
                               SharedPreferences.Editor editor=settings.edit();
                               editor.clear();
                               editor.commit();




                               onClickUnlink();



                           } else {     //안들어감
                               AlertDialog.Builder builder = new AlertDialog.Builder(UserInfoActivity.this);
                               dialog = builder.setMessage("실패")
                                       .setNegativeButton("확인", null)
                                       .create();
                               dialog.show();
                           }

                       } catch (Exception e) {
                           e.printStackTrace();
                       }
                   }
               };
               SecessionRequest secessionRequest = new SecessionRequest(userEmail, responseListener);
               RequestQueue queue = Volley.newRequestQueue(UserInfoActivity.this);
               queue.add(secessionRequest);
           }
       });
    }

    private void onClickUnlink() {
        final String appendMessage = getString(R.string.com_kakao_confirm_unlink);
        Log.d("onClickUnlink",getString(R.string.com_kakao_confirm_unlink));
        new AlertDialog.Builder(this)
                .setMessage(appendMessage)
                .setPositiveButton(getString(R.string.com_kakao_ok_button),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                UserManagement.getInstance().requestUnlink(new UnLinkResponseCallback() {
                                    @Override
                                    public void onFailure(ErrorResult errorResult) {
                                        Logger.e(errorResult.toString());
                                    }

                                    @Override
                                    public void onSessionClosed(ErrorResult errorResult) {
                                        Log.d("secession","success");
                                    }

                                    @Override
                                    public void onNotSignedUp() {
                                        Log.d("secession","yetNotLogin/imposiLog");
                                    }

                                    @Override
                                    public void onSuccess(Long userId) {
                                        Log.d("secession","success");
                                        //카카오톡 로그아웃
                                        UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                                            @Override
                                            public void onCompleteLogout() {
                                                Intent intent = new Intent(UserInfoActivity.this,MainActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                intent.addFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                                                UserInfoActivity.this.startActivity(intent);
                                                finish();
                                            }
                                        });

                                    }
                                });
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton(getString(R.string.com_kakao_cancel_button),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();

    }

}
