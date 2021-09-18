package com.zw.new_demo1.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Launcher3BroadcastReceiver extends BroadcastReceiver {
    public static final String TAG = Launcher3BroadcastReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Launcher3BroadcastReceiver#onReceive ......");
    }
}