package com.example.appdemo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toolbar;

public class Bar extends AppCompatActivity implements View.OnClickListener {
    Button btn_save,btn_help,btn_exit,btn_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        setControl();
        setEvent();
    }


    private void setControl() {
        btn_save=(Button) findViewById(R.id.btn_save);
        btn_help=(Button) findViewById(R.id.btn_help);
        btn_exit=(Button) findViewById(R.id.btn_exit);
        btn_home=(Button) findViewById(R.id.btn_home);
    }
    private void setEvent() {
        btn_home.setOnClickListener(this);
        btn_help.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        btn_exit.setOnClickListener(this);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_home:
                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_scale);
                btn_home.startAnimation(animation1);
                Intent i=new Intent(this,MainActivity.class);
                startActivity(i);
                break;
            case R.id.btn_save:
                Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_scale);
                btn_save.startAnimation(animation2);
                Intent l=new Intent(this,Save.class);
                startActivity(l);
                break;
            case R.id.btn_help:
                Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_scale);
                btn_help.startAnimation(animation3);
                Intent j=new Intent(this,Help.class);
                startActivity(j);
                break;
            case R.id.btn_exit:
                Animation animation4 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_scale);
                btn_exit.startAnimation(animation4);
                onBackPressed();
                break;
        }
    }
    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Bar.this);
        builder.setMessage("Bạn có chắc muốn thoát?")
                .setCancelable(false)
                .setPositiveButton("Vâng", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}