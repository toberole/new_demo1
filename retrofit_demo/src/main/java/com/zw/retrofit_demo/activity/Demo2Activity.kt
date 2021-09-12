package com.zw.retrofit_demo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.zw.rxjava_demo.R
import kotlinx.android.synthetic.main.activity_demo2.*

class Demo2Activity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo2)

        btn_test1.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_test1 -> {
                test1()
            }
        }
    }

    private fun test1() {

    }
}