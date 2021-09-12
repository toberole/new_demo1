package com.zw.new_demo1.service

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.os.Message
import android.util.Log
import com.zw.new_demo1.IMyAidlInterface

class MyService : Service() {
    companion object {
        var TAG = MyService::class.java.simpleName + "-xxx"
    }

    private var handlerThread = HandlerThread("test-service")
    private var handler: Handler? = null

    override fun onCreate() {
        super.onCreate()
        handler = object : Handler(handlerThread.looper) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                System.exit(0)
            }
        }

        Log.i(TAG, "onCreate ......")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "onStartCommand ......")
        return super.onStartCommand(intent, flags, startId)
    }

    private var binder = object : IMyAidlInterface.Stub() {
        override fun basicTypes(
            anInt: Int,
            aLong: Long,
            aBoolean: Boolean,
            aFloat: Float,
            aDouble: Double,
            aString: String?
        ) {
            Log.i(TAG, "anInt: $anInt,aString: $aString")
        }

        override fun test1(s: String?): String {
            var hello = "$s ...... hello"
            return hello
        }
    }

    override fun onBind(intent: Intent): IBinder {
        Log.i(TAG, "onBind ......")
        handler?.sendEmptyMessageDelayed(1, 1000 * 2)
        return binder
    }
}