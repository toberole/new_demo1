package com.zw.retrofit_demo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.zw.rxjava_demo.R
import kotlinx.android.synthetic.main.activity_launch.*

class LaunchActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        var base_url = "http://192.168.8.205:8001/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        btn_test1.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_test1 -> {

            }
        }
    }
}