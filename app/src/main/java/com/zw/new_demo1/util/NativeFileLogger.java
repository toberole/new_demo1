package com.zw.new_demo1.util;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Process;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * 常规方案的缺陷
 * 性能问题：
 * 一开始日志的写入就是通过标准I/O直接写文件，当有一条日志要写入的时候，首先，打开文件，然后写入日志，最后关闭文件。但是写文件是 IO 操作，随着日志量的增加，更多的IO操作，一定会造成性能瓶颈。为什么这么说呢？因为数据从程序写入到磁盘的过程中，其实牵涉到两次数据拷贝：一次是用户空间内存拷贝到内核空间的缓存，一次是回写时内核空间的缓存到硬盘的拷贝。当发生回写时也涉及到了内核空间和用户空间频繁切换。
 * 丢日志：
 * 为了解决性能问题，直接想到就是减少I/O操作，我们可以先把日志缓存到内存中，当达到一定数量或者在合适的时机将内存里的日志写入磁盘中。这样似乎可以减少I/O操作，但是在将内存里的日志写入磁盘的过程中，app被强杀了或者Crash了的话，这样会造成更严重的问题，日志丢失
 */
public class NativeFileLogger {
    private final static String TAG = NativeFileLogger.class.getSimpleName();

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SS");

    private static final String LOG_FILE_NAME_SUFFIX = "_native_file_logger_.log";

    private static final long ONE_DAY = 24 * 60 * 60 * 1000;
    private static final long MAX_SIZE = 1024 * 1024 * 16;

    private static final String LOG_V = "V";
    private static final String LOG_D = "D";
    private static final String LOG_I = "I";
    private static final String LOG_W = "W";
    private static final String LOG_E = "E";

    private Context context;
    private long instance = 0;

    public static final int FLUSH_WHAT = 1024;
    private HandlerThread handlerThread = new HandlerThread("flush");
    private Handler handler;

    public synchronized boolean init(Context ctx) {
        context = ctx.getApplicationContext();
        String log_file_path = initFileLogger(context);
        Log.i(TAG, "NativeFileLogger initFileLogger log_file_path: " + log_file_path);
        instance = native_init(log_file_path);
        Log.i(TAG, "NativeFileLogger init instance: " + instance);

        if (instance != 0) {
            // 定时刷新到磁盘
            handlerThread.start();
            handler = new Handler(handlerThread.getLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    flush();
                    handler.sendEmptyMessageDelayed(FLUSH_WHAT, 30 * 1000);
                }
            };
            handler.sendEmptyMessageDelayed(FLUSH_WHAT, 30 * 1000);
        }
        return instance != 0;
    }

    public synchronized void flush() {
        if (instance != 0) {
            native_flush(instance);
        }
    }

    public synchronized void close() {
        if (instance != 0) {
            native_close(instance);
            instance = 0;
        }
    }

    public synchronized NativeFileLogger v(String tag, String msg) {
        return writeLog(LOG_V, tag, msg);
    }

    public synchronized NativeFileLogger d(String tag, String msg) {
        return writeLog(LOG_D, tag, msg);
    }

    public synchronized NativeFileLogger i(String tag, String msg) {
        return writeLog(LOG_I, tag, msg);
    }

    public synchronized NativeFileLogger w(String tag, String msg) {
        return writeLog(LOG_W, tag, msg);
    }

    public synchronized NativeFileLogger e(String tag, String msg) {
        return writeLog(LOG_E, tag, msg);
    }

    private synchronized NativeFileLogger writeLog(String level, String tag, String msg) {
        if (instance != 0) {
            int pid = Process.myPid();
            String packageName = context.getApplicationInfo().packageName;
            String s = simpleDateFormat.format(System.currentTimeMillis()) + " " + pid + "/" + packageName + " [" + tag + "]" + "[" + level + "]/" + msg + "\n";
            native_write(instance, s);
        }
        return this;
    }

    private synchronized String initFileLogger(Context context) {
        String log_dir = context.getFilesDir() + "/logs";
        File file = new File(log_dir);
        if (!file.exists()) file.mkdirs();

        String log_file = System.currentTimeMillis() + LOG_FILE_NAME_SUFFIX;

        try {
            File[] fs = file.listFiles();
            if (null != fs && fs.length > 0) {
                for (File f : fs) {
                    String fileName = f.getName();
                    if (fileName.endsWith(LOG_FILE_NAME_SUFFIX)) {
                        if (f.length() >= MAX_SIZE) {
                            f.delete();
                            String cacheFileName = fileName.substring(0, fileName.indexOf(".log"));
                            File cacheFile = new File(log_dir + "/" + cacheFileName + ".cache");
                            cacheFile.delete();
                            break;
                        }

                        int index = fileName.indexOf("_");
                        if (index > 0) {
                            String timeStr = fileName.substring(0, index);
                            long time = Long.parseLong(timeStr);
                            if (System.currentTimeMillis() - time >= 7 * ONE_DAY) {
                                f.delete();
                            } else {
                                log_file = f.getName();
                            }
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return log_dir + "/" + log_file;
    }

    private native long native_init(String log_file_path);

    private native void native_write(long instance, String str);

    private native void native_flush(long instance);

    private native void native_close(long instance);

    public native void native_data(long instance,byte[]arr);

    public static NativeFileLogger getInstance() {
        return NativeFileLoggerHolder.fileLogger;
    }

    private NativeFileLogger() {
    }

    private static class NativeFileLoggerHolder {
        public static NativeFileLogger fileLogger = new NativeFileLogger();
    }
} 