package com.zw.new_demo1.activity1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.zw.new_demo1.R;
import com.zw.new_demo1.service.JobHandlerService;
import com.zw.new_demo1.util.FileLogger;
import com.zw.new_demo1.util.MMap;
import com.zw.new_demo1.util.NativeFileLogger;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

public class Demo1Activity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = Demo1Activity.class.getSimpleName();

    public static final int interval = 1000 * 5;
    public static final int first_delay = 1000 * 10;
    public static final int weakup_msg_what = 1024;

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SS");

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null && weakup_msg_what == msg.what) {
                send_weakup_Broadcast();
                handler.sendEmptyMessageDelayed(weakup_msg_what, interval);
            }
        }
    };

    private void send_weakup_Broadcast() {
        Intent intent = new Intent();
        intent.setAction("_com.meituan.qcs.meishi.wakeup_");
        intent.putExtra("interval", interval);
        intent.putExtra("first_delay", first_delay);
        sendBroadcast(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        handler.sendEmptyMessageDelayed(weakup_msg_what, first_delay);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo1);
        NativeFileLogger.getInstance().init(this);

        // Intent.ACTION_APP_ERROR
        setContentView(R.layout.activity_demo1);
        findViewById(R.id.btn_send).setOnClickListener(this);
        findViewById(R.id.btn_job).setOnClickListener(this);
        findViewById(R.id.btn_test1).setOnClickListener(this);
        findViewById(R.id.btn_test2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                send_weakup_Broadcast();
                break;
            case R.id.btn_job:
                JobHandlerService.scheduleJob(getApplicationContext());
                break;
            case R.id.btn_test1:
                MMap.native_write(MMap.CACHE_FILE, "hello 11111".getBytes());
                break;
            case R.id.btn_test2:
                test_log();
                break;
            default:
                break;
        }
    }

    private void test_log() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int count = 10000 * 100;
                    String s = "fileLogger.native_write fileLogger.native_write fileLogger.native_write fileLogger.native_write\r";
                    long t = System.currentTimeMillis();
                    for (int i = 0; i < count; i++) {
                        NativeFileLogger.getInstance().i(TAG, i + "---> " + s);
                    }
                    NativeFileLogger.getInstance().flush();
                    long end = System.currentTimeMillis();
                    long NativeFileLogger_time = end - t;
                    Log.i("time-xxx", "NativeFileLogger time: " + NativeFileLogger_time);
                    Log.i("time-xxx", "NativeFileLogger average time: " + 1.0 * NativeFileLogger_time / count);

                    FileOutputStream fileOutputStream = new FileOutputStream(Environment.getExternalStorageDirectory() + "/test.txt", true);
                    t = System.currentTimeMillis();
                    for (int i = 0; i < count; i++) {
                        String m = i + "---> " + s;
                        int pid = android.os.Process.myPid();
                        String packageName = getApplicationInfo().packageName;
                        String msg = simpleDateFormat.format(System.currentTimeMillis()) + " " + pid + "/" + packageName + " [" + TAG + "]" + "[" + "D" + "]/" + m + "\r";
                        fileOutputStream.write(msg.getBytes());
                    }
                    end = System.currentTimeMillis();
                    long fileLogger_time = end - t;
                    Log.i("time-xxx", "FileLogger time: " + fileLogger_time);
                    Log.i("time-xxx", "FileLogger average time: " + 1.0 * fileLogger_time / count);
                    Log.i("time-xxx", "FileLogger/NativeFileLogger: " + 1.0 * fileLogger_time / NativeFileLogger_time);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void test_log1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 10000 * 100;
                String s = "fileLogger.native_write fileLogger.native_write fileLogger.native_write fileLogger.native_write\r";
                long t = System.currentTimeMillis();
                for (int i = 0; i < count; i++) {
                    NativeFileLogger.getInstance().i(TAG, i + "---> " + s);
                }
                NativeFileLogger.getInstance().flush();
                long end = System.currentTimeMillis();
                long NativeFileLogger_time = end - t;
                Log.i("time-xxx", "NativeFileLogger time: " + NativeFileLogger_time);
                Log.i("time-xxx", "NativeFileLogger average time: " + 1.0 * NativeFileLogger_time / count);

                FileLogger fileLogger = FileLogger.getInstance(Demo1Activity.this, "test");
                t = System.currentTimeMillis();
                for (int i = 0; i < count; i++) {
                    fileLogger.i(TAG, i + "---> " + s);
                }
                end = System.currentTimeMillis();
                long fileLogger_time = end - t;
                Log.i("time-xxx", "FileLogger time: " + fileLogger_time);
                Log.i("time-xxx", "FileLogger average time: " + 1.0 * fileLogger_time / count);
                Log.i("time-xxx", "FileLogger/NativeFileLogger: " + 1.0 * fileLogger_time / NativeFileLogger_time);
            }
        }).start();
    }

    private void test1() {
//        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
//        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "");
    }
}