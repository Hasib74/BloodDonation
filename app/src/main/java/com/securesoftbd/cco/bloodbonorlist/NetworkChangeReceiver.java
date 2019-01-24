package com.securesoftbd.cco.bloodbonorlist;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import static android.content.ContentValues.TAG;
import static android.content.Context.JOB_SCHEDULER_SERVICE;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        // throw new UnsupportedOperationException("Not yet implemented");
        Log.d("stop", "Flag ");
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (isNetworkAvailable(context)) {
            // Do something

            Log.d("stop", "Flag No 1");

            ComponentName componentName = new ComponentName(context, DatabaseCheck.class);
            JobScheduler jobScheduler = (JobScheduler)context.getSystemService(JOB_SCHEDULER_SERVICE);
            JobInfo jobInfo = new JobInfo.Builder(1, componentName).setRequiredNetworkType(JobInfo.NETWORK_TYPE_NOT_ROAMING).setPersisted(true).build();
            jobScheduler.schedule(jobInfo);

         /*   ComponentName comp = new ComponentName(context.getPackageName(),
                    DatabaseCheck.class.getName());
            intent.putExtra("isNetworkConnected","isConnected(context)");
            context.startService((intent.setComponent(comp)));*/

/*

            ComponentName serviceName = new ComponentName(context, DatabaseCheck.class);
            int JOB_ID = 10;
            JobInfo jobInfo = new JobInfo.Builder(JOB_ID, serviceName)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                    .setRequiresDeviceIdle(true)
                    .setRequiresCharging(true)
                    .build();


            JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
            int result = scheduler.schedule(jobInfo);
            if (result == JobScheduler.RESULT_SUCCESS) {
                Log.d(TAG, "Job scheduled successfully");
            }

            }
*/

            //  jobScheduler.schedule(builder.build());
        }

    }
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
