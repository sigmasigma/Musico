package com.example.gushimakota.musico;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EditApartActivity extends AppCompatActivity {

    private static final String APARTID = "exofgfV3QJ";
    private static final String BPARTID = "fc1U1VvuGq";
    private static final String CPARTID = "vCPCvVv5Z6";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_a_part);

        EditFragment fragment = EditFragment.newInstance("A","");
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.container_edit,fragment,"fragmentA");
        transaction.commit();
    }
}
