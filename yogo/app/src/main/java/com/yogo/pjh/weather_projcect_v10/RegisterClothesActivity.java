package com.yogo.pjh.weather_projcect_v10;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterClothesActivity extends AppCompatActivity {

    private ArrayAdapter clothesTypeAdapter;
    private ArrayAdapter clothesColorAdapter;
    private Spinner clothesTypeSpinner;
    private Spinner clothesColorSpinner;

    private String clothesDetailType="";
    private String clothesType="";
    private String userGender="";
    private String clothesColor="";
    //private String weather="";
    private String userID="";
    private AlertDialog dialog;
    //private RadioGroup clothesWeatherGroup;
    private RadioGroup clothesTypeGroup;
    ImageView imageView;
    boolean rs = false;
    String encoded_string="";
    public static Context mContext;
    ConstraintLayout constraintLayout;
    File file;
    private Uri photoUri;

    //05.09



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_clothes);

        constraintLayout = (ConstraintLayout)findViewById(R.id.resgisterclothesmain);
        mContext = this;
        ImageButton captureButton = (ImageButton) findViewById(R.id.captureButton);
        ImageButton myGalleyButton = (ImageButton) findViewById(R.id.myGalleyButton);
        imageView = (ImageView) findViewById(R.id.clothesImageView);

        Intent intent = getIntent();
        userGender = intent.getStringExtra("userGender");
        userID=intent.getStringExtra("userID");

        long now = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("HH");
        String formatDate = sdfNow.format(date);
        Log.d("시간 : ", formatDate);

        if(Integer.parseInt(formatDate) >=5 && Integer.parseInt(formatDate) <= 11)
            constraintLayout.setBackgroundResource(R.drawable.f11);
        else if(Integer.parseInt(formatDate) >=12 && Integer.parseInt(formatDate) <= 15)
            constraintLayout.setBackgroundResource(R.drawable.t15);
        else if(Integer.parseInt(formatDate) >=16 && Integer.parseInt(formatDate) <= 18)
            constraintLayout.setBackgroundResource(R.drawable.f18);
        else if(Integer.parseInt(formatDate) >=19 && Integer.parseInt(formatDate) <= 21)
            constraintLayout.setBackgroundResource(R.drawable.e21);
        else if(Integer.parseInt(formatDate) > 21 || Integer.parseInt(formatDate) < 5)
            constraintLayout.setBackgroundResource(R.drawable.t5);

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


        //라디오버튼, 스피너 생성
        final Button submitButton = (Button) findViewById(R.id.submitButton);
        clothesTypeSpinner = (Spinner) findViewById(R.id.clothesTypeSpinner);
        clothesColorSpinner=(Spinner)findViewById(R.id.colorSpinner);

        //clothesWeatherGroup=(RadioGroup) findViewById(R.id.groupClothesWeather);
        clothesTypeGroup = (RadioGroup) findViewById(R.id.groupClothesType);
        int clothesTypeGroupID = clothesTypeGroup.getCheckedRadioButtonId();

        clothesColorAdapter=ArrayAdapter.createFromResource(RegisterClothesActivity.this,R.array.color2,android.R.layout.simple_dropdown_item_1line);
        clothesColorSpinner.setAdapter(clothesColorAdapter);

        /*
        clothesWeatherGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton clothesWeatherButton=(RadioButton) findViewById(checkedId);
                if(clothesWeatherButton != null)
                    weather=clothesWeatherButton.getText().toString();
            }
        });
*/

        clothesTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton clothesTypeButton = (RadioButton) findViewById(i);
                clothesType = clothesTypeButton.getText().toString();


                if (userGender.equals("남자")) {
                    if (clothesType.equals("아우터")) {
                        clothesTypeAdapter = ArrayAdapter.createFromResource(RegisterClothesActivity.this, R.array.manOutertype, android.R.layout.simple_dropdown_item_1line);
                        clothesTypeSpinner.setAdapter(clothesTypeAdapter);
                    } else if (clothesType.equals("상의")) {
                        clothesTypeAdapter = ArrayAdapter.createFromResource(RegisterClothesActivity.this, R.array.manToptype, android.R.layout.simple_dropdown_item_1line);
                        clothesTypeSpinner.setAdapter(clothesTypeAdapter);
                    } else if (clothesType.equals("하의")) {
                        clothesTypeAdapter = ArrayAdapter.createFromResource(RegisterClothesActivity.this, R.array.manBottomtype, android.R.layout.simple_dropdown_item_1line);
                        clothesTypeSpinner.setAdapter(clothesTypeAdapter);
                    }
                } else if (userGender.equals("여자")) {
                    if (clothesType.equals("아우터")) {
                        clothesTypeAdapter = ArrayAdapter.createFromResource(RegisterClothesActivity.this, R.array.womanOutertype, android.R.layout.simple_dropdown_item_1line);
                        clothesTypeSpinner.setAdapter(clothesTypeAdapter);
                    } else if (clothesType.equals("상의")) {
                        clothesTypeAdapter = ArrayAdapter.createFromResource(RegisterClothesActivity.this, R.array.womanToptype, android.R.layout.simple_dropdown_item_1line);
                        clothesTypeSpinner.setAdapter(clothesTypeAdapter);
                    } else if (clothesType.equals("하의")) {
                        clothesTypeAdapter = ArrayAdapter.createFromResource(RegisterClothesActivity.this, R.array.womanBottomtype, android.R.layout.simple_dropdown_item_1line);
                        clothesTypeSpinner.setAdapter(clothesTypeAdapter);
                    }
                }
            }
        });


        //등록 버튼 클릭
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (encoded_string.equals("") || mImageCaptureName.equals("") || clothesType.equals("") ||  userGender.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterClothesActivity.this);
                    dialog = builder.setMessage("빈칸을 채워주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }

                clothesDetailType = clothesTypeSpinner.getSelectedItem().toString();
                clothesColor=clothesColorSpinner.getSelectedItem().toString();
                clothesColor=colorKorToEng();

                if(clothesDetailType.equals("") || clothesColor.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterClothesActivity.this);
                    dialog = builder.setMessage("빈칸을 채워주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterClothesActivity.this);
                                dialog = builder.setMessage("등록 성공")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = getIntent();
                                                finish();
                                                startActivity(intent);
                                            }
                                        })
                                        .create();
                                dialog.show();



                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterClothesActivity.this);
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
                RegisterClothesRequest registerClothesRequest = new RegisterClothesRequest(encoded_string, mImageCaptureName, userID, clothesColor, clothesType ,clothesDetailType, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterClothesActivity.this);
                queue.add(registerClothesRequest);

            }
        });


        //add 07.03 for 7.0 issue
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPhoto();
            }
        });


        //갤러리에서 가져오기`
        myGalleyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent,102);
            }
        });

    }

    private String colorKorToEng(){
        if(clothesColor.equals("빨강"))
            return "red";
        else if (clothesColor.equals("주황"))
            return "orange";
        else if (clothesColor.equals("노랑"))
            return "yellow";
        else if (clothesColor.equals("초록"))
            return "green";
        else if (clothesColor.equals("파랑"))
            return "blue";
        else if (clothesColor.equals("남색"))
            return "indigo";
        else if (clothesColor.equals("보라"))
            return "purple";
        else if (clothesColor.equals("검은색"))
            return "black";
        else if (clothesColor.equals("흰색"))
            return "white";
        else if (clothesColor.equals("회색"))
            return "gray";
        else if (clothesColor.equals("검청"))
            return "maxblue";
        else if (clothesColor.equals("진청"))
            return "highblue";
        else if (clothesColor.equals("중청"))
            return "midblue";
        else if (clothesColor.equals("연청"))
            return "lowblue";
        else if (clothesColor.equals("검체크"))
            return "blackcheck";
        else if (clothesColor.equals("빨체크"))
            return "redcheck";
        else if (clothesColor.equals("초록체크"))
            return "greencheck";
        else if (clothesColor.equals("베이지"))
            return "beige";
        else if (clothesColor.equals("갈색"))
            return "brown";
        else
            return "";
    }

    boolean verifyPermission; //모든 권한 허락받았는지 확인하는변수
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        verifyPermission=true;
        switch(requestCode){
            case 51:
                if(grantResults.length>0)
                {
                    for(int i=0;i<grantResults.length;i++) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            //사용자가 권한 승인함
                        } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                            verifyPermission=false;
                            Toast.makeText(this, "권한을 부여하지 않으면 옷을 등록할 수 없습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        }
    }

    private String mCurrentPhotoPath="";
    String mImageCaptureName="";

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

        if(resultCode==Activity.RESULT_OK) {
            if (requestCode == 101)     //사진찍기
            {
                getPicturePhoto();
            }
            else if(requestCode==102) //갤러리
            {
                sendPicture(data.getData());
            }
          //  ((MyClosetActivity)(MyClosetActivity.mMyClosetActivity)).UpdatePic();
        }
    }

    //카메라
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

    /* 백그라운처리 사용 X
    private class Encode_image extends AsyncTask<Void, Void, Void> //*
    {
        protected Void doInBackground(Void... voids)
        {


            return null;
        }
        protected void onPostExecute(Void aVoid)
        {
        }
    }
    */


    //갤러리에서가져오기
    private void sendPicture(Uri imgUri)
    {
        String imagePath=getRealPathFromURI(imgUri); //path 경로
        ExifInterface exif=null;

        try{
            exif=new ExifInterface(imagePath);
        }catch (IOException e){
            e.printStackTrace();
        }

        //이미지이름설정
        String timeStamp=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        mImageCaptureName=timeStamp+".jpg";

        //사진 정방향으로 전환
        int exifOrientation=exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_NORMAL);
        int exifDegree=exifOrientationToDegrees(exifOrientation);


        mCurrentPhotoPath=imagePath;


        Bitmap bitmap=BitmapFactory.decodeFile(imagePath);//경로를 통해 비트맵으로전환
        int height=bitmap.getHeight();
        int width=bitmap.getWidth();

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

    //갤러리에서 선택된 사진 절대경로 가져오기
    private String getRealPathFromURI(Uri contentUri)
    {
        int column_index=0;
        String[] proj={MediaStore.Images.Media.DATA};
        Cursor cursor=getContentResolver().query(contentUri,proj,null,null,null);
        if(cursor.moveToFirst())
        {
            column_index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }
        return cursor.getString(column_index);
    }

    //사진 정방향으로 전환하기
    private Bitmap photoRotate(Bitmap src, float degree)
    {
        Matrix matrix=new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(src,0,0,src.getWidth(),src.getHeight(),matrix,true);
    }

}
