package com.example.chaitanya.realmdemo.WorkManager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.example.chaitanya.realmdemo.Activity.ViewDataActivity;
import com.example.chaitanya.realmdemo.Adapter.UserInfoAdapter;
import com.example.chaitanya.realmdemo.Retrofit.GetDataService;
import com.example.chaitanya.realmdemo.Retrofit.RetroPhoto;
import com.example.chaitanya.realmdemo.Retrofit.RetrofitClientInstance;

import java.util.List;

import androidx.work.Data;
import androidx.work.Worker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Chaitanya Tarole, Pune.
 * @since : 7/8/18,11:40 AM.
 * For : ISS 24/7, Pune.
 */
public class TestWorkerA extends Worker {

    GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

    Call<List<RetroPhoto>> call;

    String res;

    @Override
    public Result doWork() {

        Log.d("@", "TestWorkerA");

        String test = getInputData().getString("key");

        Log.d("@", test);

        call = service.getAllPhotos();

        call.enqueue(new Callback<List<RetroPhoto>>() {
            @Override
            public void onResponse(Call<List<RetroPhoto>> call, Response<List<RetroPhoto>> response) {

                WorkManagerActivity.retroPhotoList = response.body();
//                res = response.body().toString();
//                Log.d("@resBody", "" + res);
            }

            @Override
            public void onFailure(Call<List<RetroPhoto>> call, Throwable t) {
                Log.d("@", "onFailure");
            }
        });

        Data data = new Data.Builder()
                .putString("res", "setOutputData")
                .build();
        setOutputData(data);

        return Result.SUCCESS;
    }
}
