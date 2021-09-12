package com.zw.flutter_demo.demo;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import io.flutter.embedding.android.FlutterActivity;

public class Demo1FlutterActivity extends FlutterActivity {
    public static final String TAG = Demo1FlutterActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Demo1FlutterActivity#onCreate ......");
    }

    @Override
    public String getInitialRoute() {
        Log.i(TAG, "Demo1FlutterActivity#getInitialRoute ......");
        return super.getInitialRoute();
    }
}
