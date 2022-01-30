package com.zw.new_demo1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class Test {
    public static final String TAG = Test.class.getSimpleName();

    public void test() {
        SharedPreferences sharedPreferences = null;
        sharedPreferences.edit().apply();

        Bundle bundle = new Bundle();
        // bundle.putBinder("", );
    }

    private void test1() {
        Log.i(TAG, "Hello World!");
    }
} 