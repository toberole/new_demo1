package com.zw.new_demo1

import android.app.Application
import android.os.Environment
import android.util.Log

class App : Application() {
    companion object {
        var TAG = App.javaClass.simpleName

        init {
            System.loadLibrary("native-lib")
        }
    }

    override fun onCreate() {
        super.onCreate()

        val test_apk_path = Environment.getExternalStorageDirectory().toString() + "/app-debug.apk"
        Log.i(TAG, "test_apk_path: $test_apk_path")
    }
}