package com.example.chaitanya.realmdemo.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.chaitanya.realmdemo.Adapter.UserInfoAdapter;
import com.example.chaitanya.realmdemo.Model.HobbiesModel;
import com.example.chaitanya.realmdemo.Model.UserInfo;
import com.example.chaitanya.realmdemo.R;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class InsertFragment extends Fragment {

    View view;
    EditText edtName, edtAge, edtMobile, edtEmail;
    RadioButton rdbActive, rdbInActive;
    Button btnSubmit, btnUpdate;
    Spinner spBloodGroup;
    CheckBox ckbReading, ckbWriting, ckbDrawing;
    HobbiesModel hobbies = new HobbiesModel();
    RealmList<HobbiesModel> hobbiesModelRealmList = new RealmList<>();
    String name;


    Realm realm;

    public InsertFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_insert, container, false);
        realm = Realm.getDefaultInstance();
        edtName = (EditText) view.findViewById(R.id.edtName);
        edtAge = (EditText) view.findViewById(R.id.edtAge);
        edtMobile = (EditText) view.findViewById(R.id.edtMobile);
        edtEmail = (EditText) view.findViewById(R.id.edtEmail);
        rdbActive = (RadioButton) view.findViewById(R.id.rdbActive);
        rdbInActive = (RadioButton) view.findViewById(R.id.rdbInActive);
        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
        btnUpdate = (Button) view.findViewById(R.id.btnUpdate);
        spBloodGroup = (Spinner) view.findViewById(R.id.spBloodGroup);
        ckbReading = (CheckBox) view.findViewById(R.id.ckbReading);
        ckbWriting = (CheckBox) view.findViewById(R.id.ckbWriting);
        ckbDrawing = (CheckBox) view.findViewById(R.id.ckbDrawing);
        ckbReading.setOnClickListener(onCheckboxClicked);
        ckbWriting.setOnClickListener(onCheckboxClicked);
        ckbDrawing.setOnClickListener(onCheckboxClicked);

        try {
            name = getArguments().getString("name");
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            if (!name.isEmpty() && !name.equals(null)) {
                showData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private void showData() {
        RealmResults<UserInfo> userInfos = realm.where(UserInfo.class)
                .equalTo("name", name)
                .findAll();
        edtName.setText(userInfos.get(0).getName());
        edtAge.setText("" + userInfos.get(0).getAge());
        edtMobile.setText("" + userInfos.get(0).getMobile());
        if (userInfos.get(0).isStatus()) {
            rdbActive.setChecked(true);
        } else {
            rdbInActive.setChecked(true);
        }
    }

    private void updateData() {
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
    }

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
                Toast.makeText(getActivity(), "Select BloodGroup", Toast.LENGTH_LONG).show();
                return;
            }

           /* realm.beginTransaction();
            UserInfo userInfo = realm.createObject(UserInfo.class,UUID.randomUUID().toString());
            userInfo.setName(edtName.getText().toString());
            userInfo.setAge(Integer.parseInt(edtAge.getText().toString()));
            userInfo.setMobile(edtMobile.getText().toString());
            userInfo.setEmail(edtEmail.getText().toString());
            userInfo.setBloodgroup(spBloodGroup.getSelectedItem().toString());
            *//*hobbiesModelRealmList.add(hobbies);
            userInfo.setHobbies(hobbiesModelRealmList);*//*
            if (rdbActive.isChecked()) {
                userInfo.setStatus(true);
            } else {
                userInfo.setStatus(false);
            }*/

            UserInfo userInfo = new UserInfo();
            userInfo.setName(edtName.getText().toString());
            userInfo.setAge(Integer.parseInt(edtAge.getText().toString()));
            userInfo.setMobile(edtMobile.getText().toString());
            userInfo.setEmail(edtEmail.getText().toString());
            userInfo.setBloodgroup(spBloodGroup.getSelectedItem().toString());
            /*hobbiesModelRealmList.add(hobbies);
            userInfo.setHobbies(hobbiesModelRealmList);*/
            if (rdbActive.isChecked()) {
                userInfo.setStatus(true);
            } else {
                userInfo.setStatus(false);
            }

            realm.beginTransaction();
            UserInfo realmUser = realm.copyToRealm(userInfo);
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

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

}
