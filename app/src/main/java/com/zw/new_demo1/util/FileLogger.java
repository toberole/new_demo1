package com.zw.new_demo1.util;

import android.content.Context;
import android.os.Process;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;

public class FileLogger {
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SS");

    private static final String LOG_V = "V";
    private static final String LOG_D = "D";
    private static final String LOG_I = "I";
    private static final String LOG_W = "W";
    private static final String LOG_E = "E";

    private Context context;
    private final String log_dir;
    private String name;

    public static FileLogger getInstance(Context ctx, String name) {
        return new FileLogger(ctx, name);
    }

    public FileLogger v(String tag, String msg) {
        return writeLog(LOG_V, tag, msg);
    }

    public FileLogger d(String tag, String msg) {
        return writeLog(LOG_D, tag, msg);
    }

    public FileLogger i(String tag, String msg) {
        return writeLog(LOG_I, tag, msg);
    }

    public FileLogger w(String tag, String msg) {
        return writeLog(LOG_W, tag, msg);
    }

    public FileLogger e(String tag, String msg) {
        return writeLog(LOG_E, tag, msg);
    }

    private FileLogger writeLog(String level, String tag, String msg) {
        try {
            FileWriter fileWriter = new FileWriter(log_dir + "/" + name + ".txt", true);
            int pid = Process.myPid();
            String packageName = context.getApplicationInfo().packageName;
            String s = simpleDateFormat.format(System.currentTimeMillis()) + " " + pid + "/" + packageName + " [" + tag + "]" + "[" + level + "]/" + msg + "\r";
            fileWriter.write(s);
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    private FileLogger(Context ctx, String name) {
        this.context = ctx;
        this.name = name;

        log_dir = context.getFilesDir().getAbsolutePath() + "/logs";
        File f = new File(log_dir);
        if (!f.exists()) f.mkdirs();
    }
}