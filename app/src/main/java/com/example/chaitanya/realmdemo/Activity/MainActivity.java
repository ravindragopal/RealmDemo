package com.example.chaitanya.realmdemo.Activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.chaitanya.realmdemo.Adapter.UserInfoAdapter;
import com.example.chaitanya.realmdemo.Fragment.InsertFragment;
import com.example.chaitanya.realmdemo.Fragment.ViewFragment;
import com.example.chaitanya.realmdemo.Model.HobbiesModel;
import com.example.chaitanya.realmdemo.R;
import com.example.chaitanya.realmdemo.Model.UserInfo;

import java.lang.reflect.GenericArrayType;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity{

    /*FrameLayout fragment_container;
    FragmentTransaction transaction;
    Button btnAdd, btnView;*/

    EditText edtName, edtAge, edtMobile, edtEmail;
    RadioButton rdbActive, rdbInActive;
    Button btnSubmit, btnUpdate, btnView;
    Spinner spBloodGroup;
    CheckBox ckbReading, ckbWriting, ckbDrawing;
    HobbiesModel hobbies = new HobbiesModel();
    RealmList<HobbiesModel> hobbiesModelRealmList = new RealmList<>();
    ArrayAdapter arrayAdapter;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(getApplicationContext());


        /*fragment_container = (FrameLayout) findViewById(R.id.fragment_container);
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
        });*/


        realm = Realm.getDefaultInstance();
        edtName = (EditText) findViewById(R.id.edtName);
        edtAge = (EditText) findViewById(R.id.edtAge);
        edtMobile = (EditText) findViewById(R.id.edtMobile);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        rdbActive = (RadioButton) findViewById(R.id.rdbActive);
        rdbInActive = (RadioButton) findViewById(R.id.rdbInActive);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnView = (Button) findViewById(R.id.btnView);
        spBloodGroup = (Spinner) findViewById(R.id.spBloodGroup);
        ckbReading = (CheckBox) findViewById(R.id.ckbReading);
        ckbWriting = (CheckBox) findViewById(R.id.ckbWriting);
        ckbDrawing = (CheckBox) findViewById(R.id.ckbDrawing);
        ckbReading.setOnClickListener(onCheckboxClicked);
        ckbWriting.setOnClickListener(onCheckboxClicked);
        ckbDrawing.setOnClickListener(onCheckboxClicked);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
            }
        });

        /*btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
            }
        });*/

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ViewDataActivity.class);
                startActivity(intent);
                finish();
            }
        });

        arrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.bloodgroup));
    }


    View.OnClickListener onCheckboxClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            boolean checked = ((CheckBox) view).isChecked();
            switch (view.getId()) {
                case R.id.ckbReading:
                    if (checked) {
                        hobbies.setReading("Reading");
                    } else {
                        hobbies.setReading("");
                    }
                    break;
                case R.id.ckbWriting:
                    if (checked) {
                        hobbies.setWriting("Writing");
                    } else {
                        hobbies.setWriting("");
                    }
                    break;
                case R.id.ckbDrawing:
                    if (checked) {
                        hobbies.setDrawing("Drawing");
                    } else {
                        hobbies.setDrawing("");
                    }
                    break;
            }
        }
    };

   /* private void updateData() {
        UserInfo userInfo = new UserInfo();
        userInfo.setName(edtName.getText().toString());
        userInfo.setAge(Integer.parseInt(edtAge.getText().toString()));
        userInfo.setMobile(edtMobile.getText().toString());
        userInfo.setEmail(edtEmail.getText().toString());
        userInfo.setBloodgroup(spBloodGroup.getSelectedItem().toString());
        if (rdbActive.isChecked()) {
            userInfo.setStatus(true);
        } else {
            userInfo.setStatus(false);
        }

        realm.beginTransaction();
        UserInfo realmUser = realm.copyToRealmOrUpdate(userInfo);
        realm.commitTransaction();

        edtName.setText("");
        edtAge.setText("");
        edtMobile.setText("");
        edtEmail.setText("");
        spBloodGroup.setSelection(0);
        ckbReading.setChecked(false);
        ckbWriting.setChecked(false);
        ckbDrawing.setChecked(false);
        rdbActive.setChecked(true);
    }*/

    private void insertData() {
        try {

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
            if (!isValidEmail(edtEmail.getText().toString())) {
                edtEmail.setError("invalid");
                return;
            }
            if (spBloodGroup.getSelectedItem().toString().equalsIgnoreCase("Select")) {
                Toast.makeText(getApplicationContext(), "Select BloodGroup", Toast.LENGTH_LONG).show();
                return;
            }

            UserInfo userInfo = new UserInfo();
            userInfo.setName(edtName.getText().toString());
            userInfo.setAge(Integer.parseInt(edtAge.getText().toString()));
            userInfo.setMobile(edtMobile.getText().toString());
            userInfo.setEmail(edtEmail.getText().toString());
            userInfo.setBloodgroup(spBloodGroup.getSelectedItem().toString());
            hobbiesModelRealmList.clear();
            hobbiesModelRealmList.add(hobbies);
            userInfo.setHobbies(hobbiesModelRealmList);
            if (rdbActive.isChecked()) {
                userInfo.setStatus(true);
            } else {
                userInfo.setStatus(false);
            }

            realm.beginTransaction();
            UserInfo realmUser = realm.copyToRealmOrUpdate(userInfo);
            realm.commitTransaction();

            edtName.setText("");
            edtAge.setText("");
            edtMobile.setText("");
            edtEmail.setText("");
            spBloodGroup.setSelection(0);
            ckbReading.setChecked(false);
            ckbWriting.setChecked(false);
            ckbDrawing.setChecked(false);
            rdbActive.setChecked(true);

            Intent intent = new Intent(getApplicationContext(), ViewDataActivity.class);
            startActivity(intent);
            finish();

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            Intent intent = getIntent();
            String name = intent.getStringExtra("name");
            if (!name.isEmpty() && !name.equals(null)) {
                showData(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void showData(final String name) {
        RealmResults<UserInfo> userInfos = realm.where(UserInfo.class)
                .equalTo("name", name)
                .findAll();

        edtName.setText(userInfos.get(0).getName());
        edtAge.setText("" + userInfos.get(0).getAge());
        edtMobile.setText("" + userInfos.get(0).getMobile());
        edtEmail.setText("" + userInfos.get(0).getEmail());

        int spinnerPosition = arrayAdapter.getPosition(userInfos.get(0).getBloodgroup());
        spBloodGroup.setSelection(spinnerPosition);

        hobbiesModelRealmList = userInfos.get(0).getHobbies();

        if (hobbiesModelRealmList.get(0).getReading().equalsIgnoreCase("Reading")) {
            ckbReading.setChecked(true);
        }
        if (hobbiesModelRealmList.get(0).getWriting().equalsIgnoreCase("Writing")) {
            ckbWriting.setChecked(true);
        }
        if (hobbiesModelRealmList.get(0).getDrawing().equalsIgnoreCase("Drawing")) {
            ckbDrawing.setChecked(true);
        }

        if (userInfos.get(0).isStatus()) {
            rdbActive.setChecked(true);
        } else {
            rdbInActive.setChecked(true);
        }
    }


//    @Override
//    public void onShow(String name) {
//        showData(name);
//    }

  /*  @Override
    protected void onStart() {
        super.onStart();

        Fragment insertFragment = new InsertFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, insertFragment).commit();
    }

    private void insertFragment() {
        Fragment insertFragment = new InsertFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, insertFragment).commit();
    }

    private void viewFragment() {
        Fragment viewFragment = new ViewFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, viewFragment).commit();
    }*/
}
