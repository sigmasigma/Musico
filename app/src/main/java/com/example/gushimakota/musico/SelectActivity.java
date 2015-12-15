package com.example.gushimakota.musico;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class SelectActivity extends AppCompatActivity {

    private ImageView imageA;
    private ImageView imageB;
    private ImageView imageC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        imageA = (ImageView)findViewById(R.id.imageAPart);
        imageB = (ImageView)findViewById(R.id.imageBPart);
        imageC = (ImageView)findViewById(R.id.imageCPart);

        changeTheProgressimage();


    }

    private void changeTheProgressimage(){
        imageA.setImageResource(R.drawable.gragh2);
        imageB.setImageResource(R.drawable.gragh3);
        imageC.setImageResource(R.drawable.gragh4);
    }
}
