package com.example.gushimakota.musico;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ThankYouActivity extends AppCompatActivity {

    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);

        handler = new Handler();
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    // 4秒待機
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(ThankYouActivity.this, com.example.gushimakota.musico.FlowActivity.class);
                        finish();
                        startActivity(intent);
                    }
                });
            }
        };
        t.start();
    }

}
