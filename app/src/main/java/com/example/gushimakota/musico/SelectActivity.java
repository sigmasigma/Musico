package com.example.gushimakota.musico;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

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
    //ユーザ情報
    private String userName;
    private int userScore;
    private TextView userText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        imageA = (ImageView)findViewById(R.id.imageAPart);
        imageB = (ImageView)findViewById(R.id.imageBPart);
        imageC = (ImageView)findViewById(R.id.imageCPart);
        userText = (TextView)findViewById(R.id.userText);

        getPartStates();
        getUserInfo();

    }

    //parseがうまくいかなかったらここ変更したのでそれっぽい
    private void getPartStates(){
        ParseQuery<ParseObject> queryA = ParseQuery.getQuery("Part");
        queryA.getInBackground(APARTID, new GetCallback<ParseObject>() {
            public void done(ParseObject objectA, ParseException e) {
                if (e == null) {
                    aState = objectA.getInt("state");
                    setImageByState(imageA,aState);
                } else {
                    Toast.makeText(getApplicationContext(), "Maybe Parse is crashed.", Toast.LENGTH_LONG).show();
                }
            }
        });
        ParseQuery<ParseObject> queryB = ParseQuery.getQuery("Part");
        queryB.getInBackground(BPARTID, new GetCallback<ParseObject>() {
            public void done(ParseObject objectB, ParseException e) {
                if (e == null) {
                    bState = objectB.getInt("state");
                    setImageByState(imageB,bState);
                } else {
                    Toast.makeText(getApplicationContext(), "Maybe Parse is crashed.", Toast.LENGTH_LONG).show();
                }
            }
        });
        ParseQuery<ParseObject> queryC = ParseQuery.getQuery("Part");
        queryC.getInBackground(CPARTID, new GetCallback<ParseObject>() {
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

    private void getUserInfo(){
        ParseUser.logInInBackground("gushi", "525", new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    userName = user.getString("username");
                    userScore = user.getInt("Score");
                    userText.setText("Hello " + userName + ", your SCORE is " + String.valueOf(userScore));
                } else {

                }
            }
        });
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

    public void onClickA(View v){
        switch (aState){
            case 0:
                Intent intent0 = new Intent(SelectActivity.this, com.example.gushimakota.musico.EditApartActivity.class);
                finish();
                startActivity(intent0);
                return;
            case 1:

                return;
            case 2:
                Intent intent2 = new Intent(SelectActivity.this, com.example.gushimakota.musico.EditApartActivity.class);
                finish();
                startActivity(intent2);
                return;
            case 3:

                return;
            case 4:

                return;
            default:
                Toast.makeText(getApplicationContext(), "Maybe Parse is crashed.", Toast.LENGTH_LONG).show();
                return;
        }
    }

    public void onClickB(View v){
        switch (bState){
            case 0:

                return;
            case 1:

                return;
            case 2:

                return;
            case 3:

                return;
            case 4:

                return;
            default:
                Toast.makeText(getApplicationContext(), "Maybe Parse is crashed.", Toast.LENGTH_LONG).show();
                return;
        }
    }

    public void onClickC(View v){
        switch (cState){
            case 0:

                return;
            case 1:

                return;
            case 2:

                return;
            case 3:

                return;
            case 4:

                return;
            default:
                Toast.makeText(getApplicationContext(), "Maybe Parse is crashed.", Toast.LENGTH_LONG).show();
                return;
        }
    }

//    private void changeTheProgressimage(){
//        setImageByState(imageA,aState);
//        setImageByState(imageB,bState);
//        setImageByState(imageC,cState);
//        Toast.makeText(getApplicationContext(), String.valueOf(cState), Toast.LENGTH_LONG).show();
//    }
}
