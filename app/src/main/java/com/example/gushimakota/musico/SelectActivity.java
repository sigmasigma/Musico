package com.example.gushimakota.musico;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class SelectActivity extends AppCompatActivity {

    private static final String APARTID = "exofgfV3QJ";
    private static final String BPARTID = "fc1U1VvuGq";
    private static final String CPARTID = "vCPCvVv5Z6";

    //進捗グラフのイメージビュー
    private ImageView imageA;
    private ImageView imageB;
    private ImageView imageC;
    //パートの状態
    private int aState;
    private int bState;
    private int cState;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        imageA = (ImageView)findViewById(R.id.imageAPart);
        imageB = (ImageView)findViewById(R.id.imageBPart);
        imageC = (ImageView)findViewById(R.id.imageCPart);

        getParseStates();

//        changeTheProgressimage();

    }

    private void getParseStates(){
        ParseQuery<ParseObject> querya = ParseQuery.getQuery("Part");
        querya.getInBackground(APARTID, new GetCallback<ParseObject>() {
            public void done(ParseObject objectA, ParseException e) {
                if (e == null) {
                    aState = objectA.getInt("state");
                    setImageByState(imageA,aState);
                } else {
                    Toast.makeText(getApplicationContext(), "Maybe Parse is crashed.", Toast.LENGTH_LONG).show();
                }
            }
        });
        ParseQuery<ParseObject> queryb = ParseQuery.getQuery("Part");
        queryb.getInBackground(BPARTID, new GetCallback<ParseObject>() {
            public void done(ParseObject objectB, ParseException e) {
                if (e == null) {
                    bState = objectB.getInt("state");
                    setImageByState(imageB,bState);
                } else {
                    Toast.makeText(getApplicationContext(), "Maybe Parse is crashed.", Toast.LENGTH_LONG).show();
                }
            }
        });
        ParseQuery<ParseObject> queryc = ParseQuery.getQuery("Part");
        queryc.getInBackground(CPARTID, new GetCallback<ParseObject>() {
            public void done(ParseObject objectC, ParseException e) {
                if (e == null) {
                    cState = objectC.getInt("state");
                    setImageByState(imageC,cState);
                    Toast.makeText(getApplicationContext(), "C state Ok", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Maybe Parse is crashed.", Toast.LENGTH_LONG).show();
                }
            }
        });
        Toast.makeText(getApplicationContext(), "Parse Passed", Toast.LENGTH_LONG).show();
    }

    private void changeTheProgressimage(){
        setImageByState(imageA,aState);
        setImageByState(imageB,bState);
        setImageByState(imageC,cState);
        Toast.makeText(getApplicationContext(), String.valueOf(cState), Toast.LENGTH_LONG).show();
    }

    private void setImageByState(ImageView image,int state){
        switch (state){
            case 0:
                image.setImageResource(R.drawable.gragh0);
                return;
            case 1:
                image.setImageResource(R.drawable.gragh1);
                return;
            case 2:
                image.setImageResource(R.drawable.gragh2);
                return;
            case 3:
                image.setImageResource(R.drawable.gragh3);
                return;
            case 4:
                image.setImageResource(R.drawable.gragh4);
                return;
            default:
                Toast.makeText(getApplicationContext(), "Maybe Parse is crashed.", Toast.LENGTH_LONG).show();
                return;
        }
    }

}
