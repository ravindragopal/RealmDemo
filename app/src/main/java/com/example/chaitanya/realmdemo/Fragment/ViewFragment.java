package com.example.chaitanya.realmdemo.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chaitanya.realmdemo.Adapter.UserInfoAdapter;
import com.example.chaitanya.realmdemo.Model.UserInfo;
import com.example.chaitanya.realmdemo.R;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewFragment extends Fragment implements UserInfoAdapter.OnClick {

    View view = null;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    UserInfoAdapter userInfoAdapter;
    ArrayList<UserInfo> userInfoArrayList = new ArrayList<>();

    Realm realm;

    public ViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_view, container, false);

        realm = Realm.getDefaultInstance();

        initilization();

        return view;
    }

    private void initilization() {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);


        showData();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void showData() {

        try {

            RealmResults<UserInfo> userInfos = realm.where(UserInfo.class).findAll();

            userInfoAdapter = new UserInfoAdapter(this, userInfos, getActivity());
            recyclerView.setAdapter(userInfoAdapter);
            userInfoAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdate(String name) {
        Bundle bundle=new Bundle();
        bundle.putString("name", name);
        Fragment insertFragment = new InsertFragment();
        insertFragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, insertFragment).commit();
    }
}
