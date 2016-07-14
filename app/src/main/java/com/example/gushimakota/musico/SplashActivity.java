package com.example.gushimakota.musico;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class SplashActivity extends AppCompatActivity {

    private Handler handler;
    private static final String FINALPRODUCT_ID = "1OLksatC2L";
    private int productState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler = new Handler();

//      Enable Local Datastore.
//        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "XB4hSQBay6VfK6ZRXTWVh3ir375cML7TOwXgt9mv", "EO4ZV6k2ZWxyiLqJj7LO9YxtAAbHwPrIKsH4LDG5");

        productState = 0;
        getProjectEnd();


        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    // 2秒待機
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (productState==2) {
                            Intent intent1 = new Intent(SplashActivity.this, com.example.gushimakota.musico.ProjectEndActivity.class);
                            finish();
                            startActivity(intent1);
                        }else{
                            Intent intent2 = new Intent(SplashActivity.this, com.example.gushimakota.musico.FlowActivity.class);
                            finish();
                            startActivity(intent2);
                        }
                    }
                });
            }
        };
        t.start();
    }


    private void getProjectEnd(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("FinalProduct");
        query.getInBackground(FINALPRODUCT_ID, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    if (object.getBoolean("complete")) {
                        productState = 2;
                    }
                    Log.d("finalproduct", "OK");
                } else {

                }
            }
        });
    }
}
