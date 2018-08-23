package com.example.chaitanya.realmdemo.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.chaitanya.realmdemo.Activity.AddDataActivity;
import com.example.chaitanya.realmdemo.Activity.ViewDataActivity;
import com.example.chaitanya.realmdemo.Model.UserInfo;
import com.example.chaitanya.realmdemo.R;
import com.example.chaitanya.realmdemo.Retrofit.RetroPhoto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class UserInfoAdapter extends RecyclerView.Adapter<UserInfoAdapter.ViewHolder> {

    Activity objContext;

    ArrayList<UserInfo> userInfoArrayList = new ArrayList<>();

    private List<RetroPhoto> dataList;

    public UserInfoAdapter(Activity activity, RealmResults<UserInfo> userInfos) {
        this.userInfoArrayList.addAll(userInfos);
        this.objContext = activity;
    }

    public UserInfoAdapter(Activity context, List<RetroPhoto> res) {
        this.objContext = context;
        this.dataList = res;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.retro_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        String url = dataList.get(position).getUrl();
//        Log.d("@url",url);

        Glide.with(objContext)
                .load(url)
                .into(holder.imgView);

        /*final UserInfo userInfo = userInfoArrayList.get(position);

        holder.txtName.setText("" + userInfo.getName());
        holder.txtAge.setText("Age : " + userInfo.getAge());
        holder.txtMobile.setText("Mobile : " + userInfo.getMobile());
        if (userInfo.isStatus()) {
            holder.txtStatus.setText("Status : " + "Active");
        }

        holder.imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("id@@", "" + userInfo.getId());
                showDialog(holder, holder.getAdapterPosition());
            }
        });

        Log.d("id@@", "" + userInfo.getId());*/

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        View viewItem;
        TextView txtName, txtAge, txtMobile, txtStatus;
        ImageView imgView;

        public ViewHolder(View view) {
            super(view);
            viewItem = view;
            imgView = (ImageView) view.findViewById(R.id.imgView);
        }

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
        Intent intent = new Intent(objContext, AddDataActivity.class);
        intent.putExtra("name", name);
        objContext.startActivity(intent);
        objContext.finish();
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
}
