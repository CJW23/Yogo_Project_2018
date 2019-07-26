package com.yogo.pjh.weather_projcect_v10;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PostActivity extends AppCompatActivity {

    private String title;
    private String des;
    private String mCurrentPhotoPath="";
    String encoded_string="";
    ImageView imageView;
    EditText titletext;
    EditText destext;
    String mImageCaptureName="";
    private String userID;
    File file;
    private Uri photoUri;

    private AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        imageView = (ImageView)findViewById(R.id.postimage);
        Button captureButton = (Button) findViewById(R.id.captureButton2);
        titletext = (EditText)findViewById(R.id.title);
        destext = (EditText)findViewById(R.id.description);
        final Button submitButton = (Button) findViewById(R.id.button4);
        Intent intent = getIntent();
        userID=intent.getStringExtra("userID");
        Log.d("awdadawdawd", userID);
        //권한부여하기
        int[] permissionCheck=new int[5];
        permissionCheck[0] = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        permissionCheck[1] = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        permissionCheck[2] = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissionCheck[0]== PackageManager.PERMISSION_GRANTED && permissionCheck[1]== PackageManager.PERMISSION_GRANTED && permissionCheck[2]== PackageManager.PERMISSION_GRANTED){
            //권한있을시
        }
        else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE },51);

        }
        //등록 버튼 클릭
        submitButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                title = titletext.getText().toString();
                des = destext.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(PostActivity.this);
                                dialog = builder.setMessage("등록 성공")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(PostActivity.this);
                                dialog = builder.setMessage("등록 실패")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                PostRequest PostRequest = new PostRequest(encoded_string, mImageCaptureName, userID, title, des, responseListener);
                RequestQueue queue = Volley.newRequestQueue(PostActivity.this);
                queue.add(PostRequest);
            }
        });
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPhoto();
            }
        });
    }
    //사진가져오기
    private void selectPhoto() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (photoFile != null) {
                    photoUri = FileProvider.getUriForFile(this, getPackageName(), photoFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(intent,101);
                }
            }
        }
    }
    private File createImageFile() throws IOException{
        File dir=new File(Environment.getExternalStorageDirectory()+"/path/");
        if(!dir.exists())
        {
            dir.mkdirs();
        }
        String timeStamp=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        mImageCaptureName=timeStamp+".jpg";

        File storageDir=new File(Environment.getExternalStorageDirectory().getAbsoluteFile()+"/path/"+mImageCaptureName);
        mCurrentPhotoPath=storageDir.getAbsolutePath();

        return storageDir;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode== Activity.RESULT_OK) {
            if (requestCode == 101)     //사진찍기
            {
                getPicturePhoto();
            }
        }
    }
    private void getPicturePhoto()
    {

        Bitmap bitmap= BitmapFactory.decodeFile(mCurrentPhotoPath);

        int height=bitmap.getHeight();
        int width=bitmap.getWidth();

        ExifInterface exif=null;
        try{
            exif=new ExifInterface(mCurrentPhotoPath);
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        int exifOrientation;
        int exifDegree;

        if(exif!=null)
        {
            exifOrientation=exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            exifDegree=exifOrientationToDegrees(exifOrientation);
        }else
        {
            exifDegree=0;
        }

        //숫자부분을 수정해 원하는 크기로 변경가능
        bitmap=Bitmap.createScaledBitmap(bitmap,width/10,height/10,true);
        bitmap=photoRotate(bitmap,exifDegree);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        byte[] array= stream.toByteArray();
        encoded_string = Base64.encodeToString(array, 0);


        imageView.setImageBitmap(bitmap);
    }
    private int exifOrientationToDegrees(int exifOrientation)
    {
        if(exifOrientation==ExifInterface.ORIENTATION_ROTATE_90)
            return 90;
        else if(exifOrientation==ExifInterface.ORIENTATION_ROTATE_180)
            return 180;
        else if(exifOrientation==ExifInterface.ORIENTATION_ROTATE_270)
            return 270;
        else
            return 0;
    }
    private Bitmap photoRotate(Bitmap src, float degree)
    {
        Matrix matrix=new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(src,0,0,src.getWidth(),src.getHeight(),matrix,true);
    }
}
