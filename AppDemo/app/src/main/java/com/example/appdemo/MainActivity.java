package com.example.appdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toolbar;


public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_CODE =1000 ;
    private static final int PERMISSION_CODE_2 =1003 ;
    private static final int IMAGE_CAPTURE_CODE =1001 ;
    private static final int IMAGE_PICK_CODE =1002 ;
    private ImageButton btn_cam,btn_gal;
    Uri image_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControl();
        setEvent();

    }

    private void setControl() {
        btn_cam=(ImageButton) findViewById(R.id.btn_camera);
        btn_gal=(ImageButton) findViewById(R.id.btn_gal);
    }

    private void setEvent() {
        btn_cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_scale);
                btn_cam.startAnimation(animation1);
                goCam();
            }
        });
        btn_gal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_scale);
                btn_gal.startAnimation(animation1);
                goGal();
            }
        });
    }

    private void goGal() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED)
            {
                String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(permission, PERMISSION_CODE_2);
            }
            else {
                pickIMG();
            }
        }else
        {
            pickIMG();
        }
    }

    private void goCam() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.CAMERA)==
                    PackageManager.PERMISSION_DENIED||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==
                            PackageManager.PERMISSION_DENIED){
                String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permission, PERMISSION_CODE);
            }
            else {
                openCamera();
            }
        }else
        {
            openCamera();
        }
    }
    private void pickIMG() {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE);
    }
    private void openCamera() {
        ContentValues values=new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"my_pic");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Camera");
        image_uri= getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent cameraIntent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent,IMAGE_CAPTURE_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode==IMAGE_CAPTURE_CODE) {
            Intent intent=new Intent(this,Check.class);
            intent.putExtra("my_pic",image_uri.toString());
            startActivity(intent);
        }
        if (resultCode == RESULT_OK && requestCode==IMAGE_PICK_CODE) {
            image_uri = data.getData();
            Intent intent=new Intent(this,Check.class);
            intent.putExtra("my_pic",image_uri.toString());
            startActivity(intent);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length>0 && grantResults[0]==
                    PackageManager.PERMISSION_GRANTED){
                    openCamera();
                }
            }
            case PERMISSION_CODE_2:{
                if(grantResults.length>0 && grantResults[0]==
                        PackageManager.PERMISSION_GRANTED){
                    pickIMG();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_activity,menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu:
                Intent i=new Intent(this,Bar.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}