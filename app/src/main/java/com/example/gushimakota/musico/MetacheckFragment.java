package com.example.gushimakota.musico;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


public class MetacheckFragment extends Fragment {
    private static final String ARG_PART_NAME = "param5";
    private static final String ARG_OBJECT_ID = "param6";
    private static final String ARG_PART_ID = "param7";

    private String partName;
    private String trackId;
    private String partId;

    private ParseUser parseUser;

//    private TextView debugText;
    private Button vote;
    private Button play;

    private boolean checkPlay;

    private MetacheckListener mListener;

    public MetacheckFragment() {
        // Required empty public constructor
    }

    public interface MetacheckListener {
        public void onClickButton();
    }

    public static MetacheckFragment newInstance(String param1, String param2, String param3) {
        MetacheckFragment fragment = new MetacheckFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PART_NAME, param1);
        args.putString(ARG_OBJECT_ID, param2);
        args.putString(ARG_PART_ID, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            partName = getArguments().getString(ARG_PART_NAME);
            trackId = getArguments().getString(ARG_OBJECT_ID);
            partId= getArguments().getString(ARG_PART_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_metacheck, container, false);
        vote = (Button)v.findViewById(R.id.vote);
        play = (Button)v.findViewById(R.id.play_for_checking_meta);
        checkPlay = false;
        setMetacheckButtonAction();

        return v;
    }

    public void setMetacheckButtonAction(){
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (partName) {
                    case "A":
                        break;
                    case "B":
                        break;
                    case "C":
                        break;
                    default:
                        return;
                }
//                agree.setVisibility(View.VISIBLE);
                if (!checkPlay){
                    mListener.onClickButton();
                    checkPlay = true;
                }
            }
        });

        vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialogSendingVote();
            }
        });
    }

    private void confirmDialogSendingVote(){
        // 確認ダイアログの生成
        AlertDialog.Builder alertDlg = new AlertDialog.Builder(getContext());
        alertDlg.setTitle("このトラックに投票します");
        alertDlg.setMessage("よろしいですか？");
        alertDlg.setPositiveButton(
                "送信する",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // OK ボタンクリック処理
                        sendVoting();
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

    private void sendVoting(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Track");
        query.getInBackground(trackId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                int score = object.getInt("metaCheckScore");
                score = score + 1;
                if (score > 3) {
                    partStateForward();
                }
                object.put("metaCheckScore", score);
                object.saveInBackground();
                parseUser = ParseUser.getCurrentUser();
                parseUser.put(partName+"6", true);
                Intent goToNextIntent = new Intent(getContext(), com.example.gushimakota.musico.ThankYouActivity.class);
                startActivity(goToNextIntent);
                getActivity().finish();
            }
        });
    }

    private void partStateForward(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Part");
        query.getInBackground(partId, new GetCallback<ParseObject>() {
            public void done(ParseObject part, ParseException e) {
                if (e == null) {
                    int state = part.getInt("state");
                    part.put("state", state + 1);
                    part.saveInBackground();
                } else {
                    Toast.makeText(getContext(),"parse error",Toast.LENGTH_SHORT);
                }
            }
        });
    }

    // FragmentがActivityに追加されたら呼ばれるメソッド
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // APILevel23からは引数がActivity->Contextになっているので注意する

        // contextクラスがMyListenerを実装しているかをチェックする
        if (context instanceof MetacheckListener) {
            // リスナーをここでセットするようにします
            mListener = (MetacheckListener) context;
        }

    }

    // FragmentがActivityから離れたら呼ばれるメソッド
    @Override
    public void onDetach() {
        super.onDetach();
        // 画面からFragmentが離れたあとに処理が呼ばれることを避けるためにNullで初期化しておく
        mListener = null;
    }

    public void buttonOnTheVisible(){
        vote.setVisibility(View.VISIBLE);
    }
}