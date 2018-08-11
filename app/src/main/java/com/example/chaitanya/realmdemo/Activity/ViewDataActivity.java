package com.example.chaitanya.realmdemo.Activity;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;

import com.example.chaitanya.realmdemo.Adapter.UserInfoAdapter;
import com.example.chaitanya.realmdemo.Adapter.UserRecyclerViewAdapter;
import com.example.chaitanya.realmdemo.Fragment.AddDataFragment;
import com.example.chaitanya.realmdemo.Fragment.ViewDataFragment;
import com.example.chaitanya.realmdemo.Model.UserInfo;
import com.example.chaitanya.realmdemo.R;
import com.example.chaitanya.realmdemo.Retrofit.RetroPhoto;
import com.example.chaitanya.realmdemo.Thread.Add;
import com.example.chaitanya.realmdemo.WorkManager.TestWorkerA;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkStatus;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

public class ViewDataActivity extends AppCompatActivity implements ViewDataFragment.OnCallFragment {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    UserInfoAdapter userInfoAdapter;
    UserRecyclerViewAdapter userRecyclerViewAdapter;
    ArrayList<UserInfo> userInfoArrayList = new ArrayList<>();
    Realm realm;
    SearchView searchView;
    ImageView imgView, imgAdd;
    public RealmResults<UserInfo> userInfos;

    RealmChangeListener<RealmResults<UserInfo>> listener = new RealmChangeListener<RealmResults<UserInfo>>() {
        @Override
        public void onChange(RealmResults<UserInfo> UserInfo) {
            if (UserInfo.isLoaded()) {
                userRecyclerViewAdapter.notifyDataSetChanged();
            }
        }
    };


    int contents;
    int nextId;

    OneTimeWorkRequest oneTimeWorkRequest;
    PeriodicWorkRequest periodicWorkRequest;

    FragmentTransaction fragmentTransaction;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        Log.d("V@", "onCreate");
        realm = Realm.getDefaultInstance();

        initilization();

        userInfos = realm.where(UserInfo.class).findAllAsync();
        userInfos.addChangeListener(listener);
        showData();

        /*ScheduleJob.scheduleJob(getApplicationContext());

        Fragment fragmentView = new ViewDataFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.framLayout, fragmentView);
        fragmentTransaction.commit();*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("V@", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("V@", "onResume");
        super.onResume();
//
//        Add add = new Add(this);
//        Remove remove = new Remove(this);
//        add.start();
//        remove.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("V@", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("V@", "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("V@", "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("V@", "onDestroy");
    }

    public synchronized void add(int value) {

        Realm realm = Realm.getDefaultInstance();
        Number currentIdNum = realm.where(UserInfo.class).max("id");
        if (currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue() + 1;
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setId(nextId);
        userInfo.setName("ttt" + nextId);
        userInfo.setAge(11);
        userInfo.setMobile("1234567890");
        userInfo.setEmail("w@gmail.com");
//        userInfo.setDate(dateFormat.parse(edtDOB.getText().toString()));
//        userInfo.setBloodgroup(spBloodGroup.getSelectedItem().toString());
        userInfo.setStatus(true);

        realm.beginTransaction();
        UserInfo realmUser = realm.copyToRealmOrUpdate(userInfo);
        realm.commitTransaction();

        contents = value;
    }

    public synchronized int remove() {
        Realm realm = Realm.getDefaultInstance();

        final RealmResults<UserInfo> results = realm.where(UserInfo.class).findAll();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                UserInfo userInfo = results.last();
                userInfo.deleteFromRealm();
            }
        });

        return contents;
    }

    private void initilization() {

        searchView = (SearchView) findViewById(R.id.searchView);
        imgView = (ImageView) findViewById(R.id.imgView);
        imgAdd = (ImageView) findViewById(R.id.imgAdd);
//        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(llm);

       /* swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                userInfos = realm.where(UserInfo.class).findAll();
                showData(userInfos);
                swipeRefreshLayout.setRefreshing(false);
            }
        });*/

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (query.isEmpty() || query.equals(null)) {

                    userInfos = realm.where(UserInfo.class).findAllAsync();
                    userRecyclerViewAdapter.updateData(userInfos);
//                    showData(userInfos);

                } else {

                    userInfos = realm.where(UserInfo.class)
                            .like("name", "*" + query + "*", Case.INSENSITIVE)
                            .findAllAsync().sort("age");
                    userRecyclerViewAdapter.updateData(userInfos);
//                    showData(userInfos);
                }
                return false;
            }
        });

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*shortData();*/
            }
        });

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewDataActivity.this, AddDataActivity.class);
                intent.putExtra("name", "");
                startActivity(intent);
            }
        });
    }

    private void shortData() {

        PopupMenu popup = new PopupMenu(this, imgView);
        popup.inflate(R.menu.sort_menu);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.age:
                        userInfos = realm.where(UserInfo.class)
                                .findAllAsync().sort("age");
                        userRecyclerViewAdapter.updateData(userInfos);
//                        showData(userInfos);
                        break;
                    case R.id.dob:
                        userInfos = realm.where(UserInfo.class)
                                .findAllAsync().sort("date", Sort.ASCENDING);
                        userRecyclerViewAdapter.updateData(userInfos);
//                        showData(userInfos);
                        break;
                }
                return false;
            }
        });
        popup.show();
    }

    private void showData() {
        try {
            userRecyclerViewAdapter = new UserRecyclerViewAdapter(userInfos, ViewDataActivity.this);
            recyclerView.setAdapter(userRecyclerViewAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClickAddButton() {

//        fragmentTransaction.remove(fragment);
//        Fragment fragmentAdd1 = new ViewDataFragment();
        Fragment fragmentAdd = new AddDataFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //*fragmentTransaction.detach(fragmentAdd1);
//        fragmentTransaction.attach(fragmentAdd1);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();*//*
        fragmentTransaction.replace(R.id.framLayout, fragmentAdd);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


        /*fragmentTransaction.detach(fragmentAdd1);
        fragmentTransaction.commitNow();
   fragmentTransaction.attach(fragmentAdd);
        fragmentTransaction.commitNow();*/

    }

}
