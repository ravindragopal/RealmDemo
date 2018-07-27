package com.example.chaitanya.realmdemo.Activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;

import com.example.chaitanya.realmdemo.Adapter.UserInfoAdapter;
import com.example.chaitanya.realmdemo.Model.UserInfo;
import com.example.chaitanya.realmdemo.R;

import java.util.ArrayList;

import io.realm.Case;
import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class ViewDataActivity extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    UserInfoAdapter userInfoAdapter;
    ArrayList<UserInfo> userInfoArrayList = new ArrayList<>();
    Realm realm;
    SearchView searchView;
    ImageView imgView;
    RealmResults<UserInfo> userInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        realm = Realm.getDefaultInstance();

        initilization();
    }

    private void initilization() {
        searchView = (SearchView) findViewById(R.id.searchView);
        imgView = (ImageView) findViewById(R.id.imgView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        /*userInfos = realm.where(UserInfo.class).findAll();
        showData(userInfos);*/

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                userInfos = realm.where(UserInfo.class).findAll();
                showData(userInfos);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                /*if (query.isEmpty() || query.equals(null)) {

                    RealmResults<UserInfo> userInfos = realm.where(UserInfo.class).findAll();
                    showData(userInfos);

                } else {

                    RealmResults<UserInfo> userInfos = realm.where(UserInfo.class)
                            .like("name", query + "*", Case.INSENSITIVE)
                            .findAll();
                    showData(userInfos);
                }*/
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (query.isEmpty() || query.equals(null)) {

                    RealmResults<UserInfo> userInfos = realm.where(UserInfo.class).findAll();
                    showData(userInfos);

                } else {

                    userInfos = realm.where(UserInfo.class)
                            .like("name", "*" + query + "*", Case.INSENSITIVE)
                            .findAll().sort("age");
                    showData(userInfos);
                }
                return false;
            }
        });

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void showDialog() {
        PopupMenu popup = new PopupMenu(this, imgView);
        popup.inflate(R.menu.sort_menu);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.age:
                        userInfos = realm.where(UserInfo.class)
                                .findAll().sort("age");
                        showData(userInfos);
                        break;
                    case R.id.dob:
                        userInfos = realm.where(UserInfo.class)
                                .findAll().sort("date", Sort.ASCENDING);
                        showData(userInfos);
                        break;
                }
                return false;
            }
        });
        popup.show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        userInfos = realm.where(UserInfo.class).findAllAsync();
        userInfos.addChangeListener(callback);
    }

    @Override
    protected void onStop() {
        super.onStop();
        userInfos.removeAllChangeListeners();
    }

    private OrderedRealmCollectionChangeListener<RealmResults<UserInfo>> callback = new OrderedRealmCollectionChangeListener<RealmResults<UserInfo>>() {
        @Override
        public void onChange(RealmResults<UserInfo> userInfos, OrderedCollectionChangeSet changeSet) {
            if (userInfos.isLoaded()) {
                showData(userInfos);
            }
        }
    };

    private void showData(RealmResults<UserInfo> userInfos) {
        try {
//            RealmResults<UserInfo> userInfos = realm.where(UserInfo.class).findAll();
            userInfoAdapter = new UserInfoAdapter(ViewDataActivity.this, userInfos);
            recyclerView.setAdapter(userInfoAdapter);
            userInfoAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
