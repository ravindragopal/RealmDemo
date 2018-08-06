package com.example.chaitanya.realmdemo.Fragment;


import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;

import com.example.chaitanya.realmdemo.Activity.AddDataActivity;
import com.example.chaitanya.realmdemo.Activity.ViewDataActivity;
import com.example.chaitanya.realmdemo.Adapter.UserInfoAdapter;
import com.example.chaitanya.realmdemo.Adapter.UserRecyclerViewAdapter;
import com.example.chaitanya.realmdemo.JobScheduler.ScheduleJob;
import com.example.chaitanya.realmdemo.Model.UserInfo;
import com.example.chaitanya.realmdemo.R;

import java.util.ArrayList;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewDataFragment extends Fragment {

    View view = null;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    UserInfoAdapter userInfoAdapter;
    UserRecyclerViewAdapter userRecyclerViewAdapter;
    ArrayList<UserInfo> userInfoArrayList = new ArrayList<>();
    Realm realm;
    SearchView searchView;
    ImageView imgView, imgAdd;
    public RealmResults<UserInfo> userInfos;
    int contents;
    int nextId;

    public interface OnCallFragment{
        public void onClickAddButton();
    }

    public OnCallFragment onCallFragment;


    RealmChangeListener<RealmResults<UserInfo>> listener = new RealmChangeListener<RealmResults<UserInfo>>() {
        @Override
        public void onChange(RealmResults<UserInfo> UserInfo) {
            if (UserInfo.isLoaded()) {
                userRecyclerViewAdapter.notifyDataSetChanged();
            }
        }
    };


    public ViewDataFragment() {
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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_view, container, false);
        Log.d("@", "onCreateView");

        realm = Realm.getDefaultInstance();

        initilization();

        onCallFragment = (OnCallFragment)getActivity();

        userInfos = realm.where(UserInfo.class).findAllAsync();
        userInfos.addChangeListener(listener);
        showData();

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
        userInfos.removeAllChangeListeners();
    }

    @Override
    public void onDetach() {
        Log.d("@", "onDetach");
        super.onDetach();
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

        searchView = (SearchView) view.findViewById(R.id.searchView);
        imgView = (ImageView) view.findViewById(R.id.imgView);
        imgAdd = (ImageView) view.findViewById(R.id.imgAdd);
//        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
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

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getActivity(), AddDataActivity.class);
                intent.putExtra("name", "");
                startActivity(intent);*/

                /*Fragment fragment = new AddDataFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.framLayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/

//                ScheduleJob.scheduleJob(getActivity());

                onCallFragment.onClickAddButton();
            }
        });
    }

    private void shortData() {

        PopupMenu popup = new PopupMenu(getActivity(), imgView);
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
            userRecyclerViewAdapter = new UserRecyclerViewAdapter(userInfos, getActivity());
            recyclerView.setAdapter(userRecyclerViewAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
