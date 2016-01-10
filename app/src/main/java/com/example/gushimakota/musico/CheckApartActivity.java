package com.example.gushimakota.musico;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class CheckApartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_apart);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Track");
        query.whereEqualTo("part", "A");
        query.whereEqualTo("check",false);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects != null){
                    ParseObject object = objects.get(0);
                    CheckFragment fragment = CheckFragment.newInstance("A",object.getObjectId());
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.add(R.id.container_check_a,fragment,"fragmentA");
                    transaction.commit();
                }else{
                    Intent intent1 = new Intent(CheckApartActivity.this, com.example.gushimakota.musico.SelectActivity.class);
                    startActivity(intent1);
                    finish();
                }
            }
        });
    }
}
