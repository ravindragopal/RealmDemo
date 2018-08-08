package com.example.chaitanya.realmdemo.WorkManager;

import android.arch.lifecycle.Observer;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.chaitanya.realmdemo.Adapter.UserInfoAdapter;
import com.example.chaitanya.realmdemo.R;
import com.example.chaitanya.realmdemo.Retrofit.RetroPhoto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkStatus;

public class WorkManagerActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button btnStart, btnResult;

    OneTimeWorkRequest oneTimeWorkRequestA;
    OneTimeWorkRequest oneTimeWorkRequestB;
    PeriodicWorkRequest periodicWorkRequest;

    public static List<RetroPhoto> retroPhotoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_manager);

        btnResult = (Button) findViewById(R.id.btnResult);
        btnStart = (Button) findViewById(R.id.btnStart);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(llm);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OneTimeWorkRequestA();
            }
        });

        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workStatus();
            }
        });
    }


    private void OneTimeWorkRequestA() {

        Log.d("@", "OneTimeWorkRequest");

        // Create a Constraints that defines when the task should run
        Constraints myConstraints = new Constraints.Builder()
                .setRequiresCharging(true)
                // Many other constraints are available, see the
                // Constraints.Builder reference
                .build();

        Data myData = new Data.Builder()
                .putString("key", "TestInputData")
                .build();

        oneTimeWorkRequestA = new OneTimeWorkRequest.Builder(TestWorkerA.class)
                .setConstraints(myConstraints)
                .setInputData(myData)
                .build();

        WorkManager.getInstance().enqueue(oneTimeWorkRequestA);

    }

    private void OneTimeWorkRequestB() {

        Log.d("@", "OneTimeWorkRequest");

        // Create a Constraints that defines when the task should run
        Constraints myConstraints = new Constraints.Builder()
                .setRequiresCharging(true)
                // Many other constraints are available, see the
                // Constraints.Builder reference
                .build();

        oneTimeWorkRequestB = new OneTimeWorkRequest.Builder(TestWorkerB.class)
                .setConstraints(myConstraints)
                .build();

        WorkManager.getInstance().enqueue(oneTimeWorkRequestB);

    }


    private void periodicWorkRequest() {  // min time interval 15 min & flex duration 5 min

        PeriodicWorkRequest.Builder periodicWorkBuilder =
                new PeriodicWorkRequest.Builder(TestWorkerA.class, 15, TimeUnit.MINUTES);

        // Create a Constraints that defines when the task should run
        Constraints myConstraints = new Constraints.Builder()
                .setRequiresCharging(true)
                // Many other constraints are available, see the
                // Constraints.Builder reference
                .build();

        // Create the actual work object:
        periodicWorkRequest = periodicWorkBuilder
                .setConstraints(myConstraints)
                .build();

        // Then enqueue the recurring task:
        WorkManager.getInstance().enqueue(periodicWorkRequest);
    }

    private void chainedTask() {

        OneTimeWorkRequestA();
        OneTimeWorkRequestB();

        WorkManager.getInstance()
                .beginWith(oneTimeWorkRequestA)
                // Note: WorkManagerActivity.beginWith() returns a
                // WorkContinuation object; the following calls are
                // to WorkContinuation methods
                .then(oneTimeWorkRequestB)    // FYI, then() returns a new WorkContinuation instance
//                .then(workC)
                .enqueue();
    }

    private void workStatus() {

        WorkManager.getInstance().getStatusById(oneTimeWorkRequestA.getId())
                .observe(WorkManagerActivity.this, new Observer<WorkStatus>() {
                    @Override
                    public void onChanged(@Nullable WorkStatus status) {
                        if (status != null && status.getState().isFinished()) {
                            String response = status.getOutputData().getString("res");
                            Log.d("@res", "" + response);
                        }
                    }
                });

        setResponse(retroPhotoList);

    }

    private void cancelTask() {
        UUID compressionWorkId = oneTimeWorkRequestA.getId();
        androidx.work.WorkManager.getInstance().cancelWorkById(compressionWorkId);
    }


    public void setResponse(List<RetroPhoto> retroPhotoList) {
//        Log.d("@retroPhotoList", "" + retroPhotoList.toString());
        UserInfoAdapter userInfoAdapter = new UserInfoAdapter(WorkManagerActivity.this, retroPhotoList);
        recyclerView.setAdapter(userInfoAdapter);
        userInfoAdapter.notifyDataSetChanged();
    }

}
