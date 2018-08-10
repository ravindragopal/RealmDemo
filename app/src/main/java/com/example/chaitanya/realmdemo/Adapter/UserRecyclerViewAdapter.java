package com.example.chaitanya.realmdemo.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.chaitanya.realmdemo.Activity.AddDataActivity;
import com.example.chaitanya.realmdemo.Model.UserInfo;
import com.example.chaitanya.realmdemo.R;

import io.realm.ObjectChangeSet;
import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollection;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmModel;
import io.realm.RealmObjectChangeListener;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

/**
 * @author : Chaitanya Tarole, Pune.
 * @since : 30/7/18,5:16 PM.
 * For : ISS 24/7, Pune.
 */
public class UserRecyclerViewAdapter extends RealmRecyclerViewAdapter<UserInfo, UserRecyclerViewAdapter.ViewHolder> {


    Activity objContext;


    private OrderedRealmCollection<UserInfo> adapterData;


   /* public interface OnCallFragment{
        public void onClickAddButton();
    }

    public OnCallFragment onCallFragment;*/


    public UserRecyclerViewAdapter(@Nullable OrderedRealmCollection<UserInfo> data, Activity activity) {
        super(data, true, true);
        this.objContext = activity;
//        this.onCallFragment = (OnCallFragment) objContext;
        setHasStableIds(true);
    }

    @Override
    public void updateData(@Nullable OrderedRealmCollection<UserInfo> data) {
        super.updateData(data);
    }

    @Override
    public UserRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final UserRecyclerViewAdapter.ViewHolder holder, int position) {
        final UserInfo userInfo = getItem(position);

        holder.bind(userInfo);

        holder.txtName.setText("" + userInfo.getName());
        holder.txtAge.setText("Age : " + userInfo.getAge());
        holder.txtMobile.setText("Mobile : " + userInfo.getMobile());
        if (userInfo.isStatus()) {
            holder.txtStatus.setText("Status : " + "Active");
        }

        holder.imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(holder, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public long getItemId(int index) {
        return getItem(index).getId();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        View viewItem;
        public TextView txtName, txtAge, txtMobile, txtStatus;
        public ImageView imgView;

        public ViewHolder(View view) {
            super(view);
            viewItem = view;
            txtName = (TextView) view.findViewById(R.id.txtName);
            txtAge = (TextView) view.findViewById(R.id.txtAge);
            txtMobile = (TextView) view.findViewById(R.id.txtMobile);
            txtStatus = (TextView) view.findViewById(R.id.txtStatus);
            imgView = (ImageView) view.findViewById(R.id.imgView);
        }

        RealmObjectChangeListener realmChangeListener = new RealmObjectChangeListener<UserInfo>() {
            @Override
            public void onChange(UserInfo userInfo, ObjectChangeSet changeSet) {

                if (changeSet.isFieldChanged("name")) {
                    txtName.setText(userInfo.getName());
                }
                if (changeSet.isFieldChanged("age")) {
                    txtAge.setText(userInfo.getAge());
                }
                if (changeSet.isFieldChanged("mobile")) {
                    txtMobile.setText(userInfo.getMobile());
                }
            }
        };

        UserInfo userInfo;

        private void bind(UserInfo userInfo){
            if(this.userInfo != null && this.userInfo.isValid()) {
                this.userInfo.removeAllChangeListeners();
            }
            this.userInfo = userInfo;
            userInfo.addChangeListener(realmChangeListener);
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
                        addNew();
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

    private void addNew() {
//        Intent intent = new Intent(objContext, AddDataActivity.class);
//        intent.putExtra("name", "");
//        objContext.startActivity(intent);
//        objContext.finish();
//        onCallFragment.onClickAddButton();
    }

    private void update(final String name) {
        Intent intent = new Intent(objContext, AddDataActivity.class);
        intent.putExtra("name", name);
        objContext.startActivity(intent);
//        objContext.finish();
    }

    private void delete(final int position) {
        Realm realm = Realm.getDefaultInstance();
        final RealmResults<UserInfo> results = realm.where(UserInfo.class).findAll();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                UserInfo userInfo = results.get(position);
                userInfo.deleteFromRealm();
                notifyDataSetChanged();
            }
        });

    }

}
