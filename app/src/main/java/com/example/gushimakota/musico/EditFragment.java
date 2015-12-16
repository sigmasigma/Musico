package com.example.gushimakota.musico;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


public class EditFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String APARTID = "exofgfV3QJ";
    private static final String BPARTID = "fc1U1VvuGq";
    private static final String CPARTID = "vCPCvVv5Z6";

    private String mParam1;
    private String mParam2;

    private int idea=0;

    private TextView title;
    private Button play;
    private Button register;

    public EditFragment() {
        // Required empty public constructor
    }

    public static EditFragment newInstance(String param1, String param2) {
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit, container, false);
        setSpinners(v);
        title = (TextView)v.findViewById(R.id.titleText);
        title.setText(mParam1+" part");
        setButtonAction(v);
        return v;
    }

    public void setSpinners(View v){

        //spinnerの処理
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), R.layout.spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // アイテム追加
        adapter1.add("なし");
        adapter1.add("4つ打");
        adapter1.add("2分打");
        adapter1.add("ドンドド");
        adapter1.add("ドンドッド");
        adapter1.add("ドンドッド２");
        Spinner  kick= (Spinner) v.findViewById(R.id.kick_spin);
        // アダプター設定
        kick.setAdapter(adapter1);
        // スピナーのアイテムが選択された時に呼び出されるコールバックリスナーを登録します
        kick.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //spinnerの処理
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), R.layout.spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // アイテム追加
        adapter2.add("なし");
        adapter2.add("頭");
        adapter2.add("2拍4拍");
        adapter2.add("3拍");
        Spinner  snare= (Spinner) v.findViewById(R.id.snare_spin);
        // アダプター設定
        snare.setAdapter(adapter2);
        // スピナーのアイテムが選択された時に呼び出されるコールバックリスナーを登録します
        snare.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //spinnerの処理
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getContext(), R.layout.spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // アイテム追加
        adapter3.add("なし");
        adapter3.add("クローズ4分");
        adapter3.add("クローズ8分");
        adapter3.add("クローズ16分");
        adapter3.add("オープン4分");
        adapter3.add("オープン8分");
        adapter3.add("オープン16分");
        adapter3.add("クローズ裏打ち");
        adapter3.add("オープン裏打ち");

        Spinner hihat= (Spinner) v.findViewById(R.id.hihat_spin);
        // アダプター設定
        hihat.setAdapter(adapter3);
        // スピナーのアイテムが選択された時に呼び出されるコールバックリスナーを登録します
        hihat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //spinnerの処理
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(getContext(), R.layout.spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // アイテム追加
        adapter4.add("なし");
        adapter4.add("カノン進行");
        adapter4.add("6451進行");
        adapter4.add("456進行");
        adapter4.add("6365進行");
        Spinner chord= (Spinner) v.findViewById(R.id.chord);
        // アダプター設定
        chord.setAdapter(adapter4);
        // スピナーのアイテムが選択された時に呼び出されるコールバックリスナーを登録します
        chord.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //spinnerの処理
        ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(getContext(), R.layout.spinner_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // アイテム追加
        adapter5.add("全音");
        adapter5.add("ノリ良い");
        Spinner pattern= (Spinner) v.findViewById(R.id.pattern);
        // アダプター設定
        pattern.setAdapter(adapter5);
        // スピナーのアイテムが選択された時に呼び出されるコールバックリスナーを登録します
        pattern.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    public void setButtonAction(View v){
        play = (Button)v.findViewById(R.id.play);
        register = (Button)v.findViewById(R.id.register);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mParam1){
                    case "A":

                        break;
                    case "B":

                        break;
                    case "C":

                        break;
                    default:
                        return;
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void showDialog(){
        // 確認ダイアログの生成
        AlertDialog.Builder alertDlg = new AlertDialog.Builder(getContext());
        alertDlg.setTitle("この内容で送信します");
        alertDlg.setMessage("よろしいですか？");
        alertDlg.setPositiveButton(
                "送信する",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // OK ボタンクリック処理
                        setParses();
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

    private void setParses(){
        switch (mParam1){
            case "A":
                ParseObject aPartSet = new ParseObject("ApartSet");
                aPartSet.put("idea",idea);
                aPartSet.saveInBackground();

                ParseQuery<ParseObject> queryA = ParseQuery.getQuery("Part");
                queryA.getInBackground(APARTID, new GetCallback<ParseObject>() {
                    public void done(ParseObject aPart, ParseException e) {
                        if (e == null) {
                            // Now let's update it with some new data. In this case, only cheatMode and score
                            // will get sent to the Parse Cloud. playerName hasn't changed.
                            int state = aPart.getInt("state");
                            if (state == 0){
                                aPart.put("state", 1);
                                aPart.saveInBackground();
                            }
                        }
                    }
                });
                break;
            case "B":

                break;
            case "C":

                break;
            default:
                return;
        }
    }

}
