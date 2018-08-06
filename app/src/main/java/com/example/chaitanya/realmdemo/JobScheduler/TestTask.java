package com.example.chaitanya.realmdemo.JobScheduler;

import android.os.AsyncTask;
import android.util.Log;

/**
 * @author : Chaitanya Tarole, Pune.
 * @since : 6/8/18,3:53 PM.
 * For : ISS 24/7, Pune.
 */
public class TestTask extends AsyncTask<Void,Void,Void> {

    @Override
    protected Void doInBackground(Void... voids) {
        Log.d("@@","doInBackground");
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d("@@","onPostExecute");

    }
}
