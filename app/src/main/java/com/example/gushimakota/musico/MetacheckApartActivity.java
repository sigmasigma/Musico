package com.example.gushimakota.musico;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class MetacheckApartActivity extends AppCompatActivity  implements MetacheckFragment.MetacheckListener{


    private static final String APART_ID = "exofgfV3QJ";
    private static final String BPART_ID = "fc1U1VvuGq";
    private static final String CPART_ID = "vCPCvVv5Z6";

    private int count;
    private int checkNum;
    private int maxMetaScore;

    private List<ParseObject> trackObjects;

    private Button allNogoodButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metacheck_apart);
        allNogoodButton = (Button)findViewById(R.id.button_meta_a);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Track");
        query.whereEqualTo("part", "A");
//        query.whereEqualTo("check",false);

        count = 0;
        checkNum = 0;
        maxMetaScore = -4;
        setTrackFragments(query);
        setNoGoodButton();

    }

    // interface内のメソッドを実装します。
    @Override
    public void onClickButton() {
        checkNum++;
        if (checkNum == count){
            for (int c=0;c<count;c++){
                Fragment fragment  = getSupportFragmentManager().findFragmentByTag("fragmentA"+String.valueOf(c));
                if (fragment != null && fragment instanceof MetacheckFragment) {
                    ((MetacheckFragment) fragment).buttonOnTheVisible();
                }
            }
            allNogoodButton.setVisibility(View.VISIBLE);
        }
    }

    private void setNoGoodButton(){
        allNogoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialogSendingVote();
            }
        });
    }

    private void sendDisagree(){
        int score;
        for(ParseObject track:trackObjects){
            score = track.getInt("metaCheckScore");
            score-=1;
            track.put("metaCheckScore", score);
            track.saveInBackground();
            if (maxMetaScore<score){
                maxMetaScore = score;
            }
        }
        if (maxMetaScore<-1){
            partStateBackToZero();
        }

        Intent goToNextIntent = new Intent(MetacheckApartActivity.this, com.example.gushimakota.musico.ThankYouActivity.class);
        startActivity(goToNextIntent);
        finish();
    }

    private void confirmDialogSendingVote(){
        // 確認ダイアログの生成
        AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
        alertDlg.setTitle("このトラックに投票します");
        alertDlg.setMessage("よろしいですか？");
        alertDlg.setPositiveButton(
                "送信する",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // OK ボタンクリック処理
                        sendDisagree();
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

    private void setTrackFragments(ParseQuery<ParseObject> query){

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects != null) {
                    trackObjects = objects;
                    for (ParseObject object:objects) {
                        MetacheckFragment fragment = MetacheckFragment.newInstance("A", object.getObjectId(),APART_ID);
                        FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.add(R.id.container_meta_a, fragment, "fragmentA"+String.valueOf(count));
                        transaction.commit();
                        count++;
                    }
                } else {
                    Intent intent1 = new Intent(MetacheckApartActivity.this, com.example.gushimakota.musico.SelectActivity.class);
                    startActivity(intent1);
                    finish();
                }
            }
        });

    }

    private void partStateBackToZero(){
        for(ParseObject track:trackObjects){
            track.deleteInBackground();
        }
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Part");
        query.getInBackground(APART_ID, new GetCallback<ParseObject>() {
            public void done(ParseObject part, ParseException e) {
                if (e == null) {
                    part.put("state", 0);
                    part.saveInBackground();
                }
            }
        });
    }
}
