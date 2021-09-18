package com.zw.new_demo1.activity1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.view.View;

import com.zw.new_demo1.R;

public class Demo1Activity extends AppCompatActivity implements View.OnClickListener {
    public static final int interval = 1000 * 5;
    public static final int first_delay = 1000 * 10;
    public static final int weakup_msg_what = 1024;

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
        // Intent.ACTION_APP_ERROR
        setContentView(R.layout.activity_demo1);
        findViewById(R.id.btn_send).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                send_weakup_Broadcast();
                break;
            default:
                break;
        }
    }

    private void test1() {
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "");
    }
}