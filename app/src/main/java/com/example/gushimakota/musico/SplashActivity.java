package com.example.gushimakota.musico;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.parse.Parse;

public class SplashActivity extends AppCompatActivity {

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler = new Handler();

//      Enable Local Datastore.
//        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "XB4hSQBay6VfK6ZRXTWVh3ir375cML7TOwXgt9mv", "EO4ZV6k2ZWxyiLqJj7LO9YxtAAbHwPrIKsH4LDG5");
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    // 2秒待機
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SplashActivity.this, com.example.gushimakota.musico.FlowActivity.class);
                        finish();
                        startActivity(intent);
                    }
                });

            }
        };
        t.start();
    }

}
