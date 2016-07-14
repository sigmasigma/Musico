package com.example.gushimakota.musico;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class ProjectEndActivity extends AppCompatActivity {

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_end);



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
}
