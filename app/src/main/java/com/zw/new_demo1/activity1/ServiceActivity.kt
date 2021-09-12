package com.zw.new_demo1.activity1

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import com.zw.new_demo1.IMyAidlInterface
import com.zw.new_demo1.R
import com.zw.new_demo1.service.MyService
import kotlinx.android.synthetic.main.activity_service.*

class ServiceActivity : AppCompatActivity(), View.OnClickListener, IBinder.DeathRecipient {
    companion object {
        var TAG = ServiceActivity::class.java.simpleName + "-xxx"
    }

    private var myService: IMyAidlInterface? = null

    private var serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.i(TAG, "onServiceConnected --->")
            service?.linkToDeath(this@ServiceActivity, 0)
            myService = IMyAidlInterface.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.i(TAG, "onServiceDisconnected ComponentName: $name --->")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)

        btn_start_service.setOnClickListener(this)
        btn_bind_service.setOnClickListener(this)
        btn_unbind_service.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_start_service -> {
                var i = Intent(this@ServiceActivity, MyService::class.java)
                startService(i)
            }

            R.id.btn_bind_service -> {
                bind_service()
            }

            R.id.btn_unbind_service -> {
                unbind_service()
            }
        }
    }

    private fun unbind_service() {
        var i = Intent(this@ServiceActivity, MyService::class.java)
        this@ServiceActivity.unbindService(serviceConnection)
    }

    override fun binderDied() {
        Log.i(TAG, "binderDied ---> service die")
    }

    private fun bind_service() {
        Log.i(TAG, "bind_service ......")
        var i = Intent(this@ServiceActivity, MyService::class.java)
        this@ServiceActivity.bindService(i, serviceConnection, Service.BIND_AUTO_CREATE)
    }
}