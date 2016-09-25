package com.example.dkdk6.alwayon0906;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    String TAG = "CAMERA";
    private Context mContext = this;
    public static String IMAGE_FILE = "capture.jpg";
    int cameraCount = 0;
    int flashCount = 0;
    int timerInfo = 0;
    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "YogiCam");
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("YogiCam", "failed to create directory");
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_"+ timeStamp + ".jpg");
        Log.i("YogiCam", "Saved at"+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
        return mediaFile;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final CameraSurfaceView cameraView = new CameraSurfaceView(getApplicationContext());
        final CameraSurfaceView FrontcameraView = new CameraSurfaceView(getApplicationContext());
        FrontcameraView.getNumber(1,0);
        final FrameLayout previewFrame = (FrameLayout) findViewById(R.id.previewFrame);
        previewFrame.addView(cameraView);
        ImageButton frontCam = (ImageButton) findViewById(R.id.fcam);
        final ImageButton light = (ImageButton) findViewById(R.id.flash);
        ImageButton capture = (ImageButton) findViewById(R.id.saveBtn);
        final ImageButton timer = (ImageButton) findViewById(R.id.button_timer);
        // 버튼 이벤트 처리
        frontCam.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (cameraCount == 0) { //전면
                    cameraView.setVisibility(View.INVISIBLE);
                    previewFrame.removeAllViews();
                    previewFrame.addView(FrontcameraView);
                    FrontcameraView.setVisibility(View.VISIBLE);
                    cameraCount = 1;
                } else if (cameraCount == 1) { //두번째후면
                    cameraView.setVisibility(View.VISIBLE);
                    previewFrame.removeAllViews();
                    previewFrame.addView(cameraView);
                    FrontcameraView.setVisibility(View.INVISIBLE);
                    cameraCount=0;
                }
            }
        });
        timer.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(timerInfo==0){
                    timer.setImageResource(R.drawable.three);
                    timerInfo=3;
                }else if(timerInfo==3){
                    timer.setImageResource(R.drawable.five);
                    timerInfo=5;
                }else if(timerInfo==5){
                    timer.setImageResource(R.drawable.ten);
                    timerInfo=10;
                }else if(timerInfo==10){
                    timer.setImageResource(R.drawable.timer);
                    timerInfo=0;
                }
            }
        });

        capture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(cameraCount==0){
                    Handler mHandler = new Handler();
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            cameraView.capture(new Camera.PictureCallback() {
                                @Override
                                public void onPictureTaken(byte[] data, Camera camera) {
                                    // JPEG 이미지가 byte[] 형태로 들어옵니다
                                    File pictureFile = getOutputMediaFile();
                                    if (pictureFile == null) {
                                        Toast.makeText(mContext, "Error saving!!", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    try {
                                        FileOutputStream fos = new FileOutputStream(pictureFile);
                                        fos.write(data);
                                        Toast.makeText(mContext, "저장", Toast.LENGTH_SHORT).show();
                                        fos.close();
                                    } catch (FileNotFoundException e) {
                                        Log.d(TAG, "File not found: " + e.getMessage());
                                    } catch (IOException e) {
                                        Log.d(TAG, "Error accessing file: " + e.getMessage());
                                    }
                                }
                            });
                        }
                    }, (timerInfo*1000));
                } else if(cameraCount==1){
                    Handler mHandler = new Handler();
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            FrontcameraView.capture(new Camera.PictureCallback() {
                                @Override
                                public void onPictureTaken(byte[] data, Camera camera) {
                                    // JPEG 이미지가 byte[] 형태로 들어옵니다
                                    File pictureFile = getOutputMediaFile();
                                    if (pictureFile == null) {
                                        Toast.makeText(mContext, "Error saving!!", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    try {
                                        FileOutputStream fos = new FileOutputStream(pictureFile);
                                        fos.write(data);
                                        Toast.makeText(mContext, "저장", Toast.LENGTH_SHORT).show();
                                        fos.close();
                                    } catch (FileNotFoundException e) {
                                        Log.d(TAG, "File not found: " + e.getMessage());
                                    } catch (IOException e) {
                                        Log.d(TAG, "Error accessing file: " + e.getMessage());
                                    }
                                }
                            });
                        }
                    }, (timerInfo*1000));
                }
            }
        });
        light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //플레시
                if(cameraCount==0){
                    if (flashCount == 0) {
                        flashCount = -1;
                        cameraView.flashOn();
                        light.setImageResource(R.drawable.flash);
                    } else if (flashCount == -1) {
                        flashCount = 0;
                        light.setImageResource(R.drawable.flashout);
                        cameraView.flashOff();
                    }
                }else if(cameraCount==1){
                    Toast.makeText(mContext, "전면카메라는 플레시를 지원하지 않습니다.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        checkDangerousPermissions();
    }


    public CameraSurfaceView makingcameraView(int Information, int timer) { //카메라뷰 만드는 method
        CameraSurfaceView cameraView = new CameraSurfaceView(getApplicationContext());
        cameraView.getNumber(Information, timer);
        return cameraView;
    }

    public void search(View search){
        final Intent searchintent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(searchintent);
    }

    public void gallery(View v){
        final int REQ_CODE_SELECT_IMAGE=100;
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
    }


    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        };
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}