package com.zw.new_demo1.activity1

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.eclipsesource.v8.JavaCallback
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Array
import com.eclipsesource.v8.V8Object
import com.zw.new_demo1.R
import com.zw.new_demo1.dynamic_js.JsBundle
import com.zw.new_demo1.dynamic_js.JsContext
import kotlinx.android.synthetic.main.activity_j2_v8.*


class J2V8Activity : AppCompatActivity(), View.OnClickListener {
    private companion object {
        var TAG = "J2V8Activity-xxx"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_j2_v8)

        btn_test1.setOnClickListener(this)
        btn_test2.setOnClickListener(this)
        btn_test3.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_test1 -> {
                var v8 = V8.createV8Runtime()
                var result = v8.executeIntegerScript("var i = 0; i++; i");
                Log.i(TAG, "result: $result")
            }

            R.id.btn_test2 -> {
                val runtime = V8.createV8Runtime()
                runtime.executeVoidScript("function add(a, b) { return a + b }")
                val args = V8Array(runtime).push(1).push(2)
                val result = runtime.executeIntegerFunction("add", args)
                Log.i(TAG, "result: $result")
            }

            R.id.btn_test3 -> {
                var jsBundle = JsBundle()
                jsBundle.setAppJavaScript("var a = 1")
                var jsContext = JsContext()
                jsContext.runApplication(jsBundle)
            }
            R.id.btn_test3 -> {
                val runtime = V8.createV8Runtime()
                val console = V8Object(runtime)
                console.registerJavaMethod(JavaCallback { v8Object: V8Object?, params: V8Array ->
                    val msg = params.getString(0)
                    Log.i(TAG, msg)
                    null
                }, "info")
                runtime.add("console", console)

            }
        }
    }
}