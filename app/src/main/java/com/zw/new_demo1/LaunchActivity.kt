package com.zw.new_demo1

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.zw.new_demo1.activity1.*
import com.zw.new_demo1.device.ShellActivity
import com.zw.new_demo1.device.SystemInfoActivity
import kotlinx.android.synthetic.main.activity_launch.*
import java.util.*

class LaunchActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        var TAG = LaunchActivity::class.java.simpleName
    }

    private var PS = arrayOf<String>(
        Manifest.permission.INTERNET,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private var handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            Log.i(TAG, "handleMessage")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        testMem()

        testHandler()

        ActivityCompat.requestPermissions(this, PS, 110)
        btn_MainActivity.setOnClickListener(this)
        btn_RxjavaActivity.setOnClickListener(this)
        btn_ServiceActivity.setOnClickListener(this)
        btn_Demo1Activity.setOnClickListener(this)
        btn_SystemInfoActivity.setOnClickListener(this)
        btn_ShellActivity.setOnClickListener(this)



        testlogFiles()
    }

    private fun testHandler() {
        var runnable = object : Runnable {
            override fun run() {
                Log.i(TAG, "testHandler")
            }
        }
        Log.i("$TAG-xxx", "testHandler")
        handler.removeCallbacks(runnable)
    }

    private fun testMem() {
        val javaMax = Runtime.getRuntime().maxMemory()
        val javaTotal = Runtime.getRuntime().totalMemory()
        val javaUsed = javaTotal - Runtime.getRuntime().freeMemory()
        // Java 内存使用超过最大限制的 85%
        val proportion = javaUsed.toFloat() / javaMax
        Log.i("xxxx", "proportion: $proportion")
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_Demo1Activity -> {
                var i = Intent(this@LaunchActivity, Demo1Activity::class.java)
                startActivity(i)
            }
            R.id.btn_MainActivity -> {
                var i = Intent(this@LaunchActivity, RetrofitActivity::class.java)
                startActivity(i)
            }
            R.id.btn_RxjavaActivity -> {
                var i = Intent(this@LaunchActivity, RxjavaActivity::class.java)
                startActivity(i)
            }
            R.id.btn_ServiceActivity -> {
                var i = Intent(this@LaunchActivity, ServiceActivity::class.java)
                startActivity(i)
            }
            R.id.btn_SystemInfoActivity -> {
                var i = Intent(this@LaunchActivity, SystemInfoActivity::class.java)
                startActivity(i)
            }
            R.id.btn_ShellActivity -> {
                var i = Intent(this@LaunchActivity, ShellActivity::class.java)
                startActivity(i)
            }

        }
    }

    private fun testlogFiles() {
        var list = ArrayList<String>()
        com.zw.new_demo1.util.FileUtil.logFiles("/mnt/sdcard/aaa_test_xxx", list)
        for (s in list) {
            Log.i("path-xxx", s)
        }
    }
}