package com.example.chaitanya.realmdemo.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;


import com.example.chaitanya.realmdemo.Fragment.InsertFragment;
import com.example.chaitanya.realmdemo.Fragment.ViewFragment;
import com.example.chaitanya.realmdemo.Model.UserInfo;
import com.example.chaitanya.realmdemo.R;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class UserInfoAdapter extends RecyclerView.Adapter<UserInfoAdapter.ViewHolder> {

    Activity objContext;
    ArrayList<UserInfo> userInfoArrayList = new ArrayList<>();


    public interface OnClick {
        public abstract void onUpdate(String name);
    }


    public OnClick onClick;

    public UserInfoAdapter(ViewFragment viewFragment, RealmResults<UserInfo> userInfos, Activity objContext) {
        this.userInfoArrayList.addAll(userInfos);
        this.objContext = objContext;
        onClick = (OnClick) viewFragment;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final UserInfo userInfo = userInfoArrayList.get(position);

        holder.txtName.setText(""+userInfo.getName());
        holder.txtAge.setText("Age : " + userInfo.getAge());
        holder.txtMobile.setText("Mobile : " + userInfo.getMobile());
        if (userInfo.isStatus()) {
            holder.txtStatus.setText("Status : " + "Active");
        }

        holder.viewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(holder, holder.getAdapterPosition());
            }
        });

    }


    private void showDialog(final ViewHolder holder, final int position) {
        PopupMenu popup = new PopupMenu(objContext, holder.imgView);
        popup.inflate(R.menu.option_menu);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.view:

                        break;
                    case R.id.update:
                        update(holder.txtName.getText().toString());
                        break;
                    case R.id.delete:
                        delete(position);
                        break;
                }
                return false;
            }
        });
        popup.show();
    }

    private void update(final String name) {
        onClick.onUpdate(name);
    }

    private void delete(final int position) {
        Realm realm = Realm.getDefaultInstance();
        final RealmResults<UserInfo> results = realm.where(UserInfo.class).findAll();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                UserInfo userInfo = results.get(position);
                userInfo.deleteFromRealm();
                userInfoArrayList.remove(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return userInfoArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View viewItem;
        private TextView txtName, txtAge, txtMobile, txtStatus;
        private ImageView imgView;

        public ViewHolder(View view) {
            super(view);
            viewItem = view;
            txtName = (TextView) view.findViewById(R.id.txtName);
            txtAge = (TextView) view.findViewById(R.id.txtAge);
            txtMobile = (TextView) view.findViewById(R.id.txtMobile);
            txtStatus = (TextView) view.findViewById(R.id.txtStatus);
            imgView = (ImageView) view.findViewById(R.id.imgView);
        }

    }
}
