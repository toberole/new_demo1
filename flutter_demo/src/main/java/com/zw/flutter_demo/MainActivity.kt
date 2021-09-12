package com.zw.flutter_demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.zw.flutter_demo.R
import com.zw.flutter_demo.activity.Demo1Activity
import com.zw.flutter_demo.demo.Demo1FlutterActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_Demo1Activity.setOnClickListener(this)
        btn_Demo1FlutterActivity.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_Demo1Activity -> {
                var i = Intent(this@MainActivity, Demo1Activity::class.java)
                startActivity(i)
            }

            R.id.btn_Demo1FlutterActivity -> {
                var i = Intent(this@MainActivity, Demo1FlutterActivity::class.java)
                startActivity(i)
            }
        }
    }
}