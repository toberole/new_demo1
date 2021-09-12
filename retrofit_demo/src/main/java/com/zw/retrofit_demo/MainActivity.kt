package com.zw.retrofit_demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.zw.retrofit_demo.activity.Demo1Activity
import com.zw.rxjava_demo.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_LaunchActivity.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_LaunchActivity -> {
                var i = Intent(this@MainActivity, Demo1Activity::class.java)
                startActivity(i)
            }
        }
    }
}