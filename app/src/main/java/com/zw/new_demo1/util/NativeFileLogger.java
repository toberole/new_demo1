package com.zw.new_demo1.util;

import android.util.Log;

public class NativeFileLogger {
    private String TAG = "jni-log";
    private long instance;

    public long init(String log_file_path) {
        instance = native_init(log_file_path);
        Log.i(TAG, "NativeFileLogger init instance: " + instance);
        return instance;
    }

    public byte[] read() {
        return native_read(instance);
    }

    public void write(String str) {
        native_write(instance, str);
    }

    public void close() {
        native_close(instance);
    }

    public native long native_init(String log_file_path);

    public native byte[] native_read(long instance);

    public native void native_write(long instance, String str);

    public native void native_close(long instance);
} 