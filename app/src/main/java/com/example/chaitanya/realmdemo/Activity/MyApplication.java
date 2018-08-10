package com.example.chaitanya.realmdemo.Activity;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * @author : Chaitanya Tarole, Pune.
 * @since : 27/7/18,10:29 AM.
 * For : ISS 24/7, Pune.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("test.realm")
//                .encryptionKey()
//                .schemaVersion()
                .build();
        Realm.setDefaultConfiguration(configuration);
    }
}
