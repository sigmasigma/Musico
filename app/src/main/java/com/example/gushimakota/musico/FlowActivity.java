package com.example.gushimakota.musico;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FlowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow);

    }

    public void onClickFlow(View v){
        Intent intent = new Intent(FlowActivity.this, com.example.gushimakota.musico.SelectActivity.class);
        finish();
        startActivity(intent);
    }
}
