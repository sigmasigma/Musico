package com.example.gushimakota.musico;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    private LinearLayout l1;
    private LinearLayout l2;
    private LinearLayout l3;
    //進捗グラフのイメージビュー
    private ImageView imageA;
    private ImageView imageB;
    private ImageView imageC;
    //ボタンのID
    private Button aButton;
    private Button bButton;
    private Button cButton;
    //パートの状態
    private int aState;
    private int bState;
    private int cState;
    //ユーザ情報
    private ParseUser currentUser;
    private String userName;
    private int userScore;

    private TextView userText;
    private TextView dangerText1;
    private TextView dangerText2;
    private TextView dangerText3;
    private TextView stateTextA;
    private TextView stateTextB;
    private TextView stateTextC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        imageA = (ImageView)findViewById(R.id.imageAPart);
        imageB = (ImageView)findViewById(R.id.imageBPart);
        imageC = (ImageView)findViewById(R.id.imageCPart);
        userText = (TextView)findViewById(R.id.userText);
        aButton = (Button)findViewById(R.id.aButton);
        bButton = (Button)findViewById(R.id.bButton);
        cButton = (Button)findViewById(R.id.cButton);
        l1 = (LinearLayout)findViewById(R.id.s1);
        l2 = (LinearLayout)findViewById(R.id.s2);
        l3 = (LinearLayout)findViewById(R.id.s3);
        dangerText1 = (TextView)findViewById(R.id.danger1);
        dangerText2 = (TextView)findViewById(R.id.danger2);
        dangerText3 = (TextView)findViewById(R.id.danger3);
        stateTextA = (TextView)findViewById(R.id.ApartState);
        stateTextB = (TextView)findViewById(R.id.BpartState);
        stateTextC = (TextView)findViewById(R.id.CpartState);

        getUserInfo();
        currentUser = ParseUser.getCurrentUser();
        getPartStates();
    }

    private void getPartStates(){
        ParseQuery<ParseObject> queryA = ParseQuery.getQuery("Part");
        queryA.getInBackground(APART_ID, new GetCallback<ParseObject>() {
            public void done(ParseObject objectA, ParseException e) {
                if (e == null) {
                    aState = objectA.getInt("state");
                    setImageByState(imageA, aState, aButton, "A",stateTextA);
                    if (objectA.getBoolean("danger")){
                        l1.setBackgroundColor(0xff900000);
                        dangerText1.setVisibility(View.VISIBLE);
                    }
                } else {
//                    Toast.makeText(getApplicationContext(), "Maybe Parse is crashed in APart.", Toast.LENGTH_LONG).show();
                    getPartStates();
                }

            }
        });
        ParseQuery<ParseObject> queryB = ParseQuery.getQuery("Part");
        queryB.getInBackground(BPART_ID, new GetCallback<ParseObject>() {
            public void done(ParseObject objectB, ParseException e) {
                if (e == null) {
                    bState = objectB.getInt("state");
                    setImageByState(imageB, bState, bButton, "B",stateTextB);
                    if (objectB.getBoolean("danger")){
                        l2.setBackgroundColor(0xff900000);
                        dangerText2.setVisibility(View.VISIBLE);
                    }
                } else {
//                    Toast.makeText(getApplicationContext(), "Maybe Parse is crashed in BPart.", Toast.LENGTH_LONG).show();
                    getPartStates();
                }
            }
        });
        ParseQuery<ParseObject> queryC = ParseQuery.getQuery("Part");
        queryC.getInBackground(CPART_ID, new GetCallback<ParseObject>() {
            public void done(ParseObject objectC, ParseException e) {
                if (e == null) {
                    cState = objectC.getInt("state");
                    setImageByState(imageC, cState, cButton, "C", stateTextC);
                    if (objectC.getBoolean("danger")){
                        l3.setBackgroundColor(0xff900000);
                        dangerText3.setVisibility(View.VISIBLE);
                    }
                } else {
//                    Toast.makeText(getApplicationContext(), "Maybe Parse is crashed in CPart.", Toast.LENGTH_LONG).show();
                    getPartStates();
                }
            }
        });
    }

    private void getUserInfo(){
        ParseUser.logInInBackground(getString(R.string.username), getString(R.string.userpass), new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    userName = user.getString("username");
                    userScore = user.getInt("Score");
                    userText.setText("Hello " + userName);
//                    userText.setText("Hello " + userName + ", your SCORE is " + String.valueOf(userScore));
                } else {
//                    Toast.makeText(getApplicationContext(), "Parse User is crashed", Toast.LENGTH_LONG).show();
                    getUserInfo();
                }
            }
        });
    }

    private void setImageByState(ImageView image,int state, Button button, String partString, TextView stateText){
        switch (state){
            case 0:
                image.setImageResource(R.drawable.gragh0);
                stateText.setText("作曲");
                break;
            case 1:
                image.setImageResource(R.drawable.gragh10);
                stateText.setText("チェック");
                break;
            case 2:
                image.setImageResource(R.drawable.gragh20);
                stateText.setText("作曲");
                break;
            case 3:
                image.setImageResource(R.drawable.gragh40);
                stateText.setText("チェック");
                break;
            case 4:
                image.setImageResource(R.drawable.gragh50);
                stateText.setText("作曲");
                break;
            case 5:
                image.setImageResource(R.drawable.gragh60);
                stateText.setText("チェック");
                break;
            case 6:
                image.setImageResource(R.drawable.gragh80);
                stateText.setText("メタチェック");
                break;
            case 7:
                image.setImageResource(R.drawable.graph100);
                stateText.setText("完了");
                return;

            default:
                getPartStates();
                return;
        }
        setButtonVisible(partString, button, state);
    }

    private void setButtonVisible(final String partString, final Button button, int state) {
        if (currentUser != null) {
            if (currentUser.getBoolean(partString + String.valueOf(state))) {
                button.setVisibility(View.INVISIBLE);
            } else if(state %2 == 1 && currentUser.getBoolean(partString + String.valueOf(state-1))){
                button.setVisibility(View.INVISIBLE);
            }else {
                button.setVisibility(View.VISIBLE);
            }
        } else {
//            Toast.makeText(getApplicationContext(), "Parse User is crashed", Toast.LENGTH_LONG).show();
            getPartStates();
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
                Intent intent6 = new Intent(SelectActivity.this, com.example.gushimakota.musico.MetacheckBpartActivity.class);
                finish();
                startActivity(intent6);
                return;
            case 7:
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
                Intent intent6 = new Intent(SelectActivity.this, com.example.gushimakota.musico.MetacheckCpartActivity.class);
                finish();
                startActivity(intent6);
                return;
            case 7:
                return;
            default:
                return;
        }
    }

}
