package com.example.chaitanya.realmdemo.Thread;

import android.app.Activity;
import android.util.Log;

import com.example.chaitanya.realmdemo.Activity.ViewDataActivity;

/**
 * @author : Chaitanya Tarole, Pune.
 * @since : 1/8/18,11:27 AM.
 * For : ISS 24/7, Pune.
 */
public class Add extends Thread {

    ViewDataActivity viewDataActivity;
    int i = 1;

    public Add(Activity activity) {
        viewDataActivity = (ViewDataActivity) activity;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {

            viewDataActivity.add(i);

            Log.d("Add@@",""+i);

            try {
//            sleep((int)(Math.random() * 100));
                sleep(2000);
            } catch (InterruptedException e) {
            }
        }
    }

}
