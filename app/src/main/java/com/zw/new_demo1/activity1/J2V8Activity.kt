package com.zw.new_demo1.activity1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.eclipsesource.v8.V8
import com.zw.new_demo1.R
import kotlinx.android.synthetic.main.activity_j2_v8.*

class J2V8Activity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_j2_v8)

        btn_test1.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_test1 -> {
                var v8 = V8.createV8Runtime()
                v8.executeScript("")
            }
        }
    }
}