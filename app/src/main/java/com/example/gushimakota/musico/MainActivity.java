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
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;

import java.io.IOException;

import static com.example.gushimakota.musico.R.raw.musico_pro_a_6563456_long;

public class MainActivity extends AppCompatActivity{

    private String item;
    private SeekBar progressBar;
    private MediaPlayer mediaPlayer;
    private MediaPlayer chord1;
    private MediaPlayer chord2;
    private Button btStart;
    private Handler handler;
    private int timeProgress;
    private boolean startBool = false;
    private boolean runBool = false;
    private TextView textView;
    private boolean playChord1 = false;

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
        textView = (TextView)findViewById(R.id.textView);
        progressBar = (SeekBar) findViewById(R.id.seekBar);
        // 水平プログレスバーの最大値を設定します
        progressBar.setMax(mediaPlayer.getDuration());
        // 水平プログレスバーの値を設定します
        progressBar.setProgress(0);
        btStart = (Button)findViewById(R.id.bt_start);
        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    btStart.setText("Start");
                    mediaPlayer.pause();
                    chord1.pause();
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
                            playChord1 = true;
                            runTimeBar();
                        }
                    }
                    if (playChord1) {
                        chord1.seekTo(timeProgress+180);
                        chord1.start();
                    }
                    mediaPlayer.start();
                    runBool = true;
                }
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
                        chord1.seekTo(timeProgress+180);
                        chord1.start();
                        playChord1 = true;
                        runTimeBar();
                        runBool = true;
                    }
                }
        );

        //spinnerの処理
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // アイテム追加
        adapter.add("1");
        adapter.add("2");
        adapter.add("3");
        adapter.add("4");
        adapter.add("5");
        adapter.add("6");
        adapter.add("7");
        adapter.add("8");
        Spinner spinner = (Spinner) findViewById(R.id.spinner2);
        // アダプター設定
        spinner.setAdapter(adapter);
        // スピナーのアイテムが選択された時に呼び出されるコールバックリスナーを登録します
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Spinner spinner = (Spinner) parent;
                // 選択されたアイテムを取得します
                item = (String) spinner.getSelectedItem();
                Toast.makeText(MainActivity.this, item, Toast.LENGTH_LONG).show();
                changeChord(item);


            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        if (playChord1){
            try {
                chord1.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void changeChord(String spin){
        if (playChord1) {
            chord1.reset();
            chord1.release();
        }
        switch (spin){
            case "1":
                chord1 = MediaPlayer.create(this,R.raw.musico_pro_a_4563451_long_1);
                break;
            case "2":
                chord1 = MediaPlayer.create(this,R.raw.musico_pro_a_6563456_alpe);
                break;
            case "3":
                chord1 = MediaPlayer.create(this,R.raw.musico_pro_a_4563451_long);
                break;
            case "4":
                chord1 = MediaPlayer.create(this,R.raw.musico_pro_a_4563451_alpe);
                break;
            case "5":
                chord1 = MediaPlayer.create(this,R.raw.musico_pro_a_6351_long);
                break;
            case "6":
                chord1 = MediaPlayer.create(this,R.raw.musico_pro_a_6351_alpe);
                break;
            case "7":
                chord1 = MediaPlayer.create(this,R.raw.musico_pro_a_canon_long);
                break;
            case "8":
                chord1 = MediaPlayer.create(this,R.raw.musico_pro_a_canon_alpe);
                break;
        }
        playChord1 = true;
    }

    private void runTimeBar() {
        Thread t = new Thread() {
            @Override
            public void run() {
                while (runBool && mediaPlayer.getDuration() - mediaPlayer.getCurrentPosition() >= 1) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            progressBar.setProgress(mediaPlayer.getCurrentPosition());
//                            if (playChord1&&mediaPlayer.getCurrentPosition() > 46000 && mediaPlayer.getCurrentPosition() < 74500) {
//                                chord1.seekTo();
//                                chord1.start();
//                                playChord1 = false;
//                            }

                        }

                    });
                }
            }
        };
        t.start();
    }
}
