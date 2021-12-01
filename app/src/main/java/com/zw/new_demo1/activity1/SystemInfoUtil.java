package com.zw.new_demo1.activity1;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.zw.new_demo1.R;

import java.util.ArrayList;
import java.util.List;

public class SystemInfoUtil {
    public static final String TAG = "SystemInfoUtil-xxx";

    /**
     * 获取正在运行的进程信息
     *
     * @return
     */
    public static ArrayList<ProcessInfo> getProcessInfo(Context context) {
        ArrayList<ProcessInfo> infos = new ArrayList<ProcessInfo>();
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        PackageManager packageManager = context.getPackageManager();

        // 获取正在运行的进程信息
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am
                .getRunningAppProcesses();
        // 遍历所有 进程
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            // 进程名字 一般和包名一样
            String processName = runningAppProcessInfo.processName;
            Log.i(TAG, "processName: " + processName);
            // 进程的id
            int pid = runningAppProcessInfo.pid;
            Drawable icon = null;
            String name = null;
            long memSize = 0;
            boolean isSys = false;
            try {
                // 获取一个应用的ApplicationInfo 对象
                ApplicationInfo applicationInfo = packageManager
                        .getApplicationInfo(processName, 0);
                icon = applicationInfo.loadIcon(packageManager);// 图片
                name = applicationInfo.loadLabel(packageManager).toString();// 名字

                if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == ApplicationInfo.FLAG_SYSTEM) {
                    // 是系统进程
                    isSys = true;
                } else {
                    // 是用户进程
                    isSys = false;
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                // 包名:am 系统进程 给一些默认值
                name = processName;
                icon = context.getResources().getDrawable(
                        R.drawable.ic_launcher_background);
                // 认为是系统进程
                isSys = true;
            }

            int[] pids = new int[]{pid};
            // 获取指定pid的进程内存信息 这里可以获取多个
            android.os.Debug.MemoryInfo[] processMemoryInfo = am
                    .getProcessMemoryInfo(pids);
            // 获取内存大小
            memSize = processMemoryInfo[0].getTotalPss() * 1024;
            ProcessInfo info = new ProcessInfo(name, icon, memSize, isSys, processName);
            infos.add(info);
        }
        return infos;
    }
} 