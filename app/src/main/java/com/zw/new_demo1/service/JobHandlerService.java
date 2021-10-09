package com.zw.new_demo1.service;

import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.List;

public class JobHandlerService extends JobService {
    public static final String TAG = JobHandlerService.class.getSimpleName();

    private static JobScheduler mJobScheduler;
    public static int jobId = 1;

    public static void scheduleJob(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.i(TAG, "JobHandlerService#onStartCommand ......");
            mJobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
            JobInfo.Builder builder = new JobInfo.Builder(jobId++,
                    new ComponentName(context.getPackageName(), JobHandlerService.class.getName()));

            builder.setPeriodic(5000);
            builder.setPersisted(true);
            builder.setRequiresDeviceIdle(false);
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE);
            builder.setRequiresCharging(false);

            if (mJobScheduler.schedule(builder.build()) <= 0) {
                Log.i(TAG, "工作失败");
            } else {
                Log.i(TAG, "工作成功");
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG, "JobHandlerService#onStartJob ......");
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "JobHandlerService#onStartJob ......");
        return false;
    }

    // 服务是否运行
    public boolean isServiceRunning(Context context, String serviceName) {
        boolean isRunning = false;
        ActivityManager am = (ActivityManager) this
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> lists = am.getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo info : lists) {// 获取运行服务再启动
            System.out.println(info.processName);
            if (info.processName.equals(serviceName)) {
                isRunning = true;
            }
        }
        return isRunning;
    }
}