package com.example.gushimakota.musico;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.IOException;

//時間に応じてstateを6にするのは未実装
//音源回りは未実装

public class CheckFragment extends Fragment {

    private static final String APART_ID = "exofgfV3QJ";
    private static final String BPART_ID = "fc1U1VvuGq";
    private static final String CPART_ID = "vCPCvVv5Z6";
    //どのパートか
    private static final String ARG_PART = "param3";
    //チェックするオブジェクトのID
    private static final String ARG_OBJECTID = "param4";

    private MediaPlayer player;
    private String strIdea;
    private String ideaPath;
    private boolean playing;
    private boolean init;

    //どのパートか
    private String mPart;
    //評価するトラックのID
    private String mTrackObjectId;
    //評価してるパートのオブジェクトID
    private String mPartObjectId;
    //パートの状態
    private int mState;

    private TextView textPart;
    private TextView textOr;
    private TextView textEstimate;
    private Button play;
    private Button bad;
    private Button good;

    public CheckFragment() {
    }

    public static CheckFragment newInstance(String param1, String param2) {
        CheckFragment fragment = new CheckFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PART, param1);
        args.putString(ARG_OBJECTID, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playing = false;
        if (getArguments() != null) {
            mPart = getArguments().getString(ARG_PART);
            mTrackObjectId = getArguments().getString(ARG_OBJECTID);
            switch (mPart){
                case "A":
                    mPartObjectId = APART_ID;
                    break;
                case "B":
                    mPartObjectId = BPART_ID;
                    break;
                case "C":
                    mPartObjectId = CPART_ID;
                    break;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_check, container, false);
        textPart = (TextView)v.findViewById(R.id.textPart);
        textPart.setText(mPart + " partチェック");
        setCheckButtonActions(v);
        getPartState();
        getUserInfo();
        return v;
    }

    private void getUserInfo(){
        ParseUser.logInInBackground("gushi", "525", new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                } else {
                    Toast.makeText(getActivity(), "Parse User is crashed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void getPartState(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Part");
        query.getInBackground(mPartObjectId, new GetCallback<ParseObject>() {
            public void done(ParseObject part, ParseException e) {
                if (e == null) {
                    mState = part.getInt("state");
                }
            }
        });
    }

    //再生ボタンは未実装
    private void setCheckButtonActions(View v){
        play = (Button)v.findViewById(R.id.play_for_checking);
        bad = (Button)v.findViewById(R.id.bad);
        good = (Button)v.findViewById(R.id.good);
        textEstimate = (TextView)v.findViewById(R.id.textPleaseEstimate);
        textOr = (TextView)v.findViewById(R.id.textOr);
        player = new MediaPlayer();
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Track");
                query.getInBackground(mTrackObjectId, new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (object != null) {
                            if (!init){
                                Resources res = getResources();
                                strIdea = object.getString("idea");
                                ideaPath = Environment.getExternalStorageDirectory().getPath()+"/Musico/"+strIdea +".mp3";
                                try {
                                    player.setDataSource(ideaPath);
                                    player.prepare();
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                }
                                init=true;
                            }
                            if (!playing) {

//                                int resId = res.getIdentifier(strIdea, "raw", getActivity().getPackageName());
//                                player = MediaPlayer.create(getActivity(), resId);
//                                player.start();
                                bad.setVisibility(View.VISIBLE);
                                good.setVisibility(View.VISIBLE);
                                textEstimate.setVisibility(View.VISIBLE);
                                textOr.setVisibility(View.VISIBLE);
                                player.start();
                                play.setText("停止");
                                playing = true;
                            }else{
                                playing = false;
                                play.setText("この部分を再生する");
                                player.pause();
                            }
                        }
                    }
                });
            }
        });
        bad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialogSendingBad();
            }
        });

        good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialogSendingGood();
            }
        });
    }

    private void confirmDialogSendingBad(){
// 確認ダイアログの生成
        AlertDialog.Builder alertDlg = new AlertDialog.Builder(getContext());
        alertDlg.setTitle("このトラックは良くはない");
        alertDlg.setMessage("この内容で送信しますか？");
        alertDlg.setPositiveButton(
                "送信する",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // OK ボタンクリック処理
                        disagreeToTrack();
                    }
                });
        alertDlg.setNegativeButton(
                "キャンセル",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Cancel ボタンクリック処理
                        return;
                    }
                });

        // 表示
        alertDlg.create().show();
    }

    private void confirmDialogSendingGood(){
// 確認ダイアログの生成
        AlertDialog.Builder alertDlg = new AlertDialog.Builder(getContext());
        alertDlg.setTitle("この内容で送信します");
        alertDlg.setMessage("よろしいですか？");
        alertDlg.setPositiveButton(
                "送信する",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // OK ボタンクリック処理
                        agreeToTrack();
                    }
                });
        alertDlg.setNegativeButton(
                "やり直す",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Cancel ボタンクリック処理
                        return;
                    }
                });

        // 表示
        alertDlg.create().show();
    }

    private void disagreeToTrack(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Track");
        query.getInBackground(mTrackObjectId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (object != null) {
                    int score = object.getInt("checkScore");
                    score = score - 1;
                    if (score < -1) {
                        object.deleteInBackground();
                        partStateBack();
                        Intent goToNextIntent = new Intent(getContext(), com.example.gushimakota.musico.ThankYouActivity.class);
                        startActivity(goToNextIntent);
                        getActivity().finish();
                    } else {
                        object.put("checkScore", score);
                        Intent goToNextIntent = new Intent(getContext(), com.example.gushimakota.musico.ThankYouActivity.class);
                        startActivity(goToNextIntent);
                        getActivity().finish();
                    }
                    object.saveInBackground();
                }
            }
        });
    }

    private void agreeToTrack(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Track");
        query.getInBackground(mTrackObjectId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (object != null) {
                    int score = object.getInt("checkScore");
                    score = score + 1;
                    if (score > 0) {
                        object.put("check",true);
                        partStateForward();
                    }
                    object.put("checkScore", score);
                    object.saveInBackground();
                    ParseUser user = ParseUser.getCurrentUser();
                    user.put(mPart+String.valueOf(mState),true);
                    user.saveInBackground();
                    Intent goToNextIntent = new Intent(getContext(), com.example.gushimakota.musico.ThankYouActivity.class);
                    startActivity(goToNextIntent);
                    getActivity().finish();
                }
            }
        });
    }

    private void partStateBack(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Part");
        query.getInBackground(mPartObjectId, new GetCallback<ParseObject>() {
            public void done(ParseObject part, ParseException e) {
                if (e == null) {
                    part.put("state", mState - 1);
                    part.saveInBackground();
                }
            }
        });
    }

    private void partStateForward(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Part");
        query.getInBackground(mPartObjectId, new GetCallback<ParseObject>() {
            public void done(ParseObject part, ParseException e) {
                if (e == null) {
                    part.put("state", mState + 1);
                    part.saveInBackground();
                }
            }
        });
    }
}