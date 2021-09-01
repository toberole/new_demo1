package com.zw.new_demo1.test1;

import android.app.Instrumentation;
import android.os.Bundle;
import android.util.Log;

public class InstrumentationRoboot extends Instrumentation {
    public static final String TAG = InstrumentationRoboot.class.getSimpleName();

    @Override
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);
        Log.i(TAG, "InstrumentationRoboot#onCreate " + arguments);
    }
}