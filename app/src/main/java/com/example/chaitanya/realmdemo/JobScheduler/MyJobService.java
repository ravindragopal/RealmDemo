package com.example.chaitanya.realmdemo.JobScheduler;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.JobIntentService;
import android.util.Log;

/**
 * @author : Chaitanya Tarole, Pune.
 * @since : 3/8/18,4:35 PM.
 * For : ISS 24/7, Pune.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MyJobService extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d("@@","onStartJob");
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d("@@","onStopJob");
        return false;
    }


}
