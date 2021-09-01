package com.zw.new_demo1.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;

public class PowerConnectionReceiver extends BroadcastReceiver {
    public static final String TAG = PowerConnectionReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            float batteryPct = level * 100 / (float) scale;

            Log.i(TAG, "PowerConnectionReceiver batteryPct: " + batteryPct);
        }
    }
}