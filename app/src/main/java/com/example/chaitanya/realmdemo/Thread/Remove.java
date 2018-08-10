package com.example.chaitanya.realmdemo.Thread;

import android.app.Activity;
import android.util.Log;

import com.example.chaitanya.realmdemo.Activity.ViewDataActivity;

/**
 * @author : Chaitanya Tarole, Pune.
 * @since : 1/8/18,11:28 AM.
 * For : ISS 24/7, Pune.
 */
public class Remove extends Thread {

    ViewDataActivity viewDataActivity;

    public Remove(Activity activity) {
        viewDataActivity = (ViewDataActivity) activity;
    }

    @Override
    public void run() {
        int value = 0;
        for (int i = 0; i < 5; i++) {
            value = viewDataActivity.remove();

            Log.d("Remove@@",""+value);

            try {
//            sleep((int)(Math.random() * 100));
                sleep(2000);
            } catch (InterruptedException e) {
            }

        }
    }

}
