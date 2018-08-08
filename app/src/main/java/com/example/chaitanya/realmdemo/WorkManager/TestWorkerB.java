package com.example.chaitanya.realmdemo.WorkManager;

import android.support.annotation.NonNull;
import android.util.Log;

import androidx.work.Worker;

/**
 * @author : Chaitanya Tarole, Pune.
 * @since : 7/8/18,12:39 PM.
 * For : ISS 24/7, Pune.
 */
public class TestWorkerB extends Worker {

    @Override
    public Result doWork() {

        Log.d("@", "TestWorkerB");

        return Result.SUCCESS;
    }
}
