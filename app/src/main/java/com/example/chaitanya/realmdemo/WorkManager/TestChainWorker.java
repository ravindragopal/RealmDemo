package com.example.chaitanya.realmdemo.WorkManager;

import android.support.annotation.NonNull;

import androidx.work.Worker;

/**
 * @author : Chaitanya Tarole, Pune.
 * @since : 7/8/18,12:39 PM.
 * For : ISS 24/7, Pune.
 */
public class TestChainWorker extends Worker {

    @Override
    public Result doWork() {

        return Result.SUCCESS;
    }
}
