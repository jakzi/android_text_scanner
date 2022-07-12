package com.example.appdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

public class Check extends AppCompatActivity {
    Button btn_scan;
    ImageView imgCheck;
    Uri myUri;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setControl();
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            myUri= Uri.parse(bundle.getString("my_pic"));
            imgCheck.setImageURI(myUri);
        }
        setEvent();

    }
    private void setControl() {
        btn_scan=(Button)findViewById(R.id.btn_scan);
        imgCheck=(ImageView)findViewById(R.id.imgCheck);
    }
    private void setEvent() {

        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_scale);
                btn_scan.startAnimation(animation1);
                goResult();
            }
        });
    }

    private void goResult() {
        BitmapDrawable bitmapDrawable=(BitmapDrawable) imgCheck.getDrawable();
        bitmap = bitmapDrawable.getBitmap();
        String str="";
        TextRecognizer recognizer=new TextRecognizer.Builder(getApplicationContext()).build();
        if(recognizer.isOperational()==true){
            Frame frame=new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> items=recognizer.detect(frame);
            StringBuilder sb =new StringBuilder();
            for(int i=0;i<items.size();i++){
                TextBlock myItem=items.valueAt(i);
                sb.append(myItem.getValue());
                sb.append("\n");
            }
            str=sb.toString();
        }
        Intent intent = new Intent(this, Result.class);
        intent.putExtra("my_pic",myUri.toString());
        intent.putExtra("mss", str);
        startActivity(intent);

    }


}