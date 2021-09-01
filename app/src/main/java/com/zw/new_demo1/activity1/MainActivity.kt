package com.zw.new_demo1.activity1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.zw.new_demo1.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_test1.setOnClickListener(this)

        Intent.ACTION_PACKAGE_REPLACED
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_test1 -> {

            }
        }
    }
}