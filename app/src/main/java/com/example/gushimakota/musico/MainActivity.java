package com.example.gushimakota.musico;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.parse.Parse;
import com.parse.ParseObject;

public class MainActivity extends AppCompatActivity {

    private SeekBar progressBar;
    private MediaPlayer mediaPlayer;
    private Button btStart;
    private Button btStop;
    private Handler handler;
    private int timeProgress;
    private boolean startBool = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "XB4hSQBay6VfK6ZRXTWVh3ir375cML7TOwXgt9mv", "EO4ZV6k2ZWxyiLqJj7LO9YxtAAbHwPrIKsH4LDG5");
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();

        mediaPlayer = MediaPlayer.create(this, R.raw.emergence);


        progressBar = (SeekBar) findViewById(R.id.seekBar);
        // 水平プログレスバーの最大値を設定します
        progressBar.setMax(mediaPlayer.getDuration());
        // 水平プログレスバーの値を設定します
        progressBar.setProgress(0);
        btStart = (Button)findViewById(R.id.bt_start);
        btStop = (Button)findViewById(R.id.bt_stop);
        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    mediaPlayer.prepare();
                    startBool = true;
                }catch (Exception e){
                    mediaPlayer.start();
                    runTimeBar();
                }
            }
        });
        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();

            }
        });

        try{
            mediaPlayer.prepare();
            startBool = true;
        }catch (Exception e){
            mediaPlayer.start();
            runTimeBar();
        }

        progressBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        // ツマミをドラッグしたときに呼ばれる
                        timeProgress = progress;
                    }

                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // ツマミに触れたときに呼ばれる
                    }

                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // ツマミを離したときに呼ばれる
                        if(startBool==false){
                            try{
                                mediaPlayer.prepare();
                                startBool = true;
                            }catch (Exception e){
                                mediaPlayer.seekTo(timeProgress);
                                mediaPlayer.start();
                                runTimeBar();
                            }
                        }
                        mediaPlayer.seekTo(timeProgress);
                        mediaPlayer.start();
                    }
                }
        );
    }

    private void runTimeBar(){
        Thread t = new Thread() {
            @Override
            public void run() {
                while (mediaPlayer.getDuration() - mediaPlayer.getCurrentPosition() >= 0) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(mediaPlayer.getCurrentPosition());
                        }
                    });
                }
            }
        };
        t.start();
    }
}
