package com.zw.new_demo1.device;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.zw.new_demo1.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ShellActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "ShellActivity";

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shell);
        findViewById(R.id.btn_top).setOnClickListener(this);

        HandlerThread handlerThread = new HandlerThread("cpu");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_top:
                List<String> list = new ArrayList<>();
                list.add("com.zw.new_demo1");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getProcessCpuRate(list);
                        handler.postDelayed(this, 1000 * 10);
                    }
                }, 1000 * 10);
                break;
            default:
                break;
        }
    }

    private void getProcessCpuRate(List<String> filter) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long time = System.currentTimeMillis();
                try {
                    // 注意不同系统 执行shell命令 结果不一定相同
                    Process p = Runtime.getRuntime().exec("top -n 1");
                    BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String result;
                    while ((result = br.readLine()) != null) {
                        if (TextUtils.isEmpty(result)) {
                            continue;
                        }

                        boolean b = false;
                        for (int i = 0; i < filter.size(); i++) {
                            String s = filter.get(i);
                            if (result.contains(s)) {
                                b = true;
                                break;
                            }
                        }
                        if (!b) {
                            continue;
                        }

                        Log.d(TAG + "-cpu", result);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Log.d(TAG, "getProcessCpuRate time: " + (System.currentTimeMillis() - time));
            }
        }).start();
    }
}