package com.example.gushimakota.musico;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;

public class ThankYouActivity extends AppCompatActivity {

    private static final String APART_ID = "exofgfV3QJ";
    private static final String BPART_ID = "fc1U1VvuGq";
    private static final String CPART_ID = "vCPCvVv5Z6";

    private int aState;
    private int bState;
    private int cState;

    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);

        getState();
        if (aState == 7&&bState == 7&&cState == 7){
            ParsePush push = new ParsePush();
            push.setMessage("クラウドによる作曲が完了しました。");
            push.sendInBackground();
        }

        handler = new Handler();
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    // 4秒待機
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
//                        Intent intent = new Intent(ThankYouActivity.this, com.example.gushimakota.musico.FlowActivity.class);
                        finish();
                        System.exit(0);
//                        startActivity(intent);
                    }
                });
            }
        };
        t.start();
    }

    private void getState(){
        ParseQuery<ParseObject> queryA = ParseQuery.getQuery("Part");
        queryA.getInBackground(APART_ID, new GetCallback<ParseObject>() {
            public void done(ParseObject objectA, ParseException e) {
                if (e!= null){
                    aState = objectA.getInt("State");
                }else{
                    getState();
                }

            }
        });
        ParseQuery<ParseObject> queryB = ParseQuery.getQuery("Part");
        queryB.getInBackground(BPART_ID, new GetCallback<ParseObject>() {
            public void done(ParseObject objectB, ParseException e) {
                if (e != null) {
                    bState = objectB.getInt("state");
                }else{
                    getState();
                }

            }
        });
        ParseQuery<ParseObject> queryC = ParseQuery.getQuery("Part");
        queryC.getInBackground(CPART_ID, new GetCallback<ParseObject>() {
            public void done(ParseObject objectC, ParseException e) {
                if (e == null) {
                    cState = objectC.getInt("state");
                } else {
                    getState();
                }
            }
        });
    }

}
