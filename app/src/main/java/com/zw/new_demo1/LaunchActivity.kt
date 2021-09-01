package com.zw.new_demo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.zw.new_demo1.activity1.MainActivity
import kotlinx.android.synthetic.main.activity_launch.*

class LaunchActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        btn_test1.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_test1 -> {
                var i = Intent(this@LaunchActivity, MainActivity::class.java)
                startActivity(i)
            }
        }
    }
}