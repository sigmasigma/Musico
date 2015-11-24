package com.example.gushimakota.musico;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;

public class MainActivity extends AppCompatActivity{

    private SeekBar progressBar;
    private MediaPlayer mediaPlayer;
    private Button btStart;
    private Button btStop;
    private Handler handler;
    private int timeProgress;
    private boolean startBool = false;
    private boolean runBool = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();

        // Enable Local Datastore.
//        Parse.enableLocalDatastore(this);
//        Parse.initialize(this, "XB4hSQBay6VfK6ZRXTWVh3ir375cML7TOwXgt9mv", "EO4ZV6k2ZWxyiLqJj7LO9YxtAAbHwPrIKsH4LDG5");
//        ParseObject testObject = new ParseObject("TestObject");
//        testObject.put("foo", "bar");
//        testObject.saveInBackground();

        mediaPlayer = MediaPlayer.create(this, R.raw.musico_pro);


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
                if (mediaPlayer.isPlaying()) {
                    btStart.setText("Start");
                    mediaPlayer.pause();
                    runBool = false;
                }else{

                    btStart.setText("Pause");
                    if (startBool == false || timeProgress == mediaPlayer.getDuration()) {
                        try {
                            timeProgress = 0;
                            progressBar.setProgress(0);
                            mediaPlayer.prepare();
                        } catch (Exception e) {
                            startBool = true;
                            runBool = true;
                            mediaPlayer.seekTo(timeProgress);
                            mediaPlayer.start();
                            runTimeBar();
                        }
                    }
                    mediaPlayer.start();
                    runBool = true;
                }
            }
        });

        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        progressBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        // ツマミをドラッグしたときに呼ばれる
                        timeProgress = progress;
                    }

                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // ツマミに触れたときに呼ばれる
                        runBool = false;
                    }

                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // ツマミを離したときに呼ばれる
                        if (startBool == false) {
                            try {
                                startBool = true;
                                runBool = true;
                            } catch (Exception e) {
                            }
                        }
                        btStart.setText("Pause");
                        mediaPlayer.seekTo(timeProgress);
                        mediaPlayer.start();
                        runTimeBar();
                        runBool = true;
                    }
                }
        );
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // アイテムを追加します
        adapter.add("red");
        adapter.add("green");
        adapter.add("blue");
        Spinner spinner = (Spinner) findViewById(R.id.spinner2);
        // アダプターを設定します
        spinner.setAdapter(adapter);
        // スピナーのアイテムが選択された時に呼び出されるコールバックリスナーを登録します
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Spinner spinner = (Spinner) parent;
                // 選択されたアイテムを取得します
                String item = (String) spinner.getSelectedItem();
                Toast.makeText(MainActivity.this, item, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    private void runTimeBar(){
        Thread t = new Thread() {
            @Override
            public void run() {
                while (runBool&&mediaPlayer.getDuration() - mediaPlayer.getCurrentPosition() >= 1) {
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
