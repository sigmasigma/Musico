package com.example.gushimakota.musico;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Environment;
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

import java.io.File;
import java.io.IOException;



public class MainActivity extends AppCompatActivity {

    private SeekBar progressBar;
    private MediaPlayer mediaPlayer;
    private SoundPool recData;
    private int clapId = -1;
    private int noiseId = -1;
    private int freeId = -1;
    private Button btStart;
    private Button recMode;
    private Handler handler;
    private int timeProgress;
    private boolean startBool = false;
    private boolean runBool = false;
    private String item1 = "";
    private String item2 = "";

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

        mediaPlayer = MediaPlayer.create(this, R.raw.a00);

        recData = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        timeProgress = 0;
        progressBar = (SeekBar) findViewById(R.id.seekBar);
        progressBar.setMax(mediaPlayer.getDuration());
        progressBar.setProgress(0);
        btStart = (Button) findViewById(R.id.bt_start);

        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    btStart.setText("Start");
                    mediaPlayer.pause();
                    runBool = false;
                }else{
                    playing();
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
                        mediaPlayer.pause();
                        runBool = false;
                    }

                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // ツマミを離したときに呼ばれ
                        startBool = true;
                        runBool = true;
                        btStart.setText("Pause");
                        mediaPlayer.seekTo(timeProgress);
                        mediaPlayer.start();
                        runTimeBar();
                        runBool = true;
                    }
                }
        );

        //spinnerの処理
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // アイテム追加
        adapter2.add("NONE");
        adapter2.add("1");
        adapter2.add("2");
        adapter2.add("3");
        adapter2.add("4");
        adapter2.add("5");
        adapter2.add("6");
        adapter2.add("7");
        adapter2.add("8");
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        // アダプター設定
        spinner2.setAdapter(adapter2);
        // スピナーのアイテムが選択された時に呼び出されるコールバックリスナーを登録します
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Spinner spinner = (Spinner) parent;
                // 選択されたアイテムを取得します
                timeProgress = mediaPlayer.getCurrentPosition();
                mediaPlayer.pause();
                item1 = (String) spinner.getSelectedItem();
//                Toast.makeText(MainActivity.this, item1, Toast.LENGTH_LONG).show();
                changeChord();
                playing();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //spinnerの処理
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, R.layout.spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // アイテム追加
        adapter3.add("NONE");
        adapter3.add("1");
        adapter3.add("2");
        Spinner spinner3 = (Spinner) findViewById(R.id.spinner3);
        // アダプター設定
        spinner3.setAdapter(adapter3);
        // スピナーのアイテムが選択された時に呼び出されるコールバックリスナーを登録します
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Spinner spinner = (Spinner) parent;
                // 選択されたアイテムを取得します
                timeProgress = mediaPlayer.getCurrentPosition();
                mediaPlayer.pause();
                item2 = (String) spinner.getSelectedItem();
//                Toast.makeText(MainActivity.this, item2, Toast.LENGTH_LONG).show();
                changeChord();
                playing();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        recMode = (Button) findViewById(R.id.recModeBt);
        recMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.example.gushimakota.musico.RecordActivity.class);
                finish();
                mediaPlayer.stop();
                startActivity(intent);
            }
        });

    }

    private void changeChord() {
//        mediaPlayer.stop();
        if (item2 == "NONE") {
            switch (item1) {
                case "NONE":
                    mediaPlayer = MediaPlayer.create(this, R.raw.a00);
                case "1":
                    mediaPlayer = MediaPlayer.create(this, R.raw.a10);
                    break;
                case "2":
                    mediaPlayer = MediaPlayer.create(this, R.raw.a20);
                    break;
                case "3":
                    mediaPlayer = MediaPlayer.create(this, R.raw.a30);
                    break;
                case "4":
                    mediaPlayer = MediaPlayer.create(this, R.raw.a40);
                    break;
                case "5":
                    mediaPlayer = MediaPlayer.create(this, R.raw.a50);
                    break;
                case "6":
                    mediaPlayer = MediaPlayer.create(this, R.raw.a60);
                    break;
                case "7":
                    mediaPlayer = MediaPlayer.create(this, R.raw.a70);
                    break;
                case "8":
                    mediaPlayer = MediaPlayer.create(this, R.raw.a80);
                    break;
                default:
//                playChord1= false;
                    mediaPlayer = MediaPlayer.create(this, R.raw.a00);
            }
        } else if (item2 == "1") {
            switch (item1) {
                case "NONE":
                    mediaPlayer = MediaPlayer.create(this, R.raw.a01);
                    break;
                case "1":
                    mediaPlayer = MediaPlayer.create(this, R.raw.a11);
                    break;
                case "2":
                    mediaPlayer = MediaPlayer.create(this, R.raw.a21);
                    break;
                case "3":
                    mediaPlayer = MediaPlayer.create(this, R.raw.a31);
                    break;
                case "4":
                    mediaPlayer = MediaPlayer.create(this, R.raw.a41);
                    break;
                case "5":
                    mediaPlayer = MediaPlayer.create(this, R.raw.a51);
                    break;
                case "6":
                    mediaPlayer = MediaPlayer.create(this, R.raw.a61);
                    break;
                case "7":
                    mediaPlayer = MediaPlayer.create(this, R.raw.a71);
                    break;
                case "8":
                    mediaPlayer = MediaPlayer.create(this, R.raw.a81);
                    break;
                default:
//                playChord1= false;
                    mediaPlayer = MediaPlayer.create(this, R.raw.a00);
            }
        } else if (item2 == "2") {
            switch (item1) {
                case "NONE":
                    mediaPlayer = MediaPlayer.create(this, R.raw.a02);
                    break;
                case "1":
                    mediaPlayer = MediaPlayer.create(this, R.raw.a12);
                    break;
                case "2":
                    mediaPlayer = MediaPlayer.create(this, R.raw.a22);
                    break;
                case "3":
                    mediaPlayer = MediaPlayer.create(this, R.raw.a32);
                    break;
                case "4":
                    mediaPlayer = MediaPlayer.create(this, R.raw.a42);
                    break;
                case "5":
                    mediaPlayer = MediaPlayer.create(this, R.raw.a52);
                    break;
                case "6":
                    mediaPlayer = MediaPlayer.create(this, R.raw.a62);
                    break;
                case "7":
                    mediaPlayer = MediaPlayer.create(this, R.raw.a72);
                    break;
                case "8":
                    mediaPlayer = MediaPlayer.create(this, R.raw.a82);
                    break;
                default:
                    mediaPlayer = MediaPlayer.create(this, R.raw.a00);
            }

//        playChord1 = true;

        }

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
                            if (mediaPlayer.getCurrentPosition() == 87672) {
                                if (clapId > 0) {
                                    recData.play(clapId, 1.0F, 0.9F, 0, 0, 1.1F);
                                }
                            }
                        }

                    });
                }
            }
        };
        t.start();
    }

    private void playing() {
        File clapFile = new File(Environment.getExternalStorageDirectory() + "/MusicoRecFolder/clap.wav");
        if (clapFile.exists()) {
            clapId = recData.load(Environment.getExternalStorageDirectory() + "/MusicoRecFolder/clap.wav", 1);
        }
        File noiseFile = new File(Environment.getExternalStorageDirectory() + "/MusicoRecFolder/noise.wav");
        if (noiseFile.exists()) {
            noiseId = recData.load(Environment.getExternalStorageDirectory() + "/MusicoRecFolder/noise.wav", 1);
        }
        File freeFile = new File(Environment.getExternalStorageDirectory() + "/MusicoRecFolder/free.wav");
        if (freeFile.exists()) {
            freeId = recData.load(Environment.getExternalStorageDirectory() + "/MusicoRecFolder/free.wav", 1);
        }
        btStart.setText("Pause");
        if (startBool == false || timeProgress == mediaPlayer.getDuration()) {
            timeProgress = 0;
            progressBar.setProgress(0);
            startBool = true;
            runBool = true;
        }
        mediaPlayer.seekTo(timeProgress);
        mediaPlayer.start();
        runTimeBar();
        runBool = true;
    }

    public void debugSound(View v){
        File clapFile = new File(Environment.getExternalStorageDirectory() + "/MusicoRecFolder/clap.wav");
        if (clapFile.exists()) {
            clapId = recData.load(Environment.getExternalStorageDirectory() + "/MusicoRecFolder/clap.wav", 1);
        }
        File noiseFile = new File(Environment.getExternalStorageDirectory() + "/MusicoRecFolder/noise.wav");
        if (noiseFile.exists()) {
            noiseId = recData.load(Environment.getExternalStorageDirectory() + "/MusicoRecFolder/noise.wav", 1);
        }
        File freeFile = new File(Environment.getExternalStorageDirectory() + "/MusicoRecFolder/free.wav");
        if (freeFile.exists()) {
            freeId = recData.load(Environment.getExternalStorageDirectory() + "/MusicoRecFolder/free.wav", 1);
        }
        recData.play(clapId, 1.0F, 0.9F, 0, 0, 1.1F);
    }
}
