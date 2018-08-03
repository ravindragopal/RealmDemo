package com.example.chaitanya.realmdemo.JobScheduler;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * @author : Chaitanya Tarole, Pune.
 * @since : 3/8/18,3:56 PM.
 * For : ISS 24/7, Pune.
 */
public class ScheduleJob {

    public static final int MY_BACKGROUND_JOB = 0;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void scheduleJob(Context context) {
        JobScheduler js = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);

        JobInfo job = new JobInfo.Builder(
                MY_BACKGROUND_JOB,
                new ComponentName(context, MyJobService.class))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setRequiresCharging(true)
                .build();
        js.schedule(job);
    }

}
