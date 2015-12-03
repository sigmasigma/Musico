package com.example.gushimakota.musico;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

public class RecordActivity extends AppCompatActivity {

    private Button clapRec;
    private Button clapPlay;
    private Button noiseRec;
    private Button noisePlay;
    private Button freeRec;
    private Button freePlay;
    private Button returnBt;
    private MediaRecorder clapmr;
    private MediaPlayer clapmp;
    private MediaRecorder noisemr;
    private MediaPlayer noisemp;
    private MediaRecorder freemr;
    private MediaPlayer freemp;
    private boolean isRecording = false;
    private boolean claprecorded = false;
    private boolean noiserecorded = false;
    private boolean freerecorded = false;
    private boolean playing = false;
    private String clapFilePath;
    private String noiseFilePath;
    private String freeFilePath;
    private Handler handler;
    private int countTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec);
        clapRec = (Button)findViewById(R.id.clapRec);
        clapPlay =  (Button)findViewById(R.id.clapPlay);
        noiseRec = (Button)findViewById(R.id.noiseRec);
        noisePlay =  (Button)findViewById(R.id.noisePlay);
        freeRec = (Button)findViewById(R.id.freeRec);
        freePlay =  (Button)findViewById(R.id.freePlay);
        returnBt =  (Button)findViewById(R.id.returnBt);
        clapFilePath = Environment.getExternalStorageDirectory() + "/MusicoRecFolder/clap.wav";
        noiseFilePath = Environment.getExternalStorageDirectory() + "/MusicoRecFolder/noise.wav";
        freeFilePath = Environment.getExternalStorageDirectory() + "/MusicoRecFolder/free.wav";
        createNewDir();

        handler = new Handler();

        clapPlay.setVisibility(View.INVISIBLE);
        noisePlay.setVisibility(View.INVISIBLE);
        freePlay.setVisibility(View.INVISIBLE);


        returnBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecordActivity.this, com.example.gushimakota.musico.MainActivity.class);
                finish();
                startActivity(intent);
            }
        });

        clapRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playing) {
                    clapmp.stop();
                    clapmp = null;
                    playing = false;
                }
                if (!isRecording) {
                    clapmr = new MediaRecorder();
                    File clapFile = new File(clapFilePath);
                    if (clapFile.exists()) {
                        clapFile.delete();
                    }
                    clapmr.setAudioSource(MediaRecorder.AudioSource.MIC);
                    clapmr.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
                    clapmr.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                    //保存先
                    clapmr.setOutputFile(clapFilePath);

                    //録音準備＆録音開始
                    try {
                        clapmr.prepare();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    isRecording = true;

                    countTime = 3;
                    clapRec.setText("3");
                    runRecCount(clapRec, clapmr);
                } else {
                    clapRec.setText("RECORD");
                    try {
                        clapmr.stop();
                        claprecorded = true;
                        clapPlay.setVisibility(View.VISIBLE);
                    } catch (RuntimeException e) {
                        clapmr.reset();
                    }
                    isRecording = false;
                }
            }
        });

        clapPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (claprecorded) {
                    setSoundClap();
                }
            }
        });

        noiseRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (playing) {
                    noisemp.stop();
                    noisemp = null;
                    playing = false;
                }
                if (!isRecording) {
                    noisemr= new MediaRecorder();
                    File noiseFile = new File(noiseFilePath);
                    if (noiseFile.exists()) {
                        noiseFile.delete();
                    }
                    noisemr.setAudioSource(MediaRecorder.AudioSource.MIC);
                    noisemr.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
                    noisemr.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                    //保存先
                    noisemr.setOutputFile(noiseFilePath);
                    //録音準備＆録音開始
                    try {
                        noisemr.prepare();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    isRecording = true;

                    countTime = 3;
                    noiseRec.setText("3");
                    runRecCount(noiseRec,noisemr);
                } else {
                    noiseRec.setText("RECORD");
                    try {
                        noisemr.stop();
                        noiserecorded = true;
                        noisePlay.setVisibility(View.VISIBLE);
                    } catch (RuntimeException e) {
                        noisemr.reset();
                    }
                    isRecording = false;
                }
            }
        });
        noisePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noiserecorded) {
                    setSoundNoise();
                }
            }
        });

        freeRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (playing) {
                    freemp.stop();
                    freemp= null;
                    playing = false;
                }
                if (!isRecording) {
                    freemr = new MediaRecorder();
                    File freeFile = new File(freeFilePath);
                    if (freeFile.exists()) {
                        freeFile.delete();
                    }
                    freemr.setAudioSource(MediaRecorder.AudioSource.MIC);
                    freemr.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
                    freemr.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                    //保存先
                    freemr.setOutputFile(freeFilePath);
                    //録音準備＆録音開始
                    try {
                        freemr.prepare();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    isRecording = true;

                    countTime = 3;
                    freeRec.setText("3");
                    runRecCount(freeRec,freemr);
                } else {
                    freeRec.setText("RECORD");
                    try {
                        freemr.stop();
                        freerecorded = true;
                        freePlay.setVisibility(View.VISIBLE);
                    } catch (RuntimeException e) {
                        freemr.reset();
                    }
                    isRecording = false;
                }
            }
        });
        freePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (freerecorded) {
                    setSoundFree();
                }
            }
        });


    }

    private void runRecCount(final Button recBt, final MediaRecorder recorder) {
        Thread t = new Thread() {
            @Override
            public void run() {
                while (countTime>0) {
                    try {
                        // 1秒待機
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            countTime-=1;
                            if(countTime== 2){
                                recBt.setText("2");
                            }else if(countTime== 1){
                                recBt.setText("1");
                            }else if(countTime == 0){
                                recBt.setText("recording");
                                recorder.start();
                            }
                        }

                    });
                }
            }
        };
        t.start();
    }

    private void  setSoundClap(){
        clapmp = new MediaPlayer();
        try {
            clapmp.setDataSource(clapFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            clapmp.prepare();
        }catch (IOException e){
        }
        clapmp.start();
        playing = true;
    }

    private void  setSoundNoise(){
        noisemp = new MediaPlayer();
        try {
            noisemp.setDataSource(noiseFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            noisemp.prepare();
        }catch (IOException e){
        }
        noisemp.start();
        playing = true;
    }

    private void  setSoundFree(){
        freemp = new MediaPlayer();
        try {
            freemp.setDataSource(freeFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            freemp.prepare();
        }catch (IOException e){
        }
        freemp.start();
        playing = true;
    }

    private void createNewDir(){
        String folderPath = Environment.getExternalStorageDirectory() + "/MusicoRecFolder/";
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }
}
