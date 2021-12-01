package com.zw.new_demo1.activity1

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.zw.new_demo1.R
import kotlinx.android.synthetic.main.activity_system_info.*

class SystemInfoActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        var TAG = SystemInfoActivity::class.java.simpleName + "-xxx"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_system_info)
        btn_printProcessInfo.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_printProcessInfo -> {
                var ps = SystemInfoUtil.getProcessInfo(this)
                ps?.let {
                    for (p in ps) {
                        Log.i(TAG, "ProcessInfo: $p")
                    }
                }
            }
        }
    }
}