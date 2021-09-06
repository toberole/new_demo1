package com.zw.new_demo1

import android.app.Application
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileInputStream

class App : Application() {
    companion object {
        var TAG = App.javaClass.simpleName

        init {
            System.loadLibrary("native-lib")
        }
    }

    override fun onCreate() {
        super.onCreate()

        var f = File("${Environment.getExternalStorageDirectory()}/aaa_test_xxx")
        if (!f.exists()) {
            f.mkdirs()
            Log.i("xxxx", "mkdirs $f")
        }

        test1()
    }

    private fun test1() {
        val test_apk_path = Environment.getExternalStorageDirectory().toString() + "/app-debug.apk"
        Log.i(TAG, "test_apk_path: $test_apk_path")

        var f = File(test_apk_path)
        if (f.exists()) {
            Log.i(TAG, "@@@@@ test_apk_path exit")
        }

        Log.i(TAG, "packageName: $packageName")
        try {
            val file = File("/data/system/packages.xm")
            val fileInputStream = FileInputStream(file)
            val len = fileInputStream.available()
            val bytes = ByteArray(len)
            fileInputStream.read(bytes)
            Log.i("test-xxx", String(bytes))
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("test-xxx", "Exception packageName: ${e.message}")
        }
    }
}