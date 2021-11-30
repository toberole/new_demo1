package com.zw.rxjava_demo.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.zw.rxjava_demo.R
import com.zw.rxjava_demo.activity.test.Test4
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
                Test4.test3()
                var i = Intent(this@MainActivity, LaunchActivity::class.java)
                startActivity(i)
            }
        }
    }
}