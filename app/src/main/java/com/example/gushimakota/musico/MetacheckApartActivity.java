package com.example.gushimakota.musico;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class MetacheckApartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metacheck_apart);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Track");
        query.whereEqualTo("part", "A");
//        query.whereEqualTo("check",false);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects != null) {

                    for (ParseObject object:objects) {

                        MetacheckFragment fragment = MetacheckFragment.newInstance("A", object.getObjectId());
                        FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.add(R.id.container_meta_a, fragment, "fragmentA");
                        transaction.commit();
                    }
                } else {
                    Intent intent1 = new Intent(MetacheckApartActivity.this, com.example.gushimakota.musico.SelectActivity.class);
                    startActivity(intent1);
                    finish();
                }
            }
        });
    }
}
