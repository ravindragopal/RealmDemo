package com.example.chaitanya.realmdemo.Activity;

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
import com.example.chaitanya.realmdemo.Model.UserInfo;
import com.example.chaitanya.realmdemo.R;

import java.util.ArrayList;

import io.realm.Case;
import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmObjectChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

public class ViewDataActivity extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    UserInfoAdapter userInfoAdapter;
    UserRecyclerViewAdapter userRecyclerViewAdapter;
    ArrayList<UserInfo> userInfoArrayList = new ArrayList<>();
    Realm realm;
    SearchView searchView;
    ImageView imgView;
    public RealmResults<UserInfo> userInfos;

    RealmChangeListener<RealmResults<UserInfo>> listener = new RealmChangeListener<RealmResults<UserInfo>>() {
        @Override
        public void onChange(RealmResults<UserInfo> UserInfo) {
            if(UserInfo.isLoaded()) {
                userRecyclerViewAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        realm = Realm.getDefaultInstance();

        initilization();

        userInfos = realm.where(UserInfo.class).findAllAsync();
        userInfos.addChangeListener(listener);
        showData();

//        userInfos.addChangeListener(callback);
//        Log.d("@@","onCreate");

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userInfos.removeAllChangeListeners();
    }

  /*  private OrderedRealmCollectionChangeListener<RealmResults<UserInfo>> callback = new OrderedRealmCollectionChangeListener<RealmResults<UserInfo>>() {
        @Override
        public void onChange(RealmResults<UserInfo> userInfos, OrderedCollectionChangeSet changeSet) {
            if (userInfos.isLoaded()) {
//                showData(userInfos);
                Log.d("@@",userInfos.toString());
            }
        }
    };*/

    private void initilization() {

        searchView = (SearchView) findViewById(R.id.searchView);
        imgView = (ImageView) findViewById(R.id.imgView);
//        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
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
                shortData();
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

}
