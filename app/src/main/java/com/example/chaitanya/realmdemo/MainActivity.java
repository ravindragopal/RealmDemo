package com.example.chaitanya.realmdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {


    EditText edtName, edtAge, edtMobile;
    RadioButton rdbActive, rdbInActive;
    Button btnSubmit,btnView;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);

        realm = Realm.getDefaultInstance();

        edtName = (EditText) findViewById(R.id.edtName);
        edtAge = (EditText) findViewById(R.id.edtAge);
        edtMobile = (EditText) findViewById(R.id.edtMobile);
        rdbActive = (RadioButton) findViewById(R.id.rdbActive);
        rdbInActive = (RadioButton) findViewById(R.id.rdbInActive);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnView = (Button) findViewById(R.id.btnView);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtName.getText().toString().isEmpty()) {
                    edtName.setError("invalid");
                    return;
                }
                if (edtAge.getText().toString().isEmpty()) {
                    edtAge.setError("invalid");
                    return;
                }
                if (edtMobile.getText().toString().isEmpty() || edtMobile.getText().toString().length() < 10) {
                    edtName.setError("invalid");
                    return;
                }

                realm.beginTransaction();
                UserInfo userInfo = realm.createObject(UserInfo.class);
                userInfo.setName(edtName.getText().toString());
                userInfo.setAge(Integer.parseInt(edtAge.getText().toString()));
                userInfo.setMobile(Integer.parseInt(edtMobile.getText().toString()));
                if(rdbActive.isChecked()){
                    userInfo.setStatus(true);
                }else {
                    userInfo.setStatus(false);
                }
                realm.commitTransaction();

                edtName.setText("");
                edtAge.setText("");
                edtMobile.setText("");
                rdbActive.setChecked(true);

            }
        });


        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RealmResults<UserInfo> userInfos = realm.where(UserInfo.class)
                        .findAll();

                edtName.setText(userInfos.get(0).getName());
                edtAge.setText(""+userInfos.get(0).getAge());
                edtMobile.setText(""+userInfos.get(0).getMobile());
                if(userInfos.get(0).isStatus()){
                    rdbActive.setChecked(true);
                }else {
                    rdbInActive.setChecked(true);
                }

            }
        });

    }
}
