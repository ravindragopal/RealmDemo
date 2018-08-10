package com.example.chaitanya.realmdemo.Fragment;


import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.chaitanya.realmdemo.Activity.ViewDataActivity;
import com.example.chaitanya.realmdemo.Adapter.UserInfoAdapter;
import com.example.chaitanya.realmdemo.Model.HobbiesModel;
import com.example.chaitanya.realmdemo.Model.UserInfo;
import com.example.chaitanya.realmdemo.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddDataFragment extends Fragment {

    View view;
    EditText edtName, edtAge, edtMobile, edtEmail, edtDOB;
    RadioButton rdbActive, rdbInActive;
    Button btnSubmit, btnUpdate, btnView;
    Spinner spBloodGroup;
    CheckBox ckbReading, ckbWriting, ckbDrawing;
    SearchView searchView;
    HobbiesModel hobbies = new HobbiesModel();
    RealmList<HobbiesModel> hobbiesModelRealmList = new RealmList<>();
    ArrayAdapter arrayAdapter;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    Realm realm;

    public AddDataFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("@", "onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("@", "onCreate");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_insert, container, false);
        Log.d("@", "onCreateView");
        realm = Realm.getDefaultInstance();

        intilization();

        /*try {
            Intent intent = getActivity().getIntent();
            String name = intent.getStringExtra("name");
            if (!name.isEmpty() && !name.equals(null)) {
                showData(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        Log.d("@", "onCreate");

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("@", "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("@", "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("@", "onResume");
//        Add add = new Add(this);
//        Remove remove = new Remove(this);
//        add.start();
//        remove.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("@", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("@", "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("@", "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("@", "onDestroy");
        realm.close();
    }

    @Override
    public void onDetach() {
        Log.d("@", "onDetach");
        super.onDetach();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d("@", "onRestoreInstanceState");
        if (savedInstanceState != null) {
            edtName.setText(savedInstanceState.getString("edtName"));
            edtAge.setText(savedInstanceState.getString("edtAge"));
            edtMobile.setText(savedInstanceState.getString("edtMobile"));
            edtEmail.setText(savedInstanceState.getString("edtEmail"));
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d("@", "onSaveInstanceState");
        outState.putString("edtName", edtName.getText().toString());
        outState.putString("edtAge", edtAge.getText().toString());
        outState.putString("edtMobile", edtMobile.getText().toString());
        outState.putString("edtEmail", edtEmail.getText().toString());
        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(getActivity(), "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(getActivity(), "portrait", Toast.LENGTH_SHORT).show();
        }
    }


    private void intilization() {

        arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.bloodgroup));

        searchView = (SearchView) view.findViewById(R.id.searchView);
        edtName = (EditText) view.findViewById(R.id.edtName);
        edtAge = (EditText) view.findViewById(R.id.edtAge);
        edtMobile = (EditText) view.findViewById(R.id.edtMobile);
        edtEmail = (EditText) view.findViewById(R.id.edtEmail);
        edtDOB = (EditText) view.findViewById(R.id.edtDOB);
        rdbActive = (RadioButton) view.findViewById(R.id.rdbActive);
        rdbInActive = (RadioButton) view.findViewById(R.id.rdbInActive);
        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
        btnUpdate = (Button) view.findViewById(R.id.btnUpdate);
        btnView = (Button) view.findViewById(R.id.btnView);
        spBloodGroup = (Spinner) view.findViewById(R.id.spBloodGroup);
        ckbReading = (CheckBox) view.findViewById(R.id.ckbReading);
        ckbWriting = (CheckBox) view.findViewById(R.id.ckbWriting);
        ckbDrawing = (CheckBox) view.findViewById(R.id.ckbDrawing);
        ckbReading.setOnClickListener(onCheckboxClicked);
        ckbWriting.setOnClickListener(onCheckboxClicked);
        ckbDrawing.setOnClickListener(onCheckboxClicked);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
               /* FragmentManager fragmentManager = getActivity().getFragmentManager();
                fragmentManager.popBackStack();*/
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ViewDataActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        edtDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateTimeField();
            }
        });
    }


    private void setDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog fromDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                edtDOB.setText(dateFormat.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        fromDatePickerDialog.show();
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
            if (edtDOB.getText().toString().isEmpty()) {
                edtDOB.setError("invalid");
                return;
            }
            if (spBloodGroup.getSelectedItem().toString().equalsIgnoreCase("Select")) {
                Toast.makeText(getActivity(), "Select BloodGroup", Toast.LENGTH_LONG).show();
                return;
            }

            Number currentIdNum = realm.where(UserInfo.class).max("id");
            int nextId;
            if (currentIdNum == null) {
                nextId = 1;
            } else {
                nextId = currentIdNum.intValue() + 1;
            }

            UserInfo userInfo = new UserInfo();
            userInfo.setId(nextId);
            userInfo.setName(edtName.getText().toString());
            userInfo.setAge(Integer.parseInt(edtAge.getText().toString()));
            userInfo.setMobile(edtMobile.getText().toString());
            userInfo.setEmail(edtEmail.getText().toString());
            userInfo.setDate(dateFormat.parse(edtDOB.getText().toString()));
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

            /*Intent intent = new Intent(getApplicationContext(), ViewDataActivity.class);
            startActivity(intent);*/
            getActivity().finish();

        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    private void showData(final String name) {
        RealmResults<UserInfo> userInfos = realm.where(UserInfo.class)
                .equalTo("name", name)
                .findAll();

        edtName.setText(userInfos.get(0).getName());
        edtAge.setText("" + userInfos.get(0).getAge());
        edtMobile.setText("" + userInfos.get(0).getMobile());
        edtEmail.setText("" + userInfos.get(0).getEmail());
        edtDOB.setText("" + dateFormat.format(userInfos.get(0).getDate()));

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


}
