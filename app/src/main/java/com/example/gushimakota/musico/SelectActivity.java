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

    private static final String APART_ID = "exofgfV3QJ";
    private static final String BPART_ID = "fc1U1VvuGq";
    private static final String CPART_ID = "vCPCvVv5Z6";

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

    private void getPartStates(){
        ParseQuery<ParseObject> queryA = ParseQuery.getQuery("Part");
        queryA.getInBackground(APART_ID, new GetCallback<ParseObject>() {
            public void done(ParseObject objectA, ParseException e) {
                if (e == null) {
                    aState = objectA.getInt("state");
                    setImageByState(imageA,aState);
                } else {
                    Toast.makeText(getApplicationContext(), "Maybe Parse is crashed in APart.", Toast.LENGTH_LONG).show();
                }
            }
        });
        ParseQuery<ParseObject> queryB = ParseQuery.getQuery("Part");
        queryB.getInBackground(BPART_ID, new GetCallback<ParseObject>() {
            public void done(ParseObject objectB, ParseException e) {
                if (e == null) {
                    bState = objectB.getInt("state");
                    setImageByState(imageB, bState);
                } else {
                    Toast.makeText(getApplicationContext(), "Maybe Parse is crashed in BPart.", Toast.LENGTH_LONG).show();
                }
            }
        });
        ParseQuery<ParseObject> queryC = ParseQuery.getQuery("Part");
        queryC.getInBackground(CPART_ID, new GetCallback<ParseObject>() {
            public void done(ParseObject objectC, ParseException e) {
                if (e == null) {
                    cState = objectC.getInt("state");
                    setImageByState(imageC, cState);
                    Toast.makeText(getApplicationContext(), "C state Ok", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Maybe Parse is crashed in CPart.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void getUserInfo(){
        ParseUser.logInInBackground("gushi", "525", new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    userName = user.getString("username");
                    userScore = user.getInt("Score");
                    userText.setText("Hello " + userName + ", your SCORE is " + String.valueOf(userScore));
                } else {
                    Toast.makeText(getApplicationContext(), "Parse User is crashed", Toast.LENGTH_LONG).show();
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
                image.setImageResource(R.drawable.gragh20);
                return;
            case 2:
                image.setImageResource(R.drawable.gragh40);
                return;
            case 3:
                image.setImageResource(R.drawable.gragh50);
                return;
            case 4:
                image.setImageResource(R.drawable.gragh60);
                return;
            case 5:
                image.setImageResource(R.drawable.gragh80);
                return;
            case 6:
                image.setImageResource(R.drawable.gragh80);
                return;
            case 7:
                image.setImageResource(R.drawable.gragh80);
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
                Intent intent1 = new Intent(SelectActivity.this, com.example.gushimakota.musico.CheckApartActivity.class);
                finish();
                startActivity(intent1);
                return;
            case 2:
                Intent intent2 = new Intent(SelectActivity.this, com.example.gushimakota.musico.EditApartActivity.class);
                finish();
                startActivity(intent2);
                return;
            case 3:

                Intent intent3 = new Intent(SelectActivity.this, com.example.gushimakota.musico.CheckApartActivity.class);
                finish();
                startActivity(intent3);
                return;
            case 4:

                Intent intent4 = new Intent(SelectActivity.this, com.example.gushimakota.musico.EditApartActivity.class);
                finish();
                startActivity(intent4);
                return;
            case 5:

                Intent intent5 = new Intent(SelectActivity.this, com.example.gushimakota.musico.CheckApartActivity.class);
                finish();
                startActivity(intent5);
                return;
            case 6:
                Intent intent6 = new Intent(SelectActivity.this, com.example.gushimakota.musico.MetacheckApartActivity.class);
                finish();
                startActivity(intent6);
                return;
            case 7:
                Intent intent7 = new Intent(SelectActivity.this, com.example.gushimakota.musico.EditApartActivity.class);
                finish();
                startActivity(intent7);
                return;

        }
    }

    public void onClickB(View v){
        switch (bState){
            case 0:
                Intent intent0 = new Intent(SelectActivity.this, com.example.gushimakota.musico.EditBpartActivity.class);
                finish();
                startActivity(intent0);
                return;
            case 1:
                Intent intent1 = new Intent(SelectActivity.this, com.example.gushimakota.musico.CheckBpartActivity.class);
                finish();
                startActivity(intent1);
                return;
            case 2:
                Intent intent2 = new Intent(SelectActivity.this, com.example.gushimakota.musico.EditBpartActivity.class);
                finish();
                startActivity(intent2);
                return;
            case 3:
                Intent intent3 = new Intent(SelectActivity.this, com.example.gushimakota.musico.CheckBpartActivity.class);
                finish();
                startActivity(intent3);
                return;
            case 4:
                Intent intent4 = new Intent(SelectActivity.this, com.example.gushimakota.musico.EditBpartActivity.class);
                finish();
                startActivity(intent4);
                return;
            case 5:
                Intent intent5 = new Intent(SelectActivity.this, com.example.gushimakota.musico.CheckBpartActivity.class);
                finish();
                startActivity(intent5);
                return;
            case 6:
//                Intent intent6 = new Intent(SelectActivity.this, com.example.gushimakota.musico.MetacheckBpartActivity.class);
//                finish();
//                startActivity(intent1);
                return;
            case 7:
                Intent intent7 = new Intent(SelectActivity.this, com.example.gushimakota.musico.CheckBpartActivity.class);
                finish();
                startActivity(intent7);
                return;
        }
    }

    public void onClickC(View v){
        switch (cState){
            case 0:
                Intent intent0 = new Intent(SelectActivity.this, com.example.gushimakota.musico.EditCpartActivity.class);
                finish();
                startActivity(intent0);
                return;
            case 1:
                Intent intent1 = new Intent(SelectActivity.this, com.example.gushimakota.musico.CheckCpartActivity.class);
                finish();
                startActivity(intent1);
                return;
            case 2:
                Intent intent2 = new Intent(SelectActivity.this, com.example.gushimakota.musico.EditCpartActivity.class);
                finish();
                startActivity(intent2);
                return;
            case 3:
                Intent intent3 = new Intent(SelectActivity.this, com.example.gushimakota.musico.CheckCpartActivity.class);
                finish();
                startActivity(intent3);
                return;
            case 4:
                Intent intent4 = new Intent(SelectActivity.this, com.example.gushimakota.musico.EditCpartActivity.class);
                finish();
                startActivity(intent4);
                return;
            case 5:
                Intent intent5 = new Intent(SelectActivity.this, com.example.gushimakota.musico.CheckCpartActivity.class);
                finish();
                startActivity(intent5);
                return;
            case 6:
//                Intent intent6 = new Intent(SelectActivity.this, com.example.gushimakota.musico.MetacheckCpartActivity.class);
//                finish();
//                startActivity(intent6);
                return;
            case 7:
                return;
            default:
                Toast.makeText(getApplicationContext(), "Maybe Parse is crashed in C part.", Toast.LENGTH_LONG).show();
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
