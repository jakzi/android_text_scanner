package com.example.appdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class Result extends AppCompatActivity {
    ViewFlipper viewFlipper;
    Button btn_txt,btn_img;
    ImageView img;
    Uri myUri;
    String str="";
    EditText txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setControl();
        Bundle bundle=getIntent().getExtras();
        if(bundle.getString("my_pic")!=null)
        {
            myUri= Uri.parse(bundle.getString("my_pic"));
            img.setImageURI(myUri);
        }
        Bundle bundle2=getIntent().getExtras();
        if(bundle.getString("mss")!=null)
        {
            str=bundle2.getString("mss");
            txt.setText(str);
        }
        setEvent();

    }

    private void setControl() {
        txt=(EditText) findViewById(R.id.txtResult);
        img=(ImageView)findViewById(R.id.imgResult);
        viewFlipper=(ViewFlipper)findViewById(R.id.viewF);
        btn_img=(Button)findViewById(R.id.btn_img);
        btn_txt=(Button)findViewById(R.id.btn_txt);
    }
    private void setEvent() {

        btn_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(findViewById(R.id.firstLayout)));
                btn_txt.setBackgroundColor(Color.WHITE);
                btn_img.setBackgroundColor(Color.rgb(40,30,175));
            }
        });
        btn_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(findViewById(R.id.secondLayout)));
                btn_img.setBackgroundColor(Color.WHITE);
                btn_txt.setBackgroundColor(Color.rgb(40,30,175));
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.action_activity,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.copy:
                ClipboardManager clipboard=(ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip =ClipData.newPlainText("Edit Text",txt.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(this,"Đã copy",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}