package com.example.chaitanya.realmdemo.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.example.chaitanya.realmdemo.Fragment.InsertFragment;
import com.example.chaitanya.realmdemo.Fragment.ViewFragment;
import com.example.chaitanya.realmdemo.R;
import com.example.chaitanya.realmdemo.Model.UserInfo;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    FrameLayout fragment_container;
    FragmentTransaction transaction;
    Button btnAdd, btnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(getApplicationContext());

        fragment_container = (FrameLayout) findViewById(R.id.fragment_container);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnView = (Button) findViewById(R.id.btnView);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertFragment();
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFragment();
             }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        Fragment insertFragment = new InsertFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, insertFragment).commit();
    }

    private void insertFragment(){
        Fragment insertFragment = new InsertFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, insertFragment).commit();
    }

    private void viewFragment(){
        Fragment viewFragment = new ViewFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, viewFragment).commit();
    }
}
